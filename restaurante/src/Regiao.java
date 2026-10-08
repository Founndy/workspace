public class Regiao {

    private String nome;
    private Garcom[] garcons = new Garcom[5];


    Regiao(String nome) {
        setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    void adicionarGarcom(Garcom garcom) {
        if (garcom == null) {
            System.out.println("Garcom invalido!");
            return;
        }
        if (atende(garcom)) {
            System.out.println("Garcom ja esta nesta regiao!");
            return;
        }
        for (int i = 0; i < garcons.length; i++) {
            if (garcons[i] == null) {
                garcons[i] = garcom;
                System.out.println("Garcom " + garcom.getNome() + " adicionado a regiao " + nome);
                return;
            }
        }
        System.out.println("Vagas indisponiveis para garcons.");
    }

    boolean atende(Garcom garcom) {
        for (int i = 0; i < garcons.length; i++) {
            if (garcons[i] != null && garcons[i] == garcom) {
                return true;
            }
        }
        return false;
    }
}
