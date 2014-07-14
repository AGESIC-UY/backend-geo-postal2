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
@Table(name = "capa_filtro")
@NamedQueries({
    @NamedQuery(name = "CapaFiltro.findAll", query = "SELECT c FROM CapaFiltro c")})
public class CapaFiltro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_capa_filtro", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PerfilesCapas idCapaFiltro;
    @JoinColumn(name = "id_capa", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PerfilesCapas idCapa;

    public CapaFiltro() {
    }

    public CapaFiltro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PerfilesCapas getIdCapaFiltro() {
        return idCapaFiltro;
    }

    public void setIdCapaFiltro(PerfilesCapas idCapaFiltro) {
        this.idCapaFiltro = idCapaFiltro;
    }

    public PerfilesCapas getIdCapa() {
        return idCapa;
    }

    public void setIdCapa(PerfilesCapas idCapa) {
        this.idCapa = idCapa;
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
        if (!(object instanceof CapaFiltro)) {
            return false;
        }
        CapaFiltro other = (CapaFiltro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "encriptar.CapaFiltro[ id=" + id + " ]";
    }
    
}
