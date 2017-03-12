/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_client;

import tp_secondpart_interface.InterfaceClient;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import tp_secondpart_interface.InterfaceServer;

/**
 *
 * @author casier
 */
public class Client {

    private String login = "";

    public Client() throws RemoteException, NotBoundException, NoSuchAlgorithmException {
        Registry registry = LocateRegistry.getRegistry(4020);
        InterfaceServer stub = (InterfaceServer) registry.lookup("mini-chat");
        InterfaceClient clInter = new InterfaceImplClient();
        Scanner sc = new Scanner(System.in);
        boolean connected = false;
        while (!connected) {
            System.out.println("Choisissez un login");
            this.login = sc.nextLine();
            System.out.println("Choisissez un mot de passe");
            String pwd = sc.nextLine();
            connected = stub.Connect(login, pwd, clInter);
            if (!connected) {
                System.out.println("Erreur, ce login est déjà conecté. Recommencez.");
            } else {
                System.out.println("Vous êtes connecté !");
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
}
