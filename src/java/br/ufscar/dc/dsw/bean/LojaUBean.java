package br.ufscar.dc.dsw.bean;

import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.pojo.Loja;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LojaUBean implements Serializable {

    private Loja loja;

    public String lista() {
        return "lojaU/index.xhtml";
    }

    public String edita(Long id) {
        LojaDAO dao = new LojaDAO();
        loja = dao.get(id);
        return "form.xhtml";
    }

    public String salva() {
        LojaDAO dao = new LojaDAO();
        if (loja.getId() == null) {
            dao.save(loja);
        } else {
            dao.update(loja);
        }
        return "index.xhtml";
    }

    public String volta() {
        return "/index.xhtml?faces-redirect=true";
    }

    public List<Loja> getLojas() throws SQLException {
        LojaDAO dao = new LojaDAO();
        return dao.getAll();
    }

    public Loja getLoja() {
        return loja;
    }
}
