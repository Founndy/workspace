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
        if(cpf == null){
            throw new DadoInvalidoException("O CPF é obrigatório.");
        }

        // junta só os dígitos: "111.222.333-44" vira "11122233344"
        String digitos = "";
        int i = 0;
        while(i < cpf.length()){
            char c = cpf.charAt(i);
            if(Character.isDigit(c)){
                digitos = digitos + c;
            }
            i++;
        }

        if(digitos.length() != 11){
            throw new DadoInvalidoException("O CPF deve ter 11 dígitos.");
        }

        // recusa CPF com todos os dígitos iguais, como "111.111.111-11"
        boolean todosIguais = true;
        i = 1;
        while(i < digitos.length()){
            if(digitos.charAt(i) != digitos.charAt(0)){
                todosIguais = false;
            }
            i++;
        }
        if(todosIguais){
            throw new DadoInvalidoException("CPF inválido: todos os dígitos são iguais.");
        }

        return digitos;
    }


}

