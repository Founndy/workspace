package vetcare.excecao;

/**
 * Lançada quando uma regra da clínica é desrespeitada: CPF repetido, cancelar algo já realizado, agendar no passado.
 * Herda de RuntimeException, então quem chama não é obrigado a escrever try/catch.
 */
public class OperacaoInvalidaException extends RuntimeException {

    /**
     * Cria a exceção com a explicação do problema.
     * @param message o texto que será mostrado ao usuário
     */
    public OperacaoInvalidaException(String message) {
        super(message);
    }
}
