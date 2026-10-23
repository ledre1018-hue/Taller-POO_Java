package co.edu.puj.rpg.engine;

import java.util.HashMap;
import java.util.Map;

public class Bestiario {

    private Map<String, Integer> derrotados = new HashMap<>();

    public boolean registrarVictoria(String enemigo) {
        boolean primera = !this.derrotados.containsKey(enemigo);
        this.derrotados.put(enemigo, this.derrotados.getOrDefault(enemigo, 0) + 1);
        return primera;
    }

    public int contarDerrotas(String enemigo) {
        return this.derrotados.getOrDefault(enemigo, 0);
    }

    public Map<String, Integer> getDerrotados() {
        return this.derrotados;
    }

    public String generarReporte() {
        if (this.derrotados.isEmpty()) {
            return "El bestiario está vacío.\n";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : this.derrotados.entrySet()) {
            sb.append(String.format("%-15s derrotado %d vez/veces%n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
