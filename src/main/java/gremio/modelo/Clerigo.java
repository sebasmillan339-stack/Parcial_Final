package gremio.modelo;

public class Clerigo extends Aventurero {

    private String diosVenerado;

    public Clerigo(String nombre, int nivel, int puntosVida, String diosVenerado) {
        super(nombre, nivel, puntosVida);
        if (diosVenerado == null || diosVenerado.trim().isEmpty()) {
            throw new IllegalArgumentException("El clérigo debe venerar a algún dios.");
        }
        this.diosVenerado = diosVenerado.trim();
    }

    @Override
    public String getClase() { return "Clérigo"; }

    @Override
    public String getAtributoEspecial() {
        return "Dios venerado: " + diosVenerado;
    }

    @Override
    public String ejecutarTactica() {
        return "El Clérigo " + getNombre() + " usa Canalizar Divinidad para curar a sus aliados y expulsar muertos vivientes.";
    }
}
