package co.edu.puj.rpg.relaciones;

public class Arma {

    private String nombre;
    private int danioBase;

    public Arma(String nombre, int danioBase) {
        this.nombre = nombre;
        this.danioBase = danioBase;
    }

    public String getNombre() { return this.nombre; }
    public int getDanioBase() { return this.danioBase; }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.nombre)
                .append(" (daño ")
                .append(this.danioBase)
                .append(")")
                .toString();
    }
}
