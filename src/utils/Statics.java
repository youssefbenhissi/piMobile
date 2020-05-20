/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import org.jsoup.Jsoup;
/**
 *
 * @author bhk
 */
public class Statics {
    public static final String BASE_URL="http://127.0.0.1:8000/";
     public static final String BASE_SERVER_ASSETS = "http://127.0.0.1:8000/assets/images/";
     
             public static String html2text(String html) {
    return Jsoup.parse(html).text();
}
    
}
