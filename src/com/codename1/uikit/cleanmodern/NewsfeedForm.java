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

import com.codename1.components.InteractionDialog;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.Preferences;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.CN.convertToPixels;
import static com.codename1.ui.CN.getCurrentForm;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.Club;
import java.util.List;
import services.ServiceTask;
import com.codename1.uikit.cleanmodern.InscriptionClubForm;
import entities.whishlist;
import java.util.ArrayList;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class NewsfeedForm extends BaseForm {

    ServiceTask service = new ServiceTask();

    public NewsfeedForm(Resources res) {
        super("Clubs", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Clubs");
        getContentPane().setScrollVisible(false);
//        Form previous = Display.getInstance().getCurrent();
//        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();
        List<Club> listClubs = service.getAllTasks();
        for (Club cl : listClubs) {
            Label spacer = new Label();
            Image afficheClub = UrlAffiche(cl.getPath());
            addTab(swipe, afficheClub, spacer, "100 Likes  ", "66 Comments", cl.getNom(), cl.getId());
        }
        Label spacer1 = new Label();
        Label spacer2 = new Label();
        //addTab(swipe, res.getImage("news-item.jpg"), spacer1, "15 Likes  ", "85 Comments", "Integer ut placerat purued non dignissim neque. ");
        //addTab(swipe, res.getImage("dog.jpg"), spacer1, "100 Likes  ", "66 Comments", "Dogs are cute: story at 11");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("All", barGroup);
        all.setUIID("SelectBar");
        all.addActionListener(e -> {
            
            List<Club> listClub = service.getAllTasks();
            new topThreeClub(res, (ArrayList<Club>) listClub,0).show();
            //addButton(res.getImage("news-item-2.jpg"), "Fusce ornare cursus masspretium tortor integer placera.", true, 15, 21);
        });
        
        RadioButton popular = RadioButton.createToggle("Top 3", barGroup);
        popular.setUIID("SelectBar");
        popular.addActionListener(e -> {
            ServiceTask service = new ServiceTask();

            ArrayList<Club> liste = service.getTopThree();
            new topThreeClub(res, liste,1).show();
        });
        RadioButton myFavorite = RadioButton.createToggle("Mes favoris", barGroup);
        myFavorite.setUIID("SelectBar");
        myFavorite.addActionListener(e -> {
            ServiceTask service = new ServiceTask();

            ArrayList<Club> liste = service.afficherwhishilist(Integer.toString(SessionManager.getId()));
            for(Club c: liste){
                System.out.println(c.toString());
            }
            new topThreeClub(res, liste,2).show();
        });
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, all, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
//        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        for (Club cl : listClubs) {
            Image afficheClub = UrlAffiche(cl.getPath());
            addButton(afficheClub, cl.getDescription(), false, cl.getNom(), 32, cl);
        }
        //addButton(res.getImage("news-item-2.jpg"), "Fusce ornare cursus masspretium tortor integer placera.", true, 15, 21);
        //addButton(res.getImage("news-item-3.jpg"), "Maecenas eu risus blanscelerisque massa non amcorpe.", false, 36, 15);
        //addButton(res.getImage("news-item-4.jpg"), "Pellentesque non lorem diam. Proin at ex sollicia.", false, 11, 9);
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text, int id) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);
        Button b = new Button("", heartImage);
        b.setUIID("bbb");
        b.addActionListener(e -> {
            whishlist w = new whishlist();
            w.setId_club(id);
            w.setId_user(SessionManager.getId());
            ServiceTask.ajouterwhishlist(w);
            ToastBar.showMessage("votre club a ete enregistre dans votre whishlist. Vous recevrez un email dès que les inscriptions sont ouverts", FontImage.MATERIAL_INFO);
        });
        likes.addPointerPressedListener(e -> System.out.println("jjj"));
        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(b),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addButton(Image img, String title, boolean liked, String nomclub, int commentCount, Club cl) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(nomclub);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        Label likes = new Label(title);
        likes.setTextPosition(RIGHT);
        if (!liked) {
            FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
        } else {
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
            likes.setIcon(heartImage);
        }
       // Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
        FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes)
                ));
        add(cnt);
        image.addActionListener((e) -> {
            SpanLabel sp = new SpanLabel();
            InteractionDialog dlg = new InteractionDialog();
            dlg.setLayout(new BorderLayout());
            sp.setText("Nom: " + cl.getNom() + "\n"
                    + "Capacite: " + cl.getCapacite() + "\n"
                    + "Moyenne de like : " + cl.getMoyenneLike() + "\n"
                    + "description: " + cl.getDescription()
            );
            sp.setTextUIID("textDialog");

            Button commentairesBouton = new Button("Commentaires");
            //commentairesBouton.setUIID("CalendarHourSelected");
            commentairesBouton.addActionListener((eee) -> {
                Resources theme = UIManager.initNamedTheme("/theme", "Theme");
                new CommentaireClubForm(theme, cl).show();
                ToastBar.showMessage("Nous avons enregistre votre commentaire", FontImage.MATERIAL_INFO);
            });

            Button reservationBouton = new Button("S'inscrire");
            //reservationBouton.setUIID("CalendarHourSelected");
            reservationBouton.addActionListener((eee) -> {
                PSDTutorial p = new PSDTutorial();
                p.start(cl);
                Resources th = UIManager.initNamedTheme("/theme", "Theme");
                new InscriptionClubForm(th, cl).show();
                //ServiceTask.InscriptionEmail("youssef.benhissi@esprit.tn", "youssef");
                //ServiceTask.convertirPdf();
                //showReviewWidget(cl);
            });
            Button evalluation = new Button("Eval");
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_STAR, s);
            evalluation.setIcon(heartImage);
            evalluation.addActionListener((eee) -> {
                showReviewWidget(cl);
            });

            Container cntDetails = new Container(new BorderLayout());

            Button closeButton = new Button();
            Style closeStyle = closeButton.getAllStyles();
            closeStyle.setFgColor(0xffffff);
            closeStyle.setBgTransparency(0);
            closeStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
            closeStyle.setPadding(3, 3, 3, 3);
            closeStyle.setBorder(RoundBorder.create().shadowOpacity(100));
            FontImage.setMaterialIcon(closeButton, FontImage.MATERIAL_CLOSE);
            closeButton.addActionListener((ee) -> dlg.dispose());

            dlg.addComponent(BorderLayout.NORTH, FlowLayout.encloseRight(closeButton));
            dlg.addComponent(BorderLayout.CENTER, sp);
            dlg.addComponent(BorderLayout.SOUTH, BoxLayout.encloseX(reservationBouton, commentairesBouton, evalluation));

            Dimension pre = dlg.getContentPane().getPreferredSize();
            int h = Display.getInstance().getDisplayHeight();
            int w = Display.getInstance().getDisplayWidth();

            dlg.show(h / 2, w / 6, 20, 50);
        });
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }

    public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/Affiche?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);

        return urli;

    }

    void showReviewWidget(Club cl) {
        Preferences.set("alreadyRated", true);
        InteractionDialog id = new InteractionDialog("Evaluation");
        Form f = getCurrentForm();
        id.setLayout(new BorderLayout());
        Slider rate = createStarRankSlider();
        Button ok = new Button("OK");
        Button no = new Button("No Thanks");
        id.add(CENTER, FlowLayout.encloseCenterMiddle(rate)).
                add(BorderLayout.SOUTH, GridLayout.encloseIn(2, no, ok));
        int height = id.getPreferredH();
        id.show(f.getHeight() - height - f.getTitleArea().getHeight(), 0, 0, 0);
        no.addActionListener(e -> id.dispose());
        ok.addActionListener(e -> {
            ToastBar.showMessage("Nous avons enregistre votre avis", FontImage.MATERIAL_INFO);
            System.out.println(rate.getProgress());
            id.dispose();
            if (rate.getProgress() >= 5) {
                ServiceTask.ajouterEtoiles(cl.getId(), rate.getProgress());
            } else {
                ServiceTask.ajouterEtoiles(cl.getId(), rate.getProgress());
                if (Dialog.show("Dis_nous pourquoi?", "Vous voulez nous rapportez quelque chose?", "Ecrire", "Fermer")) {
                    Message msg = new Message("");
                    Display.getInstance().sendMessage(new String[]{Display.getInstance().getProperty("built_by_user", "youremail@somewhere.com")},
                            "Reclamation", msg);
                    new NewsfeedForm(Resources.getGlobalResources()).show();
                }
            }
        });
    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider() {
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        Slider starRank = new Slider() {
            public void refreshTheme(boolean merge) {
                initStarRankStyle(getSliderEmptySelectedStyle(), emptyStar);
                initStarRankStyle(getSliderEmptyUnselectedStyle(), emptyStar);
                initStarRankStyle(getSliderFullSelectedStyle(), fullStar);
                initStarRankStyle(getSliderFullUnselectedStyle(), fullStar);
            }
        };
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }
}
