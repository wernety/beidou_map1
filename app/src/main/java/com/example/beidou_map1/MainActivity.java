package com.example.beidou_map1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "chx";
    private  MapView mMapView;
    private LocationDisplay mLocationDisplay;
    private ArcGISMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.mMapView);
        mMapView.setAttributionTextVisible(false);

        WebTiledLayer webTiledLayer1 = TianDiTu.CreateTianDiTuTiledLayer(TianDiTu.LayerType.TIANDITU_IMAGE_2000);
//        WebTiledLayer webTiledLayer2 = TianDiTu.CreateTianDiTuTiledLayer(TianDiTu.LayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
        Log.d(TAG, "onCreate: 创建成功地图:图层和标注");


        Basemap basemap = new Basemap(webTiledLayer1);
//        basemap.getBaseLayers().add(webTiledLayer2);
        Log.d(TAG, "onCreate: 加载成功地图:图层和标注");



        map = new ArcGISMap(basemap);
//        map = new ArcGISMap(tdtBasemap2);
        mMapView.setMap(map);
        Log.d(TAG, "onCreate: 地图设置成功");
        mMapView.setViewpoint(new Viewpoint(116,30,10000));
        Log.d(TAG, "onCreate: 设置关注点坐标");



        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.READ_PRECISE_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PRECISE_PHONE_STATE);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }


//        给予权限
//
        mLocationDisplay = mMapView.getLocationDisplay();
        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);

        Point point =  mLocationDisplay.getMapLocation();

        if (!mLocationDisplay.isStarted())
            mLocationDisplay.startAsync();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
        if (mLocationDisplay.isStarted())
            mLocationDisplay.stop();
    }
}