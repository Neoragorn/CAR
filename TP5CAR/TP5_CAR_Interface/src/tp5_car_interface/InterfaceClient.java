/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author casier
 */
public interface InterfaceClient extends Remote {

//    void Receive(Message message);

    void Receive(String login, String message) throws RemoteException;

}
