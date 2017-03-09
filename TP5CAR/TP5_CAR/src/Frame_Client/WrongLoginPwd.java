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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author sofian
 */
public class WrongLoginPwd extends JPanel implements ActionListener {

    static JTextField TFPseudo;
    static JPasswordField TFPassword;
    JButton boutonConnection;

    static String Pseudo;
    static String Password;

    private JLabel wrongMessage;

    public WrongLoginPwd() {
        setLayout(null);
        setPreferredSize(new Dimension(500, 300));

        boutonConnection = new JButton("Connect");
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);
        TFPseudo = new JTextField("Pseudo");
        TFPassword = new JPasswordField("Password");
        TFPseudo.setBackground(new Color(255, 255, 255));
        TFPseudo.setForeground(new Color(0, 0, 0));
        TFPseudo.setBounds(150, 50, 200, 30);
        TFPassword.setBounds(150, 100, 200, 30);

        p1.add(boutonConnection);
        boutonConnection.setBounds(150, 180, 150, 20);
        boutonConnection.addActionListener(this);

        wrongMessage = new JLabel("Wrong login or/and password. Try again");
        wrongMessage.setOpaque(true);
        wrongMessage.setBounds(150, 150, 300, 20);

        p1.add(boutonConnection);
        p1.add(TFPassword);
        p1.add(TFPseudo);
        p1.add(wrongMessage);
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