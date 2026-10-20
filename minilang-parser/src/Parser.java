import java.util.ArrayList;
import java.util.List;

public class Parser {
    private int atual = 0;

    private List<Token> tokens = new ArrayList<>();


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
