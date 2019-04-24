package dao;

import model.Locadora;
import br.ufscar.dc.dsw.JDBCUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LocadoraDAO {

    public LocadoraDAO() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Login", "root", "root");
    }

    public void insert(Locadora locadora)  throws ClassNotFoundException   {

        try {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
             DataSource ds = JDBCUtil.getDataSource();

            Connection conn = ds.getConnection();

              String userSql = "Insert into Usuario (nome, email, senha, ativo) values (?,?, ?, ?)";

            String roleSql = "Insert into Papel (email, nome) values (?,?)";

            
            PreparedStatement userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, locadora.getNome());
            userStatement.setString(2, locadora.getEmail());
            userStatement.setString(3, encoder.encode(locadora.getSenha()));
            userStatement.setBoolean(4, true);
            userStatement.execute();
            
            userSql = "Insert into Locadora (cnpj, cidade) values (?, ?)";
            
            userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, locadora.getCnpj());
            userStatement.setString(2, locadora.getCidade());
            userStatement.execute();
            PreparedStatement roleStatement = conn.prepareStatement(roleSql);
            roleStatement.setString(1, "user@user");
            roleStatement.setString(2, "ROLE_USER");
            roleStatement.execute();
            roleStatement.close();
            userStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }

    public List<Locadora> getAll() {

        List<Locadora> listaLocadoras = new ArrayList<>();

        String sql = "SELECT * FROM Locadora";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                int cnpj = resultSet.getInt("cnpj");
                String cidade = resultSet.getString("cidade");
                Locadora locadora = new Locadora(id, nome, email, senha, cnpj, cidade);
                listaLocadoras.add(locadora);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLocadoras;
    }

    public void delete(Locadora locadora) {
        String sql = "DELETE FROM Locadora where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, locadora.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Locadora locadora) {
        String sql = "UPDATE Locadora SET nome = ?, email = ?, senha = ?, cnpj = ?, cidade = ?";
        sql += " WHERE id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, locadora.getNome());
            statement.setString(2, locadora.getEmail());
            statement.setString(3, locadora.getSenha());
            statement.setString(4, locadora.getCnpj());
            statement.setString(5, locadora.getCidade());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Locadora get(int id) {
        Locadora locadora = null;
        String sql = "SELECT * FROM Locadora WHERE id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                int cnpj = resultSet.getInt("cnpj");
                String cidade = resultSet.getString("cidade");
                locadora = new Locadora(id, nome, email, senha, cnpj, cidade);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locadora;
    }
}
