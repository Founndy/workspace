import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner Leitor = new Scanner(System.in);
        String opcao = "";

        // PROGRAMA 1 - deve PASSAR.
        // Usa tudo o que a gramatica permite: declaracao com e sem valor, num e real
        // (com ponto), expressoes com varios operadores e atribuicoes nos dois blocos.
        String programa1 = """
                begin
                    num x = 5;
                    num y;
                    real taxa = 2.5;
                    when (x > 3) {
                        y = x + 1;
                        taxa = taxa * 2 - 1;
                    } other {
                        y = 0;
                    }
                stop
                """;

        // PROGRAMA 2 - deve FALHAR: falta o ';' no fim da declaracao da linha 2.
        // O parser so percebe ao chegar no proximo token (o 'when' da linha 3).
        // Esperado: linha 3, coluna 5: esperado ';', encontrado 'when'
        String programa2 = """
                begin
                    num x = 5
                    when (x > 3) {
                        x = 1;
                    } other {
                        x = 2;
                    }
                stop
                """;

        // PROGRAMA 3 - deve FALHAR no LEXICO (antes de chegar no parser):
        // o '@' nao e um simbolo da MiniLang.
        // Esperado: linha 3, coluna 15: caractere invalido '@'
        String programa3 = """
                begin
                    num x = 5;
                    num y = x @ 2;
                    when (x > 3) {
                        y = 1;
                    } other {
                        y = 2;
                    }
                stop
                """;

        // PROGRAMA 4 - deve FALHAR: falta o 'other' (quando ha 'when', ele e obrigatorio).
        // Depois do bloco do 'when', o parser espera 'other' e encontra 'stop'.
        // Esperado: linha 6, coluna 1: esperado 'other', encontrado 'stop'
        String programa4 = """
                begin
                    num x = 5;
                    when (x > 3) {
                        x = 1;
                    }
                stop
                """;

        // PROGRAMA 5 - deve FALHAR: 'loop' e palavra reservada, nao pode ser nome de variavel.
        // O lexico gera um token LOOP (e nao IDENTIFICADOR), entao a declaracao quebra.
        // Esperado: linha 2, coluna 9: esperado um nome de variavel, encontrado 'loop'
        String programa5 = """
                begin
                    num loop = 5;
                    when (loop > 3) {
                        loop = 1;
                    } other {
                        loop = 2;
                    }
                stop
                """;

        // PROGRAMA 6 - deve FALHAR: a condicao do 'when' nao tem operador de comparacao.
        // Depois do 'x', o parser espera ==, !=, <, >, <= ou >= e encontra ')'.
        // Esperado: linha 3, coluna 12: esperado um operador de comparacao, encontrado ')'
        String programa6 = """
                begin
                    num x = 5;
                    when (x) {
                        x = 1;
                    } other {
                        x = 2;
                    }
                stop
                """;

        // PROGRAMA 7 - deve FALHAR: a expressao termina num operador ('x +' sem nada depois).
        // Depois do '+', o parser espera um nome ou um numero e encontra ';'.
        // Esperado: linha 4, coluna 17: esperado um nome ou numero, encontrado ';'
        String programa7 = """
                begin
                    num x = 5;
                    when (x > 3) {
                        x = x + ;
                    } other {
                        x = 2;
                    }
                stop
                """;

        // PROGRAMA 8 - deve PASSAR: o menor programa valido possivel.
        // So begin e stop: zero declaracoes e nenhum when-other.
        String programa8 = """
                begin
                stop
                """;

        // PROGRAMA 9 - deve FALHAR: falta o 'stop' no final.
        // O parser chega ao fim dos tokens sem encontrar 'stop'.
        // Esperado: esperado 'stop', mas o programa terminou
        String programa9 = """
                begin
                    num x = 5;
                    when (x > 3) {
                        x = 1;
                    } other {
                        x = 2;
                    }
                """;

        String[] testes = {
                programa1, programa2, programa3, programa4, programa5,
                programa6, programa7, programa8, programa9
        };

        while (!opcao.equals("0")) {
            System.out.println();
            System.out.println("=== Analisador MiniLang ===");
            System.out.println("1 - Testar um dos exemplos");
            System.out.println("2 - Ler de um arquivo");
            System.out.println("3 - Digitar o programa");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = Leitor.nextLine();

            switch (opcao) {
                case "1" -> {
                    System.out.print("Qual exemplo (1 a " + testes.length + ")? ");
                    String numero = Leitor.nextLine();
                    try {
                        int indice = Integer.parseInt(numero) - 1;
                        if (indice < 0 || indice >= testes.length) {
                            System.out.println("Exemplo inexistente!");
                        } else {
                            analisar(testes[indice]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Digite um numero!");
                    }
                }
                case "2" -> {
                    System.out.print("Caminho do arquivo: ");
                    String caminho = Leitor.nextLine().trim();
                    if (caminho.length() >= 2 && caminho.startsWith("\"") && caminho.endsWith("\"")) {
                        caminho = caminho.substring(1, caminho.length() - 1);
                    }
                    try {
                        String conteudo = Files.readString(Path.of(caminho));
                        analisar(conteudo);
                    } catch (IOException | InvalidPathException e) {
                        System.out.println("Nao foi possivel ler o arquivo: " + caminho);
                    }
                }
                case "3" -> {
                    System.out.println("Digite o programa (linha vazia para terminar):");
                    String digitado = "";
                    String linha = Leitor.nextLine();
                    while (!linha.isEmpty()) {
                        digitado = digitado + linha + "\n";
                        linha = Leitor.nextLine();
                    }
                    analisar(digitado);
                }
                case "0" -> System.out.println("Ate mais!");
                default -> System.out.println("Opcao invalida");
            }
        }
    }

    static void analisar(String texto) {
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
