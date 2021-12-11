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
 * @author T-Gamer
 */
public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;

    // Construtor: Cria a conexão E as tabelas do banco, caso nao estejam criadas
    private ConsultaDAO() {
        getConnection();
        createTable();
    }

    // Singleton - Serve para criar uma instancia de cliente DAO dentro de si mesma, para que classes não abstratas possam ser chamadas
    public static ConsultaDAO getInstance() {
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }

    // CRUD
    public Consulta create(Calendar data, String comentario, int id_animal, int id_vet, int id_tratamento, int terminado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (id, dataConsulta, horario, comentario, id_animal, id_vet, id_tratamento, terminado) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setInt(1, IdManager.getIdConsulta());
            stmt.setString(2, Parser.DataToString(data));
            stmt.setString(3, Parser.HorarioToString(data));
            stmt.setString(4, comentario);
            stmt.setInt(5, id_animal);
            stmt.setInt(6, id_vet);
            stmt.setInt(7, id_tratamento);
            stmt.setInt(8, terminado);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consulta","id"));
    }

    // Uma pequena gambiarra, depois explico...
    public boolean isLastEmpty(){
        Consulta lastConsulta = this.retrieveById(lastId("consulta","id"));
        if (lastConsulta != null) {
            return lastConsulta.getData() != null;
        }
        return false;
    }

    // Cria uma instancia Cliente com as informações trazidas do banco de dados
    private Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {
            consulta = new Consulta(rs.getInt("id"), rs.getString("dataConsulta"), rs.getString("horario"), rs.getString("comentario"), rs.getInt("id_animal"), rs.getInt("id_vet"), rs.getInt("id_tratamento"), rs.getInt("terminado"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }

    // Generic Retriever
    public List<Consulta> retrieve(String query) {
        List<Consulta> consultas = new ArrayList<Consulta>();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }

    // RetrieveAll
    public List<Consulta> retrieveAll() {
        return this.retrieve("SELECT * FROM consulta");
    }

    // RetrieveLast
    public List<Consulta> retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta","id"));
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }

    // RetrieveByIdAnimal
    public List<Consulta> retrieveByIdAnimal(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id_animal = " + id);
        return consultas;
    }

    // RetrieveByIdVet
    public List<Consulta> retrieveByIdVet(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id_vet = " + id);
        return consultas;
    }

    // RetrieveByIdTratamento
    public List<Consulta> retrieveByIdTratamento(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id_tratamento = " + id);
        return consultas;
    }

    public List<Consulta> retrieveByDia(String dia) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE dataConsulta = " + dia);
        return consultas;
    }

    // Update
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE cliente SET dataConsulta=?, horario=?, comentario=?, id_animal=?, id_vet=?, id_tratamento=?, terminado=? WHERE id=?");
            stmt.setString(1, Parser.DataToString(consulta.getData()));
            stmt.setString(2, Parser.HorarioToString(consulta.getData()));
            stmt.setString(3, consulta.getComentarios());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdVeterinario());
            stmt.setInt(6, consulta.getIdTratamento());
            stmt.setInt(7, consulta.getTerminou());
            stmt.setInt(8, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteByIdAnimal(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id_animal =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteByIdVet(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id_vet =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteByIdTratamento(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id_tratamento =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
