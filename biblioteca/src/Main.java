public class Main {
    public static void main(String[] args) {

        Livro livro = new Livro(
                "Dom Casmurro",
                "Machado de Assis",
                "978-85-359-0277-5"
        );

        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor());
        System.out.println("ISBN: " + livro.getIsbn());
        System.out.println("Disponível: " + livro.isDisponivel());

        System.out.println("\n--- EMPRÉSTIMO ---");
        livro.emprestarLivro();

        System.out.println("Disponível: " + livro.isDisponivel());

        System.out.println("\n--- TENTANDO EMPRESTAR NOVAMENTE ---");
        livro.emprestarLivro();

        System.out.println("\n--- DEVOLUÇÃO ---");
        livro.devolverLivro();

        System.out.println("Disponível: " + livro.isDisponivel());
    }
}
