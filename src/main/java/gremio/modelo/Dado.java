package gremio.modelo;

import java.util.Random;

public class Dado {

    private final Random azar;

    public Dado() {
        azar = new Random();
    }

    // Sobrecarga: con semilla da siempre los mismos resultados (sirve para las pruebas)
    public Dado(long semilla) {
        azar = new Random(semilla);
    }

    public int lanzar(int caras) {
        return azar.nextInt(caras) + 1;
    }

    public int lanzarD20() {
        return lanzar(20);
    }
}
