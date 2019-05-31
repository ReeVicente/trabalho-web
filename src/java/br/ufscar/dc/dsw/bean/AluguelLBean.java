package br.ufscar.dc.dsw.bean;

import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.AluguelDAO;
import br.ufscar.dc.dsw.pojo.Loja;
import br.ufscar.dc.dsw.pojo.Cliente;
import br.ufscar.dc.dsw.pojo.Aluguel;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AluguelLBean implements Serializable {

    
    private Aluguel aluguel;
    private String mail;
    
    public String lista(String mail) {
        this.mail=mail;
        return "aluguelL/index.xhtml";
    }

    public String delete(Aluguel aluguel) {
        AluguelDAO dao = new AluguelDAO();
        dao.delete(aluguel);
        return "index.xhtml";
    }

    public String volta() {
        return "/index.xhtml?faces-redirect=true";
    }
    
    public List<Aluguel> getAlugueis() throws SQLException {
        AluguelDAO dao = new AluguelDAO();
        return dao.getNomeLoja(mail);
    }

    public Aluguel getAluguel() {
        return aluguel;
    }
    
    public List<Loja> getLojas() throws SQLException {
        LojaDAO dao = new LojaDAO();
        return dao.getAll();
    }
    
    public List<Cliente> getClientes() throws SQLException {
        ClienteDAO dao = new ClienteDAO();
        return dao.getAll();
    }

}
