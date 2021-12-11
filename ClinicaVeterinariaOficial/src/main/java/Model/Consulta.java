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
        this.data = Parser.ToCalendar(data);
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
        return "Consulta{" +
                "id=" + id +
                ", data='" + data.toString() + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", idAnimal=" + idAnimal +
                ", idVeterinario=" + idVeterinario +
                ", idTratamento=" + idTratamento +
                ", terminou=" + terminou +
                '}';
    }
}

// Referencia: https://www.devmedia.com.br/trabalhando-com-as-classes-date-calendar-e-simpledateformat-em-java/27401

// https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html
