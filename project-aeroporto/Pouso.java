public class Pouso implements OperacaoVoo {
    @Override
    public void executar(Voo voo) {
        voo.setEstadoAtual(StatusVoo.EM_APROXIMACAO);
        voo.setEstadoAtual(StatusVoo.POUSADO);
    }
}