package View;

import Model.Animal;
import Model.Veterinario;

import java.util.List;

public class VetTableModel extends GenericTableModel {

    public VetTableModel(List vDados) {
        super(vDados, new String[]{"Nome", "Email", "Telefone"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex){
            case 0:
            case 1:
            case 2:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veterinario vet = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                return vet.getNome();
            case 1:
                return vet.getEmail();
            case 2:
                return vet.getTelefone();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        Veterinario vet = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                vet.setNome((String) aValue);
                break;
            case 1:
                vet.setEmail((String) aValue);
                break;
            case 2:
                vet.setTelefone((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        // Apenas o nome do veterinario nao pode ser modificado
        if (columnIndex == 0)
            return false;
        else
            return true;
    }
}
