/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_server;

import Persistence.PersistenceConnection;
import java.rmi.RemoteException;

/**
 *
 * @author sofian
 */
public class TP5_CAR_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {

        PersistenceConnection co = new PersistenceConnection();
        try {
            co.startConnection("casier", "C&?1+mur");
        } catch (Exception e) {
            System.out.println(e);
        }
        Server serveur = new Server();
        serveur.start();
    }

}
