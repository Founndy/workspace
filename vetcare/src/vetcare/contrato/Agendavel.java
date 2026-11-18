package vetcare.contrato;
import java.time.LocalDateTime;

/** Algo que pode ser marcado na agenda da clínica e desmarcado depois. */
public interface Agendavel {

    /**
     * Marca o item para uma data e hora.
     * @param dataHora quando o item vai acontecer
     */
    void agendar(LocalDateTime dataHora);

    /** Desmarca o item da agenda. */
    void cancelar();
}
