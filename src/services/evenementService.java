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
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import entities.Club;
import entities.CommentaireEvenement;
import entities.Evenement;
import entities.whishlist;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author youssef
 */
public class evenementService {

    public ArrayList<Evenement> tasks;

    public static evenementService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public evenementService() {
        req = new ConnectionRequest();
    }

    public static evenementService getInstance() {
        if (instance == null) {
            instance = new evenementService();
        }
        return instance;
    }

    public ArrayList<Evenement> parseTasks(String jsonText) {
        try {
            tasks = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Evenement t = new Evenement();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setIdEvenement((int) id);
                t.setNomEvenement(obj.get("nomE").toString());
                float capacite = Float.parseFloat(obj.get("capaciteE").toString());
                t.setCapaciteEvenement((int) capacite);
                t.setDescriptionEvenement(obj.get("description").toString());
                t.setImageEvenement(obj.get("imgE").toString());
                float prixEvenement = Float.parseFloat(obj.get("prixE").toString());
                t.setPrixEvenement((int) prixEvenement);
                tasks.add(t);
            }

        } catch (IOException ex) {

        }
        return tasks;
    }

    public ArrayList<Evenement> getAllTasks() {
        String url = Statics.BASE_URL + "api/afficherTousEvenements";
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
    public ArrayList<Evenement> getAllMesevenements(String username) {
        String url = "http://127.0.0.1:8000/api/afficherMesEvenements";
        req.setUrl(url);
        req.addArgument("username", username);
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

    public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/AfficheGalerieEvenement?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);

        return urli;

    }

    public void reserver(String username,int idEvenement) {

        String url = "http://127.0.0.1:8000/api/reserverEvenement";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            request.addArgument("username", username);
            request.addArgument("id", String.valueOf(idEvenement));
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void ajouterCommentaire(CommentaireEvenement commentaireC) {
        String url = "http://127.0.0.1:8000/api/ajouterCommentaireEvenement";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            //String contenuCommentaire = SessionManager.getUserName() + " : \"" + commentaireEvenement.getContenu_commentaire() + "\"";
            request.addArgument("idClub", String.valueOf(commentaireC.getId_evenement()));
            request.addArgument("idUser", String.valueOf(commentaireC.getId_user()));
            request.addArgument("contenu", commentaireC.getMessage());
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static ArrayList<CommentaireEvenement> afficherCommentaire(String idEvenement) {
        ArrayList<CommentaireEvenement> listCommentaire = new ArrayList<>();
        String url = "http://127.0.0.1:8000/api/afficherCommentaireEvenement";
        ConnectionRequest request = new ConnectionRequest(url, false);
        request.addArgument("idEvenement", idEvenement);
        NetworkManager.getInstance().addToQueueAndWait(request);

        JSONParser j = new JSONParser();
        String json = new String(request.getResponseData()) + "";
        if (!json.equals("no data")) {
            Map<String, Object> content;
            try {
                content = j.parseJSON(new CharArrayReader(json.toCharArray()));
                List<Map<String, Object>> commentaires = (List<Map<String, Object>>) content.get("root");
                for (Map<String, Object> commentaire : commentaires) {
                    CommentaireEvenement c= new CommentaireEvenement(commentaire.get("message").toString());
                    listCommentaire.add(c);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return listCommentaire;
    }
    public static void annulerReservation(int idEvenement,String username) {
        String url = "http://127.0.0.1:8000/api/ajouterCommentaireEvenement";
        ConnectionRequest request = new ConnectionRequest(url, false);
        try {
            //String contenuCommentaire = SessionManager.getUserName() + " : \"" + commentaireEvenement.getContenu_commentaire() + "\"";
            request.addArgument("idUser", username);
            request.addArgument("idUser", String.valueOf(idEvenement));
            NetworkManager.getInstance().addToQueueAndWait(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
