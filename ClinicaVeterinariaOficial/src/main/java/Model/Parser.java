package Model;

import java.time.LocalTime;
import java.util.Calendar;

public class Parser {

    public static Calendar ToCalendar(String data) {

        int dia, mes, ano;

        String aux = "";

        for (int i = 0; i<4; i++){
            aux += data.charAt(i);
        }

        ano = Integer.parseInt(aux);

        aux = "";
        for(int i = 5; i < 7; i++){
            aux += data.charAt(i);
        }

        mes = Integer.parseInt(aux);

        aux = "";
        for(int i = 8; i < 10; i++){
            aux += data.charAt(i);
        }

        dia = Integer.parseInt(aux);

        Calendar auxiliar = Calendar.getInstance();
        auxiliar.set(ano, mes, dia);

        return auxiliar;
    }

    public static Calendar ToCalendar(String data, int h, int m) {

        var auxiliar = ToCalendar(data);
        auxiliar.set(Calendar.HOUR_OF_DAY, h);
        auxiliar.set(Calendar.MINUTE, m);

        return auxiliar;
    }

    public static String DataToString(Calendar c){
        return "" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);
    }

    public static String HorarioToString(Calendar c){
        return "" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":00";
    }

}
