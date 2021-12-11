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
public class VeterinarioDAO extends DAO {
    private static VeterinarioDAO instance;

    // Construtor: Cria a conexão E as tabelas do banco, caso nao estejam criadas
    private VeterinarioDAO() {
        getConnection();
        createTable();
    }

    // Singleton - Serve para criar uma instancia de cliente DAO dentro de si mesma, para que classes não abstratas possam ser chamadas
    public static VeterinarioDAO getInstance() {
        return (instance==null?(instance = new VeterinarioDAO()):instance);
    }

    // CRUD
    public Veterinario create(String nome, String email, String telefone) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO vet (id, nome, email, telefone) VALUES (?, ?,?,?)");
            stmt.setInt(1, IdManager.getIdVeterinario());
            stmt.setString(2, nome);
            stmt.setString(3, email);
            stmt.setString(4, telefone);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("vet","id"));
    }

    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Veterinario lastVet = this.retrieveById(lastId("vet","id"));
        if (lastVet != null) {
            return lastVet.getNome().isBlank();
        }
        return false;
    }

    // Cria uma instancia Cliente com as informações trazidas do banco de dados
    private Veterinario buildObject(ResultSet rs) {
        Veterinario veterinario = null;
        try {
            veterinario = new Veterinario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinario;
    }

    // Generic Retriever
    public List<Veterinario> retrieve(String query) {
        List<Veterinario> vets = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                vets.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return vets;
    }

    // RetrieveAll
    public List<Veterinario> retrieveAll() {
        return this.retrieve("SELECT * FROM vet");
    }

    // RetrieveLast
    public List<Veterinario> retrieveLast(){
        return this.retrieve("SELECT * FROM vet WHERE id = " + lastId("vet","id"));
    }

    // RetrieveById
    public Veterinario retrieveById(int id) {
        List<Veterinario> vets = this.retrieve("SELECT * FROM vet WHERE id = " + id);
        return (vets.isEmpty()?null:vets.get(0));
    }

    // RetrieveBySimilarName
    public List<Veterinario> retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM vet WHERE nome LIKE '%" + nome + "%'");
    }

    public List<Veterinario> retrieveBySimilarEmail(String email) {
        return this.retrieve("SELECT * FROM vet WHERE email LIKE '%" + email + "%'");
    }

    public List<Veterinario> retrieveBySimilarTelefone(String tele) {
        return this.retrieve("SELECT * FROM vet WHERE telefone LIKE '%" + tele + "%'");
    }

    // Updade
    public void update(Veterinario vet) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE vet SET nome=?, email=?, telefone=? WHERE id=?");
            stmt.setString(1, vet.getNome());
            stmt.setString(2, vet.getEmail());
            stmt.setString(3, vet.getTelefone());
            stmt.setInt(4, vet.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Veterinario vet) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM vet WHERE id = ?");
            stmt.setInt(1, vet.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM vet WHERE id =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
