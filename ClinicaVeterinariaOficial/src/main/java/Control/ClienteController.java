/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import View.ClienteTableModel;
import View.GenericTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T-Gamer
 */
public class ClienteController {

    private static Cliente clienteSelecionado;

    private static JTextField nomeTextField;
    private static JTextField emailTextField;
    private static JTextField telefoneTextField;
    private static JTable clienteTabel;

    public static void SetFields(JTextField nome, JTextField email, JTextField telefone, JTable table){
        nomeTextField = nome;
        emailTextField = email;
        telefoneTextField = telefone;
        clienteTabel = table;
    }

    public static void SetTableModel(GenericTableModel tableModel) {
        clienteTabel.setModel(tableModel);
    }

    public static void ResetTableModel() {
        clienteSelecionado = null;

        nomeTextField.setText("");
        emailTextField.setText("");
        telefoneTextField.setText("");

        clienteTabel.setModel(new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
        AnimalController.ResetTableModel();
    }

    public static Object GetSelecionado() {
        return clienteSelecionado;
    }

    public static void Create() {
        var newCliente = ClienteDAO.getInstance().create("Nome Exemplo", "Av. Exemplo, 000, Exemplo-EX", "00000000", "exemplo@exemplo.com", "00122222222");
        ((GenericTableModel)clienteTabel.getModel()).addItem(newCliente);
    }

    public static void Read() {
        clienteSelecionado = (Cliente) ((GenericTableModel) clienteTabel.getModel()).getItem(clienteTabel.getSelectedRow());

        nomeTextField.setText(clienteSelecionado.getNome());
        emailTextField.setText(clienteSelecionado.getEmail());
        telefoneTextField.setText(clienteSelecionado.getTelefone());

        AnimalController.ResetTextFields();
        AnimalController.SetTableModel();
        TratamentoController.ResetTextFields();
        ConsultaController.ResetTableModel();
    }

    public static void Update() {
        ClienteDAO.getInstance().update(clienteSelecionado);
        ResetTableModel();
    }

    public static void Delete() {
        var animaisDoCliente = AnimalDAO.getInstance().retrieveByIdCliente(clienteSelecionado.getId());
        List<Tratamento> tratamentoDosAnimais = null;
        List<Consulta> consultaDosAnimais = null;

        for (Animal a: animaisDoCliente) {
            try {
                tratamentoDosAnimais.addAll(TratamentoDAO.getInstance().retrieveByAnimalId(a.getId()));
                consultaDosAnimais.addAll(ConsultaDAO.getInstance().retrieveByIdAnimal(a.getId()));
            } catch (NullPointerException npe){
                System.out.println("Animal de id " + a.getId() + " não possui tratamento ou consulta: " + npe.getMessage());
            }
        }

        if (consultaDosAnimais != null) {
            for (Consulta c : consultaDosAnimais) {
                ConsultaDAO.getInstance().delete(c);
            }
        }

        if(tratamentoDosAnimais != null) {
            for (Tratamento trat : tratamentoDosAnimais) {
                TratamentoDAO.getInstance().delete(trat);
            }
        }

        for (Animal a : animaisDoCliente){
            AnimalDAO.getInstance().delete(a);
        }

        ClienteDAO.getInstance().delete(clienteSelecionado);
        ResetTableModel();
    }

    public static void Search(String key, String value) {
        List<Cliente> vDados = new ArrayList<>();

        switch (key){
            case "Nome": vDados = ClienteDAO.getInstance().retrieveBySimilarName(value); break;
            case "Email": vDados = ClienteDAO.getInstance().retrieveByEmail(value); break;
            case "Telefone": vDados = ClienteDAO.getInstance().retrieveByPhone(value); break;
        }

        SetTableModel(new ClienteTableModel(vDados));
    }

}
