/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_server;

import Models.User;
import Persistence.UserBdd;
import tp5_car_interface.InterfaceServer;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
        User user = UserBdd.getUser(login, mdp);
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
}
