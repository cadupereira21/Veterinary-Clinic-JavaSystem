package Model;

import java.time.LocalTime;
import java.util.Calendar;

public class Consulta {
    private final int id;
    private Calendar data;
    private String comentarios;
    private int idAnimal;
    private int idVeterinario;
    private int idTratamento;
    private boolean terminou;

    public Consulta(int id, String data, String horario, String comentarios, int idAnimal, int idVeterinario, int idTratamento, int terminou) {
        this.id = id;

        var h = Parser.ToHour(horario);
        this.data = Parser.ToCalendar(data, h.get("Hour"), h.get("Minute"));
        this.comentarios = comentarios;
        this.idAnimal = idAnimal;
        this.idVeterinario = idVeterinario;
        this.idTratamento = idTratamento;
        this.terminou = terminou == 1;
    }

    public Calendar getData() { return data; }
    public void setData(Calendar data) { this.data = data; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public int getIdAnimal() { return idAnimal; }

    public int getIdVeterinario() { return idVeterinario; }

    public int getIdTratamento() { return idTratamento; }

    public int getTerminou() { return terminou ? 1 : 0; }
    public void setTerminou(boolean terminou) { this.terminou = terminou;}

    public int getId() {return id;}

    @Override
    public String   toString() {
        var minutes  = getData().get(Calendar.MINUTE) == 0 ? "" : getData().get(Calendar.MINUTE);

        return "[ " + getData().get(Calendar.DAY_OF_MONTH) + "/" + getData().get(Calendar.MONTH)+1 + "/" + getData().get(Calendar.YEAR) + " - "
                + getData().get(Calendar.HOUR_OF_DAY) + "h" + minutes + " ] " + TratamentoDAO.getInstance().retrieveById(getIdTratamento()).getNome()
                + " (" + getComentarios() + ")";
    }
}

// Referencia: https://www.devmedia.com.br/trabalhando-com-as-classes-date-calendar-e-simpledateformat-em-java/27401

// https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html
