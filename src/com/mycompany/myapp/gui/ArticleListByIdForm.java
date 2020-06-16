/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.table.TableLayout;

import com.mycompany.myapp.models.Articles;
import com.mycompany.myapp.models.Categories;
import com.mycompany.myapp.models.Tags;
import com.mycompany.myapp.services.ArticlesService;
import com.mycompany.myapp.services.ArticlesSingleService;
import com.mycompany.myapp.services.CategorieService;
import com.mycompany.myapp.utils.server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

this.add(cntGlobal);
Container cntsperator = new Container(new FlowLayout(CENTER));         
  Label lbsepe = new Label("");
  cntsperator.add(lbsepe);
   this.add(cntsperator);         
            
   
  
            
            
            
            
            
        }
        
         this.getToolbar().addCommandToOverflowMenu("Retourner", null, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
           new HomeBlogForm("Blog").showBack();
            }
        });
            
        
        
    }
    
    
    
}
