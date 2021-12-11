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
public class ExameDAO extends DAO {
    private static ExameDAO instance;

    // Construtor: Cria a conexão E as tabelas do banco, caso nao estejam criadas
    private ExameDAO() {
        getConnection();
        createTable();
    }

    // Singleton - Serve para criar uma instancia de cliente DAO dentro de si mesma, para que classes não abstratas possam ser chamadas
    public static ExameDAO getInstance() {
        return (instance==null?(instance = new ExameDAO()):instance);
    }

    // CRUD
    public Exame create(String nome, int id_consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO exame (id, nome, id_consulta) VALUES (?,?,?)");
            stmt.setInt(1, IdManager.getIdExame());
            stmt.setString(2, nome);
            stmt.setInt(3, id_consulta);

            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("exame","id"));
    }

    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Exame lastExame = this.retrieveById(lastId("exame","id"));
        if (lastExame != null) {
            return lastExame.getNome().isBlank();
        }
        return false;
    }

    // Cria uma instancia Cliente com as informações trazidas do banco de dados
    private Exame buildObject(ResultSet rs) {
        Exame exame = null;
        try {
            exame = new Exame(rs.getInt("id"), rs.getString("nome"), rs.getInt("id_consulta"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exame;
    }

    // Generic Retriever
    public List<Exame> retrieve(String query) {
        List<Exame> exames = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                exames.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exames;
    }

    // RetrieveAll
    public List<Exame> retrieveAll() {
        return this.retrieve("SELECT * FROM exame");
    }

    // RetrieveLast
    public List<Exame> retrieveLast(){
        return this.retrieve("SELECT * FROM exame WHERE id = " + lastId("exame","id"));
    }

    // RetrieveById
    public Exame retrieveById(int id) {
        List<Exame> exames = this.retrieve("SELECT * FROM exame WHERE id = " + id);
        return (exames.isEmpty()?null:exames.get(0));
    }

    public List<Exame> retrieveByIdConsulta(int id) {
        return this.retrieve("SELECT * FROM exame WHERE id_consulta = " + id);
    }

    // RetrieveBySimilarName
    public List<Exame> retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM exame WHERE nome LIKE '%" + nome + "%'");
    }

    // Update
    public void update(Exame exame) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE cliente SET nome=?, id_consulta=? WHERE id=?");
            stmt.setString(1, exame.getNome());
            stmt.setInt(2, exame.getIdConsulta());
            stmt.setInt(3, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Exame exame) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exame WHERE id = ?");
            stmt.setInt(1, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exame WHERE id =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
