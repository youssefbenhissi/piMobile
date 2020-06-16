/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.models.Articles;
import com.mycompany.myapp.models.Tags;
import com.mycompany.myapp.utils.server;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Alaa
 */
public class ArticlesService {
    private static ArticlesService instance = null;
    public  boolean resultatstuts;
    private ConnectionRequest req;

    private ArrayList<Articles> Articles = new ArrayList<>();
    public ArticlesService() {
        req = new ConnectionRequest();
    }
    
    public static ArticlesService getInstance(){
        if(instance == null){
            instance = new ArticlesService();
        }
        return instance;
    }
    
     public ArrayList<Articles> ParseArticles(String jsonArticles) throws IOException{
         try{
             Articles = new ArrayList<>();
             JSONParser j = new JSONParser();
             Map<String,Object> ArticlesListJson = j.parseJSON(new CharArrayReader(jsonArticles.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)ArticlesListJson.get("root");
         for(Map<String,Object> obj : list){
            float id = Float.parseFloat(obj.get("id").toString());
            int idd = (int) id;
            String titre = obj.get("titre").toString();
            String descri = obj.get("description").toString();
            String image = obj.get("image").toString();
            String date = obj.get("date").toString();
            String contenu = obj.get("contenu").toString();
          Map<String,Object> categorie = (Map<String,Object>) obj.get("categorie");
          String catnom = categorie.get("nom").toString();
           float idcat = Float.parseFloat(categorie.get("id").toString());
            int iddcat = (int) idcat;
        List<Map<String,Object>> listtags = (List<Map<String,Object>>)obj.get("tags");
        List<Tags> listTags = new ArrayList<Tags>();
        for(Map<String,Object> objtag : listtags){
            float idtag = Float.parseFloat(objtag.get("id").toString());
            String tagnom = objtag.get("nom").toString();
            int iddtag = (int) idtag;
            Tags tag = new  Tags(iddtag, tagnom);
            listTags.add(tag);
        }
        float vues = Float.parseFloat(obj.get("vues").toString());
            int vue = (int) vues;
            float type = Float.parseFloat(obj.get("type").toString());
            int typea = (int) type;
        
             Articles art = new Articles(titre, image, descri, contenu, date, idd, vue, typea, iddcat, listTags);
             if(art.getType() == 1){
                 Articles.add(art);
             }
         
         }
         }catch(Exception e){
             
         }
         return Articles;
     }
    
 public ArrayList<Articles> GetArticles(){
        String url =server.BASE_SERVER+"articles/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Articles = ParseArticles(new String(req.getResponseData()));
                } catch (IOException ex) {
                //    Logger.getLogger(ArticlesService.class.getName()).log(Level.SEVERE, null, ex);
                }
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Articles;
    }
 
 
  public ArrayList<Articles> GetArticlesID(int id){
        String url =server.BASE_SERVER+"articles/by-categorie/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Articles = ParseArticles(new String(req.getResponseData()));
                } catch (IOException ex) {
                //    Logger.getLogger(ArticlesService.class.getName()).log(Level.SEVERE, null, ex);
                }
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Articles;
    }
  
  
    public ArrayList<Articles> GetArticlesTagID(int id){
        String url =server.BASE_SERVER+"articles/by-Tag/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Articles = ParseArticles(new String(req.getResponseData()));
                } catch (IOException ex) {
                //    Logger.getLogger(ArticlesService.class.getName()).log(Level.SEVERE, null, ex);
                }
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Articles;
    }
    
    
        public ArrayList<Articles> GetArticlesPopulaire(){
        String url =server.BASE_SERVER+"articles/all/populaire";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Articles = ParseArticles(new String(req.getResponseData()));
                } catch (IOException ex) {
                //    Logger.getLogger(ArticlesService.class.getName()).log(Level.SEVERE, null, ex);
                }
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Articles;
    }
        
 public ArrayList<Articles> GetArticlesRech(String terme){
        String url =server.BASE_SERVER+"articles/rech/"+terme;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Articles = ParseArticles(new String(req.getResponseData()));
                } catch (IOException ex) {
                //    Logger.getLogger(ArticlesService.class.getName()).log(Level.SEVERE, null, ex);
                }
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Articles;
    }
        
        
    
}
