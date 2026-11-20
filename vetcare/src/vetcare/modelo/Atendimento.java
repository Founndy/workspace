package vetcare.modelo;
import vetcare.contrato.Agendavel;
import vetcare.contrato.Pagavel;
import vetcare.excecao.DadoInvalidoException;
import vetcare.excecao.OperacaoInvalidaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Atendimento implements Pagavel, Agendavel,Comparable<Atendimento>{

    private static int proximoId = 1;
    private final int id;
    protected Animal animal;
    protected Veterinario veterinario;
    protected LocalDateTime dataHora;

    private StatusAtendimento status;

    public Atendimento(Animal animal, Veterinario veterinario) {
        if(animal == null || veterinario == null) {
            throw new DadoInvalidoException("Dados incorretos");
        }
        this.veterinario = veterinario;
        this.animal = animal;
        this.id = proximoId++;
        status = StatusAtendimento.PENDENTE;
    }


    @Override
    public void agendar(LocalDateTime dataHora){
        if(dataHora == null) {
            throw new DadoInvalidoException("Data invalida!");
        }
        if(status == StatusAtendimento.CANCELADO || status == StatusAtendimento.REALIZADO){
            throw new OperacaoInvalidaException("Status Invalido!");
        }
        if(dataHora.isBefore(LocalDateTime.now())){
            throw new OperacaoInvalidaException("Não é possível agendar no passado.");
        }
        this.dataHora = dataHora;
        status = StatusAtendimento.AGENDADO;
    }

    public void agendar(LocalDate dia, LocalTime hora){

        agendar(LocalDateTime.of(dia, hora));
    }


    @Override
    public void cancelar(){
        if(status == StatusAtendimento.REALIZADO){
            throw new OperacaoInvalidaException("Não é possível cancelar um atendimento já realizado.");
        }
        if(status == StatusAtendimento.CANCELADO){
            throw new OperacaoInvalidaException("O atendimento já está cancelado");
        }
        status = StatusAtendimento.CANCELADO;
    }

    public void realizar(){
        if(status != StatusAtendimento.AGENDADO){
            throw new OperacaoInvalidaException("Só é possível realizar um atendimento agendado.");
        }
        status = StatusAtendimento.REALIZADO;
    }


    public int getId(){
        return id;
    }
    public Animal getAnimal(){
        return animal;
    }
    public Veterinario getVeterinario(){
        return veterinario;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public StatusAtendimento getStatus() {
        return status;
    }
    public abstract String getTipo();

}
