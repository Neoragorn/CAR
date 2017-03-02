/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4_car;

import tp4_car_interface.InterfaceServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author casier
 */
public class Server {

    private InterfaceServer skeleton;
    private Registry registry;

    public void start() throws RemoteException {
        InterfaceServer skeleton = (InterfaceServer) UnicastRemoteObject.exportObject(new InterfaceImplServer(), 4010);
        Registry registry = LocateRegistry.createRegistry(4010);        
        registry.rebind("Add", skeleton);
        System.out.println("Server created");
    }

    public Server() throws RemoteException {
    }
}
