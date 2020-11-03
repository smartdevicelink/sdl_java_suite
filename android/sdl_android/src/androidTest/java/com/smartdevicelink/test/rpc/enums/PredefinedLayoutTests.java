package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.PredefinedLayout;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.PredefinedLayout}
 */
public class PredefinedLayoutTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "DEFAULT";
        PredefinedLayout defaultenum = PredefinedLayout.valueForString(example);
        example = "MEDIA";
        PredefinedLayout media = PredefinedLayout.valueForString(example);
        example = "NON-MEDIA";
        PredefinedLayout nonmedia = PredefinedLayout.valueForString(example);
        example = "ONSCREEN_PRESETS";
        PredefinedLayout onscreen_presets = PredefinedLayout.valueForString(example);
        example = "NAV_FULLSCREEN_MAP";
        PredefinedLayout nav_fullscreen_map = PredefinedLayout.valueForString(example);
        example = "NAV_LIST";
        PredefinedLayout nav_list = PredefinedLayout.valueForString(example);
        example = "NAV_KEYBOARD";
        PredefinedLayout nav_keyboard = PredefinedLayout.valueForString(example);
        example = "GRAPHIC_WITH_TEXT";
        PredefinedLayout graphic_with_text = PredefinedLayout.valueForString(example);
        example = "TEXT_WITH_GRAPHIC";
        PredefinedLayout text_with_graphic = PredefinedLayout.valueForString(example);
        example = "TILES_ONLY";
        PredefinedLayout tiles_only = PredefinedLayout.valueForString(example);
        example = "TEXTBUTTONS_ONLY";
        PredefinedLayout textbuttons_only = PredefinedLayout.valueForString(example);
        example = "GRAPHIC_WITH_TILES";
        PredefinedLayout graphic_with_tiles = PredefinedLayout.valueForString(example);
        example = "TILES_WITH_GRAPHIC";
        PredefinedLayout tiles_with_graphic = PredefinedLayout.valueForString(example);
        example = "GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS";
        PredefinedLayout graphic_with_text_and_softbuttons = PredefinedLayout.valueForString(example);
        example = "TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC";
        PredefinedLayout text_and_softbuttons_with_graphics = PredefinedLayout.valueForString(example);
        example = "GRAPHIC_WITH_TEXTBUTTONS";
        PredefinedLayout graphic_with_textbuttons = PredefinedLayout.valueForString(example);
        example = "TEXTBUTTONS_WITH_GRAPHIC";
        PredefinedLayout textbuttons_with_graphic = PredefinedLayout.valueForString(example);
        example = "LARGE_GRAPHIC_WITH_SOFTBUTTONS";
        PredefinedLayout large_graphic_with_softbuttons = PredefinedLayout.valueForString(example);
        example = "DOUBLE_GRAPHIC_WITH_SOFTBUTTONS";
        PredefinedLayout double_graphic_with_softbuttons = PredefinedLayout.valueForString(example);
        example = "LARGE_GRAPHIC_ONLY";
        PredefinedLayout large_graphic_only = PredefinedLayout.valueForString(example);
        example = "WEB_VIEW";
        PredefinedLayout web_view = PredefinedLayout.valueForString(example);

        assertNotNull("DEFAULT returned null", defaultenum);
        assertNotNull("MEDIA returned null", media);
        assertNotNull("NON-MEDIA returned null", nonmedia);
        assertNotNull("ONSCREEN_PRESETS returned null", onscreen_presets);
        assertNotNull("NAV_FULLSCREEN_MAP returned null", nav_fullscreen_map);
        assertNotNull("NAV_LIST returned null", nav_list);
        assertNotNull("NAV_KEYBOARD returned null", nav_keyboard);
        assertNotNull("GRAPHIC_WITH_TEXT returned null", graphic_with_text);
        assertNotNull("TEXT_WITH_GRAPHIC returned null", text_with_graphic);
        assertNotNull("TILES_ONLY returned null", tiles_only);
        assertNotNull("TEXTBUTTONS_ONLY returned null", textbuttons_only);
        assertNotNull("GRAPHIC_WITH_TILES returned null", graphic_with_tiles);
        assertNotNull("TILES_WITH_GRAPHIC returned null", tiles_with_graphic);
        assertNotNull("GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS returned null", graphic_with_text_and_softbuttons);
        assertNotNull("TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC returned null", text_and_softbuttons_with_graphics);
        assertNotNull("GRAPHIC_WITH_TEXTBUTTONS returned null", graphic_with_textbuttons);
        assertNotNull("TEXTBUTTONS_WITH_GRAPHIC returned null", textbuttons_with_graphic);
        assertNotNull("LARGE_GRAPHIC_WITH_SOFTBUTTONS returned null", large_graphic_with_softbuttons);
        assertNotNull("DOUBLE_GRAPHIC_WITH_SOFTBUTTONS returned null", double_graphic_with_softbuttons);
        assertNotNull("LARGE_GRAPHIC_ONLY returned null", large_graphic_only);
        assertNotNull("WEB_VIEW returned null", web_view);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "LARGE_GRApHIC_ONLY";
        try {
            PredefinedLayout temp = PredefinedLayout.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    public void testNullEnum() {
        String example = null;
        try {
            PredefinedLayout temp = PredefinedLayout.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of PredefinedLayout.
     */
    public void testListEnum() {
        List<PredefinedLayout> enumValueList = Arrays.asList(PredefinedLayout.values());

        List<PredefinedLayout> enumTestList = new ArrayList<>();

        enumTestList.add(PredefinedLayout.DEFAULT);
        enumTestList.add(PredefinedLayout.MEDIA);
        enumTestList.add(PredefinedLayout.NON_MEDIA);
        enumTestList.add(PredefinedLayout.ONSCREEN_PRESETS);
        enumTestList.add(PredefinedLayout.NAV_FULLSCREEN_MAP);
        enumTestList.add(PredefinedLayout.NAV_LIST);
        enumTestList.add(PredefinedLayout.NAV_KEYBOARD);
        enumTestList.add(PredefinedLayout.GRAPHIC_WITH_TEXT);
        enumTestList.add(PredefinedLayout.TEXT_WITH_GRAPHIC);
        enumTestList.add(PredefinedLayout.TILES_ONLY);
        enumTestList.add(PredefinedLayout.TEXTBUTTONS_ONLY);
        enumTestList.add(PredefinedLayout.GRAPHIC_WITH_TILES);
        enumTestList.add(PredefinedLayout.TILES_WITH_GRAPHIC);
        enumTestList.add(PredefinedLayout.GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS);
        enumTestList.add(PredefinedLayout.TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC);
        enumTestList.add(PredefinedLayout.GRAPHIC_WITH_TEXTBUTTONS);
        enumTestList.add(PredefinedLayout.TEXTBUTTONS_WITH_GRAPHIC);
        enumTestList.add(PredefinedLayout.LARGE_GRAPHIC_WITH_SOFTBUTTONS);
        enumTestList.add(PredefinedLayout.DOUBLE_GRAPHIC_WITH_SOFTBUTTONS);
        enumTestList.add(PredefinedLayout.LARGE_GRAPHIC_ONLY);
        enumTestList.add(PredefinedLayout.WEB_VIEW);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}