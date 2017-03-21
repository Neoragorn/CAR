/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import tp_secondpart_client.Client;
import tp_secondpart_client.InterfaceImplClient;
import tp_secondpart_client.MyThread;
import tp_secondpart_interface.InterfaceClient;
import tp_secondpart_interface.InterfaceServer;

public class Connection extends JPanel implements ActionListener {

    static JTextField TFPseudo;
    static JPasswordField TFPassword;
    JButton boutonConnection;

    static String Pseudo;
    static String Password;

    public Connection() {
        setLayout(null);
        setPreferredSize(new Dimension(500, 300));
        boutonConnection = new JButton("Connect");
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);
        TFPseudo = new JTextField("soso");
        TFPassword = new JPasswordField("123");
        TFPseudo.setBackground(new Color(255, 255, 255));
        TFPseudo.setForeground(new Color(0, 0, 0));
        TFPseudo.setBounds(150, 50, 200, 30);
        TFPassword.setBounds(150, 100, 200, 30);

        p1.add(boutonConnection);
        boutonConnection.setBounds(150, 180, 150, 20);
        boutonConnection.addActionListener(this);

        p1.add(boutonConnection);
        p1.add(TFPassword);
        p1.add(TFPseudo);
        p1.setBounds(0, 0, 500, 300);
        add(p1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            try {
                Pseudo = TFPseudo.getText();
                Password = TFPassword.getText();
                Registry registry = LocateRegistry.getRegistry(4020);
                InterfaceServer stub = (InterfaceServer) registry.lookup("mini-chat");
                InterfaceClient clInter = new InterfaceImplClient();
                Client.getInstance().setConnected(stub.Connect(Pseudo, Password, clInter));
                if (Client.getInstance().isConnected()) {
                    Client.getInstance().setUser(stub.createUser());
                    Client.getInstance().getUser().setRegistry(registry);
                    Client.getInstance().getUser().setStub(stub);
                    Client.getInstance().getUser().getStub().Send(" s'est connecté", Client.getInstance().getUser().getPseudo(), clInter);
                    Home home = new Home();
                    if (!Client.getInstance().getMyThread().isRunning()) {
                        Client.getInstance().getMyThread().start();
                    }
                    MyFrame.getInstance().quit();
                    MyFrame.getInstance().setFrame(new JFrame("Welcome in Messenger"));
                    MyFrame.getInstance().changeFrame(home);
                } else {
                    MyFrame.getInstance().changeFrame(new WrongLoginPwd());
                }
            } catch (RemoteException | NotBoundException | NoSuchAlgorithmException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
