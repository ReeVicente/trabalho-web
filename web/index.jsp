<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
        <body>
            <h2>Bemvindo
            <%=request.getUserPrincipal().getName().toString()%>
        </h2>

        <sec:authorize access="hasRole('ADMIN')">
            <% response.sendRedirect("admin/admin.jsp"); %>
        </sec:authorize>

        <sec:authorize access="hasRole('USER')">
            <% response.sendRedirect("user/user.jsp"); %>
        </sec:authorize>
         <sec:authorize access="hasRole('CLIENTE')">
            <% response.sendRedirect("user/user.jsp"); %>
        </sec:authorize>
    </body>
</html>