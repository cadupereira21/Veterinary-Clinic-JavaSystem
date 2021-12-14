package View;

import Model.Exame;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ExameListModel implements ListModel {

    private List<Exame> exames = new ArrayList();

    public ExameListModel(List<Exame> exames) {
        this.exames = exames;
    }

    @Override
    public int getSize() {
        return exames.size();
    }

    @Override
    public Object getElementAt(int index) {
        return exames.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
