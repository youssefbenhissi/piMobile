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
public class TagService {
    Tags tag = new Tags();
    Tags tagsss = new Tags();
    private static TagService instance = null;
    public  boolean resultatstuts;
    private ConnectionRequest req;
    private ArrayList<Tags> TagsList;
    public TagService() {
        req = new ConnectionRequest();
    }
    
    public static TagService getInstance(){
        if(instance == null){
            instance = new TagService();
        }
        return instance;
    }
    
     public Tags ParseTagSingle(String jsonArticles) throws IOException{
         
         try{
           
             JSONParser j = new JSONParser();
             Map<String,Object> TagListJson = j.parseJSON(new CharArrayReader(jsonArticles.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)TagListJson.get("root");
        int va = 0;
         for(Map<String,Object> obj : list){
             va++;
             if(va == 0){
                float id = Float.parseFloat(obj.get("id").toString());
            int idd = (int) id;
            String nom = obj.get("nom").toString();
            
            tagsss = new Tags(idd, nom);
        
             return tagsss; 
             }
            
         
         
         }
         }catch(Exception e){
             
         }
         return tagsss;
     }
    
 public Tags GetTagById(int id){
     
        String url =server.BASE_SERVER+"articles/Single-Tag/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    tag = ParseTagSingle(new String(req.getResponseData()));
                } catch (IOException ex) {
                    //Logger.getLogger(ArticlesSingleService.class.getName()).log(Level.SEVERE, null, ex);
                }
              
           req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tag;
    }
 
 
 
 
 
 
 
 
 
 
 
 
 
    
}
