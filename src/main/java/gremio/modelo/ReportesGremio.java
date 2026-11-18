package gremio.modelo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class ReportesGremio {

    private ReportesGremio() { }

    // Reporte 1: cuántos héroes hay de cada clase
    public static String conteoPorClase(List<Aventurero> lista) {
        String reporte = "REPORTE 1 - Héroes por clase\n";
        if (lista.isEmpty()) {
            return reporte + "Aún no hay héroes registrados.";
        }
        Map<String, Integer> conteo = new TreeMap<>();
        for (Aventurero a : lista) {
            conteo.put(a.getClase(), conteo.getOrDefault(a.getClase(), 0) + 1);
        }
        for (Map.Entry<String, Integer> par : conteo.entrySet()) {
            reporte += "- " + par.getKey() + ": " + par.getValue() + "\n";
        }
        return reporte.trim();
    }

    // Reporte 2: héroe de mayor nivel y promedio de vida
    public static String estadisticas(List<Aventurero> lista) {
        String reporte = "REPORTE 2 - Estadísticas del gremio\n";
        if (lista.isEmpty()) {
            return reporte + "Aún no hay héroes registrados.";
        }
        Aventurero mejor = lista.get(0);
        int suma = 0;
        for (Aventurero a : lista) {
            if (a.getNivel() > mejor.getNivel()) {
                mejor = a;
            }
            suma += a.getPuntosVida();
        }
        double promedio = Math.round((double) suma / lista.size() * 10) / 10.0;
        return reporte + "Total de héroes: " + lista.size() + "\n"
                + "Héroe de mayor nivel: " + mejor.getNombre() + " (nivel " + mejor.getNivel() + ")\n"
                + "Promedio de puntos de vida: " + promedio;
    }
}
