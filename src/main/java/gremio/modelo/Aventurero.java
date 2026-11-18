package gremio.modelo;

public abstract class Aventurero implements IAccionCombate {

    public static final int NIVEL_MAXIMO = 20;
    private static int totalCreados = 0;

    private String nombre;
    private int nivel;
    private int puntosVida;

    public Aventurero(String nombre, int nivel, int puntosVida) {
        setNombre(nombre);
        setNivel(nivel);
        setPuntosVida(puntosVida);
        totalCreados++;
    }

    // Cada hijo dice cuál es su clase y su atributo especial
    public abstract String getClase();
    public abstract String getAtributoEspecial();

    // COMMIT M5: este método se agrega cuando ya exista la clase Dado
    public int tirarIniciativa(Dado dado) {
        return dado.lanzarD20() + nivel;
    }

    // Sobrecarga (overload) de métodos
    public void recibirDanio(int danio) {
        recibirDanio(danio, 0);
    }

    public void recibirDanio(int danio, int reduccion) {
        int real = danio - reduccion;
        if (real < 0) {
            real = 0;
        }
        puntosVida -= real;
        if (puntosVida < 0) {
            puntosVida = 0;
        }
    }

    // Rangos según los niveles de D&D 5e
    public final String getRango() {
        if (nivel <= 4) {
            return "Héroe local";
        } else if (nivel <= 10) {
            return "Héroe del reino";
        } else if (nivel <= 16) {
            return "Maestro del reino";
        }
        return "Maestro del mundo";
    }

    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getPuntosVida() { return puntosVida; }
    public static int getTotalCreados() { return totalCreados; }

    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public final void setNivel(int nivel) {
        if (nivel < 1 || nivel > NIVEL_MAXIMO) {
            throw new IllegalArgumentException("El nivel debe estar entre 1 y " + NIVEL_MAXIMO + ".");
        }
        this.nivel = nivel;
    }

    public final void setPuntosVida(int puntosVida) {
        if (puntosVida <= 0) {
            throw new IllegalArgumentException("Los puntos de vida deben ser mayores que 0.");
        }
        this.puntosVida = puntosVida;
    }

    @Override
    public String toString() {
        return getClase() + " " + nombre + " | Nivel " + nivel + " (" + getRango()
                + ") | PV " + puntosVida + " | " + getAtributoEspecial();
    }
}
