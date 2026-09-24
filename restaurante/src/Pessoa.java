public class Pessoa {
    private String nome;
    private int codigo;
    private String cpf;


    Pessoa(int codigo, String nome, String cpf){

        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
