package com.cofradias.android.model.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaria on 27/04/2016.
 */
public class Procesion implements Serializable {

    private List<Coordenada> coordenadas;
    private String dia, horario, id_cofradia, imagenProcesion, nombreProcesion, pasos, ruta, salida, textoIntroProcesion;


    public List<Coordenada> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Coordenada> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getId_cofradia() {
        return id_cofradia;
    }

    public void setId_cofradia(String id_cofradia) {
        this.id_cofradia = id_cofradia;
    }

    public String getImagenProcesion() {
        return imagenProcesion;
    }

    public void setImagenProcesion(String imagenProcesion) {
        this.imagenProcesion = imagenProcesion;
    }

    public String getNombreProcesion() {
        return nombreProcesion;
    }

    public void setNombreProcesion(String nombreProcesion) {
        this.nombreProcesion = nombreProcesion;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getTextoIntroProcesion() {
        return textoIntroProcesion;
    }

    public void setTextoIntroProcesion(String textoIntroProcesion) {
        this.textoIntroProcesion = textoIntroProcesion;
    }


    public Procesion() {
        this.coordenadas = new ArrayList<Coordenada>();
    }
}
