<%@page import="java.util.List"%>
<%@page import="model.Locadora"%>
<%@page import="dao.LocadoraDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Locadora - Admin</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>
    <body>
            <%@page import="java.sql.DriverManager"%>
            <%@page import="java.sql.ResultSet"%>
            <%@page import="java.sql.PreparedStatement"%>
            <%@page import="java.sql.Connection"%>
            <%@page import="br.ufscar.dc.dsw.JDBCUtil"%>
            <%@page import="javax.sql.DataSource"%>
            
            <% 
            ResultSet resultSet = null;

            DataSource ds = JDBCUtil.getDataSource();

            Connection conn = ds.getConnection();

            String userSql = "SELECT * from Usuario where email = ?";

            // Criando Usuario admin com papel ROLE_ADMIN
            
            PreparedStatement userStatement = conn.prepareStatement(userSql);
            userStatement.setString(1, request.getUserPrincipal().getName().toString());
            resultSet = userStatement.executeQuery();
            resultSet.next();
            
            String nome = resultSet.getString("nome");
            resultSet.close();
            userStatement.close();
            conn.close();
            
            
            %>
            
        
   

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="/LoginJSP/admin/admin.jsp">Menu iniciar</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/LoginJSP/admin/listaLocadoras.jsp">Locadoras</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="/LoginJSP/admin/listaUser.jsp">Usuários</a>
                    </li>
                </ul>
                <span>Olá <%= nome %></span>
                <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="/LoginJSP/logout">Sair</a>
                    </li>
                </ul>

            </div>
        </nav>
        <div class="jumbotron">
            <div class="container">
                <h1>Adicionar Locadora</h1>
            </div>
        </div>
        <div align="center">
      
            <form id="form-insert" action="/LoginJSP/locadora/insercao" method="post">
                    
                <input type="hidden"
                name="${_csrf.parameterName}"
                value="${_csrf.token}"/> 
                <table border="1" cellpadding="5">
           
                    <tr>
                        <th>Nome: </th>
                        <td>
                            <input type="text" name="nome" required class="form-control" placeholder="Nome"/>
                        </td>
                    </tr>
                    <tr>
                        <th>E-mail</th>
                        <td>
                            <input type="email" name="email" class="form-control" required placeholder="E-mail">
                        </td>
                    </tr>
                    <tr>
                        <th>Senha</th>
                        <td>
                            <input type="password" name="senha" class="form-control" required placeholder="Senha" />
                        </td>
                    </tr>
                    <tr>
                        <th>CNPJ </th>
                        <td>
                            <input type="text" class="form-control cnpj" name="cnpj"required placeholder="CNPJ" />
                        </td>
                    </tr>
                    <tr>
                        <th>Cidade</th>
                        <td>
                            <input type="text" class="form-control" name="cidade" placeholder="Cidade" required />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <button class="btn btn-primary" type="submit" value="Salva">Adicionar</button>
                        </td>
                    </tr>
                </table>
            </form>
    </div>
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
       
        <script> 
            $('.cnpj').mask('00.000.000/0000-00', {reverse: true});
        </script>
    </body>
</html>
