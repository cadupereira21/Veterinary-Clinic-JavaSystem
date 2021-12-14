/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import View.ExameTableModel;
import View.GenericTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T-Gamer
 */
public class ExameController {

    private static Exame exameSelecionado;

    private static JTextField nomeTextField;
    private static JTextField consultaTextField;
    private static JTextArea descricaoTextArea;
    private static JTable examesTable;

    public static void SetFields(JTextField nome, JTextField consultaNome, JTextArea descricao, JTable table){
        nomeTextField = nome;
        consultaTextField = consultaNome;
        descricaoTextArea = descricao;
        examesTable = table;
    }

    public static void SetTableModel(){
        List<Exame> aux = new ArrayList<>();

        try {
            aux = ExameDAO.getInstance().retrieveByIdConsulta(ConsultaController.GetSelecionado().getId());
        } catch (NullPointerException npe){
            System.out.println(npe.getMessage() + " ::: Não há consulta selecionada!");
        }

        if(aux == null){
            System.out.println("Não há exame para essa consulta!");
            return;
        }

        SetTableModel(new ExameTableModel(aux));
    }

    public static void SetTableModel(GenericTableModel tableModel){
        examesTable.setModel(tableModel);
    }

    public static void Create(){

        var consultaId = 0;

        try {
            consultaId = ConsultaController.GetSelecionado().getId();
        } catch (NullPointerException e){
            var trat = TratamentoDAO.getInstance().retrieveBySimilarName(consultaTextField.getText()).get(0);
            consultaId = ConsultaDAO.getInstance().retrieveByIdTratamento(trat.getId()).get(0).getId();
        }

        Create(consultaId);
    }

    public static void Create(int idConsulta){
        ExameDAO.getInstance().create(nomeTextField.getText(), idConsulta);

        SetTableModel();
    }

    public static void Read(){
        Read((Exame)((GenericTableModel) examesTable.getModel()).getItem(examesTable.getSelectedRow()));
        System.out.println(exameSelecionado.getNome());
    }

    public static void Read(Exame exame){
        exameSelecionado = exame;

        nomeTextField.setText(exame.getNome());
        consultaTextField.setText(TratamentoController.GetSelecionado().getNome());
        descricaoTextArea.setText(ConsultaController.GetSelecionado().getComentarios());
    }

    public static void Delete(){
        ExameDAO.getInstance().delete(exameSelecionado);
        SetTableModel();
    }
    
}
