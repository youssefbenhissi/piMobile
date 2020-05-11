package Entities;

public class Utilisateur {

    int id_Utilisateur;
    String email;
    String nom_Utilisateur;
    String motDePasse_Utilisateur;
    String role_Utilisateur;
     int telephone ;
    String image ;
    String nom ;
    String prenom ;
    String username ;
    String password ;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    
    public Utilisateur() {
    }

    public Utilisateur(int id_Utilisateur, String email, String nom_Utilisateur, String motDePasse_Utilisateur, String role_Utilisateur) {
        this.id_Utilisateur = id_Utilisateur;
        this.email = email;
        this.nom_Utilisateur = nom_Utilisateur;
        this.motDePasse_Utilisateur = motDePasse_Utilisateur;
        this.role_Utilisateur = role_Utilisateur;
    }

    public Utilisateur(int id_Utilisateur, String email, String nom_Utilisateur, String role_Utilisateur) {
        this.id_Utilisateur = id_Utilisateur;
        this.email = email;
        this.nom_Utilisateur = nom_Utilisateur;
        this.role_Utilisateur = role_Utilisateur;
    }

    public Utilisateur(String nom_Utilisateur, String email, String motDePasse_Utilisateur) {
        this.email = email;
        this.nom_Utilisateur = nom_Utilisateur;
        this.motDePasse_Utilisateur = motDePasse_Utilisateur;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id_Utilisateur=" + id_Utilisateur + ", email=" + email + ", nom_Utilisateur=" + nom_Utilisateur + ", motDePasse_Utilisateur=" + motDePasse_Utilisateur + ", role_Utilisateur=" + role_Utilisateur + '}';
    }

    public int getId_Utilisateur() {
        return id_Utilisateur;
    }

    public void setId_Utilisateur(int id_Utilisateur) {
        this.id_Utilisateur = id_Utilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom_Utilisateur() {
        return nom_Utilisateur;
    }

    public void setNom_Utilisateur(String nom_Utilisateur) {
        this.nom_Utilisateur = nom_Utilisateur;
    }

    public String getMotDePasse_Utilisateur() {
        return motDePasse_Utilisateur;
    }

    public void setMotDePasse_Utilisateur(String motDePasse_Utilisateur) {
        this.motDePasse_Utilisateur = motDePasse_Utilisateur;
    }

    public String getRole_Utilisateur() {
        return role_Utilisateur;
    }

    public void setRole_Utilisateur(String role_Utilisateur) {
        this.role_Utilisateur = role_Utilisateur;
    }

}
