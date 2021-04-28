package oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mäng {

    //Veeretamise meetod
    public static int veeretus() {
        return (int) Math.round(Math.random() * 5 + 1);
    }

    public static void main(String[] args) throws Exception {
        //Loome mängulaua
        ArrayList<Mänguruut> mängulaud = Mängulaud.mängulauaLoomine();
        Scanner scan = new Scanner(System.in); //klaviatuurilt sisestamiseks

        //Küsime palju raha mängijatel alguses on
        int algRaha = 150;
        boolean rahaSobib = false;
        while (!rahaSobib) {
            System.out.println("Sisestage stardiraha (positiivne täisarv): ");
            int sisestatudRaha = scan.nextInt();
            if (sisestatudRaha > 0) {
                rahaSobib = true;
                algRaha = sisestatudRaha;
            }
        }

        //Küsib mängijate arvu ja loob vastava arvu mängijaid
        System.out.println("Sisestage mängijate arv: ");
        int mängijate_arv = scan.nextInt();
        Mängija[] mängijad = new Mängija[mängijate_arv];
        for (int i = 0; i < mängijate_arv + 1; i++) {
            String nimi = scan.nextLine();
            if (i > 0) {
                Mängija mängija = new Mängija(nimi, algRaha, 0, false, 0);
                mängijad[i - 1] = mängija;
            }
            if (i < mängijate_arv)
                System.out.println("Sisestage " + (i + 1) + ". mängija nimi: ");
        }

        boolean mängKäib = true; //näitab, kas mäng käib või ei

        //Tsükkel, mis kordub nii kaua kuni mäng kestab
        while (mängKäib) {
            //for-tsükkel, et toimiks sama protsess iga mängija jaoks
            for (int i = 0; i < mängijate_arv; i++) {
                String kasMängijaOstabVabaks = "";

                //Kui mängija on vangis, siis küsitakse, kas ta soovib end vabaks osta
                if (mängijad[i].isVangis()) {
                    System.out.println(mängijad[i].getNimi() + ", oled vangis, kas soovid end 25 raha eest vabaks osta?");
                    System.out.println("Kui soovid end vabaks osta sisesta: 'jah'");
                    Scanner kasOstadVabaks = new Scanner(System.in);
                    kasMängijaOstabVabaks = kasOstadVabaks.nextLine();
                }

                //Täringuveeretus
                int esimeneTäring = veeretus();
                int teineTäring = veeretus();
                if (!kasMängijaOstabVabaks.equals("jah")) {
                    System.out.println(mängijad[i].getNimi() + " veeretas: " + esimeneTäring + " ja " + teineTäring + ". ");
                }
                //Kontrollitakse järjestikust duublit - kui 3 tükki, siis läheb mängija vangi ja käik jääb vahele
                mängijad[i].korrigeeriDuubel(esimeneTäring, teineTäring, mängulaud);

                //Kui mängija läks nüüd vangi, siis jääb ta käik vahele
                if (!mängijad[i].isVangis()) {
                    System.out.println("Mängija liigub edasi " + (esimeneTäring + teineTäring) + " sammu.");
                    mängijad[i].setAsukoht(mängijad[i].getAsukoht() + esimeneTäring + teineTäring, mängulaud);
                    System.out.println(mängijad[i].getNimi() + " on nüüd ruudul " + mängulaud.get(mängijad[i].getAsukoht()).getNimi() + ".");
                }

                //Rendi maksmine
                int asukoht = mängijad[i].getAsukoht();
                if (mängulaud.get(asukoht).getOmanik() != null && mängulaud.get(asukoht).isKrunt() &&
                        !mängulaud.get(asukoht).getOmanik().isVangis() && !mängulaud.get(asukoht).getOmanik().equals(mängijad[i])) {
                    Mängija krundiOmanik = mängulaud.get(asukoht).getOmanik();
                    // Kontrollib kas mängijal on piisavalt raha
                    if (mängijad[i].getRaha() >= mängulaud.get(asukoht).getRent()) {
                        mängijad[i].setRaha(mängijad[i].getRaha() - mängulaud.get(asukoht).getRent());
                        krundiOmanik.setRaha(krundiOmanik.getRaha() + mängulaud.get(asukoht).getRent());
                        System.out.println("Selle krundi omanik on " + krundiOmanik.getNimi() + ". Maksad talle " +
                                mängulaud.get(asukoht).getRent() + " raha renti.");
                    } else {
                        System.out.println(mängijad[i].getNimi() + ", sul pole rendi maksmiseks piisavalt raha!");
                        mängKäib = mängijad[i].onPankrotis();
                        break;
                    }

                    //Krundi ostmine
                } else if (mängulaud.get(asukoht).getOmanik() == null && mängulaud.get(asukoht).isKrunt() &&
                        mängijad[i].getRaha() >= (mängulaud.get(asukoht).getHind())) {
                    System.out.println(mängijad[i].getNimi() + ", kas soovid osta krunti " + mängulaud.get(asukoht).getNimi() + " hinnaga " + mängulaud.get(asukoht).getHind() + " raha?");
                    System.out.println("Sul on " + mängijad[i].getRaha() + " raha.");
                    System.out.println("Kui soovid krunti osta sisesta: 'jah'");
                    Scanner kasTahadOsta = new Scanner(System.in);
                    String mängijaOtsus = kasTahadOsta.nextLine();
                    if (mängijaOtsus.equals("jah")) {
                        mängulaud.get(asukoht).setOstetud(true);
                        mängulaud.get(asukoht).setOmanik(mängijad[i]);
                        mängijad[i].setRaha(mängijad[i].getRaha() - mängulaud.get(asukoht).getHind());
                        System.out.println("Mängijal " + mängijad[i].getNimi() + " on alles " + mängijad[i].getRaha() + " raha.");
                    }
                    //Loosiruudule sattumine
                } else if (mängulaud.get(asukoht).getNimi().equals("Loos")) {
                    int loosiRaha = (int) (Math.random() * (201) - 100);
                    if (loosiRaha == 0) {
                        System.out.println(mängijad[i].getNimi() + ", kahjuks ei võitnud sa loosist midagi.");
                    } else {
                        mängijad[i].setRaha(mängijad[i].getRaha() + loosiRaha);
                        if (loosiRaha > 0) {
                            System.out.println(mängijad[i].getNimi() + ", võitsid loosiga " + loosiRaha + " raha. Sul on nüüd " + mängijad[i].getRaha() + " raha.");
                        } else {
                            if ((mängijad[i].getRaha()) >= 0) {
                                System.out.println(mängijad[i].getNimi() + ", kaotasid loosiga " + (loosiRaha * (-1)) + " raha. Sul on nüüd " + mängijad[i].getRaha() + " raha.");
                            } else {
                                System.out.println(mängijad[i].getNimi() + " kaotas loosiga " + (loosiRaha * (-1)) + " raha.");
                                mängKäib = mängijad[i].onPankrotis();
                                break;
                            }
                        }
                    }

                    // Tulumaksuruudule sattumine
                } else if (mängulaud.get(asukoht).getNimi().equals("Tulumaks")) {
                    mängijad[i].setRaha(mängijad[i].getRaha() - 20);
                    System.out.println(mängijad[i].getNimi() + ", pead maksma 20 raha tulumaksu.");
                    if (mängijad[i].getRaha() < 0) {
                        mängKäib = mängijad[i].onPankrotis();
                        break;
                    } else {
                        System.out.println("Sul on nüüd " + mängijad[i].getRaha() + " raha.");
                    }
                }


                //Kui mängija on vangis, siis vaatab, kas mängija saab end vabaks osta või veeretab duubli
                if (mängijad[i].isVangis()) {
                    if (kasMängijaOstabVabaks.equals("jah")) {
                        if (mängijad[i].getRaha() >= 25) {
                            mängijad[i].setRaha(mängijad[i].getRaha() - 25);
                            mängijad[i].setVangis(false);
                            System.out.println("Vabanesid vangist. Sul on alles " + mängijad[i].getRaha() + " raha.");
                        } else {
                            System.out.println("Sul pole piisavalt raha, et end vabaks osta.");
                            mängijad[i].veeretaVangistVabaks(mängulaud);
                        }
                    } else {
                        mängijad[i].veeretaVangistVabaks(mängulaud);
                    }
                }

                System.out.println("Mängija " + mängijad[i].getNimi() + " käik on läbi. Tal on alles " + mängijad[i].getRaha() + " raha. Vajuta enter, et anda käik üle.");
                Scanner kasEdasi = new Scanner(System.in);
                String otsus = kasEdasi.nextLine();
                System.out.println("---------------------------------------------------------------");
            }
        }
    }
}
