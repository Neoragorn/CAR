/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_interface;

import Models.DiscussionGroup;
import Models.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author casier
 */
public interface InterfaceServer extends Remote {

    void Send(String message, String login,  InterfaceClient cl) throws RemoteException;

    void NotifyAll(String msg, String login, InterfaceClient clOrigin) throws RemoteException;

    Models.User Connect(String login, String mdp, InterfaceClient cl) throws RemoteException, SQLException, NoSuchAlgorithmException;

    void Disonnect(String login, InterfaceClient cl) throws RemoteException;

    ArrayList<DiscussionGroup> giveDiscussion(User user) throws RemoteException; 
    
    ArrayList<DiscussionGroup> giveJoinedDiscussion(User user) throws RemoteException;
}
