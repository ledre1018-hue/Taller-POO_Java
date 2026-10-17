package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

public class Personaje {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;

    private int experiencia;
    private int pociones;
    private int pocionesGrandes;
    private boolean ultimoGolpeCritico;


    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.puntosAtaque = puntosAtaque;
        this.nivel = 1;
        this.experiencia = 0;
        this.pociones = 2;
        this.pocionesGrandes = 0;
        this.ultimoGolpeCritico = false;
        this.oro = 0.0;
        this.estaVivo = true;
        this.setOro(oro);
        this.setPuntosVida(vidaMaxima);
    }

    public Personaje(String nombre) {
        this(nombre, 100, 15, 50.0);
    }

    public Personaje(int puntosVida) {
        this.puntosVida = -50;
        this.estaVivo = true;
    }

    public void atacar(Enemigo enemigo) {
        this.ultimoGolpeCritico = Math.random() < 0.15;
        int dano = this.ultimoGolpeCritico ? this.puntosAtaque * 2 : this.puntosAtaque;
        enemigo.recibirDano(dano);
    }

    public void usarPocion(int cantidadRestaurada) {
        if (cantidadRestaurada > 0) {
            this.setPuntosVida(this.puntosVida + cantidadRestaurada);
        }
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano > 0) {
            this.setPuntosVida(this.puntosVida - cantidadDano);   // via setter
        }
    }

    public void agregarPocionGrande() {
        this.pocionesGrandes++;
    }

    public boolean consumirPocion(boolean grande) {
        if (grande) {
            if (this.pocionesGrandes > 0) {
                this.pocionesGrandes--;
                return true;
            }
        } else if (this.pociones > 0) {
            this.pociones--;
            return true;
        }
        return false;
    }


    public int experienciaParaSiguienteNivel() {
        return this.nivel * 50;   // umbral predefinido
    }

    public boolean ganarExperiencia(int puntos) {
        if (puntos <= 0) {
            return false;
        }
        this.experiencia += puntos;
        boolean subio = false;
        while (this.experiencia >= this.experienciaParaSiguienteNivel()) {
            this.experiencia -= this.experienciaParaSiguienteNivel();
            this.setNivel(this.nivel + 1);      // setter protegido
            this.vidaMaxima += 10;
            this.puntosAtaque += 3;
            this.setPuntosVida(this.vidaMaxima);
            subio = true;
        }
        return subio;
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

    public boolean setOro(double oro) {
        if (oro < 0.0) {
            System.out.println("Saldo insuficiente: operacion rechazada.");
            return false;
        }
        this.oro = oro;
        return true;
    }

    protected void setNivel(int nivel) {
        if (nivel < 1) {
            return;
        }
        this.nivel = nivel;
    }

    public String getNombre() { return this.nombre; }
    public int getPuntosVida() { return this.puntosVida; }
    public int getVidaMaxima() { return this.vidaMaxima; }
    public int getPuntosAtaque() { return this.puntosAtaque; }
    public int getNivel() { return this.nivel; }
    public double getOro() { return this.oro; }
    public boolean isEstaVivo() { return this.estaVivo; }

    public int getExperiencia() { return this.experiencia; }
    public int getPociones() { return this.pociones; }
    public int getPocionesGrandes() { return this.pocionesGrandes; }
    public boolean isUltimoGolpeCritico() { return this.ultimoGolpeCritico; }
}
