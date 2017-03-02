/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4_car_client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import tp4_car_interface.InterfaceClient;

/**
 *
 * @author casier
 */
public class InterfaceImplClient extends UnicastRemoteObject implements InterfaceClient {

    public InterfaceImplClient() throws RemoteException
    {
        
    }
    
    @Override
    public void Receive(String login, String message) {
        System.out.println(login + " : " + message);
    }
}
