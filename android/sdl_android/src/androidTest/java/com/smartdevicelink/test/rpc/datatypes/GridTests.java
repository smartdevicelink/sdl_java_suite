package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class GridTests extends TestCase {

    private Grid msg;

    @Override
    public void setUp() {
        msg = new Grid();
        msg.setCol(TestValues.GENERAL_INT);
        msg.setRow(TestValues.GENERAL_INT);
        msg.setLevel(TestValues.GENERAL_INT);
        msg.setColSpan(TestValues.GENERAL_INT);
        msg.setRowSpan(TestValues.GENERAL_INT);
        msg.setLevelSpan(TestValues.GENERAL_INT);
    }

    public void testRpcValues() {
        int col = msg.getCol();
        int row = msg.getRow();
        int level = msg.getLevel();
        int colSpan = msg.getColSpan();
        int rowSpan = msg.getRowSpan();
        int levelSpan = msg.getLevelSpan();

        //valid tests
        assertEquals(TestValues.MATCH, col, TestValues.GENERAL_INT);
        assertEquals(TestValues.MATCH, row, TestValues.GENERAL_INT);
        assertEquals(TestValues.MATCH, level, TestValues.GENERAL_INT);
        assertEquals(TestValues.MATCH, colSpan, TestValues.GENERAL_INT);
        assertEquals(TestValues.MATCH, rowSpan, TestValues.GENERAL_INT);
        assertEquals(TestValues.MATCH, levelSpan, TestValues.GENERAL_INT);

        //null tests
        Grid msg = new Grid();
        assertNull(TestValues.NULL, msg.getCol());
        assertNull(TestValues.NULL, msg.getRow());
        assertNull(TestValues.NULL, msg.getLevel());
        assertNull(TestValues.NULL, msg.getColSpan());
        assertNull(TestValues.NULL, msg.getRowSpan());
        assertNull(TestValues.NULL, msg.getLevelSpan());

        //test required constructor
        Grid msg2 = new Grid(TestValues.GENERAL_INT, TestValues.GENERAL_INT);
        int row2 = msg2.getRow();
        int col2 = msg2.getCol();
        assertEquals(TestValues.MATCH, col2, TestValues.GENERAL_INT);
        assertEquals(TestValues.MATCH, row2, TestValues.GENERAL_INT);
    }

    public void testJson() {
        JSONObject original = new JSONObject();
        try {
            original.put(Grid.KEY_COL, TestValues.GENERAL_INT);
            original.put(Grid.KEY_ROW, TestValues.GENERAL_INT);
            original.put(Grid.KEY_LEVEL, TestValues.GENERAL_INT);
            original.put(Grid.KEY_COL_SPAN, TestValues.GENERAL_INT);
            original.put(Grid.KEY_ROW_SPAN, TestValues.GENERAL_INT);
            original.put(Grid.KEY_LEVEL_SPAN, TestValues.GENERAL_INT);

            JSONObject serialized = msg.serializeJSON();
            assertEquals(serialized.length(), original.length());

            Iterator<String> iter = original.keys();
            String key = "";
            Grid grid1, grid2;
            while (iter.hasNext()) {
                key = iter.next();
                grid1 = new Grid(JsonRPCMarshaller.deserializeJSONObject(original));
                grid2 = new Grid(JsonRPCMarshaller.deserializeJSONObject(serialized));
                if (key.equals(Grid.KEY_COL)) {
                    assertEquals(TestValues.MATCH, grid1.getCol(), grid2.getCol());
                } else if (key.equals(Grid.KEY_ROW)) {
                    assertEquals(TestValues.MATCH, grid1.getRow(), grid2.getRow());
                } else if (key.equals(Grid.KEY_LEVEL)) {
                    assertEquals(TestValues.MATCH, grid1.getLevel(), grid2.getLevel());
                } else if (key.equals(Grid.KEY_COL_SPAN)) {
                    assertEquals(TestValues.MATCH, grid1.getColSpan(), grid2.getColSpan());
                } else if (key.equals(Grid.KEY_ROW_SPAN)) {
                    assertEquals(TestValues.MATCH, grid1.getRowSpan(), grid2.getRowSpan());
                } else if (key.equals(Grid.KEY_LEVEL_SPAN)) {
                    assertEquals(TestValues.MATCH, grid1.getLevelSpan(), grid2.getLevelSpan());
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
