package View;

import Model.Animal;
import Model.Especie;
import Model.EspecieDAO;

import java.util.List;

public class AnimalTableModel extends GenericTableModel{

    public AnimalTableModel(List vDados) {
        super(vDados, new String[]{"Nome", "Ano de Nascimento", "Sexo", "Especie"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex){
            case 0:
            case 2:
                case 3:
                return String.class;
            case 1:
                return Integer.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                return animal.getNome();
            case 1:
                return animal.getAnoNasc();
            case 2:
                return animal.getSexo();
            case 3:
                var especie = EspecieDAO.getInstance().retrieveById(animal.getIdEspecie());
                return especie.getNome();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        Animal animal = (Animal) vDados.get(rowIndex);

        switch (columnIndex){
            case 0:
                animal.setNome((String) aValue);
                break;
            case 1:
                animal.setAnoNasc((Integer) aValue);
                break;
            case 2:
                animal.setSexo((String) aValue);
                break;
            case 3:
                var exists = EspecieDAO.getInstance().retrieveBySimilarName((String)aValue);
                Especie especie = EspecieDAO.getInstance().retrieveById(animal.getIdEspecie());
                
                if(exists.isEmpty()){
                    EspecieDAO.getInstance().create((String) aValue); 
                    especie = (Especie) EspecieDAO.getInstance().retrieveLast();
                }
                else
                    especie = (Especie) exists.get(0);
                
                animal.setIdEspecie(especie.getId());
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
}
