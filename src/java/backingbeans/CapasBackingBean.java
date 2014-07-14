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

import com.icesoft.faces.component.ext.RowSelector;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import dao.DaoAnubis;
import dao.HibernateDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import model.Campos;
import model.Capas;
import model.GeometryColumns;
import utils.JSFUtils;

/**
 *
 * @author Camila.Riveron
 */
@ManagedBean(name = "capas")
@ViewScoped
public class CapasBackingBean implements Serializable {

    private List<Capas> capas;
    private List<GeometryColumns> tablasdisponibles;
    private Boolean mostrarPopUpAgregarCapa;
    private String nombreCapa;
    private Integer lastSelectedRow;
    private RowSelector rowselector;
    private int maxRowsTablas;
    private int maxRowsCapas;
    private Capas capaSeleccionada;
    private Capas toModify;
    private Capas toModifyBK;
    private int toRemove;
    private int toEdit;
    private int capaEsquemaPopUp;
    private Campos campoEditar;
    private Campos campoEditarBK;
    private int campoEditarId;
    private boolean mostrarPopUpEditarCapa;
    private boolean mostrarPopUpEditarCampo;
    private boolean ingresarConexion;
    private Capas capaEnCurso;
    private Boolean campoAModificarNumerico = false;
    private List criteriosDeBusquedaNumericos = null;
    private List criteriosDeBusquedaTexto = null;
    private String host;
    private String puerto;
    private String user;
    private String password;
    private String base;
    private List<SelectItem> criteriosDeBusqueda;

    public CapasBackingBean() {
        try {
            inicializarComboCriterioDeBusqueda();
            capas = HibernateDao.findAll(Capas.class.getName());
            tablasdisponibles = getTablas();
            mostrarPopUpAgregarCapa = false;
            maxRowsTablas = 6;
            maxRowsCapas = 6;
            toModify = new Capas();
            campoEditar = new Campos();
            ingresarConexion = false;
        } catch (Exception ex) {
            Logger.getLogger(CapasBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        
    }

    private void inicializarComboCriterioDeBusqueda() {
        criteriosDeBusquedaNumericos = new LinkedList<SelectItem>();
        criteriosDeBusquedaTexto = new LinkedList<SelectItem>();
        ResourceBundle rb = ResourceBundle.getBundle("conf.conf");
        String[] criteriosNumericos = rb.getString("criterios.busqueda.numericos").split(",");
        String[] criteriosTexto = rb.getString("criterios.busqueda.texto").split(",");
        for (String criterio : criteriosNumericos) {
            criteriosDeBusquedaNumericos.add(new SelectItem(criterio));
        }
        for (String criterio : criteriosTexto) {
            criteriosDeBusquedaTexto.add(new SelectItem(criterio));
        }
    }

    private List<GeometryColumns> getTablas() {
        List<GeometryColumns> all = HibernateDao.findAll(GeometryColumns.class.getName());
        List<GeometryColumns> activas = new ArrayList();
        for (Capas c : capas) {
            for (GeometryColumns tabla : all) {
                if (tabla.getFTableName().equals(c.getTabla())) {
                    activas.add(tabla);
                }
            }
        }
        for (GeometryColumns activa : activas) {
            all.remove(activa);
        }

        return all;
    }

    public List<SelectItem> getCriteriosDeBusqueda() {
        return criteriosDeBusqueda;
    }

    // <editor-fold desc=" GETTERS Y SETTERS " defaultstate="collapsed">
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Campos getCampoEditar() {
        return campoEditar;
    }

    public boolean isIngresarConexion() {
        return ingresarConexion;
    }

    public void setIngresarConexion(boolean ingresarConexion) {
        this.ingresarConexion = ingresarConexion;
    }

    public void setCampoEditar(Campos campoEditar) {
        this.campoEditar = campoEditar;
    }

    public List getCriteriosDeBusquedaNumericos() {
        return criteriosDeBusquedaNumericos;
    }

    public void setCriteriosDeBusquedaNumericos(List criteriosDeBusquedaNumericos) {
        this.criteriosDeBusquedaNumericos = criteriosDeBusquedaNumericos;
    }

    public List getCriteriosDeBusquedaTexto() {
        return criteriosDeBusquedaTexto;
    }

    public void setCriteriosDeBusquedaTexto(List criteriosDeBusquedaTexto) {
        this.criteriosDeBusquedaTexto = criteriosDeBusquedaTexto;
    }

    public Capas getCapaEnCurso() {
        return capaEnCurso;
    }

    public void setCapaEnCurso(Capas capasEnCurso) {
        this.capaEnCurso = capasEnCurso;
    }

    public Boolean getCampoAModificarNumerico() {
        return campoAModificarNumerico;
    }

    public void setCampoAModificarNumerico(Boolean campoAModificarNumerico) {
        this.campoAModificarNumerico = campoAModificarNumerico;
    }

    public int getCampoEditarId() {
        return campoEditarId;
    }

    public void setCampoEditarId(int campoEditarId) {
        this.campoEditarId = campoEditarId;
    }

    public boolean isMostrarPopUpEditarCampo() {
        return mostrarPopUpEditarCampo;
    }

    public void setMostrarPopUpEditarCampo(boolean mostrarPopUpEditarCampo) {
        this.mostrarPopUpEditarCampo = mostrarPopUpEditarCampo;
    }

    public Capas getCapaSeleccionada() {
        return capaSeleccionada;
    }

    public void setCapaSeleccionada(Capas capaSeleccionada) {
        this.capaSeleccionada = capaSeleccionada;
    }

    public Capas getToModify() {
        return toModify;
    }

    public void setToModify(Capas toModify) {
        this.toModify = toModify;
    }

    public int getToRemove() {
        return toRemove;
    }

    public void setToRemove(int toRemove) {
        this.toRemove = toRemove;
    }

    public int getToEdit() {
        return toEdit;
    }

    public void setToEdit(int toEdit) {
        this.toEdit = toEdit;
    }

    public boolean isMostrarPopUpEditarCapa() {
        return mostrarPopUpEditarCapa;
    }

    public void setMostrarPopUpEditarCapa(boolean mostrarPopUpEditarCapa) {
        this.mostrarPopUpEditarCapa = mostrarPopUpEditarCapa;
    }

    public int getMaxRowsCapas() {
        return maxRowsCapas;
    }

    public void setMaxRowsCapas(int maxRowsCapas) {
        this.maxRowsCapas = maxRowsCapas;
    }

    public int getMaxRowsTablas() {
        return maxRowsTablas;
    }

    public void setMaxRowsTablas(int maxRowsTablas) {
        this.maxRowsTablas = maxRowsTablas;
    }

    public Integer getLastSelectedRow() {
        return lastSelectedRow;
    }

    public void setLastSelectedRow(Integer lastSelectedColumn) {
        this.lastSelectedRow = lastSelectedColumn;
    }

    public String getNombreCapa() {
        return nombreCapa;
    }

    public void setNombreCapa(String nombreCapa) {
        this.nombreCapa = nombreCapa;
    }

    public RowSelector getRowselector() {
        return rowselector;
    }

    public void setRowselector(RowSelector rowselector) {
        this.rowselector = rowselector;
    }

    public List<Capas> getCapas() {
        return capas;
    }

    public List<GeometryColumns> getTablasdisponibles() {
        return tablasdisponibles;
    }

    public Boolean getMostrarPopUpAgregarCapa() {
        return mostrarPopUpAgregarCapa;
    }

    public void setMostrarPopUpAgregarCapa(Boolean mostrarPopUpAgregarCapa) {
        this.mostrarPopUpAgregarCapa = mostrarPopUpAgregarCapa;
    }

    public void setTablasdisponibles(List<GeometryColumns> tablasdisponibles) {
        this.tablasdisponibles = tablasdisponibles;
    }

    // </editor-fold>
    public void mostrarAgregarCapa() {
        tablasdisponibles = getTablas();
//        setIngresarConexion(true);
//        JSFUtils.mostrarMensajeExito("Conexión exitosa.");
        setMostrarPopUpAgregarCapa(true);
    }

    public void ocultarAgregarCapa() {
        setMostrarPopUpAgregarCapa(false);
        nombreCapa = "";
    }

    public void ocultarAgregarCapaConexion() {
        setIngresarConexion(false);
        setMostrarPopUpAgregarCapa(false);
        host = "";
        puerto = "";
        user = "";
        password = "";
    }

    public void agregarCapa() {
        GeometryColumns seleccionada = null;
        for (GeometryColumns tabla : tablasdisponibles) {
            if (tabla.selected != null & tabla.getSelected()) {
                seleccionada = tabla;
            }
        }

        if (seleccionada != null && !nombreCapa.isEmpty()) {
            if (HibernateDao.getCapasPorNombre(nombreCapa) != null) {
                JSFUtils.mostrarError("Ya existe una capa con el mismo nombre");
                return;
            }
            try {
                Capas nuevaCapa = new Capas();
                nuevaCapa.setNombre(nombreCapa);
                String nombre = seleccionada.getFTableName();
                nuevaCapa.setTabla(nombre);
                List<String[]> nombresCampos = DaoAnubis.getColumnsFromTable(nombre);

                nuevaCapa = (Capas) HibernateDao.saveOrUpdate(nuevaCapa);

                ResourceBundle rb = ResourceBundle.getBundle("conf.conf");
                String criterioDeBusquedaDefault = rb.getString("criterio.busqueda.default");
                for (int i = 0; i < nombresCampos.size(); i++) {
                    String c = nombresCampos.get(i)[0];
                    Campos campo = new Campos();
                    campo.setNombreColumna(c);
                    campo.setCapaId(nuevaCapa);
                    campo.setEtiqueta("");

                    campo.setCriterioBusqueda(criterioDeBusquedaDefault);
                    nuevaCapa.agregarCampo(campo);
                }

                HibernateDao.update(nuevaCapa);
                capas = HibernateDao.obtenerCapasCompletas();
                ocultarAgregarCapa();
                nombreCapa = "";
                tablasdisponibles = getTablas();

            } catch (Exception ex) {
                Logger.getLogger(CapasBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
    }

    public void tablaSelectionListener(RowSelectorEvent event) {
        for (GeometryColumns c : tablasdisponibles) {
            c.setSelected(false);
        }

        tablasdisponibles.get(event.getRow()).setSelected(event.isSelected());
    }

    public void capaSelectionListener(RowSelectorEvent event) {
        for (Capas c : capas) {
            c.setSelected(false);
        }

        capas.get(event.getRow()).setSelected(event.isSelected());
    }

    public void yesDeleteCapa() {
        capas = HibernateDao.findAll(Capas.class.getName());
        Capas removeCapa = getCapaById(toRemove);
        if (removeCapa != null) {
            if (capaSeleccionada != null && capaSeleccionada.equals(removeCapa)) {
                capaSeleccionada = null;
            }
//            capas.remove(removeCapa);
            /*capas = HibernateDao.findAll(Capas.class.getName());*/
            HibernateDao.delete("Capas", removeCapa.getId());
//            HibernateDao.delete(removeCapa);
//            HibernateDao.resetSession();
            capas = HibernateDao.findAll(Capas.class.getName());
        }
    }

    public void capaSeleccionada2() {
        /*HtmlCommandLink p = (HtmlCommandLink) event.getComponent();
         Capas capa = (Capas) p.getAttributes().get("capa");*/

        capaSeleccionada = capaEnCurso;
        //amposCapaSeleccionada = capa.getCamposList();
    }

    public void mostrarEditarCapa() {
        toModify = getCapaById(toEdit);
        toModifyBK = toModify.clonar();
        setMostrarPopUpEditarCapa(true);

    }

    public void mostrarEditarCampo() {
        campoEditar = getCampoById(campoEditarId);
        campoEditarBK = campoEditar.clonar();
        setMostrarPopUpEditarCampo(true);
        try {
            campoAModificarNumerico = DaoAnubis.esColumnaNumerica(campoEditar.getCapaId().getTabla(), campoEditar.getNombreColumna());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Capas getCapaById(int id) {
        for (Capas currentItem : capas) {
            if (id == currentItem.getId()) {
                return currentItem;
            }
        }
        return null;
    }

    private Campos getCampoById(int id) {
        if (capaSeleccionada != null) {
            for (Campos currentItem : capaSeleccionada.getCamposList()) {
                if (id == currentItem.getId()) {
                    return currentItem;
                }
            }
        }
        return null;
    }

    public void ocultarEditarCapa() {
        toModify.setNombre(toModifyBK.getNombre());
        toModify.setTabla(toModifyBK.getTabla());
        setMostrarPopUpEditarCapa(false);
    }

    public void previsualizarPopUp() {
        PrevisualizacionPopUp.mostrarPopUp(toModify.getNombre(), toModify.getTabla(), toModify.getTemplatePopUp());
        //List<String> campos = new LinkedList<>();
//        for (Campos c: toModify.getCamposList()){
//            campos.add(c.getNombreColumna());
//        }

        /*try {

         String tabla = toModify.getTabla();
         List<String[]> nombresCampos = DaoAnubis.getColumnsFromTable(tabla);
         for (String[] nombreCampo : nombresCampos) {
         campos.add(nombreCampo[0]);
         }
         HashMap<String, String> hash = DaoAnubis.obtenerValoresRandom(tabla, campos);
         String html = 
         for (String aRemplazar : hash.keySet()) {
         html = html.replaceAll("<#" + aRemplazar + ">", hash.get(aRemplazar));
         }
         String htmlPopUp = "<div class=\"black_overlay\" ></div>"
         + "<div  class=\"olPopupCloseBox\" onclick=\"cerrarPopUp()\" ></div>"
         + "<div class=\"olFramedCloudPopupContent\">"
         + "<fieldset>"
         + "<legend>" + toModify.getNombre() + "</legend>"
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
         */
    }

    public void modificarCapa() {
        try {
            if (toModify != null && !"".equals(toModify.getNombre())) {
                if("".equals(toModify.getMetadato())){
                    toModify.setMetadato(null);
                }
                HibernateDao.update(toModify);
                setMostrarPopUpEditarCapa(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarCampo() {
        try {
            if (campoEditar != null) {
                HibernateDao.update(campoEditar);
                setMostrarPopUpEditarCampo(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ocultarEditarCampo() {
        setMostrarPopUpEditarCampo(false);
        campoEditar.setCriterioBusqueda(campoEditarBK.getCriterioBusqueda());
        campoEditar.setEtiqueta(campoEditarBK.getEtiqueta());
        campoEditarBK = null;
    }

    public void resetearCapaSeleccionada() {
        capaSeleccionada = null;
    }
}
