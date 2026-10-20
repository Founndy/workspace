public class ErroSintatico extends RuntimeException {
    ErroSintatico(String mensagem) {
        super(mensagem);
    }
}