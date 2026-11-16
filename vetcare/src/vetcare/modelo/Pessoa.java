package vetcare.modelo;

public abstract class Pessoa {
    private String nome;
    private final String cpf;
    private String telefone;


    Pessoa(String nome, String cpf,String telefone) {
        this.nome = setNome(nome);
        this.cpf = cpf;
        this.telefone = telefone;
    }
    String getNome() {
        return nome;
    }
    String getCpf() {
        return cpf;
    }
    String getTelefone() {
        return telefone;
    }

    void setNome(String nome){
        if(nome.isBlank() || nome == null){
            System.out.println("Nome invalido!");
            return;
        }
        this.nome = nome;
    }

    void setTelefone(String telefone){
        if(telefone == null || telefone.isBlank()){
            System.out.println("telefone invalido!");
            return;
        }
        this.telefone = telefone;
    }


}

