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
package servicios;

import java.util.List;

/**
 *
 * @author Camila.Riveron
 */
public class CapasModel {

    private String Nombre;    
    private String Tabla;
    private boolean Visible;
    private boolean soloBuscable;
    private boolean buscable;
    private String grupo;
    private String metadato;
    private Integer escalaMinima;
    private Integer escalaMaxima;
    private List<CamposModel> campos;
    private List<String> filtros;
    private String templatePopUp;
    private String templateBusqueda;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getMetadato() {
        return metadato;
    }

    public void setMetadato(String metadato) {
        this.metadato = metadato;
    }  

    public List<String> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<String> filtros) {
        this.filtros = filtros;
    }

    public String getTabla() {
        return Tabla;
    }

    public void setTabla(String Tabla) {
        this.Tabla = Tabla;
    }

    public boolean isVisible() {
        return Visible;
    }

    public void setVisible(boolean Visible) {
        this.Visible = Visible;
    }

    public List<CamposModel> getCampos() {
        return campos;
    }

    public void setCampos(List<CamposModel> campos) {
        this.campos = campos;
    }

    public boolean isSoloBuscable() {
        return soloBuscable;
    }

    public void setSoloBuscable(boolean soloBuscable) {
        this.soloBuscable = soloBuscable;
    }

    public String getTemplatePopUp() {
        return templatePopUp;
    }

    public void setTemplatePopUp(String templatePopUp) {
        this.templatePopUp = templatePopUp;
    }

    public boolean isBuscable() {
        return buscable;
    }

    public void setBuscable(boolean buscable) {
        this.buscable = buscable;
    }

    public String getTemplateBusqueda() {
        return templateBusqueda;
    }

    public void setTemplateBusqueda(String templateBusqueda) {
        this.templateBusqueda = templateBusqueda;
    } 
    
}
