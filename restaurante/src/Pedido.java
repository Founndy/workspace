public class Pedido {
    private Cliente cliente;
    private DiaSemana dia;
    private Prato prato;
    private Garcom garcom;
    private Telefonista telefonista;
    private TipoAtendimento tipo;
    private String horario;
    private int mesa;
    private String endereco;


    Pedido(Cliente cliente, DiaSemana dia) {
        setCliente(cliente);
        setDia(dia);
        this.tipo = TipoAtendimento.IMEDIATO;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public Prato getPrato() {
        return prato;
    }

    public Garcom getGarcom() {
        return garcom;
    }

    public Telefonista getTelefonista() {
        return telefonista;
    }

    public TipoAtendimento getTipo() {
        return tipo;
    }

    public String getHorario() {
        return horario;
    }

    public int getMesa() {
        return mesa;
    }

    public String getEndereco() {
        return endereco;
    }

    void setCliente(Cliente cliente) {
        if (cliente != null) {
            this.cliente = cliente;
        }
    }

    void setDia(DiaSemana dia) {
        if (dia != null) {
            this.dia = dia;
        }
    }

    void escolherPrato(Cardapio cardapio, Prato prato) {
        if (this.prato != null) {
            System.out.println("Prato ja escolhido!");
            return;
        }
        if (cardapio == null || prato == null) {
            System.out.println("Prato invalido!");
            return;
        }
        if (!cardapio.contem(prato)) {
            System.out.println("Prato nao esta no cardapio!");
            return;
        }
        if (prato.getDia() != this.dia) {
            System.out.println("Prato " + prato.getNome() + " indisponivel em " + this.dia + "!");
            return;
        }

        this.prato = prato;
        System.out.println("Prato " + prato.getNome() + " escolhido!");
    }

    void coletarPorGarcom(Garcom garcom, Regiao regiao) {
        if (this.garcom != null || this.telefonista != null) {
            System.out.println("Pedido ja coletado!");
            return;
        }
        if (garcom == null || regiao == null) {
            System.out.println("Garcom ou regiao invalidos!");
            return;
        }
        if (!regiao.atende(garcom)) {
            System.out.println("Garcom " + garcom.getNome() + " nao atende a regiao " + regiao.getNome() + "!");
            return;
        }

        this.garcom = garcom;
        System.out.println("Pedido coletado pelo garcom " + garcom.getNome());
    }

    void coletarPorTelefonista(Telefonista telefonista) {
        if (this.garcom != null || this.telefonista != null) {
            System.out.println("Pedido ja coletado!");
            return;
        }
        if (telefonista == null) {
            System.out.println("Telefonista invalida!");
            return;
        }

        this.telefonista = telefonista;
        System.out.println("Pedido coletado pela telefonista " + telefonista.getNome());
    }

    void agendarMesa(String horario, int mesa) {
        if (this.tipo != TipoAtendimento.IMEDIATO) {
            System.out.println("Pedido ja agendado!");
            return;
        }
        if (horario == null || horario.isBlank()) {
            System.out.println("Horario invalido!");
            return;
        }
        if (mesa <= 0) {
            System.out.println("Mesa invalida!");
            return;
        }

        this.horario = horario;
        this.mesa = mesa;
        this.tipo = TipoAtendimento.MESA;
        System.out.println("Pedido agendado para " + dia + " as " + horario + " na mesa " + mesa);
    }

    void agendarDomicilio(String horario, String endereco) {
        if (this.tipo != TipoAtendimento.IMEDIATO) {
            System.out.println("Pedido ja agendado!");
            return;
        }
        if (horario == null || horario.isBlank()) {
            System.out.println("Horario invalido!");
            return;
        }
        if (endereco == null || endereco.isBlank()) {
            System.out.println("Endereco invalido!");
            return;
        }

        this.horario = horario;
        this.endereco = endereco;
        this.tipo = TipoAtendimento.DOMICILIO;
        System.out.println("Pedido agendado para " + dia + " as " + horario + " em " + endereco);
    }

    void exibirResumo() {
        System.out.println("--- Pedido de " + cliente.getNome() + " ---");
        if (prato == null) {
            System.out.println("Prato: nenhum");
        } else {
            System.out.println("Prato: " + prato.getNome() + " R$ " + prato.getPreco());
        }
        if (garcom != null) {
            System.out.println("Coletado por: garcom " + garcom.getNome());
        } else if (telefonista != null) {
            System.out.println("Coletado por: telefonista " + telefonista.getNome());
        } else {
            System.out.println("Coletado por: ninguem ainda");
        }
        if (tipo == TipoAtendimento.IMEDIATO) {
            System.out.println("Atendimento: imediato (" + dia + ")");
        } else if (tipo == TipoAtendimento.MESA) {
            System.out.println("Atendimento: " + dia + " as " + horario + ", mesa " + mesa);
        } else {
            System.out.println("Atendimento: " + dia + " as " + horario + ", entrega em " + endereco);
        }
    }
}
