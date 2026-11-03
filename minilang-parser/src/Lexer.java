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
        pos = 0;
        linha = 1;
        coluna = 1;
        List<Token> tokens = new ArrayList<>();

        while (pos < programa.length()) {

            char c = programa.charAt(pos);
            if (c == '\n') {
                avancarLinha();
            } else if (c == ';') {
                tokens.add(new Token(TipoToken.PONTO_VIRGULA, ";", linha, coluna));
                avancar();
            } else if (c == ' ' || c == '\t' || c == '\r'){
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
            } else if (c == '=') {
                if(espiar() == '='){
                tokens.add(new Token(TipoToken.COMPARATIVO_IGUAL, "==", linha, coluna));
                avancar();
                avancar();
                }else{
                    tokens.add(new Token(TipoToken.SINAL_RECEBE, "=", linha, coluna));
                    avancar();
                }
            } else if (c == '<') {
                if(espiar() == '='){
                    tokens.add(new Token(TipoToken.COMPARATIVO_MENOR_IGUAL, "<=", linha, coluna));
                    avancar();
                    avancar();
                } else{
                tokens.add(new Token(TipoToken.COMPARATIVO_MENOR, "<", linha, coluna));
                avancar();}
            } else if (c == '>') {
                if(espiar() == '='){
                    tokens.add(new Token(TipoToken.COMPARATIVO_MAIOR_IGUAL, ">=", linha, coluna));
                    avancar();
                    avancar();
                } else{
                tokens.add(new Token(TipoToken.COMPARATIVO_MAIOR, ">", linha, coluna));
                avancar();}
            } else if (c == '!') {
                if(espiar() == '='){
                    tokens.add(new Token(TipoToken.COMPARATIVO_DIFERENTE, "!=", linha, coluna));
                    avancar();
                    avancar();
                } else {
               throw new ErroLexico("linha " + linha + ", coluna " + coluna + ": caractere invalido '" + c + "'");
                }
            } else if (Character.isLetter(c)) {
                int cP = pos;
                int colunaCP = coluna;

                while (pos < programa.length() && Character.isLetterOrDigit(programa.charAt(pos))) {
                    avancar();
                }
                String palavra = programa.substring(cP, pos);

                if (palavra.equals("num")) {
                    tokens.add(new Token(TipoToken.NUM, palavra, linha, colunaCP));
                } else if (palavra.equals("begin")) {
                    tokens.add(new Token(TipoToken.BEGIN, palavra, linha, colunaCP));
                } else if (palavra.equals("stop")) {
                    tokens.add(new Token(TipoToken.STOP, palavra, linha, colunaCP));
                } else if (palavra.equals("real")) {
                    tokens.add(new Token(TipoToken.REAL, palavra, linha, colunaCP));
                } else if (palavra.equals("when")) {
                    tokens.add(new Token(TipoToken.WHEN, palavra, linha, colunaCP));
                } else if (palavra.equals("other")) {
                    tokens.add(new Token(TipoToken.OTHER, palavra, linha, colunaCP));
                } else if (palavra.equals("loop")) {
                    tokens.add(new Token(TipoToken.LOOP, palavra, linha, colunaCP));
                } else {
                    tokens.add(new Token(TipoToken.IDENTIFICADOR, palavra, linha, colunaCP));
                }
            } else if (Character.isDigit(c)) {
                int cP = pos;
                int colunaCP = coluna;
                boolean temPonto = false;

                while (pos < programa.length()) {
                    char atual = programa.charAt(pos);
                    if (Character.isDigit(atual)) {
                        avancar();
                    } else if (atual == '.' && !temPonto && Character.isDigit(espiar())) {
                        temPonto = true;
                        avancar();
                    } else {
                        break;
                    }
                }
                String numero = programa.substring(cP, pos);
                tokens.add(new Token(TipoToken.NUMERO, numero, linha, colunaCP));
            }

            else {
                throw new ErroLexico("linha " + linha + ", coluna " + coluna + ": caractere invalido '" + c + "'");
            }

        }
        return tokens;
    }

    char espiar() {
        if (pos + 1 < programa.length()) {
            return programa.charAt(pos + 1);
        }
        return '\0';
    }

    void avancar() {
        this.coluna += 1;
        pos++;
    }

    void avancarLinha() {
        this.linha += 1;
        pos++;
        this.coluna = 1;
    }


}
