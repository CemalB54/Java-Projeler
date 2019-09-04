
import java.awt.HeadlessException;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cemal BAYRAKTAR
 */
public class OyunEkrani extends JFrame {

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }
    
    
    public static void main(String[] args) {
        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");
        
        
        //*********ekranın özelliklerini ayarlayalım;***************************************
        ekran.setResizable(false);      
        ekran.setFocusable(false);      //klavye işlemleri jframe e odaklansın istemiyoruz
        ekran.setSize(800, 600);        
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //*************************************************************************
        
        
        OyunIcerigi oyun = new OyunIcerigi();
        
        //*****JPanel Ayarlarını yapıyoruz***********
        oyun.requestFocus();        //klavye işlemlerine odaklanıyor..
        oyun.addKeyListener(oyun);  //klavye işlemlerini algılamak için implement edildi.
        oyun.setFocusable(true);    //odağı JPanele vermiş olduk
        oyun.setFocusTraversalKeysEnabled(false);//klavye işlemlerini jpanelin anlaması için gerekli..
        //*******************************************
        //yukarıdaki işlemlerin sırası önemli olabilir.
        
        ekran.add(oyun); //jpaneli jframe e ekledik.
        ekran.setVisible(true);//panel, jframe eklendiğinde direkt olarak oluşsun..
        
        
    }
    
}
