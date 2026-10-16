package com.taller.poo;

public class PruebaEstudiante {
    public static void main(String[] args) {
        Estudiante estudiante1 = new Estudiante("2024001", "Carlos Pérez", 4.5, true);
        String reporte = estudiante1.obtenerReporteAcademicos();
        System.out.println(reporte);
    }
}