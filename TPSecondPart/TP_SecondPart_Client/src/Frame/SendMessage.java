/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import Bean.UserBean;
import Models.Friend;
import Models.Message;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author casier
 */
public class SendMessage extends JPanel implements ActionListener {

    private JButton returnHome;
    private JButton sendMessage;
    private static JTextField messageToSend;
    private static String message;
    private Friend destinataire;
    private JLabel pseudoDestinataire;

    public SendMessage(int index) {
        setLayout(null);
        setPreferredSize(new Dimension(500, 400));

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);
        this.destinataire = UserBean.getInstance().getUser().getFriends().get(index);
        messageToSend = new JTextField();
        messageToSend.setBounds(20, 50, 300, 150);

        returnHome = new JButton("Return");
        returnHome.setBounds(200, 300, 100, 50);
        returnHome.addActionListener(this);

        sendMessage = new JButton("Send Message");
        sendMessage.setBounds(200, 210, 150, 50);
        sendMessage.addActionListener(this);

        pseudoDestinataire = new JLabel("Envoyer un message à " + destinataire.getPseudo());
        pseudoDestinataire.setOpaque(true);
        pseudoDestinataire.setBounds(20, 10, 300, 20);

        p1.add(sendMessage);
        p1.add(returnHome);
        p1.add(messageToSend);
        p1.add(pseudoDestinataire);
        p1.setBounds(0, 0, 1000, 800);
        add(p1);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Send Message")) {
            this.message = messageToSend.getText();
            java.util.Date d1 = new java.util.Date();
            java.sql.Date d2 = new java.sql.Date(d1.getTime());

            Message msg = new Message(message, UserBean.getInstance().getUser(), destinataire, d2);
            UserBean.getInstance().sendMessage(msg);
            MyFrame.getInstance().changeFrame(new Home());
        }

        if (e.getActionCommand().equals("Return")) {
            try {
                UserBean.getInstance().updateUserFriendInfo(UserBean.getInstance().getUser());
                MyFrame.getInstance().changeFrame(new Home());
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }
}
