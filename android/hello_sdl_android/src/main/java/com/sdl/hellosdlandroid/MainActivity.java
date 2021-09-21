package com.sdl.hellosdlandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 31) {
            if (!checkPermission()) {
                requestPermission();
            }
        } else {
            //If we are connected to a module we want to start our SdlService
            startSDLService();
        }
    }

    private void startSDLService() {
        if (BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
            SdlReceiver.queryForConnectedService(this);
        } else if (BuildConfig.TRANSPORT.equals("TCP")) {
            Intent proxyIntent = new Intent(this, SdlService.class);
            startService(proxyIntent);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH_CONNECT);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), BLUETOOTH_SCAN);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{BLUETOOTH_CONNECT, BLUETOOTH_SCAN}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean connectAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean scanAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (connectAccepted && scanAccepted) {
                        startSDLService();
                    }
                }
                break;
        }
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
}
