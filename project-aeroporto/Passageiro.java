public class Passageiro extends Pessoa{

    private String passaporte;

    Passageiro(String nome, String cpf, String passaporte){
        super(nome, cpf);
        this.passaporte = passaporte;
    }

    public String getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(String passaporte) {
        this.passaporte = passaporte;
    }
}
