/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5_car_client;

import Bean_Client.DiscussionGroupBean;
import Bean_Client.UserBean;
import Frame_Client.Discussion;
import Frame_Client.MyFrame;
import Models.DiscussionGroup;
import Models.MessageDiscussion;
import Models.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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

    @Override
    public void addMessageToDiscussion(DiscussionGroup discussion, MessageDiscussion message, User user) throws RemoteException {
        UserBean.getInstance().getUser().getStub().sendMessageDiscussion(discussion, message, user);
    }

    @Override
    public void createDiscussion(User user, String title, String description) throws RemoteException {
        UserBean.getInstance().getUser().getStub().createNewDiscussion(user, title, description);
    }

    @Override
    public void getMessageUser(int id) throws RemoteException {
        UserBean.getInstance().getUser().getStub().recoverMessageUser(id);
    }

    @Override
    public void orderNewFrameDiscussion() throws RemoteException {
        System.out.println("Order NED FRAME");
        MyFrame.getInstance().changeFrame(new Discussion());
    }
}
