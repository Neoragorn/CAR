/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_server;

import Models.MessageDiscussion;
import Models.User;
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

    public InterfaceImplServer() throws RemoteException {
        super();
    }

    @Override
    public void NotifyAll(String msg, String login, InterfaceClient clOrigin) throws RemoteException {
        for (User user : this.mapClient.keySet()) {
            System.out.println("user is +> " + user.getPseudo());
            if (user.getClInter() != null) {
                System.out.println("He has an interfaceclient ! ");
                InterfaceClient cl = user.getClInter();
                cl.Receive(login, msg);
            }
        }
    }

    @Override
    public boolean Connect(String login, String mdp, InterfaceClient cl) throws RemoteException {
        if (connectedCl.containsKey(login)) {
            return false;
        }
        try {
            User user = UserBdd.getUser(login, mdp);
            user.setClInter(cl);;
            mapClient.put(user, cl);
            System.out.println("Client connected : " + user.getPseudo());
            NotifyAll(" s'est connecté", user.getPseudo(), cl);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(InterfaceImplServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void Disonnect(String login, InterfaceClient cl) throws RemoteException {
        User userremove = null;
        for (User user : this.mapClient.keySet()) {
            if (user.getPseudo().equals(login)) {
                userremove = user;
            }
        }
        if (userremove != null) {
            this.mapClient.remove(userremove);
        }
        connectedCl.remove(login);
        listClient.remove(cl);
        NotifyAll(" s'est déconnecté", login, cl);
    }

    @Override
    public void Send(String message, String login, InterfaceClient cl) throws RemoteException {
        User userremove = null;
        for (User user : this.mapClient.keySet()) {
            if (user.getPseudo().equals(login)) {
                System.out.println("User is => " + user.getPseudo());
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
}
