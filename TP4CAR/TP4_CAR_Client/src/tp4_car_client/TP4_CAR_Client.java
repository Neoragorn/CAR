/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4_car_client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author casier
 */
public class TP4_CAR_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Client client = new Client();
    }
    
}
