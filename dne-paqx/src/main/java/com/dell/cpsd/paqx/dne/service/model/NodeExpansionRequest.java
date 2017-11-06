/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.paqx.dne.service.model;

import com.dell.cpsd.service.engineering.standards.DeviceAssignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeExpansionRequest
{
    private String idracIpAddress;
    private String idracGatewayIpAddress;
    private String idracSubnetMask;

    private String esxiManagementIpAddress;
    private String esxiManagementGatewayIpAddress;
    private String esxiManagementSubnetMask;
    private String esxiManagementHostname;

    private String scaleIoData1SvmIpAddress;
    private String scaleIoData1SvmSubnetMask;
    private String scaleIoData2SvmIpAddress;
    private String scaleIoData2SvmSubnetMask;
    private String scaleIoData1EsxIpAddress;
    private String scaleIoData1EsxSubnetMask;
    private String scaleIoData2EsxIpAddress;
    private String scaleIoData2EsxSubnetMask;
    private String scaleIoSvmManagementIpAddress;
    private String scaleIoSvmManagementGatewayAddress;
    private String scaleIoSvmManagementSubnetMask;
    private String clusterName;
    private String symphonyUuid;
    private String vMotionManagementIpAddress;
    private String vMotionManagementSubnetMask;
    private String protectionDomainName;
    private String protectionDomainId;

    private Map<String, DeviceAssignment> deviceToDeviceStoragePool;
}