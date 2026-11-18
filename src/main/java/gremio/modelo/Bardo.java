package gremio.modelo;

public class Bardo extends Aventurero {

    private String instrumentoMusical;

    public Bardo(String nombre, int nivel, int puntosVida, String instrumentoMusical) {
        super(nombre, nivel, puntosVida);
        if (instrumentoMusical == null || instrumentoMusical.trim().isEmpty()) {
            throw new IllegalArgumentException("El bardo debe tener un instrumento musical.");
        }
        this.instrumentoMusical = instrumentoMusical.trim();
    }

    @Override
    public String getClase() { return "Bardo"; }

    @Override
    public String getAtributoEspecial() {
        return "Instrumento: " + instrumentoMusical;
    }

    @Override
    public String ejecutarTactica() {
        return "El Bardo " + getNombre() + " toca su instrumento para otorgar Inspiración Bárdica a un aliado.";
    }
}
