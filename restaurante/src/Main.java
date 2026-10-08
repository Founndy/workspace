public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema do restaurante");

        Prato feijoada = new Prato("Feijoada", 45.90, DiaSemana.QUARTA);
        Prato lasanha = new Prato("Lasanha", 39.90, DiaSemana.SEXTA);
        Prato salmao = new Prato("Salmao grelhado", 62.00, DiaSemana.SEXTA);

        Cardapio cardapio = new Cardapio();
        cardapio.adicionarPrato(feijoada);
        cardapio.adicionarPrato(lasanha);
        cardapio.adicionarPrato(salmao);
        cardapio.adicionarPrato(lasanha);
        cardapio.exibirPratosDoDia(DiaSemana.SEXTA);

        Garcom garcom01 = new Garcom(1, "Joao Pereira", "11122233344");
        Garcom garcom02 = new Garcom(2, "Maria Alves", "22233344455");
        Telefonista telefonista01 = new Telefonista(3, "Paula Dias", "33344455566");

        Regiao salao = new Regiao("Salao");
        salao.adicionarGarcom(garcom01);
        Regiao varanda = new Regiao("Varanda");
        varanda.adicionarGarcom(garcom02);

        Cliente cliente01 = new Cliente(10, "Ana Souza", "10120230344");
        Cliente cliente02 = new Cliente(11, "Bruno Lima", "20230340455");
        Cliente cliente03 = new Cliente(12, "Clara Mendes", "30340450566");

        System.out.println("\n== Pedido 1: imediato no salao");
        Pedido pedido01 = new Pedido(cliente01, DiaSemana.SEXTA);
        pedido01.escolherPrato(cardapio, feijoada);
        pedido01.escolherPrato(cardapio, lasanha);
        pedido01.coletarPorGarcom(garcom02, salao);
        pedido01.coletarPorGarcom(garcom01, salao);
        pedido01.exibirResumo();

        System.out.println("\n== Pedido 2: agendado na mesa da varanda");
        Pedido pedido02 = new Pedido(cliente02, DiaSemana.QUARTA);
        pedido02.escolherPrato(cardapio, feijoada);
        pedido02.coletarPorGarcom(garcom02, varanda);
        pedido02.agendarMesa("20:00", 7);
        pedido02.exibirResumo();

        System.out.println("\n== Pedido 3: entrega em domicilio pela telefonista");
        Pedido pedido03 = new Pedido(cliente03, DiaSemana.SEXTA);
        pedido03.escolherPrato(cardapio, salmao);
        pedido03.coletarPorTelefonista(telefonista01);
        pedido03.coletarPorGarcom(garcom01, salao);
        pedido03.agendarDomicilio("19:30", "Rua das Flores, 123");
        pedido03.agendarMesa("20:00", 3);
        pedido03.exibirResumo();
    }
}
