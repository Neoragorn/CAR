/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Persistence.PersistenceConnection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author sofian
 */
@Path("Connection")
public class Connection {

    @Context
    private UriInfo context;

    public Connection() {
        PersistenceConnection co = new PersistenceConnection();
        try {
            co.startConnection("casier", "C&?1+mur");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @GET
    @Produces("text/html")
    public String connectionForm() {
        return "<form action=\"Connection_check\"> <label for=\"pseudo\">Pseudo :</label><input name=\"pseudo\" type=\"text\" id=\"pseudo\" /><br />\n"
                + "<label for=\"password\">Mot de Passe :</label><input type=\"password\" name=\"password\" id=\"password\" />\n\n"
                + "<input type=\"submit\" value=\"Submit\" \n /> </form>"
                + "<form action=\"Inscription\"> <input type=\"submit\" value=\"Inscription\" \n /> </form>";
    }

    @Produces("text/html")
    public String connectionFormAfterInscription() {
        return "<form action=\"../Connection_check\"> <label for=\"pseudo\">Pseudo :</label><input name=\"pseudo\" type=\"text\" id=\"pseudo\" /><br />\n"
                + "<label for=\"password\">Mot de Passe :</label><input type=\"password\" name=\"password\" id=\"password\" />\n\n"
                + "<input type=\"submit\" value=\"Submit\" \n /> </form>"
                + "<form action=\"Inscription\"> <input type=\"submit\" value=\"Inscription\" \n /> </form>";
    }

    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public String postHandler(String content) {
        return content;
    }
}
