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
package backingbeans;

import dao.DaoAnubis;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import utils.JSFUtils;

/**
 *
 * @author Diego.Gonzalez
 */
public class PrevisualizacionPopUp {

    public static void mostrarPopUp(String nombreCapa, String tabla, String template) {
        try {
            List<String> campos = new LinkedList<>();
            List<String[]> nombresCampos = DaoAnubis.getColumnsFromTable(tabla);
            for (String[] nombreCampo : nombresCampos) {
                campos.add(nombreCampo[0]);
            }
            HashMap<String, String> hash = DaoAnubis.obtenerValoresRandom(tabla, campos);
            String html = template.replace("\n", "");
            for (String aRemplazar : hash.keySet()) {
                html = html.replaceAll("<#" + aRemplazar + ">", hash.get(aRemplazar));
            }
            String htmlPopUp = "<div class=\"black_overlay\" ></div>"
                    + "<div  class=\"olPopupCloseBox\" onclick=\"cerrarPopUp()\" ></div>"
                    + "<div class=\"olFramedCloudPopupContent\">"
                    + "<fieldset>"
                    + "<legend>" + nombreCapa + "</legend>"
                    + html
                    + "</fieldset>"
                    + "</div>";
            String javascript =
                    " function cerrarPopUp(){"
                    + "     document.getElementById('contenedorPopUp').innerHTML = '' ;"
                    + "}"
                    + "document.getElementById('contenedorPopUp').innerHTML = '" + htmlPopUp + "';";
            JSFUtils.ejecutarJavascript(javascript);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
