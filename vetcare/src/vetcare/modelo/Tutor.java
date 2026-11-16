package vetcare.modelo;

public class Tutor extends Pessoa{

    public Tutor(String nome, String cpf,String telefone){
        super(nome,cpf,telefone);
        this.crm = crm;
    }
    private String crm;

    @Override
    public String getDescricao(){
        return "CRM" + crm;
    }
    }
}
