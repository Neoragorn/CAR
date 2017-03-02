/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp4_car;

import tp4_car_interface.InterfaceClient;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import tp4_car_interface.InterfaceServer;

/**
 *
 * @author casier
 */
public class Client {

    private String login = "";

    public Client() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(4010);
        InterfaceServer stub = (InterfaceServer) registry.lookup("Add");
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

    public Client(String login, String pwd, String str) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(4010);
        InterfaceServer stub = (InterfaceServer) registry.lookup("Add");
        InterfaceClient clInter = new InterfaceImplClient();
        Scanner sc = new Scanner(System.in);
        boolean connected = false;
        connected = stub.Connect(login, pwd, clInter);
        if (!connected) {
            System.out.println("Erreur, ce login est déjà conecté. Recommencez.");
            return;
        } else {
            System.out.println("Vous êtes connecté !");
        }
        stub.Send(str, login, clInter);
    }
}
