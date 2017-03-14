/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import tp_secondpart_client.Client;

/**
 *
 * @author sofian
 */
public class UserCategoryVirtualProxy extends ArrayList<Category> {

    int id;
    ArrayList<Category> categories = new ArrayList();

    public UserCategoryVirtualProxy(int id) {
        this.id = id;
    }

    public ArrayList<Category> initialize() throws SQLException, RemoteException, NotBoundException, NoSuchAlgorithmException {
        if (categories.isEmpty()) {
            categories = Client.getInstance().getUser().getStub().getCategoryUser(id);
        }
        return categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

}
