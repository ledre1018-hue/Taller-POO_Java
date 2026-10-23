package co.edu.puj.rpg;

import co.edu.puj.rpg.engine.Bestiario;
import co.edu.puj.rpg.engine.CombateEngine;
import co.edu.puj.rpg.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.model.PerfilJugador;
import co.edu.puj.rpg.model.Personaje;
import co.edu.puj.rpg.model.enums.TipoPersonaje;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static int lecturas = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Personaje> personajes = new ArrayList<>();
        Bestiario bestiario = new Bestiario();
        PerfilJugador perfil = new PerfilJugador("Jugador");
        CombateEngine engine = new CombateEngine();
        int opcion;

        do {
            System.out.println("\n=== ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Asignar Habilidad Única");
            System.out.println("3. Registrar Victoria en Bestiario");
            System.out.println("4. Ejecutar Ataque Especial");
            System.out.println("5. Consultar Bestiario y Logros");
            System.out.println("0. Salir");
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    crearPersonaje(scanner, personajes);
                    break;
                case 2:
                    asignarHabilidad(scanner, personajes);
                    break;
                case 3:
                    registrarVictoria(scanner, bestiario, perfil);
                    break;
                case 4:
                    ejecutarAtaque(scanner, personajes, engine);
                    break;
                case 5:
                    consultar(bestiario, perfil);
                    break;
                case 0:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.err.println("Opción inválida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void crearPersonaje(Scanner scanner, List<Personaje> personajes) {
        System.out.print("Nombre del personaje: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.err.println("El nombre no puede estar vacío.");
            return;
        }
        TipoPersonaje[] tipos = TipoPersonaje.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s%n", i + 1, tipos[i]);
        }
        int sel = leerEntero(scanner, "Seleccione el tipo: ");
        if (sel < 1 || sel > tipos.length) {
            System.err.println("Tipo inválido.");
            return;
        }
        Personaje p = new Personaje(nombre, tipos[sel - 1]);
        personajes.add(p);
        System.out.printf("Personaje %s (%s) creado. Estado: %s%n", p.getNombre(), p.getTipoPersonaje(), p.getEstado());
    }

    private static void asignarHabilidad(Scanner scanner, List<Personaje> personajes) {
        Personaje p = seleccionarPersonaje(scanner, personajes);
        if (p == null) {
            return;
        }
        System.out.print("Nombre de la habilidad: ");
        String habilidad = scanner.nextLine().trim();
        if (habilidad.isEmpty()) {
            System.err.println("El nombre no puede estar vacío.");
            return;
        }
        try {
            if (p.aprenderHabilidad(habilidad)) {
                System.out.printf("%s aprendió %s.%n", p.getNombre(), habilidad);
            } else {
                System.out.printf("La habilidad %s ya está aprendida.%n", habilidad);
            }
        } catch (CapacidadExcedidaException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void registrarVictoria(Scanner scanner, Bestiario bestiario, PerfilJugador perfil) {
        System.out.print("Nombre del enemigo derrotado: ");
        String enemigo = scanner.nextLine().trim();
        if (enemigo.isEmpty()) {
            System.err.println("El nombre no puede estar vacío.");
            return;
        }
        boolean primera = bestiario.registrarVictoria(enemigo);
        System.out.printf("%s derrotado %d vez/veces.%n", enemigo, bestiario.contarDerrotas(enemigo));
        if (primera) {
            String logro = "Primera victoria contra " + enemigo;
            perfil.registrarLogro(logro);
            System.out.println("Logro desbloqueado: " + logro);
        }
    }

    private static void ejecutarAtaque(Scanner scanner, List<Personaje> personajes, CombateEngine engine) {
        Personaje p = seleccionarPersonaje(scanner, personajes);
        if (p == null) {
            return;
        }
        int costo = leerEntero(scanner, "Costo de maná del ataque: ");
        if (costo < 0) {
            System.err.println("El costo no puede ser negativo.");
            return;
        }
        try {
            int dano = engine.ejecutarAtaqueEspecial(p, costo);
            System.out.printf("%s lanza su ataque especial y causa %d de daño. Maná restante: %d%n",
                    p.getNombre(), dano, p.getMana());
        } catch (SinManaException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void consultar(Bestiario bestiario, PerfilJugador perfil) {
        System.out.println("\n--- BESTIARIO ---");
        System.out.print(bestiario.generarReporte());
        System.out.println("--- LOGROS (orden alfabético) ---");
        if (perfil.listarLogrosOrdenados().isEmpty()) {
            System.out.println("Sin logros desbloqueados.");
        }
        for (String logro : perfil.listarLogrosOrdenados()) {
            System.out.println("- " + logro);
        }
    }

    private static Personaje seleccionarPersonaje(Scanner scanner, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.err.println("No hay personajes creados.");
            return null;
        }
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            System.out.printf("%d. %s (%s) | Vida: %d | Maná: %d | Estado: %s%n",
                    i + 1, p.getNombre(), p.getTipoPersonaje(), p.getVida(), p.getMana(), p.getEstado());
        }
        int sel = leerEntero(scanner, "Seleccione un personaje: ");
        if (sel < 1 || sel > personajes.size()) {
            System.err.println("Selección inválida.");
            return null;
        }
        return personajes.get(sel - 1);
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.err.println("Entrada inválida: ingrese un número entero.");
            } catch (NoSuchElementException e) {
                return 0;
            } finally {
                lecturas++;
            }
        }
    }
}
