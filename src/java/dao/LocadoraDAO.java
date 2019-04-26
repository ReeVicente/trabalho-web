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
            
            userSql = "Insert into Locadora (cnpj, cidade, email) values (?, ?, ?)";
            
            userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, locadora.getCnpj());
            userStatement.setString(2, locadora.getCidade());
            userStatement.setString(3, locadora.getEmail());
            userStatement.execute();
            PreparedStatement roleStatement = conn.prepareStatement(roleSql);
            roleStatement.setString(1, locadora.getEmail());
            roleStatement.setString(2, "ROLE_LOCADORA");
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

        String sql = "SELECT Locadora.*, Usuario.* FROM Locadora JOIN Usuario on Usuario.email = Locadora.email";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cnpj = resultSet.getString("cnpj");
                String cidade = resultSet.getString("cidade");
                Locadora locadora = new Locadora(id, nome, email, cnpj, cidade);
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
        Locadora loc = this.get(locadora.getId());
        
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
        
        sql = "DELETE FROM Usuario where email = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, loc.getEmail());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       
    }

    public void update(Locadora locadora) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Locadora loc = this.get(locadora.getId());
        
        try {
            String sql = "UPDATE Locadora SET cnpj = ?, cidade = ? WHERE id = ?";
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

    
            statement.setString(1, locadora.getCnpj());
            statement.setString(2, locadora.getCidade());
            statement.setInt(3, loc.getId());
            statement.execute();
            
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!locadora.getSenha().isEmpty()) {
            String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ? WHERE email = ?";
            try {
                Connection conn = this.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, locadora.getNome());
                statement.setString(2, locadora.getEmail());
                statement.setString(3, encoder.encode(locadora.getSenha()));
                statement.setString(4, loc.getEmail());
                statement.execute();
            
                statement.close();
                conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        } else {
             String sql = "UPDATE Usuario SET nome = ?, email = ? WHERE email = ?";
            try {
                Connection conn = this.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, locadora.getNome());
                statement.setString(2, locadora.getEmail());
                statement.setString(3, loc.getEmail());
                
                statement.execute();
            
                statement.close();
                conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        
        
    }

    public Locadora get(int id) {
        Locadora locadora = null;
        String sql = "SELECT Locadora.*, Usuario.* FROM Locadora JOIN Usuario on Usuario.email = Locadora.email WHERE Locadora.id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String cnpj = resultSet.getString("cnpj");
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
