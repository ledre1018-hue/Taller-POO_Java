package co.edu.puj.rpg.engine;

import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.model.Personaje;

public class CombateEngine {

    public int ejecutarAtaqueEspecial(Personaje atacante, int costoMana) throws SinManaException {
        atacante.lanzarHechizo(costoMana);
        return atacante.calcularAtaque() * 2;
    }
}
