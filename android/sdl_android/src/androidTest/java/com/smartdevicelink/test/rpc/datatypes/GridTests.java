package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.Grid;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class GridTests extends TestCase {

	private Grid msg;

	@Override
	public void setUp() {
		msg = new Grid();
		msg.setCol(Test.GENERAL_INT);
		msg.setRow(Test.GENERAL_INT);
		msg.setLevel(Test.GENERAL_INT);
		msg.setColSpan(Test.GENERAL_INT);
		msg.setRowSpan(Test.GENERAL_INT);
		msg.setLevelSpan(Test.GENERAL_INT);
	}

	public void testRpcValues() {
		int col = msg.getCol();
		int row = msg.getRow();
		int level = msg.getLevel();
		int colSpan = msg.getColSpan();
		int rowSpan = msg.getRowSpan();
		int levelSpan = msg.getLevelSpan();

		//valid tests
		assertEquals(Test.MATCH, col, Test.GENERAL_INT);
		assertEquals(Test.MATCH, row, Test.GENERAL_INT);
		assertEquals(Test.MATCH, level, Test.GENERAL_INT);
		assertEquals(Test.MATCH, colSpan, Test.GENERAL_INT);
		assertEquals(Test.MATCH, rowSpan, Test.GENERAL_INT);
		assertEquals(Test.MATCH, levelSpan, Test.GENERAL_INT);

		//null tests
		Grid msg = new Grid();
		assertNull(Test.NULL, msg.getCol());
		assertNull(Test.NULL, msg.getRow());
		assertNull(Test.NULL, msg.getLevel());
		assertNull(Test.NULL, msg.getColSpan());
		assertNull(Test.NULL, msg.getRowSpan());
		assertNull(Test.NULL, msg.getLevelSpan());

		//test required constructor
		Grid msg2 = new Grid(Test.GENERAL_INT, Test.GENERAL_INT);
		int row2 = msg2.getRow();
		int col2 = msg2.getCol();
		assertEquals(Test.MATCH, col2, Test.GENERAL_INT);
		assertEquals(Test.MATCH, row2, Test.GENERAL_INT);
	}

	public void testJson() {
		JSONObject original = new JSONObject();
		try {
			original.put(Grid.KEY_COL, Test.GENERAL_INT);
			original.put(Grid.KEY_ROW, Test.GENERAL_INT);
			original.put(Grid.KEY_LEVEL, Test.GENERAL_INT);
			original.put(Grid.KEY_COL_SPAN, Test.GENERAL_INT);
			original.put(Grid.KEY_ROW_SPAN, Test.GENERAL_INT);
			original.put(Grid.KEY_LEVEL_SPAN, Test.GENERAL_INT);

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
					assertEquals(Test.MATCH, grid1.getCol(), grid2.getCol());
				} else if (key.equals(Grid.KEY_ROW)) {
					assertEquals(Test.MATCH, grid1.getRow(), grid2.getRow());
				} else if (key.equals(Grid.KEY_LEVEL)) {
					assertEquals(Test.MATCH, grid1.getLevel(), grid2.getLevel());
				} else if (key.equals(Grid.KEY_COL_SPAN)) {
					assertEquals(Test.MATCH, grid1.getColSpan(), grid2.getColSpan());
				} else if (key.equals(Grid.KEY_ROW_SPAN)) {
					assertEquals(Test.MATCH, grid1.getRowSpan(), grid2.getRowSpan());
				} else if (key.equals(Grid.KEY_LEVEL_SPAN)) {
					assertEquals(Test.MATCH, grid1.getLevelSpan(), grid2.getLevelSpan());
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
