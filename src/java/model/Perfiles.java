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
package model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Camila.Riveron
 */
@Entity
@Table(name = "perfiles")
@NamedQueries({
    @NamedQuery(name = "Perfiles.findAll", query = "SELECT p FROM Perfiles p")})
public class Perfiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "plugins")
    private String plugins;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilId", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PerfilesCapas> perfilesCapasList;
    @OneToMany(mappedBy = "perfilId", fetch = FetchType.LAZY)
    private List<PerfilPlugins> perfilPluginsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilId", fetch = FetchType.LAZY)
    private List<PerfilBase> perfilBaseList;

    public Perfiles() {
        perfilesCapasList = new ArrayList();
    }

    public Perfiles(Integer id) {
        this.id = id;
        perfilesCapasList = new ArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public List<PerfilBase> getPerfilBaseList() {
        return perfilBaseList;
    }

    public void setPerfilBaseList(List<PerfilBase> perfilBaseList) {
        this.perfilBaseList = perfilBaseList;
    }

    public List<PerfilPlugins> getPerfilPluginsList() {
        return perfilPluginsList;
    }

    public void setPerfilPluginsList(List<PerfilPlugins> perfilPluginsList) {
        this.perfilPluginsList = perfilPluginsList;
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreURL() {
        try {
            return URLEncoder.encode(nombre, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Perfiles.class.getName()).log(Level.SEVERE, null, ex);
            return "Default";
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PerfilesCapas> getPerfilesCapasList() {
        return perfilesCapasList;
    }

    public void setPerfilesCapasList(List<PerfilesCapas> perfilesCapasList) {
        this.perfilesCapasList = perfilesCapasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfiles)) {
            return false;
        }
        Perfiles other = (Perfiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Perfiles[ id=" + id + " ]";
    }

    public void agregarCapa(PerfilesCapas perfilCapa) {
        this.perfilesCapasList.add(0,perfilCapa);
    }

    public void quitarCapa(PerfilesCapas perfilCapa) {
        this.perfilesCapasList.remove(perfilCapa);
    }

    /**
     * Se utiliza para no mostrar los botones de edicion y eliminacion en la
     * página, para el perfil Default
     *
     * @return
     */
    public boolean getEsDefault() {
        return nombre.equals("Default");
    }

    public void setEsDefault() {
    }

    public PerfilPlugins getPlugin(int id) {
        for (PerfilPlugins perfil : this.getPerfilPluginsList()) {
            if (perfil.getPluginId().getId() == id) {
                return perfil;
            }
        }
        return null;
    }

    public void agregarPlugin(PerfilPlugins nuevo) {
        this.perfilPluginsList.add(nuevo);
    }

    public void removerPlugin(PerfilPlugins perfilPlugins) {
        this.perfilPluginsList.remove(perfilPlugins);
    }

    public PerfilBase getBase(Integer id) {
        for (PerfilBase perfil : this.getPerfilBaseList()) {
            if (perfil.getBaseId().getId() == id) {
                return perfil;
            }
        }
        return null;
    }

    public void removeBase(PerfilBase aBorrar) {
        this.perfilBaseList.remove(aBorrar);
    }

    public void agregarBase(PerfilBase nuevo) {
        this.perfilBaseList.add(nuevo);
    }
}
