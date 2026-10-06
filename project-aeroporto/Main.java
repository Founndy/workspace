public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema de voos do aeroporto");


        Aviao aviao01 = new Aviao();
        Voo vooB30 = new Voo("123", "são paulo", "Goiania");
        vooB30.alocarAviao(aviao01);

        Piloto piloto01 = new Piloto("Carlos Flávio", "11122233355", "45dsa5");
        Copiloto copiloto01 = new Copiloto("Rebecca", "22335544889", "45ddt5");
        Comissario comissario01 = new Comissario("Eloah", "66887799224", "88kle5");

        Passageiro passageiro01 = new Passageiro("Ana Souza", "10120230344", "BR100001");
        Passageiro passageiro02 = new Passageiro("Bruno Lima", "20230340455", "BR100002");
        Passageiro passageiro03 = new Passageiro("Clara Mendes", "30340450566", "BR100003");
        Passageiro passageiro04 = new Passageiro("Diego Rocha", "40450560677", "BR100004");

        vooB30.alocarTripulante(piloto01);
        vooB30.alocarTripulante(copiloto01);
        vooB30.setEstadoAtual(StatusVoo.EM_ROTA);
        vooB30.alocarTripulante(comissario01);

        vooB30.embarcar(passageiro01 , 0);

        vooB30.setEstadoAtual(StatusVoo.EM_SOLO);

        vooB30.embarcar(passageiro01 , 0);
        vooB30.embarcar(passageiro01 , 10);
        vooB30.embarcar(passageiro02 , 1);
        vooB30.embarcar(passageiro03 , 2);
        vooB30.embarcar(passageiro04 , 3);

        vooB30.setOperacao(new Decolagem());
        vooB30.executarOperacao();


        vooB30.setOperacao(new Pouso());
        vooB30.executarOperacao();

        for (int assento = 0; assento <= 3; assento++) {
            vooB30.desembarcar(assento);
        }

        Pessoa[] pessoas = { piloto01, copiloto01, comissario01, passageiro01, passageiro02,passageiro03,passageiro04 };
        for (Pessoa p : pessoas) {
            p.anunciar();
        }

    }
}
