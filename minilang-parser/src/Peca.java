public class Peca {
    private Categoria tipo;
    private String texto;
    private int linha;
    private int coluna;

    Peca(Categoria tipo, String texto, int linha, int coluna) {
        this.tipo = tipo;
        this.texto = texto;
        this.linha = linha;
        this.coluna = coluna;
    }

    Categoria getTipo() {
        return tipo;
    }
    String getTexto() {
        return texto;
    }

    int getLinha() {
        return linha;
    }
    int  getColuna() {
        return coluna;
    }



}
