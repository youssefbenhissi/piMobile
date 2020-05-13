/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.guis;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;

/**
 *
 * @author hp
 */
public class Accueil extends Form{
    Form f;
    String url ="http://localhost/img/";

    public   Accueil() {
             f= new Form("BIBLIOTHEQUE", new BoxLayout(BoxLayout.Y_AXIS));
              Label lab3=new Label("Welcom");
              Container oneEvent = new Container(BoxLayout.y());
              Style s = UIManager.getInstance().getComponentStyle("MultiLine1");

              FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);

              EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 8, p.getHeight() * 8), false);

               URLImage background = URLImage.createToStorage(placeholder, "http://localhost/img/1.gif",
                "http://localhost/img/1.gif");
                      ImageViewer img = new ImageViewer(background);

              Button AccueilToTouteLeslivre= new Button("Toute les categories*");
                AccueilToTouteLeslivre.addActionListener((e)->{
                       BibliothequeForm forms= new BibliothequeForm();
                     forms.getForm().show();
                               
                 });
        
      // AccueilToTouteLesOffres.setUIID("AccueilToTouteLesOffres");
      oneEvent.add(lab3);
      oneEvent.add(img );
      oneEvent.add(AccueilToTouteLeslivre );
      f.add(oneEvent);
    }
     public Form getForm(){
   return f;
   }

}
