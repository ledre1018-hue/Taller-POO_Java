package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    private static final int CURA_POCION = 30;
    private static final int CURA_POCION_GRANDE = 80;
    private static final double COSTO_POCION_GRANDE = 25.0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        Enemigo enemigo = new Enemigo("Orco Bruto", 60, 10, 20.0, 60);



        StringBuilder historial = new StringBuilder();
        int ronda = 0;
        boolean huyo = false;

        System.out.println("=== ARENA DE HEROES ===");
        System.out.printf("%s se enfrenta a %s!%n", heroe.getNombre(), enemigo.getNombre());

        combate:
        do {
            ronda++;
            boolean consumioTurno = false;

            System.out.printf("%n----- RONDA %d -----%n", ronda);
            System.out.println("1. Atacar");
            System.out.println("2. Usar Pocion");
            System.out.println("3. Ver Estado");
            System.out.println("4. Huir");
            System.out.print("Elige una opcion: ");
            int opcion = leerOpcion(sc);

            if (opcion < 1 || opcion > 4) {
                System.out.println("Opcion invalida. Ingresa un numero del 1 al 4.");
                historial.append(String.format("Ronda %d: opcion invalida%n", ronda));
            } else {
                switch (opcion) {
                    case 1: {
                        int vidaAntes = enemigo.getPuntosVida();
                        heroe.atacar(enemigo);
                        int dano = vidaAntes - enemigo.getPuntosVida();
                        historial.append(String.format("Ronda %d: %s ataca%s y causa %d de dano a %s%n",
                                ronda, heroe.getNombre(),
                                heroe.isUltimoGolpeCritico() ? " (GOLPE CRITICO)" : "",
                                dano, enemigo.getNombre()));
                        consumioTurno = true;

                        if (!enemigo.isEstaVivo()) {
                            historial.append(String.format("Ronda %d: %s ha sido derrotado%n", ronda, enemigo.getNombre()));
                            heroe.setOro(heroe.getOro() + enemigo.getOro());
                            historial.append(String.format("Ronda %d: %s gana %.2f de oro%n",
                                    ronda, heroe.getNombre(), enemigo.getOro()));
                            int nivelAntes = heroe.getNivel();
                            boolean subio = heroe.ganarExperiencia(enemigo.getExperienciaRecompensa());
                            historial.append(String.format("Ronda %d: %s gana %d de experiencia%n",
                                    ronda, heroe.getNombre(), enemigo.getExperienciaRecompensa()));
                            if (subio) {
                                historial.append(String.format("Ronda %d: SUBIDA DE NIVEL %d -> %d%n",
                                        ronda, nivelAntes, heroe.getNivel()));
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("--- POCIONES ---");
                        System.out.printf("1. Usar Pocion (+%d PV) x%d%n", CURA_POCION, heroe.getPociones());
                        System.out.printf("2. Usar Pocion Grande (+%d PV) x%d%n", CURA_POCION_GRANDE, heroe.getPocionesGrandes());
                        System.out.printf("3. Tienda: comprar Pocion Grande de Salud (%.2f oro)%n", COSTO_POCION_GRANDE);
                        System.out.println("4. Volver");
                        System.out.print("Elige: ");
                        int sub = leerOpcion(sc);

                        if (sub == 1 || sub == 2) {
                            boolean grande = (sub == 2);
                            if (heroe.consumirPocion(grande)) {
                                int vidaAntes = heroe.getPuntosVida();
                                heroe.usarPocion(grande ? CURA_POCION_GRANDE : CURA_POCION);
                                historial.append(String.format("Ronda %d: %s usa una pocion%s y recupera %d PV%n",
                                        ronda, heroe.getNombre(), grande ? " grande" : "",
                                        heroe.getPuntosVida() - vidaAntes));
                                consumioTurno = true;
                            } else {
                                System.out.println("No tienes pociones de ese tipo.");
                                historial.append(String.format("Ronda %d: intento de usar pocion sin existencias%n", ronda));
                            }
                        } else if (sub == 3) {
                            // Reto 1: la compra llama obligatoriamente a setOro() con el nuevo saldo
                            System.out.printf("Oro disponible: %.2f%n", heroe.getOro());
                            double nuevoSaldo = heroe.getOro() - COSTO_POCION_GRANDE;
                            if (heroe.setOro(nuevoSaldo)) {
                                heroe.agregarPocionGrande();
                                System.out.printf("Compra exitosa. Oro restante: %.2f%n", heroe.getOro());
                                historial.append(String.format("Ronda %d: %s compra una Pocion Grande de Salud (-%.2f oro)%n",
                                        ronda, heroe.getNombre(), COSTO_POCION_GRANDE));
                            } else {
                                System.out.println("Transaccion rechazada: no se entrega el item.");
                                historial.append(String.format("Ronda %d: compra rechazada por saldo insuficiente%n", ronda));
                            }
                        } else if (sub == 4) {
                            System.out.println("Vuelves al combate.");
                        } else {
                            System.out.println("Opcion invalida.");
                            historial.append(String.format("Ronda %d: opcion invalida en el submenu%n", ronda));
                        }
                        break;
                    }
                    case 3:
                        mostrarEstado(heroe, enemigo);
                        break;
                    case 4:
                        historial.append(String.format("Ronda %d: %s huye del combate%n", ronda, heroe.getNombre()));
                        huyo = true;
                        break combate;
                    default:
                        break;
                }
            }

            if (consumioTurno && enemigo.isEstaVivo()) {
                int vidaAntes = heroe.getPuntosVida();
                enemigo.atacar(heroe);
                historial.append(String.format("Ronda %d: %s ataca y causa %d de dano a %s%n",
                        ronda, enemigo.getNombre(), vidaAntes - heroe.getPuntosVida(), heroe.getNombre()));
                if (!heroe.isEstaVivo()) {
                    historial.append(String.format("Ronda %d: %s ha caido en batalla%n", ronda, heroe.getNombre()));
                }
            }

            System.out.println("\n== HISTORIAL DE BATALLA ==");
            System.out.print(historial);

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo());

        System.out.println("\n=======================");
        if (huyo) {
            System.out.println("Has huido de la arena.");
        } else if (heroe.isEstaVivo()) {
            System.out.printf("VICTORIA! %s derroto a %s.%n", heroe.getNombre(), enemigo.getNombre());
        } else {
            System.out.printf("DERROTA... %s fue vencido por %s.%n", heroe.getNombre(), enemigo.getNombre());
        }
        mostrarEstado(heroe, enemigo);
        sc.close();
    }

    public static void ejecutarAccion(Personaje heroe) {
        // Mutacion que viola las reglas de negocio si el setter no esta validado
        heroe.setPuntosVida(999999); // Excede vidaMaxima
        heroe.setPuntosVida(-100);   // Vida negativa sin actualizar estaVivo a false
    }

    private static void pruebaLimites() {
        int[] valores = {-10, 0, 100000};
        Personaje p = new Personaje("Prueba", 100, 15, 50.0);
        for (int v : valores) {
            p.setPuntosVida(v);
            System.out.printf("setPuntosVida(%d) -> vida=%d, vivo=%b%n", v, p.getPuntosVida(), p.isEstaVivo());
        }
        for (int v : valores) {
            boolean ok = p.setOro(v);
            System.out.printf("setOro(%d) -> aplicado=%b, oro=%.2f%n", v, ok, p.getOro());
        }
        ejecutarAccion(p);
        System.out.printf("ejecutarAccion -> vida=%d, vivo=%b%n", p.getPuntosVida(), p.isEstaVivo());
    }

    private static int leerOpcion(Scanner sc) {
        if (!sc.hasNextLine()) {
            return 4;
        }
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void mostrarEstado(Personaje heroe, Enemigo enemigo) {
        System.out.println("\n------ ESTADO ------");
        System.out.printf("Heroe: %s | Nivel: %d | XP: %d/%d%n",
                heroe.getNombre(), heroe.getNivel(), heroe.getExperiencia(), heroe.experienciaParaSiguienteNivel());
        System.out.printf("Vida: %d/%d | Ataque: %d | Vivo: %b%n",
                heroe.getPuntosVida(), heroe.getVidaMaxima(), heroe.getPuntosAtaque(), heroe.isEstaVivo());
        System.out.printf("Oro: %.2f | Pociones: %d | Pociones Grandes: %d%n",
                heroe.getOro(), heroe.getPociones(), heroe.getPocionesGrandes());
        System.out.printf("Enemigo: %s | Vida: %d/%d | Ataque: %d%n",
                enemigo.getNombre(), enemigo.getPuntosVida(), enemigo.getVidaMaxima(), enemigo.getPuntosAtaque());
        System.out.println("--------------------");
    }
}
