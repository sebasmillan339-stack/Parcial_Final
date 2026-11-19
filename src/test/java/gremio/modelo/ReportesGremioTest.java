package gremio.modelo;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReportesGremioTest {

    private List<Aventurero> crearLista() {
        List<Aventurero> lista = new ArrayList<>();
        lista.add(new Guerrero("A", 3, 30));
        lista.add(new Guerrero("B", 4, 20));
        lista.add(new Mago("C", 9, 10, 3));
        return lista;
    }

    @Test
    void conteoPorClaseCuentaBien() {
        String reporte = ReportesGremio.conteoPorClase(crearLista());
        assertTrue(reporte.contains("Guerrero: 2"));
        assertTrue(reporte.contains("Mago: 1"));
    }

    @Test
    void estadisticasDanMejorHeroeYPromedio() {
        String reporte = ReportesGremio.estadisticas(crearLista());
        assertTrue(reporte.contains("mayor nivel: C (nivel 9)"));
        assertTrue(reporte.contains("20.0"));
    }

    @Test
    void listaVaciaNoRompeLosReportes() {
        List<Aventurero> vacia = new ArrayList<>();
        assertTrue(ReportesGremio.conteoPorClase(vacia).contains("Aún no hay héroes"));
        assertTrue(ReportesGremio.estadisticas(vacia).contains("Aún no hay héroes"));
    }
}
