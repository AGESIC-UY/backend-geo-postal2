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

import java.util.ArrayList;



/**
 *
 * @author Camila.Riveron
 */
public class BasesModel {
    private String Etiqueta;    
    private ArrayList<String> Capas; 

    public String getEtiqueta() {
        return Etiqueta;
    }

    public void setEtiqueta(String Etiqueta) {
        this.Etiqueta = Etiqueta;
    }

    public ArrayList<String> getCapas() {
        return Capas;
    }

    public void setCapas(ArrayList<String> Capas) {
        this.Capas = Capas;
    }   
    
}
