public class Decolagem implements OperacaoVoo {
    @Override
    public void executar(Voo voo) {
        voo.setEstadoAtual(StatusVoo.TAXIANDO);
        voo.setEstadoAtual(StatusVoo.DECOLADO);
        voo.setEstadoAtual(StatusVoo.EM_ROTA);//em sequência: TAXIANDO -> DECOLADO -> EM_ROTA
    }
}