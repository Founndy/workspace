package vetcare.modelo;

public class Tutor extends Pessoa{

    public Tutor(String nome, String cpf,String telefone){
        super(nome,cpf,telefone);
    }
    public Tutor(String nome,String cpf){
        super(nome,cpf);
    }
    private String descricao;

    @Override
    public String getDescricao(){
        return descricao;
    }

}
