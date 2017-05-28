package com.example.mand.mapasdegoogle;


        import android.os.Handler;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdate;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    Button bmapa;
    Button bterreno;
    Button bhibrido;
    Button binterior;
    Button position;
    Double incre = 0.0001d;
    Double lat=-33.86997d;
    Double lon=151.2089d;
    int splashTime=3000;
    private boolean moveCameraQuestion = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        bmapa = (Button)findViewById(R.id.bmapa);
        bterreno = (Button)findViewById(R.id.bterreno);
        bhibrido = (Button)findViewById(R.id.bhibrido);
        binterior = (Button)findViewById(R.id.binterior);
        position = (Button)findViewById(R.id.position);

        bmapa.setOnClickListener(this);
        bterreno.setOnClickListener(this);
        bhibrido.setOnClickListener(this);
        binterior.setOnClickListener(this);
        position.setOnClickListener(this);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View v) {
        moveCameraQuestion = false;
        switch (v.getId()){
            case R.id.position:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.86997, 151.2089), 18));
                mMap.addMarker(new MarkerOptions().position(new LatLng(-33.86997, 151.2089)).title("Sydney"));
             break;
            case R.id.bmapa:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.bhibrido:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                break;
            case R.id.bterreno:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.binterior:
                // Algunos edificios tienen mapa de interior. Hay que ponerse sobre ellos y directamente veremos las plantas
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(-33.86997, 151.2089), 18));

                // vamos a mover el mapa

               /* for(int i=0;i<10;i++) {

                    Thread th =new Thread(){
                        public void run(){

                            for(int timer=0;timer<3000;timer++){
                                int waited=0;

                                while(waited<splashTime){

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            for (int x=0;x<20;x++) {

                                                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat += incre, lon),18);
                                                mMap.animateCamera(update);

                                            } //fin del for


                                        }
                                    });
                                    waited+=20;
                                }
                            }

                        }

                    };
                    th.start();


                    // aqui termina el retardo


                }  //termina ciclo for
            */
                moveCameraQuestion = true;
                moverPantalla();
                break;
            default:
                break;


        }
    }
    public void moverPantalla(){
        Log.d("variable", "" + moveCameraQuestion);
        if(!moveCameraQuestion){
            return;
        }
        android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.scrollBy(0, -10), 2, null);
               // mMap.addMarker(new MarkerOptions().position(new LatLng(lat += 0.01, 151.2089)).title("Sydney"));
                moverPantalla();
            }
        }, 100);

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cast_dark))
                        .anchor(0.0f, 1.0f)
                        .position(latLng));

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "Has pulsado una marca", Toast.LENGTH_LONG).show();
                return false;
            }
        });



    }
}
