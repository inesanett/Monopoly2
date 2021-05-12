package oop;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Mängija {

    private String nimi;
    private int raha;
    private int asukoht;
    private boolean vangis;
    private int duubliteArv;
    private Circle nupp;
    private Color värv;

    public Mängija(String nimi, int raha, int asukoht, boolean vangis, int duubliteArv, Color värv) {
        this.nimi = nimi;
        this.raha = raha;
        this.asukoht = asukoht;
        this.vangis = vangis;
        this.duubliteArv = duubliteArv;
        this.nupp = new Circle(10);
        this.nupp.setFill(värv);
    }

    public void setAsukoht(int asukoht) {
        this.asukoht = asukoht;
    }

    public Circle getNupp() {
        return nupp;
    }

    //Peale igat veeretust rakenduv meetod, mis korrigeerib duublite järjestikust arvu
    public boolean korrigeeriDuubel(int täring1, int täring2, ArrayList<Mänguruut> mängulaud) {
        if (täring1 == täring2)
            duubliteArv += 1;
        else
            duubliteArv = 0;
        if (duubliteArv == 3) { //Kui saadakse kolmas duubel, siis rakendub vangimineku meetod
            mineVangi(mängulaud);
            return true;
        }
        return false;
    }

    //Meetod, mis rakendub kui on täidetud tingimused vangi minekuks
    public void mineVangi(ArrayList<Mänguruut> mängulaud) {
        duubliteArv = 0;
        vangis = true;
        asukoht = Mängulaud.leiaVangla(mängulaud);
    }

    public String getNimi() {
        return nimi;
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

    public boolean isVangis() {
        return vangis;
    }

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
