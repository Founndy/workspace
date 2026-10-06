public abstract class Tripulante extends Pessoa {

    private String titulo;

    public Tripulante(String nome,String cpf,String titulo){
        super(nome, cpf);
        setTitulo(titulo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo != null && !titulo.isBlank()){
        this.titulo = titulo;
        }
    }

    @Override
    public void anunciar(){
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Titulo: " + getTitulo());
    }
}



