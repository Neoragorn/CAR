package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Bean.AdminBean;
import Bean.UserBean;
import Models.Friend;
import Models.User;
import Persistence.AdminBdd;

public class Manage extends JPanel implements ActionListener, ListSelectionListener {
	
    private JList users;
    
	private JButton removeUser;
	private JButton addUser;
	private JButton modifyUser;
    private JButton returnHome;
    private JButton refresh;
    private JButton createUser;

    private JTextField pseudo;
    private JTextField mail;
    private JTextField password;
    
    private String pseudovalue;
    private String mailvalue;
    private String passwordvalue;
    
    private JTextField pseudoNewUser;
    private JTextField mailNewUser;
    private JTextField passwordNewUser;
    
    private JLabel pseudoLabel;
    private JLabel pseudoLabel2;
    private JLabel mailLabel;
    private JLabel mailLabel2;
    private JLabel passwordLabel;
    private JLabel passwordLabel2;


    private DefaultListModel listUsers;
	
	public Manage(){
		setLayout(null);
        setPreferredSize(new Dimension(1000, 800));

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setOpaque(false);
        p1.setBounds(0, 0, 1000, 800);
        
        pseudoLabel = new JLabel("Pseudo");
        pseudoLabel.setOpaque(true);
        pseudoLabel.setBounds(50, 40, 200, 30);
        
        pseudoLabel2 = new JLabel("Pseudo");
        pseudoLabel2.setOpaque(true);
        pseudoLabel2.setBounds(50, 140, 200, 30);
        
        mailLabel = new JLabel("Mail");
        mailLabel.setOpaque(true);
        mailLabel.setBounds(50, 120, 200, 30);
        
        mailLabel2 = new JLabel("Mail");
        mailLabel2.setOpaque(true);
        mailLabel2.setBounds(70, 70, 100, 20);
        
        passwordLabel = new JLabel("Password");
        passwordLabel.setOpaque(true);
        passwordLabel.setBounds(50, 200, 200, 30);
        
        passwordLabel2 = new JLabel("Password");
        passwordLabel2.setOpaque(true);
        passwordLabel2.setBounds(70, 70, 100, 20);
        
        listUsers = new DefaultListModel();
        ArrayList<User> usersList = UserBean.getInstance().getAllUser();
        for (User user : usersList) {
            listUsers.addElement(user.getPseudo() + "    \t\t\tMail : " + user.getMail());
        }

        users = new JList(listUsers);
        users.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        users.setSelectedIndex(0);
        users.addListSelectionListener(this);
        users.setVisibleRowCount(5);

        JScrollPane scrollUser = new JScrollPane(users);
        scrollUser.setBounds(300, 100, 250, 400);
        
        removeUser = new JButton("Remove User");
        removeUser.setBounds(300, 10, 110, 50);
        removeUser.setForeground(Color.RED);
        removeUser.addActionListener(this);
        
        modifyUser = new JButton("Modify User");
        modifyUser.setBounds(150, 350, 110, 50);
        modifyUser.setForeground(Color.BLUE);
        modifyUser.addActionListener(this);
        
        returnHome = new JButton("Return");
        returnHome.setBounds(50, 450, 100, 50);
        returnHome.addActionListener(this);
        
        refresh = new JButton("Refresh Values");
        refresh.setBounds(50, 350, 100, 50);
        refresh.addActionListener(this);
        
        createUser = new JButton("Create new user");
        createUser.setBounds(700, 200, 100, 50);
        createUser.addActionListener(this);

 
        pseudo = new JTextField(pseudovalue);
        mail = new JTextField(mailvalue);
        password = new JTextField("password");
        pseudo.setBounds(50, 80, 200, 30);
        mail.setBounds(50, 160, 200, 30);
        password.setBounds(50, 240, 200, 30);
        
        pseudoNewUser = new JTextField("New Pseudo");
        mailNewUser = new JTextField("New Mail");
        passwordNewUser = new JTextField("New Password");
        pseudoNewUser.setBounds(700, 50, 200, 30);
        mailNewUser.setBounds(700, 100, 200, 30);
        passwordNewUser.setBounds(700, 150, 200, 30);
        
        
        p1.add(scrollUser);
        p1.add(returnHome);
        add(pseudoLabel);
       // add(pseudoLabel2);
        add(mailLabel);
      //  add(mailLabel2);
        add(passwordLabel);
      //  add(passwordLabel2);
        add(removeUser);
        add(modifyUser);
        add(pseudo);
        add(mail);
        add(password);
        add(pseudoNewUser);
        add(mailNewUser);
        add(passwordNewUser);
        add(refresh);
        add(createUser);
        add(p1);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getActionCommand().equals("Remove User")) {
		     ArrayList<User> usersList = UserBean.getInstance().getAllUser();
			 User SelectedUser = UserBean.getInstance().getAllUser().get(users.getSelectedIndex());
	            try {
					AdminBean.deleteUser(SelectedUser);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	        }	
		 if (e.getActionCommand().equals("Return")) {
	            try {
	                UserBean.getInstance().updateUserFriendInfo(UserBean.getInstance().getUser());
	                MyFrame.getInstance().changeFrame(new Home());
	            } catch (Exception err) {
	                System.out.println(err);
	            }
	     }
		 if (e.getActionCommand().equals("Refresh Values")) {
	            refreshValues();
	     }
		 if (e.getActionCommand().equals("Create new user")) {
			 try {
				AdminBean.CreateUser(pseudoNewUser.getText(), mailNewUser.getText(), passwordNewUser.getText());
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e1) {
				e1.printStackTrace();
			}
	     }
		 if (e.getActionCommand().equals("Modify User")) {
		     ArrayList<User> usersList = UserBean.getInstance().getAllUser();
			 User SelectedUser = UserBean.getInstance().getAllUser().get(users.getSelectedIndex());
	            try {
	            	User NewUser = new User(SelectedUser.getIdUser(), pseudo.getText(), password.getText(), mail.getText());
	            	System.out.println(NewUser.toString());
					AdminBean.ModifyUser(SelectedUser, NewUser.getPseudo(), NewUser.getMail(), NewUser.getPwd());
				} catch (SQLException | NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
	       }	
	}
		
	 public void valueChanged(ListSelectionEvent e) {
	        if (e.getValueIsAdjusting() == false) {

	            if (users.getSelectedIndex() == -1) {
	                removeUser.setEnabled(false);

	            } else {
	                removeUser.setEnabled(true);
	            }
	        }
	    }
	 
	 public void refreshValues(){
	     ArrayList<User> usersList = UserBean.getInstance().getAllUser();
		 User SelectedUser = UserBean.getInstance().getAllUser().get(users.getSelectedIndex());
	     pseudovalue = SelectedUser.getPseudo();
	     mailvalue = SelectedUser.getMail();
	     pseudo.setText(pseudovalue);
	     mail.setText(mailvalue);
	 }

}
