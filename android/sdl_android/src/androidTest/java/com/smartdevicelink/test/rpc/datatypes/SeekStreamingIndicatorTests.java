package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SeekStreamingIndicator;
import com.smartdevicelink.proxy.rpc.enums.SeekIndicatorType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SeekStreamingIndicatorTests extends TestCase {

    private SeekStreamingIndicator msg;

    @Override
    protected void setUp() throws Exception {
        msg = new SeekStreamingIndicator();
        assertNotNull(TestValues.NOT_NULL, msg);

        msg.setType(SeekIndicatorType.TRACK);
        msg.setSeekTime(1);
    }

    public void testRpcValues() {
        SeekIndicatorType indicator = msg.getType();
        int seekTime = msg.getSeekTime();

        assertEquals(TestValues.MATCH, SeekIndicatorType.TRACK, indicator);
        assertEquals(TestValues.MATCH, 1, seekTime);

        SeekStreamingIndicator msg = new SeekStreamingIndicator();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getType());
        assertNull(TestValues.NULL, msg.getSeekTime());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(SeekStreamingIndicator.KEY_TYPE, SeekIndicatorType.TRACK);
            reference.put(SeekStreamingIndicator.KEY_SEEK_TIME, 1);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}