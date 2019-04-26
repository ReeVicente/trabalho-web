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
            <a class="navbar-brand" href="/LoginJSP/admin/admin.jsp">Minha Bike</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Minhas </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Alugar</a>
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
        <c:if test="${locadora != null}">
            <form action="atualizacao" method="post">
            </c:if>
            <c:if test="${locadora == null}">
                <form action="/LoginJSP/locadora/insercao" method="post">
                </c:if>
                    
                <input type="hidden"
                name="${_csrf.parameterName}"
                value="${_csrf.token}"/> 
                <table border="1" cellpadding="5">
                    <caption>
                        <h2>
                            <c:if test="${locadora != null}">
                                Edição
                            </c:if>
                            <c:if test="${locadora == null}">
                                Cadastro
                            </c:if>
                        </h2>
                    </caption>
                    <c:if test="${locadora != null}">
                        <input type="hidden" name="id" value="<c:out value='${locadora.id}' />" />
                    </c:if>            
                    <tr>
                        <th>Nome: </th>
                        <td>
                            <input type="text" name="nome" size="45" required
                                   value="<c:out value='${locadora.nome}' />"
                                   />
                        </td>
                    </tr>
                    <tr>
                        <th>E-mail</th>
                        <td>
                            <input type="email" name="email" size="45" required
                                   value="<c:out value='${locadora.email}' />"
                                   />
                        </td>
                    </tr>
                    <tr>
                        <th>Senha</th>
                        <td>
                            <input type="password" name="senha" size="45" required
                                   value="<c:out value='${locadora.senha}' />"
                                   />
                        </td>
                    </tr>
                    <tr>
                        <th>Cnpj </th>
                        <td>
                            <input type="number" name="cnpj" size="5" required
                                   min="1500" value="<c:out value='${locadora.cnpj}' />"
                                   />
                        </td>
                    </tr>
                    <tr>
                        <th>Cidade</th>
                        <td>
                            <input type="text" name="cidade" required 
                                   value="<c:out value='${locadora.cidade}' />"
                                   />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="Salva" />
                        </td>
                    </tr>
                </table>
            </form>
    </div>
                <%
                    
                %>
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
