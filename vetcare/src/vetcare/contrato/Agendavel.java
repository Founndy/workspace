package vetcare.contrato;
import java.time.LocalDateTime;

/** Algo que pode ser marcado na agenda da clínica e desmarcado depois. */
public interface Agendavel {

    /**
     * Marca o item para uma data e hora.
     * @param datahora quando o item vai acontecer
     */
    void agendar(LocalDateTime datahora);

    /** Desmarca o item da agenda. */
    void cancelar();
}
