package com.example.mand.mapasdegoogle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapaPrueba extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap map;
    private double[][] MyPosition = new double[1][2];
    double lat,lng;
    String negocio;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            lat = Double.parseDouble(extras.getString("lat"));
            lng = Double.parseDouble(extras.getString("lng"));
            negocio = extras.getString("nombre");
        }
        setContentView(R.layout.mapa2);
        MyPosition[0][0] = 19.101296223954094;
        MyPosition[0][1] = -102.35506743192673;
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa2);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(new LatLng(MyPosition[0][0], MyPosition[0][1])).title("Ubicacion de maickol"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(MyPosition[0][0], MyPosition[0][1]), 18));
        //plasmarPinchetas();
        agregarMarcador();
    }
    public void plasmarPinchetas(){
        sqlite bh = new sqlite(this,"negocios",null,4);
        SQLiteDatabase db = bh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM negocios",null);
        if(c.moveToFirst()){
            do{
                map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(c.getString(2)),Double.parseDouble(c.getString(3)))).title(c.getString(1)));
            }while(c.moveToNext());
        }
    }
    public void agregarMarcador(){
        map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(negocio));
        LatLng pos1,pos2;
        pos1 = new LatLng(MyPosition[0][0],MyPosition[0][1]);
        pos2 = new LatLng(lat,lng);

        map.addPolyline(new PolylineOptions().add(pos1,pos2).width(5).color(Color.RED));
    }
}
