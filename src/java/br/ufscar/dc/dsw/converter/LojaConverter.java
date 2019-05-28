package br.ufscar.dc.dsw.converter;

import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.pojo.Loja;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("LojaConverter")
public class LojaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Long id = Long.parseLong(string);
        LojaDAO dao = new LojaDAO();
        return dao.get(id);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Loja loja = (Loja) o;
        return loja.getId().toString();
    }
    
}
