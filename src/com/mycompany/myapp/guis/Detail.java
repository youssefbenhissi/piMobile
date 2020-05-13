/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guis;

import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.util.StringUtil;
import com.mycompany.myapp.entities.Livre;
import com.mycompany.myapp.entities.Reservation;
import com.mycompany.myapp.services.OpinionDAO;
import com.mycompany.myapp.services.ServiceCategorie;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 *
 * @author hp
 */
public class Detail  extends Form{
          EncodedImage encoded;
Form f ;
 Livre liv;
String url ="http://localhost/img/";

    public  Detail(Livre l){
                     f= new Form("Details de livre  :"+l.getNom(), new BoxLayout(BoxLayout.Y_AXIS));

               // f= new Form("Details de livre  :  "+l.getNom());

        Label nomLi = new Label("livre de la category    : "+l.getNombredepage());

        Container ctn1 = new Container(BoxLayout.y());
        Label nomLivre = new Label("nom du livre          : "+l.getNom());
        Label Description = new Label("Description            :"+l.getDescription());
        Label auteur = new Label("auteur       :"   +l.getAuteur());
        Label nombredepage = new Label("nombre de page de livre         :"+l.getNombredepage());
         Style s = UIManager.getInstance().getComponentStyle("MultiLine1");

               FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

              EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 2, p.getHeight() * 2), false);

           Label cadrephoto=new Label();
            System.out.println(l.getAuteur());
  cadrephoto.setIcon(URLImage.createToStorage(placeholder, url+ l.getNom_image(), url+ l.getNom_image())); 
        
        System.out.println(l.getNom_image());
        Label image = new Label();
                Button btnValider = new Button("mail us!");
           
           
          btnValider.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
         new OpinionDAO();
      
      }
  });  
         
         Button event_btn=new Button("Demande Reserver Livre");
         LocalNotification ln = new LocalNotification();
ln.setAlertTitle("Test");
ln.setAlertBody("This is a test message");
ln.setId("This is a test Id");
         event_btn.addActionListener((ActionEvent e)->{
                     Reservation r= new Reservation( l.getNom(), l.getId());

             ServiceCategorie rs = new ServiceCategorie();
 
             rs.Reservation(r);
             
     
            });
          
       
        //mesoffrespartagesfb.setUIID("mesoffrespartagesfb");
      
        //image.setIcon(imgserver);
        ctn1.add(cadrephoto);
        ctn1.add(nomLivre);
        ctn1.add(Description);
        ctn1.add(auteur);
        ctn1.add(nombredepage);
        ctn1.add(event_btn);
        ctn1.add(btnValider);
        f.add(ctn1);       


f.getToolbar().setBackCommand("", e -> {
          new BibliothequeForm().getForm().showBack();
          
        });


    }
    public Form getForm(){
    return f;
    }
    
    
}

