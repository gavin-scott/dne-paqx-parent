
/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.service.delegate;

import com.dell.cpsd.paqx.dne.domain.node.*;
import com.dell.cpsd.paqx.dne.repository.DataServiceRepository;
import com.dell.cpsd.paqx.dne.service.NodeService;
import com.dell.cpsd.paqx.dne.service.delegates.*;
import com.dell.cpsd.paqx.dne.service.delegates.utils.DelegateConstants;
import com.dell.cpsd.paqx.dne.service.model.ComponentEndpointIds;
import com.dell.cpsd.paqx.dne.service.model.NodeInfo;
import com.dell.cpsd.paqx.dne.service.model.NodeStatus;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.Before;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InventoryScaleIOTest {

    private InventoryScaleIO inventoryScaleIO;
    private NodeService nodeService;
    private DelegateExecution delegateExecution;
    private List<NodeInfo> discoveredNodes;
    private NodeInfo discoveredNode;
    private Object nodeInventoryResponse;
    private NodeInventory nodeInventory;
    private DataServiceRepository repository;
    private ComponentEndpointIds componentEndpointIds;

    @Before
    public void setUp() throws Exception
    {
        nodeService = mock(NodeService.class);
        delegateExecution = mock(DelegateExecution.class);
        nodeInventoryResponse = mock(Object.class);
        nodeInventory = mock(NodeInventory.class);
        repository = mock(DataServiceRepository.class);
        inventoryScaleIO = new InventoryScaleIO(nodeService, repository);
        discoveredNodes = new ArrayList<NodeInfo>();
        discoveredNode = mock(NodeInfo.class);
        componentEndpointIds = mock(ComponentEndpointIds.class);

    }

    @Ignore @Test
    public void scaleIoInventoryFailed() throws Exception {
        try {
            inventoryScaleIO = new InventoryScaleIO(nodeService, null);
            inventoryScaleIO.delegateExecute(delegateExecution);
        } catch (BpmnError error) {
            assertTrue(error.getErrorCode().equals(DelegateConstants.INVENTORY_SCALE_IO_FAILED));
        }
    }


    @Ignore @Test
    public void scaleIoInventoryNotSaved() throws Exception {
        try {
            when(repository.getComponentEndpointIds("SCALEIO")).thenReturn(componentEndpointIds);
            inventoryScaleIO.delegateExecute(delegateExecution);
        } catch (BpmnError error) {
            assertTrue(error.getErrorCode().equals(DelegateConstants.INVENTORY_SCALE_IO_FAILED));
        }
    }

    @Ignore @Test
    public void scaleIoInfoNotFound() throws Exception {
        try {
            componentEndpointIds = null;
            when(repository.getComponentEndpointIds("SCALEIO")).thenReturn(componentEndpointIds);
            inventoryScaleIO.delegateExecute(delegateExecution);
        } catch (BpmnError error) {
            assertTrue(error.getErrorCode().equals(DelegateConstants.SCALE_IO_INFORMATION_NOT_FOUND));
        }
    }

    @Ignore @Test
    public void scaleIoDiscoveryException() throws Exception {
        try {
            when(repository.getComponentEndpointIds("SCALEIO")).thenReturn(componentEndpointIds);
            given(delegateExecution.getProcessInstanceId()).willThrow(new NullPointerException());
            inventoryScaleIO.delegateExecute(delegateExecution);
        } catch (BpmnError error) {
            assertTrue(error.getErrorCode().equals(DelegateConstants.INVENTORY_SCALE_IO_FAILED));
        }
    }
}
