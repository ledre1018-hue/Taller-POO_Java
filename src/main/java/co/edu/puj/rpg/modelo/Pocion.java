package co.edu.puj.rpg.modelo;

public class Pocion {

    private String nombre;
    private int curacion;

    public Pocion(String nombre, int curacion) {
        this.nombre = nombre;
        this.curacion = curacion;
    }

    public String getNombre() { return this.nombre; }
    public int getCuracion() { return this.curacion; }
}
