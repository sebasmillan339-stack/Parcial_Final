package gremio.controlador;

import gremio.Main;
import gremio.modelo.Aventurero;
import gremio.modelo.BitacoraArchivo;
import gremio.modelo.Bardo;
import gremio.modelo.Clerigo;
import gremio.modelo.Dado;
import gremio.modelo.Explorador;
import gremio.modelo.Guerrero;
import gremio.modelo.Mago;
import gremio.modelo.Monje;
import gremio.modelo.Paladin;
import gremio.modelo.Picaro;
import gremio.vista.VentanaGremio;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTextField;

public class ControladorGremio {

    private final List<Aventurero> gremio = new ArrayList<>();
    private final VentanaGremio vista;
    private final BitacoraArchivo bitacora = new BitacoraArchivo();
    private final Dado dado = new Dado();
    private int ronda = 0;

    public ControladorGremio(VentanaGremio vista) {
        this.vista = vista;
        conectarEventos();
        vista.mostrarCampoSegunClase(vista.getClaseSeleccionada());
        actualizarPaneles();
        vista.mostrarEstado("Bienvenido, aventurero. Registra a tu primer héroe.", true);
    }

    public List<Aventurero> getGremio() {
        return Collections.unmodifiableList(gremio);
    }

    private void conectarEventos() {
        vista.addRegistrarListener(e -> registrarAventurero());
        vista.addSimularListener(e -> simularCombate());
        vista.addReportesListener(e -> mostrarReportes());
        vista.addEliminarListener(e -> eliminarAventurero());
        vista.addLimpiarListener(e -> {
            vista.limpiarCronica();
            vista.mostrarEstado("Crónica limpiada.", true);
        });
        vista.addClaseListener(e -> {
            vista.limpiarAtributo();
            vista.mostrarCampoSegunClase(vista.getClaseSeleccionada());
        });
        soloNumeros(vista.getTxtNivel());
        soloNumeros(vista.getTxtVida());
        vista.getTxtAtributo().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (claseNumerica() && !esDigitoOControl(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    // ---------- Validaciones al teclear ----------
    private boolean esDigitoOControl(char c) {
        return Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE;
    }

    private boolean claseNumerica() {
        String clase = vista.getClaseSeleccionada();
        return clase.equals("Mago") || clase.equals("Monje") || clase.equals("Paladín");
    }

    private void soloNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!esDigitoOControl(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    private int leerEntero(String texto, String campo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" debe ser un número entero.");
        }
    }

    // Revisa que un texto solo tenga letras y espacios
    private String leerTexto(String texto, String campo) {
        if (!texto.trim().matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" solo puede tener letras.");
        }
        return texto.trim();
    }

    // ---------- RF1: registrar ----------
    private Aventurero construirAventurero() {
        String nombre = vista.getNombre().trim();
        if (!nombre.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El nombre solo puede tener letras y espacios.");
        }
        int nivel = leerEntero(vista.getTxtNivel().getText(), "Nivel");
        int vida = leerEntero(vista.getTxtVida().getText(), "Puntos de vida");
        String atributo = vista.getTxtAtributo().getText();

        switch (vista.getClaseSeleccionada()) {
            case "Guerrero":
                return new Guerrero(nombre, nivel, vida, vista.isMarcado());
            case "Mago":
                return new Mago(nombre, nivel, vida, leerEntero(atributo, "Espacios de conjuro"));
            case "Clérigo":
                return new Clerigo(nombre, nivel, vida, leerTexto(atributo, "Dios venerado"));
            case "Monje":
                return new Monje(nombre, nivel, vida, leerEntero(atributo, "Puntos de Ki"));
            case "Paladín":
                return new Paladin(nombre, nivel, vida, leerEntero(atributo, "Puntos de fuerza"));
            case "Explorador":
                return new Explorador(nombre, nivel, vida, leerTexto(atributo, "Terreno favorecido"));
            case "Pícaro":
                return new Picaro(nombre, nivel, vida, vista.isMarcado());
            case "Bardo":
                return new Bardo(nombre, nivel, vida, leerTexto(atributo, "Instrumento musical"));
            default:
                throw new IllegalArgumentException("Clase no reconocida.");
        }
    }

    private void registrarAventurero() {
        try {
            Aventurero nuevo = construirAventurero();
            gremio.add(nuevo);
            vista.agregarFila(filaDe(nuevo));
            vista.limpiarFormulario();
            actualizarPaneles();
            vista.mostrarEstado("✔ " + nuevo.getClase() + " " + nuevo.getNombre() + " se unió al gremio.", true);
            bitacora.anexar("REGISTRO -> " + nuevo);
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (IOException ex) {
            vista.mostrarError("El héroe se registró, pero no se pudo escribir en registro_gremio.txt: " + ex.getMessage());
        }
    }

    private Object[] filaDe(Aventurero a) {
        return new Object[]{a.getNombre(), a.getClase(), a.getNivel(),
            a.getPuntosVida(), a.getRango(), a.getAtributoEspecial()};
    }

    // ---------- RF3: simular combate (polimorfismo) ----------
    private void simularCombate() {
        if (gremio.isEmpty()) {
            vista.mostrarMensaje("Gremio vacío", "Primero registra al menos un aventurero.");
            return;
        }
        // 1. cada héroe tira iniciativa (d20 + nivel)
        final Map<Aventurero, Integer> iniciativas = new HashMap<>();
        for (Aventurero a : gremio) {
            iniciativas.put(a, a.tirarIniciativa(dado));
        }
        // 2. ordenamos una copia de mayor a menor iniciativa
        List<Aventurero> turnos = new ArrayList<>(gremio);
        turnos.sort((a, b) -> iniciativas.get(b) - iniciativas.get(a));

        // 3. cada uno ejecuta SU táctica (el método se sobre-escribe en cada clase hija)
        ronda++;
        StringBuilder sb = new StringBuilder("=== RONDA " + ronda + " ===\n");
        for (Aventurero a : turnos) {
            sb.append("(Iniciativa ").append(iniciativas.get(a)).append(") ")
              .append(a.ejecutarTactica()).append("\n");
        }
        vista.agregarCronica(sb.toString());
        vista.mostrarEstado("⚔ Ronda " + ronda + " terminada: " + turnos.size() + " héroes actuaron.", true);
        try {
            bitacora.anexar("COMBATE -> " + sb.toString().trim());
        } catch (IOException ex) {
            vista.mostrarError("No se pudo escribir en registro_gremio.txt: " + ex.getMessage());
        }
    }

    // ---------- Extra: eliminar un aventurero ----------
    private void eliminarAventurero() {
        int fila = vista.getFilaSeleccionada();
        if (fila < 0) {
            vista.mostrarError("Primero selecciona un héroe en la tabla.");
            return;
        }
        Aventurero retirado = gremio.remove(fila);
        vista.quitarFila(fila);
        actualizarPaneles();
        vista.mostrarEstado("Se retiró a " + retirado.getNombre() + " del gremio.", true);
        try {
            bitacora.anexar("RETIRO -> " + retirado);
        } catch (IOException ex) {
            vista.mostrarError("No se pudo escribir en registro_gremio.txt: " + ex.getMessage());
        }
    }

    private void mostrarReportes() {
        actualizarPaneles();
        vista.irAReportes();
        vista.mostrarEstado("Reportes actualizados.", true);
    }

    // Recalcula las tarjetas de clases, el rango del gremio, el campeón y los reportes
    private void actualizarPaneles() {
        int[] conteo = new int[8]; // guerrero, mago, clérigo, monje, paladín, explorador, pícaro, bardo
        Aventurero campeon = null;
        for (Aventurero a : gremio) {
            switch (a.getClase()) {
                case "Guerrero": conteo[0]++; break;
                case "Mago": conteo[1]++; break;
                case "Clérigo": conteo[2]++; break;
                case "Monje": conteo[3]++; break;
                case "Paladín": conteo[4]++; break;
                case "Explorador": conteo[5]++; break;
                case "Pícaro": conteo[6]++; break;
                default: conteo[7]++;
            }
            if (campeon == null || a.getNivel() > campeon.getNivel()) {
                campeon = a;
            }
        }
        vista.actualizarContadores(conteo);

        // El rango del gremio sube según cuántos héroes tenga
        String rango;
        if (gremio.isEmpty()) {
            rango = "Gremio vacío";
        } else if (gremio.size() <= 3) {
            rango = "Gremio novato";
        } else if (gremio.size() <= 7) {
            rango = "Gremio respetado";
        } else {
            rango = "Gremio legendario";
        }
        String nombreCampeon = (campeon == null) ? "—" : campeon.getNombre() + " (nivel " + campeon.getNivel() + ")";
        vista.mostrarRangoYCampeon(rango, nombreCampeon);

        vista.actualizarReportes(Main.generarReportes(gremio));
    }
}
