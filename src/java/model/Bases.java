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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
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
import javax.persistence.Transient;

/**
 *
 * @author Camila.Riveron
 */
@Entity
@Table(name = "bases")
@NamedQueries({
    @NamedQuery(name = "Bases.findAll", query = "SELECT b FROM Bases b")})
public class Bases implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "etiqueta")
    private String etiqueta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baseId", fetch = FetchType.LAZY)
    private List<PerfilBase> perfilBaseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baseId", fetch = FetchType.EAGER)
    private List<BaseCapa> baseCapaList;
    @Transient
    private Boolean selected;
    @Transient
    private Boolean esDefault;
    

    public Bases() {
    }

    public Bases(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getEsDefault() {
        return this.id<8;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public List<PerfilBase> getPerfilBaseList() {
        return perfilBaseList;
    }

    public void setPerfilBaseList(List<PerfilBase> perfilBaseList) {
        this.perfilBaseList = perfilBaseList;
    }

    public List<BaseCapa> getBaseCapaList() {
        return baseCapaList;
    }

    public void setBaseCapaList(List<BaseCapa> baseCapaList) {
        this.baseCapaList = baseCapaList;
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
        if (!(object instanceof Bases)) {
            return false;
        }
        Bases other = (Bases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "encriptar.Bases[ id=" + id + " ]";
    }

    /**
     * Devuelve una copia de la instancia, pero sin clonar sus atributos
     *
     * @return
     */
    public Bases clonar() {
        Bases toRet = new Bases();
        toRet.setEtiqueta(this.getEtiqueta());
        return toRet;
    }

    void agregarCapa(BaseCapa aThis) {
        this.baseCapaList.add(aThis);
    }

    public String getCapas() {
        String capas = "";
        for (BaseCapa c : this.baseCapaList) {
            if (!"".equals(capas)) {
                capas += ", ";
            }
            capas += c.getCapaId().getNombre();
        }

        return capas;
    }

    public void removeBase(PerfilBase aBorrar) {
        this.perfilBaseList.remove(aBorrar);
    }

    public void agregarBase(PerfilBase nuevo) {
        this.perfilBaseList.add(nuevo);
    }

    public ArrayList<String> getTablas() {
        ArrayList<String> capas = new ArrayList<>();
        List<BaseCapa> lista = this.baseCapaList;
        Collections.sort((List) lista);
        Collections.reverse((List) lista);
        
        for (BaseCapa c : lista) {
            capas.add(c.getCapaId().getTabla());
        }
        
        return capas;
    }
    
    public boolean getesDefault(){   
        ResourceBundle rb = ResourceBundle.getBundle("conf.conf");
        String oms = rb.getString("oms");
        String gh= rb.getString("gh");
        String gp = rb.getString("gp");
        String gs = rb.getString("gs");
        String gsat = rb.getString("gsat");
        return (oms.equals(this.getEtiqueta()) || gp.equals(this.getEtiqueta()) || gs.equals(this.getEtiqueta()) || gh.equals(this.getEtiqueta()) || gsat.equals(this.getEtiqueta()) );
    }
}
