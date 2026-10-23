package co.edu.puj.rpg.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class PerfilJugador {

    private String nombre;
    private Set<String> logros;

    public PerfilJugador(String nombre) {
        this.nombre = nombre;
        this.logros = new TreeSet<>();
    }

    public boolean registrarLogro(String logro) {
        return this.logros.add(logro);
    }

    public Set<String> listarLogrosOrdenados() {
        return Collections.unmodifiableSet(this.logros);
    }

    public String getNombre() { return this.nombre; }
}
