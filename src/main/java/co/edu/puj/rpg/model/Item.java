package co.edu.puj.rpg.model;

import co.edu.puj.rpg.model.enums.RaridadItem;

public class Item {

    private String nombre;
    private int valorBase;
    private RaridadItem raridad;

    public Item(String nombre, int valorBase, RaridadItem raridad) {
        this.nombre = nombre;
        this.valorBase = valorBase;
        this.raridad = raridad;
    }

    public double getValorFinal() {
        return this.valorBase * this.raridad.getMultiplicadorStat();
    }

    public String getNombre() { return this.nombre; }
    public int getValorBase() { return this.valorBase; }
    public RaridadItem getRaridad() { return this.raridad; }
}
