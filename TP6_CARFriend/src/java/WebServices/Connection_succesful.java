/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author sofian
 */
@Path("Connection_successful")
public class Connection_succesful {

    @Context
    private UriInfo context;
    
    public Connection_succesful()
    {        
    }
    
    @GET
    @Produces("text/html")
    public String getHtml() {
        return "<h1>Connection succesful ! </h1>";
    }
}
