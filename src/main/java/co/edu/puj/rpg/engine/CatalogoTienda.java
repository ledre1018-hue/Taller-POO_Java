package co.edu.puj.rpg.engine;

import java.util.Map;
import java.util.TreeMap;

public class CatalogoTienda {

    private Map<String, Double> precios = new TreeMap<>();

    public void registrarPrecio(String item, double precio) {
        this.precios.put(item, precio);
    }

    public double consultarPrecio(String item) {
        if (this.precios.containsKey(item)) {
            return this.precios.get(item);
        }
        return -1.0;
    }

    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : this.precios.entrySet()) {
            sb.append(String.format("%-15s %.2f%n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
