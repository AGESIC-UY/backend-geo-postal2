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
import java.math.BigInteger;
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
@Table(name = "cod_postales_v0_pg")
@NamedQueries({
    @NamedQuery(name = "CodPostalesV0Pg.findAll", query = "SELECT c FROM CodPostalesV0Pg c")})
public class CodPostalesV0Pg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gid")
    private Integer gid;
    @Column(name = "__gid")
    private Long gid1;
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "area")
    private BigInteger area;
    @Column(name = "perimeter")
    private BigInteger perimeter;
    @Column(name = "ggcepo_")
    private Long ggcepo;
    @Column(name = "ggcepo_id")
    private Long ggcepoId;
    @Column(name = "ar_id")
    private Long arId;
    @Column(name = "rings_ok")
    private Long ringsOk;
    @Column(name = "rings_nok")
    private Long ringsNok;
    @Column(name = "map")
    private Long map;
    @Column(name = "x")
    private BigInteger x;
    @Column(name = "y")
    private BigInteger y;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "num_cp")
    private String numCp;
    @Column(name = "cod_postal")
    private String codPostal;
    @Lob
    @Column(name = "geom")
    private Object geom;

    public CodPostalesV0Pg() {
    }

    public CodPostalesV0Pg(Integer gid) {
        this.gid = gid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Long getGid1() {
        return gid1;
    }

    public void setGid1(Long gid1) {
        this.gid1 = gid1;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public BigInteger getArea() {
        return area;
    }

    public void setArea(BigInteger area) {
        this.area = area;
    }

    public BigInteger getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(BigInteger perimeter) {
        this.perimeter = perimeter;
    }

    public Long getGgcepo() {
        return ggcepo;
    }

    public void setGgcepo(Long ggcepo) {
        this.ggcepo = ggcepo;
    }

    public Long getGgcepoId() {
        return ggcepoId;
    }

    public void setGgcepoId(Long ggcepoId) {
        this.ggcepoId = ggcepoId;
    }

    public Long getArId() {
        return arId;
    }

    public void setArId(Long arId) {
        this.arId = arId;
    }

    public Long getRingsOk() {
        return ringsOk;
    }

    public void setRingsOk(Long ringsOk) {
        this.ringsOk = ringsOk;
    }

    public Long getRingsNok() {
        return ringsNok;
    }

    public void setRingsNok(Long ringsNok) {
        this.ringsNok = ringsNok;
    }

    public Long getMap() {
        return map;
    }

    public void setMap(Long map) {
        this.map = map;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumCp() {
        return numCp;
    }

    public void setNumCp(String numCp) {
        this.numCp = numCp;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public Object getGeom() {
        return geom;
    }

    public void setGeom(Object geom) {
        this.geom = geom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gid != null ? gid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodPostalesV0Pg)) {
            return false;
        }
        CodPostalesV0Pg other = (CodPostalesV0Pg) object;
        if ((this.gid == null && other.gid != null) || (this.gid != null && !this.gid.equals(other.gid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CodPostalesV0Pg[ gid=" + gid + " ]";
    }
    
}
