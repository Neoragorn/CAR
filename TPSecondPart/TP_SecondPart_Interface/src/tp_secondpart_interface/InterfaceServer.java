/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_interface;

import Models.Category;
import Models.DiscussionGroup;
import Models.Message;
import Models.MessageDiscussion;
import Models.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author casier
 */
public interface InterfaceServer extends Remote {

    void Send(String message, String login, InterfaceClient cl) throws RemoteException;

    void NotifyAll(String msg, String login, InterfaceClient clOrigin) throws RemoteException;

    boolean Connect(String login, String mdp, InterfaceClient cl) throws RemoteException;

    void Disonnect(String login, InterfaceClient cl) throws RemoteException;

    User createUser() throws RemoteException;

    ArrayList<MessageDiscussion> recoverDiscussionMessage() throws RemoteException;

    ArrayList<MessageDiscussion> getMessageFromDiscussion(DiscussionGroup discussion) throws RemoteException;

    ArrayList<User> getDiscussionUsers(int id) throws RemoteException;

    ArrayList<Category> getCategoryUser(int id) throws RemoteException;
    
    ArrayList<Message> getMessageUser(int id) throws RemoteException;
   
    void inscription(User user) throws RemoteException;

}
