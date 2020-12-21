package com.example.appsemestral;

import java.sql.Timestamp;
import java.util.Date;

public class Articulos {
    private String titulo;
    private String contenido;
    private Date fecha;
    private String id;

    public Articulos() {
    }

    public Articulos(String titulo, String contenido, Date fecha, String id) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fecha = fecha;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Articulos{" +
                "titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fecha=" + fecha +
                ", id='" + id + '\'' +
                '}';
    }
}
