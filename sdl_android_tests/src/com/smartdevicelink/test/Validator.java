package com.smartdevicelink.test;

import java.util.Iterator;
import java.util.List;

import com.smartdevicelink.protocol.enums.FrameData;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.AirbagStatus;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.BeltStatus;
import com.smartdevicelink.proxy.rpc.BodyInformation;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.ClusterModeStatus;
import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.proxy.rpc.DeviceStatus;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ECallInfo;
import com.smartdevicelink.proxy.rpc.EmergencyEvent;
import com.smartdevicelink.proxy.rpc.GPSData;
import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.HeadLampStatus;
import com.smartdevicelink.proxy.rpc.Headers;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.MyKey;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.TireStatus;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.proxy.rpc.VehicleDataResult;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

public class Validator{

    // TODO: This class could be (mostly) eliminated if all RPC classes implement their own .equals() and .hashCode() methods.
	// Some special methods do exist for pseudo-enums (FrameType, SessionType, FrameData, FrameDataControlFrame)
	
    private Validator(){}

    public static boolean validateMenuParams(MenuParams menuParams1, MenuParams menuParams2){
        if(menuParams1 == null){
            return ( menuParams2 == null );
        }
        if(menuParams2 == null){
            return ( menuParams1 == null );
        }

        return ( menuParams1.getMenuName().equals(menuParams2.getMenuName())
                && menuParams1.getParentID() == menuParams2.getParentID() && menuParams1.getPosition() == menuParams2
                .getPosition() );
    }
    
    public static boolean validateVehicleDataResult(VehicleDataResult result1, VehicleDataResult result2){
        if(result1 == null){
            return ( result2 == null );
        }
        if(result2 == null){
            return ( result1 == null );
        }

        return ( result1.getDataType().equals(result2.getDataType())
                && result1.getResultCode().equals(result2.getResultCode()) );
    }

    public static boolean validateBulkData(byte[] array1, byte[] array2){
        if(array1 == null){
            return ( array2 == null );
        }
        if(array2 == null){
            return ( array1 == null );
        }

        if(array1.length != array2.length){
            return false;
        }

        for(int i = 0; i < array1.length; i++){
            if(array1[i] != array2[i]){
                return false;
            }
        }

        return true;
    }
    
    public static boolean validateFrameTypeArray (FrameType[] array1, FrameType[] array2) {
    	
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
    
    public static boolean validateSessionTypeArray (SessionType[] array1, SessionType[] array2) {
    	
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
    
    public static boolean validateFrameDataControlFrameTypeArray (FrameDataControlFrameType[] array1, FrameDataControlFrameType[] array2) {
    	
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
    
    public static boolean validateFrameDataArray (FrameData[] array1, FrameData[] array2) {
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

    public static boolean validateImage(Image image1, Image image2){
        if(image1 == null){
            return ( image2 == null );
        }
        if(image2 == null){
            return ( image1 == null );
        }

        if(!( image1.getValue().equals(image2.getValue()) )){
            log("validateImage",
                    "image1 name \"" + image1.getValue() + "\" didn't match image2 name \"" + image2.getValue() + "\".");
            return false;
        }

        if(image1.getImageType() != image2.getImageType()){
            log("validateImage",
                    "image1 type \"" + image1.getImageType() + "\" didn't match image2 type \"" + image2.getImageType()
                            + "\".");
            return false;
        }

        return true;
    }

    public static boolean validateStringList(List<String> vrCommands1, List<String> vrCommands2){
        if(vrCommands1 == null){
            return ( vrCommands2 == null );
        }
        if(vrCommands2 == null){
            return ( vrCommands1 == null );
        }

        for(int i = 0; i < vrCommands1.size(); i++){
            if(!vrCommands1.get(i).equals(vrCommands2.get(i))){
                return false;
            }
        }

        return true;
    }
    
    public static boolean validateIntegerList(List<Integer> intList1, List<Integer> intList2){
        if(intList1 == null){
            return ( intList2 == null );
        }
        if(intList2 == null){
            return ( intList1 == null );
        }

        for(int i = 0; i < intList1.size(); i++){
            if(!intList1.get(i).equals(intList2.get(i))){
                return false;
            }
        }

        return true;
    }
    
    public static boolean validateLongList(List<Long> intList1, List<Long> intList2){
        if(intList1 == null){
            return ( intList2 == null );
        }
        if(intList2 == null){
            return ( intList1 == null );
        }

        for(int i = 0; i < intList1.size(); i++){
            if(!intList1.get(i).equals(intList2.get(i))){
                return false;
            }
        }

        return true;
    }

    public static boolean validateSoftButtons(List<SoftButton> list1, List<SoftButton> list2){
        if(list1 == null){
            return ( list2 == null );
        }
        if(list2 == null){
            return ( list1 == null );
        }

        Iterator<SoftButton> iterator1 = list1.iterator();
        Iterator<SoftButton> iterator2 = list2.iterator();

        while(iterator1.hasNext() && iterator2.hasNext()){
            SoftButton button1 = iterator1.next();
            SoftButton button2 = iterator2.next();

            if(!validateImage(button1.getImage(), button2.getImage())
                    || !validateText(button1.getText(), button2.getText())
                    || button1.getIsHighlighted() != button2.getIsHighlighted()
                    || button1.getSoftButtonID() != button2.getSoftButtonID()
                    || button1.getSystemAction() != button2.getSystemAction() || button1.getType() != button2.getType()){
                return false;
            }
        }

        return true;
    }

    public static boolean validateTtsChunks(List<TTSChunk> list1, List<TTSChunk> list2){
        if(list1 == null){
            return ( list2 == null );
        }
        if(list2 == null){
            return ( list1 == null );
        }

        Iterator<TTSChunk> iterator1 = list1.iterator();
        Iterator<TTSChunk> iterator2 = list2.iterator();

        while(iterator1.hasNext() && iterator2.hasNext()){
            TTSChunk chunk1 = iterator1.next();
            TTSChunk chunk2 = iterator2.next();

            if(!validateText(chunk1.getText(), chunk2.getText()) || chunk1.getType() != chunk2.getType()){
                return false;
            }
        }

        return true;
    }

    public static boolean validateChoice(Choice choice1, Choice choice2){
        if(choice1 == null){
            return ( choice2 == null );
        }
        if(choice2 == null){
            return ( choice1 == null );
        }

        if(!( validateImage(choice1.getImage(), choice2.getImage()) )){
            log("validateChoice", "choice1 image \"" + choice1.getImage() + "\" didn't match choice2 image \""
                    + choice2.getImage() + "\"");
            return false;
        }
        if(!( validateImage(choice1.getSecondaryImage(), choice2.getSecondaryImage()) )){
            log("validateChoice", "choice1 secondary image \"" + choice1.getSecondaryImage()
                    + "\" didn't match choice2 image \"" + choice2.getSecondaryImage() + "\"");
            return false;
        }
        if(choice1.getChoiceID() != choice2.getChoiceID()){
            log("validateChoice",
                    "choice1 ID \"" + choice1.getChoiceID() + "\" didn't match choice2 ID \"" + choice2.getChoiceID()
                            + "\"");
            return false;
        }
        if(!( validateText(choice1.getMenuName(), choice2.getMenuName()) )){
            log("validateChoice", "choice1 menu name \"" + choice1.getMenuName()
                    + "\" didn't match choice2 menu name \"" + choice2.getMenuName() + "\"");
            return false;
        }
        if(!( validateText(choice1.getSecondaryText(), choice2.getSecondaryText()) )){
            log("validateChoice", "choice1 secondary text \"" + choice1.getSecondaryText()
                    + "\" didn't match choice2 secondary text \"" + choice2.getSecondaryText() + "\"");
            return false;
        }
        if(!( validateText(choice1.getTertiaryText(), choice2.getTertiaryText()) )){
            log("validateChoice", "choice1 tertiary text \"" + choice1.getTertiaryText()
                    + "\" didn't match choice2 tertiary text \"" + choice2.getTertiaryText() + "\"");
            return false;
        }
        if(!( validateStringList(choice1.getVrCommands(), choice2.getVrCommands()) )){
            log("validateChoice", "choice1 VR commands \"" + choice1.getVrCommands()
                    + "\" didn't match choice2 VR commands \"" + choice2.getVrCommands() + "\"");
            return false;
        }

        return true;
    }

    public static boolean validateText(String text1, String text2){
        if(text1 == null){
            return ( text2 == null );
        }
        if(text2 == null){
            return ( text1 == null );
        }

        return text1.equals(text2);
    }

    public static boolean validateScreenParams(ScreenParams params1, ScreenParams params2){
        if(params1 == null){
            return ( params2 == null );
        }
        if(params2 == null){
            return ( params1 == null );
        }

        if(!( validateImageResolution(params1.getImageResolution(), params2.getImageResolution()) )){
            log("validateScreenParams", "Image resolutions didn't match!");
            return false;
        }

        if(!( validateTouchEventCapabilities(params1.getTouchEventAvailable(), params2.getTouchEventAvailable()) )){
            log("validateScreenParams", "Touch event capabilities didn't match!");
            return false;
        }

        return true;
    }
    
    public static boolean validateImageResolution(ImageResolution image1, ImageResolution image2){
        if(image1 == null){
            return ( image2 == null );
        }
        if(image2 == null){
            return ( image1 == null );
        }

        if((int) image1.getResolutionHeight() != (int) image2.getResolutionHeight()){
            log("validateImageResolution",
                    "Height " + image1.getResolutionHeight() + " didn't match height " + image2.getResolutionHeight()
                            + ".");
            return false;
        }

        if((int) image1.getResolutionWidth() != (int) image2.getResolutionWidth()){
            log("validateImageResolution",
                    "Width " + image1.getResolutionWidth() + " didn't match width " + image2.getResolutionWidth() + ".");
            return false;
        }

        return true;
    }

    public static boolean validateTouchEventCapabilities(TouchEventCapabilities item1, TouchEventCapabilities item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getPressAvailable() != item2.getPressAvailable()){
            log("validateTouchEventCapabilities", "Press available \"" + item1.getPressAvailable()
                    + "\" didn't match press available \"" + item1.getPressAvailable() + "\".");
            return false;
        }

        if(item1.getDoublePressAvailable() != item2.getDoublePressAvailable()){
            log("validateTouchEventCapabilities", "Double press available \"" + item1.getDoublePressAvailable()
                    + "\" didn't match double press available \"" + item1.getDoublePressAvailable() + "\".");
            return false;
        }

        if(item1.getMultiTouchAvailable() != item2.getMultiTouchAvailable()){
            log("validateTouchEventCapabilities", "Multi-touch available \"" + item1.getMultiTouchAvailable()
                    + "\" didn't match multi-touch available \"" + item1.getMultiTouchAvailable() + "\".");
            return false;
        }

        return true;
    }

    public static boolean validateTextFields(TextField item1, TextField item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getCharacterSet() != item2.getCharacterSet()){
            return false;
        }
        if(item1.getName() != item2.getName()){
            return false;
        }
        if(item1.getRows() != item2.getRows()){
            return false;
        }
        if(item1.getWidth() != item2.getWidth()){
            return false;
        }

        return true;
    }

    public static boolean validateImageFields(ImageField item1, ImageField item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }
        if(! (validateFileTypes(item1.getImageTypeSupported(), item2.getImageTypeSupported()) )){
            return false;
        }
        if(item1.getName() != item2.getName()){
            return false;
        }
        if(!( validateImageResolution(item1.getImageResolution(), item2.getImageResolution()) )){
            return false;
        }

        return true;
    }
    
    public static boolean validateFileTypes (List<FileType> item1, List<FileType> item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
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

    public static boolean validateGpsData(GPSData item1, GPSData item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getActual() != item2.getActual()){
            return false;
        }
        if(item1.getAltitude() != item2.getAltitude()){
            return false;
        }
        if(item1.getCompassDirection() != item2.getCompassDirection()){
            return false;
        }
        if(item1.getDimension() != item2.getDimension()){
            return false;
        }
        if(item1.getHdop() != item2.getHdop()){
            return false;
        }
        if(item1.getHeading() != item2.getHeading()){
            return false;
        }
        if(item1.getLatitudeDegrees() != item2.getLatitudeDegrees()){
            return false;
        }
        if(item1.getLongitudeDegrees() != item2.getLongitudeDegrees()){
            return false;
        }
        if(item1.getPdop() != item2.getPdop()){
            return false;
        }
        if(item1.getSatellites() != item2.getSatellites()){
            return false;
        }
        if(item1.getSpeed() != item2.getSpeed()){
            return false;
        }
        if(item1.getUtcDay() != item2.getUtcDay()){
            return false;
        }
        if(item1.getUtcHours() != item2.getUtcHours()){
            return false;
        }
        if(item1.getUtcMinutes() != item1.getUtcMinutes()){
            return false;
        }
        if(item1.getUtcMonth() != item2.getUtcMonth()){
            return false;
        }
        if(item1.getUtcSeconds() != item2.getUtcSeconds()){
            return false;
        }
        if(item1.getUtcYear() != item2.getUtcYear()){
            return false;
        }
        if(item1.getVdop() != item2.getVdop()){
            return false;
        }

        return true;
    }

    public static boolean validateTireStatus(TireStatus item1, TireStatus item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );                
        }

        if(item1.getPressureTellTale() != item2.getPressureTellTale()){
            return false;
        }
        
        SingleTireStatus item1Status = item1.getLeftFront();
        SingleTireStatus item2Status = item2.getLeftFront();
        boolean isEqual = validateSingleTireStatus(item1Status, item2Status);
        if(!isEqual) return false;
        item1Status = item1.getRightFront();
        item2Status = item2.getRightFront();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if(!isEqual) return false;
        item1Status = item1.getLeftRear();
        item2Status = item2.getLeftRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if(!isEqual) return false;
        item1Status = item1.getRightRear();
        item2Status = item2.getRightRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if(!isEqual) return false;
        item1Status = item1.getInnerLeftRear();
        item2Status = item2.getInnerLeftRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if(!isEqual) return false;
        item1Status = item1.getInnerRightRear();
        item2Status = item2.getInnerRightRear();
        isEqual = validateSingleTireStatus(item1Status, item2Status);
        if(!isEqual) return false;
        
        return true;
    }

    public static boolean validateSingleTireStatus(SingleTireStatus item1, SingleTireStatus item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getStatus() != item2.getStatus()){
            return false;
        }

        return true;
    }

    public static boolean validateBeltStatus(BeltStatus item1, BeltStatus item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getLeftRearInflatableBelted() != item2.getLeftRearInflatableBelted()){
            return false;
        }
        if(item1.getRightRearInflatableBelted() != item2.getRightRearInflatableBelted()){
            return false;
        }
        if(item1.getPassengerChildDetected() != item2.getPassengerChildDetected()){
            return false;
        }
        if(item1.getDriverBuckleBelted() != item2.getDriverBuckleBelted()){
            return false;
        }
        if(item1.getPassengerBuckleBelted() != item2.getPassengerBuckleBelted()){
            return false;
        }
        if(item1.getRightRow2BuckleBelted() != item2.getRightRow2BuckleBelted()){
            return false;
        }
        if(item1.getMiddleRow1BuckleBelted() != item2.getMiddleRow1BuckleBelted()){
            return false;
        }
        if(item1.getLeftRow2BuckleBelted() != item2.getLeftRow2BuckleBelted()){
            return false;
        }
        if(item1.getMiddleRow2BuckleBelted() != item2.getMiddleRow2BuckleBelted()){
            return false;
        }
        if(item1.getMiddleRow3BuckleBelted() != item2.getMiddleRow3BuckleBelted()){
            return false;
        }
        if(item1.getLeftRow3BuckleBelted() != item2.getLeftRow3BuckleBelted()){
            return false;
        }
        if(item1.getRightRow3BuckleBelted() != item2.getRightRow3BuckleBelted()){
            return false;
        }
        if(item1.getPassengerBeltDeployed() != item2.getPassengerBeltDeployed()){
            return false;
        }
        if(item1.getMiddleRow1BeltDeployed() != item2.getMiddleRow1BeltDeployed()){
            return false;
        }

        return true;
    }

    public static boolean validateBodyInformation(BodyInformation item1, BodyInformation item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getIgnitionStatus() != item2.getIgnitionStatus()){
            return false;
        }
        if(item1.getIgnitionStableStatus() != item2.getIgnitionStableStatus()){
            return false;
        }
        if(item1.getParkBrakeActive() != item2.getParkBrakeActive()){
            return false;
        }
        if(item1.getDriverDoorAjar() != item2.getDriverDoorAjar()){
            return false;
        }
        if(item1.getPassengerDoorAjar() != item2.getPassengerDoorAjar()){
            return false;
        }
        if(item1.getRearLeftDoorAjar() != item2.getRearLeftDoorAjar()){
            return false;
        }
        if(item1.getRearRightDoorAjar() != item2.getRearRightDoorAjar()){
            return false;
        }

        return true;
    }

    public static boolean validateDeviceStatus(DeviceStatus item1, DeviceStatus item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getBtIconOn() != item2.getBtIconOn()){
            return false;
        }
        if(item1.getCallActive() != item2.getCallActive()){
            return false;
        }
        if(item1.getECallEventActive() != item2.getECallEventActive()){
            return false;
        }
        if(item1.getMonoAudioOutputMuted() != item2.getMonoAudioOutputMuted()){
            return false;
        }
        if(item1.getPhoneRoaming() != item2.getPhoneRoaming()){
            return false;
        }
        if(item1.getStereoAudioOutputMuted() != item2.getStereoAudioOutputMuted()){
            return false;
        }
        if(item1.getTextMsgAvailable() != item2.getTextMsgAvailable()){
            return false;
        }
        if(item1.getVoiceRecOn() != item2.getVoiceRecOn()){
            return false;
        }
        if(item1.getBattLevelStatus() != item2.getBattLevelStatus()){
            return false;
        }
        if(item1.getPrimaryAudioSource() != item2.getPrimaryAudioSource()){
            return false;
        }
        if(item1.getSignalLevelStatus() != item2.getSignalLevelStatus()){
            return false;
        }

        return true;
    }

    public static boolean validateHeadLampStatus(HeadLampStatus item1, HeadLampStatus item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getHighBeamsOn() != item2.getHighBeamsOn()){
            return false;
        }
        if(item1.getLowBeamsOn() != item2.getLowBeamsOn()){
            return false;
        }
        if(item1.getAmbientLightStatus() != item2.getAmbientLightStatus()){
            return false;
        }

        return true;
    }

    public static boolean validateECallInfo(ECallInfo item1, ECallInfo item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getAuxECallNotificationStatus() != item2.getAuxECallNotificationStatus()){
            return false;
        }
        if(item1.getECallConfirmationStatus() != item2.getECallConfirmationStatus()){
            return false;
        }
        if(item1.getECallNotificationStatus() != item2.getECallNotificationStatus()){
            return false;
        }

        return true;
    }

    public static boolean validateAirbagStatus(AirbagStatus item1, AirbagStatus item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }
        if(item1.getDriverAirbagDeployed() != item2.getDriverAirbagDeployed()){
            return false;
        }
        if(item1.getDriverSideAirbagDeployed() != item2.getDriverSideAirbagDeployed()){
            return false;
        }
        if(item1.getDriverCurtainAirbagDeployed() != item2.getDriverCurtainAirbagDeployed()){
            return false;
        }
        if(item1.getPassengerAirbagDeployed() != item2.getPassengerAirbagDeployed()){
            return false;
        }
        if(item1.getPassengerCurtainAirbagDeployed() != item2.getPassengerCurtainAirbagDeployed()){
            return false;
        }
        if(item1.getDriverKneeAirbagDeployed() != item2.getDriverKneeAirbagDeployed()){
            return false;
        }
        if(item1.getPassengerSideAirbagDeployed() != item2.getPassengerSideAirbagDeployed()){
            return false;
        }
        if(item1.getPassengerKneeAirbagDeployed() != item2.getPassengerKneeAirbagDeployed()){
            return false;
        }

        return true;
    }

    public static boolean validateEmergencyEvent(EmergencyEvent item1, EmergencyEvent item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getEmergencyEventType() != item2.getEmergencyEventType()){
            return false;
        }
        if(item1.getFuelCutoffStatus() != item2.getFuelCutoffStatus()){
            return false;
        }
        if(item1.getMaximumChangeVelocity() != item2.getMaximumChangeVelocity()){
            return false;
        }
        if(item1.getMultipleEvents() != item2.getMultipleEvents()){
            return false;
        }
        if(item1.getRolloverEvent() != item2.getRolloverEvent()){
            return false;
        }

        return true;
    }

    public static boolean validateClusterModeStatus(ClusterModeStatus item1, ClusterModeStatus item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getPowerModeActive() != item2.getPowerModeActive()){
            return false;
        }
        if(item1.getPowerModeQualificationStatus() != item2.getPowerModeQualificationStatus()){
            return false;
        }
        if(item1.getPowerModeStatus() != item2.getPowerModeStatus()){
            return false;
        }
        if(item1.getCarModeStatus() != item2.getCarModeStatus()){
            return false;
        }

        return true;
    }

    public static boolean validateMyKey(MyKey item1, MyKey item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }

        if(item1.getE911Override() != item2.getE911Override()){
            return false;
        }

        return true;
    }

    public static boolean validatePermissionItem(PermissionItem item1, PermissionItem item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }
        /*
        if(item1 == item2){
            log("validatePermissionItem", "Items are the same object.  No defensive copy took place.");
            return false;
        }
		*/
        if(!item1.getRpcName().equals(item2.getRpcName())){
            return false;
        }

        if(!validateHmiPermissions(item1.getHMIPermissions(), item2.getHMIPermissions())){
            return false;
        }

        if(!validateParameterPermissions(item1.getParameterPermissions(), item2.getParameterPermissions())){
            return false;
        }

        return true;
    }

    public static boolean validateHmiPermissions(HMIPermissions item1, HMIPermissions item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }
        /*
        if(item1 == item2){
            log("validateHmiPermissions", "Items are the same object.  No defensive copy took place.");
            return false;
        }
         */
        if(!validateHmiLevelLists(item1.getAllowed(), item2.getAllowed())){
            return false;
        }

        List<HMILevel> item1Array = item1.getUserDisallowed();
        List<HMILevel> item2Array = item2.getUserDisallowed();

        if(item1Array.size() != item2Array.size()){
            return false;
        }

        for(int i = 0; i < item1Array.size(); i++){
            if(item1Array.get(i) != item2Array.get(i)){
                return false;
            }
        }

        return true;
    }
    
    public static boolean validateHmiLevelLists(List<HMILevel> item1Array, List<HMILevel> item2Array) {
        if(item1Array.size() != item2Array.size()){
            return false;
        }

        for(int i = 0; i < item1Array.size(); i++){
            if(item1Array.get(i) != item2Array.get(i)){
                return false;
            }
        }
        return true;
    }

    public static boolean validateParameterPermissions(ParameterPermissions item1, ParameterPermissions item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }
        /*
        if(item1 == item2){
            log("validateParameterPermissions", "Items are the same object.  No defensive copy took place.");
            return false;
        }
		*/
        if(!validateStringList(item1.getAllowed(), item2.getAllowed())){
            return false;
        }

        if(!validateStringList(item1.getUserDisallowed(), item2.getUserDisallowed())){
            return false;
        }

        return true;
    }
    
    public static boolean validateTouchEvent(TouchEvent item1, TouchEvent item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }
        
        if(item1 == item2){
            return true;
        }
        
        List<TouchCoord> tc1 = item1.getC();
        List<TouchCoord> tc2 = item2.getC();
        List<Long> ts1 = item1.getTs();
        List<Long> ts2 = item2.getTs();
        
        for(int i=0; i<tc1.size(); i++){
            if( ! validateTouchCoord(tc1.get(i), tc2.get(i)) ){
                return false;
            }
            if(ts1.get(i) != ts2.get(i)){
                return false;
            }
        }
        
        if(item1.getId() != item2.getId()){
            return false;
        }
        
        
        return true;
    }
    
    public static boolean validateTouchCoord(TouchCoord item1, TouchCoord item2){
        if(item1 == null){
            return ( item2 == null );
        }
        if(item2 == null){
            return ( item1 == null );
        }
        
        if(item1.getX() != item2.getX()){
            return false;
        }
        if(item1.getY() != item2.getY()){
            return false;
        }
        
        return true;
    }

    public static void log(String tag, String msg){
        Logger.log(tag, msg);
    }
    
    public static boolean validateSdlMsgVersion (SdlMsgVersion item1, SdlMsgVersion item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
    	}
    	
    	if (item1.getMajorVersion() != item2.getMajorVersion() ||
    		item1.getMinorVersion() != item2.getMinorVersion()) {
    		return false;
    	}
    	
    	return true;
    }
    
    public static boolean validateDeviceInfo (DeviceInfo item1, DeviceInfo item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
    	}
    	
    	if (item1.getOs()                   != item2.getOs()          ||
    		item1.getCarrier()              != item2.getCarrier()     ||    		
    		item1.getHardware()             != item2.getHardware()    ||    		
    		item1.getOsVersion()            != item2.getOsVersion()   ||
			item1.getFirmwareRev()          != item2.getFirmwareRev() ||
    		item1.getMaxNumberRFCOMMPorts() != item2.getMaxNumberRFCOMMPorts()) {
    		return false;
    	}
    	
    	return true;
    }
    
    public static boolean validateDisplayCapabilities (DisplayCapabilities item1, DisplayCapabilities item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
    	}
    	
    	if (item1.getDisplayType() == null) {
    		return ( item2.getDisplayType() == null );
    	}
    	
    	if (item1.getDisplayType() != item2.getDisplayType()) {
    		return false;
    	}
    	
    	if (item1.getGraphicSupported() != item2.getGraphicSupported()) {    		
    		return false;
    	}
    	// Failing past here:
    	// log("GS", item1.getGraphicSupported() + " : " + item2.getGraphicSupported());
    	
    	if (!validateStringList(item1.getTemplatesAvailable(),item2.getTemplatesAvailable())) {
    		log("TA", item1.getTemplatesAvailable() + " | " + item2.getTemplatesAvailable());
    		return false;
    	}
    	
    	if (item1.getNumCustomPresetsAvailable() != item2.getNumCustomPresetsAvailable()) {
    		return false;
    	}
    	
    	if (item1.getMediaClockFormats() == null) {
    		return ( item2.getMediaClockFormats() == null );
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
    		return ( item2.getTextFields() == null );
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
    		return ( item2.getImageFields() == null );
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
    
    public static boolean validateButtonCapabilities (List<ButtonCapabilities> item1, List<ButtonCapabilities> item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
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
    
    public static boolean validateSoftButtonCapabilities (List<SoftButtonCapabilities> item1, List<SoftButtonCapabilities> item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
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
    
    public static boolean validateTurnList (List<Turn> item1, List<Turn> item2) {
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
    
    public static boolean validateAudioPassThruCapabilities (List<AudioPassThruCapabilities> item1, List<AudioPassThruCapabilities> item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
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
    
    public static boolean validatePresetBankCapabilities (PresetBankCapabilities item1, PresetBankCapabilities item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
    	}
    	
    	if (item1.onScreenPresetsAvailable() != item2.onScreenPresetsAvailable()) {
    		return false;
    	}
    	
    	return true;
    }
    
    public static boolean validateVehicleType (VehicleType item1, VehicleType item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
    	}
    	
    	if (item1.getMake()      != item2.getMake()  ||
    		item1.getModel()     != item2.getModel() ||
    		item1.getTrim()      != item2.getTrim()  ||
    		item1.getModelYear() != item2.getModelYear()) {
    		return false;
    	}
    	
    	return true;
    }
    
    public static boolean validateKeyboardProperties (KeyboardProperties item1, KeyboardProperties item2) {
    	if (item1 == null) {
    		log("1", item2.toString());
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		log("2", item1.toString());
    		return ( item1 == null );
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
    		log("List",item1.getLimitedCharacterList().toString() + " | " + item2.getLimitedCharacterList().toString());
    		return false;
    	}
    	
    	return true;
    }
    
    public static boolean validateStartTime (StartTime item1, StartTime item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
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
    
    public static boolean validateVrHelpItems (List<VrHelpItem> item1, List<VrHelpItem> item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
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
    
    public static boolean validateDIDResults (List<DIDResult> item1, List<DIDResult> item2) {
    	if (item1 == null) {
    		return ( item2 == null );
    	}
    	if (item2 == null) {
    		return ( item1 == null );
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
    
    public static boolean validateHeaders(Headers item1, Headers item2){
        if (item1 == null) {
            return ( item2 == null );
        }
        if (item2 == null) {
            return ( item1 == null );
        }
        
        if( ! item1.getDoInput().equals(item2.getDoInput()) ){
            return false;
        }
        
        if( ! item1.getDoOutput().equals(item2.getDoOutput()) ){
            return false;
        }
        
        if( ! item1.getInstanceFollowRedirects().equals(item2.getInstanceFollowRedirects()) ){
            return false;
        }
        
        if( ! item1.getUseCaches().equals(item2.getUseCaches()) ){
            return false;
        }
        
        if( ! item1.getCharset().equals(item2.getCharset()) ){
            return false;
        }
        
        if( ! item1.getConnectTimeout().equals(item2.getConnectTimeout()) ){
            return false;
        }
        
        if( ! item1.getContentLength().equals(item2.getContentLength()) ){
            return false;
        }
        
        if( ! item1.getContentType().equals(item2.getContentType()) ){
            return false;
        }
        
        if( ! item1.getReadTimeout().equals(item2.getReadTimeout()) ){
            return false;
        }
        
        if( ! item1.getRequestMethod().equals(item2.getRequestMethod()) ){
            return false;
        }
        
        return true;
    }
}
