package co.edu.puj.rpg.model.enums;

public enum TipoPersonaje {
    GUERRERO(0.20, 0.30),
    MAGO(0.40, 0.05),
    ARQUERO(0.30, 0.10);

    private final double bonificadorAtaqueBase;
    private final double bonificadorDefensaBase;

    private TipoPersonaje(double bonificadorAtaqueBase, double bonificadorDefensaBase) {
        this.bonificadorAtaqueBase = bonificadorAtaqueBase;
        this.bonificadorDefensaBase = bonificadorDefensaBase;
    }

    public double getBonificadorAtaqueBase() { return this.bonificadorAtaqueBase; }
    public double getBonificadorDefensaBase() { return this.bonificadorDefensaBase; }
}
