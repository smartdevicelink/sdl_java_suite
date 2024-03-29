package com.sdl.hellosdlandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
            String[] permissionsNeeded = permissionsNeeded();
            if (permissionsNeeded.length > 0) {
                requestPermission(permissionsNeeded, REQUEST_CODE);
                for (String permission : permissionsNeeded) {
                    if (Manifest.permission.BLUETOOTH_CONNECT.equals(permission)) {
                        // We need to request BLUETOOTH_CONNECT permission to connect to SDL via Bluetooth
                        return;
                    }
                }
            }

            //If we are connected to a module we want to start our SdlService
            SdlReceiver.queryForConnectedService(this);
        } else if (BuildConfig.TRANSPORT.equals("TCP")){
            Intent proxyIntent = new Intent(this, SdlService.class);
            startService(proxyIntent);
        }
    }

    /**
     * Boolean method that checks API level and check to see if we need to request BLUETOOTH_CONNECT permission
     * @return false if we need to request BLUETOOTH_CONNECT permission
     */
    private boolean hasBTPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? checkPermission(Manifest.permission.BLUETOOTH_CONNECT) : true;
    }

    /**
     * Boolean method that checks API level and check to see if we need to request POST_NOTIFICATIONS permission
     * @return false if we need to request POST_NOTIFICATIONS permission
     */
    private boolean hasPNPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? checkPermission(Manifest.permission.POST_NOTIFICATIONS) : true;
    }

    private boolean checkPermission(String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getApplicationContext(), permission);
    }

    private void requestPermission(String[] permissions, int REQUEST_CODE) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    private @NonNull String[] permissionsNeeded() {
        ArrayList<String> result = new ArrayList<>();
        if (!hasBTPermission()) {
            result.add(Manifest.permission.BLUETOOTH_CONNECT);
        }
        if (!hasPNPermission()) {
            result.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        return (result.toArray(new String[result.size()]));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (permissions[i].equals(Manifest.permission.BLUETOOTH_CONNECT)) {
                            boolean btConnectGranted =
                                    grantResults[i] == PackageManager.PERMISSION_GRANTED;
                            if (btConnectGranted) {
                                SdlReceiver.queryForConnectedService(this);
                            }
                        } else if (permissions[i].equals(Manifest.permission.POST_NOTIFICATIONS)) {
                            boolean postNotificationGranted =
                                    grantResults[i] == PackageManager.PERMISSION_GRANTED;
                            if (!postNotificationGranted) {
                                // User denied permission, Notifications for SDL will not appear
                                // on Android 13 devices.
                            }
                        }
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
