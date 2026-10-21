package co.edu.puj.rpg.modelo;

public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    @Override
    public void defender(int danoRecibido) {
        recibirDano(danoRecibido);
    }

    public void atacar(Personaje p) {
        aplicarGolpe(p, this.ataqueBase, "ataque basico");
    }

    public void atacar(Personaje p, boolean modoFuria) {
        if (modoFuria) {
            aplicarGolpe(p, this.ataqueBase * 2, "ataque furioso");
        } else {
            atacar(p);
        }
    }

    protected void aplicarGolpe(Personaje objetivo, int dano, String tipo) {
        int antes = objetivo.vida;
        objetivo.defender(dano);
        System.out.printf("%s usa %s sobre %s: %d de dano (vida %d -> %d)%n",
                this.nombre, tipo, objetivo.nombre, antes - objetivo.vida, antes, objetivo.vida);
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public void recibirDano(int cantidad) {
        if (cantidad > 0) {
            this.vida = Math.max(0, this.vida - cantidad);
        }
    }

    public void curar(int cantidad) {
        if (cantidad > 0) {
            this.vida += cantidad;
        }
    }

    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }

    public String getNombre() { return this.nombre; }
    public int getVida() { return this.vida; }
    public int getAtaqueBase() { return this.ataqueBase; }
    public int getPosicionX() { return this.posicionX; }
    public int getPosicionY() { return this.posicionY; }
}
