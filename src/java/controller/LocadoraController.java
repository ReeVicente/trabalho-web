package controller;


import model.Locadora;
import dao.LocadoraDAO;
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

@WebServlet(urlPatterns = "/locadora/*")
public class LocadoraController extends HttpServlet {

    private LocadoraDAO dao;

    @Override
    public void init() {
        dao = new LocadoraDAO();
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
        
        List<Locadora> listaLocadoras = dao.getAll();
        try (PrintWriter out = response.getWriter()) {
           out.println(listaLocadoras.get(0).getEmail());
        }
        request.setAttribute("listaLocadoras", listaLocadoras);
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/lista.jsp");
        dispatcher.forward(request, response);
    }

  private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
            request.setCharacterEncoding("UTF-8");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cnpj = request.getParameter("cnpj");
        String cidade = request.getParameter("cidade");

        Locadora locadora = new Locadora(nome, email, senha, cnpj, cidade);
        dao.insert(locadora);
        response.sendRedirect("/LoginJSP/admin/admin.jsp");
    }

    private void atualize(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cnpj = request.getParameter("cnpj");
        String cidade = request.getParameter("cidade");
        
        Locadora locadora = new Locadora(id, nome, email, senha, cnpj, cidade);

        dao.update(locadora);
        
        response.sendRedirect("/LoginJSP/admin/admin.jsp");
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Locadora rent = new Locadora(id);
        dao.delete(rent);
        response.sendRedirect("/LoginJSP/admin/admin.jsp");
    }
}
