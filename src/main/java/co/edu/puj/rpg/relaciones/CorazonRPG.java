package co.edu.puj.rpg.relaciones;

public class CorazonRPG {

    private int saludBase;
    private int manaBase;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
    }

    public int getSaludBase() { return this.saludBase; }
    public int getManaBase() { return this.manaBase; }

    void modificarSalud(int cantidad) {
        this.saludBase = Math.max(0, this.saludBase + cantidad);
    }

    void modificarMana(int cantidad) {
        this.manaBase = Math.max(0, this.manaBase + cantidad);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CorazonRPG[salud=")
                .append(this.saludBase)
                .append(", mana=")
                .append(this.manaBase)
                .append("]")
                .toString();
    }
}
