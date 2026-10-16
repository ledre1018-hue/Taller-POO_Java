package com.taller.poo;

/**
 * Impacto en memoria: Los tipos primitivos (double, int, boolean) se almacenan
 * en el stack con valores por defecto, consumiendo menos memoria. Los Wrappers
 * (Double, Integer, Boolean) se almacenan en el heap como objetos, consumen más
 * memoria y pueden ser null, lo que requiere validaciones adicionales.
 */
public class Vehiculo {
    private String marca;
    private String modelo;
    private double kilometraje;
    private int velocidadMaxima;
    private boolean encendido;

    public Vehiculo() {
        this.marca = "Genérico";
        this.modelo = "Sin Modelo";
        this.kilometraje = 0.0;
        this.velocidadMaxima = 120;
        this.encendido = false;
    }

    public Vehiculo(String marca, String modelo, double kilometraje, int velocidadMaxima, boolean encendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public void encenderMotor() { this.encendido = true; }
    public void apagarMotor() { this.encendido = false; }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getKilometraje() { return kilometraje; }
    public int getVelocidadMaxima() { return velocidadMaxima; }
    public boolean isEncendido() { return encendido; }
}
