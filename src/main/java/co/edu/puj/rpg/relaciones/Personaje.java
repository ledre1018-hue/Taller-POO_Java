package co.edu.puj.rpg.relaciones;

public class Personaje {

    private String nombre;
    private int nivel;
    private Arma armaEquipada;
    private Gremio gremio;
    private CorazonRPG corazon;

    public Personaje(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.armaEquipada = null;
        this.gremio = null;
        this.corazon = new CorazonRPG(100, 50);
    }

    public void equiparArma(Arma nuevaArma) {
        if (nuevaArma == null) {
            System.out.println("No se puede equipar un arma nula.");
            return;
        }
        this.armaEquipada = nuevaArma;
        System.out.printf("%s equipa el arma %s.%n", this.nombre, nuevaArma.getNombre());
    }

    public void desequiparArma() {
        if (this.armaEquipada != null) {
            System.out.printf("%s retira el arma %s.%n", this.nombre, this.armaEquipada.getNombre());
            this.armaEquipada = null;
        } else {
            System.out.printf("%s no tiene arma para retirar.%n", this.nombre);
        }
    }

    public int calcularAtaqueTotal() {
        int ataqueBase = this.nivel * 5;
        if (this.armaEquipada != null) {
            ataqueBase += this.armaEquipada.getDanioBase();
        }
        return ataqueBase;
    }

    public void unirseAGremio(Gremio nuevoGremio) {
        if (nuevoGremio != null && nuevoGremio != this.gremio) {
            if (this.gremio != null) {
                abandonarGremio();
            }
            this.gremio = nuevoGremio;
            nuevoGremio.agregarMiembro(this);
        }
    }

    public void abandonarGremio() {
        if (this.gremio != null) {
            Gremio anterior = this.gremio;
            this.gremio = null;
            anterior.removerMiembro(this);
        }
    }

    public String getNombre() { return this.nombre; }
    public int getNivel() { return this.nivel; }
    public Arma getArmaEquipada() { return this.armaEquipada; }
    public Gremio getGremio() { return this.gremio; }
    public int getSalud() { return this.corazon.getSaludBase(); }
    public int getMana() { return this.corazon.getManaBase(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Personaje[nombre=").append(this.nombre);
        sb.append(", nivel=").append(this.nivel);
        sb.append(", ataqueTotal=").append(calcularAtaqueTotal());
        sb.append(", arma=").append(this.armaEquipada != null ? this.armaEquipada.toString() : "Desarmado");
        sb.append(", gremio=").append(this.gremio != null ? this.gremio.getNombre() : "Sin gremio");
        sb.append(", ").append(this.corazon.toString());
        sb.append("]");
        return sb.toString();
    }
}
