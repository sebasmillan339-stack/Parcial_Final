package gremio;

import static org.junit.jupiter.api.Assertions.*;
import gremio.modelo.Aventurero;
import gremio.modelo.Guerrero;
import gremio.modelo.Mago;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MainTest {

    private List<Aventurero> crearLista() {
        List<Aventurero> lista = new ArrayList<>();
        lista.add(new Guerrero("A", 3, 30));
        lista.add(new Guerrero("B", 4, 20));
        lista.add(new Mago("C", 9, 10, 3));
        return lista;
    }

    @Test
    void reporte1CuentaHeroesPorClase() {
        String reporte = Main.reporteConteoPorClase(crearLista());
        assertTrue(reporte.contains("Guerrero: 2"));
        assertTrue(reporte.contains("Mago: 1"));
    }

    @Test
    void reporte2DaMejorHeroeYPromedio() {
        String reporte = Main.reporteEstadisticas(crearLista());
        assertTrue(reporte.contains("mayor nivel: C (nivel 9)"));
        assertTrue(reporte.contains("20.0"));
    }

    @Test
    void listaVaciaNoRompeLosReportes() {
        List<Aventurero> vacia = new ArrayList<>();
        assertTrue(Main.reporteConteoPorClase(vacia).contains("Aún no hay héroes"));
        assertTrue(Main.reporteEstadisticas(vacia).contains("Aún no hay héroes"));
    }

    @Test
    void generarReportesIncluyeLosDos() {
        String todo = Main.generarReportes(crearLista());
        assertTrue(todo.contains("REPORTE 1"));
        assertTrue(todo.contains("REPORTE 2"));
    }
}
