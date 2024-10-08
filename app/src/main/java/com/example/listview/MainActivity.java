package com.example.listview;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private final ArrayList<String> nombres = new ArrayList<>();
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerPermission();
        getWifiList();
        ListView listView = (ListView) findViewById(R.id.viewList);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombres);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }

    public void getWifiList() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults != null && !scanResults.isEmpty()) {
            for (int i = 0; i < scanResults.size(); i++) {
                ScanResult result = scanResults.get(i);
                if (!result.SSID.isEmpty()) {
                    String key = result.SSID;
                    nombres.add(key);
                }

            }

        }

    }




    private void registerPermission(){
        //动态获取定位权限
        if (checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    100);
        } else {
            getWifiList();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            getWifiList();
        }
    }

}
