package vetcare.modelo;

/**
 * Cachorro atendido pela clínica, com raça.
 */
public class Cachorro extends Animal{


    private String raca;

    /** Cria o cachorro; raça em branco vira "SRD". */
    public Cachorro(String nome, double peso,String raca){
        super(nome,peso);
        this.raca=validarRaca(raca);
    }

    private String validarRaca(String raca){
        if(raca == null || raca.isBlank()){
            return "SRD";
        }
        return raca.trim();
    }


    /** Devolve a raça do cachorro. */
    public String getRaca() {
        return raca;
    }

    /** Devolve "Cachorro". */
    @Override
    public String getEspecie(){
        return "Cachorro";
    }

    /** Mostra o animal com a raça no fim: "Rex (Cachorro, 12,5 kg) - Labrador". */
    @Override
    public String toString(){
        return super.toString() + " - " + getRaca();
    }
}
