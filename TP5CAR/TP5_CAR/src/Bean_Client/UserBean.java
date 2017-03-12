package Bean_Client;

import Models.Category;
import Models.Friend;
import Models.Message;
import Models.User;
import Persistence.CategoryBdd;
import Persistence.MessageBdd;
import Persistence.UserBdd;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import tp5_car_client.InterfaceImplClient;

import tp5_car_interface.InterfaceClient;
import tp5_car_interface.InterfaceServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sofian
 */
public class UserBean {

    private Registry registry;
    private User user = null;
    private ArrayList<User> searchedListUser = new ArrayList();
    private ArrayList<Category> allCategoriesExceptUser = new ArrayList();
    private boolean connected = false;

    public UserBean() {

    }

    public static UserBean inst;

    static public UserBean getInstance() {
        if (inst == null) {
            inst = new UserBean();
        }
        return inst;
    }

    public ArrayList<User> getAllUser() {
        try {
            ArrayList<User> userList = UserBdd.getAllUser();
            return userList;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void modifyMail(String mail) {
        try {
            UserBdd.changeMail(this.user, mail);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getAllNotUserCategories() {
        try {
            this.allCategoriesExceptUser = CategoryBdd.getAllCategoryExceptUser(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean isUserConnected() {
        return connected;
    }

    public void removeFriend(String pseudo, String mail) throws SQLException {
        for (Friend f : this.user.getFriends()) {
            if (f.getPseudo().equals(pseudo) && f.getMail().equals(mail)) {
                UserBdd.removeFriend(user, f);
            }
        }
    }

    public void addFriend(User user) throws SQLException {
        Friend f = new Friend();
        f.setIdFriend(user.getIdUser());
        f.setPseudo(user.getPseudo());
        f.setMail(user.getMail());
        UserBdd.addFriend(this.user, f);
    }

    public void addCategory(Category cat) throws SQLException {
        CategoryBdd.insertAssoCategoryUser(user, cat);
    }

    public void removeCategory(Category cat) throws SQLException {
        CategoryBdd.deleteUserFromCategorie(user, cat);
    }

    public boolean connectUser(String pseudo, String pwd) {
        try {
            User user = new User();
            InterfaceServer stub;
            InterfaceClient clInter = new InterfaceImplClient();
            if (this.user == null) {

                if (registry == null) {
                    registry = LocateRegistry.getRegistry(4010);

                }
                stub = (InterfaceServer) registry.lookup("mini-chat");
                user = stub.Connect(pseudo, pwd, clInter);
                if (user != null) {
                    user.setRegistry(registry);
                    user.setClInter(clInter);
                    user.setStub(stub);
                    this.user = user;
                    this.connected = true;
                    System.out.println("You are connected!");
                    return true;
                }
            } else {
                System.out.println("Error in the login/pwd!");
                return false;
            }
        } catch (Exception err) {
            System.out.println(err);
        }
        return false;
    }

    public void launchSearchUser(String search) throws SQLException, NoSuchAlgorithmException {
        if (this.searchedListUser == null) {
            this.searchedListUser = new ArrayList();
        } else {
            this.searchedListUser.clear();
        }
        this.searchedListUser = UserBdd.getUserBySearch(search);
    }

    public void launchSearchUserByCategory(String search) throws SQLException, NoSuchAlgorithmException {
        if (this.searchedListUser == null) {
            this.searchedListUser = new ArrayList();
        } else {
            this.searchedListUser.clear();
        }
        Category cat = CategoryBdd.getCategoryByName(search);
        if (cat != null) {
            this.searchedListUser = UserBdd.getUserBySearchCategory(cat);
        }
    }

    public void updateUserFriendInfo(User user) throws SQLException {
        ArrayList<Friend> friend = UserBdd.getFriends(user);
        user.setFriends(friend);
    }

    public void disconnecttUser() {
        this.connected = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addUserBdd(ArrayList<String> l) {
        User use = new User(l.get(0), l.get(2), l.get(1));
        try {
            UserBdd.insertUser(use);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void sendMessage(Message message) {
        try {
            this.user.getStub().sendMessage(message);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public ArrayList<User> getSearchedListUser() {
        return searchedListUser;
    }

    public void setSearchedListUser(ArrayList<User> searchedListUser) {
        this.searchedListUser = searchedListUser;
    }

    public ArrayList<Category> getAllCategoriesExceptUser() {
        return allCategoriesExceptUser;
    }

    public void setAllCategoriesExceptUser(ArrayList<Category> allCategoriesExceptUser) {
        this.allCategoriesExceptUser = allCategoriesExceptUser;
    }

}
