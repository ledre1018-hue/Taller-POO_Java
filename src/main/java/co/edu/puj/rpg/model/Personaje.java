package co.edu.puj.rpg.model;

import co.edu.puj.rpg.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.exception.VidaInsuficienteException;
import co.edu.puj.rpg.model.enums.EstadoPersonaje;
import co.edu.puj.rpg.model.enums.TipoPersonaje;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Personaje {

    private static final int CAPACIDAD_HABILIDADES = 5;

    private String nombre;
    private TipoPersonaje tipoPersonaje;
    private EstadoPersonaje estado;
    private int nivel;
    private int vida;
    private int mana;
    private Set<String> habilidades;

    public Personaje(String nombre, TipoPersonaje tipoPersonaje) {
        this.nombre = nombre;
        this.tipoPersonaje = tipoPersonaje;
        this.estado = EstadoPersonaje.VIVO;
        this.nivel = 1;
        this.vida = 100;
        this.mana = 50;
        this.habilidades = new HashSet<>();
    }

    public boolean aprenderHabilidad(String habilidad) throws CapacidadExcedidaException {
        if (this.habilidades.contains(habilidad)) {
            return false;
        }
        if (this.habilidades.size() >= CAPACIDAD_HABILIDADES) {
            throw new CapacidadExcedidaException(
                    "El libro de habilidades está lleno (máximo " + CAPACIDAD_HABILIDADES + ").");
        }
        return this.habilidades.add(habilidad);
    }

    public Set<String> obtenerHabilidades() {
        return Collections.unmodifiableSet(this.habilidades);
    }

    public void lanzarHechizo(int costoMana) throws SinManaException {
        if (costoMana > this.mana) {
            throw new SinManaException(String.format(
                    "%s no tiene maná suficiente: necesita %d y dispone de %d.", this.nombre, costoMana, this.mana));
        }
        this.mana -= costoMana;
    }

    public void consumirVida(int costoVida) {
        if (costoVida >= this.vida) {
            throw new VidaInsuficienteException(String.format(
                    "%s no puede pagar %d de vida con %d puntos actuales.", this.nombre, costoVida, this.vida));
        }
        this.vida -= costoVida;
    }

    public int calcularAtaque() {
        return (int) Math.round(this.nivel * 5 * (1 + this.tipoPersonaje.getBonificadorAtaqueBase()));
    }

    public void recibirDano(int dano) {
        int real = (int) Math.round(dano * (1 - this.tipoPersonaje.getBonificadorDefensaBase()));
        this.vida = Math.max(0, this.vida - real);
        if (this.vida == 0) {
            this.estado = EstadoPersonaje.DERROTADO;
        }
    }

    public void setEstado(EstadoPersonaje estado) {
        if (estado != null) {
            this.estado = estado;
        }
    }

    public String getNombre() { return this.nombre; }
    public TipoPersonaje getTipoPersonaje() { return this.tipoPersonaje; }
    public EstadoPersonaje getEstado() { return this.estado; }
    public int getNivel() { return this.nivel; }
    public int getVida() { return this.vida; }
    public int getMana() { return this.mana; }
}
