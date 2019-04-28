
<%@page import="java.util.List"%>
<%@page import="model.Locadora"%>
<%@page import="dao.LocadoraDAO"%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Locadora - Admin</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <sec:authorize access="hasRole('ADMIN')">
  <% response.sendRedirect("admin/admin.jsp"); %>
</sec:authorize>
<sec:authorize access="hasRole('USER')">
  <% response.sendRedirect("user/user.jsp"); %>
</sec:authorize>
<sec:authorize access="hasRole('CLIENTE')">
  <% response.sendRedirect("user/user.jsp"); %>
</sec:authorize>
        
    </head>
    <body>
            <%@page import="java.sql.DriverManager"%>
            <%@page import="java.sql.ResultSet"%>
            <%@page import="java.sql.PreparedStatement"%>
            <%@page import="java.sql.Connection"%>
            <%@page import="br.ufscar.dc.dsw.JDBCUtil"%>
            <%@page import="javax.sql.DataSource"%>
            
            <% 
            LocadoraDAO dao;
            dao = new LocadoraDAO();

            List<Locadora> listaLocadoras;
            if(!request.getParameterMap().containsKey("city")) {
                listaLocadoras = dao.getAll();
            } else {
                listaLocadoras = dao.searchByCity(request.getParameter("city"));
            }
            %>
            
        
   

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand " href="/LoginJSP">Menu iniciar</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/LoginJSP/login">Entrar</a>
                    </li>
                </ul>

            </div>
        </nav>
        <div class="jumbotron">
            <div class="container">
                <h1>Locadoras</h1>
            </div>
        </div>
        <main class="container">
            <div>
                <form method="GET" action="/LoginJSP">
                    <div class="row my-3">
                        <div class="col col-5">
                            <input class="form-control" name="city" placeholder="Cidade">
                        </div>
                        <div class="col col-3">
                            <button class="btn btn-primary">Buscar</button>
                        </div>

                    </div>
                </form>
                <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col">Locadora</th>
                        <th scope="col">CNPJ</th>
                        <th scope="col">Cidade</th>
                    </tr>
                </thead>
                <tbody>
                       <% 
                        for(int i = 0; i < listaLocadoras.size(); i++) {
                       %>
                        <tr>
                            <td><%= listaLocadoras.get(i).getNome() %></td>
                            <td><%= listaLocadoras.get(i).getCnpj()%></td>
                            <td><%= listaLocadoras.get(i).getCidade()%></td>
                        </tr>
                        <% } %>
                    </c:forEach>

                </tbody>
            </table>
            </div>
        </main>
                <%
                    
                %>
        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    </body>
</html>
