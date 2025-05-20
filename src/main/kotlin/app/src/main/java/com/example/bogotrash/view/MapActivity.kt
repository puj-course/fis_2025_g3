package com.example.bogotrash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.repository.DatabaseConnection
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.concurrent.thread
import androidx.core.view.WindowCompat

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        thread {
            try {
                val conn = DatabaseConnection.getConnection()
                val stmt = conn?.createStatement()
                val rs = stmt?.executeQuery("SELECT name, location FROM RecyclingPoints")

                while (rs != null && rs.next()) {
                    val name = rs.getString("name")
                    val locationStr = rs.getString("location")
                    val parts = locationStr.split(",")
                    if (parts.size == 2) {
                        val lat = parts[0].toDoubleOrNull()
                        val lng = parts[1].toDoubleOrNull()
                        if (lat != null && lng != null) {
                            val point = LatLng(lat, lng)
                            runOnUiThread {
                                map.addMarker(MarkerOptions().position(point).title(name))
                            }
                        }
                    }
                }

                rs?.close()
                stmt?.close()
                conn?.close()

                runOnUiThread {
                    val bogota = LatLng(4.7110, -74.0721)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 12f))
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


