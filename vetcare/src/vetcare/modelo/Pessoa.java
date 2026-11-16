package vetcare.modelo;

import vetcare.excecao.DadoInvalidoException;

/**
 * Pessoa cadastrada na clínica, com nome, CPF e telefone.
 * É abstrata: só existem tutores e veterinários.
 */
public abstract class Pessoa {
    private String nome;
    private final String cpf;
    private String telefone;


    /**
     * Cria a pessoa validando o nome e guardando o CPF só com os dígitos.
     */
   public  Pessoa(String nome, String cpf,String telefone) {
        this.nome = validarNome(nome);
        this.cpf = normalizarCpf(cpf);
        this.telefone = telefone;
    }

    /**
     * Cria a pessoa sem telefone, que fica como "não informado".
     */
    public Pessoa(String nome, String cpf) {
        this(nome,cpf,"não informado");
    }
    
    /** Devolve o nome da pessoa. */
    public String getNome() {
        return nome;
    }
    /** Devolve o CPF, só com os 11 dígitos. */
    public String getCpf() {
        return cpf;
    }
    /** Devolve o telefone, ou "não informado". */
    public String getTelefone() {
        return telefone;
    }

    /** Troca o nome, com a mesma validação do construtor. */
    public void setNome(String nome){
        this.nome = validarNome(nome);
    }

    /** Descrição da pessoa; cada subclasse escreve a sua. */
    public abstract String getDescricao();

    /** Mostra a pessoa usando a descrição da subclasse. */
    @Override
    public String toString() {
        return getDescricao();
    }

    /** Troca o telefone; vazio ou nulo não é aceito. */
    public void setTelefone(String telefone){
        if(telefone == null || telefone.isBlank()){
            throw new DadoInvalidoException("Dado Invalido detectado,programa finalizado!");
        }
        this.telefone = telefone;
    }

    private String validarNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new DadoInvalidoException("Nome Invalido!");
        }
        return nome.trim();
    }

    private String normalizarCpf(String cpf){
        String digitos = "";
        int i = 0;
        if(cpf == null){
            throw new DadoInvalidoException("Digite um CPF!");
        }


        while(i < cpf.length()){
            char c = cpf.charAt(i);
            if(Character.isDigit(c)){
                digitos = digitos + c;
            }
            i++;
        }

        int j = 1;
        int cout = 0;
        while(j < digitos.length()){
            if(digitos.charAt(j - 1) == digitos.charAt(j)){
                cout++;
            }
        j++;
        }
        if(digitos.length() != 11 || cout == 10){
            throw new DadoInvalidoException("CPF invalido");
        }

        return digitos;
    }


}

