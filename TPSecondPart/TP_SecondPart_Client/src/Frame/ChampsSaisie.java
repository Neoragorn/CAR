package Frame;

import Bean.UserBean;
import Models.User;
import Persistence.UserBdd;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChampsSaisie extends JPanel implements ActionListener {

    JButton validation;
    JButton quitter;
    private ArrayList<String> info = new ArrayList();
    static JTextField TFPseudo;
    static JTextField TFMail;
    static JPasswordField TFPassword;
    static String Pseudo;
    static String Mail;
    static String Password;

    public ChampsSaisie() {
        setLayout(null);
        setPreferredSize(new Dimension(500, 300));
        validation = new JButton("Valid");
        quitter = new JButton("Quit");
        TFPseudo = new JTextField("Pseudo");
        TFMail = new JTextField("Mail");
        TFPassword = new JPasswordField();
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);

        p1.add(validation);
        validation.setBounds(250, 200, 150, 20);
        validation.addActionListener(this);

        p1.add(quitter);
        quitter.setBounds(50, 200, 150, 20);
        quitter.addActionListener(this);

        TFPseudo.setBackground(new Color(100, 100, 100));
        TFPseudo.setForeground(new Color(255, 255, 255));

        p1.add(TFPseudo);
        p1.add(TFMail);
        p1.add(TFPassword);

        TFPseudo.setBounds(200, 50, 200, 30);
        TFMail.setBounds(200, 100, 200, 30);
        TFPassword.setBounds(200, 150, 200, 30);
        
        p1.setBounds(0, 0, 500, 300);
        add(p1);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Valid")) {
            Pseudo = TFPseudo.getText();
            Mail = TFMail.getText();
            Password = TFPassword.getText();
            info.add(Pseudo);
            info.add(Mail);
            info.add(Password);
            UserBean.getInstance().addUserBdd(info);
            MyFrame.getInstance().changeFrame(new ChoixConnection());
        }
        else if (e.getActionCommand().equals("Quit")) {
            MyFrame.getInstance().quit();
        }
    }

    public ArrayList<String> getInfo() {
        return this.info;
    }
}
