package gremio.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DadoTest {

    @Test
    void d20SiempreEstaEntre1Y20() {
        Dado dado = new Dado();
        for (int i = 0; i < 200; i++) {
            int r = dado.lanzarD20();
            assertTrue(r >= 1 && r <= 20);
        }
    }

    @Test
    void mismaSemillaDaMismoResultado() {
        assertEquals(new Dado(42).lanzarD20(), new Dado(42).lanzarD20());
    }

    @Test
    void iniciativaSumaElNivel() {
        Guerrero g = new Guerrero("Aldric", 5, 30);
        int iniciativa = g.tirarIniciativa(new Dado(7));
        assertTrue(iniciativa >= 6 && iniciativa <= 25);
    }
}
