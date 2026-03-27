package com.example.prestamollaves.model;

public class PrestamoLlave {

    private String nombreSolicitante;
    private String salon;
    private String turno;

    public PrestamoLlave(String nombreSolicitante, String salon, String turno) {
        this.nombreSolicitante = nombreSolicitante;
        this.salon = salon;
        this.turno = turno;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return nombreSolicitante + " | " + salon + " | " + turno;
    }
}
