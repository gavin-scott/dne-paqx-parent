/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.paqx.dne.service.model;

import com.dell.cpsd.service.engineering.standards.DeviceAssignment;

import java.util.Map;

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
    private String scaleIoData1KernelAndSvmSubnetMask;
    private String scaleIoData2SvmIpAddress;
    private String scaleIoData2KernelAndSvmSubnetMask;
    private String scaleIoSvmManagementIpAddress;
    private String scaleIoSvmManagementGatewayAddress;
    private String scaleIoSvmManagementSubnetMask;
    private String clusterName;
    private String symphonyUuid;
    private String vMotionManagementIpAddress;
    private String vMotionManagementSubnetMask;
    private String protectionDomainName;
    private String protectionDomainId;

    Map<String, DeviceAssignment> deviceToDeviceStoragePool;

    public NodeExpansionRequest()
    {
    }

    public NodeExpansionRequest(String idracIpAddress, String idracGatewayIpAddress, String idracSubnetMask, String esxiManagementIpAddress,
            String esxiManagementGatewayIpAddress, String esxiManagementSubnetMask, String esxiManagementHostname,
            String scaleIoData1SvmIpAddress, String scaleIoData1KernelAndSvmSubnetMask, String scaleIoData2SvmIpAddress,
            String scaleIoData2KernelAndSvmSubnetMask, String scaleIoSvmManagementIpAddress, String scaleIoSvmManagementGatewayAddress,
            String scaleIoSvmManagementSubnetMask, String symphonyUuid, String clusterName, String vMotionManagementIpAddress,
            String vMotionManagementSubnetMask, Map<String, DeviceAssignment> deviceToDeviceStoragePool)
    {
        this.idracIpAddress = idracIpAddress;
        this.idracGatewayIpAddress = idracGatewayIpAddress;
        this.idracSubnetMask = idracSubnetMask;
        this.esxiManagementIpAddress = esxiManagementIpAddress;
        this.esxiManagementGatewayIpAddress = esxiManagementGatewayIpAddress;
        this.esxiManagementSubnetMask = esxiManagementSubnetMask;
        this.esxiManagementHostname = esxiManagementHostname;
        this.scaleIoData1SvmIpAddress = scaleIoData1SvmIpAddress;
        this.scaleIoSvmManagementGatewayAddress = scaleIoSvmManagementGatewayAddress;
        this.scaleIoData1KernelAndSvmSubnetMask = scaleIoData1KernelAndSvmSubnetMask;
        this.scaleIoData2SvmIpAddress = scaleIoData2SvmIpAddress;
        this.scaleIoData2KernelAndSvmSubnetMask = scaleIoData2KernelAndSvmSubnetMask;
        this.scaleIoSvmManagementIpAddress = scaleIoSvmManagementIpAddress;
        this.scaleIoSvmManagementSubnetMask = scaleIoSvmManagementSubnetMask;
        this.symphonyUuid = symphonyUuid;
        this.clusterName = clusterName;
        this.vMotionManagementIpAddress = vMotionManagementIpAddress;
        this.vMotionManagementSubnetMask = vMotionManagementSubnetMask;
        this.deviceToDeviceStoragePool = deviceToDeviceStoragePool;
    }

    public Map<String, DeviceAssignment> getDeviceToDeviceStoragePool()
    {
        return deviceToDeviceStoragePool;
    }

    public void setDeviceToDeviceStoragePool(final Map<String, DeviceAssignment> deviceToDeviceStoragePool)
    {
        this.deviceToDeviceStoragePool = deviceToDeviceStoragePool;
    }

    public String getIdracIpAddress()
    {
        return idracIpAddress;
    }

    public void setIdracIpAddress(String idracIpAddress)
    {
        this.idracIpAddress = idracIpAddress;
    }

    public String getIdracSubnetMask()
    {
        return idracSubnetMask;
    }

    public void setIdracSubnetMask(String idracSubnetMask)
    {
        this.idracSubnetMask = idracSubnetMask;
    }

    public String getIdracGatewayIpAddress()
    {
        return idracGatewayIpAddress;
    }

    public void setIdracGatewayIpAddress(String idracGatewayIpAddress)
    {
        this.idracGatewayIpAddress = idracGatewayIpAddress;
    }

    public String getEsxiManagementIpAddress()
    {
        return esxiManagementIpAddress;
    }

    public void setEsxiManagementIpAddress(String esxiManagementIpAddress)
    {
        this.esxiManagementIpAddress = esxiManagementIpAddress;
    }

    public String getEsxiManagementGatewayIpAddress()
    {
        return esxiManagementGatewayIpAddress;
    }

    public void setEsxiManagementGatewayIpAddress(String esxiManagementGatewayIpAddress)
    {
        this.esxiManagementGatewayIpAddress = esxiManagementGatewayIpAddress;
    }

    public String getEsxiManagementSubnetMask()
    {
        return esxiManagementSubnetMask;
    }

    public void setEsxiManagementSubnetMask(String esxiManagementSubnetMask)
    {
        this.esxiManagementSubnetMask = esxiManagementSubnetMask;
    }

    public String getEsxiManagementHostname()
    {
        return esxiManagementHostname;
    }

    public void setEsxiManagementHostname(String esxiManagementHostname)
    {
        this.esxiManagementHostname = esxiManagementHostname;
    }

    public String getScaleIoData1SvmIpAddress()
    {
        return scaleIoData1SvmIpAddress;
    }

    public void setScaleIoData1SvmIpAddress(final String scaleIoData1SvmIpAddress)
    {
        this.scaleIoData1SvmIpAddress = scaleIoData1SvmIpAddress;
    }

    public String getScaleIoData2SvmIpAddress()
    {
        return scaleIoData2SvmIpAddress;
    }

    public void setScaleIoData2SvmIpAddress(final String scaleIoData2SvmIpAddress)
    {
        this.scaleIoData2SvmIpAddress = scaleIoData2SvmIpAddress;
    }

    public String getScaleIoSvmManagementIpAddress()
    {
        return scaleIoSvmManagementIpAddress;
    }

    public void setScaleIoSvmManagementIpAddress(final String scaleIoSvmManagementIpAddress)
    {
        this.scaleIoSvmManagementIpAddress = scaleIoSvmManagementIpAddress;
    }

    public String getClusterName()
    {
        return clusterName;
    }

    public void setClusterName(String clusterName)
    {
        this.clusterName = clusterName;
    }

    public String getSymphonyUuid()
    {
        return symphonyUuid;
    }

    public void setSymphonyUuid(String symphonyUuid)
    {
        this.symphonyUuid = symphonyUuid;
    }

    public String getScaleIoData1KernelAndSvmSubnetMask()
    {
        return scaleIoData1KernelAndSvmSubnetMask;
    }

    public void setScaleIoData1KernelAndSvmSubnetMask(final String scaleIoData1KernelAndSvmSubnetMask)
    {
        this.scaleIoData1KernelAndSvmSubnetMask = scaleIoData1KernelAndSvmSubnetMask;
    }

    public String getScaleIoData2KernelAndSvmSubnetMask()
    {
        return scaleIoData2KernelAndSvmSubnetMask;
    }

    public void setScaleIoData2KernelAndSvmSubnetMask(final String scaleIoData2KernelAndSvmSubnetMask)
    {
        this.scaleIoData2KernelAndSvmSubnetMask = scaleIoData2KernelAndSvmSubnetMask;
    }

    public String getScaleIoSvmManagementSubnetMask()
    {
        return scaleIoSvmManagementSubnetMask;
    }

    public void setScaleIoSvmManagementSubnetMask(final String scaleIoSvmManagementSubnetMask)
    {
        this.scaleIoSvmManagementSubnetMask = scaleIoSvmManagementSubnetMask;
    }

    public String getvMotionManagementIpAddress()
    {
        return vMotionManagementIpAddress;
    }

    public String getScaleIoSvmManagementGatewayAddress()
    {
        return scaleIoSvmManagementGatewayAddress;
    }

    public void setScaleIoSvmManagementGatewayAddress(String scaleIoSvmManagementGatewayAddress)
    {
        this.scaleIoSvmManagementGatewayAddress = scaleIoSvmManagementGatewayAddress;
    }

    public void setvMotionManagementIpAddress(final String vMotionManagementIpAddress)
    {
        this.vMotionManagementIpAddress = vMotionManagementIpAddress;
    }

    public String getvMotionManagementSubnetMask()
    {
        return vMotionManagementSubnetMask;
    }

    public void setvMotionManagementSubnetMask(final String vMotionManagementSubnetMask)
    {
        this.vMotionManagementSubnetMask = vMotionManagementSubnetMask;
    }

    public String getProtectionDomainName()
    {
        return protectionDomainName;
    }

    public void setProtectionDomainName(String protectionDomainName)
    {
        this.protectionDomainName = protectionDomainName;
    }

    public String getProtectionDomainId()
    {
        return protectionDomainId;
    }

    public void setProtectionDomainId(final String protectionDomainId)
    {
        this.protectionDomainId = protectionDomainId;
    }

    @Override
    public String toString()
    {
        return "NodeExpansionRequest{" + "idracIpAddress='" + idracIpAddress + '\'' + ", idracGatewayIpAddress='" + idracGatewayIpAddress
                + '\'' + ", idracSubnetMask='" + idracSubnetMask + '\'' + ", esxiManagementIpAddress='" + esxiManagementIpAddress + '\''
                + ", esxiManagementGatewayIpAddress='" + esxiManagementGatewayIpAddress + '\'' + ", esxiManagementSubnetMask='"
                + esxiManagementSubnetMask + '\'' + ", esxiManagementHostname='" + esxiManagementHostname + '\''
                + ", scaleIoData1SvmIpAddress='" + scaleIoData1SvmIpAddress + '\'' + ", scaleIoData1KernelAndSvmSubnetMask='"
                + scaleIoData1KernelAndSvmSubnetMask + '\'' + ", scaleIoData2SvmIpAddress='" + scaleIoData2SvmIpAddress + '\''
                + ", scaleIoData2KernelAndSvmSubnetMask='" + scaleIoData2KernelAndSvmSubnetMask + '\'' + ", scaleIoSvmManagementIpAddress='"
                + scaleIoSvmManagementIpAddress + '\'' + ", scaleIoSvmManagementGatewayAddress='" + scaleIoSvmManagementGatewayAddress
                + '\'' + ", scaleIoSvmManagementSubnetMask='" + scaleIoSvmManagementSubnetMask + '\'' + ", clusterName='" + clusterName
                + '\'' + ", symphonyUuid='" + symphonyUuid + '\'' + ", vMotionManagementIpAddress='" + vMotionManagementIpAddress + '\''
                + ", vMotionManagementSubnetMask='" + vMotionManagementSubnetMask + '\'' + ", protectionDomainName='" + protectionDomainName
                + '\'' + ", protectionDomainId='" + protectionDomainId + '\'' + ", deviceToDeviceStoragePool='" + deviceToDeviceStoragePool
                == null ? null : deviceToDeviceStoragePool.toString() + '\'' + '}';
    }
}
