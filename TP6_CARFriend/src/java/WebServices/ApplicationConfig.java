/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author sofian
 */
@javax.ws.rs.ApplicationPath("WebResource")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(WebServices.Connection.class);
        resources.add(WebServices.Connection_check.class);
        resources.add(WebServices.Home.class);
        resources.add(WebServices.Inscription.class);
    }

}
