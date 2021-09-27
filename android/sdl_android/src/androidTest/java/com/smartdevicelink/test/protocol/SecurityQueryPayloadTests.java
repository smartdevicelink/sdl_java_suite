package com.smartdevicelink.test.protocol;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.protocol.SecurityQueryPayload;
import com.smartdevicelink.protocol.enums.SecurityQueryID;
import com.smartdevicelink.protocol.enums.SecurityQueryType;
import com.smartdevicelink.util.BitConverter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class SecurityQueryPayloadTests {

    public static SecurityQueryPayload createDummyBqh() {
        SecurityQueryPayload bqh = new SecurityQueryPayload();
        bqh.setCorrelationID(123);
        bqh.setQueryID(SecurityQueryID.SEND_HANDSHAKE_DATA);
        bqh.setQueryType(SecurityQueryType.REQUEST);
        bqh.setBulkData(null);
        bqh.setJsonSize(0);
        return bqh;
    }

    public SecurityQueryPayload safeParse(byte[] array) {
        try {
            return SecurityQueryPayload.parseBinaryQueryHeader(array);
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void testCorrectParsing() {
        byte[] array = new byte[12];
        array[0] = 0;
        array[1] = 0;
        array[2] = 0;
        array[3] = 2;
        array[4] = 0;
        array[5] = 0;
        array[6] = 0;
        array[7] = 3;
        array[8] = 0;
        array[9] = 0;
        array[10] = 0;
        array[11] = 0;

        SecurityQueryPayload parsedBqh = SecurityQueryPayload.parseBinaryQueryHeader(array);
        assertEquals(parsedBqh.getQueryType(), SecurityQueryType.REQUEST);
        assertEquals(parsedBqh.getQueryID(), SecurityQueryID.SEND_INTERNAL_ERROR);
        assertEquals(parsedBqh.getCorrelationID(), 3);
        assertEquals(parsedBqh.getJsonSize(), 0);
    }

    @Test
    public void testCorrectHeaderAssembly() {
        SecurityQueryPayload dummyBqh = new SecurityQueryPayload();
        dummyBqh.setQueryType(SecurityQueryType.REQUEST);
        dummyBqh.setQueryID(SecurityQueryID.SEND_HANDSHAKE_DATA);
        dummyBqh.setCorrelationID(3);
        dummyBqh.setJsonSize(0);

        byte[] assembledHeader = dummyBqh.assembleHeaderBytes();
        assertEquals(dummyBqh.getQueryType(), SecurityQueryType.valueOf(assembledHeader[0]));
        byte[] queryIDFromHeader = new byte[3];
        System.arraycopy(assembledHeader, 1, queryIDFromHeader, 0, 3);
        assertEquals(dummyBqh.getQueryID(), SecurityQueryID.valueOf(queryIDFromHeader));
        assertEquals(dummyBqh.getCorrelationID(), BitConverter.intFromByteArray(assembledHeader, 4));
        assertEquals(dummyBqh.getJsonSize(), BitConverter.intFromByteArray(assembledHeader, 8));
    }

    @Test
    public void testAssemblyAndParse() {
        SecurityQueryPayload bqh = createDummyBqh();

        byte[] bqhBytes = bqh.assembleHeaderBytes();
        assertNotNull(bqhBytes);

        SecurityQueryPayload parsedBqh = SecurityQueryPayload.parseBinaryQueryHeader(bqhBytes);
        assertNotNull(parsedBqh);

        assertEquals(bqh.getCorrelationID(), parsedBqh.getCorrelationID());
        assertEquals(bqh.getQueryID(), parsedBqh.getQueryID());
        assertEquals(bqh.getQueryType(), parsedBqh.getQueryType());
        assertEquals(bqh.getBulkData(), parsedBqh.getBulkData());
        assertEquals(bqh.getJsonData(), parsedBqh.getJsonData());
        assertEquals(bqh.getJsonSize(), parsedBqh.getJsonSize());
    }

    @Test
    public void testCorruptHeader() {
        SecurityQueryPayload bqh = createDummyBqh();

        byte[] bqhBytes = bqh.assembleHeaderBytes();

        assertNotNull(safeParse(bqhBytes));

        int size = bqhBytes.length;
        for (int i = 0; i < size; i++) {
            bqhBytes[i] = (byte) 0x99;
        }

        assertNull(safeParse(bqhBytes));
        SecurityQueryPayload head = SecurityQueryPayload.parseBinaryQueryHeader(bqhBytes);
        assertNull(head);
    }

    @Test
    public void testJsonSetException() {
        try {
            SecurityQueryPayload bqh = createDummyBqh();
            bqh.setJsonData(null);
            fail("Setting JSON data to null should have thrown an exception");
        } catch (Exception e) {
            //Pass
        }
    }
}
