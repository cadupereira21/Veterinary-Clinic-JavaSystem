package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manko
 */
public class EspecieDAO extends DAO {
    private static EspecieDAO instance;

    // Construtor: Cria a conexão E as tabelas do banco, caso nao estejam criadas
    private EspecieDAO() {
        getConnection();
        createTable();
    }

    // Singleton - Serve para criar uma instancia de animal DAO dentro de si mesma, para que classes não abstratas possam ser chamadas
    public static EspecieDAO getInstance() {
        return (instance==null?(instance = new EspecieDAO()):instance);
    }

    // CRUD
    public Especie create(String nome) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO especie (id, nome) VALUES (?,?)");
            stmt.setInt(1, IdManager.getIdEspecie());
            stmt.setString(2, nome);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(EspecieDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("especie","id"));
    }

    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Especie lastEspecie = this.retrieveById(lastId("especie","id"));
        if (lastEspecie != null) {
            return lastEspecie.getNome().isBlank();
        }
        return false;
    }

    // Cria uma instancia Cliente com as informações trazidas do banco de dados
    private Especie buildObject(ResultSet rs) {
        Especie especie = null;
        try {
            especie = new Especie(rs.getInt("id"), rs.getString("nome"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return especie;
    }

    // Generic Retriever
    public List<Especie> retrieve(String query) {
        List<Especie> especies = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                especies.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return especies;
    }

    // RetrieveAll
    public List<Especie> retrieveAll() {
        return this.retrieve("SELECT * FROM especie");
    }

    // RetrieveLast
    public Especie retrieveLast(){
        List<Especie> lista = this.retrieve("SELECT * FROM especie WHERE id = " + lastId("especie","id"));
        Especie especie = lista.get(0);
        return especie;
    }

    // RetrieveById
    public Especie retrieveById(int id) {
        List<Especie> especies = this.retrieve("SELECT * FROM especie WHERE id = " + id);
        return (especies.isEmpty()?null:especies.get(0));
    }

    // RetrieveBySimilarName
    public List<Especie> retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM especie WHERE nome LIKE '%" + nome + "%'");
    }

    // Update
    public void update(Especie especie) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE especie SET nome=?");
            stmt.setString(1, especie.getNome());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Especie especie) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM especie WHERE id = ?");
            stmt.setInt(1, especie.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM especie WHERE id =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
