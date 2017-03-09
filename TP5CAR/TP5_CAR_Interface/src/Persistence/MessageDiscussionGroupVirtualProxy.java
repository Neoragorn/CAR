/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Models.DiscussionGroup;
import Models.MessageDiscussion;
import Models.User;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sofian
 */
public class MessageDiscussionGroupVirtualProxy extends ArrayList<MessageDiscussion> {

    ArrayList<MessageDiscussion> messages = new ArrayList();

    public MessageDiscussionGroupVirtualProxy() {
    }

    public ArrayList<MessageDiscussion> initialize(DiscussionGroup discussion) throws SQLException {
        if (messages.isEmpty()) {
            this.messages = MessageDiscussionBdd.getMessageFromDiscussion(discussion);
        }
        return this.messages;
    }

    public void updateMessage(DiscussionGroup discussion) throws SQLException {
        this.messages = MessageDiscussionBdd.getMessageFromDiscussion(discussion);
    }

    public ArrayList<MessageDiscussion> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageDiscussion> messages) {
        this.messages = messages;
    }

}
