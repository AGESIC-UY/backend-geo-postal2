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
@Table(name = "perfil_base")
@NamedQueries({
    @NamedQuery(name = "PerfilBase.findAll", query = "SELECT p FROM PerfilBase p")})
public class PerfilBase implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Perfiles perfilId;
    @JoinColumn(name = "base_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bases baseId;
    @Column(name = "orden")
    private Integer orden;

    public PerfilBase() {
    }

    public PerfilBase(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Perfiles getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Perfiles perfilId) {
        this.perfilId = perfilId;
    }

    public Bases getBaseId() {
        return baseId;
    }

    public void setBaseId(Bases baseId) {
        this.baseId = baseId;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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
        if (!(object instanceof PerfilBase)) {
            return false;
        }
        PerfilBase other = (PerfilBase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
     @Override
    public int compareTo(Object o) {
        if (o instanceof PerfilBase) {
            if (this.orden != null) {
                return this.orden.compareTo(((PerfilBase) o).orden);
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "encriptar.PerfilBase[ id=" + id + " ]";
    }
    
}
