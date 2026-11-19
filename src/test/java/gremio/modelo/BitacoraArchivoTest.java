package gremio.modelo;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class BitacoraArchivoTest {

    @Test
    void anexaSinBorrarLoAnterior(@TempDir Path carpeta) throws IOException {
        Path archivo = carpeta.resolve("prueba.txt");
        BitacoraArchivo bitacora = new BitacoraArchivo(archivo.toString());

        bitacora.anexar("primera");
        bitacora.anexar("segunda");

        List<String> lineas = Files.readAllLines(archivo);
        assertEquals(2, lineas.size());
        assertTrue(lineas.get(0).endsWith("primera"));
        assertTrue(lineas.get(1).endsWith("segunda"));
    }
}
