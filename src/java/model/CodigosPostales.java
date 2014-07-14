/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Camila.Riveron
 */
@Entity
@Table(name = "codigos_postales")
@NamedQueries({
    @NamedQuery(name = "CodigosPostales.findAll", query = "SELECT c FROM CodigosPostales c")})
public class CodigosPostales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "apis_id")
    private Integer apisId;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cod_postal")
    private String codPostal;
    @Column(name = "tipo")
    private Integer tipo;
    @Lob
    @Column(name = "multipolygon")
    private Object multipolygon;

    public CodigosPostales() {
    }

    public CodigosPostales(Integer apisId) {
        this.apisId = apisId;
    }

    public Integer getApisId() {
        return apisId;
    }

    public void setApisId(Integer apisId) {
        this.apisId = apisId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Object getMultipolygon() {
        return multipolygon;
    }

    public void setMultipolygon(Object multipolygon) {
        this.multipolygon = multipolygon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apisId != null ? apisId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigosPostales)) {
            return false;
        }
        CodigosPostales other = (CodigosPostales) object;
        if ((this.apisId == null && other.apisId != null) || (this.apisId != null && !this.apisId.equals(other.apisId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CodigosPostales[ apisId=" + apisId + " ]";
    }
    
}
