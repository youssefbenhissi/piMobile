/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author solta
 */
public class CommentaireClub {

    private int id_commentaire_club;
    private int id_club;
    private int id_user;
    private String message;

    public CommentaireClub() {

    }

    public CommentaireClub(int id_evenement, int id_user, String contenu_commentaire) {

        this.id_club = id_evenement;
        this.id_user = id_user;
        this.message = contenu_commentaire;
    }
    
        public CommentaireClub(String contenu_commentaire) {
        this.message = contenu_commentaire;
    }

    public CommentaireClub(int id_commentaire_club, int id_club, int id_user, String message) {
        this.id_commentaire_club = id_commentaire_club;
        this.id_club = id_club;
        this.id_user = id_user;
        this.message = message;
    }

    public int getId_commentaire_club() {
        return id_commentaire_club;
    }

    public void setId_commentaire_club(int id_commentaire_club) {
        this.id_commentaire_club = id_commentaire_club;
    }

    public int getId_club() {
        return id_club;
    }

    public void setId_club(int id_club) {
        this.id_club = id_club;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommentaireClub{" + "id_commentaire_club=" + id_commentaire_club + ", id_club=" + id_club + ", id_user=" + id_user + ", message=" + message + '}';
    }
    
    

}
