/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import View.AnimalTableModel;
import View.ConsultasRecentesListModel;
import View.ExameTableModel;
import View.GenericTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T-Gamer
 */
public class AnimalController {

    private static Animal animalSelecionado;
    private static Especie especieSelecionado;

    private static JTextField nomeTextField;
    private static JTextField especieTextField;
    private static JList consultasRecentesList;
    private static JTable animalTable;

    public static void SetFields(JTextField nome, JTextField especie, JList consultasRecentes, JTable table){
        nomeTextField = nome;
        especieTextField = especie;
        consultasRecentesList = consultasRecentes;
        animalTable = table;
    }

    public static void SelecionarConsulta(){
        var consultaSelecionada = ((Consulta)consultasRecentesList.getModel().getElementAt(consultasRecentesList.getSelectedIndex()));

        TratamentoController.Read(TratamentoDAO.getInstance().retrieveById(consultaSelecionada.getIdTratamento()));
        ConsultaController.Read(consultaSelecionada);
    }

    public static void SetTableModel(){
        ResetTextFields();
        var clienteId = ((Cliente)ClienteController.GetSelecionado()).getId();

        AnimalTableModel tableModel = new AnimalTableModel(AnimalDAO.getInstance().retrieveByIdCliente(clienteId));
        animalTable.setModel(tableModel);
    }

    public static void SetTableModel(GenericTableModel tableModel){
        ResetTextFields();
        animalTable.setModel(tableModel);
    }

    public static void ResetTableModel(){
        ResetTextFields();

        SetTableModel(new AnimalTableModel(new ArrayList<Animal>()));
    }

    public static void ResetTextFields(){
        animalSelecionado = null;
        especieSelecionado = null;

        nomeTextField.setText("");
        especieTextField.setText("");
        consultasRecentesList.setModel(new ConsultasRecentesListModel(new ArrayList<>()));
    }

    public static Animal GetSelecionado(){
        return animalSelecionado;
    }

    public static void Create() {
        var newAnimal = AnimalDAO.getInstance().create(((Cliente)ClienteController.GetSelecionado()).getId(), "Nome de Exemplo", 1900, "Masculino ou Feminino", 1);
        ((GenericTableModel)animalTable.getModel()).addItem(newAnimal);
    }

    public static void Search(String key, String value){
        List<Animal> vDados = new ArrayList<>();

        var clienteId = ((Cliente)ClienteController.GetSelecionado()).getId();

        AnimalTableModel tableModel = new AnimalTableModel(AnimalDAO.getInstance().retrieveByIdCliente(clienteId));

        switch (key){
            case "Nome": vDados = AnimalDAO.getInstance().retrieveBySimilarName(value, clienteId); break;
        }

        SetTableModel(new AnimalTableModel(vDados));
    }

    public static void Read() {
        ExameController.SetTableModel(new ExameTableModel(new ArrayList()));

        animalSelecionado = (Animal) ((GenericTableModel) animalTable.getModel()).getItem(animalTable.getSelectedRow());
        especieSelecionado = EspecieDAO.getInstance().retrieveById(animalSelecionado.getIdEspecie());
        //ConsultaController.SetTableModel();

        System.out.println("ID do animal Selecionado: " + animalSelecionado.getId());
        consultasRecentesList.setModel(new ConsultasRecentesListModel(ConsultaDAO.getInstance().retrieveByIdAnimal(animalSelecionado.getId())));
        nomeTextField.setText(animalSelecionado.getNome());
        especieTextField.setText(especieSelecionado.getNome());

        TratamentoController.SetTableModel();
        ConsultaController.SetTableModel();
    }

    public static void Update() {
        var especieColumn = (String)animalTable.getModel().getValueAt(animalTable.getSelectedRow(), 3);

        if(EspecieDAO.getInstance().retrieveBySimilarName(especieColumn).isEmpty()){
            EspecieDAO.getInstance().create(especieColumn);
        }

        AnimalDAO.getInstance().update(animalSelecionado);
        ClienteController.ResetTableModel();
    }

    public static void Delete() {
        AnimalDAO.getInstance().delete(animalSelecionado);
        ClienteController.ResetTableModel();
    }

}
