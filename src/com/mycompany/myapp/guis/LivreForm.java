/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guis;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.Livre;
import com.mycompany.myapp.entities.category;
import com.mycompany.myapp.services.ServiceCategorie;
import static com.mycompany.myapp.services.ServiceCategorie.listOfBooks;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class LivreForm  extends Form{
   public  int id;
                Form f;
                SpanLabel lb;
                Label label;
               // String url;
                EncodedImage enc;
               // URLImage uRLImage;
              
                Container oneEvent;
                 Container oneE;
                 Container global;
                     public Livre liv1;

String url ="http://localhost/img/";
    public LivreForm(int id){
 
        f = new Form("Tous Les Livres",new BoxLayout(BoxLayout.Y_AXIS));
             
            
//        
         System.out.println(id);
         ArrayList<Livre> listLivre= ServiceCategorie.getMyListcl(id);
                               
//com.codename1.ui.List uiLibsList = new com.codename1.ui.List();
        for(Livre l:listLivre){
            
            Container c = new Container(BoxLayout.x());
            oneEvent = new Container(BoxLayout.y());
            ImageViewer img = new ImageViewer();

           Label lab3=new Label(l.getNom());
            System.out.println(l.getDescription());
           oneEvent.add(lab3);
              lab3.addPointerReleasedListener((ActionEvent e) -> {
                        Detail d =new Detail(l);
                        d.getForm().show();
                        
                        
                    });
                  Style s = UIManager.getInstance().getComponentStyle("MultiLine1");

               FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

              EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 2, p.getHeight() * 2), false);

           Label cadrephoto=new Label();
            System.out.println(l.getAuteur());
  cadrephoto.setIcon(URLImage.createToStorage(placeholder, url+ l.getNom_image(), url+ l.getNom_image())); 
                 
       c.add(cadrephoto);
       c.setLeadComponent(lab3);

       
//
//           Button event_btn=new Button("Détail");
//                      event_btn.addActionListener((e)->{
////                          DetailForm forms= new DetailForm(id);
////                       forms.getForm().show();
//                                 new  DetailForm(ls).show();
//                   });
//             c.add(bt1);
          c.add(oneEvent);
         
         
                    
         
          f.add(c);
          

        }
          //f.getToolbar().addCommandToLeftBar(null, theme.getImage("cal_left_arrow.png"), (ev)->{HomeForm h=new HomeForm(theme);
         
          //});
         // f.setLayout(new BorderLayout());
   
        f.getToolbar().setBackCommand("", e -> {
          new BibliothequeForm().getForm().showBack();
          
        });
    }

   

  
     public Form getForm(){
   return f;
   }

    
}

