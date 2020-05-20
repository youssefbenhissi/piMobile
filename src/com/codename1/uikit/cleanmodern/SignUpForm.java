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

import Entities.Utilisateur;
import Services.Service1;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        username.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                createLineSeparator()
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((e) -> {

            Service1 ser = new Service1();
            if (username.getText().isEmpty()) {
                Dialog dlg = new Dialog(" saisir votre username ");
                Button ok = new Button(new Command("OK"));
                dlg.add(ok);
                dlg.showDialog();
                return;
            } else if (email.getText().isEmpty()) {

                Dialog dlg = new Dialog(" saisir votre adresse mail ");
                Button ok = new Button(new Command("OK"));
                dlg.add(ok);
                dlg.showDialog();
                return;
            } else if (password.getText().isEmpty()) {

                Dialog dlg = new Dialog("saisir votre mot de passe ");
                Button ok = new Button(new Command("OK"));
                dlg.add(ok);
                dlg.showDialog();
                return;
            }

            Utilisateur t = new Utilisateur(username.getText(), email.getText(), password.getText());
            System.out.println("a" + t.toString());

            ArrayList<Utilisateur> listTasks = new ArrayList<>();

            ser.ajoutTask(t);
            Button ok = new Button(new Command("OK"));

            Dialog dlg = new Dialog("Votre compte a ete cree" + " " + t.getNom_Utilisateur());

            TextArea taa = new TextArea("Bienvenue  ");
            taa.setEditable(false);
            taa.setUIID("DialogBody");
            taa.getAllStyles().setFgColor(0);
            dlg.add(taa);

            ok.getAllStyles().setBorder(Border.createEmpty());
            ok.getAllStyles().setFgColor(0);
            ok.getUnselectedStyle().setFgColor(100000);

            dlg.add(ok);
            dlg.showDialog();
            SendSMS.sendSMSreservation();
            new NewsfeedForm(res).show();
            //SignInFormm s = new SignInFormm(theme);
            //s.show();

        });
    }
    
}
