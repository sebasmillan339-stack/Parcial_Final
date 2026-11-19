package gremio.modelo;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ClasesNuevasTest {

    @Test
    void mensajePaladin() {
        Aventurero a = new Paladin("Arthas", 8, 60, 15);
        assertEquals("El Paladín Arthas consume magia para imbuir su arma con un Castigo Divino de daño radiante.", a.ejecutarTactica());
        assertEquals("Puntos de fuerza: 15", a.getAtributoEspecial());
    }

    @Test
    void mensajeExplorador() {
        Aventurero a = new Explorador("Legolas", 6, 40, "Bosque");
        assertEquals("El Explorador Legolas aplica Marca del Cazador sobre el objetivo para maximizar su daño sostenido.", a.ejecutarTactica());
        assertEquals("Terreno favorecido: Bosque", a.getAtributoEspecial());
    }

    @Test
    void mensajePicaro() {
        Aventurero a = new Picaro("Vex", 4, 25, true);
        assertEquals("El Pícaro Vex aprovecha la ventaja táctica para asestar un Ataque Furtivo masivo y se reposiciona.", a.ejecutarTactica());
        assertEquals("Herramientas de ladrón: Sí", a.getAtributoEspecial());
    }

    @Test
    void mensajeBardo() {
        Aventurero a = new Bardo("Jaskier", 3, 22, "Laúd");
        assertEquals("El Bardo Jaskier toca su instrumento para otorgar Inspiración Bárdica a un aliado.", a.ejecutarTactica());
        assertEquals("Instrumento: Laúd", a.getAtributoEspecial());
    }

    @Test
    void atributosInvalidosLanzanExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Paladin("A", 1, 10, -1));
        assertThrows(IllegalArgumentException.class, () -> new Explorador("A", 1, 10, " "));
        assertThrows(IllegalArgumentException.class, () -> new Bardo("A", 1, 10, ""));
    }

    @Test
    void ochoClasesEnUnaSolaListaPolimorfica() {
        List<Aventurero> lista = new ArrayList<>();
        lista.add(new Guerrero("A", 1, 10));
        lista.add(new Mago("B", 1, 10, 1));
        lista.add(new Clerigo("C", 1, 10, "Pelor"));
        lista.add(new Monje("D", 1, 10, 1));
        lista.add(new Paladin("E", 1, 10, 1));
        lista.add(new Explorador("F", 1, 10, "Selva"));
        lista.add(new Picaro("G", 1, 10, false));
        lista.add(new Bardo("H", 1, 10, "Flauta"));
        for (Aventurero a : lista) {
            assertTrue(a.ejecutarTactica().contains(a.getNombre()));
        }
        assertEquals(8, lista.size());
    }
}
