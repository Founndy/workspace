public class Pessoa {

    private String nome;
    private String cpf;



    Pessoa(String nome, String cpf){
        setNome(nome);
        setCpf(cpf);
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        if(nome != null && !nome.isBlank()){
        this.nome = nome;
        }
    }
    public void anunciar(){
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
    }
}
