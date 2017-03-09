/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import tp5_car_interface.InterfaceServer;

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
        registry.rebind("mini-chat", skeleton);
        System.out.println("Server created");
    }

    public Server() throws RemoteException {
    }
    
        
}
