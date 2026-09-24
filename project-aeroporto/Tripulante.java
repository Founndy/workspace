public class Tripulante extends Pessoa{

    private String titulo;

    public Tripulante(String nome,String cpf,String titulo){
        super(nome, cpf);
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public void anunciar(){
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Titulo: " + getTitulo());
    }
}



