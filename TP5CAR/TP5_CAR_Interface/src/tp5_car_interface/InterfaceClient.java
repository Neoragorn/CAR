/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_interface;

import Models.DiscussionGroup;
import Models.MessageDiscussion;
import Models.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author casier
 */
public interface InterfaceClient extends Remote {

//    void Receive(Message message);

    void Receive(String login, String message) throws RemoteException;

    void RecoverDiscussion() throws RemoteException;
    
    void RecoverJoinedDiscussion() throws RemoteException;
    
    void addMessageToDiscussion(DiscussionGroup discussion, MessageDiscussion message, User user) throws RemoteException;
    
    void createDiscussion(User user, String title, String description) throws RemoteException;
    
    void getMessageUser(int id) throws RemoteException;
    
    void orderNewFrameDiscussion() throws RemoteException;
}
