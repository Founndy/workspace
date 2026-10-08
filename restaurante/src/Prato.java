public class Prato {
    private String nome;
    private double preco;
    private DiaSemana dia;


    Prato(String nome, double preco, DiaSemana dia) {
        setNome(nome);
        setPreco(preco);
        setDia(dia);
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    public void setPreco(double preco) {
        if (preco > 0) {
            this.preco = preco;
        }
    }

    public void setDia(DiaSemana dia) {
        if (dia != null) {
            this.dia = dia;
        }
    }
}
