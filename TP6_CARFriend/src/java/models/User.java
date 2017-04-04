package models;


import java.io.Serializable;
import java.rmi.registry.Registry;
import java.util.ArrayList;


public class User implements Serializable {

    protected String pseudo;
    protected String mail;
    protected String pwd; 
    protected int idUser;
//    protected ArrayList<Friend> friends = new ArrayList();
    
    public User() {

    }

    
    public User(int id, String pseudo, String mail) {
        this.idUser = id;
        this.pseudo = pseudo;
        this.mail = mail;
     }

    public User(int id, String pseudo, String pwd, String mail) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.pwd = pwd;
     }

    public User(String pseudo, String pwd, String mail) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.pwd = pwd;
    }

    
/*    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    } */

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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    @Override
    public String toString() {
        return ("id: " + this.idUser + "\nPseudo : " + this.pseudo + "\nmail: " + this.mail);
    }
}
