package com.taller.poo;

public class CuentaBancariaDebug {
    private String numeroCuenta;
    private double saldo;

    public CuentaBancariaDebug(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public static void main(String[] args) {
        CuentaBancariaDebug cuenta = new CuentaBancariaDebug("A-101", 500.0);
        System.out.printf("Cuenta: %s | Saldo: %.2f%n", cuenta.numeroCuenta, cuenta.saldo);
    }
}