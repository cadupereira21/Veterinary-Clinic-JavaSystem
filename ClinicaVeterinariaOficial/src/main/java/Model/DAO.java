package Model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    public static final String DBURL = "jdbc:sqlite:vet2021.db"; //O Meu DB
    private static Connection con; // Variável que armazenará a conexão com o DB
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // Conecta com o DB e retorna essa conexão
    public static Connection getConnection() {
        if (con == null) { //se nao houver conexao
            try {
                con = DriverManager.getConnection(DBURL);
                if (con != null) {
                    DatabaseMetaData meta = con.getMetaData();
                }
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        return con;
    }

    // Faz uma consulta no BD e retorna o resultado dessa consulta, ou seja, uma tabela
    protected ResultSet getResultSet(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = (Statement) con.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return rs;
    }

    // Tenta fazer um update, caso o resultado seja nada, retorna 0
    protected int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        int update;
        update = queryStatement.executeUpdate();
        return update;
    }

    // retorna o último id presente em uma tabela
    protected int lastId(String tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    // Termina a conexão com o banco
    public static void terminar() {
        try {
            (DAO.getConnection()).close();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Create table SQLite
    protected final boolean createTable() {
        try {
            PreparedStatement stmt;
            // Table client:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS cliente( \n"
                    + "id SMALLINT PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "endereco VARCHAR, \n"
                    + "cep CHAR(11), \n"
                    + "email VARCHAR, \n"
                    + "telefone CHAR(11)); \n");
            executeUpdate(stmt);
            // Table species:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS especie( \n"
                    + "id SMALLINT PRIMARY KEY, \n"
                    + "nome VARCHAR); \n");
            //+ "INSERT INTO especie (id, nome) VALUES (1, cachorro);");
            executeUpdate(stmt);
            // Table animal:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS animal( \n"
                    + "id SMALLINT PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "anoNasc SMALLINT, \n"
                    + "sexo VARCHAR, \n"
                    + "id_especie INTEGER, \n"
                    + "id_cliente INTEGER, \n" +
                    "FOREIGN KEY (id_especie) REFERENCES especie," +
                    "FOREIGN KEY (id_cliente) REFERENCES cliente); \n");
            executeUpdate(stmt);
            // Table vet:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS vet( \n"
                    + "id SMALLINT PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "email VARCHAR, \n"
                    + "telefone VARCHAR); \n");
            executeUpdate(stmt);
            // Table treatment:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tratamento( \n"
                    + "id SMALLINT PRIMARY KEY, \n"
                    + "id_animal SMALLINT, \n"
                    + "nome VARCHAR, \n"
                    + "dataIni DATE, \n"
                    + "dataFim DATE, \n"
                    + "terminado BIT," +
                    "FOREIGN KEY (id_animal) REFERENCES animal); \n");
            executeUpdate(stmt);
            // Table appointment:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS consulta( \n"
                    + "id SMALLINT PRIMARY KEY, \n"
                    + "dataConsulta DATE, \n"
                    + "horario TIME, \n"
                    + "comentario VARCHAR, \n"
                    + "id_animal SMALLINT, \n"
                    + "id_vet SMALLINT, \n"
                    + "id_tratamento SMALLINT, \n"
                    + "terminado BIT," +
                    "FOREIGN KEY (id_animal) REFERENCES animal," +
                    "FOREIGN KEY (id_vet) REFERENCES vet," +
                    "FOREIGN KEY (id_tratamento) REFERENCES tratamento); \n");
            executeUpdate(stmt);
            // Table exam:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS exame( \n"
                    + "id SMALLINT PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "id_consulta SMALLINT," +
                    "FOREIGN KEY (id_consulta) REFERENCES consulta); \n");
            executeUpdate(stmt);
            // Default element for species:
            stmt = DAO.getConnection().prepareStatement("INSERT OR IGNORE INTO especie (id, nome) VALUES (0, 'Cachorro')");
            executeUpdate(stmt);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
