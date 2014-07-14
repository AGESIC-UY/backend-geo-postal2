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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


/**
 *
 * @author Camila.Riveron
 */
@Entity
@Table(name = "perfiles_capas")
@NamedQueries({
    @NamedQuery(name = "PerfilesCapas.findAll", query = "SELECT p FROM PerfilesCapas p")})
public class PerfilesCapas implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;    
    @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Perfiles perfilId;
    @JoinColumn(name = "capa_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Capas capaId;
    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "solo_buscable")
    private Boolean soloBuscable;
    @Column(name = "buscable")
    private Boolean buscable;
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "orden")
    private Integer orden;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilCapaId", fetch = FetchType.LAZY)
    @OrderBy("campoId")
    private List<PerfilCampos> perfilCamposList;
    @OneToMany(mappedBy = "idCapaFiltro", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CapaFiltro> FiltrodeCapaList;
    @OneToMany(mappedBy = "idCapa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CapaFiltro> capaFiltroList ;
    @Column(name="template_pop_up")
    private String templatePopUp;
    @Column(name="template_barra_busqueda")
    private String templateBarraBusqueda;

    public String getNombreCapa() {
        return capaId.getNombre();
    }

    public void setNombreCapa(String nombreCapa) {
    }

        public List<CapaFiltro> getCapaFiltroList() {
        return capaFiltroList;
    }

    public void setCapaFiltroList(List<CapaFiltro> capaFiltroList) {
        this.capaFiltroList = capaFiltroList;
    }

    public List<CapaFiltro> getCapaFiltroList1() {
        return FiltrodeCapaList;
    }

    public void setCapaFiltroList1(List<CapaFiltro> capaFiltroList1) {
        this.FiltrodeCapaList = capaFiltroList1;
    }
    
    public List<PerfilCampos> getPerfilCamposList() {
        return perfilCamposList;
    }

    public void setPerfilCamposList(List<PerfilCampos> perfilCamposList) {
        this.perfilCamposList = perfilCamposList;
    }

    public PerfilesCapas() {
        perfilCamposList = new ArrayList();
    }

    public PerfilesCapas(Integer id) {
        this.id = id;
        perfilCamposList = new ArrayList();
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Perfiles getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Perfiles perfilId) {
        this.perfilId = perfilId;
    }

    public Capas getCapaId() {
        return capaId;
    }

    public void setCapaId(Capas capaId) {
        this.capaId = capaId;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getSoloBuscable() {
        return soloBuscable;
    }

    public void setSoloBuscable(Boolean soloBuscable) {
        this.soloBuscable = soloBuscable;
    }

    public String getTemplatePopUp() {
        return templatePopUp;
    }

    public void setTemplatePopUp(String templatePopUp) {
        this.templatePopUp = templatePopUp;
    }

    public Boolean getBuscable() {
        return buscable;
    }

    public void setBuscable(Boolean buscable) {
        this.buscable = buscable;
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
        if (!(object instanceof PerfilesCapas)) {
            return false;
        }
        PerfilesCapas other = (PerfilesCapas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
     
        return true;
    }

    @Override
    public String toString() {
        return "model.PerfilesCapas[ id=" + id + " ]";
    }

    public void asociarPerfilCapa(Perfiles perfilSeleccionado, Capas c) {
        this.capaId = c;
        this.perfilId = perfilSeleccionado;
        perfilSeleccionado.agregarCapa(this);
        c.agregarPerfil(this);
    }

    public void desasociarPerfilCapa(Perfiles perfilSeleccionado, Capas capaId) {
        capaId.quitarPerfil(this);
        capaId = null;
    }

    public void agregarCampo(PerfilCampos campoNuevo) {
        this.perfilCamposList.add(campoNuevo);
    }

    public void quitarCampo(PerfilCampos campo) {
        this.perfilCamposList.remove(campo);
    }

    public int compareTo(Object o) {
        if (o instanceof PerfilesCapas) {
            if (this.orden != null) {
                return this.orden.compareTo(((PerfilesCapas) o).orden);
            }
        }
        return 0;
    }

    public void removeFiltro(CapaFiltro aBorrar) {
        this.capaFiltroList.remove(aBorrar);
    }

    public void removeFiltroDe(CapaFiltro aBorrar) {
        this.FiltrodeCapaList.remove(aBorrar);
    }

    public void agregarFiltro(CapaFiltro nuevo) {
        this.capaFiltroList.add(nuevo);
    }

    public void agregarFiltroDe(CapaFiltro nuevo) {
        this.FiltrodeCapaList.add(nuevo);
    }

    public String getTemplateBarraBusqueda() {
        return templateBarraBusqueda;
    }

    public void setTemplateBarraBusqueda(String templateBarraBusqueda) {
        this.templateBarraBusqueda = templateBarraBusqueda;
    }   
    
}
