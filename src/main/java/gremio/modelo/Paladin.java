package gremio.modelo;

public class Paladin extends Aventurero {

    private int puntosFuerza;

    public Paladin(String nombre, int nivel, int puntosVida, int puntosFuerza) {
        super(nombre, nivel, puntosVida);
        if (puntosFuerza < 0) {
            throw new IllegalArgumentException("Los puntos de fuerza no pueden ser negativos.");
        }
        this.puntosFuerza = puntosFuerza;
    }

    @Override
    public String getClase() { return "Paladín"; }

    @Override
    public String getAtributoEspecial() {
        return "Puntos de fuerza: " + puntosFuerza;
    }

    @Override
    public String ejecutarTactica() {
        return "El Paladín " + getNombre() + " consume magia para imbuir su arma con un Castigo Divino de daño radiante.";
    }
}
