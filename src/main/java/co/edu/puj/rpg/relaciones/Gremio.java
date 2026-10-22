package co.edu.puj.rpg.relaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gremio {

    private String nombre;
    private List<Personaje> miembros;

    public Gremio(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public void agregarMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        if (!this.miembros.contains(p)) {
            this.miembros.add(p);
            System.out.printf("%s se une al gremio %s.%n", p.getNombre(), this.nombre);
            if (p.getGremio() != this) {
                p.unirseAGremio(this);
            }
        }
    }

    public void removerMiembro(Personaje p) {
        if (p != null && this.miembros.remove(p)) {
            System.out.printf("%s abandona el gremio %s.%n", p.getNombre(), this.nombre);
            if (p.getGremio() == this) {
                p.abandonarGremio();
            }
        }
    }

    public String getNombre() { return this.nombre; }

    public List<Personaje> getMiembros() { return Collections.unmodifiableList(this.miembros); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gremio[nombre=").append(this.nombre).append(", miembros=");
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
