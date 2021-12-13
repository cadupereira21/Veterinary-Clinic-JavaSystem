package Model;

import java.util.Calendar;

public class Tratamento {

    private final int id;
    private String nome;
    private Calendar dtInicio;
    private Calendar dtFim;
    private String descricao;
    private int idAnimal;
    private boolean terminou;

    public Tratamento(int id, String nome, String descricao,String dtInicio, String dtFim, int idAnimal, int terminou) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dtInicio = Parser.ToCalendar(dtInicio);
        this.dtFim = Parser.ToCalendar(dtFim);
        this.idAnimal = idAnimal;
        this.terminou = terminou == 1;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getDtInicio() {
        return dtInicio;
    }
    public void setDtInicio(Calendar dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Calendar getDtFim() {
        return dtFim;
    }
    public void setDtFim(Calendar dtFim) {
        this.dtFim = dtFim;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public int getTerminou() {
        return terminou ? 1 : 0;
    }
    public void setTerminou(boolean terminou) {
        this.terminou = terminou;
    }

    public int getId() {return id;}

    @Override
    public String toString() {
        return "Tratamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dtInicio=" + dtInicio +
                ", dtFim=" + dtFim +
                ", idAnimal=" + idAnimal +
                ", terminou=" + terminou +
                '}';
    }
}
