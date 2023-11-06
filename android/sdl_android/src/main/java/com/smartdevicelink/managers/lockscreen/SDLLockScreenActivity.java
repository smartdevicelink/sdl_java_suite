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

package com.smartdevicelink.managers.lockscreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartdevicelink.R;
import com.smartdevicelink.util.AndroidTools;

public class SDLLockScreenActivity extends Activity {

    public static final String LOCKSCREEN_COLOR_EXTRA = "LOCKSCREEN_COLOR_EXTRA";
    public static final String LOCKSCREEN_ICON_EXTRA = "LOCKSCREEN_ICON_EXTRA";
    public static final String LOCKSCREEN_DEVICE_LOGO_EXTRA = "LOCKSCREEN_DEVICE_LOGO_EXTRA";
    public static final String LOCKSCREEN_DEVICE_LOGO_BITMAP = "LOCKSCREEN_DEVICE_LOGO_BITMAP";
    public static final String LOCKSCREEN_CUSTOM_VIEW_EXTRA = "LOCKSCREEN_CUSTOM_VIEW_EXTRA";
    public static final String LOCKSCREEN_DEVICE_LOGO_DOWNLOADED = "LOCKSCREEN_DEVICE_LOGO_DOWNLOADED";
    public static final String CLOSE_LOCK_SCREEN_ACTION = "CLOSE_LOCK_SCREEN";
    public static final String KEY_LOCKSCREEN_DISMISSED = "KEY_LOCKSCREEN_DISMISSED";
    public static final String KEY_LOCKSCREEN_DISMISSIBLE = "KEY_LOCKSCREEN_DISMISSIBLE";
    public static final String KEY_LOCKSCREEN_WARNING_MSG = "KEY_LOCKSCREEN_WARNING_MSG";
    private static final int MIN_SWIPE_DISTANCE = 200;
    private boolean isDismissible;
    private GestureDetector dismissibleGestureDetector;
    private int backgroundColor = Color.parseColor("#394e60");
    private boolean useWhiteIconAndTextColor;

    private final BroadcastReceiver lockScreenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action != null) {
                    if (action.equalsIgnoreCase(CLOSE_LOCK_SCREEN_ACTION)) {
                        finish();
                    } else if (action.equalsIgnoreCase(LOCKSCREEN_DEVICE_LOGO_DOWNLOADED)) {
                        boolean deviceLogoEnabled = intent.getBooleanExtra(LOCKSCREEN_DEVICE_LOGO_EXTRA, true);
                        Bitmap deviceLogo = intent.getParcelableExtra(LOCKSCREEN_DEVICE_LOGO_BITMAP);
                        if (deviceLogoEnabled && deviceLogo != null) {
                            setDeviceLogo(deviceLogo);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dismissibleGestureDetector = new GestureDetector(this, new SwipeUpGestureListener());
        // set any parameters that came from the lock screen manager
        initializeActivity(getIntent(), true);

        // create intent filter
        IntentFilter lockscreenFilter = new IntentFilter();
        lockscreenFilter.addAction(CLOSE_LOCK_SCREEN_ACTION);
        lockscreenFilter.addAction(LOCKSCREEN_DEVICE_LOGO_DOWNLOADED);

        // register broadcast receivers
        AndroidTools.registerReceiver(this, lockScreenBroadcastReceiver, lockscreenFilter,
                RECEIVER_NOT_EXPORTED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isDismissible) {
            return dismissibleGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(lockScreenBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initializeActivity(intent, false);
    }

    public void initializeActivity(Intent intent, boolean inflateLayout) {
        if (intent != null) {
            isDismissible = intent.getBooleanExtra(KEY_LOCKSCREEN_DISMISSIBLE, false);
            int customView = intent.getIntExtra(LOCKSCREEN_CUSTOM_VIEW_EXTRA, 0);
            if (customView != 0) {
                if (inflateLayout) {
                    setContentView(customView);
                } //Currently the only thing done with a custom view is to inflate it
            } else {
                boolean deviceLogoEnabled = intent.getBooleanExtra(LOCKSCREEN_DEVICE_LOGO_EXTRA, true);
                int customColor = intent.getIntExtra(LOCKSCREEN_COLOR_EXTRA, 0);
                int customIcon = intent.getIntExtra(LOCKSCREEN_ICON_EXTRA, 0);
                Bitmap deviceIcon = intent.getParcelableExtra(LOCKSCREEN_DEVICE_LOGO_BITMAP);

                if (inflateLayout) {
                    setContentView(R.layout.activity_sdllock_screen);
                }

                backgroundColor = (customColor != 0) ? customColor : backgroundColor;
                setBackgroundColor();

                useWhiteIconAndTextColor = shouldUseWhiteForegroundForBackgroundColor();
                setTextColor(useWhiteIconAndTextColor ? Color.WHITE : Color.BLACK);

                // set Lock Screen Icon
                if (customIcon != 0) {
                    changeIcon(customIcon);
                } else {
                    setSdlLogo();
                }

                if (deviceLogoEnabled && deviceIcon != null) {
                    setDeviceLogo(deviceIcon);
                }

                String warningMsg = intent.getStringExtra(KEY_LOCKSCREEN_WARNING_MSG);
                setLockscreenText(warningMsg);


            }
        }
    }

    /**
     * Sets the lockScreen logo
     */
    private void setSdlLogo() {
        ImageView lockScreenImageView = findViewById(R.id.lockscreen_image);
        if (lockScreenImageView != null) {
            Drawable sdlIcon = getResources().getDrawable(R.drawable.sdl_lockscreen_icon);
            // Checks color contrast and determines if the logo should be black or white
            if (sdlIcon != null && useWhiteIconAndTextColor) {
                int color = Color.parseColor("#ffffff");

                int red = (color & 0xFF0000) / 0xFFFF;
                int green = (color & 0xFF00) / 0xFF;
                int blue = color & 0xFF;

                float[] matrix = {0, 0, 0, 0, red,
                        0, 0, 0, 0, green,
                        0, 0, 0, 0, blue,
                        0, 0, 0, 1, 0};

                ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
                sdlIcon.setColorFilter(colorFilter);
            }
            lockScreenImageView.setImageDrawable(sdlIcon);
        }
    }

    /**
     * Changes the text color to white on the lockScreen
     */
    private void setTextColor(int color) {
        TextView lockscreenTextView = findViewById(R.id.lockscreen_text);
        if (lockscreenTextView != null) {
            lockscreenTextView.setTextColor(color);
        }
    }

    /**
     * Calculates the contrast of the background to determine if the Icon and Text color
     * should be white or black
     *
     * @return True if Background and Icon should be white, False if black
     */
    private boolean shouldUseWhiteForegroundForBackgroundColor() {
        float r = Color.red(backgroundColor) / 255f;
        float b = Color.blue(backgroundColor) / 255f;
        float g = Color.green(backgroundColor) / 255f;

        // http://stackoverflow.com/a/3943023
        r = (r <= 0.3928f) ? (r / 12.92f) : (float) Math.pow(((r + 0.055f) / 1.055f), 2.4f);
        g = (g <= 0.3928f) ? (g / 12.92f) : (float) Math.pow(((g + 0.055f) / 1.055f), 2.4f);
        b = (b <= 0.3928f) ? (b / 12.92f) : (float) Math.pow(((b + 0.055f) / 1.055f), 2.4f);

        float luminescence = 0.2126f * r + 0.7152f * g + 0.0722f * b;
        return luminescence <= 0.179;
    }

    /**
     * Sets the color of the background
     * Will use default color if not set in LockScreenConfig
     */
    private void setBackgroundColor() {
        RelativeLayout layout = findViewById(R.id.lockscreen_relative_layout);
        layout.setBackgroundColor(backgroundColor);
    }

    /**
     * Used to change LockScreen default Icon to customIcon set in LockScreenConfig
     *
     * @param customIcon
     */
    private void changeIcon(int customIcon) {
        ImageView lockScreenImageView = findViewById(R.id.lockscreen_image);
        if (lockScreenImageView != null) {
            lockScreenImageView.setVisibility(View.GONE);
        }

        ImageView appIconImageView = findViewById(R.id.appIcon);
        if (appIconImageView != null) {
            appIconImageView.setVisibility(View.VISIBLE);
            appIconImageView.setBackgroundResource(customIcon);
        }
    }

    private void setDeviceLogo(Bitmap deviceLogo) {
        ImageView connectedDeviceImageView = findViewById(R.id.device_image);
        if (deviceLogo != null && connectedDeviceImageView != null) {
            connectedDeviceImageView.setImageBitmap(deviceLogo);
        }
    }

    private void setLockscreenText(String msg) {
        TextView lockscreenTextView = findViewById(R.id.lockscreen_text);
        if (lockscreenTextView != null) {
            if (isDismissible) {
                lockscreenTextView.setText(msg != null ? msg : getString(R.string.default_lockscreen_warning_message));
            } else {
                lockscreenTextView.setText(getString(R.string.lockscreen_text));
            }
        }
    }

    private class SwipeUpGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if ((event2.getY() - event1.getY()) > MIN_SWIPE_DISTANCE) {
                sendBroadcast(new Intent(KEY_LOCKSCREEN_DISMISSED));
                finish();
            }
            return true;
        }
    }
}
