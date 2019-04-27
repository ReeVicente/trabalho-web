package dao;

import model.Usuario;
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

public class UserDAO {

    public UserDAO() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Login", "root", "root");
    }

    public void insert(Usuario cliente)  throws ClassNotFoundException   {

        try {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            DataSource ds = JDBCUtil.getDataSource();

            Connection conn = ds.getConnection();

            String userSql = "Insert into Usuario (nome, email, senha, ativo) values (?,?, ?, ?)";

            String roleSql = "Insert into Papel (email, nome) values (?,?)";

            
            PreparedStatement userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, cliente.getNome());
            userStatement.setString(2, cliente.getEmail());
            userStatement.setString(3, encoder.encode(cliente.getSenha()));
            userStatement.setBoolean(4, true);
            userStatement.execute();
            
            userSql = "Insert into Cliente (cpf, telefone, sexo, datadenascimento) values (?, ?, ?, ?)";
            
            userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, cliente.getCpf());
            userStatement.setString(2, cliente.getTelefone());
            userStatement.setString(3, cliente.getSexo());
            userStatement.setString(4, cliente.getDatadenascimento());
            userStatement.execute();
            
            PreparedStatement roleStatement = conn.prepareStatement(roleSql);
            roleStatement.setString(1, cliente.getEmail());
            roleStatement.setString(2, "ROLE_USER");
            roleStatement.execute();
            roleStatement.close();
            userStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }

    public List<Usuario> getAll() {

        List<Usuario> listaUsuarios = new ArrayList<>();

        String sql = "SELECT Cliente.*, Usuario.* FROM Cliente JOIN Usuario on Usuario.email = Cliente.email";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String cpf = resultSet.getString("cpf");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String datadenascimento = resultSet.getString("datadenascimento");
                Usuario user = new Usuario(id, nome, email, cpf, telefone, sexo, datadenascimento);
                listaUsuarios.add(user);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaUsuarios;
    }

    public void delete(Usuario user) {
        Usuario usu = this.get(user.getId());
        
        String sql = "DELETE FROM Cliente where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, user.getId());
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

            statement.setString(1, usu.getEmail());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       
    }

    public void update(Usuario user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Usuario usu = this.get(user.getId());
        
        try {
            String sql = "UPDATE Cliente SET cpf = ?, telefone = ?, sexo = ?, datadenascimento = ? WHERE id = ?";
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

    
            statement.setInt(1, usu.getId());
            statement.execute();
            
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!user.getSenha().isEmpty()) {
            String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ? WHERE email = ?";
            try {
                Connection conn = this.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, user.getNome());
                statement.setString(2, user.getEmail());
                statement.setString(3, encoder.encode(user.getSenha()));
                statement.setString(4, usu.getEmail());
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
                statement.setString(1, user.getNome());
                statement.setString(2, user.getEmail());
                statement.setString(3, usu.getEmail());
                
                statement.execute();
            
                statement.close();
                conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        
        
    }

    public Usuario get(int id) {
        Usuario user = null;
        String sql = "SELECT Cliente.*, Usuario.* FROM Cliente JOIN Usuario on Usuario.email = Cliente.email";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String cpf = resultSet.getString("cpf");
                String telefone = resultSet.getString("telefone");
                String sexo = resultSet.getString("sexo");
                String datadenascimento = resultSet.getString("datadenascimento");
                user = new Usuario(id, nome, email, senha, cpf, telefone, sexo, datadenascimento);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
