/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;


import Persistence.MessageDiscussionGroupVirtualProxy;
import Persistence.UserDiscussionGroupVirtualProxy;
import java.io.Serializable;

/**
 *
 * @author sofian
 */
public class DiscussionGroup implements Serializable {

    private int idDiscussion;
    private int idCreator;
    private String title;
    private String description;
    private UserDiscussionGroupVirtualProxy members;
    private MessageDiscussionGroupVirtualProxy messagesProxy;
    
    public DiscussionGroup(int id, int idCreator, String title, String description) {
        this.idDiscussion = id;
        this.idCreator = idCreator;
        this.title = title;
        this.description = description;
        this.members = new UserDiscussionGroupVirtualProxy(id);
        this.messagesProxy = new MessageDiscussionGroupVirtualProxy();
    }

    public DiscussionGroup() {

    }

    public DiscussionGroup(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public MessageDiscussionGroupVirtualProxy getMessagesProxy() {
        return messagesProxy;
    }

    public void setMessagesProxy(MessageDiscussionGroupVirtualProxy messagesProxy) {
        this.messagesProxy = messagesProxy;
    }

    public int getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(int idCreator) {
        this.idCreator = idCreator;
    }

    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdDiscussion() {
        return idDiscussion;
    }

    public void setIdDiscussion(int idDiscussion) {
        this.idDiscussion = idDiscussion;
    }

    public UserDiscussionGroupVirtualProxy getMembers() {
        return members;
    }

    public void setMembers(UserDiscussionGroupVirtualProxy members) {
        this.members = members;
    }

}
