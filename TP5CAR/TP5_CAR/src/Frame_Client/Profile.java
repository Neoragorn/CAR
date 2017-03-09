/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frame_Client;

import Bean_Client.UserBean;
import Models.Category;
import Models.Friend;
import Models.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author sofian
 */
public class Profile extends JPanel implements ActionListener, ListSelectionListener {

    private String pseudo;
    private String mail;

    static JTextField TFPseudo;
    static JTextField TFMail;
    private JList friends;
    private JList users;
    private JList categories;
    private JList categoriesUser;

    private JButton modifyMail;
    private JButton returnHome;
    private JButton addFriend;
    private JButton removeFriend;
    private JButton addCategory;
    private JButton removeCategory;

    static String Password;
    private DefaultListModel listFriend;
    private DefaultListModel listUsers;
    private DefaultListModel listCategory;
    private DefaultListModel listCategoryUser;

    class AddCategoryListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = categories.getSelectedIndex();
            Category cat = UserBean.getInstance().getAllCategoriesExceptUser().get(index);
            try {
                UserBean.getInstance().addCategory(cat);
            } catch (Exception err) {
                System.out.println(err);
            }
            listCategory.remove(index);
            int size = listCategory.getSize();
            if (size == 0) { //Nobody's left, disable firing.
                addCategory.setEnabled(false);
            } else { //Select an index.
                if (index == listCategory.getSize()) {
                    //removed item in last position
                    index--;
                }
                categories.setSelectedIndex(index);
                categories.ensureIndexIsVisible(index);
            }
        }
    }

    class RemoveCategoryListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = categoriesUser.getSelectedIndex();
            Category cat = UserBean.getInstance().getUser().getProxyCategory().getCategories().get(index);
            try {
                UserBean.getInstance().removeCategory(cat);
            } catch (Exception err) {
                System.out.println(err);
            }
            listCategoryUser.remove(index);
            int size = listCategoryUser.getSize();
            if (size == 0) { //Nobody's left, disable firing.
                removeCategory.setEnabled(false);
            } else { //Select an index.
                if (index == listCategoryUser.getSize()) {
                    //removed item in last position
                    index--;
                }
                categoriesUser.setSelectedIndex(index);
                categoriesUser.ensureIndexIsVisible(index);
            }
        }
    }

    class AddFriendListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = users.getSelectedIndex();

            for (User user : UserBean.getInstance().getAllUser()) {
                String pseudoMail = user.getPseudo() + " " + user.getMail();
                String userchosen = listUsers.elementAt(index).toString();
                if (pseudoMail.equals(userchosen)) {
                    try {
                        UserBean.getInstance().addFriend(user);
                    } catch (Exception err) {
                        System.out.println(err);
                    }
                    break;
                }
            }
            listUsers.remove(index);
            int size = listUsers.getSize();
            if (size == 0) { //Nobody's left, disable firing.
                addFriend.setEnabled(false);
            } else { //Select an index.
                if (index == listUsers.getSize()) {
                    //removed item in last position
                    index--;
                }
                users.setSelectedIndex(index);
                users.ensureIndexIsVisible(index);
            }
        }
    }

    class RemoveFriendListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = friends.getSelectedIndex();
            for (Friend f : UserBean.getInstance().getUser().getFriends()) {
                String pseudoMail = f.getPseudo() + "//Mail : " + f.getMail();
                if (pseudoMail.equals(listFriend.elementAt(index))) {
                    try {
                        UserBean.getInstance().removeFriend(f.getPseudo(), f.getMail());
                    } catch (Exception err) {
                        System.out.println(err);
                    }
                    break;
                }
            }
            listFriend.remove(index);
            int size = listFriend.getSize();
            if (size == 0) { //Nobody's left, disable firing.
                removeFriend.setEnabled(false);
            } else { //Select an index.
                if (index == listFriend.getSize()) {
                    //removed item in last position
                    index--;
                }
                friends.setSelectedIndex(index);
                friends.ensureIndexIsVisible(index);
            }
        }
    }

    public Profile() {
        setLayout(null);
        setPreferredSize(new Dimension(1800, 800));

        TFPseudo = new JTextField(UserBean.getInstance().getUser().getPseudo());
        TFMail = new JTextField(UserBean.getInstance().getUser().getMail());
        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);

        returnHome = new JButton("Return");
        returnHome.setBounds(30, 650, 100, 50);
        returnHome.addActionListener(this);

        modifyMail = new JButton("Modify Mail");
        modifyMail.setBounds(250, 70, 120, 50);
        modifyMail.addActionListener(this);

        addFriend = new JButton("Add Friend");
        addFriend.setBounds(50, 140, 200, 50);
        addFriend.addActionListener(new AddFriendListener());

        addCategory = new JButton("Add Category");
        addCategory.setBounds(600, 140, 200, 50);
        addCategory.addActionListener(new AddCategoryListener());

        removeCategory = new JButton("remove Category");
        removeCategory.setBounds(900, 140, 200, 50);
        removeCategory.addActionListener(new RemoveCategoryListener());

        removeFriend = new JButton("remove Friend");
        removeFriend.setBounds(320, 140, 200, 50);
        removeFriend.addActionListener(new RemoveFriendListener());

        TFPseudo.setBackground(new Color(100, 100, 100));
        TFPseudo.setForeground(new Color(255, 255, 255));
        TFPseudo.setBounds(30, 10, 200, 30);
        TFMail.setBounds(30, 90, 200, 30);

        listUsers = new DefaultListModel();
        ArrayList<User> usersList = UserBean.getInstance().getAllUser();
        for (User user : usersList) {
            listUsers.addElement(user.getPseudo() + " " + user.getMail());
        }

        listCategory = new DefaultListModel();
        try {
            UserBean.getInstance().getAllNotUserCategories();
            ArrayList<Category> categoriesExcept = UserBean.getInstance().getAllCategoriesExceptUser();
            for (Category cat : categoriesExcept) {
                listCategory.addElement(cat.getNom());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        listCategoryUser = new DefaultListModel();
        try {
            UserBean.getInstance().getUser().getProxyCategory().initialize();
            ArrayList<Category> categoriesUser = UserBean.getInstance().getUser().getProxyCategory().getCategories();
            for (Category cat : categoriesUser) {
                listCategoryUser.addElement(cat.getNom());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        users = new JList(listUsers);
        users.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        users.setSelectedIndex(0);
        users.addListSelectionListener(this);
        users.setVisibleRowCount(5);

        categories = new JList(listCategory);
        categories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categories.setSelectedIndex(0);
        categories.addListSelectionListener(this);
        categories.setVisibleRowCount(5);

        categoriesUser = new JList(listCategoryUser);
        categoriesUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriesUser.setSelectedIndex(0);
        categoriesUser.addListSelectionListener(this);
        categoriesUser.setVisibleRowCount(5);

        JScrollPane scrollCat = new JScrollPane(categories);
        scrollCat.setBounds(580, 200, 250, 400);

        JScrollPane scrollCatUser = new JScrollPane(categoriesUser);
        scrollCatUser.setBounds(870, 200, 250, 400);

        JScrollPane scrollUser = new JScrollPane(users);
        scrollUser.setBounds(300, 200, 250, 400);

        listFriend = new DefaultListModel();
        ArrayList<Friend> userFriends = UserBean.getInstance().getUser().getFriends();

        for (Friend friend : userFriends) {
            listFriend.addElement(friend.getPseudo() + "//Mail : " + friend.getMail());
        }

        friends = new JList(listFriend);
        friends.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        friends.setSelectedIndex(0);
        friends.addListSelectionListener(this);
        friends.setVisibleRowCount(5);

        JScrollPane scrollFriend = new JScrollPane(friends);
        scrollFriend.setBounds(30, 200, 250, 400);

        p1.add(removeCategory);
        p1.add(scrollCatUser);
        p1.add(scrollFriend);
        p1.add(scrollUser);
        p1.add(scrollCat);
        p1.add(addCategory);
        p1.add(returnHome);
        p1.add(removeFriend);
        p1.add(addFriend);
        p1.add(TFMail);
        p1.add(TFPseudo, BorderLayout.PAGE_END);
        p1.setBounds(0, 0, 1800, 800);
        p1.add(modifyMail);
        add(p1);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (friends.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeFriend.setEnabled(false);
            } else {
                //Selection, enable the fire button.
                removeFriend.setEnabled(true);
            }
            if (users.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                addFriend.setEnabled(false);
            } else {
                //Selection, enable the fire button.
                addFriend.setEnabled(true);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return")) {
            try {
                UserBean.getInstance().updateUserFriendInfo(UserBean.getInstance().getUser());
                MyFrame.getInstance().changeFrame(new Home());
            } catch (Exception err) {
                System.out.println(err);
            }
        }
        if (e.getActionCommand().equals("Modify Mail")) {
            String newMail = TFMail.getText();
            UserBean.getInstance().modifyMail(newMail);
        }
    }
}
