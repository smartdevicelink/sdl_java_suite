package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import java.util.List;

public class ManagerUtility {

    public static class WindowCapabilityUtility {

        /**
         * Check to see if WindowCapability has an ImageFieldName of a given name.
         * @param name ImageFieldName representing a name of a given Image field that would be stored in WindowCapability
         * @return true if name exist in WindowCapability else false
         */
        public static boolean hasImageFieldOfName(ImageFieldName name, WindowCapability windowCapability) {
            if (windowCapability.getImageFields() != null) {
                for (ImageField field : windowCapability.getImageFields()) {
                    if (field != null && field.getName() != null && field.getName().equals(name)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Check to see if WindowCapability has a textField of a given name.
         * @param name - TextFieldName representing a name of a given text field that would be stored in WindowCapability
         * @return true if name exist in WindowCapability else false
         */
        public static boolean hasTextFieldOfName(TextFieldName name, WindowCapability windowCapability) {
            if (windowCapability.getTextFields() != null) {
                for (TextField field : windowCapability.getTextFields()) {
                    if (field != null && field.getName() != null && field.getName().equals(name)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Method to get number of textFields allowed to be set according to WindowCapability
         * @return linesFound - Number of textFields found in WindowCapability
         */
        public static int getMaxNumberOfMainFieldLines(WindowCapability windowCapability) {
            int linesFound = 0;

            List<TextField> textFields = windowCapability.getTextFields();
            TextFieldName name;
            if (textFields != null && !textFields.isEmpty()) {
                for (TextField field : textFields) {
                    if (field.getName() != null) {
                        name = field.getName();
                        if (name == TextFieldName.mainField1 || name == TextFieldName.mainField2 || name == TextFieldName.mainField3 || name == TextFieldName.mainField4) {
                            linesFound += 1;
                        }
                    }
                }
            }
            return linesFound;
        }
    }

}
