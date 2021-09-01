package com.smartdevicelink.test;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.protocol.enums.SecurityQueryErrorCode;
import com.smartdevicelink.protocol.enums.SecurityQueryID;
import com.smartdevicelink.protocol.enums.SecurityQueryType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.*;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;
import com.smartdevicelink.proxy.rpc.enums.DefrostZone;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.TransmissionType;
import com.smartdevicelink.proxy.rpc.enums.VentilationMode;
import com.smartdevicelink.util.CompareUtils;

import java.util.Iterator;
import java.util.List;

public class Validator {

    // TODO: This class could be (mostly) eliminated if all RPC classes implement their own .equals() and .hashCode() methods.
    // Some special methods do exist for pseudo-enums (FrameType, SessionType, FrameData, FrameDataControlFrame)

    private Validator() {
    }

    public static boolean validateMenuParams(MenuParams menuParams1, MenuParams menuParams2) {
        if (menuParams1 == null) {
            return (menuParams2 == null);
        }
        if (menuParams2 == null) {
            return (menuParams1 == null);
        }

        return (menuParams1.getMenuName().equals(menuParams2.getMenuName())
                && menuParams1.getParentID() == menuParams2.getParentID() && menuParams1.getPosition() == menuParams2
                .getPosition());
    }

    public static boolean validateVehicleDataResult(VehicleDataResult result1, VehicleDataResult result2) {
        if (result1 == null) {
            return (result2 == null);
        }
        if (result2 == null) {
            return (result1 == null);
        }

        if (result1.getDataType() != null && result2.getDataType() != null) {
            return (result1.getDataType().equals(result2.getDataType())
                    && result1.getResultCode().equals(result2.getResultCode()));
        } else {
            return (result1.getOEMCustomVehicleDataType().equals(result2.getOEMCustomVehicleDataType())
                    && result1.getResultCode().equals(result2.getResultCode()));
        }
    }

    public static boolean validateBulkData(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return (array2 == null);
        }
        if (array2 == null) {
            return (array1 == null);
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateFrameTypeArray(FrameType[] array1, FrameType[] array2) {

        if (array1 == null) {
            return (array2 == null);
        }

        if (array2 == null) {
            return (array1 == null);
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateSessionTypeArray(SessionType[] array1, SessionType[] array2) {

        if (array1 == null) {
            return (array2 == null);
        }

        if (array2 == null) {
            return (array1 == null);
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateQueryTypeArray(SecurityQueryType[] array1, SecurityQueryType[] array2) {

        if (array1 == null) {
            return (array2 == null);
        }

        if (array2 == null) {
            return (array1 == null);
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateQueryIDArray(SecurityQueryID[] array1, SecurityQueryID[] array2) {

        if (array1 == null) {
            return (array2 == null);
        }

        if (array2 == null) {
            return (array1 == null);
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateQueryErrorCodeArray(SecurityQueryErrorCode[] array1, SecurityQueryErrorCode[] array2) {

        if (array1 == null) {
            return (array2 == null);
        }

        if (array2 == null) {
            return (array1 == null);
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateFrameDataControlFrameTypeArray(FrameDataControlFrameType[] array1, FrameDataControlFrameType[] array2) {

        if (array1 == null) {
            return (array2 == null);
        }

        if (array2 == null) {
            return (array1 == null);
        }

        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }

        return true;
    }


    public static boolean validateImage(Image image1, Image image2) {
        if (image1 == null) {
            return (image2 == null);
        }
        if (image2 == null) {
            return (image1 == null);
        }

        if (!(image1.getValue().equals(image2.getValue()))) {
            log("validateImage",
                    "image1 name \"" + image1.getValue() + "\" didn't match image2 name \"" + image2.getValue() + "\".");
            return false;
        }

        if (image1.getImageType() != image2.getImageType()) {
            log("validateImage",
                    "image1 type \"" + image1.getImageType() + "\" didn't match image2 type \"" + image2.getImageType()
                            + "\".");
            return false;
        }

        return true;
    }

    public static boolean validateSdlFile(SdlFile sdlFile1, SdlFile sdlFile2) {
        if (sdlFile1 == null) {
            return (sdlFile2 == null);
        }
        if (sdlFile2 == null) {
            return (sdlFile1 == null);
        }

        if (!(sdlFile1.getName().equals(sdlFile2.getName()))) {
            log("validateSdlFile",
                    "sdlFile1 name \"" + sdlFile1.getName() + "\" didn't match sdlFile2 name \"" + sdlFile2.getName() + "\".");
            return false;
        }

        if (sdlFile1.getResourceId() != sdlFile2.getResourceId()) {
            log("validateSdlFile",
                    "sdlFile1 resourceId \"" + sdlFile1.getName() + "\" didn't match sdlFile2 resourceId \"" + sdlFile2.getName() + "\".");
            return false;
        }

        if ((sdlFile1.getType() != null && sdlFile2.getType() != null) && !(sdlFile1.getType().equals(sdlFile2.getType()))) {
            log("validateSdlFile",
                    "sdlFile1 type \"" + sdlFile1.getType() + "\" didn't match sdlFile2 type \"" + sdlFile2.getType() + "\".");
            return false;
        }

        if ((sdlFile1.getUri() != sdlFile2.getUri()) && !(sdlFile1.getUri().equals(sdlFile2.getUri()))) {
            log("validateSdlFile",
                    "sdlFile1 uri \"" + sdlFile1.getUri() + "\" didn't match sdlFile2 uri \"" + sdlFile2.getUri() + "\".");
            return false;
        }


        return true;
    }

    public static boolean validateStringList(List<String> vrCommands1, List<String> vrCommands2) {
        if (vrCommands1 == null) {
            return (vrCommands2 == null);
        }
        if (vrCommands2 == null) {
            return (vrCommands1 == null);
        }

        for (int i = 0; i < vrCommands1.size(); i++) {
            if (!vrCommands1.get(i).equals(vrCommands2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateIntegerList(List<Integer> intList1, List<Integer> intList2) {
        if (intList1 == null) {
            return (intList2 == null);
        }
        if (intList2 == null) {
            return (intList1 == null);
        }

        for (int i = 0; i < intList1.size(); i++) {
            if (!intList1.get(i).equals(intList2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateLongList(List<Long> intList1, List<Long> intList2) {
        if (intList1 == null) {
            return (intList2 == null);
        }
        if (intList2 == null) {
            return (intList1 == null);
        }

        for (int i = 0; i < intList1.size(); i++) {
            if (!intList1.get(i).equals(intList2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateSoftButton(SoftButton button1, SoftButton button2) {
        return validateImage(button1.getImage(), button2.getImage())
                && validateText(button1.getText(), button2.getText())
                && button1.getIsHighlighted() == button2.getIsHighlighted()
                && ((button1.getSoftButtonID() == null && button2.getSoftButtonID() == null) || button1.getSoftButtonID().equals(button2.getSoftButtonID()))
                && button1.getSystemAction() == button2.getSystemAction()
                && button1.getType() == button2.getType();
    }

    public static boolean validateSoftButtons(List<SoftButton> list1, List<SoftButton> list2) {
        if (list1 == null) {
            return (list2 == null);
        }
        if (list2 == null) {
            return (list1 == null);
        }

        Iterator<SoftButton> iterator1 = list1.iterator();
        Iterator<SoftButton> iterator2 = list2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            SoftButton button1 = iterator1.next();
            SoftButton button2 = iterator2.next();

            if (!validateSoftButton(button1, button2)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateCoordinate(Coordinate c1, Coordinate c2) {
        if (c1 == null) {
            return (c2 == null);
        }
        if (c2 == null) {
            return (c1 == null);
        }

        if (c1.getLatitudeDegrees() != c2.getLatitudeDegrees()) {
            return false;
        }

        if (c1.getLongitudeDegrees() != c2.getLongitudeDegrees()) {
            return false;
        }

        return true;
    }

    public static boolean validateRectangle(Rectangle c1, Rectangle c2) {
        if (c1 == null) {
            return (c2 == null);
        }
        if (c2 == null) {
            return (c1 == null);
        }

        if (c1.getX() != c2.getX()) {
            return false;
        }

        if (c1.getY() != c2.getY()) {
            return false;
        }

        if (c1.getWidth() != c2.getWidth()) {
            return false;
        }

        if (c1.getHeight() != c2.getHeight()) {
            return false;
        }

        return true;
    }

    public static boolean validateOasisAddress(OasisAddress a1, OasisAddress a2) {
        if (a1 == null) {
            return (a2 == null);
        }
        if (a2 == null) {
            return (a1 == null);
        }

        if (!a1.getAdministrativeArea().equals(a2.getAdministrativeArea())) {
            return false;
        }

        if (!a1.getCountryCode().equals(a2.getCountryCode())) {
            return false;
        }

        if (!a1.getCountryName().equals(a2.getCountryName())) {
            return false;
        }

        if (!a1.getLocality().equals(a2.getLocality())) {
            return false;
        }

        if (!a1.getSubLocality().equals(a2.getSubLocality())) {
            return false;
        }

        if (!a1.getSubAdministrativeArea().equals(a2.getSubAdministrativeArea())) {
            return false;
        }

        if (!a1.getPostalCode().equals(a2.getPostalCode())) {
            return false;
        }

        if (!a1.getThoroughfare().equals(a2.getThoroughfare())) {
            return false;
        }

        if (!a1.getSubThoroughfare().equals(a2.getSubThoroughfare())) {
            return false;
        }

        return true;
    }

    public static boolean validateTtsChunks(List<TTSChunk> list1, List<TTSChunk> list2) {
        if (list1 == null) {
            return (list2 == null);
        }
        if (list2 == null) {
            return (list1 == null);
        }

        Iterator<TTSChunk> iterator1 = list1.iterator();
        Iterator<TTSChunk> iterator2 = list2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            TTSChunk chunk1 = iterator1.next();
            TTSChunk chunk2 = iterator2.next();

            if (!validateText(chunk1.getText(), chunk2.getText()) || chunk1.getType() != chunk2.getType()) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateAppServiceTypeList(List<AppServiceType> list1, List<AppServiceType> list2) {
        if (list1 == null) {
            return (list2 == null);
        }
        if (list2 == null) {
            return (list1 == null);
        }

        Iterator<AppServiceType> iterator1 = list1.iterator();
        Iterator<AppServiceType> iterator2 = list2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            AppServiceType chunk1 = iterator1.next();
            AppServiceType chunk2 = iterator2.next();

            if (chunk1 != chunk2) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateAppServiceCapabilities(AppServicesCapabilities params1, AppServicesCapabilities params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!validateAppServiceCapabilityList(params1.getAppServices(), params2.getAppServices())) {
            return false;
        }

        return true;
    }

    public static boolean validateTemplateConfiguration(TemplateConfiguration params1, TemplateConfiguration params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!params1.getTemplate().equals(params2.getTemplate())) {
            return false;
        }

        if (!validateTemplateColorScheme(params1.getDayColorScheme(), params2.getDayColorScheme())) {
            return false;
        }

        if (!validateTemplateColorScheme(params1.getNightColorScheme(), params2.getNightColorScheme())) {
            return false;
        }

        return true;
    }

    public static boolean validateAppServiceCapabilityList(List<AppServiceCapability> list1, List<AppServiceCapability> list2) {
        if (list1 == null) {
            return (list2 == null);
        }
        if (list2 == null) {
            return (list1 == null);
        }

        Iterator<AppServiceCapability> iterator1 = list1.iterator();
        Iterator<AppServiceCapability> iterator2 = list2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            AppServiceCapability chunk1 = iterator1.next();
            AppServiceCapability chunk2 = iterator2.next();

            if (!validateAppServiceRecord(chunk1.getUpdatedAppServiceRecord(), chunk2.getUpdatedAppServiceRecord())) {
                return false;
            }

            if (!chunk1.getUpdateReason().equals(chunk2.getUpdateReason())) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateMediaServiceManifest(MediaServiceManifest params1, MediaServiceManifest params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        return true;
    }

    public static boolean validateNavigationServiceManifest(NavigationServiceManifest params1, NavigationServiceManifest params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!params1.getAcceptsWayPoints().equals(params2.getAcceptsWayPoints())) {
            return false;
        }

        return true;
    }

    public static boolean validateAppServiceRecord(AppServiceRecord params1, AppServiceRecord params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!params1.getServiceActive().equals(params2.getServiceActive())) {
            return false;
        }

        if (!params1.getServicePublished().equals(params2.getServicePublished())) {
            return false;
        }

        if (!params1.getServiceID().equals(params2.getServiceID())) {
            return false;
        }

        if (!validateAppServiceManifest(params1.getServiceManifest(), params2.getServiceManifest())) {
            return false;
        }

        return true;
    }

    public static boolean validateAppServiceManifest(AppServiceManifest params1, AppServiceManifest params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!params1.getAllowAppConsumers().equals(params2.getAllowAppConsumers())) {
            return false;
        }

        if (!validateImage(params1.getServiceIcon(), params2.getServiceIcon())) {
            return false;
        }

        if (!params1.getServiceName().equals(params2.getServiceName())) {
            return false;
        }

        if (!params1.getServiceType().equals(params2.getServiceType())) {
            return false;
        }

        if (!validateSdlMsgVersion(params1.getRpcSpecVersion(), params2.getRpcSpecVersion())) {
            return false;
        }

        if (!validateMediaServiceManifest(params1.getMediaServiceManifest(), params2.getMediaServiceManifest())) {
            return false;
        }

        if (!validateWeatherServiceManifest(params1.getWeatherServiceManifest(), params2.getWeatherServiceManifest())) {
            return false;
        }

        if (!validateIntegerList(params1.getHandledRpcs(), params2.getHandledRpcs())) {
            return false;
        }

        return true;
    }

    public static boolean validateWeatherServiceManifest(WeatherServiceManifest params1, WeatherServiceManifest params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!params1.getMaxHourlyForecastAmount().equals(params2.getMaxHourlyForecastAmount())) {
            return false;
        }

        if (!params1.getMaxMinutelyForecastAmount().equals(params2.getMaxMinutelyForecastAmount())) {
            return false;
        }

        if (!params1.getMaxMultidayForecastAmount().equals(params2.getMaxMultidayForecastAmount())) {
            return false;
        }

        if (params1.getCurrentForecastSupported() != params2.getCurrentForecastSupported()) {
            return false;
        }

        if (params1.getWeatherForLocationSupported() != params2.getWeatherForLocationSupported()) {
            return false;
        }

        return true;
    }

    public static boolean validateLocationDetails(LocationDetails params1, LocationDetails params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!params1.getAddressLines().equals(params2.getAddressLines())) {
            return false;
        }

        if (!validateCoordinate(params1.getCoordinate(), params2.getCoordinate())) {
            return false;
        }

        if (!params1.getLocationDescription().equals(params2.getLocationDescription())) {
            return false;
        }

        if (!params1.getPhoneNumber().equals(params2.getPhoneNumber())) {
            return false;
        }

        if (!validateImage(params1.getLocationImage(), params2.getLocationImage())) {
            return false;
        }

        if (!params1.getLocationName().equals(params2.getLocationName())) {
            return false;
        }

        if (!validateOasisAddress(params1.getSearchAddress(), params2.getSearchAddress())) {
            return false;
        }
        return true;
    }

    public static boolean validateChoice(Choice choice1, Choice choice2) {
        if (choice1 == null) {
            return (choice2 == null);
        }
        if (choice2 == null) {
            return (choice1 == null);
        }

        if (!(validateImage(choice1.getImage(), choice2.getImage()))) {
            log("validateChoice", "choice1 image \"" + choice1.getImage() + "\" didn't match choice2 image \""
                    + choice2.getImage() + "\"");
            return false;
        }
        if (!(validateImage(choice1.getSecondaryImage(), choice2.getSecondaryImage()))) {
            log("validateChoice", "choice1 secondary image \"" + choice1.getSecondaryImage()
                    + "\" didn't match choice2 image \"" + choice2.getSecondaryImage() + "\"");
            return false;
        }
        if (choice1.getChoiceID() != choice2.getChoiceID()) {
            log("validateChoice",
                    "choice1 ID \"" + choice1.getChoiceID() + "\" didn't match choice2 ID \"" + choice2.getChoiceID()
                            + "\"");
            return false;
        }
        if (!(validateText(choice1.getMenuName(), choice2.getMenuName()))) {
            log("validateChoice", "choice1 menu name \"" + choice1.getMenuName()
                    + "\" didn't match choice2 menu name \"" + choice2.getMenuName() + "\"");
            return false;
        }
        if (!(validateText(choice1.getSecondaryText(), choice2.getSecondaryText()))) {
            log("validateChoice", "choice1 secondary text \"" + choice1.getSecondaryText()
                    + "\" didn't match choice2 secondary text \"" + choice2.getSecondaryText() + "\"");
            return false;
        }
        if (!(validateText(choice1.getTertiaryText(), choice2.getTertiaryText()))) {
            log("validateChoice", "choice1 tertiary text \"" + choice1.getTertiaryText()
                    + "\" didn't match choice2 tertiary text \"" + choice2.getTertiaryText() + "\"");
            return false;
        }
        if (!(validateStringList(choice1.getVrCommands(), choice2.getVrCommands()))) {
            log("validateChoice", "choice1 VR commands \"" + choice1.getVrCommands()
                    + "\" didn't match choice2 VR commands \"" + choice2.getVrCommands() + "\"");
            return false;
        }

        return true;
    }

    public static boolean validateText(String text1, String text2) {
        if (text1 == null) {
            return (text2 == null);
        }
        if (text2 == null) {
            return (text1 == null);
        }

        return text1.equals(text2);
    }

    public static boolean validateScreenParams(ScreenParams params1, ScreenParams params2) {
        if (params1 == null) {
            return (params2 == null);
        }
        if (params2 == null) {
            return (params1 == null);
        }

        if (!(validateImageResolution(params1.getImageResolution(), params2.getImageResolution()))) {
            log("validateScreenParams", "Image resolutions didn't match!");
            return false;
        }

        if (!(validateTouchEventCapabilities(params1.getTouchEventAvailable(), params2.getTouchEventAvailable()))) {
            log("validateScreenParams", "Touch event capabilities didn't match!");
            return false;
        }

        return true;
    }

    public static boolean validateImageResolution(ImageResolution image1, ImageResolution image2) {
        if (image1 == null) {
            return (image2 == null);
        }
        if (image2 == null) {
            return (image1 == null);
        }

        if ((int) image1.getResolutionHeight() != (int) image2.getResolutionHeight()) {
            log("validateImageResolution",
                    "Height " + image1.getResolutionHeight() + " didn't match height " + image2.getResolutionHeight()
                            + ".");
            return false;
        }

        if ((int) image1.getResolutionWidth() != (int) image2.getResolutionWidth()) {
            log("validateImageResolution",
                    "Width " + image1.getResolutionWidth() + " didn't match width " + image2.getResolutionWidth() + ".");
            return false;
        }

        return true;
    }

    public static boolean validateTemperature(Temperature temperature1, Temperature temperature2) {
        if (temperature1 == null) {
            return (temperature2 == null);
        }
        if (temperature2 == null) {
            return (temperature1 == null);
        }

        if ((float) temperature1.getValue() != (float) temperature2.getValue()) {
            log("validateTemperature",
                    "Value " + temperature1.getValue() + " didn't match value " + temperature2.getValue()
                            + ".");
            return false;
        }

        if (temperature1.getUnit() != temperature2.getUnit()) {
            log("validateTemperature",
                    "Unit " + temperature1.getUnit() + " didn't match unit " + temperature2.getUnit() + ".");
            return false;
        }

        return true;
    }

    public static boolean validateRdsData(RdsData rdsData1, RdsData rdsData2) {
        if (rdsData1 == null) {
            return (rdsData2 == null);
        }
        if (rdsData2 == null) {
            return (rdsData1 == null);
        }

        if (rdsData1.getProgramService() != rdsData2.getProgramService()) {
            log("validateRdsData",
                    "Ps " + rdsData1.getProgramService() + " didn't match Ps " + rdsData2.getProgramService()
                            + ".");
            return false;
        }

        if (rdsData1.getRadioText() != rdsData2.getRadioText()) {
            log("validateRdsData",
                    "Rt " + rdsData1.getRadioText() + " didn't match Rt " + rdsData2.getRadioText()
                            + ".");
            return false;
        }

        if (rdsData1.getClockText() != rdsData2.getClockText()) {
            log("validateRdsData",
                    "Ct " + rdsData1.getClockText() + " didn't match Ct " + rdsData2.getClockText()
                            + ".");
            return false;
        }

        if (rdsData1.getProgramIdentification() != rdsData2.getProgramIdentification()) {
            log("validateRdsData",
                    "Pi " + rdsData1.getProgramIdentification() + " didn't match Pi " + rdsData2.getProgramIdentification()
                            + ".");
            return false;
        }

        if (rdsData1.getRegion() != rdsData2.getRegion()) {
            log("validateRdsData",
                    "Reg " + rdsData1.getRegion() + " didn't match Reg " + rdsData2.getRegion()
                            + ".");
            return false;
        }

        if (rdsData1.getTrafficProgram() != rdsData2.getTrafficProgram()) {
            log("validateRdsData",
                    "Tp " + rdsData1.getTrafficProgram() + " didn't match Tp " + rdsData2.getTrafficProgram()
                            + ".");
            return false;
        }

        if (rdsData1.getTrafficAnnouncement() != rdsData2.getTrafficAnnouncement()) {
            log("validateRdsData",
                    "Ta " + rdsData1.getTrafficAnnouncement() + " didn't match Ta " + rdsData2.getTrafficAnnouncement()
                            + ".");
            return false;
        }

        if (rdsData1.getProgramType() != rdsData2.getProgramType()) {
            log("validateRdsData",
                    "Pty " + rdsData1.getProgramType() + " didn't match Pty " + rdsData2.getProgramType()
                            + ".");
            return false;
        }

        return true;
    }

    public static boolean validateHMISettingsControlCapabilities(HMISettingsControlCapabilities hmiSettingsControlCapabilities1, HMISettingsControlCapabilities hmiSettingsControlCapabilities2) {
        if (hmiSettingsControlCapabilities1 == null) {
            return (hmiSettingsControlCapabilities2 == null);
        }
        if (hmiSettingsControlCapabilities2 == null) {
            return (hmiSettingsControlCapabilities1 == null);
        }

        if (hmiSettingsControlCapabilities1.getModuleName() != hmiSettingsControlCapabilities2.getModuleName()) {
            return false;
        }

        if (hmiSettingsControlCapabilities1.getDisplayModeUnitAvailable() != hmiSettingsControlCapabilities2.getDisplayModeUnitAvailable()) {
            return false;
        }

        if (hmiSettingsControlCapabilities1.getDistanceUnitAvailable() != hmiSettingsControlCapabilities2.getDistanceUnitAvailable()) {
            return false;
        }

        if (hmiSettingsControlCapabilities1.getTemperatureUnitAvailable() != hmiSettingsControlCapabilities2.getTemperatureUnitAvailable()) {
            return false;
        }

        return true;
    }

    public static boolean validateLightControlCapabilities(LightControlCapabilities lightControlCapabilities1, LightControlCapabilities lightControlCapabilities2) {
        if (lightControlCapabilities1 == null) {
            return (lightControlCapabilities2 == null);
        }
        if (lightControlCapabilities2 == null) {
            return (lightControlCapabilities1 == null);
        }

        if (lightControlCapabilities1.getModuleName() != lightControlCapabilities2.getModuleName()) {
            return false;
        }

        if (!(validateLightCapabilitiesList(lightControlCapabilities1.getSupportedLights(), lightControlCapabilities2.getSupportedLights()))) {
            return false;
        }

        return true;
    }

    public static boolean validateClimateControlData(ClimateControlData climateControlData1, ClimateControlData climateControlData2) {
        if (climateControlData1 == null) {
            return (climateControlData2 == null);
        }
        if (climateControlData2 == null) {
            return (climateControlData1 == null);
        }

        if (climateControlData1.getFanSpeed() != climateControlData2.getFanSpeed()) {
            log("validateClimateControlData",
                    "FanSpeed " + climateControlData1.getFanSpeed() + " didn't match fanSpeed " + climateControlData2.getFanSpeed()
                            + ".");
            return false;
        }

        if (!(validateTemperature(climateControlData1.getCurrentTemperature(), climateControlData2.getCurrentTemperature()))) {
            return false;
        }

        if (!(validateTemperature(climateControlData1.getDesiredTemperature(), climateControlData2.getDesiredTemperature()))) {
            return false;
        }

        if (climateControlData1.getAcEnable() != climateControlData2.getAcEnable()) {
            log("validateClimateControlData",
                    "AcEnable " + climateControlData1.getAcEnable() + " didn't match AcEnable " + climateControlData2.getAcEnable()
                            + ".");
            return false;
        }

        if (climateControlData1.getCirculateAirEnable() != climateControlData2.getCirculateAirEnable()) {
            log("validateClimateControlData",
                    "CirculateAirEnable " + climateControlData1.getCirculateAirEnable() + " didn't match CirculateAirEnable " + climateControlData2.getCirculateAirEnable()
                            + ".");
            return false;
        }

        if (climateControlData1.getAutoModeEnable() != climateControlData2.getAutoModeEnable()) {
            log("validateClimateControlData",
                    "AutoModeEnable " + climateControlData1.getAutoModeEnable() + " didn't match AutoModeEnable " + climateControlData2.getAutoModeEnable()
                            + ".");
            return false;
        }

        if (climateControlData1.getDefrostZone() != climateControlData2.getDefrostZone()) {
            log("validateClimateControlData",
                    "DefrostZone " + climateControlData1.getDefrostZone() + " didn't match DefrostZone " + climateControlData2.getDefrostZone()
                            + ".");
            return false;
        }

        if (climateControlData1.getDualModeEnable() != climateControlData2.getDualModeEnable()) {
            log("validateClimateControlData",
                    "DualModeEnable " + climateControlData1.getDualModeEnable() + " didn't match DualModeEnable " + climateControlData2.getDualModeEnable()
                            + ".");
            return false;
        }

        if (climateControlData1.getAcMaxEnable() != climateControlData2.getAcMaxEnable()) {
            log("validateClimateControlData",
                    "AcMaxEnable " + climateControlData1.getAcMaxEnable() + " didn't match AcMaxEnable " + climateControlData2.getAcMaxEnable()
                            + ".");
            return false;
        }

        if (climateControlData1.getVentilationMode() != climateControlData2.getVentilationMode()) {
            log("validateClimateControlData",
                    "VentilationMode " + climateControlData1.getVentilationMode() + " didn't match VentilationMode " + climateControlData2.getVentilationMode()
                            + ".");
            return false;
        }


        return true;
    }

    public static boolean validateSeatControlData(SeatControlData seatControlData1, SeatControlData seatControlData2) {
        if (seatControlData1 == null) {
            return (seatControlData2 == null);
        }
        if (seatControlData2 == null) {
            return (seatControlData1 == null);
        }

        if (seatControlData1.getCoolingEnabled() != seatControlData2.getCoolingEnabled()) {
            return false;
        }
        if (seatControlData1.getHeatingEnabled() != seatControlData2.getHeatingEnabled()) {
            return false;
        }
        if (seatControlData1.getMassageEnabled() != seatControlData2.getMassageEnabled()) {
            return false;
        }
        if (seatControlData1.getBackTiltAngle() != seatControlData2.getBackTiltAngle()) {
            return false;
        }
        if (seatControlData1.getBackVerticalPosition() != seatControlData2.getBackVerticalPosition()) {
            return false;
        }
        if (seatControlData1.getCoolingLevel() != seatControlData2.getCoolingLevel()) {
            return false;
        }
        if (seatControlData1.getFrontVerticalPosition() != seatControlData2.getFrontVerticalPosition()) {
            return false;
        }
        if (seatControlData1.getHeadSupportHorizontalPosition() != seatControlData2.getHeadSupportHorizontalPosition()) {
            return false;
        }
        if (seatControlData1.getHeadSupportVerticalPosition() != seatControlData2.getHeadSupportVerticalPosition()) {
            return false;
        }
        if (seatControlData1.getHeatingLevel() != seatControlData2.getHeatingLevel()) {
            return false;
        }
        if (seatControlData1.getHorizontalPosition() != seatControlData2.getHorizontalPosition()) {
            return false;
        }
        if (seatControlData1.getId() != seatControlData2.getId()) {
            return false;
        }

        if (!(validateSeatMemoryAction(seatControlData1.getMemory(), seatControlData2.getMemory()))) {
            return false;
        }

        if (!(validateSeatMemoryAction(seatControlData1.getMemory(), seatControlData2.getMemory()))) {
            return false;
        }

        return true;
    }

    public static boolean validateAudioControlData(AudioControlData audioControlData1, AudioControlData audioControlData2) {
        if (audioControlData1 == null) {
            return (audioControlData2 == null);
        }
        if (audioControlData2 == null) {
            return (audioControlData1 == null);
        }

        if (audioControlData1.getKeepContext() != audioControlData2.getKeepContext()) {
            return false;
        }

        if (audioControlData1.getSource() != audioControlData2.getSource()) {
            return false;
        }

        if (audioControlData1.getVolume() != audioControlData2.getVolume()) {
            return false;
        }

        if (!(validateEqualizerSettingsList(audioControlData1.getEqualizerSettings(), audioControlData2.getEqualizerSettings()))) {
            return false;
        }


        return true;
    }

    public static boolean validateHMISettingsControlData(HMISettingsControlData hmiSettingsControlData1, HMISettingsControlData hmiSettingsControlData2) {
        if (hmiSettingsControlData1 == null) {
            return (hmiSettingsControlData2 == null);
        }
        if (hmiSettingsControlData2 == null) {
            return (hmiSettingsControlData1 == null);
        }

        if (hmiSettingsControlData1.getDisplayMode() != hmiSettingsControlData2.getDisplayMode()) {
            return false;
        }

        if (hmiSettingsControlData1.getDistanceUnit() != hmiSettingsControlData2.getDistanceUnit()) {
            return false;
        }

        if (hmiSettingsControlData1.getTemperatureUnit() != hmiSettingsControlData2.getTemperatureUnit()) {
            return false;
        }

        return true;
    }

    public static boolean validateLightControlData(LightControlData lightControlData1, LightControlData lightControlData2) {
        if (lightControlData1 == null) {
            return (lightControlData2 == null);
        }
        if (lightControlData2 == null) {
            return (lightControlData1 == null);
        }

        if (!(validateLightStateList(lightControlData1.getLightState(), lightControlData2.getLightState()))) {
            return false;
        }

        return true;
    }

    public static boolean validateModuleData(ModuleData moduleData1, ModuleData moduleData2) {
        if (moduleData1 == null) {
            return (moduleData2 == null);
        }
        if (moduleData2 == null) {
            return (moduleData1 == null);
        }

        if (moduleData1.getModuleType() != moduleData2.getModuleType()) {
            log("validateModuleData",
                    "ModuleType " + moduleData1.getModuleType() + " didn't match ModuleType " + moduleData2.getModuleType()
                            + ".");
            return false;
        }

        if (!(validateRadioControlData(moduleData1.getRadioControlData(), moduleData2.getRadioControlData()))) {
            return false;
        }

        if (!(validateClimateControlData(moduleData1.getClimateControlData(), moduleData2.getClimateControlData()))) {
            return false;
        }

        return true;
    }

    public static boolean validateSisData(SisData sisData1, SisData sisData2) {
        if (sisData1 == null) {
            return (sisData2 == null);
        }
        if (sisData2 == null) {
            return (sisData1 == null);
        }

        if (sisData1.getStationShortName() != sisData2.getStationShortName()) {
            return false;
        }

        if (!(validateStationIDNumber(sisData1.getStationIDNumber(), sisData2.getStationIDNumber()))) {
            return false;
        }

        if (sisData1.getStationLongName() != sisData2.getStationLongName()) {
            return false;
        }

        if (!(validateGpsData(sisData1.getStationLocation(), sisData2.getStationLocation()))) {
            return false;
        }

        if (sisData1.getStationMessage() != sisData2.getStationMessage()) {
            return false;
        }

        return true;
    }

    public static boolean validateStationIDNumber(StationIDNumber stationIDNumber1, StationIDNumber stationIDNumber2) {
        if (stationIDNumber1 == null) {
            return (stationIDNumber2 == null);
        }
        if (stationIDNumber2 == null) {
            return (stationIDNumber1 == null);
        }

        if (stationIDNumber1.getCountryCode() != stationIDNumber2.getCountryCode()) {
            return false;
        }

        if (stationIDNumber1.getFccFacilityId() != stationIDNumber2.getFccFacilityId()) {
            return false;
        }

        return true;
    }

    public static boolean validateRemoteControlCapabilities(RemoteControlCapabilities remoteControlCapabilities1, RemoteControlCapabilities remoteControlCapabilities2) {
        if (remoteControlCapabilities1 == null) {
            return (remoteControlCapabilities2 == null);
        }
        if (remoteControlCapabilities2 == null) {
            return (remoteControlCapabilities1 == null);
        }

        if (!(validateButtonCapabilities(remoteControlCapabilities1.getButtonCapabilities(), remoteControlCapabilities2.getButtonCapabilities()))) {
            return false;
        }

        if (!(validateRadioControlCapabilities(remoteControlCapabilities1.getRadioControlCapabilities(), remoteControlCapabilities2.getRadioControlCapabilities()))) {
            return false;
        }

        if (!(validateClimateControlCapabilities(remoteControlCapabilities1.getClimateControlCapabilities(), remoteControlCapabilities2.getClimateControlCapabilities()))) {
            return false;
        }

        return true;
    }

    public static boolean validateRadioControlData(RadioControlData radioControlData1, RadioControlData radioControlData2) {
        if (radioControlData1 == null) {
            return (radioControlData2 == null);
        }
        if (radioControlData2 == null) {
            return (radioControlData1 == null);
        }

        if (radioControlData1.getFrequencyInteger() != radioControlData2.getFrequencyInteger()) {
            log("validateRadioControlData",
                    "FrequencyInteger " + radioControlData1.getFrequencyInteger() + " didn't match FrequencyInteger " + radioControlData2.getFrequencyInteger()
                            + ".");
            return false;
        }

        if (radioControlData1.getFrequencyFraction() != radioControlData2.getFrequencyFraction()) {
            log("validateRadioControlData",
                    "FrequencyFraction " + radioControlData1.getFrequencyFraction() + " didn't match FrequencyFraction " + radioControlData2.getFrequencyFraction()
                            + ".");
            return false;
        }

        if (radioControlData1.getBand() != radioControlData2.getBand()) {
            log("validateRadioControlData",
                    "Band " + radioControlData1.getBand() + " didn't match Band " + radioControlData2.getBand()
                            + ".");
            return false;
        }

        if (!(validateRdsData(radioControlData1.getRdsData(), radioControlData2.getRdsData()))) {
            return false;
        }

        if (radioControlData1.getAvailableHDs() != radioControlData2.getAvailableHDs()) {
            log("validateRadioControlData",
                    "AvailableHDs " + radioControlData1.getAvailableHDs() + " didn't match AvailableHDs " + radioControlData2.getAvailableHDs()
                            + ".");
            return false;
        }

        if (radioControlData1.getHdChannel() != radioControlData2.getHdChannel()) {
            log("validateRadioControlData",
                    "HdChannel " + radioControlData1.getHdChannel() + " didn't match HdChannel " + radioControlData2.getHdChannel()
                            + ".");
            return false;
        }

        if (radioControlData1.getSignalStrength() != radioControlData2.getSignalStrength()) {
            log("validateRadioControlData",
                    "SignalStrength " + radioControlData1.getSignalStrength() + " didn't match SignalStrength " + radioControlData2.getSignalStrength()
                            + ".");
            return false;
        }

        if (radioControlData1.getSignalChangeThreshold() != radioControlData2.getSignalChangeThreshold()) {
            log("validateRadioControlData",
                    "SignalChangeThreshold " + radioControlData1.getSignalChangeThreshold() + " didn't match SignalChangeThreshold " + radioControlData2.getSignalChangeThreshold()
                            + ".");
            return false;
        }

        if (radioControlData1.getRadioEnable() != radioControlData2.getRadioEnable()) {
            log("validateRadioControlData",
                    "RadioEnable " + radioControlData1.getRadioEnable() + " didn't match RadioEnable " + radioControlData2.getRadioEnable()
                            + ".");
            return false;
        }

        if (radioControlData1.getState() != radioControlData2.getState()) {
            log("validateRadioControlData",
                    "State " + radioControlData1.getState() + " didn't match State " + radioControlData2.getState()
                            + ".");
            return false;
        }

        return true;
    }

    public static boolean validateNavigationCapability(NavigationCapability navigationCapability1, NavigationCapability navigationCapability2) {
        if (navigationCapability1 == null) {
            return (navigationCapability2 == null);
        }
        if (navigationCapability2 == null) {
            return (navigationCapability1 == null);
        }

        if (navigationCapability1.getSendLocationEnabled() != navigationCapability2.getSendLocationEnabled()) {
            log("validateNavigationCapability",
                    "SendLocationEnabled " + navigationCapability1.getSendLocationEnabled() + " didn't match SendLocationEnabled " + navigationCapability2.getSendLocationEnabled()
                            + ".");
            return false;
        }

        if (navigationCapability1.getWayPointsEnabled() != navigationCapability2.getWayPointsEnabled()) {
            log("validateNavigationCapability",
                    "WayPointsEnabled " + navigationCapability1.getWayPointsEnabled() + " didn't match WayPointsEnabled " + navigationCapability2.getWayPointsEnabled() + ".");
            return false;
        }

        return true;
    }

    public static boolean validateDriverDistractionCapability(DriverDistractionCapability driverDistractionCapability1, DriverDistractionCapability driverDistractionCapability2) {
        if (driverDistractionCapability1 == null) {
            return (driverDistractionCapability1 == null);
        }
        if (driverDistractionCapability2 == null) {
            return (driverDistractionCapability2 == null);
        }

        if (driverDistractionCapability1.getMenuLength() != driverDistractionCapability2.getMenuLength()) {
            log("validateDriverDistractionCapability",
                    "menuLength " + driverDistractionCapability1.getMenuLength() + " didn't match menuLength " + driverDistractionCapability2.getMenuLength()
                            + ".");
            return false;
        }

        if (driverDistractionCapability1.getSubMenuDepth() != driverDistractionCapability2.getSubMenuDepth()) {
            log("validateDriverDistractionCapability",
                    "subMenuDepth " + driverDistractionCapability1.getSubMenuDepth() + " didn't match subMenuDepth " + driverDistractionCapability2.getSubMenuDepth()
                            + ".");
            return false;
        }

        return true;
    }

    public static boolean validatePhoneCapability(PhoneCapability phoneCapability1, PhoneCapability phoneCapability2) {
        if (phoneCapability1 == null) {
            return (phoneCapability2 == null);
        }
        if (phoneCapability2 == null) {
            return (phoneCapability1 == null);
        }

        if (phoneCapability1.getDialNumberEnabled() != phoneCapability2.getDialNumberEnabled()) {
            log("validatePhoneCapability",
                    "DialNumberEnabled " + phoneCapability1.getDialNumberEnabled() + " didn't match DialNumberEnabled " + phoneCapability1.getDialNumberEnabled()
                            + ".");
            return false;
        }

        return true;
    }

    public static boolean validateTouchEventCapabilities(TouchEventCapabilities item1, TouchEventCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getPressAvailable() != item2.getPressAvailable()) {
            log("validateTouchEventCapabilities", "Press available \"" + item1.getPressAvailable()
                    + "\" didn't match press available \"" + item1.getPressAvailable() + "\".");
            return false;
        }

        if (item1.getDoublePressAvailable() != item2.getDoublePressAvailable()) {
            log("validateTouchEventCapabilities", "Double press available \"" + item1.getDoublePressAvailable()
                    + "\" didn't match double press available \"" + item1.getDoublePressAvailable() + "\".");
            return false;
        }

        if (item1.getMultiTouchAvailable() != item2.getMultiTouchAvailable()) {
            log("validateTouchEventCapabilities", "Multi-touch available \"" + item1.getMultiTouchAvailable()
                    + "\" didn't match multi-touch available \"" + item1.getMultiTouchAvailable() + "\".");
            return false;
        }

        return true;
    }

    public static boolean validateSeatMemoryAction(SeatMemoryAction item1, SeatMemoryAction item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getAction() == null) {
            return (item2.getAction() == null);
        }

        if (item1.getAction() != item2.getAction()) {
            return false;
        }

        if (item1.getId() != item2.getId()) {
            return false;
        }

        if (item1.getLabel() != item2.getLabel()) {
            return false;
        }

        return true;
    }

    public static boolean validateTextFields(TextField item1, TextField item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }


        if (item1.getCharacterSet() != item2.getCharacterSet()) {
            return false;
        }
        if (item1.getName() != item2.getName()) {
            return false;
        }
        if (item1.getRows() != item2.getRows()) {
            return false;
        }
        if (item1.getWidth() != item2.getWidth()) {
            return false;
        }

        return true;
    }

    public static boolean validateImageFields(ImageField item1, ImageField item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }
        if (!(validateFileTypes(item1.getImageTypeSupported(), item2.getImageTypeSupported()))) {
            return false;
        }
        if (item1.getName() != item2.getName()) {
            return false;
        }
        if (!(validateImageResolution(item1.getImageResolution(), item2.getImageResolution()))) {
            return false;
        }

        return true;
    }

    public static boolean validateWindowTypeCapabilities(WindowTypeCapabilities item1, WindowTypeCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getType() == null) {
            return (item2.getType() == null);
        }

        if (item1.getType() != item2.getType()) {
            return false;
        }

        if (item1.getMaximumNumberOfWindows() != item2.getMaximumNumberOfWindows()) {
            return false;
        }

        return true;
    }

    public static boolean validateFileTypes(List<FileType> item1, List<FileType> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i) != item2.get(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateWeatherDataList(List<WeatherData> item1Array, List<WeatherData> item2Array) {
        if (item1Array.size() != item2Array.size()) {
            return false;
        }

        for (int i = 0; i < item1Array.size(); i++) {
            if (!validateWeatherData(item1Array.get(i), item2Array.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateWeatherData(WeatherData item1, WeatherData item2) {
        if (item1 == null) {
            return (item2 == null);
        }

        if (item2 == null) {
            return (item1 == null);
        }

        if (!validateTemperature(item1.getCurrentTemperature(), item2.getCurrentTemperature())) {
            return false;
        }

        if (!validateTemperature(item1.getTemperatureHigh(), item2.getTemperatureHigh())) {
            return false;
        }

        if (!validateTemperature(item1.getTemperatureLow(), item2.getTemperatureLow())) {
            return false;
        }

        if (!validateTemperature(item1.getApparentTemperature(), item2.getApparentTemperature())) {
            return false;
        }

        if (!validateTemperature(item1.getApparentTemperatureHigh(), item2.getApparentTemperatureHigh())) {
            return false;
        }

        if (!validateTemperature(item1.getApparentTemperatureLow(), item2.getApparentTemperatureLow())) {
            return false;
        }

        if (!item1.getWeatherSummary().equals(item2.getWeatherSummary())) {
            return false;
        }

        if (!validateDateTime(item1.getTime(), item2.getTime())) {
            return false;
        }

        if (!item1.getHumidity().equals(item2.getHumidity())) {
            return false;
        }

        if (!item1.getCloudCover().equals(item2.getCloudCover())) {
            return false;
        }

        if (!item1.getMoonPhase().equals(item2.getMoonPhase())) {
            return false;
        }

        if (!item1.getWindBearing().equals(item2.getWindBearing())) {
            return false;
        }

        if (!item1.getWindGust().equals(item2.getWindGust())) {
            return false;
        }

        if (!item1.getWindSpeed().equals(item2.getWindSpeed())) {
            return false;
        }

        if (!item1.getNearestStormBearing().equals(item2.getNearestStormBearing())) {
            return false;
        }

        if (!item1.getNearestStormDistance().equals(item2.getNearestStormDistance())) {
            return false;
        }

        if (!item1.getPrecipAccumulation().equals(item2.getPrecipAccumulation())) {
            return false;
        }

        if (!item1.getPrecipIntensity().equals(item2.getPrecipIntensity())) {
            return false;
        }

        if (!item1.getPrecipProbability().equals(item2.getPrecipProbability())) {
            return false;
        }

        if (!item1.getPrecipType().equals(item2.getPrecipType())) {
            return false;
        }

        if (!item1.getVisibility().equals(item2.getVisibility())) {
            return false;
        }

        if (!validateImage(item1.getWeatherIcon(), item2.getWeatherIcon())) {
            return false;
        }

        return true;
    }

    public static boolean validateWeatherAlertList(List<WeatherAlert> item1Array, List<WeatherAlert> item2Array) {
        if (item1Array.size() != item2Array.size()) {
            return false;
        }

        for (int i = 0; i < item1Array.size(); i++) {
            if (!validateWeatherAlert(item1Array.get(i), item2Array.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateNavigationInstructionList(List<NavigationInstruction> item1Array, List<NavigationInstruction> item2Array) {
        if (item1Array == null && item2Array == null) {
            return true;
        }

        if (item1Array == null || item2Array == null) {
            return false;
        }

        if (item1Array.size() != item2Array.size()) {
            return false;
        }

        for (int i = 0; i < item1Array.size(); i++) {
            if (!validateNavigationInstruction(item1Array.get(i), item2Array.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateNavigationInstruction(NavigationInstruction item1, NavigationInstruction item2) {
        if (item1 == null) {
            return (item2 == null);
        }

        if (item2 == null) {
            return (item1 == null);
        }

        if (!validateLocationDetails(item1.getLocationDetails(), item2.getLocationDetails())) {
            return false;
        }

        if (!item1.getAction().equals(item2.getAction())) {
            return false;
        }

        if (!validateDateTime(item1.getEta(), item2.getEta())) {
            return false;
        }

        if (!item1.getBearing().equals(item2.getBearing())) {
            return false;
        }

        if (!item1.getJunctionType().equals(item2.getJunctionType())) {
            return false;
        }

        if (!item1.getDrivingSide().equals(item2.getDrivingSide())) {
            return false;
        }

        if (!item1.getDetails().equals(item2.getDetails())) {
            return false;
        }

        if (!validateImage(item1.getImage(), item2.getImage())) {
            return false;
        }

        return true;
    }

    public static boolean validateMediaServiceData(MediaServiceData item1, MediaServiceData item2) {
        if (item1 == null) {
            return (item2 == null);
        }

        if (item2 == null) {
            return (item1 == null);
        }

        if (!item1.getMediaType().equals(item2.getMediaType())) {
            return false;
        }

        if (!item1.getMediaTitle().equals(item2.getMediaTitle())) {
            return false;
        }

        if (!item1.getMediaArtist().equals(item2.getMediaArtist())) {
            return false;
        }

        if (!item1.getMediaAlbum().equals(item2.getMediaAlbum())) {
            return false;
        }

        if (!item1.getPlaylistName().equals(item2.getPlaylistName())) {
            return false;
        }

        if (!item1.getIsExplicit().equals(item2.getIsExplicit())) {
            return false;
        }

        if (!item1.getTrackPlaybackProgress().equals(item2.getTrackPlaybackProgress())) {
            return false;
        }

        if (!item1.getTrackPlaybackDuration().equals(item2.getTrackPlaybackDuration())) {
            return false;
        }

        if (!item1.getQueuePlaybackProgress().equals(item2.getQueuePlaybackProgress())) {
            return false;
        }

        if (!item1.getQueueCurrentTrackNumber().equals(item2.getQueueCurrentTrackNumber())) {
            return false;
        }

        if (!item1.getQueuePlaybackDuration().equals(item2.getQueuePlaybackDuration())) {
            return false;
        }

        if (!item1.getQueueTotalTrackCount().equals(item2.getQueueTotalTrackCount())) {
            return false;
        }

        return true;
    }

    public static boolean validateWeatherServiceData(WeatherServiceData item1, WeatherServiceData item2) {
        if (item1 == null) {
            return (item2 == null);
        }

        if (item2 == null) {
            return (item1 == null);
        }

        if (!validateLocationDetails(item1.getLocation(), item2.getLocation())) {
            return false;
        }

        if (!validateWeatherData(item1.getCurrentForecast(), item2.getCurrentForecast())) {
            return false;
        }

        if (!validateWeatherDataList(item1.getHourlyForecast(), item2.getHourlyForecast())) {
            return false;
        }

        if (!validateWeatherDataList(item1.getMinuteForecast(), item2.getMinuteForecast())) {
            return false;
        }

        if (!validateWeatherDataList(item1.getMultidayForecast(), item2.getMultidayForecast())) {
            return false;
        }

        if (!validateWeatherAlertList(item1.getAlerts(), item2.getAlerts())) {
            return false;
        }

        return true;
    }

    public static boolean validateNavigationServiceData(NavigationServiceData item1, NavigationServiceData item2) {
        if (item1 == null) {
            return (item2 == null);
        }

        if (item2 == null) {
            return (item1 == null);
        }

        if (!validateDateTime(item1.getTimeStamp(), item2.getTimeStamp())) {
            return false;
        }

        if (!validateLocationDetails(item1.getOrigin(), item2.getOrigin())) {
            return false;
        }

        if (!validateLocationDetails(item1.getDestination(), item2.getDestination())) {
            return false;
        }

        if (!validateDateTime(item1.getDestinationETA(), item2.getDestinationETA())) {
            return false;
        }

        if (!validateNavigationInstructionList(item1.getInstructions(), item2.getInstructions())) {
            return false;
        }

        if (!validateDateTime(item1.getNextInstructionETA(), item2.getNextInstructionETA())) {
            return false;
        }

        if (item1.getNextInstructionDistance() != null && item2.getNextInstructionDistance() != null && !item1.getNextInstructionDistance().equals(item2.getNextInstructionDistance())) {
            return false;
        }

        if (item1.getNextInstructionDistanceScale() != null && item2.getNextInstructionDistanceScale() != null && !item1.getNextInstructionDistanceScale().equals(item2.getNextInstructionDistanceScale())) {
            return false;
        }

        if (item1.getPrompt() != null && item1.getPrompt() != null && !item1.getPrompt().equals(item2.getPrompt())) {
            return false;
        }

        return true;
    }

    public static boolean validateWeatherAlert(WeatherAlert item1, WeatherAlert item2) {
        if (item1 == null) {
            return (item2 == null);
        }

        if (item2 == null) {
            return (item1 == null);
        }

        if (!validateDateTime(item1.getExpires(), item2.getExpires())) {
            return false;
        }

        if (!validateDateTime(item1.getTimeIssued(), item2.getTimeIssued())) {
            return false;
        }

        if (!item1.getTitle().equals(item2.getTitle())) {
            return false;
        }

        if (!item1.getSummary().equals(item2.getSummary())) {
            return false;
        }

        if (!item1.getSeverity().equals(item2.getSeverity())) {
            return false;
        }

        if (!validateStringList(item1.getRegions(), item2.getRegions())) {
            return false;
        }

        return true;
    }

    public static boolean validateDateTime(DateTime item1, DateTime item2) {
        if (item1 == null) {
            return (item2 == null);
        }

        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getDay() != item2.getDay()) {
            return false;
        }

        if (item1.getHour() != item2.getHour()) {
            return false;
        }

        if (item1.getMilliSecond() != item2.getMilliSecond()) {
            return false;
        }

        if (item1.getMinute() != item2.getMinute()) {
            return false;
        }

        if (item1.getMonth() != item2.getMonth()) {
            return false;
        }

        if (item1.getSecond() != item2.getSecond()) {
            return false;
        }

        if (item1.getTzHour() != item2.getTzHour()) {
            return false;
        }

        if (item1.getTzMinute() != item2.getTzMinute()) {
            return false;
        }

        if (item1.getYear() != item2.getYear()) {
            return false;
        }

        return true;
    }

    public static boolean validateGpsData(GPSData item1, GPSData item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getActual() != item2.getActual()) {
            return false;
        }
        if (item1.getAltitude() != item2.getAltitude()) {
            return false;
        }
        if (item1.getCompassDirection() != item2.getCompassDirection()) {
            return false;
        }
        if (item1.getDimension() != item2.getDimension()) {
            return false;
        }
        if (item1.getHdop() != item2.getHdop()) {
            return false;
        }
        if (item1.getHeading() != item2.getHeading()) {
            return false;
        }
        if (item1.getLatitudeDegrees() != item2.getLatitudeDegrees()) {
            return false;
        }
        if (item1.getLongitudeDegrees() != item2.getLongitudeDegrees()) {
            return false;
        }
        if (item1.getPdop() != item2.getPdop()) {
            return false;
        }
        if (item1.getSatellites() != item2.getSatellites()) {
            return false;
        }
        if (item1.getSpeed() != item2.getSpeed()) {
            return false;
        }
        if (item1.getUtcDay() != item2.getUtcDay()) {
            return false;
        }
        if (item1.getUtcHours() != item2.getUtcHours()) {
            return false;
        }
        if (item1.getUtcMinutes() != item1.getUtcMinutes()) {
            return false;
        }
        if (item1.getUtcMonth() != item2.getUtcMonth()) {
            return false;
        }
        if (item1.getUtcSeconds() != item2.getUtcSeconds()) {
            return false;
        }
        if (item1.getUtcYear() != item2.getUtcYear()) {
            return false;
        }
        if (item1.getVdop() != item2.getVdop()) {
            return false;
        }
        if (item1.getShifted() != item2.getShifted()) {
            return false;
        }

        return true;
    }

    public static boolean validateTireStatus(TireStatus item1, TireStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getPressureTellTale() != item2.getPressureTellTale()) {
            return false;
        }

        SingleTireStatus item1Status = item1.getLeftFront();
        SingleTireStatus item2Status = item2.getLeftFront();
        boolean isEqual = validateSingleTireStatus(item1Status, item2Status);
        if (!isEqual) return false;
        item1Status = item1.getRightFront();
        item2Status = item2.getRightFront();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if (!isEqual) return false;
        item1Status = item1.getLeftRear();
        item2Status = item2.getLeftRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if (!isEqual) return false;
        item1Status = item1.getRightRear();
        item2Status = item2.getRightRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if (!isEqual) return false;
        item1Status = item1.getInnerLeftRear();
        item2Status = item2.getInnerLeftRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if (!isEqual) return false;
        item1Status = item1.getInnerRightRear();
        item2Status = item2.getInnerRightRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if (!isEqual) return false;

        return true;
    }

    public static boolean validateSingleTireStatus(SingleTireStatus item1, SingleTireStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getStatus() != item2.getStatus()) {
            return false;
        }

        return true;
    }

    public static boolean validateBeltStatus(BeltStatus item1, BeltStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getLeftRearInflatableBelted() != item2.getLeftRearInflatableBelted()) {
            return false;
        }
        if (item1.getRightRearInflatableBelted() != item2.getRightRearInflatableBelted()) {
            return false;
        }
        if (item1.getPassengerChildDetected() != item2.getPassengerChildDetected()) {
            return false;
        }
        if (item1.getDriverBuckleBelted() != item2.getDriverBuckleBelted()) {
            return false;
        }
        if (item1.getPassengerBuckleBelted() != item2.getPassengerBuckleBelted()) {
            return false;
        }
        if (item1.getRightRow2BuckleBelted() != item2.getRightRow2BuckleBelted()) {
            return false;
        }
        if (item1.getMiddleRow1BuckleBelted() != item2.getMiddleRow1BuckleBelted()) {
            return false;
        }
        if (item1.getLeftRow2BuckleBelted() != item2.getLeftRow2BuckleBelted()) {
            return false;
        }
        if (item1.getMiddleRow2BuckleBelted() != item2.getMiddleRow2BuckleBelted()) {
            return false;
        }
        if (item1.getMiddleRow3BuckleBelted() != item2.getMiddleRow3BuckleBelted()) {
            return false;
        }
        if (item1.getLeftRow3BuckleBelted() != item2.getLeftRow3BuckleBelted()) {
            return false;
        }
        if (item1.getRightRow3BuckleBelted() != item2.getRightRow3BuckleBelted()) {
            return false;
        }
        if (item1.getPassengerBeltDeployed() != item2.getPassengerBeltDeployed()) {
            return false;
        }
        if (item1.getMiddleRow1BeltDeployed() != item2.getMiddleRow1BeltDeployed()) {
            return false;
        }

        return true;
    }

    public static boolean validateBodyInformation(BodyInformation item1, BodyInformation item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getIgnitionStatus() != item2.getIgnitionStatus()) {
            return false;
        }
        if (item1.getIgnitionStableStatus() != item2.getIgnitionStableStatus()) {
            return false;
        }
        if (item1.getParkBrakeActive() != item2.getParkBrakeActive()) {
            return false;
        }
        if (item1.getDriverDoorAjar() != item2.getDriverDoorAjar()) {
            return false;
        }
        if (item1.getPassengerDoorAjar() != item2.getPassengerDoorAjar()) {
            return false;
        }
        if (item1.getRearLeftDoorAjar() != item2.getRearLeftDoorAjar()) {
            return false;
        }
        if (item1.getRearRightDoorAjar() != item2.getRearRightDoorAjar()) {
            return false;
        }

        return true;
    }

    public static boolean validateDeviceStatus(DeviceStatus item1, DeviceStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getBtIconOn() != item2.getBtIconOn()) {
            return false;
        }
        if (item1.getCallActive() != item2.getCallActive()) {
            return false;
        }
        if (item1.getECallEventActive() != item2.getECallEventActive()) {
            return false;
        }
        if (item1.getMonoAudioOutputMuted() != item2.getMonoAudioOutputMuted()) {
            return false;
        }
        if (item1.getPhoneRoaming() != item2.getPhoneRoaming()) {
            return false;
        }
        if (item1.getStereoAudioOutputMuted() != item2.getStereoAudioOutputMuted()) {
            return false;
        }
        if (item1.getTextMsgAvailable() != item2.getTextMsgAvailable()) {
            return false;
        }
        if (item1.getVoiceRecOn() != item2.getVoiceRecOn()) {
            return false;
        }
        if (item1.getBattLevelStatus() != item2.getBattLevelStatus()) {
            return false;
        }
        if (item1.getPrimaryAudioSource() != item2.getPrimaryAudioSource()) {
            return false;
        }
        if (item1.getSignalLevelStatus() != item2.getSignalLevelStatus()) {
            return false;
        }

        return true;
    }

    public static boolean validateHeadLampStatus(HeadLampStatus item1, HeadLampStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getHighBeamsOn() != item2.getHighBeamsOn()) {
            return false;
        }
        if (item1.getLowBeamsOn() != item2.getLowBeamsOn()) {
            return false;
        }
        if (item1.getAmbientLightStatus() != item2.getAmbientLightStatus()) {
            return false;
        }

        return true;
    }

    public static boolean validateECallInfo(ECallInfo item1, ECallInfo item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getAuxECallNotificationStatus() != item2.getAuxECallNotificationStatus()) {
            return false;
        }
        if (item1.getECallConfirmationStatus() != item2.getECallConfirmationStatus()) {
            return false;
        }
        if (item1.getECallNotificationStatus() != item2.getECallNotificationStatus()) {
            return false;
        }

        return true;
    }

    public static boolean validateAirbagStatus(AirbagStatus item1, AirbagStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }
        if (item1.getDriverAirbagDeployed() != item2.getDriverAirbagDeployed()) {
            return false;
        }
        if (item1.getDriverSideAirbagDeployed() != item2.getDriverSideAirbagDeployed()) {
            return false;
        }
        if (item1.getDriverCurtainAirbagDeployed() != item2.getDriverCurtainAirbagDeployed()) {
            return false;
        }
        if (item1.getPassengerAirbagDeployed() != item2.getPassengerAirbagDeployed()) {
            return false;
        }
        if (item1.getPassengerCurtainAirbagDeployed() != item2.getPassengerCurtainAirbagDeployed()) {
            return false;
        }
        if (item1.getDriverKneeAirbagDeployed() != item2.getDriverKneeAirbagDeployed()) {
            return false;
        }
        if (item1.getPassengerSideAirbagDeployed() != item2.getPassengerSideAirbagDeployed()) {
            return false;
        }
        if (item1.getPassengerKneeAirbagDeployed() != item2.getPassengerKneeAirbagDeployed()) {
            return false;
        }

        return true;
    }

    public static boolean validateEmergencyEvent(EmergencyEvent item1, EmergencyEvent item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getEmergencyEventType() != item2.getEmergencyEventType()) {
            return false;
        }
        if (item1.getFuelCutoffStatus() != item2.getFuelCutoffStatus()) {
            return false;
        }
        if (item1.getMaximumChangeVelocity() != item2.getMaximumChangeVelocity()) {
            return false;
        }
        if (item1.getMultipleEvents() != item2.getMultipleEvents()) {
            return false;
        }
        if (item1.getRolloverEvent() != item2.getRolloverEvent()) {
            return false;
        }

        return true;
    }

    public static boolean validateClusterModeStatus(ClusterModeStatus item1, ClusterModeStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getPowerModeActive() != item2.getPowerModeActive()) {
            return false;
        }
        if (item1.getPowerModeQualificationStatus() != item2.getPowerModeQualificationStatus()) {
            return false;
        }
        if (item1.getPowerModeStatus() != item2.getPowerModeStatus()) {
            return false;
        }
        if (item1.getCarModeStatus() != item2.getCarModeStatus()) {
            return false;
        }

        return true;
    }

    public static boolean validateMyKey(MyKey item1, MyKey item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getE911Override() != item2.getE911Override()) {
            return false;
        }

        return true;
    }

    public static boolean validateFuelRange(List<FuelRange> item1, List<FuelRange> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i).getType() != item2.get(i).getType()) {
                return false;
            }
            if (!item1.get(i).getRange().equals(item2.get(i).getRange())) {
                return false;
            }

            if (!item1.get(i).getCapacity().equals(item2.get(i).getCapacity())) {
                return false;
            }

            if (!item1.get(i).getCapacityUnit().equals(item2.get(i).getCapacityUnit())) {
                return false;
            }

            if (!item1.get(i).getLevel().equals(item2.get(i).getLevel())) {
                return false;
            }

            if (!item1.get(i).getLevelState().equals(item2.get(i).getLevelState())) {
                return false;
            }
        }

        return true;
    }

    public static boolean validatePermissionItem(PermissionItem item1, PermissionItem item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }
        /*
        if(item1 == item2){
            log("validatePermissionItem", "Items are the same object.  No defensive copy took place.");
            return false;
        }
		*/
        if (!item1.getRpcName().equals(item2.getRpcName())) {
            return false;
        }

        if (!validateHmiPermissions(item1.getHMIPermissions(), item2.getHMIPermissions())) {
            return false;
        }

        if (!validateParameterPermissions(item1.getParameterPermissions(), item2.getParameterPermissions())) {
            return false;
        }

        if (item1.getRequireEncryption() != item2.getRequireEncryption()) {
            return false;
        }

        return true;
    }

    public static boolean validateHmiPermissions(HMIPermissions item1, HMIPermissions item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }
        /*
        if(item1 == item2){
            log("validateHmiPermissions", "Items are the same object.  No defensive copy took place.");
            return false;
        }
         */
        if (!validateHmiLevelLists(item1.getAllowed(), item2.getAllowed())) {
            return false;
        }

        List<HMILevel> item1Array = item1.getUserDisallowed();
        List<HMILevel> item2Array = item2.getUserDisallowed();

        if (item1Array.size() != item2Array.size()) {
            return false;
        }

        for (int i = 0; i < item1Array.size(); i++) {
            if (item1Array.get(i) != item2Array.get(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateHmiLevelLists(List<HMILevel> item1Array, List<HMILevel> item2Array) {
        if (item1Array.size() != item2Array.size()) {
            return false;
        }

        for (int i = 0; i < item1Array.size(); i++) {
            if (item1Array.get(i) != item2Array.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateParameterPermissions(ParameterPermissions item1, ParameterPermissions item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }
        /*
        if(item1 == item2){
            log("validateParameterPermissions", "Items are the same object.  No defensive copy took place.");
            return false;
        }
		*/
        if (!validateStringList(item1.getAllowed(), item2.getAllowed())) {
            return false;
        }

        if (!validateStringList(item1.getUserDisallowed(), item2.getUserDisallowed())) {
            return false;
        }

        return true;
    }

    public static boolean validateTouchEvent(TouchEvent item1, TouchEvent item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1 == item2) {
            return true;
        }

        List<TouchCoord> tc1 = item1.getC();
        List<TouchCoord> tc2 = item2.getC();
        List<Long> ts1 = item1.getTs();
        List<Long> ts2 = item2.getTs();

        for (int i = 0; i < tc1.size(); i++) {
            if (!validateTouchCoord(tc1.get(i), tc2.get(i))) {
                return false;
            }
            if (ts1.get(i) != ts2.get(i)) {
                return false;
            }
        }

        if (item1.getId() != item2.getId()) {
            return false;
        }


        return true;
    }

    public static boolean validateTouchCoord(TouchCoord item1, TouchCoord item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getX() != item2.getX()) {
            return false;
        }
        if (item1.getY() != item2.getY()) {
            return false;
        }

        return true;
    }

    public static boolean validateMassageModeData(MassageModeData item1, MassageModeData item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getMassageMode() != item2.getMassageMode()) {
            return false;
        }

        if (item1.getMassageZone() != item2.getMassageZone()) {
            return false;
        }

        return true;
    }

    public static boolean validateMassageModeDataList(List<MassageModeData> item1, List<MassageModeData> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!validateMassageModeData(item1.get(i), item2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateMassageCushionFirmness(MassageCushionFirmness item1, MassageCushionFirmness item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getCushion() != item2.getCushion()) {
            return false;
        }

        if (item1.getFirmness() != item2.getFirmness()) {
            return false;
        }

        return true;
    }

    public static boolean validateMassageCushionFirmnessList(List<MassageCushionFirmness> item1, List<MassageCushionFirmness> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!validateMassageCushionFirmness(item1.get(i), item2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static void log(String tag, String msg) {
        Logger.log(tag, msg);
    }

    public static boolean validateSdlMsgVersion(SdlMsgVersion item1, SdlMsgVersion item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getMajorVersion() != item2.getMajorVersion() ||
                item1.getMinorVersion() != item2.getMinorVersion()) {
            return false;
        }

        return true;
    }

    public static boolean validateDeviceInfo(DeviceInfo item1, DeviceInfo item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getOs() != item2.getOs() ||
                item1.getCarrier() != item2.getCarrier() ||
                item1.getHardware() != item2.getHardware() ||
                item1.getOsVersion() != item2.getOsVersion() ||
                item1.getFirmwareRev() != item2.getFirmwareRev() ||
                item1.getMaxNumberRFCOMMPorts() != item2.getMaxNumberRFCOMMPorts()) {
            return false;
        }

        return true;
    }

    public static boolean validateAppInfo(AppInfo item1, AppInfo item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getAppDisplayName() != item1.getAppDisplayName()
                || item1.getAppBundleID() != item2.getAppBundleID()
                || item1.getAppVersion() != item2.getAppVersion()
                || item1.getAppIcon() != item2.getAppIcon()) {
            return false;
        }

        return true;
    }

    public static boolean validateTemplateColorScheme(TemplateColorScheme item1, TemplateColorScheme item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getPrimaryColor().getRed() != item2.getPrimaryColor().getRed()
                || item1.getPrimaryColor().getGreen() != item2.getPrimaryColor().getGreen()
                || item1.getPrimaryColor().getBlue() != item2.getPrimaryColor().getBlue()
                || item1.getSecondaryColor().getRed() != item2.getSecondaryColor().getRed()
                || item1.getSecondaryColor().getGreen() != item2.getSecondaryColor().getGreen()
                || item1.getSecondaryColor().getBlue() != item2.getSecondaryColor().getBlue()
                || item1.getBackgroundColor().getRed() != item2.getBackgroundColor().getRed()
                || item1.getBackgroundColor().getGreen() != item2.getBackgroundColor().getGreen()
                || item1.getBackgroundColor().getBlue() != item2.getBackgroundColor().getBlue()

        ) {
            return false;
        }

        return true;
    }

    public static boolean validateRGBColor(RGBColor item1, RGBColor item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getRed() != item2.getRed()
                || item1.getGreen() != item2.getGreen()
                || item1.getBlue() != item2.getBlue()) {
            return false;
        }

        return true;
    }

    public static boolean validateSupportedFormats(VideoStreamingFormat vsf1, VideoStreamingFormat vsf2) {
        if (vsf1.getProtocol() != vsf2.getProtocol()) {
            return false;
        }

        if (vsf1.getCodec() != vsf2.getCodec()) {
            return false;
        }

        return true;
    }

    public static boolean validateDisplayCapabilities(DisplayCapabilities item1, DisplayCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getDisplayType() == null) {
            return (item2.getDisplayType() == null);
        }

        if (item1.getDisplayType() != item2.getDisplayType()) {
            return false;
        }

        if (!CompareUtils.areStringsEqual(item1.getDisplayName(), item2.getDisplayName(), true, true)) {
            return false;
        }

        if (item1.getGraphicSupported() != item2.getGraphicSupported()) {
            return false;
        }
        // Failing past here:
        // log("GS", item1.getGraphicSupported() + " : " + item2.getGraphicSupported());

        if (!validateStringList(item1.getTemplatesAvailable(), item2.getTemplatesAvailable())) {
            log("TA", item1.getTemplatesAvailable() + " | " + item2.getTemplatesAvailable());
            return false;
        }

        if (item1.getNumCustomPresetsAvailable() != item2.getNumCustomPresetsAvailable()) {
            return false;
        }

        if (item1.getMediaClockFormats() == null) {
            return (item2.getMediaClockFormats() == null);
        }

        if (item1.getMediaClockFormats().size() != item2.getMediaClockFormats().size()) {
            return false;
        }

        for (int i = 0; i < item1.getMediaClockFormats().size(); i++) {
            if (item1.getMediaClockFormats().get(i) == null && item2.getMediaClockFormats().get(i) != null) {
                return false;
            }

            if (item1.getMediaClockFormats().get(i) != item2.getMediaClockFormats().get(i)) {
                return false;
            }
        }

        if (item1.getTextFields() == null) {
            return (item2.getTextFields() == null);
        }

        if (item1.getTextFields().size() != item2.getTextFields().size()) {
            return false;
        }

        for (int i = 0; i < item1.getTextFields().size(); i++) {
            if (item1.getTextFields().get(i) == null && item2.getTextFields().get(i) != null) {
                return false;
            }

            if (!validateTextFields(item1.getTextFields().get(i), item2.getTextFields().get(i))) {
                return false;
            }
        }

        if (item1.getImageFields() == null) {
            return (item2.getImageFields() == null);
        }

        if (item1.getImageFields().size() != item2.getImageFields().size()) {
            return false;
        }

        for (int i = 0; i < item1.getImageFields().size(); i++) {
            if (item1.getImageFields().get(i) == null && item2.getImageFields().get(i) != null) {
                return false;
            }

            if (!validateImageFields(item1.getImageFields().get(i), item2.getImageFields().get(i))) {
                return false;
            }
        }

        if (!validateScreenParams(item1.getScreenParams(), item2.getScreenParams())) {
            return false;
        }

        return true;
    }

    public static boolean validateDisplayCapabilityList(List<DisplayCapability> list1, List<DisplayCapability> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!validateDisplayCapability(list1.get(i), list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateDisplayCapability(DisplayCapability item1, DisplayCapability item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (!item1.getDisplayName().equals(item2.getDisplayName())) {
            return false;
        }

        if (item1.getWindowTypeSupported() == null) {
            return (item2.getWindowTypeSupported() == null);
        }

        if (item1.getWindowTypeSupported().size() != item2.getWindowTypeSupported().size()) {
            return false;
        }

        for (int i = 0; i < item1.getWindowTypeSupported().size(); i++) {
            if (item1.getWindowTypeSupported().get(i) == null && item2.getWindowTypeSupported().get(i) != null) {
                return false;
            }

            if (!validateWindowTypeCapabilities(item1.getWindowTypeSupported().get(i), item2.getWindowTypeSupported().get(i))) {
                return false;
            }
        }

        if (item1.getWindowCapabilities() == null) {
            return (item2.getWindowCapabilities() == null);
        }

        if (item1.getWindowCapabilities().size() != item2.getWindowCapabilities().size()) {
            return false;
        }

        for (int i = 0; i < item1.getWindowCapabilities().size(); i++) {
            if (item1.getWindowCapabilities().get(i) == null && item2.getWindowCapabilities().get(i) != null) {
                return false;
            }

            if (!validateWindowCapability(item1.getWindowCapabilities().get(i), item2.getWindowCapabilities().get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateWindowCapability(WindowCapability item1, WindowCapability item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getWindowID() != item2.getWindowID()) {
            return false;
        }

        if (!validateStringList(item1.getTemplatesAvailable(), item2.getTemplatesAvailable())) {
            log("TA", item1.getTemplatesAvailable() + " | " + item2.getTemplatesAvailable());
            return false;
        }

        if (item1.getNumCustomPresetsAvailable() != item2.getNumCustomPresetsAvailable()) {
            return false;
        }

        if (item1.getTextFields() == null) {
            return (item2.getTextFields() == null);
        }

        if (item1.getTextFields().size() != item2.getTextFields().size()) {
            return false;
        }

        for (int i = 0; i < item1.getTextFields().size(); i++) {
            if (item1.getTextFields().get(i) == null && item2.getTextFields().get(i) != null) {
                return false;
            }

            if (!validateTextFields(item1.getTextFields().get(i), item2.getTextFields().get(i))) {
                return false;
            }
        }

        if (item1.getImageFields() == null) {
            return (item2.getImageFields() == null);
        }

        if (item1.getImageFields().size() != item2.getImageFields().size()) {
            return false;
        }

        for (int i = 0; i < item1.getImageFields().size(); i++) {
            if (item1.getImageFields().get(i) == null && item2.getImageFields().get(i) != null) {
                return false;
            }

            if (!validateImageFields(item1.getImageFields().get(i), item2.getImageFields().get(i))) {
                return false;
            }
        }

        if (item1.getImageTypeSupported() == null) {
            return (item2.getImageTypeSupported() == null);
        }

        if (item1.getImageTypeSupported().size() != item2.getImageTypeSupported().size()) {
            return false;
        }

        for (int i = 0; i < item1.getImageTypeSupported().size(); i++) {
            if (item1.getImageTypeSupported().get(i) == null) {
                return (item2.getImageTypeSupported().get(i) == null);
            }

            if (item1.getImageTypeSupported().get(i) != item2.getImageTypeSupported().get(i)) {
                return false;
            }
        }

        if (!validateButtonCapabilities(item1.getButtonCapabilities(), item2.getButtonCapabilities())) {
            return false;
        }

        if (!validateSoftButtonCapabilities(item1.getSoftButtonCapabilities(), item2.getSoftButtonCapabilities())) {
            return false;
        }

        return true;
    }

    public static boolean validateButtonCapabilities(List<ButtonCapabilities> item1, List<ButtonCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i).getName() != item2.get(i).getName()) {
                return false;
            }
            if (item1.get(i).getUpDownAvailable() != item2.get(i).getUpDownAvailable()) {
                return false;
            }
            if (item1.get(i).getLongPressAvailable() != item2.get(i).getLongPressAvailable()) {
                return false;
            }
            if (item1.get(i).getShortPressAvailable() != item2.get(i).getShortPressAvailable()) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateSoftButtonCapabilities(List<SoftButtonCapabilities> item1, List<SoftButtonCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i).getImageSupported() != item2.get(i).getImageSupported()) {
                return false;
            }
            if (item1.get(i).getUpDownAvailable() != item2.get(i).getUpDownAvailable()) {
                return false;
            }
            if (item1.get(i).getLongPressAvailable() != item2.get(i).getLongPressAvailable()) {
                return false;
            }
            if (item1.get(i).getShortPressAvailable() != item2.get(i).getShortPressAvailable()) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateRadioControlCapabilities(List<RadioControlCapabilities> item1, List<RadioControlCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i).getModuleName() != item2.get(i).getModuleName()) {
                return false;
            }
            if (item1.get(i).getRadioEnableAvailable() != item2.get(i).getRadioEnableAvailable()) {
                return false;
            }
            if (item1.get(i).getRadioBandAvailable() != item2.get(i).getRadioBandAvailable()) {
                return false;
            }
            if (item1.get(i).getRadioFrequencyAvailable() != item2.get(i).getRadioFrequencyAvailable()) {
                return false;
            }
            if (item1.get(i).getHdChannelAvailable() != item2.get(i).getHdChannelAvailable()) {
                return false;
            }
            if (item1.get(i).getRdsDataAvailable() != item2.get(i).getRdsDataAvailable()) {
                return false;
            }
            if (item1.get(i).getAvailableHDsAvailable() != item2.get(i).getAvailableHDsAvailable()) {
                return false;
            }
            if (item1.get(i).getStateAvailable() != item2.get(i).getStateAvailable()) {
                return false;
            }
            if (item1.get(i).getSignalStrengthAvailable() != item2.get(i).getSignalStrengthAvailable()) {
                return false;
            }
            if (item1.get(i).getSignalChangeThresholdAvailable() != item2.get(i).getSignalChangeThresholdAvailable()) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateClimateControlCapabilities(List<ClimateControlCapabilities> item1, List<ClimateControlCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i).getModuleName() != item2.get(i).getModuleName()) {
                return false;
            }
            if (item1.get(i).getFanSpeedAvailable() != item2.get(i).getFanSpeedAvailable()) {
                return false;
            }
            if (item1.get(i).getDesiredTemperatureAvailable() != item2.get(i).getDesiredTemperatureAvailable()) {
                return false;
            }
            if (item1.get(i).getAcEnableAvailable() != item2.get(i).getAcEnableAvailable()) {
                return false;
            }
            if (item1.get(i).getAcMaxEnableAvailable() != item2.get(i).getAcMaxEnableAvailable()) {
                return false;
            }
            if (item1.get(i).getCirculateAirEnableAvailable() != item2.get(i).getCirculateAirEnableAvailable()) {
                return false;
            }
            if (item1.get(i).getAutoModeEnableAvailable() != item2.get(i).getAutoModeEnableAvailable()) {
                return false;
            }
            if (item1.get(i).getDualModeEnableAvailable() != item2.get(i).getDualModeEnableAvailable()) {
                return false;
            }
            if (item1.get(i).getDefrostZoneAvailable() != item2.get(i).getDefrostZoneAvailable()) {
                return false;
            }

            if (!(validateDefrostZones(item1.get(i).getDefrostZone(), item2.get(i).getDefrostZone()))) {
                return false;
            }

            if (item1.get(i).getVentilationModeAvailable() != item2.get(i).getVentilationModeAvailable()) {
                return false;
            }

            if (!(validateVentilationModes(item1.get(i).getVentilationMode(), item2.get(i).getVentilationMode()))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateSeatControlCapabilities(SeatControlCapabilities item1, SeatControlCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getModuleName() != item2.getModuleName()) {
            return false;
        }
        if (item1.getHeatingEnabledAvailable() != item2.getHeatingEnabledAvailable()) {
            return false;
        }
        if (item1.getCoolingEnabledAvailable() != item2.getCoolingEnabledAvailable()) {
            return false;
        }
        if (item1.getHeatingLevelAvailable() != item2.getHeatingLevelAvailable()) {
            return false;
        }
        if (item1.getCoolingLevelAvailable() != item2.getCoolingLevelAvailable()) {
            return false;
        }
        if (item1.getHorizontalPositionAvailable() != item2.getHorizontalPositionAvailable()) {
            return false;
        }
        if (item1.getVerticalPositionAvailable() != item2.getVerticalPositionAvailable()) {
            return false;
        }
        if (item1.getFrontVerticalPositionAvailable() != item2.getFrontVerticalPositionAvailable()) {
            return false;
        }
        if (item1.getBackVerticalPositionAvailable() != item2.getBackVerticalPositionAvailable()) {
            return false;
        }
        if (item1.getBackTiltAngleAvailable() != item2.getBackTiltAngleAvailable()) {
            return false;
        }
        if (item1.getHeadSupportVerticalPositionAvailable() != item2.getHeadSupportVerticalPositionAvailable()) {
            return false;
        }
        if (item1.getHeadSupportHorizontalPositionAvailable() != item2.getHeadSupportHorizontalPositionAvailable()) {
            return false;
        }
        if (item1.getMassageEnabledAvailable() != item2.getMassageEnabledAvailable()) {
            return false;
        }
        if (item1.getMassageModeAvailable() != item2.getMassageModeAvailable()) {
            return false;
        }
        if (item1.getMassageCushionFirmnessAvailable() != item2.getMassageCushionFirmnessAvailable()) {
            return false;
        }
        if (item1.getMemoryAvailable() != item2.getMemoryAvailable()) {
            return false;
        }

        return true;
    }

    public static boolean validateSeatControlCapabilitiesList(List<SeatControlCapabilities> item1, List<SeatControlCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!validateSeatControlCapabilities(item1.get(i), item2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateAudioControlCapabilities(AudioControlCapabilities item1, AudioControlCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getModuleName() != item2.getModuleName()) {
            return false;
        }
        if (item1.getSourceAvailable() != item2.getSourceAvailable()) {
            return false;
        }
        if (item1.getKeepContextAvailable() != item2.getKeepContextAvailable()) {
            return false;
        }
        if (item1.getVolumeAvailable() != item2.getVolumeAvailable()) {
            return false;
        }
        if (item1.getEqualizerAvailable() != item2.getEqualizerAvailable()) {
            return false;
        }
        if (item1.getEqualizerMaxChannelId() != item2.getEqualizerMaxChannelId()) {
            return false;
        }

        return true;
    }

    public static boolean validateEqualizerSettingsList(List<EqualizerSettings> item1, List<EqualizerSettings> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!validateEqualizerSettings(item1.get(i), item2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateEqualizerSettings(EqualizerSettings item1, EqualizerSettings item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getChannelId() != item2.getChannelId()) {
            return false;
        }

        if (item1.getChannelName() != item2.getChannelName()) {
            return false;
        }

        if (item1.getChannelSetting() != item2.getChannelSetting()) {
            return false;
        }

        return true;
    }

    public static boolean validateLightStateList(List<LightState> item1, List<LightState> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!validateLightState(item1.get(i), item2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateLightState(LightState item1, LightState item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getId() != item2.getId()) {
            return false;
        }

        if (item1.getStatus() != item2.getStatus()) {
            return false;
        }

        if (item1.getDensity() != item2.getDensity()) {
            return false;
        }

        if (!(validateRGBColor(item1.getColor(), item2.getColor()))) {
            return false;
        }

        return true;
    }

    public static boolean validateLightCapabilitiesList(List<LightCapabilities> item1, List<LightCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!validateLightCapabilities(item1.get(i), item2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateLightCapabilities(LightCapabilities item1, LightCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getName() != item2.getName()) {
            return false;
        }

        if (item1.getDensityAvailable() != item2.getDensityAvailable()) {
            return false;
        }

        if (item1.getRGBColorSpaceAvailable() != item2.getRGBColorSpaceAvailable()) {
            return false;
        }

        return true;
    }

    public static boolean validateAudioControlCapabilitiesList(List<AudioControlCapabilities> item1, List<AudioControlCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!validateAudioControlCapabilities(item1.get(i), item2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateDefrostZones(List<DefrostZone> item1, List<DefrostZone> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i) != item2.get(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateVentilationModes(List<VentilationMode> item1, List<VentilationMode> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i) != item2.get(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateTurnList(List<Turn> item1, List<Turn> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!item1.get(i).getNavigationText().equals(item2.get(i).getNavigationText())) {
                return false;
            }
            if (!validateImage(item1.get(i).getTurnIcon(), item2.get(i).getTurnIcon())) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateAudioPassThruCapabilities(List<AudioPassThruCapabilities> item1, List<AudioPassThruCapabilities> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (item1.get(i).getAudioType() != item2.get(i).getAudioType()) {
                return false;
            }
            if (item1.get(i).getBitsPerSample() != item2.get(i).getBitsPerSample()) {
                return false;
            }
            if (item1.get(i).getSamplingRate() != item2.get(i).getSamplingRate()) {
                return false;
            }
        }

        return true;
    }

    public static boolean validatePcmStreamCapabilities(AudioPassThruCapabilities item1, AudioPassThruCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getAudioType() != item2.getAudioType()) {
            return false;
        }

        if (item1.getBitsPerSample() != item2.getBitsPerSample()) {
            return false;
        }

        if (item1.getSamplingRate() != item2.getSamplingRate()) {
            return false;
        }

        return true;
    }

    public static boolean validatePresetBankCapabilities(PresetBankCapabilities item1, PresetBankCapabilities item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.onScreenPresetsAvailable() != item2.onScreenPresetsAvailable()) {
            return false;
        }

        return true;
    }

    public static boolean validateVehicleType(VehicleType item1, VehicleType item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getMake() != item2.getMake() ||
                item1.getModel() != item2.getModel() ||
                item1.getTrim() != item2.getTrim() ||
                item1.getModelYear() != item2.getModelYear()) {
            return false;
        }

        return true;
    }

    public static boolean validateKeyboardProperties(KeyboardProperties item1, KeyboardProperties item2) {
        if (item1 == null) {
            log("1", item2.toString());
            return (item2 == null);
        }
        if (item2 == null) {
            log("2", item1.toString());
            return (item1 == null);
        }

        if (!item1.getAutoCompleteText().equals(item2.getAutoCompleteText())) {
            log("ACT", item1.getAutoCompleteText().toString() + " | " + item2.getAutoCompleteText().toString());
            return false;
        }
        if (item1.getKeyboardLayout() != item2.getKeyboardLayout()) {
            log("KL", item1.getKeyboardLayout().toString() + " | " + item2.getKeyboardLayout().toString());
            return false;
        }
        if (item1.getKeypressMode() != item2.getKeypressMode()) {
            log("KM", item1.getKeypressMode() + " | " + item2.getKeypressMode());
            return false;
        }
        if (item1.getLanguage() != item2.getLanguage()) {
            log("L", item1.getLanguage().toString() + " | " + item2.getLanguage().toString());
            return false;
        }
        if (!validateStringList(item1.getLimitedCharacterList(), item2.getLimitedCharacterList())) {
            log("List", item1.getLimitedCharacterList().toString() + " | " + item2.getLimitedCharacterList().toString());
            return false;
        }

        return true;
    }

    public static boolean validateStartTime(StartTime item1, StartTime item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getHours() != item2.getHours()) {
            return false;
        }
        if (item1.getMinutes() != item2.getMinutes()) {
            return false;
        }
        if (item1.getSeconds() != item2.getSeconds()) {
            return false;
        }

        return true;
    }

    public static boolean validateVrHelpItems(List<VrHelpItem> item1, List<VrHelpItem> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!item1.get(i).getText().equals(item2.get(i).getText())) {
                return false;
            }
            if (!validateImage(item1.get(i).getImage(), item2.get(i).getImage())) {
                return false;
            }
            if ((int) item1.get(i).getPosition() != (int) item2.get(i).getPosition()) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateDIDResults(List<DIDResult> item1, List<DIDResult> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        for (int i = 0; i < item1.size(); i++) {
            if (!item1.get(i).getData().equals(item2.get(i).getData())) {
                return false;
            }
            if (item1.get(i).getResultCode() != item2.get(i).getResultCode()) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateHeaders(Headers item1, Headers item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (!item1.getDoInput().equals(item2.getDoInput())) {
            return false;
        }

        if (!item1.getDoOutput().equals(item2.getDoOutput())) {
            return false;
        }

        if (!item1.getInstanceFollowRedirects().equals(item2.getInstanceFollowRedirects())) {
            return false;
        }

        if (!item1.getUseCaches().equals(item2.getUseCaches())) {
            return false;
        }

        if (!item1.getCharset().equals(item2.getCharset())) {
            return false;
        }

        if (!item1.getConnectTimeout().equals(item2.getConnectTimeout())) {
            return false;
        }

        if (!item1.getContentLength().equals(item2.getContentLength())) {
            return false;
        }

        if (!item1.getContentType().equals(item2.getContentType())) {
            return false;
        }

        if (!item1.getReadTimeout().equals(item2.getReadTimeout())) {
            return false;
        }

        if (!item1.getRequestMethod().equals(item2.getRequestMethod())) {
            return false;
        }

        return true;
    }

    public static boolean validateHMICapabilities(HMICapabilities hmiA, HMICapabilities hmiB) {
        if (hmiA.isPhoneCallAvailable() != hmiB.isPhoneCallAvailable()) {
            return false;
        }

        if (hmiA.isVideoStreamingAvailable() != hmiB.isVideoStreamingAvailable()) {
            return false;
        }

        if (hmiA.isNavigationAvailable() != hmiB.isNavigationAvailable()) {
            return false;
        }

        return true;
    }

    public static boolean validateHMIZoneCapabilities(List<HmiZoneCapabilities> hmizA, List<HmiZoneCapabilities> hmizB) {
        for (int i = 0; i < hmizA.size(); i++) {
            if (!hmizA.get(i).equals(hmizB.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateSpeechCapabilities(List<SpeechCapabilities> spA, List<SpeechCapabilities> spB) {
        for (int i = 0; i < spA.size(); i++) {
            if (!spA.get(i).equals(spB.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validatePreRecordedSpeechCapabilities(List<PrerecordedSpeech> spA, List<PrerecordedSpeech> spB) {
        for (int i = 0; i < spA.size(); i++) {
            if (!spA.get(i).equals(spB.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateVideoStreamingFormat(VideoStreamingFormat a, VideoStreamingFormat b) {
        if (!a.getCodec().equals(b.getCodec())) {
            return false;
        }

        if (!a.getProtocol().equals(b.getProtocol())) {
            return false;
        }

        return true;
    }

    public static boolean validateVideoStreamingCapability(VideoStreamingCapability a, VideoStreamingCapability b) {
        if (!validateImageResolution(a.getPreferredResolution(), b.getPreferredResolution())) {
            return false;
        }

        if (!a.getMaxBitrate().equals(b.getMaxBitrate())) {
            return false;
        }

        for (int i = 0; i < a.getSupportedFormats().size(); i++) {
            if (!validateVideoStreamingFormat(a.getSupportedFormats().get(i), b.getSupportedFormats().get(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateGrid(Grid g1, Grid g2) {
        String tag = "validateGrid";
        if (g1 == null) {
            return (g2 == null);
        }
        if (g2 == null) {
            return (g1 == null);
        }
        if (g1.getCol() != g2.getCol()) {
            log(tag, "Columns do not match");
            return false;
        }
        if (g1.getRow() != g2.getRow()) {
            log(tag, "Rows do not match");
            return false;
        }
        if (g1.getLevel() != g2.getLevel()) {
            log(tag, "Levels do not match");
            return false;
        }
        if (g1.getColSpan() != g2.getColSpan()) {
            log(tag, "Column spans do not match");
            return false;
        }
        if (g1.getRowSpan() != g2.getRowSpan()) {
            log(tag, "Row spans do not match");
            return false;
        }
        if (g1.getLevelSpan() != g2.getLevelSpan()) {
            log(tag, "Level spans do not match");
            return false;
        }

        return true;
    }

    public static boolean validateModuleInfo(ModuleInfo m1, ModuleInfo m2) {
        if (m1 == null) {
            return (m2 == null);
        }
        if (m2 == null) {
            return (m1 == null);
        }
        if (!m1.getModuleId().equals(m2.getModuleId())) {
            return false;
        }
        if (!m1.getMultipleAccessAllowance().equals(m2.getMultipleAccessAllowance())) {
            return false;
        }
        if (!validateGrid(m1.getModuleLocation(), m2.getModuleLocation())) {
            return false;
        }
        if (!validateGrid(m1.getModuleServiceArea(), m2.getModuleServiceArea())) {
            return false;
        }

        return true;
    }

    public static boolean validateSeatLocation(SeatLocation cap1, SeatLocation cap2) {
        if (cap1 == null) {
            return (cap2 == null);
        }
        if (cap2 == null) {
            return (cap1 == null);
        }
        return validateGrid(cap1.getGrid(), cap2.getGrid());
    }

    public static boolean validateGearStatuses(GearStatus status1, GearStatus status2) {
        if (status1 == null) {
            return (status2 == null);
        }
        if (status2 == null) {
            return (status1 == null);
        }

        PRNDL actualGear1 = status1.getActualGear();
        PRNDL actualGear2 = status2.getActualGear();

        TransmissionType transmissionType1 = status1.getTransmissionType();
        TransmissionType transmissionType2 = status2.getTransmissionType();

        PRNDL userSelectedGear1 = status1.getUserSelectedGear();
        PRNDL userSelectedGear2 = status2.getUserSelectedGear();

        return actualGear1.equals(actualGear2)
                && transmissionType1.equals(transmissionType2)
                && userSelectedGear1.equals(userSelectedGear2);
    }

    public static boolean validateWindowStatuses(List<WindowStatus> item1, List<WindowStatus> item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.size() != item2.size()) {
            return false;
        }

        Iterator<WindowStatus> iterator1 = item1.iterator();
        Iterator<WindowStatus> iterator2 = item2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            WindowStatus windowStatus1 = iterator1.next();
            WindowStatus windowStatus2 = iterator2.next();

            if (!validateWindowStatus(windowStatus1, windowStatus2)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateWindowStatus(WindowStatus item1, WindowStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (!validateWindowStates(item1.getState(), item2.getState())) {
            return false;
        }
        if (!validateGrid(item1.getLocation(), item2.getLocation())) {
            return false;
        }

        return true;
    }

    public static boolean validateWindowStates(WindowState item1, WindowState item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        Integer approxPosition1 = item1.getApproximatePosition();
        Integer approxPosition2 = item2.getApproximatePosition();

        Integer deviation1 = item1.getDeviation();
        Integer deviation2 = item2.getDeviation();


        return approxPosition1.equals(approxPosition2) && deviation1.equals(deviation2);
    }

    public static boolean validateSeatOccupancies(SeatOccupancy item1, SeatOccupancy item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        List<SeatStatus> seatsBelted1 = item1.getSeatsBelted();
        List<SeatStatus> seatsOccupied1 = item1.getSeatsOccupied();

        List<SeatStatus> seatsBelted2 = item2.getSeatsBelted();
        List<SeatStatus> seatsOccupied2 = item2.getSeatsOccupied();

        return validateSeatStatuses(seatsBelted1, seatsBelted2) && validateSeatStatuses(seatsOccupied1, seatsOccupied2);
    }

    public static boolean validateStabilityControlStatus(StabilityControlsStatus status1, StabilityControlsStatus status2) {
        if (status1 == null) {
            return (status2 == null);
        }
        if (status2 == null) {
            return (status2 == null);
        }
        return status1.getEscSystem().equals(status2.getEscSystem()) && status1.getTrailerSwayControl().equals(status2.getTrailerSwayControl());
    }

    public static boolean validateStabilityControlStatus(VehicleDataResult status1, VehicleDataResult status2) {
        if (status1 == null) {
            return (status2 == null);
        }
        if (status2 == null) {
            return (status2 == null);
        }
        return status1.getDataType().equals(status2.getDataType()) && status1.getResultCode().equals(status2.getResultCode());
    }

    public static boolean validateKeyboardLayoutCapability(KeyboardLayoutCapability keyboard1, KeyboardLayoutCapability keyboard2) {
        if (keyboard1 == null) {
            return (keyboard2 == null);
        }
        if (keyboard2 == null) {
            return (keyboard1 == null);
        }
        return keyboard1.getKeyboardLayout().equals(keyboard2.getKeyboardLayout()) && keyboard1.getNumConfigurableKeys().equals(keyboard2.getNumConfigurableKeys());
    }

    public static boolean validateKeyboardCapabilities(KeyboardCapabilities keyboardCapabilities1, KeyboardCapabilities keyboardCapabilities2) {
        if (keyboardCapabilities1 == null) {
            return (keyboardCapabilities2 == null);
        }
        if (keyboardCapabilities2 == null) {
            return (keyboardCapabilities1 == null);
        }

        boolean keyboardsEqual = true;
        if (keyboardCapabilities1.getSupportedKeyboards() != null && keyboardCapabilities2.getSupportedKeyboards() != null) {

            for (KeyboardLayoutCapability keyboardLayoutCapability1 : keyboardCapabilities1.getSupportedKeyboards()) {
                for (KeyboardLayoutCapability keyboardLayoutCapability2 : keyboardCapabilities2.getSupportedKeyboards()) {
                    if (!validateKeyboardLayoutCapability(keyboardLayoutCapability1, keyboardLayoutCapability2)) {
                        keyboardsEqual = false;
                        break;
                    }
                }
                if (!keyboardsEqual) {
                    break;
                }
            }
        }

        return keyboardsEqual && keyboardCapabilities1.getMaskInputCharactersSupported().equals(keyboardCapabilities2.getMaskInputCharactersSupported());
    }

    public static boolean validateSeatStatuses(List<SeatStatus> seatStatusesItem1, List<SeatStatus> seatStatusesItem2) {
        if (seatStatusesItem1 == null) {
            return (seatStatusesItem2 == null);
        }
        if (seatStatusesItem2 == null) {
            return (seatStatusesItem1 == null);
        }

        if (seatStatusesItem1.size() != seatStatusesItem2.size()) {
            return false;
        }

        Iterator<SeatStatus> iterator1 = seatStatusesItem1.iterator();
        Iterator<SeatStatus> iterator2 = seatStatusesItem2.iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {
            SeatStatus seatStatus1 = iterator1.next();
            SeatStatus seatStatus2 = iterator2.next();

            if (!validateSeatStatus(seatStatus1, seatStatus2)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateSeatStatus(SeatStatus item1, SeatStatus item2) {
        if (item1 == null) {
            return (item2 == null);
        }
        if (item2 == null) {
            return (item1 == null);
        }

        if (item1.getConditionActive() != item2.getConditionActive()) {
            return false;
        }

        return validateSeatLocation(item1.getSeatLocation(), item2.getSeatLocation());
    }

    public static boolean validateDoorStatus(DoorStatus status1, DoorStatus status2) {
        if (status1 == null) {
            return (status2 == null);
        }
        if (status2 == null) {
            return (status2 == null);
        }
        boolean gridValidated = validateGrid(status1.getLocation(), status2.getLocation());
        return gridValidated && status1.getStatus().equals(status2.getStatus());
    }

    public static boolean validateGateStatus(GateStatus status1, GateStatus status2) {
        if (status1 == null) {
            return (status2 == null);
        }
        if (status2 == null) {
            return (status2 == null);
        }
        boolean gridValidated = validateGrid(status1.getLocation(), status2.getLocation());
        return gridValidated && status1.getStatus().equals(status2.getStatus());
    }

    public static boolean validateRoofStatus(RoofStatus status1, RoofStatus status2) {
        if (status1 == null) {
            return (status2 == null);
        }
        if (status2 == null) {
            return (status2 == null);
        }
        boolean gridValidated = validateGrid(status1.getLocation(), status2.getLocation());
        return gridValidated && status1.getStatus().equals(status2.getStatus()) && validateWindowStates(status1.getState(), status2.getState());
    }

    public static boolean validateClimateData(ClimateData climateData1, ClimateData climateData2) {
        if (climateData1 == null) {
            return (climateData2 == null);
        }
        if (climateData2 == null) {
            return (climateData1 == null);
        }

        if (!validateTemperature(climateData1.getExternalTemperature(), climateData2.getExternalTemperature())) {
            return false;
        }

        if (!validateTemperature(climateData1.getCabinTemperature(), climateData2.getCabinTemperature())) {
            return false;
        }

        return climateData1.getAtmosphericPressure().floatValue() == climateData2.getAtmosphericPressure().floatValue();
    }
}
