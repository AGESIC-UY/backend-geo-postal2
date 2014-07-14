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
import java.util.List;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego.Gonzalez
 */
@Entity
@Table(name = "plugins")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plugins.findAll", query = "SELECT p FROM Plugins p"),
    @NamedQuery(name = "Plugins.findById", query = "SELECT p FROM Plugins p WHERE p.id = :id"),
    @NamedQuery(name = "Plugins.findByNombre", query = "SELECT p FROM Plugins p WHERE p.nombre = :nombre")})
public class Plugins implements Serializable {
    @OneToMany(mappedBy = "pluginId", fetch = FetchType.LAZY)
    private List<PerfilPlugins> perfilPluginsList;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion; 
    @Column(name = "js")
    private String js;

    public Plugins() {
    }

    public Plugins(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Plugins)) {
            return false;
        }
        Plugins other = (Plugins) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Plugins[ id=" + id + " ]";
    }

    public List<PerfilPlugins> getPerfilPluginsList() {
        return perfilPluginsList;
    }

    public void setPerfilPluginsList(List<PerfilPlugins> perfilPluginsList) {
        this.perfilPluginsList = perfilPluginsList;
    }

    public void agregarPlugin(PerfilPlugins nuevo) {
        this.perfilPluginsList.add(nuevo);
    }

    public void removePlugin(PerfilPlugins perfilPlugins) {
         this.perfilPluginsList.add(perfilPlugins);
    }
    
}
