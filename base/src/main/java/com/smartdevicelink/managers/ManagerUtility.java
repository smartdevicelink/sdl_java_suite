package com.smartdevicelink.managers;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <strong>ManagerUtility</strong> <br>
 * <p>
 * Static Methods to be used throughout the Manager classes <br>
 */
public class ManagerUtility {

    public static class WindowCapabilityUtility {

        /**
         * Check to see if WindowCapability has an ImageFieldName of a given name.
         *
         * @param windowCapability WindowCapability representing the capabilities of the desired window
         * @param name             ImageFieldName representing a name of a given Image field that would be stored in WindowCapability
         * @return true if name exist in WindowCapability else false
         */
        public static boolean hasImageFieldOfName(final WindowCapability windowCapability, final ImageFieldName name) {
            if (windowCapability == null || name == null) {
                return false;
            }
            if (windowCapability.getImageFields() != null) {
                List<ImageField> imageFields = windowCapability.getImageFields();
                if (imageFields != null && imageFields.size() > 0) {
                    for (ImageField field : imageFields) {
                        if (field != null && name.equals(field.getName())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Check to see if WindowCapability has a textField of a given name.
         *
         * @param windowCapability WindowCapability representing the capabilities of the desired window
         * @param name             TextFieldName representing a name of a given text field that would be stored in WindowCapability
         * @return true if name exist in WindowCapability else false
         */
        public static boolean hasTextFieldOfName(final WindowCapability windowCapability, final TextFieldName name) {
            if (windowCapability == null || name == null) {
                return false;
            }
            if (windowCapability.getTextFields() != null) {
                List<TextField> textFields = windowCapability.getTextFields();
                if (textFields != null && textFields.size() > 0) {
                    for (TextField field : textFields) {
                        if (field != null && name.equals(field.getName())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * Method to get number of textFields allowed to be set according to WindowCapability
         *
         * @param windowCapability WindowCapability representing the capabilities of the desired window
         * @return linesFound Number of textFields found in WindowCapability
         */
        public static int getMaxNumberOfMainFieldLines(final WindowCapability windowCapability) {
            int highestFound = 0;
            if (windowCapability != null && windowCapability.getTextFields() != null) {
                for (TextField field : windowCapability.getTextFields()) {
                    int fieldNumber = 0;
                    if (field != null && field.getName() != null) {
                        switch (field.getName()) {
                            case mainField1:
                                fieldNumber = 1;
                                break;
                            case mainField2:
                                fieldNumber = 2;
                                break;
                            case mainField3:
                                fieldNumber = 3;
                                break;
                            case mainField4:
                                fieldNumber = 4;
                                break;
                        }
                    }
                    if (fieldNumber > 0) {
                        highestFound = Math.max(highestFound, fieldNumber);
                        if (highestFound == 4) {
                            break;
                        }
                    }
                }
            }
            return highestFound;
        }

        /**
         * Method to get number of alert textFields allowed to be set according to WindowCapability
         *
         * @param windowCapability WindowCapability representing the capabilities of the desired window
         * @return linesFound Number of alert textFields found in WindowCapability
         */
        public static int getMaxNumberOfAlertFieldLines(final WindowCapability windowCapability) {
            int highestFound = 0;
            if (windowCapability != null && windowCapability.getTextFields() != null) {
                for (TextField field : windowCapability.getTextFields()) {
                    int fieldNumber = 0;
                    if (field != null && field.getName() != null) {
                        switch (field.getName()) {
                            case alertText1:
                                fieldNumber = 1;
                                break;
                            case alertText2:
                                fieldNumber = 2;
                                break;
                            case alertText3:
                                fieldNumber = 3;
                                break;
                        }
                    }
                    if (fieldNumber > 0) {
                        highestFound = Math.max(highestFound, fieldNumber);
                        if (highestFound == 3) {
                            break;
                        }
                    }
                }
            }
            return highestFound;
        }

        /**
         * Method to get a list of all available text fields
         *
         * @return list of all available text fields with CID1SET Character Set
         */
        public static List<TextField> getAllTextFields() {
            List<TextField> allTextFields = new ArrayList<>();
            for (TextFieldName name : TextFieldName.values()) {
                allTextFields.add(new TextField(name, CharacterSet.UTF_8, 500, 8));
            }
            return allTextFields;
        }

        /**
         * Method to get a list of all available Image fields
         *
         * @return list of all available Image fields with GRAPHIC_BMP, GRAPHIC_JPEG, GRAPHIC_PNG File Types
         */
        public static List<ImageField> getAllImageFields() {
            List<ImageField> allImageFields = new ArrayList<>();
            List<FileType> allImageFileTypes = Arrays.asList(FileType.GRAPHIC_BMP, FileType.GRAPHIC_JPEG, FileType.GRAPHIC_PNG);
            for (ImageFieldName name : ImageFieldName.values()) {
                allImageFields.add(new ImageField(name, allImageFileTypes));
            }
            return allImageFields;
        }
    }

    public static class LoginUtil {

        public static boolean hasUsbAccessoryPermission(Context context) {
            UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
            for (final UsbAccessory usbAccessory : manager.getAccessoryList()) {
                if (manager.hasPermission(usbAccessory)) {
                    Log.i("Julian", "hasUsbAccessoryPermission: returning true");
                    return true;
                }
            }
            Log.i("Julian", "hasUsbAccessoryPermission: returning false");
            return false;
        }

        /**
         * Boolean method that checks API level and check to see if we need to request BLUETOOTH_CONNECT permission
         * @return false if we need to request BLUETOOTH_CONNECT permission
         */
        public static boolean hasBTPermission(Context context) {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? checkPermission(context, Manifest.permission.BLUETOOTH_CONNECT) : true;
        }

        /**
         * Boolean method that checks API level and check to see if we need to request POST_NOTIFICATIONS permission
         * @return false if we need to request POST_NOTIFICATIONS permission
         */
        public static boolean hasPNPermission(Context context) {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? checkPermission(context, Manifest.permission.POST_NOTIFICATIONS) : true;
        }

        public static boolean checkPermission(Context applicationContext, String permission) {
            Log.i("Julian", "checkPermission: " + ContextCompat.checkSelfPermission(applicationContext, permission));
            return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(applicationContext, permission);
        }

        public static void requestPermission(Activity activity, String[] permissions, int REQUEST_CODE) {
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
        }

        public static @NonNull String[] permissionsNeeded(Context context) {
            ArrayList<String> result = new ArrayList<>();
            if (!hasBTPermission(context)) {
                result.add(Manifest.permission.BLUETOOTH_CONNECT);
            }
            if (!hasPNPermission(context)) {
                result.add(Manifest.permission.POST_NOTIFICATIONS);
            }
            return (result.toArray(new String[result.size()]));
        }

        public static boolean hasForegroundServiceTypePermission(Context context) {
            if (!ManagerUtility.LoginUtil.checkPermission(context, Manifest.permission.BLUETOOTH_CONNECT) && !ManagerUtility.LoginUtil.hasUsbAccessoryPermission(context)) {
                DebugTool.logInfo("julian", "Permission missing for ForegroundServiceType connected device.");
                return false;
            }
            return true;
        }

    }
}
