package View;

import Model.Animal;
import Model.AnimalDAO;
import Model.Parser;
import Model.Tratamento;

import java.util.List;

public class TratamentoTableModel extends GenericTableModel{

    public TratamentoTableModel(List vDados) {
        super(vDados, new String[]{"Nome do Animal", "Nome do Tratamento", "Data de Início", "Data de Encerramento"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex){
            case 0:
            case 1:
            case 2:
            case 3:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tratamento trat = (Tratamento) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                Animal animal = (Animal) AnimalDAO.getInstance().retrieveById(trat.getIdAnimal());
                return animal.getNome();
            case 1:
                return trat.getNome();
            case 2:
                return Parser.DataToString(trat.getDtInicio());
            case 3:
                return Parser.DataToString(trat.getDtFim());
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        Tratamento trat = (Tratamento) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                Animal animal = AnimalDAO.getInstance().retrieveById(trat.getIdAnimal());
                animal.setNome((String) aValue);
                break;
            case 1:
                trat.setNome((String) aValue);
                break;
            case 2:
                trat.setDtInicio(Parser.ToCalendar((String) aValue));
                break;
            case 3:
                trat.setDtFim(Parser.ToCalendar((String) aValue));
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        // Nada do tratamento pode ser modificado
        return false;
    }
}
