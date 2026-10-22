package co.edu.puj.rpg.relaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Escuadron {

    private String nombre;
    private List<Personaje> miembros;

    public Escuadron(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public void reclutarPersonaje(Personaje p) {
        if (p == null) {
            System.out.println("No se puede reclutar un personaje nulo.");
            return;
        }
        if (this.miembros.contains(p)) {
            System.out.printf("%s ya pertenece al escuadrón %s.%n", p.getNombre(), this.nombre);
            return;
        }
        this.miembros.add(p);
        System.out.printf("%s fue reclutado en el escuadrón %s.%n", p.getNombre(), this.nombre);
    }

    public void expulsarPersonaje(Personaje p) {
        if (this.miembros.remove(p)) {
            System.out.printf("%s fue expulsado del escuadrón %s.%n", p.getNombre(), this.nombre);
        }
    }

    public String getNombre() { return this.nombre; }

    public List<Personaje> getMiembros() { return Collections.unmodifiableList(this.miembros); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Escuadron[nombre=").append(this.nombre).append(", miembros=");
        for (int i = 0; i < this.miembros.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(this.miembros.get(i).getNombre());
        }
        sb.append("]");
        return sb.toString();
    }
}
