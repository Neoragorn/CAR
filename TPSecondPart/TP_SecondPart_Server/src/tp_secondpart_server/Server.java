/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_server;

import tp_secondpart_interface.InterfaceServer;
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
        InterfaceServer skeleton = (InterfaceServer) UnicastRemoteObject.exportObject(new InterfaceImplServer(), 4020);
        Registry registry = LocateRegistry.createRegistry(4020);        
        registry.rebind("mini-chat", skeleton);
        System.out.println("Server created");
    }

    public Server() throws RemoteException {
    }
}
