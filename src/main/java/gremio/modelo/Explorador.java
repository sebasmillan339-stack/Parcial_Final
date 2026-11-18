package gremio.modelo;

public class Explorador extends Aventurero {

    private String terrenoFavorecido;

    public Explorador(String nombre, int nivel, int puntosVida, String terrenoFavorecido) {
        super(nombre, nivel, puntosVida);
        if (terrenoFavorecido == null || terrenoFavorecido.trim().isEmpty()) {
            throw new IllegalArgumentException("El explorador debe tener un terreno favorecido.");
        }
        this.terrenoFavorecido = terrenoFavorecido.trim();
    }

    @Override
    public String getClase() { return "Explorador"; }

    @Override
    public String getAtributoEspecial() {
        return "Terreno favorecido: " + terrenoFavorecido;
    }

    @Override
    public String ejecutarTactica() {
        return "El Explorador " + getNombre() + " aplica Marca del Cazador sobre el objetivo para maximizar su daño sostenido.";
    }
}
