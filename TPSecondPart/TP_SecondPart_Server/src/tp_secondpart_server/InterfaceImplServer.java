/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_server;

import Models.Category;
import Models.DiscussionGroup;
import Models.Message;
import Models.MessageDiscussion;
import Models.User;
import Persistence.CategoryBdd;
import Persistence.DiscussionGroupBdd;
import Persistence.MessageDiscussionBdd;
import Persistence.UserBdd;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_secondpart_interface.InterfaceClient;
import tp_secondpart_interface.InterfaceServer;

/**
 *
 * @author casier
 */
public class InterfaceImplServer implements InterfaceServer {

    private HashMap<User, InterfaceClient> mapClient = new HashMap();
    private ArrayList<InterfaceClient> listClient = new ArrayList();
    private ArrayList<User> listUser = new ArrayList();
    private HashMap<String, String> connectedCl = new HashMap();
    private User user = null;

    public InterfaceImplServer() throws RemoteException {
        super();
    }

    public void displayMap() {
        for (User user : this.mapClient.keySet()) {
            System.out.println("user connected : " + user.getPseudo());
        }
    }

    @Override
    public User createUser() throws RemoteException {

        User usertmp = this.user;
        this.user = null;
        return usertmp;
    }

    @Override
    public void NotifyAll(String msg, String login, InterfaceClient clOrigin) throws RemoteException {
        for (User user : this.mapClient.keySet()) {
            if (user.getClInter() != null) {
                InterfaceClient cl = user.getClInter();
                cl.Receive(login, msg);
            }
        }
    }

    @Override
    public boolean Connect(String login, String mdp, InterfaceClient cl) throws RemoteException {
        for (User user : this.mapClient.keySet()) {
            if (user.getPseudo().equals(login)) {
                System.out.println("the user is already connected in the map");
                return false;
            }
        }
        try {
            User user = UserBdd.getUser(login, mdp);
            user.setClInter(cl);;
            mapClient.put(user, cl);
            this.user = user;
            displayMap();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(InterfaceImplServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void Disonnect(String login, InterfaceClient cl) throws RemoteException {
        System.out.println("ENTERED IN DISCONNECT");
        User userremove = null;
        NotifyAll(" s'est déconnecté", login, cl);
        for (User user : this.mapClient.keySet()) {
            System.out.println("login is " + login);
            System.out.println("User login is " + user.getPseudo());
            if (user.getPseudo().equals(login)) {
                System.out.println("found the user to disconect => " + user.getPseudo());
                userremove = user;
            }
        }
        if (userremove != null) {
            System.out.println("removed ! ");
            this.mapClient.remove(userremove);
        }
        displayMap();
        connectedCl.remove(login);
        listClient.remove(cl);
    }

    @Override
    public void Send(String message, String login, InterfaceClient cl) throws RemoteException {
        User userremove = null;
        for (User user : this.mapClient.keySet()) {
            if (user.getPseudo().equals(login)) {
                userremove = user;
            }
        }
        if (userremove != null) {
            java.sql.Date date = new java.sql.Date(0);
            MessageDiscussion msg = new MessageDiscussion(message, userremove.getPseudo(), date);
            MessageDiscussionBdd.insertMessageIntoDiscussion(msg, userremove);
        }
        NotifyAll(message, login, cl);
    }

    @Override
    public ArrayList<MessageDiscussion> recoverDiscussionMessage() throws RemoteException {
        ArrayList<MessageDiscussion> listmsg = MessageDiscussionBdd.getPastMessageFromDiscussion();
        return listmsg;
    }

    @Override
    public ArrayList<MessageDiscussion> getMessageFromDiscussion(DiscussionGroup discussion) throws RemoteException {
        ArrayList<MessageDiscussion> listmsg = MessageDiscussionBdd.getMessageFromDiscussion(discussion);
        return listmsg;
    }

    @Override
    public ArrayList<User> getDiscussionUsers(int id) throws RemoteException {
        ArrayList<User> users = DiscussionGroupBdd.getDiscussionGroupUserById(id);
        return users;
    }

    @Override
    public ArrayList<Category> getCategoryUser(int id) throws RemoteException {
        ArrayList<Category> cat = CategoryBdd.getCategoryByUserId(id);
        return cat;
    }

    @Override
    public ArrayList<Message> getMessageUser(int id) throws RemoteException {
        ArrayList<Message> msglist = UserBdd.getPrivateMessageById(id);
        return msglist;
    }

    @Override
    public void inscription(User user) throws RemoteException {
        UserBdd.insertUser(user);
    }
}
