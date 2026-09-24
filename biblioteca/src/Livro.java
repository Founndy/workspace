public class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponivel;

    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void emprestarLivro() {
        if (isDisponivel()) {
            disponivel = false;
            System.out.println("SEU EMPRÉSTIMO FOI REALIZADO COM SUCESSO");
        } else {
            System.out.println("LIVRO INDISPONÍVEL");
        }
    }

    public void devolverLivro() {
        disponivel = true;
        System.out.println("SUA DEVOLUÇÃO FOI REALIZADA COM SUCESSO");
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }
}
