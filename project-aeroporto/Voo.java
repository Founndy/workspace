public class Voo {
    private String codVoo;
    private String nomeVoo;  //normalmente o nome é a cidade de chega.
    private String enderecoSaida;
    private String enderecoChegada;
    private String statusVoo;


    Tripulante[] tripulante = new Tripulante[10];
    Aviao aviao = new Aviao();
    Passageiro[] passageiro = new Passageiro[50];

    Voo(){
        statusVoo = "Planejado";
    }

}
