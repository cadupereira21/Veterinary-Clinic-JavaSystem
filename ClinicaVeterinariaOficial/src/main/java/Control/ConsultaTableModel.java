package Control;

import Model.Cliente;
import Model.Consulta;
import Model.Parser;
import Model.VeterinarioDAO;
import View.GenericTableModel;

import java.util.Calendar;
import java.util.List;

public class ConsultaTableModel extends GenericTableModel {


    public ConsultaTableModel(List vDados) {
        super(vDados, new String[]{"Data", "Horário", "Comentário", "Veterinário"});
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
        Consulta consulta = (Consulta) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                return Parser.DataToString(consulta.getData());
            case 1:
                return Parser.HorarioToString(consulta.getData());
            case 2:
                return consulta.getComentarios();
            case 3:
                return VeterinarioDAO.getInstance().retrieveById(consulta.getIdVeterinario()).getNome();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        Consulta consulta = (Consulta) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
            case 1:
                consulta.setData(Parser.ToCalendar((String)aValue));
                break;
            case 2:
                consulta.setComentarios((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        if(columnIndex != 3){
            return true;
        }
        return false;
    }
}
