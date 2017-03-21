package Frame;

import Models.MessageDiscussion;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import tp_secondpart_client.Client;

public class Home extends JPanel implements ActionListener, ListSelectionListener {

    private JButton quitter;
    private JButton answer;
    private JLabel pseudo;
    private JEditorPane discussionField;
    private JTextField TFMessage;

    public void displayButtonAndInformation() throws RemoteException, NotBoundException, NoSuchAlgorithmException {
        pseudo = new JLabel("Pseudo : " + Client.getInstance().getUser().getPseudo());
        pseudo.setOpaque(true);
        pseudo.setBounds(20, 10, 150, 20);

        discussionField = new JEditorPane();
        discussionField.setBounds(400, 40, 700, 520);

        TFMessage = new JTextField();
        TFMessage.setBounds(80, 300, 200, 60);

        quitter = new JButton("Quit");
        quitter.setBounds(100, 500, 100, 50);
        quitter.addActionListener(this);

        answer = new JButton("Answer");
        answer.setBounds(80, 420, 200, 50);
        answer.addActionListener(this);

    }

    public void updateDiscussion() {
        try {
            Document doc = discussionField.getDocument();
            ArrayList<MessageDiscussion> msgList = Client.getInstance().getUser().getStub().recoverDiscussionMessage();
            for (MessageDiscussion msg : msgList) {
                doc.insertString(doc.getLength(), "[" + msg.getTime() + "] " + msg.getAuteur() + ": " + msg.getMessage() + "\n", null);

            }
            discussionField.revalidate();
            discussionField.repaint();
        } catch (RemoteException | NotBoundException | NoSuchAlgorithmException | BadLocationException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Home() throws RemoteException, NotBoundException, NoSuchAlgorithmException {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 600));

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);

        displayButtonAndInformation();
        Document doc = discussionField.getDocument();
        ArrayList<MessageDiscussion> msgList = Client.getInstance().getUser().getStub().recoverDiscussionMessage();
        for (MessageDiscussion msg : msgList) {
            try {
                doc.insertString(doc.getLength(), "[" + msg.getTime() + "] " + msg.getAuteur() + ": " + msg.getMessage() + "\n", null);
            } catch (BadLocationException ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

        add(pseudo);
        add(answer);
        add(quitter);
        add(TFMessage);
        add(discussionField);

        p1.setBounds(0, 0, 1500, 800);
        add(p1);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Quit")) {
            try {
                Client.getInstance().getUser().getStub().Disonnect(Client.getInstance().getUser().getPseudo(), Client.getInstance().getUser().getClInter());
            } catch (RemoteException | NotBoundException | NoSuchAlgorithmException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
            MyFrame.getInstance().quit();
            System.exit(0);
            try {
                Client.getInstance().getMyThread().stopThread();
            } catch (RemoteException | NotBoundException | NoSuchAlgorithmException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getActionCommand().equals("Answer")) {
            try {
                String messageToAdd = TFMessage.getText();
                Client.getInstance().getMyThread().setInterreuptedCt(true);
                Client.getInstance().getUser().getStub().Send(messageToAdd, Client.getInstance().getUser().getPseudo(), Client.getInstance().getUser().getClInter());

            } catch (Exception ex) {
                Logger.getLogger(Home.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
