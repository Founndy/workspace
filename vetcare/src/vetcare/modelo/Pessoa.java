package vetcare.modelo;

import vetcare.excecao.DadoInvalidoException;

public abstract class Pessoa {
    private String nome;
    private final String cpf;
    private String telefone;


    Pessoa(String nome, String cpf,String telefone) {
        this.nome = validarNome(nome);
        this.cpf = normalizarCpf(cpf);
        this.telefone = telefone;
    }

    Pessoa(String nome, String cpf) {
        this.nome = validarNome(nome);
        this.cpf = normalizarCpf(cpf);
    }
    
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public String getTelefone() {
        return telefone;
    }

    public void setNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new DadoInvalidoException("Dado Invalido detectado,programa finalizado!");
        }
        this.nome = nome;
    }

    public void setTelefone(String telefone){
        if(telefone == null || telefone.isBlank()){
            throw new DadoInvalidoException("Dado Invalido detectado,programa finalizado!");
        }
        this.telefone = telefone;
    }

    private String validarNome(String nome){
        if(nome == null || nome.isBlank() || nome.isEmpty()){
            throw new DadoInvalidoException("Nome Invalido!");
        }
        return nome.trim();
    }

    private String normalizarCpf(String cpf){
        int i = 0;
        while(i < cpf.length()){
            
            i++;
        }
        return cpf;
    }


}

