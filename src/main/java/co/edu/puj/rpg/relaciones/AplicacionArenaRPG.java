package co.edu.puj.rpg.relaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionArenaRPG {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        List<Gremio> gremios = new ArrayList<>();
        List<Escuadron> escuadrones = new ArrayList<>();
        List<Personaje> personajes = new ArrayList<>();

        do {
            System.out.println("\n=== MENÚ ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Crear Gremio");
            System.out.println("3. Reclutar Personaje a Escuadrón");
            System.out.println("4. Equipar Arma a Personaje");
            System.out.println("5. Mostrar Estado Detallado (Personaje y Corazón RPG)");
            System.out.println("6. Salir");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    crearPersonaje(scanner, personajes);
                    break;
                case 2:
                    crearGremio(scanner, gremios);
                    break;
                case 3:
                    reclutarEnEscuadron(scanner, personajes, escuadrones);
                    break;
                case 4:
                    equiparArma(scanner, personajes);
                    break;
                case 5:
                    mostrarEstado(personajes, gremios, escuadrones);
                    break;
                case 6:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    private static void crearPersonaje(Scanner scanner, List<Personaje> personajes) {
        String nombre = leerTexto(scanner, "Nombre del personaje: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        personajes.add(new Personaje(nombre, 1));
        System.out.printf("Personaje %s creado.%n", nombre);
    }

    private static void crearGremio(Scanner scanner, List<Gremio> gremios) {
        String nombre = leerTexto(scanner, "Nombre del gremio: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        gremios.add(new Gremio(nombre));
        System.out.printf("Gremio %s creado.%n", nombre);
    }

    private static void reclutarEnEscuadron(Scanner scanner, List<Personaje> personajes, List<Escuadron> escuadrones) {
        Personaje p = seleccionarPersonaje(scanner, personajes);
        if (p == null) {
            return;
        }
        String nombreEscuadron = leerTexto(scanner, "Nombre del escuadrón: ");
        if (nombreEscuadron.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        Escuadron escuadron = null;
        for (Escuadron e : escuadrones) {
            if (e.getNombre().equalsIgnoreCase(nombreEscuadron)) {
                escuadron = e;
                break;
            }
        }
        if (escuadron == null) {
            escuadron = new Escuadron(nombreEscuadron);
            escuadrones.add(escuadron);
            System.out.printf("Escuadrón %s creado.%n", nombreEscuadron);
        }
        escuadron.reclutarPersonaje(p);
    }

    private static void equiparArma(Scanner scanner, List<Personaje> personajes) {
        Personaje p = seleccionarPersonaje(scanner, personajes);
        if (p == null) {
            return;
        }
        String nombreArma = leerTexto(scanner, "Nombre del arma: ");
        if (nombreArma.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        int danio = leerEntero(scanner, "Daño base del arma: ");
        if (danio < 0) {
            System.out.println("Daño inválido.");
            return;
        }
        p.equiparArma(new Arma(nombreArma, danio));
    }

    private static void mostrarEstado(List<Personaje> personajes, List<Gremio> gremios, List<Escuadron> escuadrones) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes creados.");
            return;
        }
        System.out.printf("%n%-12s %-6s %-8s %-8s %-8s %-14s %-20s%n",
                "Personaje", "Nivel", "Ataque", "Salud", "Mana", "Gremio", "Arma");
        for (Personaje p : personajes) {
            System.out.printf("%-12s %-6d %-8d %-8d %-8d %-14s %-20s%n",
                    p.getNombre(), p.getNivel(), p.calcularAtaqueTotal(), p.getSalud(), p.getMana(),
                    p.getGremio() != null ? p.getGremio().getNombre() : "-",
                    p.getArmaEquipada() != null ? p.getArmaEquipada().getNombre() : "-");
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n--- DETALLE DE PERSONAJES ---\n");
        for (Personaje p : personajes) {
            reporte.append(p).append("\n");
        }
        reporte.append("\n--- GREMIOS ---\n");
        for (Gremio g : gremios) {
            reporte.append(g).append("\n");
        }
        reporte.append("\n--- ESCUADRONES ---\n");
        for (Escuadron e : escuadrones) {
            reporte.append(e).append("\n");
        }
        System.out.print(reporte);
    }

    private static Personaje seleccionarPersonaje(Scanner scanner, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes creados.");
            return null;
        }
        for (int i = 0; i < personajes.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, personajes.get(i).getNombre());
        }
        int sel = leerEntero(scanner, "Seleccione un personaje: ");
        if (sel < 1 || sel > personajes.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return personajes.get(sel - 1);
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            return 6;
        }
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine().trim();
    }
}
