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
@Table(name = "base_capa")
@NamedQueries({
    @NamedQuery(name = "BaseCapa.findAll", query = "SELECT b FROM BaseCapa b")})
public class BaseCapa implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "orden")
    private Integer orden;
    @JoinColumn(name = "capa_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Capas capaId;
    @JoinColumn(name = "base_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bases baseId;

    public BaseCapa() {
    }

    public BaseCapa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Capas getCapaId() {
        return capaId;
    }

    public void setCapaId(Capas capaId) {
        this.capaId = capaId;
    }

    public Bases getBaseId() {
        return baseId;
    }

    public void setBaseId(Bases baseId) {
        this.baseId = baseId;
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
        if (!(object instanceof BaseCapa)) {
            return false;
        }
        BaseCapa other = (BaseCapa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        return "BaseCapa[ orden = " + orden + ",  capaId = "+ this.getCapaId().getId()+" , nombreCapa = "+ this.capaId.getNombre() +"]";
    }

    public void asociarPerfilCapa(Bases baseEnCurso, Capas capaAAgregar) {
        this.capaId = capaAAgregar;
        this.baseId = baseEnCurso;
        baseEnCurso.agregarCapa(this);
        capaAAgregar.agregarBase(this);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof BaseCapa) {
            if (this.orden != null) {
                return this.orden.compareTo(((BaseCapa) o).orden);
            }
        }
        return 0;
    }
    
}
