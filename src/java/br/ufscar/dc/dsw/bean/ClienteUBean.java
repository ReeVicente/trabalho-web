package br.ufscar.dc.dsw.bean;

import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.PapelDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.pojo.Cliente;
import br.ufscar.dc.dsw.pojo.Papel;
import br.ufscar.dc.dsw.pojo.Usuario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ManagedBean
@SessionScoped
public class ClienteUBean implements Serializable {

    private Cliente cliente;
    private Usuario usuario;
    private String mail;

    public String lista(String mail) {
        this.mail=mail;
        return "clienteU/index.xhtml";
    }

    public String edita(Long id) {
        ClienteDAO dao = new ClienteDAO();
        UsuarioDAO dao1 = new UsuarioDAO();
        cliente = dao.get(id);
        usuario = dao1.getByEmail(cliente.getEmail());
        return "form.xhtml";
    }


    public String salva() {
        ClienteDAO dao = new ClienteDAO();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        PapelDAO papelDAO = new PapelDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Criando Usuario admin com papel ROLE_ADMIN
     

        usuario.setEmail(cliente.getEmail());
        usuario.setSenha(encoder.encode(cliente.getSenha()));
        usuario.setAtivo(true);
        
        if (cliente.getId() == null) {
            usuarioDAO.save(usuario);

        Papel p1 = new Papel();
        p1.setNome("ROLE_CLIENTE");
        papelDAO.save(p1);
        
        usuario.getPapel().add(p1);
        usuarioDAO.update(usuario);
            dao.save(cliente);
        } else {
            usuarioDAO.update(usuario);
            dao.update(cliente);
        }
        return "index.xhtml";
    }

    public String volta() {
        return "/index.xhtml?faces-redirect=true";
    }

    public Cliente getClientes() throws SQLException {
        ClienteDAO dao = new ClienteDAO();
        return dao.getByEmail(mail);
    }

    public Cliente getCliente() {
        return cliente;
    }
}
