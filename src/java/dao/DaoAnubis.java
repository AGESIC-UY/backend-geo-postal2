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
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Settings;
import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.postgis.PGgeometry;
import org.postgresql.geometric.PGpoint;
import servicios.ResultadoBusqueda;
import servicios.ResultadoBusquedaPunto;

/**
 *
 * @author Diego.Gonzalez
 */
public class DaoAnubis {

    private static Connection conn = null;
    //private static ResourceBundle conf = ResourceBundle.getBundle("apis.conf.config");
    /**
     * Los parametros deben tomarse del hibernate.cfg.xml *
     */
    private static String connectString = getConnectString();
    private static String user = getUserConnection();
    private static String pass = getPassConnection();

    public static String getConnectString() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
        Properties props = sessionFactoryImpl.getProperties();
        String url = props.get("hibernate.connection.url").toString();
        return url;
    }

    public static String getUserConnection() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
        Properties props = sessionFactoryImpl.getProperties();
        String user = props.get("hibernate.connection.username").toString();
        return user;
    }

    public static String getPassConnection() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
        Properties props = sessionFactoryImpl.getProperties();
        String pass = props.get("hibernate.connection.password").toString();
        return pass;
    }

    public static Session getSession() {
        HibernateSessionBean sessionBean = findBean("HibernateSessionBean");
        return sessionBean.getSession();
    }

    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getParameter("");

        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
                //connectString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
                System.out.println(connectString);
                System.out.println("Se va a conectar: " + user + pass);
                conn = DriverManager.getConnection(connectString, user, pass);
                //Statement stmt = con.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (conn != null && conn.isClosed()) {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(connectString, user, pass);
            }
        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn == null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Object executeSingleResult(String sql) throws Exception {
        try {
            Statement stm = getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            stm.close();
            getConnection().close();
            return null;
        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            try {
                getConnection().close();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            return null;
        }
    }

    public static void executeUpdate(String sql) throws Exception {
        try {
            Statement stm = getConnection().createStatement();
            stm.executeUpdate(sql);
            stm.close();
            getConnection().close();
        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            try {
                getConnection().close();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        }
    }

    public static List<String> getListadeValores(String tabla, String campo) {
        List<String> lista = new ArrayList<>();

        Connection conexion = getConnection();
        try {

            String consulta = "select distinct t." + campo + " from " + tabla + " t order by t. " + campo;
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cam = rs.getString(1);
                lista.add(cam);
            }
            conexion.close();
        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conexion.close();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        }

        return lista;
    }

    public static boolean existsTable(String tableName) throws Exception {
        try {
            DatabaseMetaData md = getConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            boolean toRet = false;
            while (rs.next()) {
                toRet = true;
            }
            getConnection().close();
            return toRet;
        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            try {
                getConnection().close();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            return false;
        }
    }

    public static PGpoint getCoordenadasTransformadas(Double coordX, Double coordY, String srid) {
        Connection conexion = getConnection();
        try {
            String srid_num = srid.split(":")[1];
//            Double x = coordX * -1;
//            Double y = coordY * -1;
            //String consulta = "select st_transform(st_geomfromtext('POINT(-34.906388   -56.199722)',4326),32721)";
            String consulta = "select st_transform(st_geomfromtext('POINT(" + coordX + " " + coordY + ")',32721)," + srid_num + ")";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            PGpoint pg = null;
            if (rs.next()) {
                PGgeometry geom = (PGgeometry) rs.getObject(1);
                org.postgis.Point p = geom.getGeometry().getFirstPoint();
                pg = new PGpoint(p.x, p.y);
            }
            conexion.close();
            return pg;
        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conexion.close();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            return null;
        }
    }

    public static List<String[]> getColumnsFromTable(String nameTable) throws Exception {
        try {
            List res = new ArrayList();
            DatabaseMetaData md = getConnection().getMetaData();
            ResultSet rs = md.getColumns(null, null, nameTable, null);

            while (rs.next()) {
                String[] fila = new String[2];
                fila[0] = rs.getString(4);
                fila[1] = rs.getString(6);
                res.add(fila);
            }
            getConnection().close();

            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            getConnection().close();
            return new ArrayList();
        }
    }

    public static String getTypeColumnFromTable(String nameTable, String columnName) throws Exception {
        try {
            DatabaseMetaData md = getConnection().getMetaData();
            ResultSet rs = md.getColumns(null, null, nameTable, null);
            while (rs.next()) {
                if (rs.getString(4).toUpperCase().equals(columnName.toUpperCase())) {
                    return rs.getString(6);
                }
            }
            getConnection().close();
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            getConnection().close();
            return "";
        }
    }

    public static Boolean esColumnaNumerica(String nameTable, String columnName) throws Exception {
        /**
         *
         *
         *
         * SI SE AGREGA UN TIPO ACA, AGREGAR TAMBIEN EN HibernateDao FUNCION
         * setearValor
         *
         *
         *
         */
        String tipo = getTypeColumnFromTable(nameTable, columnName);
        switch (tipo) {
            case "serial":
            case "int":
            case "float8":
            case "float4":
            case "decimal":
            case "numeric":
            case "int4":
            case "int8":
            case "serial4":
            case "serial8":
                return true;
            case "varchar":
            case "char":
                return false;
            default:
                return null;
        }
    }

    public static int getCP(Geometry res) throws SQLException {
        Connection conexion = getConnection();
        try {
            //                Session s = HibernateDao.getSession();
            //                s.getTransaction().begin();            
            String consulta = "select cp.cod_postal as CP from codigos_postales cp where st_isvalid(?) and st_isvalid(cp.multipolygon) and st_intersects(cp.multipolygon,st_transform(?,32721)) order by st_area(st_intersection(cp.multipolygon,st_transform(?,32721))) desc limit 1";

            PreparedStatement ps = conexion.prepareStatement(consulta);

            ps.setObject(1, GeometryToPGgeometry(res));
            ps.setObject(2, GeometryToPGgeometry(res));
            ps.setObject(3, GeometryToPGgeometry(res));

            ResultSet rs = ps.executeQuery();
            int num = 0;
            if (rs.next()) {
                num = (Integer) rs.getInt(1);
            } else {
                num = 0;
            }
            return num;

        } catch (ParseException ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public static HashMap<String, String> obtenerValoresRandom(String tabla, List<String> campos) throws SQLException {
        Connection conexion = null;
        try {
            conexion = getConnection();

            String sql = "select 1 ";
            for (String campo : campos) {
                sql += "," + campo;
            }
            sql += " from " + tabla + " order by random() limit 1";
            PreparedStatement ps = conexion.prepareStatement(sql);
            HashMap<String, String> toReturn = new HashMap<>();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                for (String campo : campos) {
                    if (!campo.equals("1")) {
                        String valor = rs.getObject(campo).toString();
                        toReturn.put(campo, valor);
                    }
                }
            }
            conexion.close();
            return toReturn;
        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            if (conexion != null) {
                conexion.close();
            }
            return null;
        }
    }

    public static int getCP(PGpoint res) throws SQLException {
        Connection conexion = null;
        try {
            conexion = getConnection();

            String consulta = "select cp.cod_postal as CP from codigos_postales cp where st_isvalid(CP.multipolygon) and st_intersects(cp.multipolygon,st_transform(st_geomfromtext('POINT(" + res.x + " " + res.y + ")', 4326),32721)) order by st_area(st_intersection(cp.multipolygon,st_transform(st_geomfromtext('POINT(" + res.x + " " + res.y + ")', 4326),32721))) desc limit 1";
            PreparedStatement ps = conexion.prepareStatement(consulta);

            ResultSet rs = ps.executeQuery();
            int num = 0;
            if (rs.next()) {
                num = (Integer) rs.getInt(1);
            } else {
                num = 0;
            }
            conexion.close();
            return num;

        } catch (Exception ex) {
            Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
            if (conexion != null) {
                conexion.close();
            }
            return 0;
        }
    }

    public static ResultadoBusquedaPunto buscarDireccionNumeroCuentaUTE(String NumeroCuentaUTE, int NIS, boolean esNIS) {
        ResourceBundle b = ResourceBundle.getBundle("conf.conf");
        String nis = Integer.toString(NIS);
        String conexionURL = b.getString("urlute");

        //******Tener en cuenta si no es minuscula *****//
//        String user = desEncriptar(b.getString("userNumeroCuentaUTE")).toLowerCase();
//        String pass = desEncriptar(b.getString("passNumeroCuentaUTE")).toLowerCase();

        String user = b.getString("userNumeroCuentaUTE");
        String pass = b.getString("passNumeroCuentaUTE");

        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conexionURL, user, pass);

            String consulta = esNIS ? "select coordenadas " + "from direccion " + "where nis_red = ? " : "select coordenadas " + "from direccion " + "where num_cuenta=CAST(? as bigint) ";

            PreparedStatement ps = con.prepareStatement(consulta);
            if (esNIS) {
                ps.setInt(1, NIS);
            } else {
                ps.setString(1, NumeroCuentaUTE);
            }


            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            PGpoint geom = (PGpoint) rs.getObject(1);
//            CoordinateSequence coord = new CoordinateSequence(geom.x, geom.y);
//            Point punto = new Point(coord, new GeometryFactory());

            HashMap<String, String> propiedades = new HashMap<String, String>();

            consulta = !esNIS ? "select sum_depto, sum_localidad, sum_calle, sum_num, env_depto, env_localidad, env_calle, env_numero, observaciones_anc "
                    + "from direccion "
                    + "where num_cuenta=CAST(? as bigint) " : "select sum_depto, sum_localidad, sum_calle, sum_num, env_depto, env_localidad, env_calle, env_numero, observaciones_anc "
                    + "from direccion "
                    + "where nis_red=?";
            ps = con.prepareStatement(consulta);
            if (esNIS) {
                ps.setInt(1, NIS);
            } else {
                ps.setString(1, NumeroCuentaUTE);
            }

            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            propiedades.put("Departamento", StringEscapeUtils.escapeHtml(rs.getString(1).trim()));
            propiedades.put("Localidad", StringEscapeUtils.escapeHtml(rs.getString(2).trim()));
            propiedades.put("Calle", StringEscapeUtils.escapeHtml(rs.getString(3).trim()));
            propiedades.put("Numero", rs.getString(4).trim());
            propiedades.put("Observaciones", StringEscapeUtils.escapeHtml(rs.getString(9)));

            //propiedades.put("Numero", rs.getString(4));
            String dpto_env = rs.getString(5).trim();
            String loc_env = rs.getString(6).trim();
            String calle_env = rs.getString(7).trim();
            String num_env = rs.getString(8);

            if (calle_env != null && !"".equals(calle_env) && !calle_env.equals(propiedades.get("Calle")) && num_env != null && !"".equals(num_env) && !num_env.equals(propiedades.get("Numero"))) {
                propiedades.put("DepartamentoEnvio", StringEscapeUtils.escapeHtml(dpto_env));
                propiedades.put("LocalidadEnvio", StringEscapeUtils.escapeHtml(loc_env));
                propiedades.put("CalleEnvio", StringEscapeUtils.escapeHtml(calle_env));
                propiedades.put("NumeroEnvio", num_env);
            }
            con.close();

            return new ResultadoBusquedaPunto(1212, null, geom, esNIS ? nis : NumeroCuentaUTE + "", propiedades);

            //  ResultadoBusqueda res = new ResultadoBusqueda(nis, geom, geom, "");
            // return res;

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public static String guardarObsPuntoTsubasa(Integer idPunto, String observacion) {
        ResourceBundle b = ResourceBundle.getBundle("conf.conf");
        String conexionURL = b.getString("urlTsubasa");
        String user = b.getString("userTsubasa");
        String pass = b.getString("passTsubasa");
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conexionURL, user, pass);

            String consulta = " "
                    + " INSERT INTO eventos_puntos(  usuario, punto_id, descripcion, valoracion, valido, sistema_id)"
                    + "VALUES ( ?, ?, ?, null, ?, ?);";
            PreparedStatement ps = con.prepareStatement(consulta);
            ps.setString(1, "Visualizador");
            ps.setInt(2, idPunto);
            ps.setString(3, observacion);
            ps.setBoolean(4, true);
            ps.setInt(5, 2); // Sistema Visualizador
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            return "ErrorInterno";
        }
        return "ok";
    }

    public static String guardarObsNumeroCuentaUTE(String NumeroCuentaUTE, int NIS, String obs, boolean esNIS) {
        ResourceBundle b = ResourceBundle.getBundle("conf.conf");

        String conexionURL = b.getString("urlute");

        //******Tener en cuenta si no es minuscula *****//
        String user = b.getString("userNumeroCuentaUTE");
        String pass = b.getString("passNumeroCuentaUTE");
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conexionURL, user, pass);

            String consulta = esNIS ? "update direccion SET observaciones_anc = ? where nis_red= ? " : "update direccion SET observaciones_anc = ? where num_cuenta= CAST(? as bigint)";
            PreparedStatement ps = con.prepareStatement(consulta);
            ps.setString(1, obs);
            if (esNIS) {
                ps.setInt(2, NIS);
            } else {
                ps.setString(2, NumeroCuentaUTE);
            }

            ps.execute();
            con.close();
            return "True";

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "False";
    }

    public static void transformar(ResultadoBusquedaPunto rs, int ini, int fin) {
        Connection conexion = null;
        try {
            PGpoint pu = rs.getPunto();
            if (pu.x != 0 && pu.y != 0) {
                Class.forName("org.postgresql.Driver");
                conexion = getConnection();
                String consulta = "select st_transform(st_geomfromtext('POINT(" + rs.getPunto().x + " " + rs.getPunto().y + ")'," + ini + ")," + fin + ")";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ResultSet res = ps.executeQuery();
                res.next();
                PGgeometry geom = (PGgeometry) res.getObject(1);

                org.postgis.Point p = geom.getGeometry().getFirstPoint();
                PGpoint pg = new PGpoint(p.x, p.y);
                rs.setPunto(pg);
            }
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static String desEncriptar(String texto) {

        String textEnc = "";
        String inicio = "";
        String fin = "";
        int largo = texto.length();
        for (int i = 0; i < largo; i = i + 2) {
            inicio += texto.substring(i, i + 1);
            if (largo != i + 1) {
                fin = texto.substring(i + 1, i + 2) + fin;
            }
        }
        textEnc = inicio + fin;
        textEnc = textEnc.substring(10);

        String textOrig = "";
        for (int i = 0; i < textEnc.length(); i = i + 2) {
            int valor = Integer.parseInt(textEnc.substring(i, i + 2));
            char car = (char) valor;
            String text = Character.toString(car);
            textOrig += text;
        }

        return textOrig;
    }

    static public PGgeometry GeometryToPGgeometry(Geometry geom) throws SQLException,
            ParseException {
        String PGgeometryStr =
                "SRID=" + geom.getSRID() + ";" + geom.toString();
        PGgeometry pg = new PGgeometry(PGgeometry.geomFromString(PGgeometryStr));
        return pg;

    }
    static int SRID = 0;
    static GeometryFactory fact = new GeometryFactory(new PrecisionModel(1E10), SRID);

    static public Geometry convertGeometry(PGgeometry pg) throws SQLException,
            ParseException {

        String geometryString = pg.getGeometry().toString();
        WKTReader r = new WKTReader(fact);
        Geometry geom;
        // ADD SRID

        if (geometryString.indexOf(';') != -1) {
            String[] temp = PGgeometry.splitSRID(geometryString);
            int srid = Integer.parseInt(temp[0].substring(5));
            geom = (Geometry) r.read(temp[1]);
            geom.setSRID(SRID);
        } else {
            geom = (Geometry) r.read(geometryString);
        }
        return geom;

    }

    public static ResultadoBusquedaPunto buscarPuntoXIdTsubasa(int idPunto) {
        ResourceBundle b = ResourceBundle.getBundle("conf.conf");
        String conexionURL = b.getString("urlTsubasa");
        String user = b.getString("userTsubasa");
        String pass = b.getString("passTsubasa");
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(conexionURL, user, pass);

            String consulta = "select idcalle,nombre,numero,cp,solar_id,solar, manzana,nombre_inmueble,st_x(st_transform(geom,4326)) as x,st_y(st_transform(geom,4326)) as y from clasificacion_puntos p "
                    + "where id=?";

            PreparedStatement ps = con.prepareStatement(consulta);
            ps.setInt(1, idPunto);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            PGpoint geom = new PGpoint(rs.getDouble("x"), rs.getDouble("y"));

            //PGpoint geom = (PGpoint) rs.getObject(3);


            HashMap<String, String> propiedades = new HashMap<String, String>();

            String calle = rs.getString(2);
            if (calle == null) {
                calle = "";
            }
            String nro = rs.getString(3);
            if (nro == null) {
                nro = "";
            }
            String cp = rs.getString(4);
            if (cp == null) {
                cp = "";
            }
            String solar = rs.getString(6);
            if (solar == null) {
                solar = "";
            }
            String manzana = rs.getString(7);
            if (manzana == null) {
                manzana = "";
            }
            String inmueble = rs.getString(8);
            if (inmueble == null) {
                inmueble = "";
            }

            propiedades.put("Calle", calle.trim());
            propiedades.put("Numero", nro.trim());
            propiedades.put("CP", cp.trim());
            propiedades.put("Solar", solar.trim());
            propiedades.put("Manzana", manzana.trim());
            propiedades.put("Nombre_inmueble", inmueble.trim());

            con.close();

            return new ResultadoBusquedaPunto(1212, null, geom, "Punto: " + idPunto, propiedades);


        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DaoAnubis.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;



    }
}
