package com.taller.poo;

public class VehiculoDebug {
    private int velocidadMaxima;
    private boolean encendido;

    public VehiculoDebug(int velocidadMaxima, boolean encendido) {
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public void imprimirEstado() {
        System.out.printf("Velocidad Máxima: %d | Encendido: %b%n", velocidadMaxima, encendido);
    }

    public static void main(String[] args) {
        VehiculoDebug v = new VehiculoDebug(200, true);
        v.imprimirEstado();
    }
}
