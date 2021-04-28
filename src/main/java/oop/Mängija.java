package oop;

//import java.sql.SQLOutput;
import java.util.ArrayList;

public class Mängija {

    //Isendiväljad
    private String nimi;
    private int raha;
    private int asukoht;
    private boolean vangis;
    private int duubliteArv;

    //Konstruktor
    public Mängija(String nimi, int raha, int asukoht, boolean vangis, int duubliteArv) {
        this.nimi = nimi;
        this.raha = raha;
        this.asukoht = asukoht;
        this.vangis = vangis;
        this.duubliteArv = duubliteArv;
    }

    //Meetod, mis kontrollib kas mängija on pankrotis
    public boolean onPankrotis(){
        if (this.raha < 0) {
            System.out.println("Mängija "+this.getNimi()+" on nüüd pankrotis ja kaotas. Mäng sai läbi.");
            return false; //return false, sest selliseks määratakse klassis "Mäng" muutuja mängKäib
        } else
            return true;
    }

    //Peale igat veeretust rakenduv meetod, mis korrigeerib duublite järjestikust arvu
    public void korrigeeriDuubel(int täring1, int täring2, ArrayList<Mänguruut> mängulaud) {
        if (täring1 == täring2)
            duubliteArv += 1;
        else
            duubliteArv = 0;
        if (duubliteArv == 3) { //Kui saadakse kolmas duubel, siis rakendub vangimineku meetod
            mineVangi(mängulaud);
            System.out.println("Veeretasid kolmanda järjestikuse duubli - Lähed vangi!");
        }
    }

    //Meetod, mis rakendub kui on täidetud tingimused vangi minekuks
    public void mineVangi(ArrayList<Mänguruut> mängulaud) {
        duubliteArv = 0;
        vangis = true;
        asukoht = Mängulaud.leiaVangla(mängulaud);
    }

    //Meetod, mis rakendub, kui mängija on vangis.
    public void veeretaVangistVabaks(ArrayList<Mänguruut> mängulaud) {
        if (duubliteArv == 1) {
            vangis = false;
            asukoht = Mängulaud.leiaVangla(mängulaud);
            duubliteArv = 0;
            System.out.println("Vabanesid vangist!");
        }
    }

    //Get- ja set-meetodid
    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {

        this.nimi = nimi;
    }

    public int getRaha() {

        return raha;
    }

    public void setRaha(int raha) {

        this.raha = raha;
    }

    public int getAsukoht() {

        return asukoht;
    }

    public void setVangis(boolean vangis) {
        this.vangis = vangis;
    }

    public int getDuubliteArv() {
        return duubliteArv;
    }

    public void setDuubliteArv(int duubliteArv) {
        this.duubliteArv = duubliteArv;
    }

    public void setAsukoht(int asukoht, ArrayList<Mänguruut> mängulaud) {
        /*Kui tahetakse panna asukohta, mis on suurem kui viimane mänguruut,
        siis alustatakse uut ringi + mängija saab raha*/

        if (asukoht > mängulaud.size() - 1) {//[-1 on siin sp, et esimene ruut on nr 0 ja seega viimane ruut kus saab olla on ruutude arv - 1]
            this.asukoht = (asukoht - mängulaud.size());
            this.raha += 20;
            System.out.println("Möödusid stardiruudust, saad 20 raha! Nüüd on sul " + this.getRaha() + " raha.");
        } else
            this.asukoht = asukoht;
        if (asukoht == Mängulaud.leiaVangiMinek(mängulaud)) {
            mineVangi(mängulaud);
            System.out.println("Sattusid \"Mine Vangi\" ruudule - Lähed vangi!");
        }
    }

    public boolean isVangis() {
        return vangis;
    }

    //toString
    @Override
    public String toString() {
        return "Mängija{" +
                "nimi='" + nimi + '\'' +
                ", raha=" + raha +
                ", asukoht=" + asukoht +
                ", vangis=" + vangis +
                '}';
    }
}
