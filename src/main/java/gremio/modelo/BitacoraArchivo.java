package gremio.modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BitacoraArchivo {

    public static final String RUTA_POR_DEFECTO = "registro_gremio.txt";
    private final String ruta;

    public BitacoraArchivo() {
        this(RUTA_POR_DEFECTO);
    }

    // Sobrecarga: permite otra ruta (útil para las pruebas)
    public BitacoraArchivo(String ruta) {
        this.ruta = ruta;
    }

    // El true del FileWriter significa "anexar": no borra lo anterior
    public void anexar(String texto) throws IOException {
        String marca = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta, StandardCharsets.UTF_8, true))) {
            escritor.write("[" + marca + "] " + texto);
            escritor.newLine();
        }
    }
}
