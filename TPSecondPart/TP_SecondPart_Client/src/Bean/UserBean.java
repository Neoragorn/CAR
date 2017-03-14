package Bean;

import Models.Category;
import Models.User;
import java.util.ArrayList;

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
