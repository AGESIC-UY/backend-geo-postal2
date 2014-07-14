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
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Camila.Riveron
 */
@Entity
@Table(name = "campos")
@NamedQueries({
    @NamedQuery(name = "Campos.findAll", query = "SELECT c FROM Campos c")})
public class Campos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre_columna")
    private String nombreColumna;
    @Column(name = "etiqueta")
    private String etiqueta;
    @Column(name = "criterio_busqueda")
    private String criterioBusqueda;
    @JoinColumn(name = "capa_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Capas capaId;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campoId", fetch = FetchType.LAZY)
//    private List<PerfilCampos> perfilCamposList;
    @Transient
    private String tipo;    
    
    public Campos() {
    }

    public Campos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

//    public List<PerfilCampos> getPerfilCamposList() {
//        return perfilCamposList;
//    }
//
//    public void setPerfilCamposList(List<PerfilCampos> perfilCamposList) {
//        this.perfilCamposList = perfilCamposList;
//    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }

    public Capas getCapaId() {
        return capaId;
    }

    public void setCapaId(Capas capaId) {
        this.capaId = capaId;
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
        if (!(object instanceof Campos)) {
            return false;
        }
        Campos other = (Campos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Campos[ id=" + id + " ]";
    }

//    public void agregarPerfilCampo(PerfilCampos campoNuevo) {
//        this.perfilCamposList.add(campoNuevo);
//    }

    public Campos clonar() {
        Campos toRet = new Campos();
        toRet.setEtiqueta(this.getEtiqueta());
        toRet.setNombreColumna(this.getNombreColumna());
        toRet.setCriterioBusqueda(this.getCriterioBusqueda());
        return toRet;
    }
    
}
