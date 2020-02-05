package veritabani;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author Hrn
 */

public class UpdateKontrol{
    
    private final String kontrolAdresi = "https://arculatech.blogspot.com/p/arcmysql.html"; //"http://localhost/versionKontrol.html";
    private int bTekrarSayisi = 0;
    
    public String sonSurumuCek(){ 
        String veri = "";
        try {
            veri = tumSayfayiCek();
            return veri.substring(veri.indexOf("[surum]")+7,veri.indexOf("[/surum]"));
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Hata Bağlantı Yapılamadı...");
        }
        return "1.0";
    }
    
    public String yeniNelerVar(){
        String veri = "";
        try {
            veri = tumSayfayiCek();
            return veri.substring(veri.indexOf("[yeni]")+6,veri.indexOf("[/yeni]"));
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Hata Bağlantı Yapılamadı...");
        }
        return "";
    }
    
    public String yeniSurumAdresi(){ 
        String veri = "";
        try {
            veri = tumSayfayiCek();
            return veri.substring(veri.indexOf("[link]")+6,veri.indexOf("[/link]"));
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, "Hata Bağlantı Yapılamadı...");
        }
        return "";
    }
    
    private synchronized String tumSayfayiCek(){
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
        }
        
        URL urlAdresi = null;
        
        try {
            
            urlAdresi = new URL(kontrolAdresi);
            
        } catch (MalformedURLException ex) {
            
            System.err.println("Adres bulunamadı!");
            
        }
        
        InputStream html = null;

        try {
            
            html = urlAdresi.openStream();
            
        } catch (IOException ex) {
            if(bTekrarSayisi<6 && updateEkrani.calisiyor){
            JOptionPane.showMessageDialog(null, "Hata Bağlantı Yapılamadı"
                    + "\n10'ar Saniye Arayla 6 Kez Tekrar Denenecek");
            }
            while(updateEkrani.calisiyor){
            try {
                bTekrarSayisi++;
                if(bTekrarSayisi<6 && updateEkrani.calisiyor){
                    
                Thread.sleep(10000);
                
                if(updateEkrani.calisiyor){
                System.out.println("Adres Deneniyor...");
                html = urlAdresi.openStream();
                System.out.println("Başarılı!");
                break;
                }
                
                }
                else{

                break;
                  
                }
                
            } catch (InterruptedException ex1) {
            
            }   catch (IOException ex1) {
                
                }
            }
        }
        
        int c = 0;
        StringBuilder sb = new StringBuilder("");

        while(c != -1) {
            try {
                c = html.read();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Hata Bağlantı Yapılamadı HTML Çek");
            }
            
        sb.append((char)c);
        }
        return sb.toString();
    }
    
}

