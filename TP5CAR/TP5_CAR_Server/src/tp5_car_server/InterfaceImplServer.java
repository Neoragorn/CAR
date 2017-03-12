/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_server;

import Models.DiscussionGroup;
import Models.Friend;
import Models.Message;
import Models.MessageDiscussion;
import Models.User;
import Persistence.DiscussionGroupBdd;
import Persistence.MessageBdd;
import Persistence.MessageDiscussionBdd;
import Persistence.UserBdd;
import Persistence.UserCategoryVirtualProxy;
import Persistence.UserMessageVirtualProxy;
import tp5_car_interface.InterfaceServer;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp5_car_interface.InterfaceClient;

/**
 *
 * @author casier
 */
public class InterfaceImplServer implements InterfaceServer {

    private ArrayList<InterfaceClient> listClient = new ArrayList();
    private HashMap<String, String> connectedCl = new HashMap();

    public InterfaceImplServer() throws RemoteException {
        super();
    }

    @Override
    public void NotifyAll(String msg, String login, InterfaceClient clOrigin) throws RemoteException {
        for (InterfaceClient cl : listClient) {
            cl.Receive(login, msg);
        }
    }

    @Override
    public User Connect(String login, String mdp, InterfaceClient cl) throws RemoteException, SQLException, NoSuchAlgorithmException {
        System.out.println("Entered Connec");
        User user = UserBdd.getUser(login, mdp);
        user.setProxyMessage(new UserMessageVirtualProxy(user.getIdUser()));
        user.setProxyCategory(new UserCategoryVirtualProxy(user.getIdUser()));
        ArrayList<Friend> friend = UserBdd.getFriends(user);
        user.setFriends(friend);
        if (user != null) {
            System.out.println("Client connected : " + login);
            NotifyAll(" s'est connecté", login, cl);
            listClient.add(cl);
            return user;
        }
        return null;
    }

    @Override
    public void Disonnect(String login, InterfaceClient cl) throws RemoteException {
        connectedCl.remove(login);
        listClient.remove(cl);
        NotifyAll(" s'est déconnecté", login, cl);
    }

    @Override
    public void Send(String message, String login, InterfaceClient cl) throws RemoteException {
        NotifyAll(message, login, cl);
    }

    @Override
    public ArrayList<DiscussionGroup> giveDiscussion(User user) throws RemoteException {
        try {
            return DiscussionGroupBdd.getNotJoinedDiscussionGroupBdd(user);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public ArrayList<DiscussionGroup> giveJoinedDiscussion(User user) throws RemoteException {
        try {
            return DiscussionGroupBdd.getJoinedDiscussionGroupBdd(user);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void sendMessageDiscussion(DiscussionGroup discussion, MessageDiscussion message, User user) throws RemoteException {
        MessageDiscussionBdd.insertMessageIntoDiscussion(discussion, message, user);
    }

    @Override
    public void createNewDiscussion(User user, String title, String description) throws RemoteException {
        try {
            DiscussionGroupBdd.createDiscussionGroupBdd(user.getIdUser(), title, description);
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceImplServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Message> recoverMessageUser(int id) throws RemoteException {
        try {
            return UserBdd.getPrivateMessageById(id);
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceImplServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public void sendMessage(Message msg) throws RemoteException, SQLException {
        System.out.println("Entered in sendMessage");
        MessageBdd.insertMessage(msg);
    }
    
    @Override
    public void changeFrameDiscussion(ArrayList<User> users) throws RemoteException {
        for (InterfaceClient user : listClient)
        {
                user.orderNewFrameDiscussion();
        }
    }
}
