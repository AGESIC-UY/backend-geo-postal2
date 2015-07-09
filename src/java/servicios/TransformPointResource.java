/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import com.google.gson.JsonObject;
import dao.DaoAnubis;
import javax.jws.WebParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.icefaces.ace.json.JSONObject;
import org.postgresql.geometric.PGpoint;

/**
 * REST Web Service
 *
 * @author Diego.Gonzalez
 */
@Path("transformPoint")
public class TransformPointResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TransformPointResource
     */
    public TransformPointResource() {
    }

    /**
     * Retrieves representation of an instance of
     * servicios.TransformPointResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("latitud") String latitud,
            @QueryParam("londitud") String longitud,
            @QueryParam("srid_source") String sridSource,
            @QueryParam("srid_destination") String sridDestination) {
        PGpoint p = DaoAnubis.transformCordinates(latitud, longitud, sridSource, sridDestination);
        if (p != null) {
            return "{ \"latitud\": " + p.y + ",\"londitud\": " + p.x + "}";
        } else {
            return "{ \"latitud\": 0,\"londitud\": 0}";
        }
    }

    /**
     * PUT method for updating or creating an instance of TransformPointResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
