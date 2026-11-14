package vetcare.contrato;

/** Algo que tem um valor a ser cobrado pela clínica. */
public interface Pagavel {

    /**
     * Calcula quanto este item custa.
     * @return o valor em reais
     */
    double calcularValor();
}
