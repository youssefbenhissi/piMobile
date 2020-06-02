/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author youssef
 */
public class CommentaireEvenement {
    private int id_commentaire_evenement;
    private int id_evenement;
    private int id_user;
    private String message;

    public int getId_commentaire_evenement() {
        return id_commentaire_evenement;
    }

    public void setId_commentaire_evenement(int id_commentaire_evenement) {
        this.id_commentaire_evenement = id_commentaire_evenement;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
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

    public CommentaireEvenement(int id_evenement, int id_user, String message) {
        this.id_commentaire_evenement = id_commentaire_evenement;
        this.id_evenement = id_evenement;
        this.id_user = id_user;
        this.message = message;
    }

    public CommentaireEvenement() {
    }
    public CommentaireEvenement(String message){
        this.message=message;
    }
    
}
