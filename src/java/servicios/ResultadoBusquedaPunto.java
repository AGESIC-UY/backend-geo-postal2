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

import com.vividsolutions.jts.geom.Coordinate;
import java.util.HashMap;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.postgis.PGgeometry;
import org.postgresql.geometric.PGpoint;

public class ResultadoBusquedaPunto {

    private int id;    
    private PGgeometry geometria;
    private PGpoint punto;
    private String descripcion;
    private HashMap<String, String> propiedades;
    private int cp;

    public ResultadoBusquedaPunto(int id, PGgeometry geometria, PGpoint punto, String descripcion) {
        this.id = id;
        this.geometria = geometria;
        this.punto = punto;
        this.descripcion = descripcion;
        this.propiedades = new HashMap<String, String>();
        this.cp = 0;
    }

    public ResultadoBusquedaPunto(int id, PGgeometry geometria, PGpoint punto, String descripcion, HashMap<String, String> propiedades) {
        this.id = id;
        this.geometria = geometria;
        this.punto = punto;
        this.descripcion = descripcion;
        this.propiedades = propiedades;
        this.cp = 0;
    } 

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public HashMap<String, String> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(HashMap<String, String> propiedades) {
        this.propiedades = propiedades;
    }

    public int getId() {
        return id;
    }

    public PGgeometry getGeometria() {
        return geometria;
    }

    public PGpoint getPunto() {
        return punto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPunto(PGpoint punto) {
        this.punto = punto;
    }

    public String getPropiedadesJSON() {
        String salida = "{";

        for (String k : this.propiedades.keySet()) {
            if (!salida.equals("{")) {
                salida += ",";
            }
            salida += k + ": '" + this.propiedades.get(k) + "'";
        }

        salida += "}";
        return salida;
    }
}
