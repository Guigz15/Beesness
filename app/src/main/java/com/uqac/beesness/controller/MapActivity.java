package com.uqac.beesness.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.uqac.beesness.R;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapActivity extends AppCompatActivity {

    private MapView map = null;
    private MyLocationNewOverlay locationOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
            return;
        }

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.setMaxZoomLevel(20.0);
        map.setMinZoomLevel(4.0);
        map.setVerticalMapRepetitionEnabled(false);
        map.getController().setZoom(10.0);

        locationOverlay = new MyLocationNewOverlay(map);
        locationOverlay.enableFollowLocation();
        locationOverlay.enableMyLocation();
        map.getOverlays().add(locationOverlay);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        locationOverlay.enableMyLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        locationOverlay.disableMyLocation();
    }
}