/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author sofian
 */
public class Statut {
    private int idStatut;
    private String auteur;
    private String statut;
    private Date publication;    
    private ArrayList<Comment> commentList = new ArrayList();
    
    public Statut(int idStatut, String auteur, String statut, Date publication) {
        this.idStatut = idStatut;
        this.auteur = auteur;
        this.statut = statut;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }
    
    
}
