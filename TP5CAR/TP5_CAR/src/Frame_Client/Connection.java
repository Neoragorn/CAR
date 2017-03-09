/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame_Client;

import Bean_Client.UserBean;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import tp5_car_interface.InterfaceClient;
import tp5_car_interface.InterfaceServer;

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
        TFPseudo = new JTextField("Neor");
        TFPassword = new JPasswordField("123456789");
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

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            Pseudo = TFPseudo.getText();
            Password = TFPassword.getText();            
            if (UserBean.getInstance().connectUser(Pseudo, Password)) {
                Home home = new Home();
                MyFrame.getInstance().getFrame().dispose();
                MyFrame.getInstance().setFrame(new JFrame("Welcome in Messenger"));
                MyFrame.getInstance().changeFrame(home);
            } else {
                MyFrame.getInstance().changeFrame(new WrongLoginPwd());
            }
        }
    }

}
