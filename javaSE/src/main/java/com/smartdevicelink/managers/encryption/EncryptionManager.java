package com.smartdevicelink.managers.encryption;

import android.support.annotation.NonNull;
import com.smartdevicelink.proxy.interfaces.ISdl;

public class EncryptionManager extends BaseEncryptionManager {

    /**
     * Creates a new instance of the EncryptionManager
     *
     * @param internalInterface an instance of the ISdl object
     */
    public EncryptionManager(@NonNull ISdl internalInterface, @NonNull EncryptionCallback cb) {
        super(internalInterface, cb);
    }
}
