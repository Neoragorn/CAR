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
public class MessageDiscussionGroupVirtualProxy extends ArrayList<MessageDiscussion> {

    ArrayList<MessageDiscussion> messages = new ArrayList();

    public MessageDiscussionGroupVirtualProxy() {
    }

    public ArrayList<MessageDiscussion> initialize(DiscussionGroup discussion) throws SQLException, RemoteException, NotBoundException, NoSuchAlgorithmException {
        if (messages.isEmpty()) {
            this.messages = Client.getInstance().getUser().getStub().recoverDiscussionMessage();
        }
        return this.messages;
    }

    public void updateMessage(DiscussionGroup discussion) throws SQLException, RemoteException, NotBoundException, NoSuchAlgorithmException {
        this.messages = Client.getInstance().getUser().getStub().getMessageFromDiscussion(discussion);
    }

    public ArrayList<MessageDiscussion> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageDiscussion> messages) {
        this.messages = messages;
    }

    
}
