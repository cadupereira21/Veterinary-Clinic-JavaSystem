package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manko
 */
public class TratamentoDAO extends DAO {
    private static TratamentoDAO instance;

    // Construtor: Cria a conexão E as tabelas do banco, caso nao estejam criadas
    private TratamentoDAO() {
        getConnection();
        createTable();
    }


    // Singleton - Serve para criar uma instancia de cliente DAO dentro de si mesma, para que classes não abstratas possam ser chamadas
    public static TratamentoDAO getInstance() {
        return (instance==null?(instance = new TratamentoDAO()):instance);
    }

    // CRUD
    public Tratamento create(String nome, String descricao, Calendar dataIni, Calendar dataFim, int id_animal, int terminado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO tratamento (id_animal, nome, descricao, dataIni, dataFim, terminado) VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, id_animal);
            stmt.setString(2, nome);
            stmt.setString(3, descricao);
            stmt.setString(4, Parser.DataToString(dataIni));
            stmt.setString(5, Parser.DataToString(dataFim));
            stmt.setInt(6, terminado);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("tratamento","id"));
    }

    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Tratamento lastTrat = this.retrieveById(lastId("tratamento","id"));
        if (lastTrat != null) {
            return lastTrat.getNome().isBlank();
        }
        return false;
    }

    // Cria uma instancia Cliente com as informações trazidas do banco de dados
    private Tratamento buildObject(ResultSet rs) {
        Tratamento tratamento = null;
        try {
            tratamento = new Tratamento(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getString("dataIni"), rs.getString("dataFim"), rs.getInt("id_animal"), rs.getInt("terminado"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamento;
    }

    // Generic Retriever
    public List<Tratamento> retrieve(String query) {
        List<Tratamento> tratamentos = new ArrayList<Tratamento>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }

    // RetrieveAll
    public List<Tratamento> retrieveAll() {
        return this.retrieve("SELECT * FROM tratamento");
    }

    // RetrieveLast
    public List<Tratamento> retrieveLast(){
        return this.retrieve("SELECT * FROM tratamento WHERE id = " + lastId("tratamento","id"));
    }

    // RetrieveById
    public Tratamento retrieveById(int id) {
        List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamento WHERE id = " + id);
        return (tratamentos.isEmpty()?null:tratamentos.get(0));
    }

    public List<Tratamento> retrieveByAnimalId(int idAnimal) {
        return this.retrieve("SELECT * FROM tratamento WHERE id_animal = " + idAnimal);
    }

    // RetrieveBySimilarName
    public List<Tratamento> retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM tratamento WHERE nome LIKE '%" + nome + "%'");
    }

    public List<Tratamento> retrieveBySimilarName(String nome, int idAnimal) {
        return this.retrieve("SELECT * FROM tratamento WHERE nome LIKE '%" + nome + "%' AND id_animal = " + idAnimal);
    }

    // Updade
    public void update(Tratamento tratamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE tratamento SET nome=?, descricao=?, dataIni=?, dataFim=?, id_animal=?, terminado=? WHERE id=?");
            stmt.setString(1, tratamento.getNome());
            stmt.setString(2, tratamento.getDescricao());
            stmt.setString(3, Parser.DataToString(tratamento.getDtInicio()));
            stmt.setString(4, Parser.DataToString(tratamento.getDtFim()));
            stmt.setInt(5, tratamento.getIdAnimal());
            stmt.setInt(6, tratamento.getTerminou());
            stmt.setInt(7, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Tratamento tratamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
