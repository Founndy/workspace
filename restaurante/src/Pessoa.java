public abstract class Pessoa {
    private String nome;
    private int codigo;
    private String cpf;


    Pessoa(int codigo, String nome, String cpf) {
        setCodigo(codigo);
        setNome(nome);
        setCpf(cpf);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf != null && !cpf.isBlank()) {
            this.cpf = cpf;
        }
    }
}
