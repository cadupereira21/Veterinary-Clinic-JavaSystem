/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import View.GenericTableModel;
import View.TratamentoTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T-Gamer
 */
public class TratamentoController {

    private static Tratamento tratamentoSelecionado;

    private static JTable tratTable;
    private static JTextField nomeTextField;
    private static JTextField dataInicioTextField;
    private static JTextField dataEncerramentoTextField;
    private static JTextArea descricaoTextArea;
    private static JCheckBox isEncerradoCheckBox;

    public static void SetFields(JTable table, JTextArea descricao, JCheckBox isEncerrado, JTextField nome, JTextField dataIni, JTextField dataEnc){
        tratTable = table;
        descricaoTextArea = descricao;
        isEncerradoCheckBox = isEncerrado;
        nomeTextField = nome;
        dataInicioTextField = dataIni;
        dataEncerramentoTextField = dataEnc;
    }

    public static Tratamento getTratamentoSelecionado() {
        return tratamentoSelecionado;
    }

    public static void setTratamentoSelecionado(Tratamento tratamentoSelecionado) {
        TratamentoController.tratamentoSelecionado = tratamentoSelecionado;
    }

    public static void SetTableModel(){
        SetTableModel(new TratamentoTableModel(TratamentoDAO.getInstance().retrieveByAnimalId(AnimalController.GetSelecionado().getId())));
    }

    public static void SetTableModel(GenericTableModel tableModel){
        tratTable.setModel(tableModel);
    }

    public static void ResetTableModel(){
        ResetTextFields();

        tratTable.setModel(new TratamentoTableModel(TratamentoDAO.getInstance().retrieveByAnimalId(AnimalController.GetSelecionado().getId())));
    }

    public static void ResetTextFields(){
        tratamentoSelecionado = null;

        nomeTextField.setText("");
        dataInicioTextField.setText("");
        dataEncerramentoTextField.setText("");
        descricaoTextArea.setText("");
        isEncerradoCheckBox.setSelected(false);
    }

    public static void Read() {
        Read((Tratamento) ((GenericTableModel) tratTable.getModel()).getItem(tratTable.getSelectedRow()));
    }

    public static void Read(Tratamento trat){
        tratamentoSelecionado = trat;

        nomeTextField.setText(tratamentoSelecionado.getNome());
        dataInicioTextField.setText(Parser.DataToString(tratamentoSelecionado.getDtInicio()));
        dataEncerramentoTextField.setText(Parser.DataToString(tratamentoSelecionado.getDtFim()));
        descricaoTextArea.setText(tratamentoSelecionado.getDescricao());
        isEncerradoCheckBox.setSelected(tratamentoSelecionado.getTerminou() == 1);

        ConsultaController.SetTableModel(new ConsultaTableModel(ConsultaDAO.getInstance().retrieveByIdTratamento(tratamentoSelecionado.getId())));
    }

    public static void Create(){
        if(tratamentoSelecionado != null){
            System.out.println("Não deixe um tratamento selecionado!");
            return;
        }

        var nome = nomeTextField.getText();
        var descricao = descricaoTextArea.getText();
        var dataIni = Parser.ToCalendar(dataInicioTextField.getText());
        var dataFim = Parser.ToCalendar(dataEncerramentoTextField.getText());

        try {
            var newTratamento = TratamentoDAO.getInstance().create(nome, descricao, dataIni, dataFim, AnimalController.GetSelecionado().getId(), 0);

            ((GenericTableModel) tratTable.getModel()).addItem(newTratamento);
        } catch (Exception e){
            System.out.println(e.getMessage() + " ::: Todos os campos de Tratamento devem estar preenchidos!");
        }
    }

    public static void Update(){
        System.out.println(tratamentoSelecionado.getId());
        var newTratamento = new Tratamento(tratamentoSelecionado.getId(), nomeTextField.getText(), descricaoTextArea.getText(), dataInicioTextField.getText(),
                dataEncerramentoTextField.getText(), AnimalController.GetSelecionado().getId(), 0);
        TratamentoDAO.getInstance().update(newTratamento);
    }

    public static void Delete(){
        TratamentoDAO.getInstance().delete(tratamentoSelecionado);
        ResetTableModel();
    }

    public static void Search(String key, String value){
        List<Tratamento> vDados = new ArrayList<>();

        switch(key){
            case "Nome": vDados = TratamentoDAO.getInstance().retrieveBySimilarName(value, AnimalController.GetSelecionado().getId()); break;
        }

        SetTableModel(new TratamentoTableModel(vDados));
    }

    public static Tratamento GetSelecionado() {
        return tratamentoSelecionado;
    }
}
