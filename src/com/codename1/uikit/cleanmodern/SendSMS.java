/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

/**
 *
 * @author youssef
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
public class SendSMS {
    public static final String ACCOUNT_SID = "ACfbd03ebad1bc7be3f64db3401386c998";
    public static final String AUTH_TOKEN = "fe8ca0d3c2eaf55d0a13ff46b7b3ecba";

    public static void sendSMSreservation() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new com.twilio.type.PhoneNumber("+21655378936"),//to
                new com.twilio.type.PhoneNumber("+17372456684"),//from 
                "un nouveau commentaire a été ajouté").create();
    }
}
