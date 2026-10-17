package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;

public class Enemigo {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;
    private int experienciaRecompensa;

    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque, double oro, int experienciaRecompensa) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.puntosAtaque = puntosAtaque;
        this.nivel = 1;
        this.oro = oro;
        this.experienciaRecompensa = experienciaRecompensa;
        this.estaVivo = true;
        this.setPuntosVida(vidaMaxima);
    }

    public void atacar(Personaje heroe) {
        heroe.recibirDano(this.puntosAtaque);
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano > 0) {
            this.setPuntosVida(this.puntosVida - cantidadDano);
        }
    }

    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }

    public String getNombre() { return this.nombre; }
    public int getPuntosVida() { return this.puntosVida; }
    public int getVidaMaxima() { return this.vidaMaxima; }
    public int getPuntosAtaque() { return this.puntosAtaque; }
    public int getNivel() { return this.nivel; }
    public double getOro() { return this.oro; }
    public int getExperienciaRecompensa() { return this.experienciaRecompensa; }
    public boolean isEstaVivo() { return this.estaVivo; }
}
