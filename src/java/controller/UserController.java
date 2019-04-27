package controller;


import model.Usuario;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/cliente/*")
public class UserController extends HttpServlet {

    private UserDAO dao;

    @Override
    public void init() {
        dao = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getPathInfo();
        
        try (PrintWriter out = response.getWriter()) {
            try {   
                switch (action) {
                    case "/insercao":
                        out.println(request.getParameter("email"));
                        insere(request, response);
                        break;
                    case "/atualizacao":
                        atualize(request, response);
                        break;
                    default:
                        lista(request, response);
                        break;
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LocadoraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        try (PrintWriter out = response.getWriter()) {
            try {   
                switch (action) {
                    case "/remocao":
                        remove(request, response);
                        break;
                    default:
                        lista(request, response);
                        break;
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    private void lista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Usuario> listaUser = dao.getAll();
        try (PrintWriter out = response.getWriter()) {
           out.println(listaUser.get(0).getEmail());
        }
        request.setAttribute("listaUser", listaUser);
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/listaUser.jsp");
        dispatcher.forward(request, response);
    }

  private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
            request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String datadenascimento = request.getParameter("datadenascimento");

        Usuario user = new Usuario(nome, email, senha, cpf, telefone, sexo, datadenascimento);
        dao.insert(user);
        response.sendRedirect("/LoginJSP/admin/listaUser.jsp");
    }

    private void atualize(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String datadenascimento = request.getParameter("datadenascimento");
        
        Usuario user = new Usuario(id, nome, email, senha, cpf, telefone, sexo, datadenascimento);

        dao.update(user);
        
        response.sendRedirect("/LoginJSP/admin/listaUser.jsp");
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Usuario usr = new Usuario(id);
        dao.delete(usr);
        response.sendRedirect("/LoginJSP/admin/listaUser.jsp");
    }
}
