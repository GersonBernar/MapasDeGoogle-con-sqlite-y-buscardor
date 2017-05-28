package com.example.mand.mapasdegoogle;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class sqlite extends SQLiteOpenHelper{
    String negocios = "CREATE TABLE negocios(idnegocio INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,lat TEXT,lng TEXT)";

    public sqlite(Context context,String name,SQLiteDatabase.CursorFactory cursor,int version){
        super(context,name,cursor,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(negocios);
        db.beginTransaction();
        try {
            llenarCoordenadasEstaticas();
            ContentValues values = new ContentValues();
            for (int i = 0;i<3;i++){
                Log.d("jose",""+i);
                values.put("nombre","Negocio "+(i+1));
                values.put("lat",coordenadas[i][0]);
                values.put("lng",coordenadas[i][1]);
                db.insert("negocios",null,values);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS negocios");
        onCreate(db);
    }
    double[][] coordenadas = new double[3][2];
    public void llenarCoordenadasEstaticas(){
        coordenadas[0][0] = 19.098335865690004;
        coordenadas[0][1] = -102.35534906387329;

        coordenadas[1][0] = 19.101823405486133;
        coordenadas[1][1] = -102.36225843429565;

        coordenadas[2][0] = 19.09306386367622;
        coordenadas[2][1] = -102.34276413917542;
    }

}
