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
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.Club;
import entities.Evenement;
import java.util.List;
import services.ServiceTask;
import services.evenementService;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class EvenementForm extends BaseForm {
    evenementService service = new evenementService();
    public EvenementForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        List<Evenement> listEvenements = service.getAllTasks();
        for (Evenement cl : listEvenements) {
            Label spacer = new Label();
            Image afficheClub = UrlAffiche(cl.getImageEvenement());
            addTab(swipe, afficheClub, spacer, "", "", cl.getNomEvenement());
        }        
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
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[1].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Tous les evenements", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Mes réservations", barGroup);
        featured.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, all, featured),
                FlowLayout.encloseBottom(arrow)
        ));
        all.addActionListener(e->{
            new EvenementForm(res).show();
        });
        featured.addActionListener(e->{
            new MesReservation(res).show();
        });
        
        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        for (Evenement cl : listEvenements) {
            Image afficheClub = UrlAffiche(cl.getImageEvenement());
            addButton(afficheClub, cl.getDescriptionEvenement(), false, cl.getNomEvenement(), 32, cl);
        }
    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    
   private void addButton(Image img, String title, boolean liked, String nomclub, int commentCount, Evenement cl) {
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
            sp.setText("Nom: " + cl.getNomEvenement()+ "\n"
                    + "Capacite: " + cl.getCapaciteEvenement()+ "\n"
                    + "Prix de l'evenement : " + cl.getPrixEvenement()+ "\n"
            );
            sp.setTextUIID("textDialog");

            Button commentairesBouton = new Button("Commentaires");
             commentairesBouton.addActionListener((eee) -> {
                Resources theme = UIManager.initNamedTheme("/theme", "Theme");
                new CommentaireEvenementForm(theme, cl).show();
                ToastBar.showMessage("Nous avons enregistre votre commentaire", FontImage.MATERIAL_INFO);
            });
            Button reservationBouton = new Button("Reserver");
            //reservationBouton.setUIID("CalendarHourSelected");
            reservationBouton.addActionListener((eee) -> {
                service.reserver(SessionManager.getUserName(), cl.getIdEvenement());
            });
            
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_STAR, s);
            

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
            ShareButton sb = new ShareButton();
            sb.setText("Partager");
            dlg.addComponent(BorderLayout.SOUTH, BoxLayout.encloseX(reservationBouton, commentairesBouton));

            Dimension pre = dlg.getContentPane().getPreferredSize();
            int h = Display.getInstance().getDisplayHeight();
            int w = Display.getInstance().getDisplayWidth();

            dlg.show(h / 2, w / 6, 20, 50);
        });
    }
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
