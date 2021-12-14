/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import View.ExameListModel;
import View.GenericTableModel;
import View.HorariosDisponiveisListModel;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *
 * @author T-Gamer
 */
public class ConsultaController {

    private static Consulta consultaSelecionada;
    private static Veterinario veterinarioSelecionado;

    private static JTextField nomeVetTextField;
    private static JTextField telefoneVetText;
    private static JTextField nomeVetNovaConsultaText;
    private static JTable consultaTable;
    private static JTextField dataTextField;
    private static JTextArea comentariosTextArea;
    private static JList examesList;
    private static JList horariosList;

    public static void SetFields(JTextField nomeVet, JTextField telefoneVet, JTextField dataConsulta, JTextField nomeVetNovaConsulta,JTextArea comentariosConsulta, JTable table, JList exames, JList horarios){
        nomeVetTextField = nomeVet;
        telefoneVetText = telefoneVet;
        dataTextField = dataConsulta;
        nomeVetNovaConsultaText = nomeVetNovaConsulta;
        comentariosTextArea = comentariosConsulta;
        consultaTable = table;
        examesList = exames;
        horariosList = horarios;
    }

    public static void ResetTableModel(){
        SetTableModel(new ConsultaTableModel(new ArrayList()));

        nomeVetTextField.setText("");
        telefoneVetText.setText("");

        veterinarioSelecionado = null;
        consultaSelecionada = null;

        examesList.setModel(new ExameListModel(new ArrayList<>()));
    }

    public static void SetTableModel(){
        SetTableModel(new ConsultaTableModel(ConsultaDAO.getInstance().retrieveByIdAnimal(AnimalController.GetSelecionado().getId())));
    }

    public static void SetTableModel(GenericTableModel tableModel){
        consultaTable.setModel(tableModel);
    }

    public static Consulta GetSelecionado(){
        return consultaSelecionada;
    }

    public static void Read(){
        var consulta = (Consulta) ((GenericTableModel)consultaTable.getModel()).getItem(consultaTable.getSelectedRow());
        Read(consulta);
        TratamentoController.Read(TratamentoDAO.getInstance().retrieveById(consulta.getIdTratamento()));
    }

    public static void Read(Consulta consulta){
        consultaSelecionada = consulta;
        veterinarioSelecionado = VeterinarioDAO.getInstance().retrieveById(consultaSelecionada.getIdVeterinario());
        List<Exame> exames = ExameDAO.getInstance().retrieveByIdConsulta(consultaSelecionada.getId());

        nomeVetTextField.setText(veterinarioSelecionado.getNome());
        telefoneVetText.setText(veterinarioSelecionado.getTelefone());
        dataTextField.setText(Parser.DataToString(consultaSelecionada.getData()));
        comentariosTextArea.setText(consulta.getComentarios());
        examesList.setModel(new ExameListModel(exames));
        ExameController.SetTableModel();

    }

    public static void Create(){
        Map<String, Integer> horario;
        Calendar data = null;
        String comentarios = "";
        int vetId = -1;

        try {
            horario = Parser.ToHour((String) horariosList.getSelectedValue());
            data = Parser.ToCalendar(dataTextField.getText(), horario.get("Hour"), horario.get("Minute"));
            comentarios = comentariosTextArea.getText();
            vetId = VeterinarioDAO.getInstance().retrieveBySimilarName(nomeVetNovaConsultaText.getText()).get(0).getId();
        } catch (NullPointerException e){
            System.out.println(e.getMessage() + " :: Todos os campos devem estar preenchidos!");
            return;
        }

        ConsultaDAO.getInstance().create(data, comentarios, AnimalController.GetSelecionado().getId(), vetId, TratamentoController.GetSelecionado().getId(), 0);

        SetTableModel();
    }

    public static void SetHorariosDisponiveis(String value) {
        List<Consulta> consultas = new ArrayList<>();

        try{
            consultas = ConsultaDAO.getInstance().retrieveByData(value);
        } catch (NullPointerException e){
            System.out.println(e);
        }


        var allHorarios = new ArrayList<String>();

        var s = new String[] {"8:0" , "9:0", "10:0", "11:0", "12:0", "13:0", "14:0", "15:0", "16:0", "17:0"};
        allHorarios.addAll(List.of(s));

        HorariosDisponiveisListModel listModel = new HorariosDisponiveisListModel(allHorarios);

        for (Consulta c: consultas) {
           var aux = c.getData().get(Calendar.HOUR_OF_DAY) + ":" + c.getData().get(Calendar.MINUTE);

           if(allHorarios.contains(aux)){
               listModel.RemoveElement(aux);
           }
        }

        horariosList.setModel(listModel);
    }

    public static void Delete(){
        ConsultaDAO.getInstance().delete(consultaSelecionada);
        SetTableModel();
    }

    public static void Search(String key, String value){

    }
}
