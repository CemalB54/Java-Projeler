//Çalışanların bilgileri burda
public class Calisan {
    private int ID;
    private String ad;
    private String soyad;
    private String departman;
    private String maas;

    public Calisan(int ID, String ad, String soyad, String departman, String maas) {
        this.ID = ID;
        this.ad = ad;
        this.soyad = soyad;
        this.departman = departman;
        this.maas = maas;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getMaas() {
        return maas;
    }

    public void setMaas(String maas) {
        this.maas = maas;
    }
    
    
}
