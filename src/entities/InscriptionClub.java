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
public class InscriptionClub {
    private int idClub;
    private int idUser;
    private String questionP;
    private String questionD;
    private String questionT;
    private String reponseP;
    private String reponseD;
    private String reponseT;

    public InscriptionClub(int idClub, int idUser, String questionP, String questionD, String questionT, String reponseP, String reponseD, String reponseT) {
        this.idClub = idClub;
        this.idUser = idUser;
        this.questionP = questionP;
        this.questionD = questionD;
        this.questionT = questionT;
        this.reponseP = reponseP;
        this.reponseD = reponseD;
        this.reponseT = reponseT;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getQuestionP() {
        return questionP;
    }

    public void setQuestionP(String questionP) {
        this.questionP = questionP;
    }

    public String getQuestionD() {
        return questionD;
    }

    public void setQuestionD(String questionD) {
        this.questionD = questionD;
    }

    public String getQuestionT() {
        return questionT;
    }

    public void setQuestionT(String questionT) {
        this.questionT = questionT;
    }

    public String getReponseP() {
        return reponseP;
    }

    public void setReponseP(String reponseP) {
        this.reponseP = reponseP;
    }

    public String getReponseD() {
        return reponseD;
    }

    public void setReponseD(String reponseD) {
        this.reponseD = reponseD;
    }

    public String getReponseT() {
        return reponseT;
    }

    public void setReponseT(String reponseT) {
        this.reponseT = reponseT;
    }
    
}
