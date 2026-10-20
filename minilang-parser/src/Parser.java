import java.util.List;

public class Parser {
    private int atual = 0;

    private List<Token> tokens;


    Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    void consumir(TipoToken esperado, String descricao){
        if(atual >= tokens.size()){
        throw new ErroSintatico("esperado " + descricao + ", mas o programa terminou");
        }
        Token token = tokens.get(atual);
        if(token.getTipo() != esperado){
            throw new ErroSintatico("linha " + token.getLinha() + ", coluna " + token.getColuna() + ": esperado " + descricao + ", encontrado '" + token.getTexto() + "'");
        }
        atual++;
    }
    void programa() {
        consumir(TipoToken.START, "'start'");
        while (verificar(TipoToken.INT) || verificar(TipoToken.DOUBLE)) {
            declaracao();
        }
        ifElse();
        consumir(TipoToken.END, "'end'");
    }

    void declaracao(){
        if(verificar(TipoToken.INT)){
            consumir(TipoToken.INT, "'int'");
        }else if(verificar(TipoToken.DOUBLE)){
            consumir(TipoToken.DOUBLE, "'double'");
        }
        consumir(TipoToken.IDENTIFICADOR,"um nome de variavel");
        if (verificar(TipoToken.SINAL_RECEBE)){
            consumir(TipoToken.SINAL_RECEBE, "'='");
            valor();
        }
        consumir(TipoToken.PONTO_VIRGULA, "';'");
    }

    void valor(){
        expressao();
    }

    void atribuicao(){
        consumir(TipoToken.IDENTIFICADOR, "um nome de variavel");
        consumir(TipoToken.SINAL_RECEBE, "'='");
        expressao();
        consumir(TipoToken.PONTO_VIRGULA, "';'");
    }

    void bloco(){
        consumir(TipoToken.ABRE_CHAVES, "'{'");
        while(verificar(TipoToken.IDENTIFICADOR)){
            atribuicao();
        }
        consumir(TipoToken.FECHA_CHAVES, "'}'");
    }

    void operador(){
        if (verificar(TipoToken.COMPARATIVO_IGUAL) || verificar(TipoToken.COMPARATIVO_DIFERENTE) ||
                verificar(TipoToken.COMPARATIVO_MAIOR) || verificar(TipoToken.COMPARATIVO_MENOR) ||
                verificar(TipoToken.COMPARATIVO_MAIOR_IGUAL) ||
                verificar(TipoToken.COMPARATIVO_MENOR_IGUAL)) {
            atual++;
        } else {
            consumir(TipoToken.COMPARATIVO_IGUAL, "um operador de comparacao");
        }
    }

    void condicao(){
        expressao();
        operador();
        expressao();
    }

    void ifElse(){
        consumir(TipoToken.IF, "'if'");
        consumir(TipoToken.ABRE_PARENTESES, "'('");
        condicao();
        consumir(TipoToken.FECHA_PARENTESES, "')'");
        bloco();
        consumir(TipoToken.ELSE, "'else'");
        bloco();
    }


    void expressao(){
        termo();
        while(verificar(TipoToken.MAIS) || verificar(TipoToken.MENOS) || verificar(TipoToken.MULTIPLICA) || verificar(TipoToken.DIVISAO)){
            atual++;
            termo();
        }
    }

    void termo(){
        if(verificar(TipoToken.IDENTIFICADOR)){
            consumir(TipoToken.IDENTIFICADOR, "um nome ou numero");
        }else{
            consumir(TipoToken.NUMERO, "um nome ou numero");
        }
    }

    boolean verificar(TipoToken tipo){
        if(atual >= tokens.size()){
            return false;
        }
        Token token = tokens.get(atual);
        if(token.getTipo() != tipo){
            return false;
        }
        return true;
    }
}
