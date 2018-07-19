package com.smartdevicelink.api.lockscreen;

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

	public static final String LOCKSCREEN_COLOR_EXTRA = "LOCKSCREEN_COLOR_EXTRA";
	public static final String LOCKSCREEN_ICON_EXTRA = "LOCKSCREEN_ICON_EXTRA";
	public static final String LOCKSCREEN_OEM_ICON_EXTRA = "LOCKSCREEN_OEM_ICON_EXTRA";
	public static final String LOCKSCREEN_OEM_ICON_BITMAP = "LOCKSCREEN_OEM_ICON_BITMAP";
	public static final String LOCKSCREEN_CUSTOM_VIEW_EXTRA = "LOCKSCREEN_CUSTOM_VIEW_EXTRA";
	public static final String CLOSE_LOCK_SCREEN_ACTION = "CLOSE_LOCK_SCREEN";
	private static final String TAG = "SDLLockScreenActivity";

	private int customView, customIcon, customColor;
	private Bitmap lockScreenOEMIcon;

	private final BroadcastReceiver closeLockScreenBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// set any parameters that came from the lock screen manager
		initializeActivity(getIntent());

		// register broadcast receivers
		registerReceiver(closeLockScreenBroadcastReceiver, new IntentFilter(CLOSE_LOCK_SCREEN_ACTION));
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(closeLockScreenBroadcastReceiver);
		super.onDestroy();
	}

	public void initializeActivity(Intent intent){
		if (intent != null){
			boolean showOEMLogo = intent.getBooleanExtra(LOCKSCREEN_OEM_ICON_EXTRA, true);
			customColor = intent.getIntExtra(LOCKSCREEN_COLOR_EXTRA, 0);
			customIcon = intent.getIntExtra(LOCKSCREEN_ICON_EXTRA, 0);
			customView = intent.getIntExtra(LOCKSCREEN_CUSTOM_VIEW_EXTRA, 0);
			lockScreenOEMIcon = intent.getParcelableExtra(LOCKSCREEN_OEM_ICON_BITMAP);

			if (customView != 0){
				setCustomView();
			} else {
				setContentView(R.layout.activity_sdllock_screen);

				if (customColor != 0){
					changeBackgroundColor();
				}

				if (customIcon != 0){
					changeIcon();
				}

				if (showOEMLogo && lockScreenOEMIcon != null){
					showOEMIcon();
				}
			}
		}
	}

	private void changeBackgroundColor() {
		RelativeLayout layout = findViewById(R.id.lockscreen_relative_layout);
		layout.setBackgroundColor(getResources().getColor(customColor));
	}

	private void changeIcon() {
		ImageView lockscreen_iv = findViewById(R.id.lockscreen_image);
		lockscreen_iv.setBackgroundResource(customIcon);
	}

	private void showOEMIcon() {
		ImageView oem_iv = findViewById(R.id.OEM_image);
		if (lockScreenOEMIcon != null) {
			oem_iv.setImageBitmap(lockScreenOEMIcon);
		}
	}

	private void setCustomView() {
		setContentView(customView);
	}

}
