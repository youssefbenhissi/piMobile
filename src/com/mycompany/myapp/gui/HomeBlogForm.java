/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.mycompany.myapp.models.Articles;
import com.mycompany.myapp.models.Categories;
import com.mycompany.myapp.services.ArticlesService;
import com.mycompany.myapp.services.CategorieService;
import com.mycompany.myapp.utils.server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;


/**
 *
 * @author Alaa
 */
public class HomeBlogForm extends Form{
    ArrayList<Articles> Articles;
    ArrayList<Categories> Categories;
     EncodedImage enc;
    public HomeBlogForm(String name) {
       this.setLayout(new BorderLayout());
       
        Resources theme = UIManager.initFirstTheme("/theme");
    Container cnt1, cnt2, cnt3;
    Tabs tab;
        this.setTitle(name);
        
        ArticlesService art = new ArticlesService();
        
        Articles=art.GetArticles();
        
        
        
        
        
        
       UIBuilder ui = new UIBuilder();
        cnt1 = ui.createContainer(theme, "GUI 1");
        Container rechcontainer = new Container(new FlowLayout(CENTER,CENTER));
          TextField rechfield = new TextField(10);
        Button btrech = new Button("Rechercher");
         btrech.addActionListener(e->{
             //System.out.println(btrech.getText());
     new ArticleListByRechercheForm(rechfield.getText()).show();
            
        });
        rechcontainer.add(rechfield);
        rechcontainer.add(btrech);
        cnt1.add(rechcontainer);
        
        
        
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
        String urlImage = server.BASE_SERVER_ASSETS+Articles.get(i).getImage();
        Image img = URLImage.createToStorage(enc, "Image"+Articles.get(i).getId(), urlImage,URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(img);
        cntImage.add(imgv);
        cntGlobal.add(cntImage);
        Container cntTitre = new Container(new TableLayout(2, 1));
            Label lbTitre = new Label(Articles.get(i).getTitre());
cntTitre.add(lbTitre);
cntGlobal.add(cntTitre);
Container cntDescri = new Container(new TableLayout(3, 1));
Label lbDesc = new Label(server.html2text(Articles.get(i).getDescription()));
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

cnt1.add(cntGlobal);
Container cntsperator = new Container(new FlowLayout(CENTER));         
  Label lbsepe = new Label("");
  cntsperator.add(lbsepe);
   cnt1.add(cntsperator);         
            
            
            
            
            
            
            
        }
        
        cnt2 = ui.createContainer(theme, "GUI 2");
        
        
        
        
        CategorieService catser = new CategorieService();
        Categories = catser.GetCats();
        
        
         for(int i =0;i<Categories.size();i++){
          
            final int idcat = Categories.get(i).getId();
            
            Container cntGlobal = new Container(new FlowLayout(CENTER));
       
        
        Container cntTitre = new Container(new FlowLayout(LEFT));
            Label lbTitre = new Label(Categories.get(i).getNom());
cntTitre.add(lbTitre);
cntGlobal.add(cntTitre);

Container cntRead = new Container(new FlowLayout(RIGHT));
Button btread = new Button("Voir les articles");
 btread.addActionListener(e->{
   new ArticleListByIdForm(idcat).show();
            System.out.println("cliked ID :"+idcat);
        });
      //  cntRead.setLeadComponent(btread);
cntRead.add(btread);
cntGlobal.add(cntRead);

cnt2.add(cntGlobal);
Container cntsperator = new Container(new FlowLayout(CENTER));         
  Label lbsepe = new Label("");
  cntsperator.add(lbsepe);
   cnt2.add(cntsperator);         
            
            
            
            
            
            
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Populaire
        cnt3 = ui.createContainer(theme, "GUI 3");
        
        List<Articles> Articlespop = art.GetArticlesPopulaire();
        
                for(int i =0;i<Articlespop.size();i++){
          
            final int idart = Articlespop.get(i).getId();
            
            Container cntGlobal = new Container(new FlowLayout());
        Container cntImage = new Container(new TableLayout(1, 1));
       
           try {
               enc = EncodedImage.create("/load.png");
           } catch (IOException ex) {
             //  Logger.getLogger(HomeBlogForm.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println("ERROR");
           }
        String urlImage = server.BASE_SERVER_ASSETS+Articlespop.get(i).getImage();
        Image img = URLImage.createToStorage(enc, "Image"+Articlespop.get(i).getId(), urlImage,URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(img);
        cntImage.add(imgv);
        cntGlobal.add(cntImage);
        Container cntTitre = new Container(new TableLayout(2, 1));
            Label lbTitre = new Label(Articlespop.get(i).getTitre());
cntTitre.add(lbTitre);
cntGlobal.add(cntTitre);
Container cntDescri = new Container(new TableLayout(3, 1));
Label lbDesc = new Label(server.html2text(Articlespop.get(i).getDescription()));
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
   Label lbVues = new Label("| Vues :"+Integer.toString(Articlespop.get(i).getVues()));
   cntRead.add(lbVues);
   
cntGlobal.add(cntRead);

cnt3.add(cntGlobal);
Container cntsperator = new Container(new FlowLayout(CENTER));         
  Label lbsepe = new Label("");
  cntsperator.add(lbsepe);
   cnt3.add(cntsperator);         
            
            
            
            
            
            
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
      
        //Form HomeBlog = new Form("BLOG", new BorderLayout());
        tab = new Tabs();
        tab.addTab("Articles", cnt1);
        tab.addTab("Catégories", cnt2);

        tab.addTab("Populaires", cnt3);
      
     
       
        //this.add(BorderLayout.CENTER, tab);
        this.add(BorderLayout.CENTER,tab);
     //  HomeBlog.show();
        
    }
    
    
    
    
    

}
