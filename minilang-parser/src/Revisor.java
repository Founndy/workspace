import java.util.List;

public class Revisor {
    private int atual = 0;

    private List<Peca> pecas;


    Revisor(List<Peca> pecas){
        this.pecas = pecas;
    }

    void exigir(Categoria esperado, String descricao){
        if(atual >= pecas.size()){
        throw new ErroDeEstrutura("esperado " + descricao + ", mas o programa terminou");
        }
        //tratamento do pos final.
        Peca peca = pecas.get(atual);
        if(peca.getTipo() != esperado){
            throw new ErroDeEstrutura("linha " + peca.getLinha() + ", coluna " + peca.getColuna() + ": esperado " + descricao + ", encontrado '" + peca.getTexto() + "'");
        }
        atual++;
    }
    void programa() {
        exigir(Categoria.BEGIN, "'begin'");

        //na linguagen java = int ou double.
        while (aVezEhDe(Categoria.NUM) || aVezEhDe(Categoria.REAL)) {
            declaracao();
        }
        if (aVezEhDe(Categoria.WHEN)) {
            whenOther();
        }
        exigir(Categoria.STOP, "'stop'");
        if (atual < pecas.size()) {
            Peca peca = pecas.get(atual);
            throw new ErroDeEstrutura("linha " + peca.getLinha() + ", coluna " + peca.getColuna() + ": esperado o fim do programa depois de 'stop', encontrado '" + peca.getTexto() + "'");
        }
    }

    void declaracao(){
        if(aVezEhDe(Categoria.NUM)){
            exigir(Categoria.NUM, "'num'");
        }else if(aVezEhDe(Categoria.REAL)){
            exigir(Categoria.REAL, "'real'");
        }
        exigir(Categoria.IDENTIFICADOR,"um nome de variavel");
        if (aVezEhDe(Categoria.SINAL_RECEBE)){
            exigir(Categoria.SINAL_RECEBE, "'='");
            valor();
        }
        exigir(Categoria.PONTO_VIRGULA, "';'");
    }

    void valor(){
        expressao();
    }

    void atribuicao(){
        exigir(Categoria.IDENTIFICADOR, "um nome de variavel");
        exigir(Categoria.SINAL_RECEBE, "'='");
        expressao();
        exigir(Categoria.PONTO_VIRGULA, "';'");
    }

    void bloco(){
        exigir(Categoria.ABRE_CHAVES, "'{'");
        while(aVezEhDe(Categoria.IDENTIFICADOR)){
            atribuicao();
        }
        exigir(Categoria.FECHA_CHAVES, "'}'");
    }

    void operador(){
        if (aVezEhDe(Categoria.COMPARATIVO_IGUAL) || aVezEhDe(Categoria.COMPARATIVO_DIFERENTE) ||
                aVezEhDe(Categoria.COMPARATIVO_MAIOR) || aVezEhDe(Categoria.COMPARATIVO_MENOR) ||
                aVezEhDe(Categoria.COMPARATIVO_MAIOR_IGUAL) ||
                aVezEhDe(Categoria.COMPARATIVO_MENOR_IGUAL)) {
            atual++;
        } else {
            exigir(Categoria.COMPARATIVO_IGUAL, "um operador de comparacao");
        }
    }

    void condicao(){
        expressao();
        operador();
        expressao();
    }

    void whenOther(){
        exigir(Categoria.WHEN, "'when'");
        exigir(Categoria.ABRE_PARENTESES, "'('");
        condicao();
        exigir(Categoria.FECHA_PARENTESES, "')'");
        bloco();
        exigir(Categoria.OTHER, "'other'");
        bloco();
    }


    void expressao(){
        termo();
        while(aVezEhDe(Categoria.MAIS) || aVezEhDe(Categoria.MENOS) || aVezEhDe(Categoria.MULTIPLICA) || aVezEhDe(Categoria.DIVISAO)){
            atual++;
            termo();
        }
    }

    void termo(){
        if(aVezEhDe(Categoria.IDENTIFICADOR)){
            exigir(Categoria.IDENTIFICADOR, "um nome ou numero");
        }else{
            exigir(Categoria.NUMERO, "um nome ou numero");
        }
    }

    boolean aVezEhDe(Categoria tipo){
        if(atual >= pecas.size()){
            return false;
        }
        Peca peca = pecas.get(atual);
        if(peca.getTipo() != tipo){
            return false;
        }
        return true;
    }
}
