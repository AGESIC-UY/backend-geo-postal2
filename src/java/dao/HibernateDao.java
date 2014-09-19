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
package dao;

import backingbeans.HibernateSessionBean;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import login.MD5Login;
import model.BaseCapa;
import model.Bases;
import model.Campos;
import model.CapaFiltro;
import model.Capas;
import model.ContactoVisualizador;
import model.PerfilBase;
import model.PerfilCampos;
import model.PerfilPlugins;
import model.Perfiles;
import model.PerfilesCapas;
import model.Plugins;
import model.Usuarios;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.proxy.HibernateProxyHelper;
import org.hibernate.spatial.GeometryType;
import org.hibernate.type.StringType;
import servicios.Consulta;
import servicios.ConsultaCampo;
import servicios.ConsultaFiltro;
import servicios.ConsultaModel;

/**
 *
 * @author Diego.Gonzalez
 */
public class HibernateDao {

//    public static HibernateDao instance = null;
    private static Session session = null;
    private static HibernateSessionBean sessionBean = null;

    public static Object getBasePorNombre(String nombreBase) {
        Session s = HibernateDao.getSession();
        s.getTransaction().begin();
        Query query = s.createQuery("SELECT p from Bases p WHERE p.etiqueta = ?");
        query.setString(0, nombreBase);
        List<Capas> result = (List<Capas>) query.list();
        s.getTransaction().commit();
        s.close();
//        s.disconnect();
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public static Usuarios getUser(String usuario, String contraseña) {
        MD5Login md5 = new MD5Login();
        String res = "";
        String encriptada = md5.hash(contraseña);
        Usuarios user = null;

        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT u from Usuarios u WHERE u.nombre = ? AND u.password = ?");
            query.setString(0, usuario);
            query.setString(1, encriptada);

            List<Usuarios> result = (List<Usuarios>) query.list();

            if (result.size() > 0) {
                user = result.get(0);
            }

            s.getTransaction().commit();
            s.close();
//            s.disconnect();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
//            s.disconnect();
            return null;
        }
    }

    public static List<Bases> obtenerBasesCompletas() {
        Session s = HibernateDao.getSession();
        s.setDefaultReadOnly(true);
        s.getTransaction().begin();
        Query q = s.createQuery("select p from Bases p");
        List<Bases> result = (List<Bases>) q.list();
        /**
         * Increiblemente hibernate no deja hacer fetch de varias collecciones,
         * entonces se mapea con fetch lazy y se accede a los elementos de las
         * colecciones para traerlos.
         */
        for (Bases b : result) {
            for (BaseCapa bc : b.getBaseCapaList()) {
                bc.getCapaId().getId();
                for (BaseCapa bcc : bc.getCapaId().getBaseCapaList()) {
                    bcc.getCapaId().getId();
                }
            }
            for (PerfilBase pb : b.getPerfilBaseList()) {
                pb.getOrden();
            }
        }
        s.getTransaction().commit();
        s.close();
//        s.disconnect();
        return result;
    }

    public static List<Capas> obtenerCapasCompletas() {
        Session s = HibernateDao.getSession();
        s.setDefaultReadOnly(true);
        s.getTransaction().begin();
        Query q = s.createQuery("select p from Capas p");
        List<Capas> result = (List<Capas>) q.list();
        /**
         * Increiblemente hibernate no deja hacer fetch de varias collecciones,
         * entonces se mapea con fetch lazy y se accede a los elementos de las
         * colecciones para traerlos.
         */
        for (Capas c : result) {
            for (BaseCapa bc : c.getBaseCapaList()) {
                bc.getCapaId();
            }
            for (PerfilesCapas bc : c.getPerfilesCapasList()) {
                bc.getCapaId().getId();
            }
        }
        s.getTransaction().commit();
        s.close();
//        s.disconnect();
        return result;
    }

    public static List<Perfiles> obtenerPerfilesCompletos() {
        Session s = HibernateDao.getSession();
        s.setDefaultReadOnly(true);
        s.getTransaction().begin();
        Query q = s.createQuery("select p from Perfiles p");
        List<Perfiles> result = (List<Perfiles>) q.list();
        /**
         * Increiblemente hibernate no deja hacer fetch de varias collecciones,
         * entonces se mapea con fetch lazy y se accede a los elementos de las
         * colecciones para traerlos.
         */
        for (Perfiles p : result) {
            for (PerfilPlugins pp : p.getPerfilPluginsList()) {
                pp.getPluginId().getId();
                for (PerfilPlugins ppl : pp.getPluginId().getPerfilPluginsList()) {
                    ppl.getPluginId().getId();
                }

            }
            for (PerfilBase pb : p.getPerfilBaseList()) {
                pb.getBaseId().getId();
                for (PerfilBase pb2 : pb.getBaseId().getPerfilBaseList()) {
                    pb2.getOrden();
                }
            }
            for (PerfilesCapas pc : p.getPerfilesCapasList()) {
                pc.getCapaId().getId();
                for (PerfilCampos pe : pc.getPerfilCamposList()) {
                    pe.getEtiqueta();
                }
                for (CapaFiltro cf : pc.getCapaFiltroList()) {
                    cf.getIdCapaFiltro().getNombreCapa();
                }
                for (CapaFiltro cf : pc.getCapaFiltroList1()) {
                    cf.getIdCapaFiltro().getNombreCapa();
                }
            }
        }
        s.getTransaction().commit();
        s.close();
//        s.disconnect();
        return result;
    }

    public static List<Plugins> obtenerPluginsCompletos() {
        Session s = HibernateDao.getSession();
        s.setDefaultReadOnly(true);
        s.getTransaction().begin();
        Query q = s.createQuery("select p from Plugins p");
        List<Plugins> result = (List<Plugins>) q.list();
        /**
         * Increiblemente hibernate no deja hacer fetch de varias collecciones,
         * entonces se mapea con fetch lazy y se accede a los elementos de las
         * colecciones para traerlos.
         */
        for (Plugins p : result) {
            for (PerfilPlugins pp : p.getPerfilPluginsList()) {
                pp.getId();
                pp.getPluginId().getId();
            }
        }
        s.getTransaction().commit();
        s.close();
//        s.disconnect();
        return result;
    }

    protected HibernateDao() {
    }

//    public static HibernateDao getInstance() {
//        if (instance == null) {
//            instance = new HibernateDao();
//        }
//        return instance;
//    }
    /**
     * * Busca en la base las tablas con componentes geográficos **
     */
    public static List<String> getTablasDisponibles() {
        Session s = HibernateDao.getSession();
        s.setDefaultReadOnly(true);
        s.getTransaction().begin();
        List<String> result = (List<String>) s.createQuery("SELECT DISTINCT f_table_name FROM geometry_columns").list();
        s.getTransaction().commit();
        s.close();
//        s.disconnect();
        return result;
    }

    /**
     * * Busca en la base las tablas con componentes geográficos **
     */
//    public static List<String> getTablasDisponibles() {
//        Session s = HibernateDao.getSession();
//        s.beginTransaction();
//        List<String> result = (List<String>) s.createQuery("SELECT DISTINCT f_table_name FROM geometry_columns").list();        
//        s.getTransaction().commit();
//        return result;
//    }
    private static void setearValor(int i, SQLQuery query, String[] valores) {
        /**
         *
         *
         *
         * SI SE AGREGA UN TIPO ACA, AGREGAR TAMBIEN EN DAO FUNCION
         * esColumnaNumerica
         *
         *
         *
         */
        String s;
        byte[] b;
        switch (valores[1].trim().toLowerCase()) {
            case "serial":
                query.setInteger(i, Integer.parseInt(valores[0]));
                break;
            case "int":
                query.setInteger(i, Integer.parseInt(valores[0]));
                break;
            case "float8":
                query.setFloat(i, Float.parseFloat(valores[0]));
                break;
            case "varchar":
                s = valores[0];
                try {
                    String ss = URLDecoder.decode(s, "UTF-8");
                    query.setString(i, ss);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(HibernateDao.class.getName()).log(Level.SEVERE, null, ex);
                    query.setString(i, valores[0]);
                }
                break;
            case "char":
                s = valores[0];
                try {
                    String ss = URLDecoder.decode(s, "UTF-8");
                    query.setString(i, ss);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(HibernateDao.class.getName()).log(Level.SEVERE, null, ex);
                    query.setString(i, valores[0]);
                }
                break;
            case "float4":
                query.setFloat(i, Float.parseFloat(valores[0]));
                break;
            case "decimal":
                query.setFloat(i, Float.parseFloat(valores[0]));
                break;
            case "numeric":
                query.setInteger(i, Integer.parseInt(valores[0]));
                break;
            case "int4":
                query.setInteger(i, Integer.parseInt(valores[0]));
                break;
            case "int8":
                query.setInteger(i, Integer.parseInt(valores[0]));
                break;
            case "serial4":
                query.setInteger(i, Integer.parseInt(valores[0]));
                break;
            case "serial8":
                query.setInteger(i, Integer.parseInt(valores[0]));
                break;
            default:
                s = valores[0];
                try {
                    String ss = URLDecoder.decode(s, "UTF-8");
                    query.setString(i, ss);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(HibernateDao.class.getName()).log(Level.SEVERE, null, ex);
                    query.setString(i, valores[0]);
                }
                break;
        }
    }

    public static String login(String user, String password) {
        MD5Login md5 = new MD5Login();
        String res = "";
        String encriptada = md5.hash(password);

        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT u from Usuarios u WHERE u.nombre = ? AND u.password = ?");
            query.setString(0, user);
            query.setString(1, encriptada);

            List<Usuarios> result = (List<Usuarios>) query.list();

            if (result.size() > 0) {
                res = "OK";
            } else {
                res = "El usuario y/o contraseña no son correctos.";
            }

            s.getTransaction().commit();
            s.close();
//            s.disconnect();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
//            s.disconnect();
            return "Error inesperado";
        }

    }

    public static List busqueda(ConsultaModel consultas, List<String> lista, List<String> etiquetas) {

        Session s = HibernateDao.getSession();
        try {
            s.beginTransaction();
            String perfilNombre = consultas.getPerfil();
            Query queryp = s.createQuery("SELECT p from Perfiles p WHERE p.nombre = ?");
            queryp.setString(0, perfilNombre);
            List<Perfiles> resultp = (List<Perfiles>) queryp.list();
            Perfiles perfil = resultp.get(0);

            List<Consulta> consultasDatos = consultas.getConsultas();
            int cantidadCapas = consultasDatos.size();

            String consulta = "";
            Consulta consultaDato;
            List<String[]> valores = new ArrayList<>();
            List<String> campos2 = new LinkedList();
            //for (int i = 0; i < cantidadCapas; i++) {

            consultaDato = consultasDatos.get(0);
            PerfilesCapas capa = new PerfilesCapas();

            for (PerfilesCapas c : perfil.getPerfilesCapasList()) {
                if (c.getCapaId().getTabla().equals(consultaDato.getNombreCapa())) {
                    capa = c;
                    break;
                }
            }
            List res = busquedaCapa(consultaDato, capa, lista, etiquetas);
            s.close();
            return res;
            /*
               

             String campos = "";
             for (PerfilCampos c : capa.getPerfilCamposList()) {
             if (c.getEtiquetaResultado() != null && !c.getEtiqueta().isEmpty() && !c.getNombreColumna().equals(columnaGeometrica)) {
             campos += " , p." + c.getNombreColumna() + " as " + c.getNombreColumna();
             campos2.add(c.getNombreColumna());
             if (c.getEtiquetaResultado()) {
             etiquetas.add(c.getNombreColumna());
             }
             }
             }

             lista.addAll(campos2);

             if (i > 0) {
             consulta += " UNION ";
             }

             if (consultasFiltros.isEmpty()) {
             consulta += "select st_simplify(st_transform(p." + columnaGeometrica + ",4326),0.0000005) as geom, st_transform(st_centroid(p." + columnaGeometrica + "),4326) as centroid "
             + campos + " from " + consultaDato.getNombreCapa() + " p "
             + "where 1=1 ";

             List<ConsultaCampo> consultaCampos = consultaDato.getConsultaCampos();
             for (int j = 0; j < consultaCampos.size(); j++) {
             consulta += "AND" + getCriterio("p", consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo(), consultaCampos.get(j).getCriterio());
             String[] fila = new String[2];
             fila[0] = consultaCampos.get(j).getValor();
             fila[1] = DaoAnubis.getTypeColumnFromTable(consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo());
             valores.add(fila);
             }
             } else {

             consulta += "select st_simplify(st_transform(p." + columnaGeometrica + ",4326),0.0000005) as geom, st_transform(st_centroid(p." + columnaGeometrica + "),4326) as centroid "
             + campos + " from " + consultaDato.getNombreCapa() + " p "
             + "where 1=1 ";

             List<ConsultaCampo> consultaCampos = consultaDato.getConsultaCampos();
             for (int j = 0; j < consultaCampos.size(); j++) {
             consulta += "AND" + getCriterio("p", consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo(), consultaCampos.get(j).getCriterio());
             String[] fila = new String[2];
             fila[0] = consultaCampos.get(j).getValor();
             fila[1] = DaoAnubis.getTypeColumnFromTable(consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo());
             valores.add(fila);
             }

             String interseccion = "";


             for (int k = 0; k < consultasFiltros.size(); k++) {

             String consultafiltro = "";

             ConsultaFiltro consultaDatoFiltro = consultasFiltros.get(k);

             String columnaGeometricaFiltro = "geom";
             Query qgeoF = s.createQuery("SELECT c.fGeometryColumn FROM GeometryColumns c where c.fTableName = ?");
             qgeoF.setString(0, consultaDatoFiltro.getNombreCapa());
             List resGeoColF = qgeoF.list();
             columnaGeometricaFiltro = (String) resGeoColF.get(0);

             interseccion = " st_transform(p." + columnaGeometrica + ",4326) ";

             consultafiltro += " (select st_transform(f." + columnaGeometricaFiltro + ",4326) as geom"
             + " from " + consultaDatoFiltro.getNombreCapa() + " f "
             + "where st_isvalid(f." + columnaGeometricaFiltro + ") ";

             List<ConsultaCampo> consultaCamposfiltro = consultaDatoFiltro.getConsultaCampos();
             for (int j = 0; j < consultaCamposfiltro.size(); j++) {
             consultafiltro += "AND" + getCriterio("f", consultaDatoFiltro.getNombreCapa(), consultaCamposfiltro.get(j).getNombreCampo(), consultaCamposfiltro.get(j).getCriterio());
             String[] fila = new String[2];
             fila[0] = consultaCamposfiltro.get(j).getValor();
             fila[1] = DaoAnubis.getTypeColumnFromTable(consultaDatoFiltro.getNombreCapa(), consultaCamposfiltro.get(j).getNombreCampo());
             valores.add(fila);
             }
             consultafiltro += " limit 1) ";
             interseccion = " st_intersects(" + interseccion + "," + consultafiltro + ") ";
             consulta = consulta + " AND " + interseccion;
             }
             }
             */
            //}            
        } catch (Exception ex) {
            ex.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
            return new ArrayList();
        }
    }

    public static List busquedaCapa(Consulta consultaDato, PerfilesCapas capa, List<String> lista, List<String> etiquetas) {
        Session s = HibernateDao.getSession();
        try {
            s.beginTransaction();
            String consulta = "";
            List<String[]> valores = new ArrayList<>();
            List<String> campos2 = new LinkedList();

            List<ConsultaFiltro> consultasFiltros = consultaDato.getConsultaFiltros();

            String columnaGeometrica = "geom";
            Query qgeo = s.createQuery(""
                    + "SELECT c.fGeometryColumn FROM GeometryColumns c where c.fTableName = ?");
            qgeo.setString(0, consultaDato.getNombreCapa());
            List resGeoCol = qgeo.list();
            columnaGeometrica = (String) resGeoCol.get(0);

            String campos = "";
            for (PerfilCampos c : capa.getPerfilCamposList()) {
                if (c.getEtiquetaResultado() != null && !c.getEtiqueta().isEmpty() && !c.getNombreColumna().equals(columnaGeometrica)) {
                    campos += " , p." + c.getNombreColumna() + " as " + c.getNombreColumna();
                    campos2.add(c.getNombreColumna());
                    if (c.getEtiquetaResultado()) {
                        etiquetas.add(c.getNombreColumna());
                    }
                }
            }

            lista.addAll(campos2);

            if (consultasFiltros.isEmpty()) {
                consulta += "select st_simplify(st_transform(p." + columnaGeometrica + ",4326),0.0000005) as geom, st_transform(st_centroid(p." + columnaGeometrica + "),4326) as centroid "
                        + campos + " from " + consultaDato.getNombreCapa() + " p "
                        + "where 1=1 ";

                List<ConsultaCampo> consultaCampos = consultaDato.getConsultaCampos();
                for (int j = 0; j < consultaCampos.size(); j++) {
                    consulta += "AND" + getCriterio("p", consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo(), consultaCampos.get(j).getCriterio());
                    String[] fila = new String[2];
                    fila[0] = consultaCampos.get(j).getValor();
                    fila[1] = DaoAnubis.getTypeColumnFromTable(consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo());
                    valores.add(fila);
                }
            } else {

                consulta += "select st_simplify(st_transform(p." + columnaGeometrica + ",4326),0.0000005) as geom, st_transform(st_centroid(p." + columnaGeometrica + "),4326) as centroid "
                        + campos + " from " + consultaDato.getNombreCapa() + " p "
                        + "where 1=1 ";

                List<ConsultaCampo> consultaCampos = consultaDato.getConsultaCampos();
                for (int j = 0; j < consultaCampos.size(); j++) {
                    consulta += "AND" + getCriterio("p", consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo(), consultaCampos.get(j).getCriterio());
                    String[] fila = new String[2];
                    fila[0] = consultaCampos.get(j).getValor();
                    fila[1] = DaoAnubis.getTypeColumnFromTable(consultaDato.getNombreCapa(), consultaCampos.get(j).getNombreCampo());
                    valores.add(fila);
                }

                String interseccion = "";
                for (int k = 0; k < consultasFiltros.size(); k++) {

                    String consultafiltro = "";

                    ConsultaFiltro consultaDatoFiltro = consultasFiltros.get(k);

                    String columnaGeometricaFiltro = "geom";
                    Query qgeoF = s.createQuery("SELECT c.fGeometryColumn FROM GeometryColumns c where c.fTableName = ?");
                    qgeoF.setString(0, consultaDatoFiltro.getNombreCapa());
                    List resGeoColF = qgeoF.list();
                    columnaGeometricaFiltro = (String) resGeoColF.get(0);

                    interseccion = " st_transform(p." + columnaGeometrica + ",4326) ";

                    consultafiltro += " (select st_transform(f." + columnaGeometricaFiltro + ",4326) as geom"
                            + " from " + consultaDatoFiltro.getNombreCapa() + " f "
                            + "where st_isvalid(f." + columnaGeometricaFiltro + ") ";

                    List<ConsultaCampo> consultaCamposfiltro = consultaDatoFiltro.getConsultaCampos();
                    for (int j = 0; j < consultaCamposfiltro.size(); j++) {
                        consultafiltro += "AND" + getCriterio("f", consultaDatoFiltro.getNombreCapa(), consultaCamposfiltro.get(j).getNombreCampo(), consultaCamposfiltro.get(j).getCriterio());
                        String[] fila = new String[2];
                        fila[0] = consultaCamposfiltro.get(j).getValor();
                        fila[1] = DaoAnubis.getTypeColumnFromTable(consultaDatoFiltro.getNombreCapa(), consultaCamposfiltro.get(j).getNombreCampo());
                        valores.add(fila);
                    }
                    consultafiltro += " limit 1) ";
                    interseccion = " st_intersects(" + interseccion + "," + consultafiltro + ") ";
                    consulta = consulta + " AND " + interseccion;
                }
            }

            SQLQuery query = s.createSQLQuery(consulta);
            for (int i = 0; i < valores.size(); i++) {
                setearValor(i, query, valores.get(i));
            }
            query.addScalar("geom", GeometryType.INSTANCE);
            query.addScalar("centroid", GeometryType.INSTANCE);
            for (String a : campos2) {
                query.addScalar(a, StringType.INSTANCE);
            }
            System.out.println(consulta);
            List result = query.list();
            s.getTransaction().commit();
            s.close();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
            return new ArrayList();
        }

    }

    private static String getCriterio(String letracol, String tabla, String campo, String criterio) {
        if (criterio.equals("Like")) {
            return " upper( CAST(" + letracol + "." + campo + " as text)) like '%' || upper(CAST (? as text)) || '%'";
        } else if (criterio.equals("Igual")) {
            return " UPPER(CAST(" + letracol + "." + campo + " as text))  = UPPER(CAST( ? as text))";
        } else if (criterio.equals("Entre")) {
        } else if (criterio.equals("Mayor")) {
            return " " + letracol + "." + campo + "  > ?";
        } else if (criterio.equals("Menor")) {
            return " " + letracol + "." + campo + " < ?";
        } //        else if(criterio.equals("Exacto"))
        //        {
        //            return tabla +"."+ campo + "=" + valor;
        //        }
        else if (criterio.equals("Distinto")) {
            return " CAST(" + letracol + "." + campo + " as text) != ?";
        }
        return "1=1";
    }

    /**
     * Elimina todos los perfilesCampo que tienen asociado el campo pasado por
     * parámetro
     *
     * @param campo
     */
    public static void deletePerfilCampo(Campos campo) {
        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT p from PerfilCampos p WHERE p.campoId.id = ?");
            query.setInteger(0, campo.getId());
            List<PerfilCampos> result = (List<PerfilCampos>) query.list();
            for (PerfilCampos c : result) {
                s.delete(c);
            }
            s.getTransaction().commit();
            s.close();
//            s.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
//            s.disconnect();
        }
    }

    public static List<PerfilesCapas> getPerfilByName(String perfilNombre) {
        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT p from PerfilesCapas p WHERE p.perfilId.nombre = ?");
            query.setString(0, perfilNombre);
            List<PerfilesCapas> result = (List<PerfilesCapas>) query.list();
            for (PerfilesCapas capa : result) {
                capa.getCapaId().getNombre();
                for (CapaFiltro filtro : capa.getCapaFiltroList()) {
                    filtro.getIdCapaFiltro().getNombreCapa();
                }
                for (PerfilCampos pc : capa.getPerfilCamposList()) {
                    pc.getEtiqueta();
                }
            }
            s.getTransaction().commit();
            s.close();
//        s.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            s.close();
            return new LinkedList<>();
        }
    }

    public static List<PerfilBase> getPerfilBaseByName(String nombrePerfil) {
        Session s = HibernateDao.getSession();
        try {
            s.setDefaultReadOnly(true);
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT p from PerfilBase p WHERE p.perfilId.nombre = ?");
            query.setString(0, nombrePerfil);
            List<PerfilBase> result = (List<PerfilBase>) query.list();
            for (PerfilBase pb : result) {
                pb.getPerfilId().getId();
                pb.getBaseId().getEtiqueta();
                for (BaseCapa bc : pb.getBaseId().getBaseCapaList()) {
                    bc.getCapaId().getTabla();
                }
                pb.getOrden();
            }
            s.getTransaction().commit();
            s.close();
//        s.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            s.close();
            return null;
        }
    }

    public static List<PerfilesCapas> getPerfilCapaByPerfil(String nombrePerfil) {
        Session s = HibernateDao.getSession();
        try {
            s.setDefaultReadOnly(true);
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT p from PerfilesCapas p WHERE p.perfilId.nombre = ?");
            query.setString(0, nombrePerfil);
            List<PerfilesCapas> result = (List<PerfilesCapas>) query.list();
            for (PerfilesCapas pc : result) {
                pc.getPerfilId().getId();
                pc.getOrden();
                pc.getCapaId().getId();
                for (PerfilCampos perfilC : pc.getPerfilCamposList()) {
                    perfilC.getCampoId().getNombreColumna();
                }
            }
            s.getTransaction().commit();
            s.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            s.close();
            return new LinkedList<>();
        }
    }

    public static Capas getCapasPorNombre(String capasNombre) {
        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT p from Capas p WHERE p.nombre = ?");
            query.setString(0, capasNombre);
            List<Capas> result = (List<Capas>) query.list();
            s.getTransaction().commit();
            s.close();
//        s.disconnect();
            if (result != null && result.size() > 0) {
                return result.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            s.close();
            return null;
        }
    }

    public static Perfiles getPerfilPorNombre(String perfilNombre) {
        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            Query query = s.createQuery("SELECT p from Perfiles p WHERE p.nombre = ?");
            query.setString(0, perfilNombre);
            List<Perfiles> result = (List<Perfiles>) query.list();
            s.getTransaction().commit();
//        s.disconnect();
            if (result != null && result.size() > 0) {
                Perfiles p = result.get(0);
                for (PerfilPlugins plugin : p.getPerfilPluginsList()) {
                    plugin.getPluginId().getJs();
                }
                s.close();
                return result.get(0);
            } else {
                s.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            s.close();
            return null;
        }
    }

    public static List findAll(String className) {
        Session s = HibernateDao.getSession();
        try {

            s.setDefaultReadOnly(true);
            s.getTransaction().begin();
            Query q = s.createQuery("select p from " + className + " p");
            List result = (List) q.list();

            s.getTransaction().commit();
            s.close();
//        s.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            s.close();
            return new LinkedList();
        }
    }

    public static Object getById(String className, Integer id) {
        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            List result = (List) s.createQuery("select p from " + className + " p where id = " + id).list();
            if (result.size() > 0) {
                s.refresh(result.get(0));
            }
            s.getTransaction().commit();
            s.close();
//            s.disconnect();
            return result.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
//            s.disconnect();
            return null;
        }
    }

    public static void update(Object entity) {
        Session s = HibernateDao.getSession();
        try {
            Transaction t = s.getTransaction();

            t.begin();            //s.saveOrUpdate(entity);
//            entity = s.merge(entity);
//            s.saveOrUpdate(entity);
            s.update(entity);
            //s.getTransaction().commit();
            t.commit();
            s.flush();
            s.close();
//            s.disconnect();
            /**
             * *
             * Se obtiene el nombre de la clase desde el HibernateProxyHelper
             * porque en caso que se ejecute LazyLoading el nombre de la clase
             * posee información que da error en la consulta *
             */
        } catch (Exception e) {
            e.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
//            s.disconnect();
        }
    }

    public static Object saveOrUpdate(Object entity) {
        Session s = HibernateDao.getSession();
        try {
            Transaction t = s.getTransaction();

            t.begin();            //s.saveOrUpdate(entity);
//            entity = s.merge(entity);
//            s.saveOrUpdate(entity);
            Integer a = (Integer) s.save(entity);
            //s.getTransaction().commit();
            t.commit();
            s.flush();
            /**
             * *
             * Se obtiene el nombre de la clase desde el HibernateProxyHelper
             * porque en caso que se ejecute LazyLoading el nombre de la clase
             * posee información que da error en la consulta *
             */
            Object o = getById(HibernateProxyHelper.getClassWithoutInitializingProxy(entity).getName(), a);
            s.close();
//            s.disconnect();
            return o;

        } catch (Exception e) {
            e.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
//            s.disconnect();
        }
        return null;
    }

    @Deprecated
    public static void delete(Object a) {
        System.out.println("Esta funcion no se usa mas");
    }

    public static void delete(String clase, Integer id) {
        Session s = HibernateDao.getSession();
        try {
            s.getTransaction().begin();
            List result = (List) s.createQuery("select p from " + clase + " p where id = " + id).list();
            if (result.size() > 0) {
                s.delete(result.get(0));
            }
            s.getTransaction().commit();
            s.close();
//            s.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
            s.close();
//            s.disconnect();
        }
//        Session s = HibernateDao.getSession();
//        try {
//            s.getTransaction().begin();
//            s.delete(entity);
//            s.getTransaction().commit();
//            s.disconnect();
//            s.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (s.getTransaction() != null) {
//                s.getTransaction().rollback();
//            }
//            s.disconnect();
//            s.close();
//        }
//s.close();        
    }

    public static Session getSession() {
        return HibernateSessionFactory.getSessionFactory().openSession();
    }

    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getParameter("");
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }

    /*OBTENER COMENTARIOS GEOPOSTAL*/
    public static  List<HashMap> obtenerComentariosGeopostal() {
        
        Session s = HibernateDao.getSession();    
        ArrayList<HashMap> list = new ArrayList<HashMap>();  
       
        try {
            s.getTransaction().begin();
            //filtro la cantidad?????
            Query query = s.createQuery("SELECT p from ContactoVisualizador p where verificado=true ORDER BY fecha desc");
            List<ContactoVisualizador> result = (List<ContactoVisualizador>) query.list();
            for (ContactoVisualizador c : result) {
                 HashMap resultado = new HashMap();               
                resultado.put("Nombre", c.getNombre());
                resultado.put("Mensaje", c.getMensaje());
                resultado.put("Fecha", c.getFecha().toString());
                resultado.put("Respuesta", c.getRespuesta());
               list.add(resultado);
            }
        } catch (Exception e) {
            e.printStackTrace();
            s.close();
            return null;
        }
        return list;

    }
}
