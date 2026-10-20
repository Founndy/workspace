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
               //invalido-tratamento de exceção. fazer depois.//
                avancar();
                }
            } else if (Character.isLetter(c)) {
                int cP = pos;
                int colunaCP = coluna;

                while (pos < programa.length() && Character.isLetterOrDigit(programa.charAt(pos))) {
                    avancar();
                }
                String palavra = programa.substring(cP, pos);

                if (palavra.equals("int")) {
                    tokens.add(new Token(TipoToken.INT, palavra, linha, colunaCP));
                } else if (palavra.equals("start")) {
                    tokens.add(new Token(TipoToken.START, palavra, linha, colunaCP));
                } else if (palavra.equals("end")) {
                    tokens.add(new Token(TipoToken.END, palavra, linha, colunaCP));
                } else if (palavra.equals("double")) {
                    tokens.add(new Token(TipoToken.DOUBLE, palavra, linha, colunaCP));
                } else if (palavra.equals("if")) {
                    tokens.add(new Token(TipoToken.IF, palavra, linha, colunaCP));
                } else if (palavra.equals("else")) {
                    tokens.add(new Token(TipoToken.ELSE, palavra, linha, colunaCP));
                } else if (palavra.equals("while")) {
                    tokens.add(new Token(TipoToken.WHILE, palavra, linha, colunaCP));
                } else {
                    tokens.add(new Token(TipoToken.IDENTIFICADOR, palavra, linha, colunaCP));
                }
            } else {
                avancar();
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
