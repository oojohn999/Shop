package com.oojohn.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {  //實作介面OnMapReadyCallback當地圖完成時被通知的對象，並實作onMapReady方法

    private lateinit var mMap: GoogleMap //mMap晚點新增 晚點初始化，GooogleMap在Fragment產生後才會產生

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment //用Manager找到ID (R.id.map)
        mapFragment.getMapAsync(this)  //非同步
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0) //預設位置為澳洲雪梨
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")) //紅色氣球標示符號
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))//顯示雪梨位置在正中間
        setupMap()
    }

    private fun setupMap() {
        //25.0339808,121.5623502
        val taipei101=LatLng(25.0339808,121.5623502)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taipei101,17f)) //17f為往下Zoom in的遠近 ,21最近 2最遠，CameraUpdateFactory鏡頭更新工廠類別
        mMap.addMarker(MarkerOptions()//標示
            .position(taipei101)
            .title("Here's Taipei 101"))//滑鼠移到標示位置所顯示的文字
        mMap.uiSettings.isZoomControlsEnabled=true  //顯示Zoom in Zoom out的圖示
    }
}