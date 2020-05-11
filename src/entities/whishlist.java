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
public class whishlist {
    private int id_whislist;
    private int id_club;
    private int id_user;
    private Club c;

    public Club getC() {
        return c;
    }

    public void setC(Club c) {
        this.c = c;
    }
    
    public whishlist() {
    }

    public int getId_whislist() {
        return id_whislist;
    }

    public void setId_whislist(int id_whislist) {
        this.id_whislist = id_whislist;
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

    public whishlist(int id_whislist, int id_club, int id_user) {
        this.id_whislist = id_whislist;
        this.id_club = id_club;
        this.id_user = id_user;
    }
    
}
