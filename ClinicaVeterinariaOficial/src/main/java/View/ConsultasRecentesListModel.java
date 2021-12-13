package View;

import Model.Consulta;
import Model.TratamentoDAO;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConsultasRecentesListModel implements ListModel {

    private List<String> consultas = new ArrayList<>();

    public ConsultasRecentesListModel(List<Consulta> consultas) {

        if(!consultas.isEmpty()) {
            try {
                for (Consulta c : consultas) {
                    var minutes  = c.getData().get(Calendar.MINUTE) == 0 ? "" : c.getData().get(Calendar.MINUTE);
                    var aux = "[ " + c.getData().get(Calendar.DAY_OF_MONTH) + "/" + c.getData().get(Calendar.MONTH) + "/" + c.getData().get(Calendar.YEAR) + " - "
                            + c.getData().get(Calendar.HOUR_OF_DAY) + "h" + minutes + " ] " + TratamentoDAO.getInstance().retrieveById(c.getIdTratamento()).getNome()
                            + " (" + c.getComentarios() + ")";

                    this.consultas.add(aux);
                }
            } catch (NullPointerException npe) {
                System.out.println(npe.getMessage());
            }
        } else {
            System.out.println("Não foram encontradas Consultas!");
        }
    }

    @Override
    public int getSize() {
        return consultas.size();
    }

    @Override
    public Object getElementAt(int index) {
        return consultas.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
