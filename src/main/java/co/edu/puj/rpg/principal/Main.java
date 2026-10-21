package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Inventario;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.Pocion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int TAMANO = 5;
    private static final int COFRE_X = 2;
    private static final int COFRE_Y = 2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random azar = new Random();

        char[][] tablero = new char[TAMANO][TAMANO];
        List<Personaje> heroes = new ArrayList<>();
        List<Personaje> enemigos = new ArrayList<>();
        Inventario<Pocion> inventario = new Inventario<>();

        heroes.add(new Guerrero("Thorin", 120, 15, 0, 0, 10));
        heroes.add(new Mago("Gandalf", 80, 25, 0, 1, 50));

        enemigos.add(new Guerrero("Orco", 60, 12, 4, 0, 6));
        enemigos.add(new Guerrero("Trasgo", 50, 10, 0, 4, 4));
        enemigos.add(new Mago("Brujo", 40, 15, 4, 4, 30));

        inventario.agregar(new Pocion("Pocion pequena", 30));
        inventario.agregar(new Pocion("Pocion mediana", 40));
        inventario.agregar(new Pocion("Pocion grande", 60));

        Personaje principal = heroes.get(0);
        boolean cofreAlcanzado = false;
        int ronda = 0;

        System.out.println("=== ARENA RPG ===");

        do {
            ronda++;
            actualizarTablero(tablero, heroes, enemigos);
            System.out.printf("%n===== RONDA %d =====%n", ronda);
            mostrarTablero(tablero);
            mostrarEstadisticas("HEROES", heroes);
            mostrarEstadisticas("ENEMIGOS", enemigos);

            for (Personaje heroe : heroes) {
                if (!heroe.estaVivo()) {
                    continue;
                }
                if (vivos(enemigos).isEmpty()) {
                    break;
                }

                boolean turnoCompleto = false;
                while (!turnoCompleto) {
                    System.out.printf("%n--- Turno de %s ---%n", heroe.getNombre());
                    System.out.println("1. Atacar");
                    System.out.println("2. Usar Inventario");
                    System.out.println("3. Mover en Tablero");
                    int opcion = leerEntero(sc, "Elige una opcion: ");

                    switch (opcion) {
                        case 1:
                            turnoCompleto = atacar(sc, heroe, enemigos);
                            break;
                        case 2:
                            turnoCompleto = usarInventario(sc, heroe, inventario);
                            break;
                        case 3:
                            turnoCompleto = mover(sc, heroe, heroes, enemigos);
                            break;
                        default:
                            System.out.println("Opcion invalida. Ingresa un numero del 1 al 3.");
                            break;
                    }
                }

                actualizarTablero(tablero, heroes, enemigos);
                mostrarTablero(tablero);

                if (principal.estaVivo() && principal.getPosicionX() == COFRE_X
                        && principal.getPosicionY() == COFRE_Y) {
                    cofreAlcanzado = true;
                    break;
                }
            }

            if (!cofreAlcanzado) {
                System.out.printf("%n--- Turno enemigo ---%n");
                for (Personaje enemigo : enemigos) {
                    if (!enemigo.estaVivo()) {
                        continue;
                    }
                    List<Personaje> objetivos = vivos(heroes);
                    if (objetivos.isEmpty()) {
                        break;
                    }
                    Personaje objetivo = objetivos.get(azar.nextInt(objetivos.size()));
                    enemigo.realizarAtaqueEspecial(objetivo);
                }
            }

            for (Personaje p : heroes) {
                if (!p.estaVivo()) {
                    System.out.printf("%s ha caido en batalla.%n", p.getNombre());
                }
            }
            for (Personaje p : enemigos) {
                if (!p.estaVivo()) {
                    System.out.printf("%s ha sido derrotado.%n", p.getNombre());
                }
            }
            heroes.removeIf(p -> !p.estaVivo());
            enemigos.removeIf(p -> !p.estaVivo());

        } while (!heroes.isEmpty() && !enemigos.isEmpty() && vidaTotal(heroes) > 0 && !cofreAlcanzado);

        actualizarTablero(tablero, heroes, enemigos);
        System.out.println();
        mostrarTablero(tablero);
        System.out.println("\n=======================");
        if (cofreAlcanzado) {
            System.out.printf("VICTORIA: %s alcanzo el cofre.%n", principal.getNombre());
        } else if (enemigos.isEmpty()) {
            System.out.println("VICTORIA: todos los enemigos fueron eliminados.");
        } else {
            System.out.println("DERROTA: el escuadron heroe ha caido.");
        }
        sc.close();
    }

    public static void mostrarTablero(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
    }

    private static void actualizarTablero(char[][] tablero, List<Personaje> heroes, List<Personaje> enemigos) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = '.';
            }
        }
        tablero[COFRE_Y][COFRE_X] = 'C';
        for (Personaje e : enemigos) {
            if (e.estaVivo()) {
                tablero[e.getPosicionY()][e.getPosicionX()] = 'E';
            }
        }
        for (Personaje h : heroes) {
            if (h.estaVivo()) {
                tablero[h.getPosicionY()][h.getPosicionX()] = (h instanceof Guerrero) ? 'G' : 'M';
            }
        }
    }

    private static void mostrarEstadisticas(String titulo, List<Personaje> lista) {
        System.out.printf("%s:%n", titulo);
        for (Personaje p : lista) {
            if (p instanceof Mago m) {
                System.out.printf("  %s | Vida: %d | Mana: %d%n", m.getNombre(), m.getVida(), m.getPuntosMana());
            } else {
                System.out.printf("  %s | Vida: %d | Mana: -%n", p.getNombre(), p.getVida());
            }
        }
    }

    private static boolean atacar(Scanner sc, Personaje heroe, List<Personaje> enemigos) {
        List<Personaje> objetivos = vivos(enemigos);
        if (objetivos.isEmpty()) {
            return true;
        }
        System.out.println("Enemigos disponibles:");
        for (int i = 0; i < objetivos.size(); i++) {
            System.out.printf("%d. %s (vida %d)%n", i + 1, objetivos.get(i).getNombre(), objetivos.get(i).getVida());
        }
        int sel = leerEntero(sc, "Objetivo: ");
        if (sel < 1 || sel > objetivos.size()) {
            System.out.println("Objetivo invalido.");
            return false;
        }
        heroe.realizarAtaqueEspecial(objetivos.get(sel - 1));
        return true;
    }

    private static boolean usarInventario(Scanner sc, Personaje heroe, Inventario<Pocion> inventario) {
        List<Pocion> items = inventario.getItems();
        if (items.isEmpty()) {
            System.out.println("El inventario esta vacio.");
            return false;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s (+%d vida)%n", i + 1, items.get(i).getNombre(), items.get(i).getCuracion());
        }
        int sel = leerEntero(sc, "Pocion (0 para cancelar): ");
        if (sel == 0) {
            return false;
        }
        if (sel < 1 || sel > items.size()) {
            System.out.println("Seleccion invalida.");
            return false;
        }
        Pocion pocion = inventario.obtener(sel - 1);
        heroe.curar(pocion.getCuracion());
        items.remove(sel - 1);
        System.out.printf("%s usa %s y recupera %d de vida (vida: %d)%n",
                heroe.getNombre(), pocion.getNombre(), pocion.getCuracion(), heroe.getVida());
        return true;
    }

    private static boolean mover(Scanner sc, Personaje heroe, List<Personaje> heroes, List<Personaje> enemigos) {
        System.out.println("1. Arriba  2. Abajo  3. Izquierda  4. Derecha  0. Cancelar");
        int dir = leerEntero(sc, "Direccion: ");
        int nx = heroe.getPosicionX();
        int ny = heroe.getPosicionY();

        switch (dir) {
            case 0:
                return false;
            case 1:
                ny--;
                break;
            case 2:
                ny++;
                break;
            case 3:
                nx--;
                break;
            case 4:
                nx++;
                break;
            default:
                System.out.println("Direccion invalida.");
                return false;
        }

        if (nx < 0 || nx >= TAMANO || ny < 0 || ny >= TAMANO) {
            System.out.println("Movimiento fuera del tablero.");
            return false;
        }
        if (ocupada(nx, ny, heroes, enemigos)) {
            System.out.println("La casilla esta ocupada.");
            return false;
        }
        heroe.setPosicion(nx, ny);
        System.out.printf("%s se mueve a (%d, %d)%n", heroe.getNombre(), nx, ny);
        return true;
    }

    private static boolean ocupada(int x, int y, List<Personaje> heroes, List<Personaje> enemigos) {
        for (Personaje h : heroes) {
            if (h.estaVivo() && h.getPosicionX() == x && h.getPosicionY() == y) {
                return true;
            }
        }
        for (Personaje e : enemigos) {
            if (e.estaVivo() && e.getPosicionX() == x && e.getPosicionY() == y) {
                return true;
            }
        }
        return false;
    }

    private static List<Personaje> vivos(List<Personaje> lista) {
        List<Personaje> resultado = new ArrayList<>();
        for (Personaje p : lista) {
            if (p.estaVivo()) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private static int vidaTotal(List<Personaje> lista) {
        int suma = 0;
        for (Personaje p : lista) {
            suma += p.getVida();
        }
        return suma;
    }

    private static int leerEntero(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        if (!sc.hasNextLine()) {
            System.out.println("\nFin de entrada.");
            System.exit(0);
        }
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
