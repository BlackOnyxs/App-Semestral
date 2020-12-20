package com.example.appsemestral;

import java.sql.Timestamp;
import java.util.Date;

public class Articulos {
    private String titulo;
    private String contenido;
    private Date fecha;

    public Articulos() {
    }

    public Articulos(String titulo, String contenido, Timestamp fecha) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Articulos{" +
                "titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
