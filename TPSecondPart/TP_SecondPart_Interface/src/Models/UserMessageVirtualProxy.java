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
public class UserMessageVirtualProxy extends ArrayList<Message> {

    int id;
    ArrayList<Message> messages = new ArrayList();

    public UserMessageVirtualProxy(int id) {
        this.id = id;
    }

    public ArrayList<Message> initialize() throws SQLException
    {
        if (this.messages.isEmpty())
        {
           // messages = UserBdd.getPrivateMessageById(this.id);            
        }
        return messages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    
    
}
