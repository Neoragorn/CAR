/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;

/**
 *
 * @author sofian
 */
public class MessageDiscussion {

    private int idMessage;
    private String message;
    private String auteur;
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public MessageDiscussion(int idMessage, String message, String auteur, Date time) {
        this.idMessage = idMessage;
        this.message = message;
        this.auteur = auteur;
        this.time = time;
    }
    
   public MessageDiscussion(String message, String auteur, Date time) {
        this.message = message;
        this.auteur = auteur;
        this.time = time;
    }


    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    
}
