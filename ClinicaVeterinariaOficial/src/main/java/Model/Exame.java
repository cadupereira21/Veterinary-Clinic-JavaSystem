package Model;

import java.util.Calendar;

public class Exame {

    private final int id;
    private String nome;
    private int idConsulta;

    public Exame(int id, String descicao, int idConsulta) {
        this.id = id;
        this.nome = descicao;
        this.idConsulta = idConsulta;
    }

    public String getNome() {return nome;}
    public void setNome(String descricao) {this.nome = descricao;}

    public int getIdConsulta() {return idConsulta;}
    public void setIdConsulta(int idConsulta) {this.idConsulta = idConsulta;}

    public int getId() {return id;}

    @Override
    public String toString() {
        return "[ " + id + " ] - " + nome;
    }
}
