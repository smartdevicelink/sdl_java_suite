package com.sdl.hellosdlandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	public static final String IP = "ip";
	public static final String PORT = "port";

	public static final String PREDEFINED_WIDTH = "pre_def_w";
	public static final String PREDEFINED_HEIGHT = "pre_def_h";
	public static final String IP_ADDRESS = "ip_address";
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);


		Button startStreaming = findViewById(R.id.start_streaming);
		Button startStreamingUI = findViewById(R.id.start_streaming_ui);
		Button startProxy = findViewById(R.id.start_proxy);
		Button exitApplication = findViewById(R.id.exit_application);

		final EditText machineIp = findViewById(R.id.machine_ip);
		final EditText machinePort = findViewById(R.id.machine_port);

		final EditText preConfWidth = findViewById(R.id.pre_conf_width);
		final EditText preConfHeight = findViewById(R.id.pre_conf_height);

		machineIp.setText(preferences.getString(IP_ADDRESS, "192.168.0.101"));

		machineIp.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }

			@Override
			public void afterTextChanged(Editable s) {
				if (s!=null) {
					preferences.edit().putString(IP_ADDRESS, s.toString()).commit();
				}
			}
		});

		startProxy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//If we are connected to a module we want to start our SdlService
				if(BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
					SdlReceiver.queryForConnectedService(MainActivity.this);
				}else if(BuildConfig.TRANSPORT.equals("TCP")) {
					Intent proxyIntent = new Intent(MainActivity.this, SdlService.class);

					String ipString = machineIp.getText().toString();
					String portString = machinePort.getText().toString();
					if (!ipString.isEmpty() && !portString.isEmpty()) {
						proxyIntent.putExtra(IP, machineIp.getText().toString());
						try {
							proxyIntent.putExtra(PORT, Integer.parseInt(machinePort.getText().toString()));
						} catch (NumberFormatException e){
							Toast.makeText(MainActivity.this, "Port should be number", Toast.LENGTH_SHORT).show();
							return;
						}

						startService(proxyIntent);
					} else {
						Toast.makeText(MainActivity.this, "IP and Port are empty", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		startStreaming.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!preConfWidth.getText().toString().isEmpty() && !preConfHeight.getText().toString().isEmpty()) {
					preferences.edit().putInt(PREDEFINED_WIDTH, Integer.parseInt(preConfWidth.getText().toString())).commit();
					preferences.edit().putInt(PREDEFINED_HEIGHT, Integer.parseInt(preConfHeight.getText().toString())).commit();
					SdlService.relay.accept(STREAM_ENUM.START_STREAMING);
				} else {
					Toast.makeText(MainActivity.this, "Configure display width and height", Toast.LENGTH_SHORT).show();
				}
			}
		});

		startStreamingUI.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (!preConfWidth.getText().toString().isEmpty() && !preConfHeight.getText().toString().isEmpty()) {
					preferences.edit().putInt(PREDEFINED_WIDTH, Integer.parseInt(preConfWidth.getText().toString())).commit();
					preferences.edit().putInt(PREDEFINED_HEIGHT, Integer.parseInt(preConfHeight.getText().toString())).commit();
					SdlService.relay.accept(STREAM_ENUM.START_STREAMING_UI);
				} else {
					Toast.makeText(MainActivity.this, "Configure display width and height", Toast.LENGTH_SHORT).show();
				}
			}
		});

		exitApplication.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				stopService(new Intent(MainActivity.this, SdlService.class));

				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				}, 50);

				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static enum STREAM_ENUM{
		START_STREAMING, START_STREAMING_UI
	}
}
