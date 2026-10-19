import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private String programa;
    private int linha = 1;
    private int coluna = 1;
    private int pos = 0;


    Lexer(String programa) {
        this.programa = programa;
    }


    List<Token> analisar() {
        List<Token> tokens = new ArrayList<>();



        while (pos < programa.length()) {
            char c = programa.charAt(pos);
            if (c == '\n') {
                avancarLinha();
            } else if (c == ';') {
                tokens.add(new Token(TipoToken.PONTO_VIRGULA, ";", linha, coluna));
                avancar();
            } else if (c == ' ' || c == '\t'){
                avancar();
            } else if (c == '(') {
                tokens.add(new Token(TipoToken.ABRE_PARENTESES, "(", linha, coluna));
                avancar();
            } else if (c == ')') {
                tokens.add(new Token(TipoToken.FECHA_PARENTESES, ")", linha, coluna));
                avancar();
            } else if (c == '{') {
                tokens.add(new Token(TipoToken.ABRE_CHAVES, "{", linha, coluna));
                avancar();
            } else if (c == '}') {
                tokens.add(new Token(TipoToken.FECHA_CHAVES, "}", linha, coluna));
                avancar();
            } else if (c == '+') {
                tokens.add(new Token(TipoToken.MAIS, "+", linha, coluna));
                avancar();
            } else if (c == '-') {
                tokens.add(new Token(TipoToken.MENOS, "-", linha, coluna));
                avancar();
            } else if (c == '*') {
                tokens.add(new Token(TipoToken.MULTIPLICA, "*", linha, coluna));
                avancar();
            } else if (c == '/') {
                tokens.add(new Token(TipoToken.DIVISAO, "/", linha, coluna));
                avancar();
            }
            pos++;
        }
        return tokens;
    }

    void avancar() {
        this.coluna += 1;
    }

    void avancarLinha() {
        this.linha += 1;
        this.coluna = 1;
    }
}
