/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_client;

import Bean_Client.UserBean;
import Frame_Client.ChoixConnection;
import Frame_Client.MyFrame;
import Models.User;
import Persistence.PersistenceConnection;
import java.io.UnsupportedEncodingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.swing.JPanel;
import tp5_car_interface.InterfaceClient;
import tp5_car_interface.InterfaceServer;

/**
 *
 * @author sofian
 */
public class TP5_CAR_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, RemoteException, NotBoundException {

        PersistenceConnection co = new PersistenceConnection();
        try {
            co.startConnection("casier", "C&?1+mur");
        } catch (Exception e) {
            System.out.println(e);
        }
        MyFrame myF = new MyFrame();
        MyFrame.setInst(myF);
        JPanel jp = new ChoixConnection();
        MyFrame.getInstance().setActualPanel(jp);
        //Ecran pour soit se connecter, soit s'inscrire
        myF.startPoint(jp);
    }
}
