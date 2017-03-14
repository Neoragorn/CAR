/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sofian
 */
public class UserDiscussionGroupVirtualProxy extends ArrayList<DiscussionGroup> {

    int id;
    ArrayList<User> users = new ArrayList();

    public UserDiscussionGroupVirtualProxy(int id) {
        this.id = id;
    }

    public ArrayList<User> initialize() throws SQLException
    {
        if (users.isEmpty())
        {
           //  users = DiscussionGroupBdd.getDiscussionGroupUserById(id);
        }
        return users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
    
}
