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
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import entities.Categories;
import entities.Tags;
import entities.Articles;
import services.ArticlesSingleService;
import services.CategorieService;
import utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Alaa
 */
public class ArticleSingleForm extends Form{
 EncodedImage enc;
 Date date;
 String email ="email1";
    public ArticleSingleForm(int id) {
        
        
        this.setLayout(new FlowLayout());

        Articles article = new Articles();
              

        ArticlesSingleService artser = new ArticlesSingleService();
        
        
        
        
        
        artser.ArticleVueinc(id);
        article = artser.GetArticleById(id);
         this.setTitle(article.getTitre());
        //System.out.println(article.getDescription());
        
        
        
        
        
        
         Container cntGlobal = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         
         
         //Image
         Container cntImage = new Container(new FlowLayout(CENTER));
       
           try {
               enc = EncodedImage.create("/load.png");
           } catch (IOException ex) {
             //  Logger.getLogger(HomeBlogForm.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println("ERROR");
           }
        String urlImage = Statics.BASE_SERVER_ASSETS+article.getImage();
        Image img = URLImage.createToStorage(enc, "Image"+article.getId(), urlImage,URLImage.RESIZE_SCALE);
        ImageViewer imgv = new ImageViewer(img);
        cntImage.add(imgv);
        cntGlobal.add(cntImage);
        
        
        
        
        //Titre
        Container cntTitre = new Container(new FlowLayout());
            Label lbTitre = new Label(Statics.html2text(article.getTitre()));
cntTitre.add(lbTitre);

if(artser.GetArticlesLike(id, "email1") != null){
            //System.out.println("Like");
            Button bttag = new Button("Dislike");
            cntTitre.add(bttag);
            bttag.addActionListener(e->{
                artser.ArticleLikeremove(id, email);
               
     new ArticleSingleForm(id).show();
        });
        }else {
            //System.out.println("Pas de like");
             Button bttag = new Button("Like");
            
             cntTitre.add(bttag);
             bttag.addActionListener(e->{
                   Dialog.show("Like", "Merci", "OK",null);
                  artser.ArticleLikeAdd(id, email);
    new ArticleSingleForm(id).show();
        });
        }







int nbrlik = artser.GetArticlesLikescount(id);
Label lblikes = new Label("Likes :"+nbrlik);
cntTitre.add(lblikes);
cntGlobal.add(cntTitre);


         // Vues - date - categ
Container cntVuNbr = new Container(new BoxLayout(BoxLayout.X_AXIS));

Label lbVues = new Label("Vues :"+Integer.toString(article.getVues()));
cntVuNbr.add(lbVues);
 SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        
     try {
         date = parser.parse(article.getDate());
     } catch (ParseException ex) {
        // Logger.getLogger(ArticleSingleForm.class.getName()).log(Level.SEVERE, null, ex);
     }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
Label lbDate = new Label("Date :"+formattedDate);
cntVuNbr.add(lbDate);

        CategorieService cats = new CategorieService();
        Categories categ = new Categories();
        categ = cats.GetCategorieById(article.getCat_id());
SpanLabel lbcat = new SpanLabel("Catégorie :"+categ.getNom());
cntVuNbr.add(lbcat);

cntGlobal.add(cntVuNbr);




    // description
    
    Container Descri = new Container(new FlowLayout(CENTER));
        SpanLabel lbdescri = new SpanLabel(Statics.html2text(article.getDescription()));
    Descri.add(lbdescri);
    cntGlobal.add(Descri);
    
    
    //Contenu 
    
     Container Contenu = new Container(new FlowLayout(CENTER));
        SpanLabel lbContenu = new SpanLabel(Statics.html2text(article.getContenu()));
    Contenu.add(lbContenu);
    cntGlobal.add(Contenu);
    
    //Tags
    Container tagscon = new Container(new BoxLayout(BoxLayout.X_AXIS));
        List<Tags> taglist = article.getListags();
        for(int i=0;i<taglist.size();i++){
            Button bttag = new Button(taglist.get(i).getNom());
            final int idtag = taglist.get(i).getId();
            bttag.addActionListener(e->{
     new ArticleListByIdTag(idtag).show();
            System.out.println("cliked ID :"+idtag);
        });
            tagscon.add(bttag);
        }
    cntGlobal.add(tagscon);
   Toolbar tb = new Toolbar(true);
     this.setToolbar(tb);
      Resources theme = UIManager.initFirstTheme("/theme");
      BibliothequeForm forms= new BibliothequeForm();
        tb.addMaterialCommandToSideMenu("Retourner", FontImage.MATERIAL_10K, e -> {
            new HomeBlogForm("Blog").showBack();
        });
        tb.addMaterialCommandToSideMenu("Nos Clubs", FontImage.MATERIAL_CASINO, e -> new NewsfeedForm(theme).show());
        tb.addMaterialCommandToSideMenu("Notre Bibliotheque", FontImage.MATERIAL_BOOK, e -> forms.getForm().show());
        tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(theme).show());
        tb.addMaterialCommandToSideMenu("Blog", FontImage.MATERIAL_POST_ADD, e -> new HomeBlogForm("Blog").show());
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(theme).show());
    

         
    
    this.add(cntGlobal);
  
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
}
