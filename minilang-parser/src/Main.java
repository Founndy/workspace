import java.util.List;

public class Main {
    public static void main(String[] args) {

        // PROGRAMA 1 - deve PASSAR.
        // Usa tudo o que a gramatica permite: declaracao com e sem valor, int e double
        // (com ponto), expressoes com varios operadores e atribuicoes nos dois blocos.
        String programa1 = """
                start
                    int x = 5;
                    int y;
                    double taxa = 2.5;
                    if (x > 3) {
                        y = x + 1;
                        taxa = taxa * 2 - 1;
                    } else {
                        y = 0;
                    }
                end
                """;

        // PROGRAMA 2 - deve FALHAR: falta o ';' no fim da declaracao da linha 2.
        // O parser so percebe ao chegar no proximo token (o 'if' da linha 3).
        // Esperado: linha 3, coluna 5: esperado ';', encontrado 'if'
        String programa2 = """
                start
                    int x = 5
                    if (x > 3) {
                        x = 1;
                    } else {
                        x = 2;
                    }
                end
                """;

        // PROGRAMA 3 - deve FALHAR no LEXICO (antes de chegar no parser):
        // o '@' nao e um simbolo da MiniLang.
        // Esperado: linha 3, coluna 15: caractere invalido '@'
        String programa3 = """
                start
                    int x = 5;
                    int y = x @ 2;
                    if (x > 3) {
                        y = 1;
                    } else {
                        y = 2;
                    }
                end
                """;

        // PROGRAMA 4 - deve FALHAR: falta o 'else' (na gramatica ele e obrigatorio).
        // Depois do bloco do 'if', o parser espera 'else' e encontra 'end'.
        // Esperado: linha 6, coluna 1: esperado 'else', encontrado 'end'
        String programa4 = """
                start
                    int x = 5;
                    if (x > 3) {
                        x = 1;
                    }
                end
                """;

        // PROGRAMA 5 - deve FALHAR: 'while' e palavra reservada, nao pode ser nome de variavel.
        // O lexico gera um token WHILE (e nao IDENTIFICADOR), entao a declaracao quebra.
        // Esperado: linha 2, coluna 9: esperado um nome de variavel, encontrado 'while'
        String programa5 = """
                start
                    int while = 5;
                    if (while > 3) {
                        while = 1;
                    } else {
                        while = 2;
                    }
                end
                """;

        // PROGRAMA 6 - deve FALHAR: a condicao do 'if' nao tem operador de comparacao.
        // Depois do 'x', o parser espera ==, !=, <, >, <= ou >= e encontra ')'.
        // Esperado: linha 3, coluna 10: esperado um operador de comparacao, encontrado ')'
        String programa6 = """
                start
                    int x = 5;
                    if (x) {
                        x = 1;
                    } else {
                        x = 2;
                    }
                end
                """;

        // PROGRAMA 7 - deve FALHAR: a expressao termina num operador ('x +' sem nada depois).
        // Depois do '+', o parser espera um nome ou um numero e encontra ';'.
        // Esperado: linha 4, coluna 17: esperado um nome ou numero, encontrado ';'
        String programa7 = """
                start
                    int x = 5;
                    if (x > 3) {
                        x = x + ;
                    } else {
                        x = 2;
                    }
                end
                """;

        // PROGRAMA 8 - deve PASSAR: o menor programa valido possivel.
        // Zero declaracoes e os dois blocos vazios (a gramatica permite "zero ou mais").
        String programa8 = """
                start
                    if (1 == 1) {
                    } else {
                    }
                end
                """;

        // PROGRAMA 9 - deve FALHAR: falta o 'end' no final.
        // O parser chega ao fim dos tokens sem encontrar 'end'.
        // Esperado: erro dizendo que esperava 'end' e o programa acabou.
        String programa9 = """
                start
                    int x = 5;
                    if (x > 3) {
                        x = 1;
                    } else {
                        x = 2;
                    }
                """;

        String[] testes = {
                programa1, programa2, programa3, programa4, programa5,
                programa6, programa7, programa8, programa9
        };
        int numero = 1;
        for (String texto : testes) {
            System.out.print("Programa " + numero + " -> ");
            numero++;
            try {
                Lexer lexer = new Lexer(texto);
                List<Token> tokens = lexer.analisar();
                Parser parser = new Parser(tokens);
                parser.programa();
                System.out.println("Programa valido!");
            } catch (ErroLexico e) {
                System.out.println(e.getMessage());
            } catch (ErroSintatico e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
