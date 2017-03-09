/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_client;

import Bean_Client.DiscussionGroupBean;
import Bean_Client.UserBean;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import tp5_car_interface.InterfaceClient;

/**
 *
 * @author casier
 */
public class InterfaceImplClient extends UnicastRemoteObject implements InterfaceClient {

    public InterfaceImplClient() throws RemoteException {

    }

    @Override
    public void Receive(String login, String message) {
        System.out.println(login + " : " + message);
    }

    @Override
    public void RecoverDiscussion() throws RemoteException {
        DiscussionGroupBean.getInstance().setNotJoinedDiscussionGroup(UserBean.getInstance().getUser().getStub().giveDiscussion(UserBean.getInstance().getUser()));
    }

    @Override
    public void RecoverJoinedDiscussion() throws RemoteException {
        DiscussionGroupBean.getInstance().setJoinedDiscussionGroup(UserBean.getInstance().getUser().getStub().giveJoinedDiscussion(UserBean.getInstance().getUser()));
    }
    
    /*    public void addMessageToDiscussion(DiscussionGroup discussion, MessageDiscussion message, User user) throws SQLException {
        MessageDiscussionBdd.insertMessageIntoDiscussion(discussion, message, user);
    }

    public void createDiscussion(User user, String title, String description) throws SQLException {
        DiscussionGroupBdd.createDiscussionGroupBdd(user.getIdUser(), title, description);
    }

    public void recoverNotJoinedDiscussionGroups(User user) throws SQLException {
        try {
            this.notJoinedDiscussionGroup = DiscussionGroupBdd.getNotJoinedDiscussionGroupBdd(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void recoverJoinedDiscussionGroups(User user) throws SQLException {
        try {
            this.joinedDiscussionGroup = DiscussionGroupBdd.getJoinedDiscussionGroupBdd(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    } */
}
