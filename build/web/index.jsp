<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <body>
        <h2>Bemvindo
            <%=request.getUserPrincipal().getName().toString()%>
        </h2>

        <sec:authorize access="hasRole('ADMIN')">

            Este conteúdo só será visível para usuários que desempenhem 
            o papel "ADMIN" <br/><br/>

            <a href="admin/admin.jsp">Área de Administrador</a>
        </sec:authorize>

        <sec:authorize access="hasRole('USER')">
            Este conteúdo só será visível para usuários que desempenhem 
            o papel "USER" <br/><br/>

            <a href="user/user.jsp">Área de Usuário</a>
        </sec:authorize>
    </body>
</html>