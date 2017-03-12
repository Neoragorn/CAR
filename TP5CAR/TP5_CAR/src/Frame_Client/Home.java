package Frame_Client;

import Bean_Client.DiscussionGroupBean;
import Bean_Client.UserBean;
import Models.DiscussionGroup;
import Models.Friend;
import Models.Message;
import Models.User;
import Persistence.MessageDiscussionGroupVirtualProxy;
import Persistence.UserDiscussionGroupVirtualProxy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Home extends JPanel implements ActionListener, ListSelectionListener {

    private JButton quitter;
    private JButton createDiscussion;
    private JButton profile;
    private JButton sendMessage;
    private JButton answer;
    private JButton manage;
    private JButton joinDiscussion;
    private JButton joinNewDiscussion;
    private JButton searchUser;
    private JButton searchCategory;

    private JTextField TFRechercheUser;
    private JTextField TFRechercheCategoryUser;

    private DefaultListModel listJoinedDiscussion;
    private DefaultListModel listNotJoinedDiscussion;
    private DefaultListModel listFriend;
    private DefaultListModel boiteReception;
    private DefaultListModel listSearchResult;
    private DefaultListModel listSearchCategoryResult;

    private JList joinedDiscussionGroup;
    private JList notJoinedDiscussionGroup;
    private JList friends;
    private JList privateMessage;
    private JList searchResult;
    private JList searchCategoryResult;

    private JLabel rechercheUser;
    private JLabel rechercheCategoryUser;
    private JLabel pseudo;
    private JLabel reception;
    private JLabel yourFriends;
    private JLabel joinNewDiscussionGroup;
    private JLabel yourDiscussionGroup;

    private JScrollPane searchScroll = new JScrollPane();
    private JScrollPane searchScrollCategory = new JScrollPane();

    public void displayButtonAndInformation() {
        pseudo = new JLabel("Pseudo : " + UserBean.getInstance().getUser().getPseudo());
        pseudo.setOpaque(true);
        pseudo.setBounds(20, 10, 150, 20);

        rechercheUser = new JLabel("Search for a User");
        rechercheUser.setOpaque(true);
        rechercheUser.setBounds(1470, 20, 150, 20);

        rechercheCategoryUser = new JLabel("Search for a user by category");
        rechercheCategoryUser.setOpaque(true);
        rechercheCategoryUser.setBounds(1470, 400, 250, 20);

        yourFriends = new JLabel("Your Friends");
        yourFriends.setOpaque(true);
        yourFriends.setBounds(70, 70, 100, 20);

        joinNewDiscussionGroup = new JLabel("Join a new Discussion Group");
        joinNewDiscussionGroup.setOpaque(true);
        joinNewDiscussionGroup.setBounds(640, 10, 300, 20);

        yourDiscussionGroup = new JLabel("Your Discussion Group");
        yourDiscussionGroup.setOpaque(true);
        yourDiscussionGroup.setBounds(640, 380, 250, 20);

        reception = new JLabel("Your private messages");
        reception.setOpaque(true);
        reception.setBounds(70, 380, 200, 20);

        TFRechercheUser = new JTextField();
        TFRechercheUser.setBounds(1470, 40, 200, 50);

        TFRechercheCategoryUser = new JTextField();
        TFRechercheCategoryUser.setBounds(1470, 430, 200, 80);

        quitter = new JButton("Quit");
        quitter.setBounds(100, 700, 100, 50);
        quitter.addActionListener(this);

        searchUser = new JButton("Search");
        searchUser.setBounds(1470, 100, 130, 60);
        searchUser.addActionListener(this);

        searchCategory = new JButton("Search by Category");
        searchCategory.setBounds(1470, 520, 180, 60);
        searchCategory.addActionListener(this);

        sendMessage = new JButton("Send Message");
        sendMessage.setBounds(80, 310, 200, 50);
        sendMessage.addActionListener(this);

        answer = new JButton("Answer");
        answer.setBounds(80, 620, 200, 50);
        answer.addActionListener(this);

        profile = new JButton("Profile");
        profile.setBounds(200, 10, 80, 30);
        profile.addActionListener(this);

        manage = new JButton("Manage");
        manage.setBounds(300, 10, 80, 30);
        manage.addActionListener(this);

        joinNewDiscussion = new JButton("Join New Discussion");
        joinNewDiscussion.setBounds(700, 300, 200, 50);
        joinNewDiscussion.addActionListener(this);

        createDiscussion = new JButton("Create Discussion");
        createDiscussion.setBounds(1000, 690, 200, 50);
        createDiscussion.addActionListener(this);

        joinDiscussion = new JButton("Join Discussion");
        joinDiscussion.setBounds(700, 690, 200, 50);
        joinDiscussion.addActionListener(this);

    }

    public void displayList() {
        listJoinedDiscussion = new DefaultListModel();
        listNotJoinedDiscussion = new DefaultListModel();
        boiteReception = new DefaultListModel();
        listSearchResult = new DefaultListModel();
        listSearchCategoryResult = new DefaultListModel();

        try {
            ArrayList<Message> privateMsg = UserBean.getInstance().getUser().getStub().recoverMessageUser(UserBean.getInstance().getUser().getIdUser());
            System.out.println("entered private message reciovering");
            for (Message msg : privateMsg) {
                System.out.println(msg.getMessage());
                boiteReception.addElement("[" + msg.getDate() + "] " + msg.getDestinataire().getPseudo() + " : " + msg.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            UserBean.getInstance().getUser().getClInter().RecoverJoinedDiscussion();
            for (DiscussionGroup discusionGroup : DiscussionGroupBean.getInstance().getJoinedDiscussionGroup()) {
                listJoinedDiscussion.addElement("-->" + discusionGroup.getTitle() + "           ->" + discusionGroup.getDescription());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            UserBean.getInstance().getUser().getClInter().RecoverDiscussion();
            for (DiscussionGroup discusionGroup : DiscussionGroupBean.getInstance().getNotJoinedDiscussionGroup()) {
                listNotJoinedDiscussion.addElement("-->" + discusionGroup.getTitle() + "           ->" + discusionGroup.getDescription());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        //A modifier
        listFriend = new DefaultListModel();
        ArrayList<Friend> userFriends = UserBean.getInstance().getUser().getFriends();
        for (Friend friend : userFriends) {
            listFriend.addElement(friend.getPseudo() + "    \t\t\tMail : " + friend.getMail());
        }

        joinedDiscussionGroup = new JList(listJoinedDiscussion);
        joinedDiscussionGroup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        joinedDiscussionGroup.setSelectedIndex(1);
        joinedDiscussionGroup.addListSelectionListener(this);
        joinedDiscussionGroup.setVisibleRowCount(5);

        notJoinedDiscussionGroup = new JList(listNotJoinedDiscussion);
        notJoinedDiscussionGroup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notJoinedDiscussionGroup.setSelectedIndex(1);
        notJoinedDiscussionGroup.addListSelectionListener(this);
        notJoinedDiscussionGroup.setVisibleRowCount(5);

        friends = new JList(listFriend);
        friends.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        friends.setSelectedIndex(0);
        friends.addListSelectionListener(this);
        friends.setVisibleRowCount(5);

        privateMessage = new JList(boiteReception);
        privateMessage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        privateMessage.setSelectedIndex(0);
        privateMessage.addListSelectionListener(this);
        privateMessage.setVisibleRowCount(5);

        searchResult = new JList(listSearchResult);
        searchResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResult.setSelectedIndex(1);
        searchResult.addListSelectionListener(this);
        searchResult.setVisibleRowCount(5);

        searchCategoryResult = new JList(listSearchCategoryResult);
        searchCategoryResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchCategoryResult.setSelectedIndex(1);
        searchCategoryResult.addListSelectionListener(this);
        searchCategoryResult.setVisibleRowCount(5);
    }

    public Home() {
        setLayout(null);
        setPreferredSize(new Dimension(1800, 1200));

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);

        displayButtonAndInformation();
        displayList();

        JScrollPane scrollDiscussion = new JScrollPane(joinedDiscussionGroup);
        scrollDiscussion.setBounds(650, 400, 800, 250);

        JScrollPane scrollNotJoinedDiscussion = new JScrollPane(notJoinedDiscussionGroup);
        scrollNotJoinedDiscussion.setBounds(650, 40, 800, 250);

        JScrollPane scrollFriend = new JScrollPane(friends);
        scrollFriend.setBounds(50, 100, 500, 200);

        JScrollPane scrollPrivateMessage = new JScrollPane(privateMessage);
        scrollPrivateMessage.setBounds(50, 410, 500, 200);

        add(yourFriends);
        add(reception);
        add(scrollPrivateMessage);
        add(scrollNotJoinedDiscussion);
        add(pseudo);
        add(answer);
        add(scrollDiscussion);
        add(scrollFriend);
        add(sendMessage);
        add(quitter);
        add(yourDiscussionGroup);
        add(joinNewDiscussion);
        add(profile);
        add(createDiscussion);
        add(joinDiscussion);
        add(joinNewDiscussionGroup);
        add(TFRechercheUser);
        add(rechercheUser);
        add(searchUser);
        add(TFRechercheCategoryUser);
        add(searchCategory);
        add(rechercheCategoryUser);
        if (UserBean.getInstance().getUser().getPseudo().equals("admin")) {
            add(manage);
        }
        p1.setBounds(0, 0, 1500, 800);
        add(p1);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (friends.getSelectedIndex() == -1) {
                sendMessage.setEnabled(false);

            } else {
                sendMessage.setEnabled(true);
            }
            if (privateMessage.getSelectedIndex() == -1) {
                answer.setEnabled(false);

            } else {
                answer.setEnabled(true);
            }
        }
    }

    public void fillResultCategorySearch() {
        String searching = TFRechercheCategoryUser.getText();
        try {
            UserBean.getInstance().launchSearchUserByCategory(searching);
            if (UserBean.getInstance().getSearchedListUser() != null) {
                this.listSearchCategoryResult.clear();
                for (User u : UserBean.getInstance().getSearchedListUser()) {
                    this.listSearchCategoryResult.addElement(u.getPseudo());
                }
                this.searchCategoryResult.setModel(listSearchCategoryResult);
                this.searchCategoryResult.revalidate();
                this.searchScrollCategory.setViewportView(searchCategoryResult);
                searchScrollCategory.setBounds(1470, 600, 250, 200);
                Border b = BorderFactory.createLineBorder(Color.BLACK);
                b.paintBorder(this.searchScrollCategory, this.getGraphics(), 1470, 180, 250, 200);
                searchScrollCategory.setBorder(b);
                add(searchScrollCategory);
                this.revalidate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fillResultUserSearch() {
        String searching = TFRechercheUser.getText();
        try {
            UserBean.getInstance().launchSearchUser(searching);
            if (UserBean.getInstance().getSearchedListUser() != null) {
                this.listSearchResult.clear();
                for (User u : UserBean.getInstance().getSearchedListUser()) {
                    this.listSearchResult.addElement(u.getPseudo());
                }
                this.searchResult.setModel(listSearchResult);
                this.searchResult.revalidate();
                this.searchScroll.setViewportView(searchResult);
                searchScroll.setBounds(1470, 180, 250, 200);
                Border b = BorderFactory.createLineBorder(Color.BLACK);
                b.paintBorder(this.searchScroll, this.getGraphics(), 1470, 180, 250, 200);
                searchScroll.setBorder(b);
                add(searchScroll);
                this.revalidate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Quit")) {
            MyFrame.getInstance().quit();
        }
        if (e.getActionCommand().equals("Search")) {
            fillResultUserSearch();
        }
        if (e.getActionCommand().equals("Search by Category")) {
            fillResultCategorySearch();
        }
        if (e.getActionCommand().equals("Manage")) {
            MyFrame.getInstance().changeFrame(new Manage());
        }
        if (e.getActionCommand().equals("Profile")) {
            MyFrame.getInstance().changeFrame(new Profile());
        }
        if (e.getActionCommand().equals("Send Message")) {
            MyFrame.getInstance().changeFrame(new SendMessage(friends.getSelectedIndex()));
        }
        if (e.getActionCommand().equals("Answer")) {
            MyFrame.getInstance().changeFrame(new AnswerMessage(privateMessage.getSelectedIndex()));
        }
        if (e.getActionCommand().equals("Create Discussion")) {
            try {
                MyFrame.getInstance().getFrame().setVisible(false);
                MyFrame createDiscussion = new MyFrame("Create Discussion");
                MyFrame.getInstance().setSecondMyFrame(createDiscussion);
                createDiscussion.startPoint(new CreateDiscussion());
            } catch (Exception err) {
                System.out.println(err);
            }
        }
        if (e.getActionCommand().equals("Join Discussion")) {
            try {
                DiscussionGroup discu = DiscussionGroupBean.getInstance().getJoinedDiscussionGroup().get(joinedDiscussionGroup.getSelectedIndex());
                if (discu != null) {
                    if (!discu.equals(DiscussionGroupBean.getInstance().getDiscussion())) {
                        discu.setMembers(new UserDiscussionGroupVirtualProxy(discu.getIdDiscussion()));
                        discu.getMembers().initialize();
                        MessageDiscussionGroupVirtualProxy msgProxy = new MessageDiscussionGroupVirtualProxy();
                        msgProxy.initialize(discu);
                        discu.setMessagesProxy(msgProxy);
                        DiscussionGroupBean.getInstance().setDiscussion(discu);
                    }
                    DiscussionGroupBean.getInstance().insertAssoUserDiscu(UserBean.getInstance().getUser());
                    DiscussionGroupBean.getInstance().checkUserModerator();
                    MyFrame.getInstance().changeFrame(new Discussion());
                }
            } catch (Exception err) {
                System.out.println(err);
            }
        }
        if (e.getActionCommand().equals("Join New Discussion")) {
            try {
                System.out.println("entered new discussion");
                DiscussionGroup discu = DiscussionGroupBean.getInstance().getNotJoinedDiscussionGroup().get(notJoinedDiscussionGroup.getSelectedIndex());
                if (discu != null) {
                    if (!discu.equals(DiscussionGroupBean.getInstance().getDiscussion())) {
                        discu.setMembers(new UserDiscussionGroupVirtualProxy(discu.getIdDiscussion()));
                        discu.getMembers().initialize();
                        MessageDiscussionGroupVirtualProxy msgProxy = new MessageDiscussionGroupVirtualProxy();
                        msgProxy.initialize(discu);
                        discu.setMessagesProxy(msgProxy);
                        DiscussionGroupBean.getInstance().setDiscussion(discu);
                    }
                    DiscussionGroupBean.getInstance().insertAssoUserDiscu(UserBean.getInstance().getUser());
                    DiscussionGroupBean.getInstance().checkUserModerator();
                    MyFrame.getInstance().changeFrame(new Discussion());
                }
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }
}
