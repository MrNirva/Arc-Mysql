package veritabani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hrn
 */

public class Baglanti {
    
    private final String kullaniciAdi;
    private final String parola;
    
    private String db_ismi;
    
    private final String host;
    
    private final int port;
    
    private Connection connection = null;
    
    private Statement statement = null;
    
    public void setDb_ismi(String db_ismi){
        this.db_ismi = db_ismi;
    }
    
    
    public Baglanti(String kullaniciAdi_s, String parola_s, String db_ismi_s, String host_s, Integer port_i){

        kullaniciAdi = kullaniciAdi_s;
        parola = parola_s;
        db_ismi = db_ismi_s;
        host = host_s;
        port = port_i;
 
    }
    
    public boolean baglan(Boolean kontrol){
        
     String url = "jdbc:mysql://" + host + ":" + port + "/" + db_ismi + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=utf8"; 
        
        try {
            
            connection = DriverManager.getConnection(url,kullaniciAdi,parola);
            statement = connection.createStatement();
            if(kontrol==true){
            JOptionPane.showMessageDialog(null, "Bağlantı Başarılı...");
            System.out.println("Bağlantı Başarılı...");
            }
            
            return true;
            
        } catch (SQLException ex) {
            
            if(kontrol==true){
            JOptionPane.showMessageDialog(null, "Bağlantı Başarısız!"
                        + "\n"+ex.toString(),"Hata!", 2);
            System.err.println("Bağlantı Başarısız");
            }
            
            return false;
            
        }
        
    }
    
    public ArrayList<String> veritabaniIsimleri(){
        
        String sorgu = "SHOW DATABASES";
        ArrayList<String> arr = new ArrayList<>();
        try {
            statement = connection.createStatement(); //ile con'dan statement'ı oluşturuyoruz
            ResultSet rs = statement.executeQuery(sorgu); 
            while(rs.next()){
                
                String ad = rs.getString(1);
                arr.add(ad);
                
            }
            return arr;
        }
        catch(Exception e){
            return arr;
        }
    }
    
    public ArrayList<String> veritabaniOlustur(String veritabaniAdi){
        ArrayList<String> bos = new ArrayList<>();
        String sorgu = "CREATE DATABASE `"+veritabaniAdi+"` DEFAULT CHARSET=utf8 COLLATE utf8_turkish_ci";
        try {
            statement = connection.createStatement(); //ile con'dan statement'ı oluşturuyoruz
            statement.executeUpdate(sorgu); 
            JOptionPane.showMessageDialog(null, "Veritabanı Oluşturuldu...");
            return veritabaniIsimleri();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Hata Veritabanı Oluşturma Başarısız!"
                        + "\n"+e.toString(),"Hata!", 2);
            return veritabaniIsimleri();
        }
        
    }
    
    public ArrayList<String> veritabaniSil(){
        
        String sorgu = "DROP DATABASE `"+db_ismi+"`";
        try {
            statement = connection.createStatement(); //ile con'dan statement'ı oluşturuyoruz
            statement.executeUpdate(sorgu); 
            JOptionPane.showMessageDialog(null, "Veritabanı Başarıyla Silindi!");
            return veritabaniIsimleri();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Hata Veritabanı Silinemedi!"
                        + "\n"+e.toString(),"Hata!", 2);
            return veritabaniIsimleri();
        }
    }
    
    public ArrayList<String> tabloOlustur(String tabloAdi,ArrayList<String> veriBasliklari,ArrayList<String> veriTurleri){
        String sorgu = "CREATE TABLE `"+db_ismi+"`.`"+tabloAdi+"` ( ";
        
        for(int i=0; i<veriBasliklari.size(); i++){
            
            sorgu += "`"+veriBasliklari.get(i)+"` "+veriTurleri.get(i)+" NOT NULL ";
            
            if(i != veriBasliklari.size()-1){
               sorgu += ","; 
            }
            
        }
        sorgu += ") ENGINE = InnoDB;";

        try {
            statement = connection.createStatement(); //ile con'dan statement'ı oluşturuyoruz
            statement.executeUpdate(sorgu); 
            JOptionPane.showMessageDialog(null, "Tablo Oluşturuldu...");
            return veritabaniIsimleri();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Hata Tablo Oluşturma Başarısız!"
                        + "\n"+e.toString(),"Hata!", 2);
            return veritabaniIsimleri();
        }
        
    }
    
    public ArrayList<String> tabloAdlandir(String tabloAdi,String yeniTabloAdi){
        String sorgu = "";

            sorgu = "RENAME TABLE `"+db_ismi+"`.`"+tabloAdi+"` TO `"+db_ismi+"`.`"+yeniTabloAdi+"`;";
        
        
        
        try {
            statement = connection.createStatement(); //ile con'dan statement'ı oluşturuyoruz
            statement.executeUpdate(sorgu); 

            JOptionPane.showMessageDialog(null, tabloAdi+" Tablosu "+yeniTabloAdi+" Olarak Adlandirildi...");
            return tabloIsimleri();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Hata Adlandırma Başarısız!"
                        + "\n"+e.toString(),"Hata!", 2);
            return veritabaniIsimleri();
        }
        
    }
    
    public ArrayList<String> tabloIsimleri(){
        
        String sorgu = "SHOW TABLES in `"+db_ismi+"`";
        ArrayList<String> arr = new ArrayList<>();
        try {
            statement = connection.createStatement(); //ile con'dan statement'ı oluşturuyoruz
            ResultSet rs = statement.executeQuery(sorgu); 
            while(rs.next()){
                
                String ad = rs.getString(1);
                arr.add(ad);
                
            }
            return arr;
        }
        catch(Exception e){
            return arr;
        }
    }
    
    public ArrayList<String> tabloSil(String tabloAdi){
        
        String sorgu = "DROP TABLE `"+db_ismi+"`.`"+tabloAdi+"`";

        try {
            statement = connection.createStatement(); //ile con'dan statement'ı oluşturuyoruz
            statement.executeUpdate(sorgu); 
            JOptionPane.showMessageDialog(null, "Tablo Başarıyla Silindi!");
            return tabloIsimleri();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Hata Tablo Silinemedi!"
                        + "\n"+e.toString(),"Hata!", 2);
            return tabloIsimleri();
        }
    }
    
    public String cName(String tabloAdi){
        
        String sorgu = "SELECT column_name\n" +
        "FROM INFORMATION_SCHEMA.COLUMNS\n" +
        "WHERE table_name = '"+tabloAdi+"'";
        
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while(rs.next()){
                return rs.getString(1);
            }
         }
         catch(Exception e){
             
         }
           
        return "";
        
    }
    
    public ArrayList<String> kIsimleri(String tabloAdi){
        
        String sorgu = "SELECT `COLUMN_NAME` \n" +
        "FROM `INFORMATION_SCHEMA`.`COLUMNS` \n" +
        "WHERE `TABLE_SCHEMA`='"+db_ismi+"' \n" +
        "AND `TABLE_NAME`='"+tabloAdi+"'";
        ArrayList<String> arr = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while(rs.next()){
                arr.add(rs.getString(1));
            }
            return arr;
        } catch (Exception e) {
        }
        return arr;
    }
    
    public Integer cSayisi(String tabloAdi){
        
        String sorgu = "SELECT COUNT(*)\n" +
        "FROM INFORMATION_SCHEMA.COLUMNS\n" +
        "WHERE TABLE_NAME = '"+tabloAdi+"' AND"
        + "`TABLE_SCHEMA`='"+db_ismi+"'";
        
         try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sorgu);
            while(rs.next()){
                return rs.getInt(1);
            }
         }
         catch(Exception e){
             
         }
           
        return 0;
    }


    public ArrayList<String> veriDondur(String tabloAdi){
        
        int kolonSayisi = cSayisi(tabloAdi);
        String [][]verilerim = null;
        String sorgu = "Select * From "+"`"+tabloAdi+"`";
        String veriler = "";
        ArrayList <String> arr = new ArrayList<>();
        
        try {
            
            statement = connection.createStatement(); 
            ResultSet rs = statement.executeQuery(sorgu); 
            
            while(rs.next()){
                for(int i=0; i<kolonSayisi;i++){
                    
                    veriler += rs.getString(i+1)+" | ";
                    
                }
                arr.add(veriler);
                veriler = "";
             }
            
            return arr;
            
        }catch(Exception e){
                
        
        }
        
        return arr;
        
    }
    
    
    
    public String[][] veriCek(String tabloAdi){
        
        int kolonSayisi = cSayisi(tabloAdi);
        String [][]verilerim = null;
        String sorgu = "Select * From "+"`"+tabloAdi+"`";
        String veriler = "";
        ArrayList <String> arr = new ArrayList<>();
        
        try {
            
            statement = connection.createStatement(); 
            ResultSet rs = statement.executeQuery(sorgu); 
            
            while(rs.next()){
                for(int i=0; i<kolonSayisi;i++){
                    
                    veriler += rs.getString(i+1)+" | ";
                    
                }
                arr.add(veriler);
                veriler = "";
            
            }
            verilerim = new String[arr.size()][kolonSayisi];
                
            statement = connection.createStatement(); 
            rs = statement.executeQuery(sorgu); 
            int a = 0;
            
            while(rs.next()){
                
                for(int i=0; i<kolonSayisi;i++){

                    verilerim[a][i] = rs.getString(i+1);
                    
                }
                
                a++;
            
            }
            
            return verilerim;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Listeleme Başarısız..."
                    + "\n"+ex.toString(),"Hata!", 2);
            return verilerim;
        }
        
    }
    
    public String[][] veriEkle(ArrayList<String> kolonAdlari,ArrayList<String> eklenecekler,String tabloAdi,Integer kolonSayisi){
        
        ArrayList<String> arr2 = new ArrayList<>();
        String sorgu = "";
        try {
            statement = connection.createStatement();
            sorgu = "INSERT INTO `"+tabloAdi+"` (";
            for(int i=0; i < kolonSayisi; i++){
                
                sorgu += kolonAdlari.get(i);
                if(i != kolonSayisi-1){
                    sorgu += ",";
                }
                
            }
            sorgu += ") VALUES(";
            for(int i=0; i < kolonSayisi; i++){
                sorgu+= "'";
                sorgu += eklenecekler.get(i);
                sorgu += "'";
                if(i != kolonSayisi-1){
                sorgu += ",";
                }
            }
            sorgu += ")";
            statement.executeUpdate(sorgu);
            String [][]verilerim = veriCek(tabloAdi);
            JOptionPane.showMessageDialog(null, "Veri Ekleme Başarılı...");
            return verilerim;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Hata Veri Ekleme Başarısız!"
                        + "\n"+e.toString(),"Hata!", 2);
            return null;
        }
        
    }
    
    public String[][] veriGuncelle(String tabloAdi, String kolonAdi,String yeniDeger,String eskiDeger){
        try {
            
            statement = connection.createStatement();
            String sorgu = "Update `"+tabloAdi+"` Set "+kolonAdi+" = '"+yeniDeger+"' where "+kolonAdi+" = '"+eskiDeger+"'";
            statement.executeUpdate(sorgu);
            String [][]verilerim = veriCek(tabloAdi);
            
            JOptionPane.showMessageDialog(null, "Güncelleme Başarılı...");

            return verilerim;
            
        }catch(Exception e){ 
                JOptionPane.showMessageDialog(null, "Hata Güncelleme Başarısız!"
                        + "\n"+e.toString(),"Hata!", 2);
            return null;
        } 
        
    }
    
    public String[][] veriSil(ArrayList<String> kolonAdlari,ArrayList<String> itemler,String tabloAdi){
        
        
        ArrayList<String> arr2 = new ArrayList<>();
        if(!(itemler.isEmpty())){
        try {
            statement = connection.createStatement();
            
            String sorgu = "Delete from `" + tabloAdi + "` WHERE";
            
            for(int i=0; i<itemler.size();i++){
            
                sorgu += " "+kolonAdlari.get(i) +" = '"+itemler.get(i)+"'";
            
                if(!(itemler.size()==1) && i<itemler.size()-1){
                    sorgu += " AND";
                } 
            }
            
            statement.executeUpdate(sorgu);
            String [][]verilerim = veriCek(tabloAdi);
            
            JOptionPane.showMessageDialog(null, "Silme Başarılı...");
            return verilerim;
            
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "Hata Silme Başarısız!"
                        + "\n"+e.toString(),"Hata!", 2);
       }
       }
       return null;
        
    }
    
    
    
}
