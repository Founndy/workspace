public class Passageiro extends Pessoa{

    private String passaporte;

    Passageiro(String nome, String cpf, String passaporte){
        super(nome, cpf);
        setPassaporte(passaporte);
    }

    public String getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(String passaporte) {
        if(passaporte != null && !passaporte.isBlank()){
        this.passaporte = passaporte;
        }
    }
}
