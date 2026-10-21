package co.edu.puj.rpg.modelo;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posX, int posY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posX, posY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        aplicarGolpe(objetivo, this.ataqueBase + this.fuerzaEscudo, "Golpe de Escudo");
    }

    @Override
    public void defender(int danoRecibido) {
        recibirDano(Math.max(1, danoRecibido - this.fuerzaEscudo / 2));
    }

    public int getFuerzaEscudo() { return this.fuerzaEscudo; }
}
