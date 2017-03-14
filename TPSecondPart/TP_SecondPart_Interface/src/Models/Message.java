/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.sql.Date;

/**
 *               Strign message = ;
 * @author sofian
 */
public class Message implements Serializable {

    private String message;
    private User auteur;
    private Friend destinataire;
    private Date date;

    public Message(String message, User auteur, Friend destinataire, Date date) {
        this.message = message;
        this.auteur = auteur;
        this.destinataire = destinataire;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAuteur() {
        return auteur;
    }

    public void setAuteur(User auteur) {
        this.auteur = auteur;
    }

    public Friend getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Friend destinataire) {
        this.destinataire = destinataire;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
   
       
}
