package vetcare.modelo;

import vetcare.excecao.DadoInvalidoException;

/**
 * Veterinário da clínica, com CRMV e especialidade.
 */
public class Veterinario extends Pessoa{
    private final String crmv;
    private String especialidade;


    /** Cria o veterinário com uma especialidade. */
    public Veterinario(String nome, String cpf,String crmv, String especialidade){
        super(nome, cpf);
        this.crmv = validarCrmv(crmv);
        this.especialidade = especialidade;
    }

    /** Cria o veterinário como clínico geral. */
    public Veterinario(String nome, String cpf, String crmv){
        this(nome,cpf,crmv, "Clínico geral");

    }

    /** Devolve o CRMV do veterinário. */
    public String getCrmv() {
        return crmv;
    }

    /** Devolve a especialidade do veterinário. */
    public String getEspecialidade() {
        return especialidade;
    }

    /** Troca a especialidade do veterinário. */
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    private String validarCrmv(String crmv){
        if(crmv == null || crmv.isBlank()){
            throw new DadoInvalidoException("crmv Invalido!");
        }
        return crmv.trim();
    }


    /** Descrição do veterinário com nome, CRMV e especialidade. */
    @Override
    public String getDescricao(){
        return "Veterinário(a): " + getNome() + " - "+ getCrmv() +" ("+ especialidade +")";
    }
}
