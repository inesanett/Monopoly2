package oop;

public class Mänguruut {

    //Isendiväljad
    private int asukoht;
    private String nimi;
    private int hind;
    private int rent;
    private String sektsioon;
    private boolean krunt;
    private boolean ostetud;
    private Mängija omanik;

    //Konstruktor
    public Mänguruut(int asukoht, String nimi, int hind, int rent, String sektsioon, boolean krunt, boolean ostetud) {
        this.nimi = nimi;
        this.asukoht = asukoht;
        this.hind = hind;
        this.rent = rent;
        this.sektsioon = sektsioon;
        this.krunt = krunt;
        this.ostetud = ostetud;
        this.omanik=null;
    }


    //Get-meetodid
    public String getNimi() {
        return nimi;
    }

    public int getAsukoht() {
        return asukoht;
    }

    public int getHind() {
        return hind;
    }

    public int getRent() {
        return rent;
    }

    public String getSektsioon() {
        return sektsioon;
    }

    public boolean isKrunt() {
        return krunt;
    }

    public boolean isOstetud() {
        return ostetud;
    }

    public Mängija getOmanik() {
        return omanik;
    }

    //Set-meetodid
    public void setRent(int rent) {
        this.rent = rent;
    }

    public void setOstetud(boolean ostetud) {
        this.ostetud = ostetud;
    }

    public void setOmanik(Mängija omanik) {
        this.omanik = omanik;
    }

    @Override
    public String toString() {
        return "Mänguruut{" +
                "asukoht=" + asukoht +
                ", nimi='" + nimi + '\'' +
                ", hind=" + hind +
                ", rent=" + rent +
                ", sektsioon='" + sektsioon + '\'' +
                ", krunt=" + krunt +
                ", ostetud=" + ostetud +
                ", omanik=" + omanik +
                '}';
    }
}

