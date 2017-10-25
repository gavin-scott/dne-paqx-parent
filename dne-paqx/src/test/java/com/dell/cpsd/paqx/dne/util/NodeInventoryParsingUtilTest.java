package com.dell.cpsd.paqx.dne.util;

import com.dell.cpsd.service.engineering.standards.Device;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class NodeInventoryParsingUtilTest {

    private String jsonString;
    @Before
    public void setUp() throws IOException{
        //read json string from file
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/node_inventory.json"));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            jsonString = stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    @Test
    public void testParseNewDevices()  {
        List<Device> newDevices = NodeInventoryParsingUtil.parseNewDevices(jsonString);
        Assert.assertNotNull(newDevices);
        Assert.assertEquals(21,newDevices.size());
    }
}
