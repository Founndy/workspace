package vetcare.excecao;

/**
 * Lançada quando um dado de entrada não serve: nome vazio, CPF mal formado, peso menor ou igual a zero.
 * Herda de RuntimeException, então quem chama não é obrigado a escrever try/catch.
 */
public class DadoInvalidoException extends RuntimeException {

    /**
     * Cria a exceção com a explicação do problema.
     * @param message o texto que será mostrado ao usuário
     */
    public DadoInvalidoException(String message) {
        super(message);
    }
}
