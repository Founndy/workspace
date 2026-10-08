public class Cardapio {

    private Prato[] pratos = new Prato[20];


    void adicionarPrato(Prato prato) {
        if (prato == null) {
            System.out.println("Prato invalido!");
            return;
        }
        if (contem(prato)) {
            System.out.println("Prato ja esta no cardapio!");
            return;
        }
        for (int i = 0; i < pratos.length; i++) {
            if (pratos[i] == null) {
                pratos[i] = prato;
                System.out.println("Prato " + prato.getNome() + " adicionado ao cardapio!");
                return;
            }
        }
        System.out.println("Cardapio cheio!");
    }

    boolean contem(Prato prato) {
        for (int i = 0; i < pratos.length; i++) {
            if (pratos[i] != null && pratos[i] == prato) {
                return true;
            }
        }
        return false;
    }

    void exibirPratosDoDia(DiaSemana dia) {
        System.out.println("Pratos de " + dia + ":");
        for (Prato p : pratos) {
            if (p != null && p.getDia() == dia) {
                System.out.println(" - " + p.getNome() + " R$ " + p.getPreco());
            }
        }
    }
}
