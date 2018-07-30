package com.smartdevicelink.api.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.datatypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;

/**
 * <strong>SoftButtonState</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself.
 */
public class SoftButtonState {

    private static final String TAG = "SoftButtonState";
    private String name;
    private SdlArtwork artwork;
    private SoftButton softButton;

    public SoftButtonState(String name, String text, SdlArtwork artwork) {
        if (text == null && artwork == null) {
            Log.e(TAG, "Attempted to create an invalid soft button state: text and artwork are both null");
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
        Image image = null;
        if (artwork != null) {
            image = new Image();
            image.setValue(artwork.getName());
            image.setImageType(ImageType.DYNAMIC);
            softButton.setImage(image);
        }

        // Set the SoftButton's text
        if (text != null) {
            softButton.setText(text);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public SoftButton getSoftButton() {
        return softButton;
    }

    public SdlArtwork getArtwork() {
        return artwork;
    }
}
