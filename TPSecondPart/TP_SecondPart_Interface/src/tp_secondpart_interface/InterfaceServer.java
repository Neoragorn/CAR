/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author casier
 */
public interface InterfaceServer extends Remote {

    void Send(String message, String login,  InterfaceClient cl) throws RemoteException;

    void NotifyAll(String msg, String login, InterfaceClient clOrigin) throws RemoteException;

    boolean Connect(String login, String mdp, InterfaceClient cl) throws RemoteException;

    void Disonnect(String login, InterfaceClient cl) throws RemoteException;

}
