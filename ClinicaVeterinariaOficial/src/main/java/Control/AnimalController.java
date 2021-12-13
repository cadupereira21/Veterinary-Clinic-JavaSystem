/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import View.AnimalTableModel;
import View.ConsultasRecentesListModel;
import View.GenericTableModel;

import javax.swing.*;
import java.util.ArrayList;

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

    public static void SetTableModel(){
        var clienteId = ((Cliente)ClienteController.GetSelecionado()).getId();

        AnimalTableModel tableModel = new AnimalTableModel(AnimalDAO.getInstance().retrieveByIdCliente(clienteId));
        animalTable.setModel(tableModel);
    }

    public static void SetTableModel(GenericTableModel tableModel){
        animalTable.setModel(tableModel);
    }

    public static void ResetTableModel(){
        animalSelecionado = null;
        especieSelecionado = null;

        nomeTextField.setText("");
        especieTextField.setText("");

        SetTableModel(new AnimalTableModel(new ArrayList<Animal>()));
    }

    public static void ResetTextFields(){
        animalSelecionado = null;
        especieSelecionado = null;

        nomeTextField.setText("");
        especieTextField.setText("");
        consultasRecentesList.setModel(new ConsultasRecentesListModel(new ArrayList<>()));
    }

    public static Object Create(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void Read() {
        animalSelecionado = (Animal) ((GenericTableModel) animalTable.getModel()).getItem(animalTable.getSelectedRow());
        especieSelecionado = EspecieDAO.getInstance().retrieveById(animalSelecionado.getIdEspecie());
        //ConsultaController.SetTableModel();

        System.out.println("ID do animal Selecionado: " + animalSelecionado.getId());
        consultasRecentesList.setModel(new ConsultasRecentesListModel(ConsultaDAO.getInstance().retrieveByIdAnimal(animalSelecionado.getId())));
        nomeTextField.setText(animalSelecionado.getNome());
        especieTextField.setText(especieSelecionado.getNome());
    }

    public static void Update(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void Delete(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
