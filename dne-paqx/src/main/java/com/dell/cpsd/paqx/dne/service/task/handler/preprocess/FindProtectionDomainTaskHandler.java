/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.service.task.handler.preprocess;

import com.dell.cpsd.paqx.dne.domain.IWorkflowTaskHandler;
import com.dell.cpsd.paqx.dne.domain.Job;
import com.dell.cpsd.paqx.dne.domain.node.NodeInventory;
import com.dell.cpsd.paqx.dne.domain.scaleio.ScaleIOData;
import com.dell.cpsd.paqx.dne.domain.scaleio.ScaleIOProtectionDomain;
import com.dell.cpsd.paqx.dne.domain.scaleio.ScaleIOSDS;
import com.dell.cpsd.paqx.dne.domain.vcenter.Host;
import com.dell.cpsd.paqx.dne.repository.DataServiceRepository;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.model.Status;
import com.dell.cpsd.paqx.dne.service.model.TaskResponse;
import com.dell.cpsd.paqx.dne.service.model.ValidateProtectionDomainResponse;
import com.dell.cpsd.paqx.dne.service.task.handler.BaseTaskHandler;
import com.dell.cpsd.service.engineering.standards.EssValidateProtectionDomainsRequestMessage;
import com.dell.cpsd.service.engineering.standards.EssValidateProtectionDomainsResponseMessage;
import com.dell.cpsd.service.engineering.standards.NodeData;
import com.dell.cpsd.service.engineering.standards.ProtectionDomain;
import com.dell.cpsd.service.engineering.standards.ScaleIODataServer;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @version 1.0
 * @since 1.0
 */
@Component
public class FindProtectionDomainTaskHandler extends BaseTaskHandler implements IWorkflowTaskHandler
{
    /**
     * The logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FindProtectionDomainTaskHandler.class);

    /**
     * The <code>NodeService</code> instance
     */
    private NodeService nodeService;

    private final DataServiceRepository repository;

    public FindProtectionDomainTaskHandler(final NodeService nodeService, final DataServiceRepository repository)
    {
        this.nodeService = nodeService;
        this.repository = repository;
    }

    @Override
    public boolean executeTask(Job job)
    {
        LOGGER.info("Execute FindProtectionDomain task");

        TaskResponse response = initializeResponse(job);

        try
        {
            EssValidateProtectionDomainsRequestMessage requestMessage = new EssValidateProtectionDomainsRequestMessage();

            final ScaleIOProtectionDomain scaleIOProtectionDomain = repository.getScaleIoProtectionDomains().stream().findFirst()
                    .orElseGet(null);
            if (scaleIOProtectionDomain == null)
            {
                throw new IllegalStateException("No ScaleIO protection domains found.");
            }

            /**
             * Finding uuid
             */
            String uuid = job.getInputParams().getSymphonyUuid();
            LOGGER.info("Node uuid is:" + uuid);

            /**
             * Setting up NodeData request
             */
            NodeData nodeData = new NodeData();
            nodeData.setSymphonyUuid(uuid);
            nodeData.setType(getNodeType(uuid));

            List<ProtectionDomain> protectionDomainList = requestMessage.getProtectionDomains();
            ProtectionDomain protectionDomainRequest = new ProtectionDomain();

            List<ScaleIOData> scaleIODataList = nodeService.listScaleIOData();
            if (scaleIODataList != null && scaleIODataList.size() > 0)
            {
                ScaleIOData scaleIOData = scaleIODataList.get(0);

                List<ScaleIODataServer> scaleIODataServers = new ArrayList<>();

                List<Host> hosts = repository.getVCenterHosts();
                List<ScaleIOProtectionDomain> protectionDomains = scaleIOData.getProtectionDomains();
                if (protectionDomains != null)
                {
                    /**
                     * Setting up ProtectionDomainList request
                     */
                    for (ScaleIOProtectionDomain protectionDomain : protectionDomains)
                    {
                        protectionDomainRequest.setId(protectionDomain.getId());
                        protectionDomainRequest.setName(protectionDomain.getName());
                        protectionDomainRequest.setState(protectionDomain.getProtectionDomainState());
                        for (ScaleIOSDS scaleIOSDS : protectionDomain.getSdsList())
                        {
                            if (scaleIOSDS == null)
                            {
                                throw new IllegalStateException("No ScaleIO SDS found.");
                            }
                            /**
                             * Setting up ScaleIO data server for each protection domain found.
                             */
                            ScaleIODataServer scaleIODataServer = new ScaleIODataServer();
                            scaleIODataServer.setType(getHostType(scaleIOSDS, hosts));
                            scaleIODataServer.setId(scaleIOSDS.getId());
                            scaleIODataServer.setName(scaleIOSDS.getName());
                            scaleIODataServers.add(scaleIODataServer);

                            if (scaleIOSDS.getName().contains(job.getInputParams().getEsxiManagementHostname()) || scaleIOSDS.getName()
                                    .contains(job.getInputParams().getEsxiManagementIpAddress()))
                            {
                                nodeData.setProtectionDomainId(scaleIOProtectionDomain.getId());
                            }
                            else
                            {
                                nodeData.setProtectionDomainId("");
                            }
                        }
                        protectionDomainRequest.setScaleIODataServers(scaleIODataServers);
                        protectionDomainList.add(protectionDomainRequest);
                    }

                }
            }

            /**
             * Setting up EssValidateProtectionDomainsRequestMessage
             */
            requestMessage.setNodeData(nodeData);
            requestMessage.setProtectionDomains(protectionDomainList);

            /**
             * Creating the response.
             */
            EssValidateProtectionDomainsResponseMessage protectionDomainResponse = nodeService.validateProtectionDomains(requestMessage);
            if (protectionDomainResponse.getValidProtectionDomains().size() == 0)
            {
                response.addError(protectionDomainResponse.getError().getMessage());
                throw new IllegalStateException("No valid protection domain found");
            }

            /**
             * Mapping the actual response to local response
             */
            ValidateProtectionDomainResponse validateProtectionDomainResponse = new ValidateProtectionDomainResponse();
            String protectionDomainId = protectionDomainResponse.getValidProtectionDomains().get(0).getProtectionDomainID();

            // find protection domain based on id
            List<String> protectionDomainNameList = protectionDomainList.stream()
                    .filter(p -> p.getId().equalsIgnoreCase(protectionDomainId)).map(p -> p.getName()).collect(Collectors.toList());

            validateProtectionDomainResponse.setProtectionDomainName(
                    protectionDomainNameList.size() == 1 ? protectionDomainList.get(0).getName() : protectionDomainId);
            validateProtectionDomainResponse.setProtectionDomainId(protectionDomainId);

            response.setResults(buildResponseResult(validateProtectionDomainResponse));

            for (Integer n = 0; n < protectionDomainResponse.getValidProtectionDomains().get(0).getWarningMessages().size(); n++)
            {
                response.addWarning(protectionDomainResponse.getValidProtectionDomains().get(0).getWarningMessages().get(n).getMessage());
            }
            response.setWorkFlowTaskStatus(Status.SUCCEEDED);

            return true;

        }
        catch (Exception e)
        {
            LOGGER.error("Exception occurred", e);
            response.setWorkFlowTaskStatus(Status.FAILED);
            response.addError(e.toString());
        }
        response.setWorkFlowTaskStatus(Status.FAILED);
        return false;
    }

    private Map<String, String> buildResponseResult(ValidateProtectionDomainResponse validateProtectionDomainResponse)
    {
        Map<String, String> result = new HashMap<>();

        if (validateProtectionDomainResponse == null)
        {
            return result;
        }

        if (validateProtectionDomainResponse.getProtectionDomainName() != null)
        {
            result.put("protectionDomainName", validateProtectionDomainResponse.getProtectionDomainName());
        }

        if (validateProtectionDomainResponse.getProtectionDomainId() != null)
        {
            result.put("protectionDomainId", validateProtectionDomainResponse.getProtectionDomainId());
        }

        return result;
    }

    private String getHostType(ScaleIOSDS scaleIOSDS, List<Host> hosts)
    {
        String type = null;
        for (Host host : hosts)
        {
            String[] split_scaleio_name = scaleIOSDS.getName().split("-");
            String scaleIoName = split_scaleio_name[0] +"-"+ split_scaleio_name[1];
            String hostName =host.getName();
            if (scaleIoName.equals(hostName))
            {
                type = host.getType();
            }
            else
            {
                LOGGER.info("ScaleIOSds name does not match with Host name");
            }
        }
        return type;
    }

    private String getNodeType(final String symphonyUuid)
    {
        NodeInventory nodeInventory = repository.getNodeInventory(symphonyUuid);
        try
        {
            if (nodeInventory != null && nodeInventory.getNodeInventory() != null)
            {
                JSONArray productNames = JsonPath.read(nodeInventory.getNodeInventory(), "$..data..['System Information']['Product Name']");
                String productName;
                if (CollectionUtils.isEmpty(productNames))
                {
                    throw new InvalidNameException("No product name found for node " + nodeInventory.getSymphonyUUID());
                }

                productName = (String) productNames.get(0);
                String NodeType = null;
                if (productName.contains("R6"))
                {
                    NodeType = "1U1N";
                }
                else if (productName.contains("R7"))
                {
                    NodeType = "2U1N";
                }
                return NodeType;
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Error while getting the node inventory data", e);
        }
        return null;
    }

}
