/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;


import entities.Categories;
import entities.Tags;
import entities.Articles;

import services.ArticlesSingleService;
import services.CategorieService;
import utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import services.ArticlesService;


/**
 *
 * @author Alaa
 */
public class ArticleListByIdForm extends Form{
 EncodedImage enc;
 Date date;
 ArrayList<Articles> Articles;

    public ArticleListByIdForm(int id) {
        
        
        this.setLayout(new FlowLayout());

        Articles article = new Articles();
              

        ArticlesService artser = new ArticlesService();
        
      Articles = artser.GetArticlesID(id);
      CategorieService catser = new CategorieService();
        Categories cat = catser.GetCategorieById(id);
       this.setTitle(cat.getNom());
        //System.out.println(article.getDescription());
         for(int i =0;i<Articles.size();i++){
          
            final int idart = Articles.get(i).getId();
            
            Container cntGlobal = new Container(new FlowLayout());
        Container cntImage = new Container(new TableLayout(1, 1));
       
           try {
               enc = EncodedImage.create("/load.png");
           } catch (IOException ex) {
             //  Logger.getLogger(HomeBlogForm.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println("ERROR");
           }
        String urlImage = Statics.BASE_SERVER_ASSETS+Articles.get(i).getImage();
        Image img = URLImage.createToStorage(enc, "Image"+Articles.get(i).getId(), urlImage,URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(img);
        cntImage.add(imgv);
        cntGlobal.add(cntImage);
        Container cntTitre = new Container(new TableLayout(2, 1));
            Label lbTitre = new Label(Articles.get(i).getTitre());
cntTitre.add(lbTitre);
cntGlobal.add(cntTitre);
Container cntDescri = new Container(new TableLayout(3, 1));
Label lbDesc = new Label(Statics.html2text(Articles.get(i).getDescription()));
cntDescri.add(lbDesc);
cntGlobal.add(cntDescri);
Container cntRead = new Container(new TableLayout(3, 2));
Button btread = new Button("Lire !");
 btread.addActionListener(e->{
     new ArticleSingleForm(idart).show();
            System.out.println("cliked ID :"+idart);
        });
      //  cntRead.setLeadComponent(btread);
cntRead.add(btread);
   Label lbVues = new Label("| Vues :"+Integer.toString(Articles.get(i).getVues()));
   cntRead.add(lbVues);
   
cntGlobal.add(cntRead);

this.add(cntGlobal);
Container cntsperator = new Container(new FlowLayout(CENTER));         
  Label lbsepe = new Label("");
  cntsperator.add(lbsepe);
   this.add(cntsperator);         
            
   
  
            
            
            
            
            
        }
        
           Toolbar tb = new Toolbar(true);
     this.setToolbar(tb);
      BibliothequeForm forms= new BibliothequeForm();
       Resources theme = UIManager.initFirstTheme("/theme");
        tb.addMaterialCommandToSideMenu("Retourner", FontImage.MATERIAL_10K, e -> {
            new HomeBlogForm("Blog").showBack();
        });
        tb.addMaterialCommandToSideMenu("Nos Clubs", FontImage.MATERIAL_CASINO, e -> new NewsfeedForm(theme).show());
        tb.addMaterialCommandToSideMenu("Notre Bibliotheque", FontImage.MATERIAL_BOOK, e -> forms.getForm().show());
        tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(theme).show());
        tb.addMaterialCommandToSideMenu("Blog", FontImage.MATERIAL_POST_ADD, e -> new HomeBlogForm("Blog").show());
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(theme).show());
            
        
        
    }
    
    
    
}
