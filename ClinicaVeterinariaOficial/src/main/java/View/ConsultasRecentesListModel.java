package View;

import Model.Consulta;
import Model.TratamentoDAO;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConsultasRecentesListModel implements ListModel {

    private List<Consulta> consultas = new ArrayList<>();

    public ConsultasRecentesListModel(List<Consulta> consultasString) {

        if(!consultasString.isEmpty()) {
            try {
                for (Consulta c : consultasString) {
                    var minutes  = c.getData().get(Calendar.MINUTE) == 0 ? "" : c.getData().get(Calendar.MINUTE);
                    consultas.add(c);
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
