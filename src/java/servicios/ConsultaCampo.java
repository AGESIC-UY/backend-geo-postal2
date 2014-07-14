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
public class ConsultaCampo {
    private String NombreCampo;
    private String Valor;
    private String Criterio;

    public String getNombreCampo() {
        return NombreCampo;
    }

    public void setNombreCampo(String NombreCampo) {
        this.NombreCampo = NombreCampo;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public String getCriterio() {
        return Criterio;
    }

    public void setCriterio(String Criterio) {
        this.Criterio = Criterio;
    }
    
    
}
