/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author sofian
 */
public class Friend {

    private int idFriend;
    private String pseudo;
    private String mail;
    private String lastConnection;
    private boolean connected = false;
    public Friend() {
    }

     public Friend(int id, String pseudo, String mail) {
         this.idFriend = id;
         this.pseudo = pseudo;
    	this.mail = mail;	
    }
     
    public Friend(String pseudo, String mail) {
    	this.pseudo = pseudo;
    	this.mail = mail;	
    }
    
    public int getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(int idFriend) {
        this.idFriend = idFriend;
    }

    
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    
    
    
}
