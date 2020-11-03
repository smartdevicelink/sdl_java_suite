package com.smartdevicelink.test.rpc;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.proxy.RPCStruct;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class RPCStructTests {

    @Test
    public void testFormatObject() {
        final String KEY = "LIST";
        RPCStruct struct = new RPCStruct();
        List<RPCStruct> structs = new ArrayList<>();
        struct.setValue(KEY, structs);
        assertNotNull(struct.getObject(RPCStruct.class, KEY));

        structs.add(new RPCStruct());
        struct.setValue(KEY, structs);
        assertNotNull(struct.getObject(RPCStruct.class, KEY));

        structs.clear();
        structs.add(null);
        struct.setValue(KEY, structs);
        assertNotNull(struct.getObject(RPCStruct.class, KEY));

        structs.clear();
        structs.add(null);
        structs.add(new RPCStruct());
        struct.setValue(KEY, structs);
        assertNotNull(struct.getObject(RPCStruct.class, KEY));

        structs.clear();
        structs.add(new RPCStruct());
        structs.add(null);
        structs.add(new RPCStruct());
        struct.setValue(KEY, structs);
        assertNotNull(struct.getObject(RPCStruct.class, KEY));
    }
}
