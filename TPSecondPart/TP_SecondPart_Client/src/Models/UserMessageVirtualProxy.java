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
public class UserMessageVirtualProxy extends ArrayList<Message> {

    int id;
    ArrayList<Message> messages = new ArrayList();

    public UserMessageVirtualProxy(int id) {
        this.id = id;
    }

    public ArrayList<Message> initialize() throws SQLException, RemoteException, NotBoundException, NoSuchAlgorithmException
    {
        if (this.messages.isEmpty())
        {
           messages = Client.getInstance().getUser().getStub().getMessageUser(id);
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
