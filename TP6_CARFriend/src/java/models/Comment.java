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
public class Comment {
    private int idStatut;
    private String auteur;
    private String commentaire;
    private Date publication;  

    public Comment(int idStatut, String auteur, String commentaire, Date publication) {
        this.idStatut = idStatut;
        this.auteur = auteur;
        this.commentaire = commentaire;
        this.publication = publication;
    }

    public int getIdStatut() {
        return idStatut;
    }

    public void setIdStatut(int idStatut) {
        this.idStatut = idStatut;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }
    
    
}
