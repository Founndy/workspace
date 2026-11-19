package vetcare.modelo;

import vetcare.excecao.DadoInvalidoException;

import java.util.Locale;

/**
 * Animal atendido pela clínica, com nome, peso e prontuário.
 * É abstrata: só existem cachorros e gatos.
 */
public abstract class Animal {

    private String nome;
    private double peso;
    protected final Prontuario prontuario;

    /** Cria o animal validando nome e peso, e abre o prontuário dele. */
    public Animal(String nome, double peso){
        this.nome = validarNome(nome);
        this.peso = validarPeso(peso);
        this.prontuario = new Prontuario();
    }

    private String validarNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new DadoInvalidoException("Nome invalido!");
        }
        return nome.trim();
    }

    private double validarPeso(double peso){
        if(peso <= 0){
            throw new DadoInvalidoException("Peso invalido!");
        }
        return peso;
    }

    /** Devolve o nome do animal. */
    public String getNome() {
        return nome;
    }
    /** Devolve o peso do animal, em kg. */
    public double getPeso() {
        return peso;
    }
    /** Devolve o prontuário do animal. */
    public Prontuario getProntuario() {
        return prontuario;
    }

    /** Troca o peso; zero ou negativo não é aceito. */
    public void setPeso(double peso){
        this.peso = validarPeso(peso);
    }
    /** Troca o nome, com a mesma validação do construtor. */
    public void setNome(String nome){
        this.nome = validarNome(nome);
    }

    /** Espécie do animal; cada subclasse diz a sua. */
    public abstract String getEspecie();

    /** Mostra o animal como "Rex (Cachorro, 12,5 kg)". */
    @Override
    public String toString() {
        return getNome() + " (" + getEspecie() +", " + String.format(Locale.forLanguageTag("pt-BR"), "%.1f", peso) +" kg)";
    }
}
