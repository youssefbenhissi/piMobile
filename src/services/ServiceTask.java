/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Entities.CommentaireClub;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Club;
import entities.InscriptionClub;
import entities.whishlist;
//import com.mycompany.myapp.entities.Task;
//import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author bhk
 */
public class ServiceTask {

    public ArrayList<Club> tasks;
    public ArrayList<whishlist> taskWhishlist;

    public static ServiceTask instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceTask() {
        req = new ConnectionRequest();
    }

    public static ServiceTask getInstance() {
        if (instance == null) {
            instance = new ServiceTask();
        }
        return instance;
    }

    public ArrayList<Club> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Club t = new Club();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                //t.setStatus(((int)Float.parseFloat(obj.get("status").toString())));
                t.setNom(obj.get("nom").toString());
                t.setCapacite((int) Float.parseFloat(obj.get("capacite").toString()));
                t.setMoyenneLike( Float.parseFloat(obj.get("moyenneLike").toString()));
                t.setDescription(obj.get("description").toString());
                t.setQuestionPr(obj.get("questionPr").toString());
                t.setQuestionDe(obj.get("questionDe").toString());
                t.setQuestionTr(obj.get("questionTr").toString());
                t.setPath(obj.get("image").toString());
                tasks.add(t);
            }

        } catch (IOException ex) {

        }
        return tasks;
    }
    
    public ArrayList<Club> getAllTasks() {
        String url = Statics.BASE_URL + "api/afficherTousClubs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

    public ArrayList<Club> getTopThree() {
        String url = Statics.BASE_URL + "api/afficherTopThree";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

    public ArrayList<Club> afficherwhishilist(String idClub) {
        
        String url = Statics.BASE_URL + "api/afficherwishlist";
        req.setUrl(url);
        req.addArgument("idUser", idClub);
        //req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

    public static ArrayList<CommentaireClub> afficherCommentaire(String idClub) {
        ArrayList<CommentaireClub> listCommentaire = new ArrayList<>();
        String url = "http://127.0.0.1:8000/api/afficherCommentaireApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        request.addArgument("idClub", idClub);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            Map<String, Object> content;
            try {
                content = j.parseJSON(new CharArrayReader(json.toCharArray()));
                List<Map<String, Object>> commentaires = (List<Map<String, Object>>) content.get("root");
                for (Map<String, Object> commentaire : commentaires) {

                    // Map<String, Object> time = (Map<String, Object>) commentaire.get("dateCommentaire");
                    listCommentaire.add(new CommentaireClub(commentaire.get("message").toString()));

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return listCommentaire;
    }

    public static void ajouterCommentaire(CommentaireClub commentaireC) {
        String url = "http://127.0.0.1:8000/api/ajouterCommentaireApi";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            //String contenuCommentaire = SessionManager.getUserName() + " : \"" + commentaireEvenement.getContenu_commentaire() + "\"";
            request.addArgument("idClub", String.valueOf(commentaireC.getId_club()));
            request.addArgument("idUser", String.valueOf(commentaireC.getId_user()));
            request.addArgument("contenu", commentaireC.getMessage());
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void InscriptionEmail(String email, String nom) {
        String url = "http://127.0.0.1:8000/api/Inscription";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("nom", nom);
            request.addArgument("email", email);
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void convertirPdf() {
        String url = "http://127.0.0.1:8000/api/convertirPdf";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            // request.addArgument("nom", nom);
            //request.addArgument("email", email);
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void ajouterEtoiles(Integer id, Integer nombreEtoiles) {
        String url = "http://127.0.0.1:8000/api/ajouterUneEtoile";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            //String contenuCommentaire = SessionManager.getUserName() + " : \"" + commentaireEvenement.getContenu_commentaire() + "\"";
            request.addArgument("id", String.valueOf(id));
            request.addArgument("nombre", String.valueOf(nombreEtoiles));
            //request.addArgument("contenu", commentaireC.getMessage());
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void ajouterInscriptionCllub(InscriptionClub inscr) {
        String url = "http://127.0.0.1:8000/api/InscriptionClub";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            //String contenuCommentaire = SessionManager.getUserName() + " : \"" + commentaireEvenement.getContenu_commentaire() + "\"";
            request.addArgument("idClub", String.valueOf(inscr.getIdClub()));
            request.addArgument("idUser", String.valueOf(inscr.getIdUser()));
            request.addArgument("questionP", inscr.getQuestionP());
            request.addArgument("questionD", inscr.getQuestionD());
            request.addArgument("questionT", inscr.getQuestionT());
            request.addArgument("reponseP", inscr.getReponseP());
            request.addArgument("reponseD", inscr.getReponseD());
            request.addArgument("reponseT", inscr.getReponseT());
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void ajouterwhishlist(whishlist inscr) {
        String url = "http://127.0.0.1:8000/api/wishlist";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("idClub", String.valueOf(inscr.getId_club()));
            request.addArgument("idUser", String.valueOf(inscr.getId_user()));
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
