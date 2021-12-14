package View;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorariosDisponiveisListModel implements ListModel {

    private List<String> horarios = new ArrayList<>();

    public HorariosDisponiveisListModel(List<String> horarios) {
        this.horarios.addAll(horarios);
    }

    public void RemoveElement(String horario){
        horarios.remove(horario);
    }

    @Override
    public int getSize() {
        return horarios.size();
    }

    @Override
    public Object getElementAt(int index) {
        return horarios.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
