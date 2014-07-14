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

import backingbeans.HibernateSessionBean;
import com.google.gson.Gson;
import dao.DaoAnubis;
import dao.HibernateDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CapaFiltro;
import model.PerfilBase;
import model.PerfilCampos;
import model.Perfiles;
import model.PerfilesCapas;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Diego.Gonzalez
 */
public class Controller extends HttpServlet {

    public void getCapas(HttpServletRequest request, HttpServletResponse response) {
        try {
            String nombrePerfil = (String) request.getParameter("perfil");
            String jsonCallbackParam = request.getParameter("jsoncallback");
            try {
                nombrePerfil = URLDecoder.decode(nombrePerfil, "UTF-8");
            } catch (Exception ex) {
                nombrePerfil = "Default";
            }

            List<PerfilesCapas> capas = HibernateDao.getPerfilByName(nombrePerfil);
            Perfiles perf = HibernateDao.getPerfilPorNombre(nombrePerfil);
            Collections.sort((List) capas);
            Collections.reverse((List) capas);

            List<PerfilBase> bases = HibernateDao.getPerfilBaseByName(nombrePerfil);
            Collections.sort((List) bases);
            //Collections.reverse((List) bases);

            PerfilModel perfil = new PerfilModel();
            perfil.setNombrePerfil(nombrePerfil);
            List<CapasModel> capasConvertidas = convertCapaModel(capas);
            List<BasesModel> basesConvertidas = convertBasesModel(bases);
            perfil.setCapas(capasConvertidas);
            perfil.setBases(basesConvertidas);

            Gson gson;
            gson = new Gson();
            String jsonRes = gson.toJson(perfil);
            try {
                String respuesta = "";
                if ( jsonCallbackParam != null ) {
                    respuesta = jsonCallbackParam + "(" + jsonRes + ");";
                }else{
                    respuesta = jsonRes;
                }
                response.getOutputStream().write(respuesta.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        if (action.equalsIgnoreCase("/getcapas.ctr")) {
            getCapas(request, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    private List<CapasModel> convertCapaModel(List<PerfilesCapas> capas) {
        List<CapasModel> capasConv = new ArrayList<CapasModel>();
        for (PerfilesCapas capa : capas) {
            CapasModel capaC = new CapasModel();
            capaC.setNombre(capa.getCapaId().getNombre());
            capaC.setTabla(capa.getCapaId().getTabla());
            capaC.setVisible(capa.getVisible());
            capaC.setSoloBuscable(capa.getSoloBuscable());
            List<CamposModel> campos = new ArrayList<CamposModel>();

            List<String> filtros = new ArrayList<>();

            for (CapaFiltro filtro : capa.getCapaFiltroList()) {
                filtros.add(filtro.getIdCapaFiltro().getNombreCapa());
            }
            capaC.setFiltros(filtros);

            for (PerfilCampos campo : capa.getPerfilCamposList()) {
                CamposModel campoC = new CamposModel();
                campoC.setEtiquetaResultado(campo.getEtiquetaResultado());
                campoC.setCriterioBusqueda(campo.getCriterioBusqueda());
                campoC.setEtiqueta(campo.getEtiqueta());
                campoC.setNombreColumna(campo.getNombreColumna());
                campos.add(campoC);
                if (campo.getCriterioBusqueda().equals("Lista")) {
                    List<String> lista = DaoAnubis.getListadeValores(capaC.getTabla(), campoC.getNombreColumna());
                    String combo = "<select id='combo_" + capaC.getTabla() + "_" + campoC.getEtiqueta() + "'> <option value='null'>[Seleccione un valor]</option>";
                    for (String l : lista) {
                        if (l != null) {
                            combo += "<option value='" + l + "'>" + l + "</option>";
                        }
                    }
                    combo += "</select>";
                    campoC.setLista(combo);
                }
            }

            capaC.setCampos(campos);
            capasConv.add(capaC);
        }

        return capasConv;
    }

    private List<BasesModel> convertBasesModel(List<PerfilBase> bases) {
        List<BasesModel> basesConv = new ArrayList<>();
        for (PerfilBase base : bases) {
            BasesModel baseC = new BasesModel();
            baseC.setEtiqueta(base.getBaseId().getEtiqueta());
            baseC.setCapas(base.getBaseId().getTablas());
            basesConv.add(baseC);
        }
        return basesConv;
    }
}
