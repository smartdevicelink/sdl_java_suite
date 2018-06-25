/***************************************************************************************************
 * Copyright © 2017 Xevo Inc.
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *    and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 *    conditions and the following disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 *    endorse or promote products derived from this software without specific prior written
 *    permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **************************************************************************************************/
package com.smartdevicelink.haptic;

import android.view.View;
import android.view.ViewGroup;

import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.HapticRect;
import com.smartdevicelink.proxy.rpc.Rectangle;
import com.smartdevicelink.proxy.rpc.SendHapticData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/22/2017.
 * 
 * Manages haptic data used to render focusable areas on the HU screen.  App developers can
 * over-ride the default logic used to find focusable Views by passing their own data to
 * {@link #setHapticData(List)}
 */
public class HapticInterfaceManager {
    private static final String TAG = "Haptic";

    private WeakReference<ISdl> proxyHolder;
    private List<HapticRect> userHapticData;

    public HapticInterfaceManager(ISdl proxy) {
        this.proxyHolder = new WeakReference<>(proxy);
    }

    /**
     * Sets haptic data and sends update to the HU.  To be used by app code instead of letting
     * Presentation find the Views and automatically send to HU.
     *
     * @param hapticData
     *          Rect data indicating "focusable" screen elements or areas
     */
    public void setHapticData(List<HapticRect> hapticData) {
        userHapticData = hapticData;
        ISdl proxy = proxyHolder.get();
        if (proxy != null) {
            SendHapticData msg = new SendHapticData();
            msg.setHapticRectData(userHapticData);
            proxy.sendRPCRequest(msg);
        }
    }

    /**
     * Sends haptic data found by searching for focusable and clickable Views in the view heirarchy
     * to the HU.  Should be called by Presentation's OnShowListener.
     * 
     * @param root
     *          the root or parent View
     */
    public void refreshHapticData(View root) {
        ISdl proxy = proxyHolder.get();
        if ((userHapticData == null) && (proxy != null)) {
            List<HapticRect> hapticRects = new ArrayList<>();
            findHapticRects(root, hapticRects);

            SendHapticData msg = new SendHapticData();
            msg.setHapticRectData(hapticRects);

            proxy.sendRPCRequest(msg);
        }
    }

    private void findHapticRects(View root, final List<HapticRect> hapticRects) {
        List<View> focusables = new ArrayList<>();
        getFocusableViews(root, focusables);

        int [] loc = new int[2];
        int id = 0;
        for (View view : focusables) {
            int w = view.getWidth();
            int h = view.getHeight();
            view.getLocationOnScreen(loc);

            Rectangle rect = new Rectangle();
            rect.setWidth((float) w);
            rect.setHeight((float) h);
            rect.setX((float) loc[0]);
            rect.setY((float) loc[1]);

            HapticRect hapticRect = new HapticRect();
            hapticRect.setId(id++);
            hapticRect.setRect(rect);
            hapticRects.add(hapticRect);
        }
    }

    private void getFocusableViews(View view, final List<View> focusables) {
        // Not using addFocusables() or addTouchables() because of concerns with adding ViewGroup
        // and not getting "clickables."

        if (!(view instanceof ViewGroup) && (view != null) &&
                (view.isFocusable() || view.isClickable())) {
            focusables.add(view);
        }

        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;

            for (int i = 0; i < parent.getChildCount(); i++) {
                getFocusableViews(parent.getChildAt(i), focusables);
            }
        }
    }
}
