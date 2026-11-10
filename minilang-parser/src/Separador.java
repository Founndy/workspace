import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Separador {

    private String programa;
    private int linha = 1;
    private int coluna = 1;
    private int pos = 0;
    private Map<String, Categoria> reservadas = new HashMap<>();


    Separador(String programa) {
        this.programa = programa;
        reservadas.put("when", Categoria.WHEN);
        reservadas.put("begin", Categoria.BEGIN);
        reservadas.put("stop", Categoria.STOP);
        reservadas.put("num", Categoria.NUM);
        reservadas.put("real", Categoria.REAL);
        reservadas.put("other", Categoria.OTHER);
        reservadas.put("loop", Categoria.LOOP);
    }


    List<Peca> analisar() {
        pos = 0;
        linha = 1;
        coluna = 1;
        List<Peca> pecas = new ArrayList<>();

        while (pos < programa.length()) {

            char c = programa.charAt(pos);
            if (c == '\n') {
                avancarLinha();
            } else if (c == ';') {
                pecas.add(new Peca(Categoria.PONTO_VIRGULA, ";", linha, coluna));
                avancar();
            } else if (c == ' ' || c == '\t' || c == '\r') {
                avancar();
            } else if (c == '(') {
                pecas.add(new Peca(Categoria.ABRE_PARENTESES, "(", linha, coluna));
                avancar();
            } else if (c == ')') {
                pecas.add(new Peca(Categoria.FECHA_PARENTESES, ")", linha, coluna));
                avancar();
            } else if (c == '{') {
                pecas.add(new Peca(Categoria.ABRE_CHAVES, "{", linha, coluna));
                avancar();
            } else if (c == '}') {
                pecas.add(new Peca(Categoria.FECHA_CHAVES, "}", linha, coluna));
                avancar();
            } else if (c == '+') {
                pecas.add(new Peca(Categoria.MAIS, "+", linha, coluna));
                avancar();
            } else if (c == '-') {
                pecas.add(new Peca(Categoria.MENOS, "-", linha, coluna));
                avancar();
            } else if (c == '*') {
                pecas.add(new Peca(Categoria.MULTIPLICA, "*", linha, coluna));
                avancar();
            } else if (c == '/') {
                pecas.add(new Peca(Categoria.DIVISAO, "/", linha, coluna));
                avancar();
            } else if (c == '=') {
                if (espiar() == '=') {
                    pecas.add(new Peca(Categoria.COMPARATIVO_IGUAL, "==", linha, coluna));
                    avancar();
                    avancar();
                } else {
                    pecas.add(new Peca(Categoria.SINAL_RECEBE, "=", linha, coluna));
                    avancar();
                }
            } else if (c == '<') {
                if (espiar() == '=') {
                    pecas.add(new Peca(Categoria.COMPARATIVO_MENOR_IGUAL, "<=", linha, coluna));
                    avancar();
                    avancar();
                } else {
                    pecas.add(new Peca(Categoria.COMPARATIVO_MENOR, "<", linha, coluna));
                    avancar();
                }
            } else if (c == '>') {
                if (espiar() == '=') {
                    pecas.add(new Peca(Categoria.COMPARATIVO_MAIOR_IGUAL, ">=", linha, coluna));
                    avancar();
                    avancar();
                } else {
                    pecas.add(new Peca(Categoria.COMPARATIVO_MAIOR, ">", linha, coluna));
                    avancar();
                }
            } else if (c == '!') {
                if (espiar() == '=') {
                    pecas.add(new Peca(Categoria.COMPARATIVO_DIFERENTE, "!=", linha, coluna));
                    avancar();
                    avancar();
                } else {
                    throw new ErroDeEscrita("linha " + linha + ", coluna " + coluna + ": caractere invalido '" + c + "'");
                }
            } else if (Character.isLetter(c)) {
                int cP = pos;
                int colunaCP = coluna;

                while (pos < programa.length() && Character.isLetterOrDigit(programa.charAt(pos))) {
                    avancar();
                }
                String palavra = programa.substring(cP, pos);
                Categoria categoria = reservadas.get(palavra);

                if (categoria == null) {
                    categoria = Categoria.IDENTIFICADOR;
                }
                pecas.add(new Peca(categoria, palavra, linha, colunaCP));
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
                pecas.add(new Peca(Categoria.NUMERO, numero, linha, colunaCP));
            } else {
                throw new ErroDeEscrita("linha " + linha + ", coluna " + coluna + ": caractere invalido '" + c + "'");
            }

        }
        return pecas;
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
