package gremio.modelo;

public class Mago extends Aventurero {

    private int espaciosConjuro;

    public Mago(String nombre, int nivel, int puntosVida, int espaciosConjuro) {
        super(nombre, nivel, puntosVida);
        if (espaciosConjuro < 0) {
            throw new IllegalArgumentException("Los espacios de conjuro no pueden ser negativos.");
        }
        this.espaciosConjuro = espaciosConjuro;
    }

    @Override
    public String getClase() { return "Mago"; }

    @Override
    public String getAtributoEspecial() {
        return "Espacios de conjuro: " + espaciosConjuro;
    }

    @Override
    public String ejecutarTactica() {
        return "El Mago " + getNombre() + " gasta un espacio de conjuro para lanzar una poderosa Bola de Fuego.";
    }
}
