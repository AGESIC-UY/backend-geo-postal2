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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Camila.Riveron
 */
@Entity
@Table(name = "perfil_plugins")
@NamedQueries({
    @NamedQuery(name = "PerfilPlugins.findAll", query = "SELECT p FROM PerfilPlugins p")})
public class PerfilPlugins implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "plugin_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Plugins pluginId;
    @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Perfiles perfilId;

    public PerfilPlugins() {
    }

    public PerfilPlugins(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Plugins getPluginId() {
        return pluginId;
    }

    public void setPluginId(Plugins pluginId) {
        this.pluginId = pluginId;
    }

    public Perfiles getPerfilCapaId() {
        return perfilId;
    }

    public void setPerfilCapaId(Perfiles perfilId) {
        this.perfilId = perfilId;
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
        if (!(object instanceof PerfilPlugins)) {
            return false;
        }
        PerfilPlugins other = (PerfilPlugins) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PerfilPlugins[ id=" + id + " ]";
    }
    
}
