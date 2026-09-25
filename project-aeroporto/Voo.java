public class Voo {
    private String codVoo;
    private String aeroSaida;
    private String aeroEntrada;
    private StatusVoo estadoAtual;
    private Aviao aviao;

    private Tripulante[] tripulantes = new Tripulante[10];


    Voo(String codVoo, String aeroSaida, String aeroEntrada) {
        setCodVoo(codVoo);
        setAeroSaida(aeroSaida);
        setAeroEntrada(aeroEntrada);
        this.estadoAtual = StatusVoo.PLANEJADO;

    }

    String getCodVoo() {
        return codVoo;
    }

    String getAeroSaida() {
        return aeroSaida;
    }

    String getAeroEntrada() {
        return aeroEntrada;
    }

    StatusVoo getEstadoAtual() {
        return estadoAtual;
    }

    void setEstadoAtual(StatusVoo estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    void setCodVoo(String codVoo) {
        if (codVoo != null && !codVoo.isBlank()) {
            this.codVoo = codVoo;
        }
    }

    void setAeroSaida(String aeroSaida) {
        if (aeroSaida != null && !aeroSaida.isBlank()) {
            this.aeroSaida = aeroSaida;
        }
    }


    void setAeroEntrada(String aeroEntrada) {
        if (aeroEntrada != null && !aeroEntrada.isBlank()) {
            this.aeroEntrada = aeroEntrada;
        }
    }

    void alocarAviao(Aviao aviao) {


        if (this.aviao != null) {
            System.out.println("Avião já alocado!");
            return;
        }
        if (StatusVoo.PLANEJADO != this.estadoAtual) {
            System.out.println("voo indisponivel ou cancelado!");
            return;
        }
        if (aviao == null) {
            System.out.println("Avião inválido para alocamento.");
            return;
        }
        if (!aviao.isDisponivel()) {
            System.out.println("Avião não disponível para ser alocado!");
            return;
        }


        this.aviao = aviao;
        aviao.setDisponivel(false);
        System.out.println("Aviao alocado");

    }
    void alocarTripulante(Tripulante tripulante) {
        if (tripulante == null) {
            System.out.println("Tripulante invalido!");
            return;
        }
        if (StatusVoo.PLANEJADO != this.estadoAtual) {
            System.out.println("Voo indisponivel ou cancelado!");
            return;
        }
        for (int i = 0; i < tripulantes.length; i++) {
            if (tripulante == tripulantes[i]) {
                System.out.println("Tripulante ja alocado!");
                return;
            }
        }
        if (tripulante instanceof Comissario) {
            for (int i = 0; i < tripulantes.length; i++) {
                if (tripulantes[i] == null) {
                    tripulantes[i] = tripulante;
                    System.out.println("Tripulante alocado com sucesso!");
                    return;
                }

            }
        }
        if (tripulante instanceof Piloto || tripulante instanceof Copiloto) {
            if (tripulante instanceof Piloto) {
                for (int i = 0; i < tripulantes.length; i++) {
                    if (tripulantes[i] instanceof Piloto) {
                        System.out.println("Piloto ja alocado!");
                        return;
                    }
                }
            }
            if(tripulante instanceof Copiloto) {
                for (int i = 0; i < tripulantes.length; i++) {
                    if (tripulantes[i] instanceof Copiloto) {
                        System.out.println("Copiloto ja alocado!");
                        return;
                    }
                }
            }
            for (int i = 0; i < tripulantes.length; i++) {
                if (tripulantes[i] == null) {
                    tripulantes[i] = tripulante;
                    System.out.println("Tripulante alocado com sucesso!");
                    return;
                }

            }

        }

        System.out.println("Vagas indisponivel para Tripulantes.");
    }

    boolean requisitosAlcancado(){
       int count_comisario = 0, count_copiloto = 0, count_piloto = 0;
        for(int i = 0; i < tripulantes.length;i++) {
            if (tripulantes[i] instanceof Piloto) {
                count_piloto++;
            }
            if (tripulantes[i] instanceof Copiloto) {
                count_copiloto++;
            }
            if (tripulantes[i] instanceof Comissario) {
                count_comisario++;
            }
        }
               return count_copiloto == 1 && count_comisario >= 1 && count_piloto == 1;
        }
    }




