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
import java.util.List;
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
@Table(name = "capas")
@NamedQueries({
    @NamedQuery(name = "Capas.findAll", query = "SELECT c FROM Capas c")})
public class Capas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tabla")
    private String tabla;
    @Column(name = "metadato")
    private String metadato;
    @Column(name = "escalaminima")
    private Integer escalaMinima;
    @Column(name = "escalamaxima")
    private Integer escalaMaxima;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capaId", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PerfilesCapas> perfilesCapasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capaId", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Campos> camposList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capaId", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BaseCapa> baseCapaList;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilId", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PerfilBase> perfilBaseList;*/
    @Column(name="template_pop_up")
    private String templatePopUp;
    
    @Transient
    public Boolean selected;

    public Capas() {
        camposList = new ArrayList();
        perfilesCapasList = new ArrayList();
    }

    public Capas(Integer id) {
        this.id = id;
        camposList = new ArrayList();
        perfilesCapasList = new ArrayList();
    }

    public Integer getEscalaMinima() {
        return escalaMinima;
    }

    public void setEscalaMinima(Integer escalaMinima) {
        this.escalaMinima = escalaMinima;
    }

    public Integer getEscalaMaxima() {
        return escalaMaxima;
    }

    public void setEscalaMaxima(Integer escalaMaxima) {
        this.escalaMaxima = escalaMaxima;
    }   
    

    public String getMetadato() {
        return metadato;
    }

    public void setMetadato(String metadato) {
        this.metadato = metadato;
    }  
    

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<BaseCapa> getBaseCapaList() {
        return baseCapaList;
    }

    public void setBaseCapaList(List<BaseCapa> baseCapaList) {
        this.baseCapaList = baseCapaList;
    }

    public String getTemplatePopUp() {
        return templatePopUp;
    }

    public void setTemplatePopUp(String templatePopUp) {
        this.templatePopUp = templatePopUp;
    }

    
    
/*
    public List<PerfilBase> getPerfilBaseList() {
        return perfilBaseList;
    }

    public void setPerfilBaseList(List<PerfilBase> perfilBaseList) {
        this.perfilBaseList = perfilBaseList;
    }*/

    public List<PerfilesCapas> getPerfilesCapasList() {
        return perfilesCapasList;
    }

    public void setPerfilesCapasList(List<PerfilesCapas> perfilesCapasList) {
        this.perfilesCapasList = perfilesCapasList;
    }

    public List<Campos> getCamposList() {
        return camposList;
    }

    public void setCamposList(List<Campos> camposList) {
        this.camposList = camposList;
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
        if (!(object instanceof Capas)) {
            return false;
        }
        Capas other = (Capas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Capas[ id=" + id + " ]";
    }

    void agregarPerfil(PerfilesCapas perfil) {
        this.perfilesCapasList.add(perfil);
    }

    void quitarPerfil(PerfilesCapas perfil) {
        this.perfilesCapasList.remove(perfil);
    }

    public void agregarCampo(Campos campo) {
        this.camposList.add(campo);
    }

    /**
     * Devuelve una copia de la instancia, pero sin clonar sus atributos
     *
     * @return
     */
    public Capas clonar() {
        Capas toRet = new Capas();
        toRet.setNombre(this.getNombre());
        toRet.setTabla(this.getTabla());
        return toRet;
    }

    void agregarBase(BaseCapa aThis) {
        this.baseCapaList.add(aThis);
    }
}
