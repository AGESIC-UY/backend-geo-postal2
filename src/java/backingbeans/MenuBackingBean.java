/*
 * Licensed under the GPL License.  You may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package backingbeans;

import dao.HibernateDao;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Usuarios;

/**
 *
 * @author Camila.Riveron
 */
@ManagedBean(name = "menu")
@ViewScoped
public class MenuBackingBean {

    public MenuBackingBean() {
        SessionBean sessionBean = HibernateDao.findBean("SessionBean");
        Usuarios u = sessionBean.getUser();
        if (u != null) {
            usuario = u.getNombre();
        } else {
            String url = "/Anubis/login.jsf";
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            try {
                ec.redirect(url);
            } catch (IOException ex) {
                Logger.getLogger(LoginBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                
            }
        }
    }
    private String usuario;
    private String urlIsis;

    public String getUrlIsis() {
        ResourceBundle rb = ResourceBundle.getBundle("conf.conf");
        String url = rb.getString("url_visualizador");        
        return url;
    }  

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void cerrarSesion() {
        SessionBean sessionBean = HibernateDao.findBean("SessionBean");
        sessionBean.setUser(null);
        String url = "/Anubis/login.jsf";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
