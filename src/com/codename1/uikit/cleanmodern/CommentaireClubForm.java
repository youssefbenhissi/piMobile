/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

//import Entities.Evenement;
import Entities.CommentaireClub;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
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
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Stroke;
import com.codename1.ui.plaf.RoundBorder;
import entities.Club;
import java.util.ArrayList;
import services.ServiceTask;

/**
 *
 * @author solta
 */
public class CommentaireClubForm extends BaseForm {

    public CommentaireClubForm(Resources res, Club evenDetails) {
        super(BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setTitle("Bienvenue chez nous ");
        getContentPane().setScrollVisible(true);
        super.addSideMenu(res);

        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());

        Image img = res.getImage("transparentToolBar.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth()) / 4);
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        add(LayeredLayout.encloseIn(sl));

        Font largeBoldSystemFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        Font dateFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL);
        Image afficheEvenement = UrlAffiche(evenDetails.getPath());
        ScaleImageLabel image = new ScaleImageLabel(afficheEvenement);

        Container carrouselContainer = new Container();
        carrouselContainer.setLayout(new BorderLayout());
        carrouselContainer.addComponent(BorderLayout.CENTER, new ScaleImageLabel(afficheEvenement));

        carrouselContainer.addComponent(BorderLayout.NORTH, BoxLayout.encloseY(
                FlowLayout.encloseCenter(createForFont(largeBoldSystemFont, evenDetails.getNom()))
                ));

        add(LayeredLayout.encloseIn(carrouselContainer));
        Label commentaireLabel = new Label("Commentaires");
        add(FlowLayout.encloseCenter(commentaireLabel));
        commentaireLabel.getAllStyles().setFgColor(0x0099ff);

        // commentaire
        ArrayList<CommentaireClub> commentaires = ServiceTask.afficherCommentaire(String.valueOf(evenDetails.getId()));
        if (commentaires.size() != 0) {
            for (CommentaireClub commentaire : commentaires) {
                add(addCommentaire(commentaire));
            }
        } else {
            refreshTheme();
            Image afficheCommentaire = res.getImage("pasEvenement.png");
            ScaleImageLabel a = new ScaleImageLabel(afficheCommentaire);
            a.setUIID("Container");
            a.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FIT);
            Label text = new Label("aucun commentaire");
            add(LayeredLayout.encloseIn(text));
            add(LayeredLayout.encloseIn(a));

        }

        TextField commentaireTextField = new TextField("", "ajouter un commentaire", 20, TextArea.ANY);
        Button commenterBouton = new Button("Commenter");


        commenterBouton.addActionListener((add) -> {
            SendSMS.sendSMSreservation();
            CommentaireClub commentaire = new CommentaireClub(evenDetails.getId(), SessionManager.getId(), "Ajoute par "+SessionManager.getUserName()+":\n"+commentaireTextField.getText());
            ServiceTask.ajouterCommentaire(commentaire);
            new CommentaireClubForm(Resources.getGlobalResources(), evenDetails).show();

        });

        add(BoxLayout.encloseY(commentaireTextField, commenterBouton));

    }

    private Container addCommentaire(CommentaireClub commentaire) {
        int height = Display.getInstance().convertToPixels(4f);
        int width = Display.getInstance().convertToPixels(4f);
        Image avatarImage = Resources.getGlobalResources().getImage("avatar.png").fill(width, height);

        TextArea contenuCommentaire = new TextArea(commentaire.getMessage());
        contenuCommentaire.setEditable(false);

        Style contenuCommentaireStyle = contenuCommentaire.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        contenuCommentaireStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        contenuCommentaireStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        contenuCommentaireStyle.setMargin(Component.BOTTOM, 3);

        Container cnt = new Container();
        cnt.setLayout(new BorderLayout());
        cnt.addComponent(BorderLayout.CENTER, contenuCommentaire);
        cnt.addComponent(BorderLayout.WEST, new ScaleImageLabel(avatarImage));

        return cnt;
    }

    public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/Affiche?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);
        return urli;

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
