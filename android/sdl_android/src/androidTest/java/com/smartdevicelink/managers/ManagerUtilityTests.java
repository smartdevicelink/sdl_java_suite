package com.smartdevicelink.managers;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * This is a unit test class for the SmartDeviceLink library manager class :
 * {@link ManagerUtility}
 */
@RunWith(AndroidJUnit4.class)
public class ManagerUtilityTests {


    @Before
    public void setUp() throws Exception {

    }

    // TESTS

    @Test
    public void testGetAllImageFields() {

        List<ImageField> fields = ManagerUtility.WindowCapabilityUtility.getAllImageFields();
        assertNotNull(fields);
        int size = fields.size();
        assertEquals(ImageFieldName.values().length, size);

        ImageFieldName[] names = ImageFieldName.values();

        boolean found;
        for (ImageFieldName name : names) {
            found = false;
            for (ImageField field : fields) {
                if (field != null
                        && field.getName() != null
                        && field.getName().equals(name)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }

    }

    @Test
    public void testGetAllTextFields() {

        List<TextField> fields = ManagerUtility.WindowCapabilityUtility.getAllTextFields();
        assertNotNull(fields);
        int size = fields.size();
        assertEquals(TextFieldName.values().length, size);

        TextFieldName[] names = TextFieldName.values();

        boolean found;
        for (TextFieldName name : names) {
            found = false;
            for (TextField field : fields) {
                if (field != null
                        && field.getName() != null
                        && field.getName().equals(name)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }

    }

    @Test
    public void testHasTextFieldOfName() {
        WindowCapability capability = new WindowCapability();
        List<TextField> textFieldList = new ArrayList<>();
        textFieldList.add(new TextField(TextFieldName.mainField1, CharacterSet.UTF_8, 500, 8));
        capability.setTextFields(textFieldList);

        assertTrue(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.mainField1));
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.alertText3));

        textFieldList.add(new TextField(TextFieldName.alertText3, CharacterSet.UTF_8, 500, 8));
        capability.setTextFields(textFieldList);
        assertTrue(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.mainField1));
        assertTrue(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.alertText3));

        textFieldList.clear();
        textFieldList.add(null);
        capability.setTextFields(textFieldList);
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.mainField1));
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.alertText3));

        textFieldList.add(new TextField(TextFieldName.alertText3, CharacterSet.UTF_8, 500, 8));
        capability.setTextFields(textFieldList);
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.mainField1));
        assertTrue(ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(capability, TextFieldName.alertText3));

    }

    @Test
    public void testHasImageFieldOfName() {

        WindowCapability capability = new WindowCapability();
        List<FileType> allImageFileTypes = Arrays.asList(FileType.GRAPHIC_BMP, FileType.GRAPHIC_JPEG, FileType.GRAPHIC_PNG);

        List<ImageField> imageFieldList = new ArrayList<>();
        imageFieldList.add(new ImageField(ImageFieldName.graphic, allImageFileTypes));
        capability.setImageFields(imageFieldList);

        assertTrue(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.graphic));
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.alertIcon));

        imageFieldList.add(new ImageField(ImageFieldName.alertIcon, allImageFileTypes));
        capability.setImageFields(imageFieldList);
        assertTrue(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.graphic));
        assertTrue(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.alertIcon));
        ;

        imageFieldList.clear();
        imageFieldList.add(null);
        capability.setImageFields(imageFieldList);
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.graphic));
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.alertIcon));

        imageFieldList.add(new ImageField(ImageFieldName.alertIcon, allImageFileTypes));
        capability.setImageFields(imageFieldList);
        assertFalse(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.graphic));
        assertTrue(ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(capability, ImageFieldName.alertIcon));

    }


    @Test
    public void testGetMaxNumberOfMainFieldLines() {

        WindowCapability capability = new WindowCapability();
        capability.setTextFields(ManagerUtility.WindowCapabilityUtility.getAllTextFields());

        int maxNumerOfLines = ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(capability);

        assertEquals(4, maxNumerOfLines);

        //Single line
        List<TextField> singleLineList = new ArrayList<>();
        singleLineList.add(new TextField(TextFieldName.mainField1, CharacterSet.UTF_8, 500, 8));
        capability.setTextFields(singleLineList);
        maxNumerOfLines = ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(capability);
        assertEquals(1, maxNumerOfLines);

        singleLineList.add(new TextField(TextFieldName.mainField2, CharacterSet.UTF_8, 500, 8));
        capability.setTextFields(singleLineList);
        maxNumerOfLines = ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(capability);
        assertEquals(2, maxNumerOfLines);

        singleLineList.add(new TextField(TextFieldName.mainField3, CharacterSet.UTF_8, 500, 8));
        capability.setTextFields(singleLineList);
        maxNumerOfLines = ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(capability);
        assertEquals(3, maxNumerOfLines);

        singleLineList.add(new TextField(TextFieldName.mainField4, CharacterSet.UTF_8, 500, 8));
        capability.setTextFields(singleLineList);
        maxNumerOfLines = ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(capability);
        assertEquals(4, maxNumerOfLines);

        List<TextField> nullList = new ArrayList<>();
        nullList.add(null);
        assertNotNull(nullList);
        capability.setTextFields(nullList);
        assertNotNull(capability);
        assertNotNull(capability.getTextFields());

        maxNumerOfLines = ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(capability);
        assertEquals(0, maxNumerOfLines);

        nullList.add(new TextField(TextFieldName.mainField4, CharacterSet.UTF_8, 500, 8));
        assertNotNull(nullList);
        capability.setTextFields(nullList);
        assertNotNull(capability);
        assertNotNull(capability.getTextFields());
        maxNumerOfLines = ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(capability);
        assertEquals(4, maxNumerOfLines);

    }
}
