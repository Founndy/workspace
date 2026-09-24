public class Pedido {
    private Cliente cliente;
    private Garcom garcom;
    private Prato prato;
    private Boolean agendamento;
    private String endereço;

    Pedido(Cliente cliente,Garcom garcom,Prato prato, Boolean agendamento, String endereço){
        this.cliente = cliente;
        this.garcom = garcom;
        this.prato = prato;
        this.agendamento = agendamento;
        this.endereço = endereço;
    }

    Pedido(Cliente cliente,Garcom garcom,Prato prato, Boolean agendamento){
        this.cliente = cliente;
        this.garcom = garcom;
        this.prato = prato;
        this.agendamento = agendamento;
    }

    Pedido(Cliente cliente,Telefonista telefonista,Prato prato, Boolean agendamento, String endereço){
        this.cliente = cliente;
        this.garcom = garcom;
        this.prato = prato;
        this.agendamento = agendamento;
        this.endereço = endereço;
    }

    Pedido(Cliente cliente,Telefonista telefonista, Prato prato, Boolean agendamento){
        this.cliente = cliente;
        this.garcom = garcom;
        this.prato = prato;
        this.agendamento = agendamento;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public Garcom getGarcom() {
        return garcom;
    }

    public Prato getPrato() {
        return prato;
    }

    public String getEndereço() {
        return endereço;
    }


}
