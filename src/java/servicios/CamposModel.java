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

/**
 *
 * @author Camila.Riveron
 */
public class CamposModel {

    private String NombreColumna;
    //Si la etiqueta es vacía, el campo no es visible para ese perfil
    private String Etiqueta;
    private String CriterioBusqueda;
    private Boolean etiquetaResultado;
    private String lista;

    public String getNombreColumna() {
        return NombreColumna;
    }

    public void setNombreColumna(String NombreColumna) {
        this.NombreColumna = NombreColumna;
    }

    public String getEtiqueta() {
        return Etiqueta;
    }

    public void setEtiqueta(String Etiqueta) {
        this.Etiqueta = Etiqueta;
    }

    public String getCriterioBusqueda() {
        return CriterioBusqueda;
    }

    public void setCriterioBusqueda(String CriterioBusqueda) {
        this.CriterioBusqueda = CriterioBusqueda;
    }

    public Boolean getEtiquetaResultado() {
        return etiquetaResultado;
    }

    public void setEtiquetaResultado(Boolean etiquetaResultado) {
        this.etiquetaResultado = etiquetaResultado;
    }
    
    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    } 
    
    
}
