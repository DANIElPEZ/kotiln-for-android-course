package com.example.segunda

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class fragment_direction : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val home = LatLng(7.059430, -73.862541)
        googleMap.addMarker(MarkerOptions().position(home).title("Direccion del programador"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(home))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(home,18f),4000,null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_direction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}