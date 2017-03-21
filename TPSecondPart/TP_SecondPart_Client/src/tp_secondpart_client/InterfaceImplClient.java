/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_client;

import Frame.Home;
import Frame.MyFrame;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_secondpart_interface.InterfaceClient;

/**
 *
 * @author casier
 */
public class InterfaceImplClient extends UnicastRemoteObject implements InterfaceClient {

    public InterfaceImplClient() throws RemoteException {

    }

    @Override
    public void Receive(String login, String message) {
        if (MyFrame.getInstance().getFrame() != null) {
            try {
                MyFrame.getInstance().changeFrame(new Home());
            } catch (RemoteException | NotBoundException | NoSuchAlgorithmException ex) {
                Logger.getLogger(InterfaceImplClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(login + " : " + message);
        
    }
}
