public class Aviao {

    private Passageiro[] assentos = new Passageiro[50];
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

    void removerPessoa(int assento){

        if(assento < 0 || assento > assentos.length - 1){
            System.out.println("assento invalido!");
            return;
        }
        if(assentos[assento] == null){
            System.out.println("assento ja desucupado!");
            return;
        }
        assentos[assento] = null;
        System.out.println("Passageiro removido!");
    }


    void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    int contarPassageiros(){
        int count_passageiro = 0;
        for(int i = 0; i < assentos.length;i++){
            if(assentos[i] != null){
                count_passageiro++;
            }
        } return count_passageiro;
    }
}
