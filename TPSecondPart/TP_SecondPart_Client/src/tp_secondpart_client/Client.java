/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_secondpart_client;

import Frame.ChoixConnection;
import Frame.MyFrame;
import Models.User;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import javax.swing.JPanel;

/**
 *
 * @author casier
 */
public class Client {

    private String login = "";
    private User user = null;
    private static Client inst;
    private boolean connected = false;
    private MyThread myThread;

    public void setMyThread(MyThread myThread) {
        this.myThread = myThread;
    }

    
    static public Client getInstance() throws RemoteException, NotBoundException, NoSuchAlgorithmException {
        if (inst == null) {
            inst = new Client();
        }
        return inst;
    }

    public Client() throws RemoteException, NotBoundException, NoSuchAlgorithmException {
        myThread = new MyThread();
//        myThread.start();
        MyFrame myF = new MyFrame();
        MyFrame.setInst(myF);
        JPanel jp = new ChoixConnection();
        //Ecran pour soit se connecter, soit s'inscrire
        myF.startPoint(jp);
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

    public MyThread getMyThread() {
        return myThread;
    }

}
