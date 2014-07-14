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
public class ConsultaFiltro{

    private String NombreCapa;
    private List<ConsultaCampo> consultaCampos;    
    

    public String getNombreCapa() {
        return NombreCapa;
    }

    public void setNombreCapa(String NombreCapa) {
        this.NombreCapa = NombreCapa;
    }

    public List<ConsultaCampo> getConsultaCampos() {
        return consultaCampos;
    }

    public void setConsultaCampos(List<ConsultaCampo> consultaCampos) {
        this.consultaCampos = consultaCampos;
    }
}

