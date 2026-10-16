package com.taller.poo;

public class Principal {
    public static void main(String[] args) {
        System.out.println("=== REPORTE DE VEHÍCULOS ===");
        Vehiculo v1 = new Vehiculo("Toyota", "Corolla", 15000.50, 180, true);
        Vehiculo v2 = new Vehiculo();
        Vehiculo v3 = new Vehiculo("Ford", "Fiesta", 8500.0, 160, false);

        System.out.printf("[1] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                v1.getMarca(), v1.getModelo(), v1.getKilometraje(), v1.getVelocidadMaxima(), v1.isEncendido());
        System.out.printf("[2] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                v2.getMarca(), v2.getModelo(), v2.getKilometraje(), v2.getVelocidadMaxima(), v2.isEncendido());
        System.out.printf("[3] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                v3.getMarca(), v3.getModelo(), v3.getKilometraje(), v3.getVelocidadMaxima(), v3.isEncendido());

        System.out.println("\n=== REPORTE DE ESTUDIANTES ===");
        Estudiante e1 = new Estudiante("2024001", "Carlos Pérez", 4.5, true);
        Estudiante e2 = new Estudiante("2024002", "Ana Gómez", 3.8, true);
        Estudiante e3 = new Estudiante("2024003", "Luis Ramírez", 2.9, false);

        System.out.println(e1.obtenerReporteAcademicos());
        System.out.println(e2.obtenerReporteAcademicos());
        System.out.println(e3.obtenerReporteAcademicos());

        System.out.println("=== REPORTE DE CUENTAS BANCARIAS ===");
        CuentaBancaria c1 = new CuentaBancaria("1001", "Carlos Pérez", 1500.0, 500.0);
        CuentaBancaria c2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria c3 = new CuentaBancaria();

        c1.depositar(250.0);
        c1.retirar(500.0);
        c2.depositar(50.0);

        System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                c1.getNumeroCuenta(), c1.getTitular(),
                ConversorMoneda.formatearMoneda(c1.getSaldo()),
                ConversorMoneda.formatearMoneda(c1.getLimiteCredito()));
        System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                c2.getNumeroCuenta(), c2.getTitular(),
                ConversorMoneda.formatearMoneda(c2.getSaldo()),
                ConversorMoneda.formatearMoneda(c2.getLimiteCredito()));
        System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                c3.getNumeroCuenta(), c3.getTitular(),
                ConversorMoneda.formatearMoneda(c3.getSaldo()),
                ConversorMoneda.formatearMoneda(c3.getLimiteCredito()));

        System.out.println("\n=== CONVERSIÓN DE MONEDA ===");
        double euros = ConversorMoneda.convertirDolaresAEuros(100.0, 0.92);
        System.out.printf("100 USD = %s EUR (tasa 0.92)%n", ConversorMoneda.formatearMoneda(euros));
    }
}