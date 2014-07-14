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

import com.icesoft.faces.context.effects.Effect;
import dao.DaoAnubis;
import dao.HibernateDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import model.Bases;
import model.Campos;
import model.CapaFiltro;
import model.Capas;
import model.PerfilBase;
import model.PerfilCampos;
import model.PerfilPlugins;
import model.Perfiles;
import model.PerfilesCapas;
import model.Plugins;
import utils.JSFUtils;

/**
 *
 * @author Diego.Gonzalez
 */
@ViewScoped
@ManagedBean(name = "perfilbb")
public class PerfilBackinBean implements Serializable {

    static final int MODO_POP_UP_OCULTO = 0;
    static final int MODO_POP_UP_ALTA = 1;
    static final int MODO_POP_UP_EDICION = 2;
    private List<Perfiles> perfiles = new LinkedList<Perfiles>();
    private Perfiles perfilEnCurso = null;
    private Perfiles perfilAEliminar = null;
    private Boolean visibilidadCapas = false;
    private Boolean visibilidadBases = false;
    private Boolean visibilidadCampos = false;
    private Boolean visibilidadFiltros = false;
    private PerfilesCapas perfilCapaAEliminar = null;
    private List<Capas> capasDisponibles = null;
    private List<Object[]> capasDisponiblesParaFiltro = null;
    private Capas capaAAgregar = null;
    private PerfilesCapas perfilCapaEnCurso = null;
    private int modoPopUpCrearEditarPerfil = 0;
    private Perfiles perfilACrearEditar = new Perfiles();
    private Effect efectoTablas = null;
    private Effect efectoCampos = null;
    private int modoPopUpModificarCampo = 0;
    private PerfilCampos campoEnEdicion = null;
    private PerfilCampos campoEnEdicionBackUp = null;
    private List criteriosDeBusquedaNumericos = null;
    private List criteriosDeBusquedaTexto = null;
    private PerfilesCapas capaAOrdenar = null;
    private Boolean verPlugins = false;
    private List<Plugins> pluginsDisponibles = new LinkedList<>();
    private List<Object[]> plugins = new ArrayList<>();
    private Object[] pluginSeleccionado;
    private List<Bases> basesDisponibles = new LinkedList<>();
    private List<Object[]> bases = new ArrayList<>();
    private Object[] baseSeleccionada;
    private Object[] filtroSeleccionado;
    private List<Object[]> basespordefecto = new ArrayList<>();
    private Object[] basepordefectoSeleccionada;
    private Boolean campoAModificarNumerico = true;
    private Object[] baseAOrdenar;
    private boolean mostrarErrorCapaBase;
    private boolean mostrarPopUpAgregarGrupoPerfil = false;
    private boolean mostrarPopUpEditarTemplatePerfil = false;

    public PerfilBackinBean() {

        inicializarComboCriterioDeBusqueda();
        perfiles = HibernateDao.obtenerPerfilesCompletos();
//        perfiles = (List) HibernateDao.findAll(Perfiles.class.getName());
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

    /**
     * Inicializa los valores iniciales.
     */
    private void inicializar() {
        perfilACrearEditar = null;
        visibilidadCapas = false;
        visibilidadCampos = false;
        perfilCapaAEliminar = null;
        perfilEnCurso = null;
        perfilAEliminar = null;
        perfilCapaEnCurso = null;
        capasDisponibles = null;
        capaAAgregar = null;
        modoPopUpCrearEditarPerfil = MODO_POP_UP_OCULTO;
        perfiles = HibernateDao.obtenerPerfilesCompletos();
        inicializarComboCriterioDeBusqueda();

    }

    public void modificarCampo() {
        modoPopUpModificarCampo = MODO_POP_UP_EDICION;
        campoEnEdicionBackUp = campoEnEdicion.clonar();
        try {
            campoAModificarNumerico = DaoAnubis.esColumnaNumerica(campoEnEdicion.getCampoId().getCapaId().getTabla(), campoEnEdicion.getNombreColumna());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aceptarModificarCampo() {
        HibernateDao.update(campoEnEdicion);
        campoEnEdicionBackUp = null;
        modoPopUpModificarCampo = MODO_POP_UP_OCULTO;
    }

    public void aceptarAgregarGrupoPerfil() {
        HibernateDao.update(perfilCapaEnCurso);
        perfilCapaEnCurso = null;
        mostrarPopUpAgregarGrupoPerfil = false;
    }

    public void aceptarEditarTemplatePerfil() {
        HibernateDao.update(perfilCapaEnCurso);
        perfilCapaEnCurso = null;
        mostrarPopUpEditarTemplatePerfil = false;
    }
    
    public void aceptarBusquedaCapaPerfil(){
        HibernateDao.update(perfilCapaEnCurso);        
    }

    public void editarPerfil() {
        seleccionarPerfil();
        perfilACrearEditar = perfilEnCurso;
        modoPopUpCrearEditarPerfil = MODO_POP_UP_EDICION;
    }

    public void editarFiltros() {
        visibilidadCampos = false;
        visibilidadFiltros = true;
        capasDisponiblesParaFiltro = new ArrayList<Object[]>();
        for (PerfilesCapas perfil : perfilEnCurso.getPerfilesCapasList()) {
            if (!perfil.getNombreCapa().equals(perfilCapaEnCurso.getNombreCapa())) {
                Object[] o = new Object[3];
                o[0] = perfil.getCapaId().getNombre();
                o[1] = false;
                o[2] = perfil;
                for (CapaFiltro filtro : perfilCapaEnCurso.getCapaFiltroList()) {
                    if (filtro.getIdCapaFiltro().getNombreCapa().equals(perfil.getCapaId().getNombre())) {
                        o[1] = true;
                        o[2] = filtro;
                        break;
                    }
                }
                capasDisponiblesParaFiltro.add(o);
            }
        }

    }

    public void guardarFiltroCapa(ValueChangeEvent event) {
        filtroSeleccionado = (Object[]) event.getComponent().getAttributes().get("filtro");
        if (filtroSeleccionado != null) {
            PhaseId phaseId = event.getPhaseId();
            if (phaseId.equals(PhaseId.ANY_PHASE)) {
                event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
                event.queue();
                return;
            } else if (!phaseId.equals(PhaseId.UPDATE_MODEL_VALUES)) {
                return;
            }

            filtroSeleccionado[1] = event.getNewValue();
            if (filtroSeleccionado[1] == false) {
                CapaFiltro aBorrar = (CapaFiltro) filtroSeleccionado[2];
                int idPerfilCapa = ((CapaFiltro) filtroSeleccionado[2]).getIdCapaFiltro().getId(); //Id del PerfilCapa filtro
                perfilCapaEnCurso.removeFiltro(aBorrar);
                filtroSeleccionado[2] = HibernateDao.getById(PerfilesCapas.class.getName(), idPerfilCapa);
                ((PerfilesCapas) filtroSeleccionado[2]).removeFiltroDe(aBorrar);
                HibernateDao.delete(aBorrar.getClass().getName(), aBorrar.getId());
            } else {
                CapaFiltro nuevo = new CapaFiltro();
                nuevo.setIdCapa(perfilCapaEnCurso);
                nuevo.setIdCapaFiltro((PerfilesCapas) filtroSeleccionado[2]);
                perfilCapaEnCurso.agregarFiltro(nuevo);
                ((PerfilesCapas) filtroSeleccionado[2]).agregarFiltroDe(nuevo);
                filtroSeleccionado[2] = nuevo;
                HibernateDao.saveOrUpdate(nuevo);
                HibernateDao.update(perfilCapaEnCurso);
            }
        }
    }

    // <editor-fold desc=" ACCIONES " defaultstate="collapsed">
    public void previsualizarPopUp() {
        String template = perfilCapaEnCurso.getTemplatePopUp();
        String nombre = perfilCapaEnCurso.getCapaId().getNombre();
        String tabla = perfilCapaEnCurso.getCapaId().getTabla();
        PrevisualizacionPopUp.mostrarPopUp(nombre, tabla, template);
    }

    /**
     * Se invoca desde la tabla de perfiles, muestra y crea las tablas de las
     * capas
     */
    public void seleccionarPerfil() {
        verPlugins = false;
        visibilidadBases = false;
        plugins.clear();
        visibilidadCapas = true;
        cargarCapasDisponibles();
        visibilidadCampos = false;
        perfilCapaEnCurso = null;
        if (perfilEnCurso.getPerfilesCapasList() != null) {
            Collections.sort((List) perfilEnCurso.getPerfilesCapasList());
            int i = 0;
            for (PerfilesCapas pc : perfilEnCurso.getPerfilesCapasList()) {
                pc.setOrden(i);
                i++;
            }
            Collections.reverse((List) perfilEnCurso.getPerfilesCapasList());
        }
    }

    private void swapOrden(PerfilesCapas a, PerfilesCapas b) {
        if (a == null || b == null) {
            return;
        }
        Integer aux = a.getOrden();
        a.setOrden(b.getOrden());
        b.setOrden(aux);
    }

    private void swapOrdenBase(PerfilBase a, PerfilBase b) {
        if (a == null || b == null) {
            return;
        }
        Integer aux = a.getOrden();
        a.setOrden(b.getOrden());
        b.setOrden(aux);
    }

    private PerfilesCapas obtenerPerfilCapaPorOrden(Integer orden) {
        for (PerfilesCapas c : perfilEnCurso.getPerfilesCapasList()) {
            if (c.getOrden().equals(orden)) {
                return c;
            }
        }
        return null;
    }

    private PerfilBase obtenerPerfilBasePorOrden(Integer orden) {
        for (PerfilBase c : perfilEnCurso.getPerfilBaseList()) {
            if (c.getOrden().equals(orden)) {
                return c;
            }
        }
        return null;
    }

    public void subirOrdenCapa() {
        ordenarCapasDelPerfilEnCurso();
        if (capaAOrdenar.getOrden().equals(perfilEnCurso.getPerfilesCapasList().size() - 1)) {
            return;
        }
        swapOrden(capaAOrdenar, obtenerPerfilCapaPorOrden(capaAOrdenar.getOrden() + 1));
        HibernateDao.update(perfilEnCurso);
        ordenarCapasDelPerfilEnCurso();
    }

    public void subirOrdenBase() {
        PerfilBase base = (PerfilBase) baseAOrdenar[3];
        ordenarBasesDelPerfilEnCurso();
        if (base.getOrden().equals(perfilEnCurso.getPerfilBaseList().size() - 1)) {
            return;
        }
        swapOrdenBase(base, obtenerPerfilBasePorOrden(base.getOrden() + 1));


        HibernateDao.update(perfilEnCurso);
        ordenarBasesDelPerfilEnCurso();

        editarBases();
    }

    public void bajarOrdenCapa() {
        ordenarCapasDelPerfilEnCurso();
        if (capaAOrdenar.getOrden().equals(0)) {
            return;
        }
        swapOrden(capaAOrdenar, obtenerPerfilCapaPorOrden(capaAOrdenar.getOrden() - 1));
        HibernateDao.update(perfilEnCurso);
        ordenarCapasDelPerfilEnCurso();
    }

    public void bajarOrdenBase() {
        PerfilBase base = (PerfilBase) baseAOrdenar[3];
        ordenarBasesDelPerfilEnCurso();
        if (base.getOrden().equals(0)) {
            return;
        }
        swapOrdenBase(base, obtenerPerfilBasePorOrden(base.getOrden() - 1));


        HibernateDao.update(perfilEnCurso);
        Collections.sort((List) perfilEnCurso.getPerfilBaseList());
        Collections.reverse((List) perfilEnCurso.getPerfilBaseList());

        editarBases();
    }

    public void agregarPerfil() {
        perfilACrearEditar = new Perfiles();
        modoPopUpCrearEditarPerfil = MODO_POP_UP_ALTA;

    }

    public void aceptarAgregarEditarPerfil() {
        if (HibernateDao.getPerfilPorNombre(perfilACrearEditar.getNombre()) != null) {
            JSFUtils.mostrarError("Ya existe un perfil con el mismo nombre");
            return;
        }
        Perfiles p = null;
        if (modoPopUpCrearEditarPerfil == MODO_POP_UP_ALTA) {
            p = (Perfiles) HibernateDao.saveOrUpdate(perfilACrearEditar);
        } else {
            HibernateDao.update(perfilACrearEditar);
            p = perfilACrearEditar;

        }
        inicializar();
        perfilEnCurso = p;
        seleccionarPerfil();
        modoPopUpCrearEditarPerfil = MODO_POP_UP_OCULTO;

    }

    public void cancelarAgregarEditarPerfil() {
        perfilACrearEditar = null;
        modoPopUpCrearEditarPerfil = MODO_POP_UP_OCULTO;
    }

    /**
     * Se invoca desde la tabla de perfiles
     */
    public void eliminarPerfil() {
        if (perfilAEliminar != null) {
            if (perfilEnCurso == perfilAEliminar) {
                visibilidadCapas = false;
            }
            HibernateDao.delete(perfilAEliminar.getClass().getName(), perfilAEliminar.getId());
            perfiles.remove(perfilAEliminar);
        }
        inicializar();
    }

    public void editarPlugin() {
        plugins.clear();
        verPlugins = true;
        visibilidadCapas = true;
//        pluginsDisponibles = HibernateDao.findAll(Plugins.class.getName());
        pluginsDisponibles = HibernateDao.obtenerPluginsCompletos();
        for (Plugins plugin : pluginsDisponibles) {
            PerfilPlugins p = perfilEnCurso.getPlugin(plugin.getId());
            Object[] fila = new Object[4];
            if (p != null) {
                fila[0] = true;
                fila[1] = plugin.getNombre();
                fila[2] = plugin.getDescripcion();
                fila[3] = p;
            } else {
                fila[0] = false;
                fila[1] = plugin.getNombre();
                fila[2] = plugin.getDescripcion();
                fila[3] = plugin;
            }
            plugins.add(fila);
        }
    }

    public void editarBases() {
        bases.clear();
        visibilidadBases = true;
        verPlugins = false;
        visibilidadCapas = true;

        basesDisponibles = HibernateDao.obtenerBasesCompletas();//HibernateDao.findAll(Bases.class.getName());

        for (Bases base : basesDisponibles) {
            PerfilBase p = perfilEnCurso.getBase(base.getId());
            Object[] fila = new Object[6];
            if (p != null) {
                fila[0] = true;
                fila[3] = p;
                fila[5] = p.getOrden();
            } else {
                fila[0] = false;
                fila[3] = base;
                fila[5] = 0;
            }
            fila[1] = base.getEtiqueta();
            fila[2] = base.getCapas();
            fila[4] = true;

            bases.add(fila);
        }

        List<Object[]> aux = copiaLimpia(bases);
        bases.clear();
        for (Object[] ob : aux) {
            if ((boolean) ob[0]) {
                bases.add(ob);
            }
        }
        for (Object[] ob : bases) {
            aux.remove(ob);
        }
        for (Object[] ob : aux) {
            bases.add(ob);
        }

    }

    public void eliminarCapaPerfil() {
        perfilEnCurso.getPerfilesCapasList().remove(perfilCapaAEliminar);

        perfilCapaAEliminar = null;
        cargarCapasDisponibles();
        int i = perfilEnCurso.getPerfilesCapasList().size() - 1;
        for (PerfilesCapas pc : perfilEnCurso.getPerfilesCapasList()) {
            pc.setOrden(i);
            i--;
        }
        HibernateDao.update(perfilEnCurso);

        ordenarCapasDelPerfilEnCurso();
        visibilidadCampos = false;
        perfilCapaEnCurso = null;
    }

    public void agregarCapaPerfil() {
        PerfilesCapas p = new PerfilesCapas();
        p.asociarPerfilCapa(perfilEnCurso, capaAAgregar);
        p.setVisible(true);
        p.setOrden(perfilEnCurso.getPerfilesCapasList().size() - 1);
        p.setSoloBuscable(false);
        p.setBuscable(true);
        for (Campos campo : capaAAgregar.getCamposList()) {
            PerfilCampos campoNuevo = new PerfilCampos();
            campoNuevo.setCriterioBusqueda(campo.getCriterioBusqueda());
            campoNuevo.setEtiqueta(campo.getEtiqueta());
            campoNuevo.setCampoId(campo);
            campoNuevo.setPerfilCapaId(p);
            p.agregarCampo(campoNuevo);
        }
        HibernateDao.update(perfilEnCurso);
        cargarCapasDisponibles();
        /*int i = 0;
         for (PerfilesCapas pc : perfilEnCurso.getPerfilesCapasList()) {
         pc.setOrden(i);
         i++;
         }*/
        //HibernateDao.update(perfilEnCurso);
        //ordenarCapasDelPerfilEnCurso();

        visibilidadCampos = true;
        perfilCapaEnCurso = p;
    }

    public void guardarCapaEnCurso(ValueChangeEvent event) {
        PhaseId phaseId = event.getPhaseId();
        if (phaseId.equals(PhaseId.ANY_PHASE)) {
            event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
            event.queue();
            return;
        } else if (!phaseId.equals(PhaseId.UPDATE_MODEL_VALUES)) {
            return;
        }
        HibernateDao.update(perfilEnCurso);
        ordenarCapasDelPerfilEnCurso();
    }

    public void perfilCapaSeleccionado() {
        visibilidadCampos = true;
        visibilidadFiltros = false;
        //efectoCampos = new BlindDown();
    }

    public void cancelarEditarCampo() {
        campoEnEdicion.setCriterioBusqueda(campoEnEdicionBackUp.getCriterioBusqueda());
        campoEnEdicion.setEtiqueta(campoEnEdicionBackUp.getEtiqueta());
        campoEnEdicionBackUp = null;
        modoPopUpModificarCampo = MODO_POP_UP_OCULTO;
    }

    public void habilitacionPlugin(ValueChangeEvent event) {
        pluginSeleccionado = (Object[]) event.getComponent().getAttributes().get("plugin");
        if (pluginSeleccionado[0] == true) {
            PerfilPlugins aBorrar = (PerfilPlugins) pluginSeleccionado[3];
            int id = ((PerfilPlugins) pluginSeleccionado[3]).getId();
            int idPlugin = ((PerfilPlugins) pluginSeleccionado[3]).getPluginId().getId();
            perfilEnCurso.removerPlugin(aBorrar);
//            pluginSeleccionado[3] = HibernateDao.getById(Plugins.class.getName(), idPlugin);
            ((PerfilPlugins) pluginSeleccionado[3]).getPluginId().removePlugin(aBorrar);
            HibernateDao.update(pluginSeleccionado[3]);
            HibernateDao.update(perfilEnCurso);
            HibernateDao.delete(aBorrar.getClass().getName(), aBorrar.getId());

        } else {
            PerfilPlugins nuevo = new PerfilPlugins();
            nuevo.setPerfilCapaId(perfilEnCurso);
            perfilEnCurso.agregarPlugin(nuevo);
            ((Plugins) pluginSeleccionado[3]).agregarPlugin(nuevo);
            nuevo.setPluginId((Plugins) pluginSeleccionado[3]);
            nuevo = (PerfilPlugins) HibernateDao.saveOrUpdate(nuevo);
            pluginSeleccionado[3] = nuevo;
        }
    }

    public void habilitacionBase(ValueChangeEvent event) {


        baseSeleccionada = (Object[]) event.getComponent().getAttributes().get("base");
        if (baseSeleccionada != null) {
            PhaseId phaseId = event.getPhaseId();
            if (phaseId.equals(PhaseId.ANY_PHASE)) {
                event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
                event.queue();
                return;
            } else if (!phaseId.equals(PhaseId.UPDATE_MODEL_VALUES)) {
                return;
            }


            baseSeleccionada[0] = event.getNewValue();
            if (baseSeleccionada[0] == false) {

                PerfilBase aBorrar = (PerfilBase) baseSeleccionada[3];
                int id = ((PerfilBase) baseSeleccionada[3]).getId();
                int idBase = ((PerfilBase) baseSeleccionada[3]).getBaseId().getId();
                perfilEnCurso.removeBase(aBorrar);
//                baseSeleccionada[3] = HibernateDao.getById(Bases.class.getName(), idBase);
                ((PerfilBase) baseSeleccionada[3]).getBaseId().removeBase(aBorrar);
                HibernateDao.delete(aBorrar.getClass().getName(), aBorrar.getId());
//                HibernateDao.delete(aBorrar);

                if (perfilEnCurso.getPerfilBaseList().isEmpty()) {
                    mostrarErrorCapaBase();
                    return;
                }

            } else {
                PerfilBase nuevo = new PerfilBase();
                nuevo.setPerfilId(perfilEnCurso);
                nuevo.setOrden(perfilEnCurso.getPerfilBaseList().size());
                baseSeleccionada[5] = perfilEnCurso.getPerfilBaseList().size();
                perfilEnCurso.agregarBase(nuevo);
                ((Bases) baseSeleccionada[3]).agregarBase(nuevo);
                nuevo.setBaseId((Bases) baseSeleccionada[3]);
                baseSeleccionada[3] = nuevo;

                HibernateDao.saveOrUpdate(nuevo);
                HibernateDao.update(perfilEnCurso);


            }

            int i = 0;
            for (PerfilBase pc : perfilEnCurso.getPerfilBaseList()) {
                pc.setOrden(i);
                i++;
            }
            ordenarBasesDelPerfilEnCurso();

            editarBases();
        }

        List<Object[]> aux = copiaLimpia(bases);
        bases.clear();
        for (Object[] ob : aux) {
            if ((boolean) ob[0]) {
                bases.add(ob);
            }
        }
        for (Object[] ob : bases) {
            aux.remove(ob);
        }
        for (Object[] ob : aux) {
            bases.add(ob);
        }

    }

    private List<Object[]> copiaLimpia(List<Object[]> bases) {
        List<Object[]> aux = new ArrayList<>();
        for (Object[] ob : bases) {
            Object[] nuevo = new Object[6];
            nuevo[0] = ob[0];
            nuevo[1] = ob[1];
            nuevo[2] = ob[2];
            nuevo[3] = ob[3];
            nuevo[4] = ob[4];
            nuevo[5] = ob[5];
            aux.add(nuevo);
        }
        return aux;
    }

    private void mostrarErrorCapaBase() {
        mostrarErrorCapaBase = true;
    }

    public void cerrarErrorCapaBase() {
        mostrarErrorCapaBase = false;
    }

    public void cancelarAgregarGrupoPerfil() {
        mostrarPopUpAgregarGrupoPerfil = false;
    }

    public void mostrarAgregarGrupoPerfil() {
        mostrarPopUpAgregarGrupoPerfil = true;
    }

    public void cancelarEditarTemplatePopUpPerfil() {
        mostrarPopUpEditarTemplatePerfil = false;
    }

    public void mostrarEditarTemplatePopUpPerfil() {
        mostrarPopUpEditarTemplatePerfil = true;
    }

    // </editor-fold>
    // <editor-fold desc=" FUNCIONES AUXILIARES " defaultstate="collapsed">
    private void cargarCapasDisponibles() {
        capasDisponibles = HibernateDao.obtenerCapasCompletas();
//        capasDisponibles = HibernateDao.findAll(Capas.class.getName());
        for (PerfilesCapas pc : perfilEnCurso.getPerfilesCapasList()) {
            Capas p = pc.getCapaId();
            String hola = p.getNombre();
            capasDisponibles.remove(pc.getCapaId());
        }
    }

    private void ordenarCapasDelPerfilEnCurso() {
        Collections.sort((List) perfilEnCurso.getPerfilesCapasList());
        Collections.reverse((List) perfilEnCurso.getPerfilesCapasList());
    }

    private void reasignarOrdenCapasPerfil() {
        /*Collections.reverse((List) perfilEnCurso.getPerfilBaseList());
         Collections.reverse((List) perfilEnCurso.getPerfilBaseList());
         int i = 0;
         for( PerfilesCapas pc : perfilEnCurso.getPerfilesCapasList()){
         pc.setOrden(i);
         i++;
         }
         Collections.reverse((List) perfilEnCurso.getPerfilBaseList());*/
    }

    private void ordenarBasesDelPerfilEnCurso() {
        Collections.sort((List) perfilEnCurso.getPerfilBaseList());
        Collections.reverse((List) perfilEnCurso.getPerfilBaseList());
    }

// </editor-fold>    
    // <editor-fold desc=" GETTERS Y SETTERS " defaultstate="collapsed">
    public boolean isMostrarPopUpAgregarGrupoPerfil() {
        return mostrarPopUpAgregarGrupoPerfil;
    }

    public void setMostrarPopUpAgregarGrupoPerfil(boolean mostrarPopUpAgregarGrupoPerfil) {
        this.mostrarPopUpAgregarGrupoPerfil = mostrarPopUpAgregarGrupoPerfil;
    }

    public Boolean getVisibilidadFiltros() {
        return visibilidadFiltros;
    }

    public void setVisibilidadFiltros(Boolean visibilidadFiltros) {
        this.visibilidadFiltros = visibilidadFiltros;
    }

    public List<Object[]> getCapasDisponiblesParaFiltro() {
        return capasDisponiblesParaFiltro;
    }

    public void setCapasDisponiblesParaFiltro(List<Object[]> capasDisponiblesParaFiltro) {
        this.capasDisponiblesParaFiltro = capasDisponiblesParaFiltro;
    }

    public boolean isMostrarErrorCapaBase() {
        return mostrarErrorCapaBase;
    }

    public void setMostrarErrorCapaBase(boolean mostrarErrorCapaBase) {
        this.mostrarErrorCapaBase = mostrarErrorCapaBase;
    }

    public Object[] getBaseAOrdenar() {
        return baseAOrdenar;
    }

    public void setBaseAOrdenar(Object[] baseAOrdenar) {
        this.baseAOrdenar = baseAOrdenar;
    }

    public boolean isMostrarPopUpEditarTemplatePerfil() {
        return mostrarPopUpEditarTemplatePerfil;
    }

    public void setMostrarPopUpEditarTemplatePerfil(boolean mostrarPopUpEditarTemplatePerfil) {
        this.mostrarPopUpEditarTemplatePerfil = mostrarPopUpEditarTemplatePerfil;
    }

    public List<Object[]> getBasespordefecto() {
        return basespordefecto;
    }

    public void setBasespordefecto(List<Object[]> basespordefecto) {
        this.basespordefecto = basespordefecto;
    }

    public Object[] getBasepordefectoSeleccionada() {
        return basepordefectoSeleccionada;
    }

    public void setBasepordefectoSeleccionada(Object[] basepordefectoSeleccionada) {
        this.basepordefectoSeleccionada = basepordefectoSeleccionada;
    }

    public List<Bases> getBasesDisponibles() {
        return basesDisponibles;
    }

    public void setBasesDisponibles(List<Bases> basesDisponibles) {
        this.basesDisponibles = basesDisponibles;
    }

    public List<Object[]> getBases() {
        return bases;
    }

    public void setBases(List<Object[]> bases) {
        this.bases = bases;
    }

    public Object[] getBaseSeleccionada() {
        return baseSeleccionada;
    }

    public void setBaseSeleccionada(Object[] baseSeleccionada) {
        this.baseSeleccionada = baseSeleccionada;
    }

    public Boolean getVisibilidadBases() {
        return visibilidadBases;
    }

    public void setVisibilidadBases(Boolean visibilidadBases) {
        this.visibilidadBases = visibilidadBases;
    }

    public List<Object[]> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Object[]> plugins) {
        this.plugins = plugins;
    }

    public List<Perfiles> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<Perfiles> perfiles) {
        this.perfiles = perfiles;
    }

    public Boolean getVerPlugins() {
        return verPlugins;
    }

    public void setVerPlugins(Boolean verPlugins) {
        this.verPlugins = verPlugins;
    }

    public List<Plugins> getPluginsDisponibles() {
        return pluginsDisponibles;
    }

    public void setPluginsDisponibles(List<Plugins> pluginsDisponibles) {
        this.pluginsDisponibles = pluginsDisponibles;
    }

    public PerfilesCapas getCapaAOrdenar() {
        return capaAOrdenar;
    }

    public void setCapaAOrdenar(PerfilesCapas capaAOrdenar) {
        this.capaAOrdenar = capaAOrdenar;
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

    public Boolean getCampoAModificarNumerico() {
        return campoAModificarNumerico;
    }

    public void setCampoAModificarNumerico(Boolean campoAModificarNumerico) {
        this.campoAModificarNumerico = campoAModificarNumerico;
    }

    public int getModoPopUpModificarCampo() {
        return modoPopUpModificarCampo;
    }

    public void setModoPopUpModificarCampo(int modoPopUpModificarCampo) {
        this.modoPopUpModificarCampo = modoPopUpModificarCampo;
    }

    public PerfilCampos getCampoEnEdicion() {
        return campoEnEdicion;
    }

    public void setCampoEnEdicion(PerfilCampos campoEnEdicion) {
        this.campoEnEdicion = campoEnEdicion;
    }

    public int getModoPopUpCrearEditarPerfil() {
        return modoPopUpCrearEditarPerfil;
    }

    public void setModoPopUpCrearEditarPerfil(int modoPopUpCrearEditarPerfil) {
        this.modoPopUpCrearEditarPerfil = modoPopUpCrearEditarPerfil;
    }

    public Perfiles getPerfilACrearEditar() {
        return perfilACrearEditar;
    }

    public void setPerfilACrearEditar(Perfiles perfilACrearEditar) {
        this.perfilACrearEditar = perfilACrearEditar;
    }

    public Capas getCapaAAgregar() {
        return capaAAgregar;
    }

    public void setCapaAAgregar(Capas capaAAgregar) {
        this.capaAAgregar = capaAAgregar;
    }

    public PerfilesCapas getPerfilCapaAEliminar() {
        return perfilCapaAEliminar;
    }

    public void setPerfilCapaAEliminar(PerfilesCapas perfilCapaAEliminar) {
        this.perfilCapaAEliminar = perfilCapaAEliminar;
    }

    public Boolean getVisibilidadCapas() {
        return visibilidadCapas;
    }

    public void setVisibilidadCapas(Boolean visibilidadCapas) {
        this.visibilidadCapas = visibilidadCapas;
    }

    public Perfiles getPerfilAEliminar() {
        return perfilAEliminar;
    }

    public void setPerfilAEliminar(Perfiles perfilAEliminar) {
        this.perfilAEliminar = perfilAEliminar;
    }

    public List<Capas> getCapasDisponibles() {
        return capasDisponibles;
    }

    public void setCapasDisponibles(List<Capas> capasDisponibles) {
        this.capasDisponibles = capasDisponibles;
    }

    public Boolean getVisibilidadCampos() {
        return visibilidadCampos;
    }

    public void setVisibilidadCampos(Boolean visibilidadCampos) {
        this.visibilidadCampos = visibilidadCampos;
    }

    public PerfilesCapas getPerfilCapaEnCurso() {
        return perfilCapaEnCurso;
    }

    public void setPerfilCapaEnCurso(PerfilesCapas perfilCapaEnCurso) {
        this.perfilCapaEnCurso = perfilCapaEnCurso;
    }

    public Effect getEfectoTablas() {
        return efectoTablas;
    }

    public void setEfectoTablas(Effect efectoTablas) {
        this.efectoTablas = efectoTablas;
    }

    public Effect getEfectoCampos() {
        return efectoCampos;
    }

    public void setEfectoCampos(Effect efectoCampos) {
        this.efectoCampos = efectoCampos;
    }

    public Perfiles getPerfilEnCurso() {
        return perfilEnCurso;
    }

    public void setPerfilEnCurso(Perfiles perfilEnCurso) {
        this.perfilEnCurso = perfilEnCurso;
    }

    public Object[] getPluginSeleccionado() {
        return pluginSeleccionado;
    }

    public void setPluginSeleccionado(Object[] pluginSeleccionado) {
        this.pluginSeleccionado = pluginSeleccionado;
    }
    // </editor-fold>
}
