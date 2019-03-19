package com.smartdevicelink.proxy.rpc.enums;

/**
 * For application-initiated interactions (<i>{@linkplain com.smartdevicelink.proxy.rpc.PerformInteraction}</i>), this specifies
 * the mode by which the user is prompted and by which the user's selection is
 * indicated
 * 
 * @since SmartDeviceLink 1.0
 */
public enum InteractionMode {
	/**
	 * This mode causes the interaction to occur only on the display, meaning
	 * the choices are presented and selected only via the display. Selections
	 * are viewed with the SEEKRIGHT, SEEKLEFT, TUNEUP, TUNEDOWN buttons. User's
	 * selection is indicated with the OK button
	 */
	MANUAL_ONLY,
	/**
	 * This mode causes the interaction to occur only through TTS and VR. The
	 * user is prompted via TTS to select a choice by saying one of the choice's
	 * synonyms
	 */
	VR_ONLY,
	/**
	 * This mode is a combination of MANUAL_ONLY and VR_ONLY, meaning the user
	 * is prompted both visually and audibly. The user can make a selection
	 * either using the mode described in MANUAL_ONLY or using the mode
	 * described in VR_ONLY. If the user views selections as described in
	 * MANUAL_ONLY mode, the interaction becomes strictly, and irreversibly, a
	 * MANUAL_ONLY interaction (i.e. the VR session is cancelled, although the
	 * interaction itself is still in progress). If the user interacts with the
	 * VR session in any way (e.g. speaks a phrase, even if it is not a
	 * recognized choice), the interaction becomes strictly, and irreversibly, a
	 * VR_ONLY interaction (i.e. the MANUAL_ONLY mode forms of interaction will
	 * no longer be honored)
	 * 
	 * <p>The TriggerSource parameter of the
	 * {@linkplain com.smartdevicelink.proxy.rpc.PerformInteraction} response will
	 * indicate which interaction mode the user finally chose to attempt the
	 * selection (even if the interaction did not end with a selection being
	 * made)</P>
	 */
	BOTH;

	/**
	 * Returns InteractionMode (MANUAL_ONLY, VR_ONLY or BOTH)
	 * @param value a String
	 * @return InteractionMode -MANUAL_ONLY, VR_ONLY or BOTH
	 */

    public static InteractionMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
