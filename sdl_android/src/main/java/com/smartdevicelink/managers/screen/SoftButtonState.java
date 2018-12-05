package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;

/**
 * <strong>SoftButtonState</strong> <br>
 * Defines an individual state for SoftButtonObject.<br>
 * The states of SoftButtonObject allow the developer to not have to manage multiple SoftButtons that have very similar functionality.<br>
 * For example, a repeat button in a music app can be thought of as one SoftButtonObject with three typical states: repeat off, repeat 1, and repeat on.<br>
 * @see SoftButtonObject
 */
public class SoftButtonState {

    private static final String TAG = "SoftButtonState";
    private String name;
    private SdlArtwork artwork;
    private final SoftButton softButton;

    /**
     * Creates a new instance of SoftButtonState
     * Note: state names should be different for each SoftButtonObject
     * @param name a String value represents name of the state
     * @param text a String represents the text for the state
     * @param artwork an SdlArtwork represents the artwork for the state
     */
    public SoftButtonState(@NonNull String name, String text, SdlArtwork artwork) {
        if (text == null && artwork == null) {
            Log.e(TAG, "Attempted to create an invalid soft button state: text and artwork are both null");
            softButton = null;
            return;
        }
        this.name = name;
        this.artwork = artwork;


        // Create a SoftButton and set its Type
        SoftButtonType type;
        if (artwork != null && text != null) {
            type = SoftButtonType.SBT_BOTH;
        } else if (artwork != null) {
            type = SoftButtonType.SBT_IMAGE;
        } else {
            type = SoftButtonType.SBT_TEXT;
        }
        this.softButton = new SoftButton(type, 0);


        // Set the SoftButton's image
        if (artwork != null) {
            Image image = new Image(artwork.getName(), ImageType.DYNAMIC);
            image.setIsTemplate(artwork.isTemplateImage());
            softButton.setImage(image);
        }

        // Set the SoftButton's text
        if (text != null) {
            softButton.setText(text);
        }
    }

    /**
     * Get the state name
     * @return a String value represents the name of the state
     */
    public String getName() {
        return name;
    }

    /**
     * Set the state name
     * @param name a String value represents the name of the state
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Get the SoftButton for the state
     * @return a SoftButton object represents the SoftButton for the state
     */
    public SoftButton getSoftButton() {
        return softButton;
    }

    /**
     * Get the Artwork for the state
     * @return an SdlArtwork object represents the artwork for the state
     */
    public SdlArtwork getArtwork() {
        return artwork;
    }
}
