package com.example.mystories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mystories.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var token: String = ""
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(MainActivity.TOKEN) ?: ""

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        markStoryLocation()
    }

    private fun markStoryLocation(){
        lifecycleScope.launchWhenResumed {
            launch {
                mapsViewModel.getStoriesWithLocation(token).collect { result ->
                    result.onSuccess { response ->
                        response.stories.forEach{
                            if(it.lat != null && it.lon != null){
                                val latLng = LatLng(it.lat, it.lon)

                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(latLng)
                                        .title(it.name)
                                        .snippet("Lat: ${it.lat}, Lon: ${it.lon}")
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}