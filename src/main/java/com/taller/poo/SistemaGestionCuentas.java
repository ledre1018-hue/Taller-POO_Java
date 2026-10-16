package com.taller.poo;

import java.util.Scanner;

public class SistemaGestionCuentas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("1001", "Carlos Pérez", 1500.0, 500.0);
        cuentas[1] = new CuentaBancaria("1002", "Ana Gómez", 200.0, 100.0);
        cuentas[2] = new CuentaBancaria("1003", "Luis Ramírez", 800.0, 300.0);

        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE CUENTAS ===");
            System.out.println("1. Depositar en una cuenta");
            System.out.println("2. Retirar de una cuenta");
            System.out.println("3. Ver reporte consolidado");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            String entrada = sc.nextLine();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el número de cuenta: ");
                    String numCuentaDep = sc.nextLine();
                    CuentaBancaria cuentaDep = buscarCuenta(cuentas, numCuentaDep);
                    if (cuentaDep != null) {
                        System.out.print("Ingrese el monto a depositar: ");
                        double montoDep = Double.parseDouble(sc.nextLine());
                        if (montoDep > 0) {
                            if (cuentaDep.depositar(montoDep)) {
                                System.out.printf("Depósito exitoso. Nuevo saldo: %s%n", ConversorMoneda.formatearMoneda(cuentaDep.getSaldo()));
                            } else {
                                System.out.println("Error al procesar el depósito.");
                            }
                        } else {
                            System.out.println("El monto debe ser mayor a cero.");
                        }
                    } else {
                        System.out.printf("Cuenta %s no encontrada.%n", numCuentaDep);
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el número de cuenta: ");
                    String numCuentaRet = sc.nextLine();
                    CuentaBancaria cuentaRet = buscarCuenta(cuentas, numCuentaRet);
                    if (cuentaRet != null) {
                        System.out.print("Ingrese el monto a retirar: ");
                        double montoRet = Double.parseDouble(sc.nextLine());
                        if (montoRet > 0) {
                            if (cuentaRet.retirar(montoRet)) {
                                System.out.printf("Retiro exitoso. Nuevo saldo: %s%n", ConversorMoneda.formatearMoneda(cuentaRet.getSaldo()));
                            } else {
                                System.out.println("Fondos insuficientes o monto inválido.");
                            }
                        } else {
                            System.out.println("El monto debe ser mayor a cero.");
                        }
                    } else {
                        System.out.printf("Cuenta %s no encontrada.%n", numCuentaRet);
                    }
                    break;

                case 3:
                    StringBuilder reporte = new StringBuilder();
                    double totalSaldos = 0;
                    reporte.append("\n=== REPORTE CONSOLIDADO ===\n");
                    for (int i = 0; i < cuentas.length; i++) {
                        reporte.append(String.format("Cuenta: %s | Titular: %s | Saldo: %s | Crédito: %s%n",
                                cuentas[i].getNumeroCuenta(), cuentas[i].getTitular(),
                                ConversorMoneda.formatearMoneda(cuentas[i].getSaldo()),
                                ConversorMoneda.formatearMoneda(cuentas[i].getLimiteCredito())));
                        totalSaldos += cuentas[i].getSaldo();
                    }
                    reporte.append(String.format("Total acumulado en el sistema: %s%n", ConversorMoneda.formatearMoneda(totalSaldos)));
                    System.out.println(reporte.toString());
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numeroCuenta) {
        for (CuentaBancaria c : cuentas) {
            if (c.getNumeroCuenta().equals(numeroCuenta)) {
                return c;
            }
        }
        return null;
    }
}