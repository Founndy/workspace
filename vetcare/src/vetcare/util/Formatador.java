package vetcare.util;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.text.NumberFormat;

/**
 * Transforma valores e datas em texto no padrão brasileiro.
 * Só tem métodos static: é usada sem criar objeto, como Formatador.moeda(150.0).
 */
public class Formatador {

    private Formatador(){}

    /**
     * Escreve um valor como dinheiro, no formato R$ 150,00.
     * @param valor o valor em reais
     * @return o valor formatado
     */
    public static String moeda(double valor){
        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        return fmt.format(valor);
    }

    /**
     * Escreve data e hora no formato dd/MM/yyyy HH:mm.
     * @param dataHora a data e hora a formatar; pode ser nula
     * @return o texto formatado, ou um aviso se for nula
     */
    public static String dataHora(LocalDateTime dataHora){
        if(dataHora == null){
            return "sem data";
        }
        DateTimeFormatter moldeHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(moldeHora);
    }

    /**
     * Escreve uma data no formato dd/MM/yyyy.
     * @param data a data a formatar; pode ser nula
     * @return o texto formatado, ou um aviso se for nula
     */
    public static String data(LocalDate data){
        if(data == null){
            return "sem data";
        }
        DateTimeFormatter moldeData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(moldeData);
    }
}
