package com.example.mapdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng marker) {
                android.util.Log.i("all", "you clicked on the map");
                final String savedMarker = marker.toString();

                final EditText input = new EditText(MapsActivity.this);

                new AlertDialog.Builder(MapsActivity.this)
                        .setTitle("Adding Location")
                        .setMessage("Do you want to place this location?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                                new AlertDialog.Builder(MapsActivity.this)
                                        .setView(input)
                                        .setMessage("Add name to this location?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mMap.addMarker(new MarkerOptions().position(marker).title(input.getText().toString() + " " + savedMarker));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 3));
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        /*
        LatLng dkPizza = new LatLng(55.6615325, 12.5251474); // burger palace: 55.6615325, 12.5251474
        mMap.addMarker(new MarkerOptions().position(dkPizza).title("Marker in denmark with best pizza/burger"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(dkPizza));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dkPizza, 15)); //zoom in on the place, goes from 1 to 21

        LatLng dkLive = new LatLng(55.6491189, 12.5249); // royal arena: 55.6491189, 12.5249
        mMap.addMarker(new MarkerOptions().position(dkLive).title("Marker in denmark with best live concert"));
        */
    }
}
