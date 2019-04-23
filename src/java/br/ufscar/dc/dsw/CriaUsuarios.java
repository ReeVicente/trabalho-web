package br.ufscar.dc.dsw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriaUsuarios {

    public static void main(String[] args) throws ClassNotFoundException {

        try {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            DataSource ds = JDBCUtil.getDataSource();

            Connection conn = ds.getConnection();

            String userSql = "Insert into Usuario (email, nome, senha, ativo) "
                    + "values (?,?, ?, ?)";

            String roleSql = "Insert into Papel (email, nome)"
                    + "values (?,?)";

            // Criando Usuario admin com papel ROLE_ADMIN
            
            PreparedStatement userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, "admin@admin");
            userStatement.setString(2, "administrador");
            userStatement.setString(3, encoder.encode("admin"));
            userStatement.setBoolean(4, true);
            userStatement.execute();

            PreparedStatement roleStatement = conn.prepareStatement(roleSql);
            roleStatement.setString(1, "admin@admin");
            roleStatement.setString(2, "ROLE_ADMIN");
            roleStatement.execute();

            // Criando Usuario user com papel ROLE_USER
            userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, "user@user");
            userStatement.setString(2, "usuario");
            userStatement.setString(3, encoder.encode("user"));
            userStatement.setBoolean(4, true);
            userStatement.execute();

            roleStatement = conn.prepareStatement(roleSql);
            roleStatement.setString(1, "user@user");
            roleStatement.setString(2, "ROLE_USER");
            roleStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
