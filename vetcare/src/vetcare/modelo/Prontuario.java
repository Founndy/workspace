package vetcare.modelo;

import vetcare.excecao.DadoInvalidoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Histórico de um animal: as anotações de cada atendimento realizado.
 * Só o Animal cria um prontuário (construtor de pacote).
 */
public class Prontuario {

    private final LocalDate abertura;
    private final List<String> registros = new ArrayList<>();

    Prontuario() {
        abertura = LocalDate.now();
    }

    /** Acrescenta uma anotação; vazia ou nula não é aceita. */
    public void registrar(String anotacao){
        if(anotacao == null || anotacao.isBlank()){
            throw  new DadoInvalidoException("Registro Invalido");
        }
        registros.add(anotacao);
    }

    /** Devolve quantas anotações o prontuário tem. */
    public int total(){
        return registros.size();
    }

    /** Devolve uma cópia das anotações, que não pode ser alterada. */
    public List<String> getRegistros() {
        return List.copyOf(registros);
    }

    /** Devolve a data em que o prontuário foi aberto. */
    public LocalDate getAbertura() {
        return abertura;
    }

}
