/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Club;
import entities.InscriptionClub;
import services.ServiceTask;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class InscriptionClubForm extends BaseForm {

    public InscriptionClubForm(Resources res, Club cl) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Inscription \n"+cl.getNom());
        getContentPane().setScrollVisible(false);
        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());
        super.addSideMenu(res);

       // tb.addSearchCommand(e -> {
        //});

        Image img = res.getImage("profile-background.jpg");
        Image afficheClub = UrlAffiche(cl.getPath());
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(afficheClub);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        add(LayeredLayout.encloseIn(
                sl
                
        ));
        TextField username = new TextField("");
        username.setUIID("TextFieldBlack");
        addStringValue(cl.getQuestionPr(), username);

        TextField QuestionnD = new TextField("");
        username.setUIID("TextFieldBlack");
        addStringValue(cl.getQuestionDe(), QuestionnD);

        TextField QuestionT = new TextField("");
        username.setUIID("TextFieldBlack");
        addStringValue(cl.getQuestionTr(), QuestionT);
        
//        CheckBox cb1 = CheckBox.createToggle(res.getImage("on-off-off.png"));
//        cb1.setUIID("Label");
//        cb1.setPressedIcon(res.getImage("on-off-on.png"));
//        CheckBox cb2 = CheckBox.createToggle(res.getImage("on-off-off.png"));
//        cb2.setUIID("Label");
//        cb2.setPressedIcon(res.getImage("on-off-on.png"));
//        
//        addStringValue("Facebook", FlowLayout.encloseRightMiddle(cb1));
//        addStringValue("Twitter", FlowLayout.encloseRightMiddle(cb2));
        //  TextField commentaireTextField = new TextField("", "ajouter un commentaire", 20, TextArea.ANY);
        Button commenterBouton = new Button("Valider");
        commenterBouton.addActionListener((add) -> {
            ServiceTask.InscriptionEmail("youssef.benhissi@esprit.tn","inscription à "+ cl.getNom());
            //System.out.println(c.getId());
            InscriptionClub inscri = new InscriptionClub(cl.getId(), SessionManager.getId(), cl.getQuestionPr(),cl.getQuestionDe(),cl.getQuestionTr(),username.getText(),QuestionnD.getText(),QuestionT.getText());
            ServiceTask.ajouterInscriptionCllub(inscri);
            ToastBar.showMessage("Nous avons enregistre votre inscription.Verifiez votre email", FontImage.MATERIAL_INFO);
            //new GUI.CommentaireClubForm(Resources.getGlobalResources(), evenDetails).show();

        });
        add(commenterBouton);
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
     public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/Affiche?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);

        return urli;

    }
}
