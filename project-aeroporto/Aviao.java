public class Aviao {

    private Passageiro[] assentos = new Passageiro[50];
    private Tripulante[] tripulantes = new Tripulante[10];
    private boolean disponivel;


    Aviao(){
        disponivel = true;
    }

    public void adicionarPessoa(Passageiro passageiro, int assento){
        for(int i = 0; i < assentos.length; i++){
            if(passageiro == assentos[i]){
                System.out.println("Passageiro ja alocado!");
                return;
            }
        }
            if(assento > -1 && assento < assentos.length && assentos[assento] == null && passageiro != null){
                assentos[assento] = passageiro;
                System.out.println("Assento alocado com sucesso");
            }else{
                System.out.println("Assento indisponivel!\n tente novamente!");
            }
    }

    public void adicionarTripulante(Tripulante tripulante){
        for(int i = 0; i < tripulantes.length;i++) {
            if (tripulantes[i] == null) {
                tripulantes[i] = tripulante;
                System.out.println("Tripulante alocado com sucesso!");
                return;
            }
        }
                System.out.println("Sem espaço para tripulantes");
    }

    void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
