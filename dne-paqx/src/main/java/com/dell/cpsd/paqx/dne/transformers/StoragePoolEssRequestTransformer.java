/**
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 */

package com.dell.cpsd.paqx.dne.transformers;

import com.dell.cpsd.paqx.dne.domain.scaleio.ScaleIODevice;
import com.dell.cpsd.paqx.dne.domain.scaleio.ScaleIOSDS;
import com.dell.cpsd.paqx.dne.domain.scaleio.ScaleIOStoragePool;
import com.dell.cpsd.paqx.dne.domain.vcenter.Host;
import com.dell.cpsd.paqx.dne.domain.vcenter.HostStorageDevice;
import com.dell.cpsd.service.engineering.standards.Device;
import com.dell.cpsd.service.engineering.standards.EssValidateStoragePoolRequestMessage;
import com.dell.cpsd.service.engineering.standards.StoragePool;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Transform message from ESS format to DNE format.
 * <p>
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

@Component
public class StoragePoolEssRequestTransformer
{

    /**
     * Transforms the scale io storage pools to request object
     *
     * @param scaleIOStoragePools
     * @param hostToStorageDeviceMap
     * @return
     */
    public EssValidateStoragePoolRequestMessage transform(List<ScaleIOStoragePool> scaleIOStoragePools,
            Map<String, Map<String, HostStorageDevice>> hostToStorageDeviceMap)
    {
        EssValidateStoragePoolRequestMessage requestMessage = new EssValidateStoragePoolRequestMessage();

        List<StoragePool> storagePools = scaleIOStoragePools.stream().filter(Objects::nonNull)
                .map(storagePool -> collectDevicesInPool(storagePool, hostToStorageDeviceMap)).filter(Objects::nonNull)
                .collect(Collectors.toList());

        requestMessage.setStoragePools(storagePools);
        return requestMessage;
    }

    /**
     * Collects all of the storage devices present in the storage pool. Determines the type of storage pool and device
     *
     * @param scaleIOStoragePool     {@code ScaleIOStoragePool} for which to collect the devices
     * @param hostToStorageDeviceMap {@code hostName : displayName : HostStorageDevice}
     * @return
     */
    public StoragePool collectDevicesInPool(ScaleIOStoragePool scaleIOStoragePool,
            Map<String, Map<String, HostStorageDevice>> hostToStorageDeviceMap)
    {
        StoragePool storagePool = new StoragePool();
        storagePool.setId(scaleIOStoragePool.getId());
        storagePool.setName(scaleIOStoragePool.getName());
        storagePool.setNumberOfDevices("" + scaleIOStoragePool.getDevices().size());

        // for empty storage pools, set the type to SSD
        List<Device> devices = new ArrayList<>();

        if (CollectionUtils.isEmpty(scaleIOStoragePool.getDevices()))
        {
            // for empty storage pools validate that it satisfies useRfcache = false, useRmcache = false, zeroPaddingEnabled = true;
            if (!scaleIOStoragePool.isUseRfcache() && !scaleIOStoragePool.isUseRmcache() && scaleIOStoragePool.isZeroPaddingEnabled()) {
                // set the type to SSD for empty pools
                storagePool.setType(StoragePool.Type.SSD);
            }
            else
            {
                return null;
            }
        }
        else
        {
            storagePool.setType(StoragePool.Type.HDD);

            for (ScaleIODevice scaleIODevice : scaleIOStoragePool.getDevices())
            {

                if (scaleIODevice == null)
                {
                    continue;
                }
                Device device = new Device();
                device.setName(scaleIODevice.getName());
                device.setType(Device.Type.HDD);

                // correlate the vCenter and scaleIO data
                Map<String, HostStorageDevice> displayNameToSsdMap = correlateScaleIOAndVcenterData(scaleIODevice.getSds(),
                        hostToStorageDeviceMap);

                if (displayNameToSsdMap != null)
                {
                    // for the matching hosts in vCenter, extract the information like serialNumber, disk type
                    HostStorageDevice hostStorageDevice = displayNameToSsdMap.get(device.getName());
                    if (hostStorageDevice != null)
                    {
                        device.setSerialNumber(hostStorageDevice.getSerialNumber());

                        device.setId(
                                hostStorageDevice.getCanonicalName() != null ? hostStorageDevice.getCanonicalName().split("\\.")[1] : null);

                        if (hostStorageDevice.isSsd())
                        {
                            device.setType(Device.Type.SSD);
                            // if type = SSD already set on the pool, don't set it again
                            if (StoragePool.Type.HDD.equals(storagePool.getType()))
                            {
                                storagePool.setType(StoragePool.Type.SSD);
                            }
                        }
                    }
                }

                devices.add(device);
            }
        }

        storagePool.setDevices(devices);

        return storagePool;
    }

    /**
     * Correlate scaleIOSDS data with vCenter hosts data. Returns a map of {@code displayName : HostStorageDevice}
     * where scaleIOSDS name contains vCenter host name from {@code hostToStorageDeviceMap}
     *
     * @param scaleIOSDS             {@code ScaleIOSDS} for which to match the name
     * @param hostToStorageDeviceMap {@code hostName : displayName : HostStorageDevice}
     * @return {@code displayName : HostStorageDevice}
     */
    public Map<String, HostStorageDevice> correlateScaleIOAndVcenterData(ScaleIOSDS scaleIOSDS,
            Map<String, Map<String, HostStorageDevice>> hostToStorageDeviceMap)
    {
        return hostToStorageDeviceMap.entrySet().stream().filter(e -> scaleIOSDS.getName().contains(e.getKey())).map(Map.Entry::getValue)
                .findFirst().orElse(null);
    }

    /**
     * Create a map of hosts from vCenter so that it is easier to iterate through.
     *
     * @param hosts List of hosts from vCenter
     * @return {@code hostName : displayName : HostStorageDevice}
     */
    public Map<String, Map<String, HostStorageDevice>> getHostToStorageDeviceMap(List<Host> hosts)
    {
        return hosts.stream().collect(Collectors.toMap(Host::getName, host -> host.getHostStorageDeviceList().stream()
                .collect(Collectors.toMap(HostStorageDevice::getDisplayName, hostStorageDevice -> hostStorageDevice))));
    }
}
