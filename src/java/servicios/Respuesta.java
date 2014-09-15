/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.HashMap;

/**
 *
 * @author Silvina.Lizardi
 */
public class Respuesta {
    private int id;  
    private HashMap<String, String> propiedades;

    public Respuesta(int id, HashMap<String, String> propiedades) {
        this.id = id;
        this.propiedades = propiedades;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<String, String> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(HashMap<String, String> propiedades) {
        this.propiedades = propiedades;
    }
    
}
