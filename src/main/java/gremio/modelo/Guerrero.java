package gremio.modelo;

public class Guerrero extends Aventurero {

    private boolean armaduraPesada;

    public Guerrero(String nombre, int nivel, int puntosVida, boolean armaduraPesada) {
        super(nombre, nivel, puntosVida);
        this.armaduraPesada = armaduraPesada;
    }

    // Sobrecarga de constructor: si no dicen nada, no usa armadura pesada
    public Guerrero(String nombre, int nivel, int puntosVida) {
        this(nombre, nivel, puntosVida, false);
    }

    @Override
    public String getClase() { return "Guerrero"; }

    @Override
    public String getAtributoEspecial() {
        return "Armadura pesada: " + (armaduraPesada ? "Sí" : "No");
    }

    @Override
    public String ejecutarTactica() {
        return "El Guerrero " + getNombre() + " usa Oleada de Acción para realizar múltiples ataques físicos continuos.";
    }
}
