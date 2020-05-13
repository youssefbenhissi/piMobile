/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;



/**
 *
 * @author HP
 */
public  class MenuForm  extends Form{

    public  Form f;
            Resources theme;
            Resources theme1;
    
   Form current;

  public   MenuForm(Resources theme) {
      
  theme1 = UIManager.initFirstTheme("/theme");

        

        //getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ARCHIVE, e -> {});
                getToolbar().addMaterialCommandToSideMenu("open", FontImage.MATERIAL_BOOK, e->new BibliothequeForm().getForm().show());

         getToolbar().addMaterialCommandToSideMenu(" bibliotheque", FontImage.MATERIAL_ARCHIVE, e -> new Accueil().getForm().show());

              // getToolbar().addMaterialCommandToSideMenu("opinions", FontImage.MATERIAL_MAIL, e -> new Opinion(current).show());

    
       // getToolbar().addMaterialCommandToSideMenu("  Blogccc", FontImage.MATERIAL_BOOK, e -> showOtherForm(res));
        //getToolbar().addMaterialCommandToSideMenu("  Déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> new NewsfeedForm().show());


         }
    
   public Form getForm1(){
   return f;
   }

   
    

    
    
}


   

   

   
