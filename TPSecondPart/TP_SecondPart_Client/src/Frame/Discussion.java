/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame;

import Bean.DiscussionGroupBean;
import Bean.UserBean;
import Models.MessageDiscussion;
import Models.User;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;

/**
 *
 * @author sofian
 */
public class Discussion extends JPanel implements ActionListener, ListSelectionListener {

    private DefaultListModel listMembers;

    private JEditorPane discussionField;
    private JList members;

    private JButton returnHome;
    private JButton AddMessage;
    private JButton leaveDiscussion;
    private JButton supressUser;

    private JTextField TFMessage;

    private JLabel discussionTitle;
    private JLabel AddMessageLabel;

    class RemoveUserDiscussionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = members.getSelectedIndex();
            ArrayList<User> listUser = DiscussionGroupBean.getInstance().getDiscussion().getMembers().getUsers();
            try {
                DiscussionGroupBean.getInstance().destroyUserFromDiscussion(listUser.get(index));
            } catch (Exception err) {
                System.out.println(err);
            }
            listMembers.remove(index);
            int size = listMembers.getSize();
            if (size == 0) { //Nobody's left, disable firing.
                supressUser.setEnabled(false);
            } else { //Select an index.
                if (index == listMembers.getSize()) {
                    //removed item in last position
                    index--;
                }
                members.setSelectedIndex(index);
                members.ensureIndexIsVisible(index);
            }
        }
    }

    public Discussion() {
        setLayout(null);
        setPreferredSize(new Dimension(1600, 800));
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);

        discussionTitle = new JLabel(DiscussionGroupBean.getInstance().getDiscussion().getTitle());
        discussionTitle.setOpaque(true);
        discussionTitle.setBounds(40, 10, 300, 20);

        AddMessageLabel = new JLabel("Ajouter un message");
        AddMessageLabel.setOpaque(true);
        AddMessageLabel.setBounds(190, 680, 300, 20);

        TFMessage = new JTextField();
        TFMessage.setBounds(190, 700, 400, 60);

        returnHome = new JButton("Return");
        returnHome.setBounds(30, 700, 100, 50);
        returnHome.addActionListener(this);

        supressUser = new JButton("Kick User");
        supressUser.setBounds(840, 50, 150, 50);
        supressUser.addActionListener(new RemoveUserDiscussionListener());

        leaveDiscussion = new JButton("Leave Discussion");
        leaveDiscussion.setBounds(1200, 700, 200, 50);
        leaveDiscussion.addActionListener(this);

        AddMessage = new JButton("Add Message");
        AddMessage.setBounds(600, 700, 200, 50);
        AddMessage.addActionListener(this);

        listMembers = new DefaultListModel();
        try {
            ArrayList<User> users = DiscussionGroupBean.getInstance().getDiscussion().getMembers().initialize();
            for (User user : users) {
                if (DiscussionGroupBean.getInstance().getDiscussion().getIdCreator() == user.getIdUser()) {
                    listMembers.addElement(user.getPseudo() + "   Moderator");
                } else {
                    listMembers.addElement(user.getPseudo());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        members = new JList(listMembers);
        members.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        members.setSelectedIndex(0);
        members.addListSelectionListener(this);
        members.setVisibleRowCount(5);

        discussionField = new JEditorPane();
        discussionField.setBounds(30, 50, 800, 600);

        try {
            Document doc = discussionField.getDocument();
            ArrayList<MessageDiscussion> msgList = DiscussionGroupBean.getInstance().getDiscussion().getMessagesProxy().getMessages();
            for (MessageDiscussion msg : msgList) {
                doc.insertString(doc.getLength(), "[" + msg.getTime() + "] " + msg.getAuteur() + ": " + msg.getMessage() + "\n", null);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        JScrollPane scrollMembers = new JScrollPane(members);
        scrollMembers.setBounds(1000, 50, 500, 600);

        p1.add(leaveDiscussion);
        p1.add(AddMessageLabel);
        p1.add(AddMessage);
        p1.add(discussionField);
        p1.setBounds(0, 0, 1600, 800);
        p1.add(returnHome);
        p1.add(scrollMembers);
        p1.add(discussionTitle);
        p1.add(TFMessage);
        if (DiscussionGroupBean.getInstance().isModerator()) {
            p1.add(supressUser);
        }
        add(p1);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return")) {
            try {
                MyFrame.getInstance().changeFrame(new Home());
            } catch (Exception err) {
                System.out.println(err);
            }
        }
        if (e.getActionCommand().equals("Leave Discussion") && !DiscussionGroupBean.getInstance().isModerator()) {
            try {
                DiscussionGroupBean.getInstance().destroyUserFromDiscussion(UserBean.getInstance().getUser());
                MyFrame.getInstance().changeFrame(new Home());
            } catch (Exception err) {
                System.out.println(err);
            }

        }
        if (e.getActionCommand().equals("Add Message")) {
            try {
                String messageToAdd = TFMessage.getText();
                java.util.Date d1 = new java.util.Date();
                java.sql.Date d2 = new java.sql.Date(d1.getTime());
                MessageDiscussion msg = new MessageDiscussion(messageToAdd, UserBean.getInstance().getUser().getPseudo(), d2);
                DiscussionGroupBean.getInstance().addMessageToDiscussion(DiscussionGroupBean.getInstance().getDiscussion(), msg, UserBean.getInstance().getUser());
                DiscussionGroupBean.getInstance().getDiscussion().getMessagesProxy().updateMessage(DiscussionGroupBean.getInstance().getDiscussion());
                MyFrame.getInstance().changeFrame(new Discussion());
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }
}
