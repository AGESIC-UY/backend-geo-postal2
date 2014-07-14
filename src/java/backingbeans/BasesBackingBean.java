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
import dao.HibernateDao;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.BaseCapa;
import model.Bases;
import model.Campos;
import model.Capas;
import net.bull.javamelody.JdbcDriver;
import utils.JSFUtils;

/**
 *
 * @author Camila.Riveron
 */
@ManagedBean(name = "bases")
@ViewScoped
public class BasesBackingBean implements Serializable {

    private List<Bases> bases;
    private Boolean mostrarPopUpAgregarBase;
    private Integer lastSelectedRow;
    private RowSelector rowselector;
    private int maxRowsTablas;
    private int maxRowsBases;
    private Bases baseSeleccionada;
    private Bases baseSeleccionada2;
    private Bases toModify;
    private Bases toModifyBK;
    private int toRemove;
    private int toEdit;
    private Campos campoEditar;
    private int campoEditarId;
    private Bases baseEnCurso;
    private List criteriosDeBusquedaNumericos = null;
    private List criteriosDeBusquedaTexto = null;
    private String nombreBase;
    private boolean mostrarPopUpEditarBase;
    private boolean visibilidadCapas;
    private Capas capaAAgregar = null;
    private BaseCapa capaAOrdenar;
    private BaseCapa capaAEliminar;
    private List<Capas> capasDisponibles;

    public BasesBackingBean() {
        try {
            bases = HibernateDao.obtenerBasesCompletas();
            mostrarPopUpAgregarBase = false;
            maxRowsTablas = 6;
            maxRowsBases = 6;
            toModify = new Bases();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void ocultarAgregarBase() {
        setMostrarPopUpAgregarBase(false);
        nombreBase = "";
    }

    public void mostrarAgregarBase() {
        setMostrarPopUpAgregarBase(true);
        nombreBase = "";
    }

    public void basesSelectionListener(RowSelectorEvent event) {
        for (Bases c : bases) {
            c.setSelected(false);
        }

        bases.get(event.getRow()).setSelected(event.isSelected());
    }

    public void yesDeleteBase() {
        Bases removeBase = getBaseById(toRemove);
        if (removeBase != null) {
            if (baseSeleccionada != null && baseSeleccionada.equals(removeBase)) {
                baseSeleccionada = null;
            }
            HibernateDao.delete(Bases.class.getName(), toRemove);
            bases = HibernateDao.obtenerBasesCompletas();
            visibilidadCapas = false;
        }
    }

    public void baseSeleccionada2() {
        baseSeleccionada = baseEnCurso;
    }

    private Bases getBaseById(int id) {
        for (Bases currentItem : bases) {
            if (id == currentItem.getId()) {
                return currentItem;
            }
        }
        return null;
    }

    public void agregarBase() {
        if (!nombreBase.isEmpty()) {
            if (HibernateDao.getBasePorNombre(nombreBase) != null) {
                JSFUtils.mostrarError("Ya existe una base con el mismo nombre");
                return;
            }
            try {
                Bases nuevaBase = new Bases();
                nuevaBase.setEtiqueta(nombreBase);
                HibernateDao.saveOrUpdate(nuevaBase);
                bases = HibernateDao.obtenerBasesCompletas();
                ocultarAgregarBase();
                nombreBase = "";

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void seleccionarBase() {
        visibilidadCapas = true;
        cargarCapasDisponibles();
        if (baseEnCurso.getBaseCapaList() != null) {
            Collections.sort((List) baseEnCurso.getBaseCapaList());
            int i = 0;
            for (BaseCapa pc : baseEnCurso.getBaseCapaList()) {
                pc.setOrden(i);
                i++;
            }
            Collections.reverse((List) baseEnCurso.getPerfilBaseList());
        }
        ordenarCapasDeBaseEnCurso();
    }

    public void ocultarEditarBase() {
        toModify.setEtiqueta(toModifyBK.getEtiqueta());
        setMostrarPopUpEditarBase(false);
    }

    public void mostrarEditarBase() {
        toModify = getBaseById(toEdit);
        toModifyBK = toModify.clonar();
        setMostrarPopUpEditarBase(true);
    }

    public void modificarBase() {
        try {
            if (toModify != null && !"".equals(toModify.getEtiqueta())) {
                HibernateDao.update(toModify);
                setMostrarPopUpEditarBase(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subirOrdenCapa() {
        ordenarCapasDeBaseEnCurso();
        if (capaAOrdenar.getOrden().equals(baseEnCurso.getBaseCapaList().size() - 1)) {
            return;
        }
        swapOrden(capaAOrdenar, obtenerCapaPorOrden(capaAOrdenar.getOrden() + 1));
//        for (BaseCapa bc: baseEnCurso.getBaseCapaList()){
//            HibernateDao.saveOrUpdate(bc);
//        }
        HibernateDao.update(baseEnCurso);
//        HibernateDao.saveOrUpdate(baseEnCurso);
        ordenarCapasDeBaseEnCurso();
    }

    public void bajarOrdenCapa() {
        ordenarCapasDeBaseEnCurso();
        if (capaAOrdenar.getOrden().equals(0)) {
            return;
        }
        swapOrden(capaAOrdenar, obtenerCapaPorOrden(capaAOrdenar.getOrden() - 1));
//        baseEnCurso.setEtiqueta("*"+ baseEnCurso.getEtiqueta());
//        for (BaseCapa bc: baseEnCurso.getBaseCapaList()){
//            HibernateDao.saveOrUpdate(bc);
//        }
        HibernateDao.update(baseEnCurso);
//        HibernateDao.saveOrUpdate(baseEnCurso);
        ordenarCapasDeBaseEnCurso();
    }

    private void ordenarCapasDeBaseEnCurso() {
        Collections.sort((List) baseEnCurso.getBaseCapaList());
        Collections.reverse((List) baseEnCurso.getBaseCapaList());
    }

    private void swapOrden(BaseCapa a, BaseCapa b) {
        if (a == null || b == null) {
            return;
        }
        Integer aux = a.getOrden();
        a.setOrden(b.getOrden());
        b.setOrden(aux);
    }

    private BaseCapa obtenerCapaPorOrden(Integer orden) {
        for (BaseCapa c : baseEnCurso.getBaseCapaList()) {
            if (c.getOrden().equals(orden)) {
                return c;
            }
        }
        return null;
    }

    public void eliminarCapa() {
        baseEnCurso.getBaseCapaList().remove(capaAEliminar);
        capaAEliminar.getBaseId().getBaseCapaList().remove(capaAEliminar);
        capaAEliminar.setBaseId(null); 
        capaAEliminar.getCapaId().getBaseCapaList().remove(capaAEliminar);
        capaAEliminar.setCapaId(null);
        HibernateDao.delete(BaseCapa.class.getName(), capaAEliminar.getId());
        HibernateDao.update(baseEnCurso);
        capaAEliminar = null;
        cargarCapasDisponibles();
        ordenarCapasDeBaseEnCurso();
    }

    private void cargarCapasDisponibles() {
        capasDisponibles = HibernateDao.obtenerCapasCompletas();
        for (BaseCapa pc : baseEnCurso.getBaseCapaList()) {
            Capas p = pc.getCapaId();
            Capas capa = pc.getCapaId();
            capasDisponibles.remove(capa);
        }
    }

    public void agregarCapa() {
        BaseCapa p = new BaseCapa();
        p.asociarPerfilCapa(baseEnCurso, capaAAgregar);
        p.setOrden(baseEnCurso.getBaseCapaList().size());
        Collections.sort(baseEnCurso.getBaseCapaList());
        int i = 0;
        for (BaseCapa pc : baseEnCurso.getBaseCapaList()) {
            pc.setOrden(i);
            i++;
        }
        HibernateDao.update(baseEnCurso);
        cargarCapasDisponibles();
        ordenarCapasDeBaseEnCurso();
    }

    public void resetearCapaSeleccionada() {
        baseSeleccionada = null;
    }

    // <editor-fold desc=" GETTERS Y SETTERS " defaultstate="collapsed">
    public List<Bases> getBases() {
        return bases;
    }

    public void setBases(List<Bases> bases) {
        this.bases = bases;
    }

    public Campos getCampoEditar() {
        return campoEditar;
    }

    public Capas getCapaAAgregar() {
        return capaAAgregar;
    }

    public void setCapaAAgregar(Capas capaAAgregar) {
        this.capaAAgregar = capaAAgregar;
    }

    public BaseCapa getCapaAEliminar() {
        return capaAEliminar;
    }

    public void setCapaAEliminar(BaseCapa capaAEliminar) {
        this.capaAEliminar = capaAEliminar;
    }

    public List<Capas> getCapasDisponibles() {
        return capasDisponibles;
    }

    public void setCapasDisponibles(List<Capas> capasDisponibles) {
        this.capasDisponibles = capasDisponibles;
    }

    public boolean isVisibilidadCapas() {
        return visibilidadCapas;
    }

    public void setVisibilidadCapas(boolean visibilidadCapas) {
        this.visibilidadCapas = visibilidadCapas;
    }

    public Bases getBaseSeleccionada2() {
        return baseSeleccionada2;
    }

    public void setBaseSeleccionada2(Bases baseSeleccionada2) {
        this.baseSeleccionada2 = baseSeleccionada2;
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

    public Bases getBaseEnCurso() {
        return baseEnCurso;
    }

    public void setBaseEnCurso(Bases capasEnCurso) {
        this.baseEnCurso = capasEnCurso;
    }

    public int getCampoEditarId() {
        return campoEditarId;
    }

    public void setCampoEditarId(int campoEditarId) {
        this.campoEditarId = campoEditarId;
    }

    public boolean isMostrarPopUpEditarBase() {
        return mostrarPopUpEditarBase;
    }

    public void setMostrarPopUpEditarBase(boolean mostrarPopUpEditar) {
        this.mostrarPopUpEditarBase = mostrarPopUpEditar;
    }

    public Bases getBaseSeleccionada() {
        return baseSeleccionada;
    }

    public void setBaseSeleccionada(Bases baseSeleccionada) {
        this.baseSeleccionada = baseSeleccionada;
    }

    public Bases getToModify() {
        return toModify;
    }

    public void setToModify(Bases toModify) {
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

    public int getMaxRowsBases() {
        return maxRowsBases;
    }

    public void setMaxRowsCapas(int maxRowsBases) {
        this.maxRowsBases = maxRowsBases;
    }

    public int getMaxRowsTablas() {
        return maxRowsTablas;
    }

    public BaseCapa getCapaAOrdenar() {
        return capaAOrdenar;
    }

    public void setCapaAOrdenar(BaseCapa capaAOrdenar) {
        this.capaAOrdenar = capaAOrdenar;
    }

    public void setMaxRowsTablas(int maxRowsTablas) {
        this.maxRowsTablas = maxRowsTablas;
    }

    public Integer getLastSelectedRow() {
        return lastSelectedRow;
    }

    public String getNombreBase() {
        return nombreBase;
    }

    public void setNombreBase(String nombreBase) {
        this.nombreBase = nombreBase;
    }

    public void setLastSelectedRow(Integer lastSelectedColumn) {
        this.lastSelectedRow = lastSelectedColumn;
    }

    public RowSelector getRowselector() {
        return rowselector;
    }

    public void setRowselector(RowSelector rowselector) {
        this.rowselector = rowselector;
    }

    public List<Bases> getCapas() {
        return bases;
    }

    public Boolean getMostrarPopUpAgregarBase() {
        return mostrarPopUpAgregarBase;
    }

    public void setMostrarPopUpAgregarBase(Boolean mostrarPopUpAgregarBase) {
        this.mostrarPopUpAgregarBase = mostrarPopUpAgregarBase;
    }
    // </editor-fold>
}
