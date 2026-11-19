package gremio.modelo;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AventureroTest {

    @Test
    void guerreroGuardaSusDatos() {
        Guerrero g = new Guerrero("Brienne", 5, 45, true);
        assertEquals("Brienne", g.getNombre());
        assertEquals(5, g.getNivel());
        assertEquals(45, g.getPuntosVida());
        assertEquals("Armadura pesada: Sí", g.getAtributoEspecial());
    }

    @Test
    void nombreVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Mago("   ", 1, 8, 2));
    }

    @Test
    void nivelFueraDeRangoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Monje("Ryu", 0, 20, 3));
        assertThrows(IllegalArgumentException.class, () -> new Monje("Ryu", 21, 20, 3));
    }

    @Test
    void vidaNoPositivaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Clerigo("Ana", 2, 0, "Pelor"));
    }

    @Test
    void clerigoSinDiosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Clerigo("Ana", 2, 10, " "));
    }

    @Test
    void sobrecargaDeConstructorGuerrero() {
        Guerrero g = new Guerrero("Tormund", 2, 20);
        assertEquals("Armadura pesada: No", g.getAtributoEspecial());
    }

    @Test
    void sobrecargaDeRecibirDanio() {
        Mago m = new Mago("Elminster", 10, 30, 4);
        m.recibirDanio(10);
        assertEquals(20, m.getPuntosVida());
        m.recibirDanio(10, 4);
        assertEquals(14, m.getPuntosVida());
        m.recibirDanio(100);
        assertEquals(0, m.getPuntosVida());
    }

    @Test
    void rangosSegunNivel() {
        assertEquals("Héroe local", new Guerrero("A", 4, 10).getRango());
        assertEquals("Héroe del reino", new Guerrero("A", 10, 10).getRango());
        assertEquals("Maestro del reino", new Guerrero("A", 16, 10).getRango());
        assertEquals("Maestro del mundo", new Guerrero("A", 20, 10).getRango());
    }

    @Test
    void polimorfismoDevuelveCadaMensaje() {
        List<Aventurero> lista = new ArrayList<>();
        lista.add(new Guerrero("Aldric", 3, 30, true));
        lista.add(new Mago("Mirel", 3, 15, 3));
        lista.add(new Clerigo("Sera", 3, 22, "Pelor"));
        lista.add(new Monje("Kai", 3, 24, 5));

        assertEquals("El Guerrero Aldric usa Oleada de Acción para realizar múltiples ataques físicos continuos.", lista.get(0).ejecutarTactica());
        assertEquals("El Mago Mirel gasta un espacio de conjuro para lanzar una poderosa Bola de Fuego.", lista.get(1).ejecutarTactica());
        assertEquals("El Clérigo Sera usa Canalizar Divinidad para curar a sus aliados y expulsar muertos vivientes.", lista.get(2).ejecutarTactica());
        assertEquals("El Monje Kai gasta puntos de Ki para ejecutar una Ráfaga de Golpes y un Ataque Aturdidor.", lista.get(3).ejecutarTactica());
    }
}
