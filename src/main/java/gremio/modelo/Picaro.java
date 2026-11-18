package gremio.modelo;

public class Picaro extends Aventurero {

    private boolean herramientasLadron;

    public Picaro(String nombre, int nivel, int puntosVida, boolean herramientasLadron) {
        super(nombre, nivel, puntosVida);
        this.herramientasLadron = herramientasLadron;
    }

    @Override
    public String getClase() { return "Pícaro"; }

    @Override
    public String getAtributoEspecial() {
        return "Herramientas de ladrón: " + (herramientasLadron ? "Sí" : "No");
    }

    @Override
    public String ejecutarTactica() {
        return "El Pícaro " + getNombre() + " aprovecha la ventaja táctica para asestar un Ataque Furtivo masivo y se reposiciona.";
    }
}
