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
@ManagedBean(name = "login")
@ViewScoped
public class LoginBackingBean {

    private String usuario;
    private String contraseña;
    private String error = "";

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void login() {
        if (usuario.isEmpty()) {
            error = "Debe ingresar un usuario.";
        } else if (contraseña.isEmpty()) {
            error = "Debe ingresar una contraseña.";
        } else {
            String res = HibernateDao.login(usuario, contraseña);
            if (!"OK".equals(res)) {
                error = res;
            } else {
                error = "";
                SessionBean sessionBean = HibernateDao.findBean("SessionBean");
                Usuarios u = HibernateDao.getUser(usuario, contraseña);
                if (u != null) {
                    sessionBean.setUser(u);
                    String url = "/Anubis/index.jsf";
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
        }
    }
}
