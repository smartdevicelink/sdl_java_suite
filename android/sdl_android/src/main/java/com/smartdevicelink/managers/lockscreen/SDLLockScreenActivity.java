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
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartdevicelink.R;

public class SDLLockScreenActivity extends Activity {

	private static final String TAG = "SDLLockScreenActivity";
	public static final String LOCKSCREEN_COLOR_EXTRA = "LOCKSCREEN_COLOR_EXTRA";
	public static final String LOCKSCREEN_ICON_EXTRA = "LOCKSCREEN_ICON_EXTRA";
	public static final String LOCKSCREEN_DEVICE_LOGO_EXTRA = "LOCKSCREEN_DEVICE_LOGO_EXTRA";
	public static final String LOCKSCREEN_DEVICE_LOGO_BITMAP = "LOCKSCREEN_DEVICE_LOGO_BITMAP";
	public static final String LOCKSCREEN_CUSTOM_VIEW_EXTRA = "LOCKSCREEN_CUSTOM_VIEW_EXTRA";
	public static final String LOCKSCREEN_DEVICE_LOGO_DOWNLOADED = "LOCKSCREEN_DEVICE_LOGO_DOWNLOADED";
	public static final String CLOSE_LOCK_SCREEN_ACTION = "CLOSE_LOCK_SCREEN";

	private final BroadcastReceiver lockScreenBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null){
				String action = intent.getAction();
				if (action != null){
					if (action.equalsIgnoreCase(CLOSE_LOCK_SCREEN_ACTION)){
						finish();
					} else if (action.equalsIgnoreCase(LOCKSCREEN_DEVICE_LOGO_DOWNLOADED)){
						boolean deviceLogoEnabled = intent.getBooleanExtra(LOCKSCREEN_DEVICE_LOGO_EXTRA, true);
						Bitmap deviceLogo = intent.getParcelableExtra(LOCKSCREEN_DEVICE_LOGO_BITMAP);
						if (deviceLogoEnabled && deviceLogo != null){
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

		// set any parameters that came from the lock screen manager
		initializeActivity(getIntent());

		// create intent filter
		IntentFilter lockscreenFilter = new IntentFilter();
		lockscreenFilter.addAction(CLOSE_LOCK_SCREEN_ACTION);
		lockscreenFilter.addAction(LOCKSCREEN_DEVICE_LOGO_DOWNLOADED);

		// register broadcast receivers
		registerReceiver(lockScreenBroadcastReceiver, lockscreenFilter);
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
	}

	public void initializeActivity(Intent intent){
		if (intent != null){
			boolean deviceLogoEnabled = intent.getBooleanExtra(LOCKSCREEN_DEVICE_LOGO_EXTRA, true);
			int customColor = intent.getIntExtra(LOCKSCREEN_COLOR_EXTRA, 0);
			int customIcon = intent.getIntExtra(LOCKSCREEN_ICON_EXTRA, 0);
			int customView = intent.getIntExtra(LOCKSCREEN_CUSTOM_VIEW_EXTRA, 0);
			Bitmap deviceIcon = intent.getParcelableExtra(LOCKSCREEN_DEVICE_LOGO_BITMAP);

			if (customView != 0){
				setCustomView(customView);
			} else {
				setContentView(R.layout.activity_sdllock_screen);

				if (customColor != 0){
					changeBackgroundColor(customColor);
				}

				if (customIcon != 0){
					changeIcon(customIcon);
				}

				if (deviceLogoEnabled && deviceIcon != null){
					setDeviceLogo(deviceIcon);
				}
			}
		}
	}

	private void changeBackgroundColor(int customColor) {
		RelativeLayout layout = findViewById(R.id.lockscreen_relative_layout);
		layout.setBackgroundColor(getResources().getColor(customColor));
	}

	private void changeIcon(int customIcon) {
		ImageView lockscreen_iv = findViewById(R.id.lockscreen_image);
		lockscreen_iv.setBackgroundResource(customIcon);
	}

	private void setDeviceLogo(Bitmap deviceLogo) {
		ImageView device_iv = findViewById(R.id.device_image);
		if (deviceLogo != null) {
			device_iv.setImageBitmap(deviceLogo);
		}
	}

	private void setCustomView(int customView) {
		setContentView(customView);
	}

}
