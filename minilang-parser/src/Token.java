public class Token {
    private TipoToken tipo;
    private String texto;
    private int linha;
    private int coluna;

    Token(TipoToken tipo, String texto, int linha, int coluna) {
        this.tipo = tipo;
        this.texto = texto;
        this.linha = linha;
        this.coluna = coluna;
    }

    TipoToken getTipo() {
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
