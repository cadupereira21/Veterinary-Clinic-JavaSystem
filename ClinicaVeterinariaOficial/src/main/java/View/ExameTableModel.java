package View;

import Model.Cliente;
import Model.ConsultaDAO;
import Model.Exame;

import java.util.List;

public class ExameTableModel extends GenericTableModel{
    public ExameTableModel(List vDados) {
        super(vDados, new String[]{"Nome", "Consulta"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex){
            case 0:
            case 1:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Exame exame = (Exame) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                return exame.getNome();
            case 1:
                return ConsultaDAO.getInstance().retrieveById(exame.getIdConsulta());
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        Exame exame = (Exame) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                exame.setNome((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        if(columnIndex == 0){
            return true;
        }
        return false;
    }
}
