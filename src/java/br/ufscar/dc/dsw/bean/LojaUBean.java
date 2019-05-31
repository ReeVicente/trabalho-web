package br.ufscar.dc.dsw.bean;

import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.dao.PapelDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.pojo.Loja;
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
public class LojaUBean implements Serializable {

    private Loja loja;
    private Usuario usuario;
    private String mail;

    public String lista(String mail) {
        this.mail=mail;
        return "lojaU/index.xhtml";
    }

    public String edita(Long id) {
        LojaDAO dao = new LojaDAO();
        UsuarioDAO dao1 = new UsuarioDAO();
        loja = dao.get(id);
        usuario = dao1.getByEmail(loja.getEmail());
        return "form.xhtml";
    }

    public String salva() {
        LojaDAO dao = new LojaDAO();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        PapelDAO papelDAO = new PapelDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Criando Usuario admin com papel ROLE_ADMIN
     

        usuario.setEmail(loja.getEmail());
        usuario.setSenha(encoder.encode(loja.getSenha()));
        usuario.setAtivo(true);
        
        if (loja.getId() == null) {
            usuarioDAO.save(usuario);

        Papel p1 = new Papel();
        p1.setNome("ROLE_LOJA");
        papelDAO.save(p1);
        
        usuario.getPapel().add(p1);
        usuarioDAO.update(usuario);
            dao.save(loja);
        } else {
            usuarioDAO.update(usuario);
            dao.update(loja);
        }
        return "index.xhtml";
    }

    public String volta() {
        return "/index.xhtml?faces-redirect=true";
    }

    public Loja getLojas() throws SQLException {
        LojaDAO dao = new LojaDAO();
        return dao.getByEmail(mail);
    }

    public Loja getLoja() {
        return loja;
    }
}
