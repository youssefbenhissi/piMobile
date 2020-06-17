/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Asus
 */
public class Reclamation {
    private int id;
    private String sujet,description,dateRec;

    public Reclamation() {
    }

    public Reclamation(int id, String sujet, String description, String dateRec) {
        this.id = id;
        this.sujet = sujet;
        this.description = description;
        this.dateRec = dateRec;
    }

    public Reclamation(String sujet, String description, String dateRec) {
        this.sujet = sujet;
        this.description = description;
        this.dateRec = dateRec;
    }

    public Reclamation(String sujet, String description) {
        this.sujet = sujet;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateRec() {
        return dateRec;
    }

    public void setDateRec(String dateRec) {
        this.dateRec = dateRec;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", sujet=" + sujet + ", description=" + description + ", dateRec=" + dateRec + '}';
    }

   
  


    
}
