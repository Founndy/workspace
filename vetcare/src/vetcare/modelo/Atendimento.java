package vetcare.modelo;
import vetcare.contrato.Agendavel;
import vetcare.contrato.Pagavel;
import vetcare.excecao.DadoInvalidoException;
import vetcare.excecao.OperacaoInvalidaException;
import vetcare.util.Formatador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Atendimento de um animal por um veterinário: pode ser agendado, cancelado e realizado.
 * É abstrata: cada tipo de atendimento calcula o próprio valor.
 */
public abstract class Atendimento implements Pagavel, Agendavel,Comparable<Atendimento>{

    private static int proximoId = 1;
    private final int id;
    protected Animal animal;
    protected Veterinario veterinario;
    protected LocalDateTime dataHora;

    private StatusAtendimento status;

    /** Cria o atendimento como PENDENTE, com o próximo id da sequência. */
    public Atendimento(Animal animal, Veterinario veterinario) {
        if(animal == null || veterinario == null) {
            throw new DadoInvalidoException("Dados incorretos");
        }
        this.veterinario = veterinario;
        this.animal = animal;
        this.id = proximoId++;
        status = StatusAtendimento.PENDENTE;
    }


    /** Agenda para a data e hora informadas; não aceita data no passado. */
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

    /** Agenda com o dia e a hora separados; segue as mesmas regras do outro agendar. */
    public void agendar(LocalDate dia, LocalTime hora){

        agendar(LocalDateTime.of(dia, hora));
    }


    /** Cancela o atendimento; não vale para um já realizado ou já cancelado. */
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

    /** Marca como realizado; só vale para um atendimento agendado. */
    public void realizar(){
        if(status != StatusAtendimento.AGENDADO){
            throw new OperacaoInvalidaException("Só é possível realizar um atendimento agendado.");
        }
        status = StatusAtendimento.REALIZADO;
    }

    /** Ordena pela data e hora (sem data vai para o fim); empate desempata pelo id. */
    @Override
    public int compareTo(Atendimento outro){
        if(this.dataHora == null && outro.dataHora == null){
            return Integer.compare(this.id, outro.id);
        }
        if(this.dataHora == null){
            return 1;
        }
        if(outro.dataHora == null){
            return -1;
        }
        int posicao = this.dataHora.compareTo(outro.dataHora);
        if(posicao == 0){
            return Integer.compare(this.id, outro.id);
        }
        return posicao;
    }


    /** Mostra o atendimento: "#1 Consulta | Rex | Luís Lima | 14/11/2026 14:00 | R$ 150,00 | REALIZADO". */
    @Override
    public String toString() {
        return "#" + id + " " + getTipo() + " | " + animal.getNome() + " | " + veterinario.getNome()
                + " | " + Formatador.dataHora(dataHora)
                + " | " + Formatador.moeda(calcularValor()) + " | " + status;
    }


    /** Devolve o número do atendimento. */
    public int getId(){
        return id;
    }
    /** Devolve o animal atendido. */
    public Animal getAnimal(){
        return animal;
    }
    /** Devolve o veterinário responsável. */
    public Veterinario getVeterinario(){
        return veterinario;
    }
    /** Devolve a data e hora marcadas, ou null se ainda não foi agendado. */
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    /** Devolve a situação atual do atendimento. */
    public StatusAtendimento getStatus() {
        return status;
    }
    /** Nome do tipo de atendimento; cada subclasse diz o seu. */
    public abstract String getTipo();

}
