package co.edu.puj.rpg.modelo;

public class Mago extends Personaje {

    private static final int COSTO_MANA = 15;

    protected int puntosMana;

    public Mago(String nombre, int vida, int ataqueBase, int posX, int posY, int puntosMana) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.puntosMana = puntosMana;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (this.puntosMana >= COSTO_MANA) {
            this.puntosMana -= COSTO_MANA;
            aplicarGolpe(objetivo, this.ataqueBase * 2, "Bola de Fuego");
        } else {
            System.out.printf("%s no tiene mana suficiente.%n", this.nombre);
            atacar(objetivo);
        }
    }

    public int getPuntosMana() { return this.puntosMana; }
}
