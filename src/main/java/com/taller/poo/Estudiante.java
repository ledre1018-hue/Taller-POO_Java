package com.taller.poo;

public class Estudiante {
    private String identificacion;
    private String nombreCompleto;
    private double promedioCalificaciones;
    private boolean estaMatriculado;

    public Estudiante(String identificacion, String nombreCompleto, double promedioCalificaciones, boolean estaMatriculado) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.promedioCalificaciones = promedioCalificaciones;
        this.estaMatriculado = estaMatriculado;
    }

    public String obtenerReporteAcademicos() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Estudiante: %s%n", this.nombreCompleto));
        sb.append(String.format("Identificación: %s%n", this.identificacion));
        sb.append(String.format("Promedio: %.2f%n", this.promedioCalificaciones));
        sb.append(String.format("Matriculado: %b%n", this.estaMatriculado));
        return sb.toString();
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombreCompleto() { return nombreCompleto; }
    public double getPromedioCalificaciones() { return promedioCalificaciones; }
    public boolean isEstaMatriculado() { return estaMatriculado; }
}