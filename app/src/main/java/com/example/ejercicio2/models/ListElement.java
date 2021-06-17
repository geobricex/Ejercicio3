package com.example.ejercicio2.models;

public class ListElement {
    public String img;
    public String titulo;
    public String doi;
    public String fecha;
    public String anio;
    public String volumen;

    public ListElement() {
    }

    public ListElement(String img, String titulo, String doi, String fecha, String anio, String volumen) {
        this.img = img;
        this.titulo = titulo;
        this.doi = doi;
        this.fecha = fecha;
        this.anio = anio;
        this.volumen = volumen;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }
}
