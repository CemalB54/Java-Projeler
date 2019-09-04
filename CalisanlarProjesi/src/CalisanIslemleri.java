
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

// Veri tabanı üzerindeki veri ala/ güncelleme işlemleri...

public class CalisanIslemleri {
    private Connection con = null;
    public Statement statement = null;
    private PreparedStatement preparedStatement = null;

    
//CONSTRUCTER//
    public CalisanIslemleri() {
        //Veri tabanına bağlantı işlemleri
        String url = "jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.DB_ismi;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            System.out.println("Driver bulunamadı. . .");
        }
        
        try {
            con = DriverManager.getConnection(url, Database.KullaniciAdi, Database.Parola);
        } catch(SQLException ex) {
           System.out.println("bağlantı başarısız. . .");
           
        }
        System.out.println("bağlantı başarılı. . .");
    }
    
    public boolean GirisYap(String kullanici_adi, String parola){
        String sorgu = "Select * From adminler where username = ? and password = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullanici_adi);
            preparedStatement.setString(2, parola);
            
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); //rs miz bir değer göstermezse false, bir değer içerirse yani kullanici adı,parola doğru ise
                              // true döndürür.
        } catch (SQLException ex) {
            return false; // herhangi bir ex oluşursa yukardaki return çalışmaz. buraya da return yazmak gerekli.
        }          
    }
    
    public ArrayList<Calisan> calisanGetir(){
        ArrayList<Calisan> cikti = new ArrayList<Calisan>(); //veri tabanından alınan bilgiler cıktıya atılır sonra 
                                                            //fonksiyon tarafından dönüş parametresi olarak kullanıcak       
        
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * From calisanlar"); //calisan bilgileri rs de.
            
            while(rs.next()){ //rs üzerinde gezinerek bilgileri cıktıya atıyoruz.
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String departman = rs.getString("departman");
                String maas = rs.getString("maas");
                
                cikti.add(new Calisan(id, ad, soyad, departman, maas)); //rs de gezindikçe her yeni calışan çıktıya eklenir
            }
            return cikti;
        } catch (SQLException ex) {
            return null; //hata olursa hiç bir arraylist dönmicek. . .
        }
    }
    
    public void calisanEkle(String ad,String soyad,String departman,String maas){
        String sorgu = "Insert Into calisanlar (ad,soyad,departman,maas) VALUES(?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.executeUpdate(); //insert işlemini yapar. bilgiler eklenir.
            
        } catch (SQLException ex) {
            
        }
    }
    public void calisanGuncelle(int id,String yeni_ad,String  yeni_soyad,String  yeni_departman,String  yeni_maas){
        String sorgu = "Update calisanlar set ad = ? , soyad = ? , departman = ? , maas = ? where id = ?";
                                 //verdiğim id deki çalışanın bilgilerini güncelle dedik. . .
        
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setString(1,  yeni_ad);
            preparedStatement.setString(2,  yeni_soyad);
            preparedStatement.setString(3,  yeni_departman);
            preparedStatement.setString(4,  yeni_maas);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            
        }
    }
    public void calisanSil(int id){
        String sorgu = "Delete From calisanlar where id = ?";
        
        try {
            preparedStatement = con.prepareStatement(sorgu);            
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); //çalışan silinmiş oldu. . .
            
        } catch (SQLException ex) {
        }
        
        
    }
}


