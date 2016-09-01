package com.smartdevicelink.proxy.rpc.enums;
/**
 * The name that identifies the field.
 * 
 * @since SmartDeviceLink 2.3.2
 */
public enum ImageFieldName {
	/** The image field for SoftButton
	 * 
	 */

	softButtonImage,
	/** The first image field for Choice.
	 * 
	 */

	choiceImage,
	/** The secondary image field for Choice.
	 * 
	 */

	choiceSecondaryImage,
	/** The image field for vrHelpItem.
	 * 
	 */

	vrHelpItem,
	/** The image field for Turn.
	 * 
	 */

	turnIcon,
	/** The image field for the menu icon in SetGlobalProperties.
	 * 
	 */

	menuIcon,
	/** The image filed for AddCommand.
	 * 
	 */

	cmdIcon,
	/** The image field for the app icon ( set by setAppIcon).
	 * 
	 */

	appIcon,
	/** The image filed for Show.
	 * 
	 */

	graphic,
	/** The primary image field for ShowConstant TBT.
	 * 
	 */

	showConstantTBTIcon,

	/** The secondary image field for ShowConstant TBT.
	 * 
	 */
	showConstantTBTNextTurnIcon,
	/**
     * The optional image of a destination / location
     * 
     * @since SmartDeviceLink 4.0
     */
	locationImage,
	;
    
	/**
     * Convert String to ImageFieldName
     * @param value String
     * @return ImageFieldName
     */  
	public static ImageFieldName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
