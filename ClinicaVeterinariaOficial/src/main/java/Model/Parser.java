package Model;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static Map<String, Integer> ToHour(String hour){
        Map<String, Integer> aux = new HashMap<String, Integer>(2);
        String[] input = hour.split("\\:");

        aux.put("Hour", Integer.parseInt(input[0]));
        aux.put("Minute", Integer.parseInt(input[1]));

        return aux;
    }

    public static Calendar ToCalendar(String data) {

        int dia, mes, ano;

        String[] aux = data.split("\\/");

        ano = Integer.parseInt(aux[2]);
        mes = Integer.parseInt(aux[1]);
        dia = Integer.parseInt(aux[0]);

        Calendar auxiliar = Calendar.getInstance();
        auxiliar.set(ano, mes-1, dia);

        return auxiliar;
    }

    public static Calendar ToCalendar(String data, int h, int m) {

        var auxiliar = ToCalendar(data);
        auxiliar.set(Calendar.HOUR_OF_DAY, h);
        auxiliar.set(Calendar.MINUTE, m);

        return auxiliar;
    }

    public static String DataToString(Calendar c){
        return "" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH)+1 + "/" + c.get(Calendar.YEAR);
    }

    public static String HorarioToString(Calendar c){
        return "" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
    }

}
