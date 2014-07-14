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

public class ResultadoBusqueda {

    private int id;
    private Geometry geometria;
    private Point punto;
    private String descripcion;
    private HashMap<String, String> propiedades;
    private int cp;

    public ResultadoBusqueda(int id, Geometry geometria, Point punto, String descripcion) {
        this.id = id;
        this.geometria = geometria;
        this.punto = punto;
        this.descripcion = descripcion;
        this.propiedades = new HashMap<String, String>();
        this.cp = 0;
    }

    public ResultadoBusqueda(int id, Geometry geometria, Point punto, String descripcion, HashMap<String, String> propiedades) {
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

    public Geometry getGeometria() {
        return geometria;
    }

    public Point getPunto() {
        return punto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPunto(Point punto) {
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

    public String getGeometriaJSON() {
        if (geometria == null) {
            return "null";
        }

        String salida = "{tipo: '" + geometria.getGeometryType() + "',";
        salida += "srid: " + geometria.getSRID() + ",";
        salida += "geometrias: [";
        if (geometria instanceof MultiPolygon) {
            MultiPolygon mp = (MultiPolygon) geometria;
            int i = 1;

            for (int y = 0; y < mp.getNumGeometries(); y++) {
                Polygon p = (Polygon) mp.getGeometryN(y);
                if (i != 1) {
                    salida += ",";
                }
                salida += "[";
                int j = 1;
                for (Coordinate pp : p.getExteriorRing().getCoordinates()) {
                    if (j != 1) {
                        salida += ",";
                    }
                    salida += "{x:" + pp.x + ",y:" + pp.y + "}";
                    j++;
                }
                salida += "]";
                i++;
            }

        } else if (geometria instanceof MultiLineString) {
            MultiLineString ml = (MultiLineString) geometria;
            int i = 1;
             for (int y = 0; y < ml.getNumGeometries(); y++) {
                LineString ln = (LineString) ml.getGeometryN(y);            
                if (i != 1) {
                    salida += ",";
                }
                salida += "[";
                int j = 1;
                for (Coordinate pp : ln.getCoordinates()) {
                    if (j != 1) {
                        salida += ",";
                    }
                    salida += "{x:" + pp.x + ",y:" + pp.y + "}";
                    j++;
                }
                salida += "]";
                i++;
            }
        } else if (geometria instanceof Point) {
            Point pp = (Point) geometria;
            salida += "{x:" + pp.getCoordinate().x + ",y:" + pp.getCoordinate().y + "}";
        } else {
            System.out.println("TIPO NO SOPORTADO: " + geometria.getGeometryType());
            return "null";
        }
//		System.out.println(geometria.getGeometry().getTypeString());
//		System.out.println(geometria.getValue());

        salida += "]}";

        return salida;
    }
}
