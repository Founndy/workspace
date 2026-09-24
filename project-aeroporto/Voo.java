public class Voo {
    private String codVoo;
    private String aeroSaida;
    private String aeroEntrada;
    private StatusVoo estadoAtual;
    private Aviao aviao;

    private Tripulante[] tripulante = new Tripulante[10];


    Voo(String codVoo, String aeroSaida, String aeroEntrada) {
        setCodVoo(codVoo);
        setAeroSaida(aeroSaida);
        setAeroEntrada(aeroEntrada);
        this.estadoAtual = StatusVoo.PLANEJADO;

    }

    String getCodVoo() {
        return codVoo;
    }
    String getAeroSaida() {
        return aeroSaida;
    }

    String getAeroEntrada() {
        return aeroEntrada;
    }

    StatusVoo getEstadoAtual() {
        return estadoAtual;
    }

    void setEstadoAtual(StatusVoo estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    void setCodVoo(String codVoo) {
        if(codVoo != null && !codVoo.isBlank()) {
            this.codVoo = codVoo;
        }
    }

    void setAeroSaida(String aeroSaida) {
        if(aeroSaida != null && !aeroSaida.isBlank()) {
            this.aeroSaida = aeroSaida;
        }
    }


    void setAeroEntrada(String aeroEntrada) {
        if(aeroEntrada != null && !aeroEntrada.isBlank()) {
            this.aeroEntrada = aeroEntrada;
        }
    }


}
