package com.example.mand.mapasdegoogle;

/**
 * Created by MAND on 27/05/2017.
 */
public class Negocio {
    public int id;
    public String nombre;
    public String lat,lng;
    public Negocio(int id,String nombre,String lat,String lng){
        setId(id);
        setNombre(nombre);
        setLat(lat);
        setLng(lng);
    }
    public String toString(){
        return this.nombre;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
