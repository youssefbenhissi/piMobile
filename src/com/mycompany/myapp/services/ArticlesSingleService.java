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
public class ArticlesSingleService {
    Articles ar = new Articles();
    private static ArticlesSingleService instance = null;
    public  boolean resultatstuts;
    private ConnectionRequest req;
        ConnectionRequest con;
    StringBuffer str;
    String reponse;
    Boolean likeresu ;
     String resu = null;
     int nbrvue = 0;
    public ArticlesSingleService() {
        req = new ConnectionRequest();
    }
    
    
      
      
    
    public static ArticlesSingleService getInstance(){
        if(instance == null){
            instance = new ArticlesSingleService();
        }
        return instance;
    }
    
     public Articles ParseArticle(String jsonArticles) throws IOException{
         Articles art = new Articles();
         try{
           
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
        
              art = new Articles(titre, image, descri, contenu, date, idd, vue, typea, iddcat, listTags);
             if(art.getType() == 1){
                 return art;
             }
         
         }
         }catch(Exception e){
             
         }
         return art;
     }
    
 public Articles GetArticleById(int id){
     
        String url =server.BASE_SERVER+"articles/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    ar = ParseArticle(new String(req.getResponseData()));
                } catch (IOException ex) {
                    //Logger.getLogger(ArticlesSingleService.class.getName()).log(Level.SEVERE, null, ex);
                }
              
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ar;
    }
    
 
 
 public void ArticleVueinc(int id){
        String url =server.BASE_SERVER+"articles/vueinc/"+id;
        req.setUrl(url);
        req.setPost(false);
       
        NetworkManager.getInstance().addToQueueAndWait(req);
       
    }
 
 
 
    public String Parselike(String jsonArticles) throws IOException{
       
        String email = null;
         try{
           
             JSONParser j = new JSONParser();
             Map<String,Object> likListJson = j.parseJSON(new CharArrayReader(jsonArticles.toCharArray()));
       List<Map<String,Object>> list = (List<Map<String,Object>>)likListJson.get("root");
         for(Map<String,Object> obj : list){
            email = obj.get("emailUser").toString();
           
            
         }
         }catch(Exception e){
             
         }
         return email;
     }
 
 
   
   
   
   
   
   
   
   
   
   
   
 
 
 
         public String GetArticlesLike(int id,String email){
   
            String url = server.BASE_SERVER+"articles/likecheck/?"
                    + "id=" + id + "&email=" + email;
          req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                     resu = Parselike(new String(req.getResponseData()));
                } catch (IOException ex) {
                    //Logger.getLogger(ArticlesSingleService.class.getName()).log(Level.SEVERE, null, ex);
                }
              
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resu;
            
            
    
         
    }
         
         public void ArticleLikeAdd(int id,String email){
        String url = server.BASE_SERVER+"articles/likeadd/?"
                    + "id=" + id + "&email=" + email;
        req.setUrl(url);
        req.setPost(false);
       
        NetworkManager.getInstance().addToQueueAndWait(req);
       
    }
         
          public void ArticleLikeremove(int id,String email){
        String url = server.BASE_SERVER+"articles/likeremo/?"
                    + "id=" + id + "&email=" + email;
        req.setUrl(url);
        req.setPost(false);
       
        NetworkManager.getInstance().addToQueueAndWait(req);
       
    }

          
          
          
          
           public int Parselikescount(String jsonArticles) throws IOException{
       int nbrr =0;
        
         try{
           
             JSONParser j = new JSONParser();
             Map<String,Object> likListJson = j.parseJSON(new CharArrayReader(jsonArticles.toCharArray()));
       List<Map<String,Object>> list = (List<Map<String,Object>>)likListJson.get("root");
         for(Map<String,Object> obj : list){
          float nbr = Float.parseFloat(obj.get("nbr").toString());
           nbrr = (int) nbr;
            
         }
         }catch(Exception e){
             
         }
         return nbrr;
     }
          
          
          
          
          
          
          
  public int GetArticlesLikescount(int id){
   
            String url = server.BASE_SERVER+"articles/Likes/"+id;
          req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                     nbrvue = Parselikescount(new String(req.getResponseData()));
                } catch (IOException ex) {
                    //Logger.getLogger(ArticlesSingleService.class.getName()).log(Level.SEVERE, null, ex);
                }
              
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return nbrvue;
            
            
    
         
    }
 
 
 
}
