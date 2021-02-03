package com.myapp.dotgame;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.text.*;

public class Misc extends Fragment {
    private TextView lat, longi;
    private LocationManager lm;
    private TextView wifi;
    private WebView wv1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_misc, container, false);

        wv1=(WebView) v.findViewById(R.id.currentTime);
        wv1.setWebViewClient(new MyBrowser());
        String url1 = "http://192.168.1.27:8080/TestServer/date";
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url1);

        lat = v.findViewById(R.id.latitude2);
        longi = v.findViewById(R.id.longitude);
        lm = (LocationManager)getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        wifi = v.findViewById(R.id.wifi);
        DecimalFormat df = new DecimalFormat("#.##");
        WifiManager wifiMgr = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiMgr.setWifiEnabled(true);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        String wifiName = wifiInfo.getSSID();
        wifi.setText("WIFI name: " + wifiName);

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
          (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                longi.setText("Longitude: " + String.valueOf(df.format(locationGPS.getLongitude())));
                lat.setText("Latitude: " + String.valueOf(df.format(locationGPS.getLatitude())));
            }
        }
        return v;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}