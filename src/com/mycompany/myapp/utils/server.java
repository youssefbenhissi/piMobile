/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.utils;

import org.jsoup.Jsoup;

/**
 *
 * @author Alaa
 */
public class server {
     public static final String BASE_SERVER = "http://127.0.0.1:8000/api/";
     public static final String BASE_SERVER_ASSETS = "http://127.0.0.1:8000/assets/images/";
             public static String html2text(String html) {
    return Jsoup.parse(html).text();
}
}
