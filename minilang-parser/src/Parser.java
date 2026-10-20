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
        consumir(TipoToken.END, "'end'");
    }
}
