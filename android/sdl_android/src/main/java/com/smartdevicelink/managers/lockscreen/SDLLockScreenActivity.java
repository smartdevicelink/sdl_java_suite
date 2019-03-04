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
