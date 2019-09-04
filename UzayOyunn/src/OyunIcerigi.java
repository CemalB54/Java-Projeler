
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cemal BAYRAKTAR
 */
public class OyunIcerigi extends JPanel implements KeyListener,ActionListener{
    //class a ait özellik ve değişkenleri tanımlayalım;
    
    Timer timer = new Timer(5, this); //(top a hareket vermek için)5 ms de bir ActionListener e ait ActionPerformed metotuna gider.
    
    
    private int harcanan_ates=0;
    private BufferedImage image;              //eklenecek resim...
    
    private int top_X = 0;                    //top x koordinat
    private int top_Y = 0;                    //top y koordinat
    private int top_X_otele =  2;             //öteleme miktarı
            
    private int uzayGemisi_X = 0;             //gemi x koordinat
    private int uzayGemisi_Y = 490;           //gemi y koordinat
    private int uzayGemisi_X_otele = 30;      //öteleme miktarı
    private int uzayGemisi_Y_otele = 20;      //y öteleme miktarı
    
    private int mermi_x = 0;
    private int mermi_Y_otele=10;
    private ArrayList<Mermi> mermiler = new ArrayList<Mermi>();  //aynı anda bir çok mermi atmak için mermi nesnelerini tutan dizi 
    
    //***************************************************
    class Mermi{
        private int x;
        private int y;

        public Mermi(int x, int y) {
            this.x = x;
            this.y = y;
            
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
        
    }
    //***************************************************************************
    
    
    
    public OyunIcerigi() {    //*******YAPILANDIRICI METOT**********
        
        try {
            image = ImageIO.read(new FileImageInputStream(new File("gemi.png")));
        } catch (IOException ex) {
            Logger.getLogger(OyunIcerigi.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.black);   //panel siyah olarak ayarlandı
        timer.start();
    }//*************************************************************

    
    @Override //***********çizim işlemleri için Override edildi..**************************
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
        g.setColor(Color.red);            //top için kırmızı renk seçildi.  
        g.fillOval(top_X,top_Y,20,20);    //top için kordinat ve büyüklük bilgiileri atandı.  
        
        g.drawImage(image, uzayGemisi_X, uzayGemisi_Y, image.getWidth()/10, image.getHeight()/10, this); //resim için parametreler..
        
        
        g.setColor(Color.blue); //mermiler mavi çizilecek
        for(Mermi mermi: mermiler){
            g.fillRect(mermi.getX(),mermi.getY(), 10, 10); //oluşan her bir Mermi nesnesi 'mermiler' array listine atılmıştı.
                                                           //forEach döngüsü ile her nesne arraylistten okunup ekrana basılıyor.
        }
        
        //********Topu vurursak olacaklar******************************
        if(KontrolEt()){
            timer.stop();
            String message = " KAZANDINIZ !\n"+
                             "Harcanan Ateş Sayısı: "+harcanan_ates;
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }//**********************************************************
    }
    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }
  //*************************************************************************************** 
    
    
    
  //************Top u vurduk  mu acaba ? Diye yapılan sorgu kısmı**************************
    public boolean KontrolEt(){
    for(Mermi mermi:mermiler){
        if(new Rectangle(mermi.getX(), mermi.getY(),10,10).intersects(new Rectangle(top_X, 0, 14, 14))){
        //her bir mermi nesnesi, Top ile kesişiyor mu diye sorgu yapılıyor(NOT: Top, 14x14 kare gibi tanımlandı)
            
            return true;    
        }   
    }
    return  false;
}//****************************************************************************************
    
    
    
    //Interface kullandığımız için bunları override etmek zorundayız. bu metotları kullanacağız.
    @Override
    public void keyTyped(KeyEvent e) {       
    }

    @Override  //*******uzay gemisi hareket kabiliyeti için gerekli kodlar;************
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode(); //klavyeden inputu aldık
        
        if(c == KeyEvent.VK_LEFT){  //input sol tuşu ise;
            uzayGemisi_X -= uzayGemisi_X_otele;
            if(uzayGemisi_X <= 0){
                uzayGemisi_X =0;
            }
        }
        if(c == KeyEvent.VK_RIGHT){ //input sağ tuşu ise;
            uzayGemisi_X += uzayGemisi_X_otele;
            if(uzayGemisi_X >= 755){
                uzayGemisi_X = 755;
            }
        }
        if(c == KeyEvent.VK_UP){   //input yukarı tuşu ise
            uzayGemisi_Y -= uzayGemisi_Y_otele;
            if(uzayGemisi_Y <= 350){
                uzayGemisi_Y = 350;
            }
        }
        if(c == KeyEvent.VK_DOWN){  //input aşağı tuşu ise
            uzayGemisi_Y += uzayGemisi_Y_otele;
            if(uzayGemisi_Y>=490){
                uzayGemisi_Y=490;
            }
        }      
    //************************************************************************
        
        
    //**** Space'e her basıldığında mermi fırlatıyor..************************    
        if(c == KeyEvent.VK_SPACE){
            mermiler.add(new Mermi(uzayGemisi_X+15, uzayGemisi_Y-20));  //Space tuşuna her basıldığında yeni mermi oluşuyor. . .
            harcanan_ates++; //atılan mermiler sayılıyor. . .
        }//*******************************************************************
    }

    
    
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //*********mermiye hareket verecek komutlar*****************************
        for(Mermi mermi:mermiler){
            mermi.setY(mermi.getY()-mermi_Y_otele); //oluşan her yeni mermi nesnesi, 5ms'de, öteleme miktarı kadar öteleniyor. . .
        }
        //**********************************************************************
        
        
        //**************Top'a Hareket Veren Komutlar*****************
        top_X += top_X_otele;
        
        if(top_X >= 775){
           top_X_otele = -top_X_otele;
        }
        if(top_X <= 0){
            top_X_otele = -top_X_otele;
        }
        //***********************************************************

        repaint(); //güncel şekilleri ekrana çizdirmek için çağırıyor. repaint'te paint i çağırıp ekrana çizim yaptırıyor. . .
    }
    
    
}
