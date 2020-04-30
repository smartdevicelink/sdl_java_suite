package com.sdl.hellosdlandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	public static final String IP = "ip";
	public static final String PORT = "port";
	public static final String COMMAND = "command";
	public static final String COMMAND_START_PROXY = "c_start_proxy";
	public static final String COMMAND_START_STREAM = "c_start_stream";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button startStreaming = findViewById(R.id.start_streaming);
		Button startStreamingUI = findViewById(R.id.start_streaming_ui);
		Button startProxy = findViewById(R.id.start_proxy);

		final EditText ip = findViewById(R.id.machine_ip);
		final EditText port = findViewById(R.id.machine_port);

		startProxy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//If we are connected to a module we want to start our SdlService
				if(BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
					SdlReceiver.queryForConnectedService(MainActivity.this);
				}else if(BuildConfig.TRANSPORT.equals("TCP")) {
					Intent proxyIntent = new Intent(MainActivity.this, SdlService.class);

					String ipString = ip.getText().toString();
					String portString = port.getText().toString();
					if (!ipString.isEmpty() && !portString.isEmpty()) {
						proxyIntent.putExtra(IP, ip.getText().toString());
						try {
							proxyIntent.putExtra(PORT, Integer.parseInt(port.getText().toString()));
						} catch (NumberFormatException e){
							Toast.makeText(MainActivity.this, "Port should be number", Toast.LENGTH_SHORT).show();
							return;
						}

						startService(proxyIntent);
					} else {
						Toast.makeText(MainActivity.this, "Fill ip and port", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		startStreaming.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SdlService.relay.accept(STREAM_ENUM.START_STREAMING);
			}
		});

		startStreamingUI.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SdlService.relay.accept(STREAM_ENUM.START_STREAMING_UI);
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
