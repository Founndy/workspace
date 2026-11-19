package vetcare.modelo;

/**
 * Gato atendido pela clínica, castrado ou não.
 */
public class Gato extends Animal {
    private boolean castrado;

    /** Cria o gato informando se é castrado. */
    public Gato(String nome, double peso, boolean castrado) {
        super(nome, peso);
        this.castrado = castrado;
    }
    /** Cria o gato como não castrado. */
    public Gato(String nome, double peso) {
        this(nome, peso, false);
    }

    /** Diz se o gato é castrado. */
    public boolean isCastrado() {
        return castrado;
    }

    /** Devolve "Gato". */
    @Override
    public String getEspecie() {
        return "Gato";
    }
}
