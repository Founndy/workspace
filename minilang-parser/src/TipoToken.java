public enum TipoToken {
    // inicio e fim do programa
    START,
    END,

    // palavras reservadas INVENTAR O MEU PROPRIO.
    INT,
    DOUBLE,
    IF,
    ELSE,
    WHILE,

    // simbolos de agrupamento e pontuacao
    ABRE_PARENTESES,
    FECHA_PARENTESES,
    ABRE_CHAVES,
    FECHA_CHAVES,
    PONTO_VIRGULA,

    // atribuicao
    SINAL_RECEBE,

    // operadores aritmeticos
    MAIS,
    MENOS,
    MULTIPLICA,
    DIVISAO,

    // operadores de comparacao
    COMPARATIVO_IGUAL,
    COMPARATIVO_DIFERENTE,
    COMPARATIVO_MAIOR,
    COMPARATIVO_MENOR,
    COMPARATIVO_MAIOR_IGUAL,
    COMPARATIVO_MENOR_IGUAL,

    // valores variaveis (o texto muda a cada token)
    IDENTIFICADOR,
    NUMERO
}
