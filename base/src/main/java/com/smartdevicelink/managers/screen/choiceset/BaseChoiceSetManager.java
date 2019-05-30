/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.managers.screen.choiceset;

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;

/**
 * <strong>ChoiceSetManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 */
public abstract class BaseChoiceSetManager extends BaseSubManager {

    // additional state
    private static final int CHECKING_VOICE = 0xA0;

    private final WeakReference<FileManager> fileManager;
    private HashSet<ChoiceCell> preloadedChoices;
    private KeyboardProperties keyboardConfiguration;


    public BaseChoiceSetManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface);

        this.fileManager = new WeakReference<>(fileManager);

    }

    @Override
    public void start(CompletionListener listener){

        transitionToState(READY);
        super.start(listener);
    }

    @Override
    public void dispose(){


        super.dispose();
    }

    public void preloadChoices(List<ChoiceCell> choices, CompletionListener listener){


    }

    public void deleteChoices(List<ChoiceCell> choices){


    }

    public void presentChoiceSet(ChoiceSet choiceSet, InteractionMode mode, KeyboardListener listener){


    }

    public void presentKeyboardWithInitialText(String initialText, KeyboardListener listener){


    }
}
