package com.example.mand.mapasdegoogle;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;

public class MostrarNegocios extends AppCompatActivity{
    private ListView listView;
    private ArrayList<Negocio> negocios;
    private Toolbar toolbar;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.mostarnegocios);
        listView = (ListView) findViewById(R.id.listView);
        toolbar = (Toolbar) findViewById(R.id.toolbarGeneral);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        llenarNegocios("SELECT * FROM negocios");
        inicializarLista();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MostrarNegocios.this,""+position,Toast.LENGTH_LONG).show();
                Negocio negocio = negocios.get(position);
                Intent intent = new Intent(MostrarNegocios.this,MapaPrueba.class);
                Log.d("maickol",""+negocio.nombre+" "+negocio.getLng()+" "+negocio.getLat());
                intent.putExtra("nombre",negocio.getNombre());
                intent.putExtra("lat",negocio.getLat());
                intent.putExtra("lng",negocio.getLng());
                startActivity(intent);
            }
        });
    }
    public void inicializarLista(){
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, negocios));
    }
    public void llenarNegocios(String sql){
        negocios = new ArrayList<>();
        sqlite bh = new sqlite(this,"negocios",null,4);
        SQLiteDatabase db = bh.getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                negocios.add(new Negocio(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
            }while(c.moveToNext());
        }
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu,menu);

        final MenuItem searchItem = menu.findItem(R.id.buscarCliente);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                negocios.removeAll(negocios);
                newText = "%"+newText+"%";
                llenarNegocios("SELECT * FROM negocios WHERE nombre LIKE '"+newText+"'");
                inicializarLista();
                return false;
            }
        });

        return true;
    }
}
