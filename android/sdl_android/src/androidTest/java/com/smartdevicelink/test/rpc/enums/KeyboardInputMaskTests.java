package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.KeyboardInputMask;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardInputMaskTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "ENABLE_INPUT_KEY_MASK";
        KeyboardInputMask enumEnableInputKeyMask = KeyboardInputMask.valueForString(example);
        example = "DISABLE_INPUT_KEY_MASK";
        KeyboardInputMask enumDisableInputKeyMask = KeyboardInputMask.valueForString(example);
        example = "USER_CHOICE_INPUT_KEY_MASK";
        KeyboardInputMask enumUserChoiceInputKeyMask = KeyboardInputMask.valueForString(example);

        assertNotNull("ENABLE_INPUT_KEY_MASK returned null", enumEnableInputKeyMask);
        assertNotNull("DISABLE_INPUT_KEY_MASK returned null", enumDisableInputKeyMask);
        assertNotNull("USER_CHOICE_INPUT_KEY_MASK returned null", enumUserChoiceInputKeyMask);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "ENAablE_INPUT_KEY_MASK";
        try {
            KeyboardInputMask temp = KeyboardInputMask.valueForString(example);
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
            KeyboardInputMask temp = KeyboardInputMask.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of MassageCushion.
     */
    public void testListEnum() {
        List<KeyboardInputMask> enumValueList = Arrays.asList(KeyboardInputMask.values());

        List<KeyboardInputMask> enumTestList = new ArrayList<KeyboardInputMask>();
        enumTestList.add(KeyboardInputMask.DISABLE_INPUT_KEY_MASK);
        enumTestList.add(KeyboardInputMask.ENABLE_INPUT_KEY_MASK);
        enumTestList.add(KeyboardInputMask.USER_CHOICE_INPUT_KEY_MASK);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
