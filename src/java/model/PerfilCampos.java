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
@Table(name = "perfil_campos")
@NamedQueries({
    @NamedQuery(name = "PerfilCampos.findAll", query = "SELECT p FROM PerfilCampos p")})
public class PerfilCampos implements Serializable {
    private static final long serialVersionUID = 1L;    
    @Column(name = "etiqueta")
    private String etiqueta;
    @Column(name = "criterio_busqueda")
    private String criterioBusqueda;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "perfil_capa_id", referencedColumnName = "id", nullable=false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PerfilesCapas perfilCapaId;
    @JoinColumn(name = "campo_id", referencedColumnName = "id", nullable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Campos campoId;    
    @Column(name = "etiqueta_resultado")
    private Boolean etiquetaResultado;
  
    public PerfilCampos() {
    }

    public PerfilCampos(Integer id) {
        this.id = id;
    }

    public String getNombreColumna() {
        return this.campoId.getNombreColumna();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PerfilesCapas getPerfilCapaId() {
        return perfilCapaId;
    }

    public void setPerfilCapaId(PerfilesCapas perfilCapaId) {
        this.perfilCapaId = perfilCapaId;
    }

    public Campos getCampoId() {
        return campoId;
    }

    public void setCampoId(Campos campoId) {
        this.campoId = campoId;
    }

    public Boolean getEtiquetaResultado() {
        return etiquetaResultado;
    }

    public void setEtiquetaResultado(Boolean etiquetaResultado) {
        this.etiquetaResultado = etiquetaResultado;
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
        if (!(object instanceof PerfilCampos)) {
            return false;
        }
        PerfilCampos other = (PerfilCampos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PerfilCampos[ id=" + id + " ]";
    }
    
    
    /**
     * Retorna una copia de la instancia, con los mismos parametros
     * No clona todos sus atributos, solo la entidad
     * @return 
     */
    public PerfilCampos clonar(){
        PerfilCampos toRet = new PerfilCampos();
        toRet.setCampoId(this.getCampoId());
        toRet.setCriterioBusqueda(this.getCriterioBusqueda());
        toRet.setEtiqueta(this.getEtiqueta());
        toRet.setPerfilCapaId(this.getPerfilCapaId());
        return toRet;
    }
}
