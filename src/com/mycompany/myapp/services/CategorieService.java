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
import com.mycompany.myapp.models.Categories;
import com.mycompany.myapp.models.Tags;
import com.mycompany.myapp.utils.server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 *
 * @author Alaa
 */
public class CategorieService {
    Categories cate = new Categories();
    private static CategorieService instance = null;
    public  boolean resultatstuts;
    private ConnectionRequest req;
    private ArrayList<Categories> CategoriesListfin;
    public CategorieService() {
        req = new ConnectionRequest();
    }
    
    public static CategorieService getInstance(){
        if(instance == null){
            instance = new CategorieService();
        }
        return instance;
    }
    
     public Categories ParseCategorieSingle(String jsonArticles) throws IOException{
         Categories cat = new Categories();
         try{
           
             JSONParser j = new JSONParser();
             Map<String,Object> CateListJson = j.parseJSON(new CharArrayReader(jsonArticles.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)CateListJson.get("root");
         for(Map<String,Object> obj : list){
            float id = Float.parseFloat(obj.get("id").toString());
            int idd = (int) id;
            String nom = obj.get("nom").toString();
            String descri = obj.get("description").toString();
            cat = new Categories(idd, nom, descri);
        
             return cat;
         
         
         }
         }catch(Exception e){
             
         }
         return cat;
     }
    
 public Categories GetCategorieById(int id){
     
        String url =server.BASE_SERVER+"articles/categories/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    cate = ParseCategorieSingle(new String(req.getResponseData()));
                } catch (IOException ex) {
                    //Logger.getLogger(ArticlesSingleService.class.getName()).log(Level.SEVERE, null, ex);
                }
              
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cate;
    }
 
 
 
 
 
 
 
      public ArrayList<Categories> ParseCategories(String jsonArticles) throws IOException{
         try{
             CategoriesListfin = new ArrayList<>();
             JSONParser j = new JSONParser();
             Map<String,Object> CatsListJson = j.parseJSON(new CharArrayReader(jsonArticles.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)CatsListJson.get("root");
         for(Map<String,Object> obj : list){
            float id = Float.parseFloat(obj.get("id").toString());
            int idd = (int) id;
            String titre = obj.get("nom").toString();
            String descri = obj.get("description").toString();
            
        Categories catego = new Categories(idd, titre, descri);
             CategoriesListfin.add(catego);
         
         }
         }catch(Exception e){
             
         }
         return CategoriesListfin;
     }
    
 public ArrayList<Categories> GetCats(){
        String url =server.BASE_SERVER+"articles/categories/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    CategoriesListfin = ParseCategories(new String(req.getResponseData()));
                } catch (IOException ex) {
                //    Logger.getLogger(ArticlesService.class.getName()).log(Level.SEVERE, null, ex);
                }
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return CategoriesListfin;
    }
 
 
 
 
 
 
    
}
