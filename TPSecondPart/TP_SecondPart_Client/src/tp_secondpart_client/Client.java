/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_client;

import Models.MessageDiscussion;
import Models.User;
import tp_secondpart_interface.InterfaceClient;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import tp_secondpart_interface.InterfaceServer;

/**
 *
 * @author casier
 */
public class Client {

    private String login = "";
    private User user = null;
    private static Client inst;
    private boolean connected = false;
    
    
    static public Client getInstance() throws RemoteException, NotBoundException, NoSuchAlgorithmException {
        if (inst == null) {
            inst = new Client();
        }
        return inst;
    }
    
    public Client() throws RemoteException, NotBoundException, NoSuchAlgorithmException {
        Registry registry = LocateRegistry.getRegistry(4020);
        InterfaceServer stub = (InterfaceServer) registry.lookup("mini-chat");
        InterfaceClient clInter = new InterfaceImplClient();
        Scanner sc = new Scanner(System.in);
        while (!connected) {
            System.out.println("Choisissez un login");
            this.login = sc.nextLine();
            System.out.println("Choisissez un mot de passe");
            String pwd = sc.nextLine();
            connected = stub.Connect(login, pwd, clInter);
            this.user = stub.createUser();
            if (!connected) {
                System.out.println("Erreur, ce login est déjà conecté. Recommencez.");
            } else {
                System.out.println("Vous êtes connecté !");
            }
        }
        ArrayList<MessageDiscussion> listmgs = stub.recoverDiscussionMessage();
        if (!listmgs.isEmpty()) {
            for (MessageDiscussion msg : listmgs) {
                System.out.println(msg.getTime() + " | " + msg.getAuteur() + ": " + msg.getMessage());
            }
        }
        String str = "";
        while (true) {
            str = sc.nextLine();
            if (str.equals("/disco")) {
                stub.Disonnect(login, clInter);
                System.out.println("Vous êtes déconnecté. Au revoir !");
                return;
            } else {
                stub.Send(str, login, clInter);
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Client getInst() {
        return inst;
    }

    public static void setInst(Client inst) {
        Client.inst = inst;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    
}
