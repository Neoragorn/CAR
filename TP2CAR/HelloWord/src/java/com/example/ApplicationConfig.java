/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author sofian
 */
@javax.ws.rs.ApplicationPath("webresource")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.example.ServiceResource.class);
        resources.add(com.example.TodoResource.class);
        resources.add(com.example.TodosResource.class);
    }
    
}
