package com.taller.poo;

public class CuentaBancaria {
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    /*
     * PREGUNTAS DE ANÁLISIS:
     * 1. El primitivo double 'saldo' se almacena en el stack con valor por defecto 0.0,
     *    mientras que el Wrapper Double 'limiteCredito' se almacena en el heap como objeto
     *    y su valor por defecto es null, consumiendo más memoria.
     * 2. Una operación aritmética sobre un Double no inicializado (null) lanza
     *    NullPointerException porque Java intenta hacer unboxing de null a double.
     *    En cambio, un double primitivo no inicializado vale 0.0 y opera normalmente.
     */

    public CuentaBancaria() {
        this.numeroCuenta = "000000";
        this.titular = "Sin Nombre";
        this.saldo = 0.0;
        this.limiteCredito = 0.0;
    }

    public CuentaBancaria(String numeroCuenta, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteCredito = 100.0;
    }

    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }

    public boolean depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        }
        return false;
    }

    public boolean retirar(double monto) {
        double disponible = this.limiteCredito != null ? this.saldo + this.limiteCredito : this.saldo;
        if (monto > 0 && monto <= disponible) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public Double getLimiteCredito() { return limiteCredito; }
}