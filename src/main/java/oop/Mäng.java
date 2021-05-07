package oop;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Mäng extends Application {

    int mängijateArv;
    int algRaha;

    public Mäng() {
        this.mängijateArv = 0;
        this.algRaha = 150;
    }

    //Määrab mängijate arvu, kui sisend on ebasobiv, siis viskab erindi
    public void setMängijateArv(int mängijateArv) throws EbasobivMängijateArvErind {
        if (mängijateArv < 0) {
            throw new EbasobivMängijateArvErind("Mängijate arv ei saa olla negatiivne! ");
        } else if (mängijateArv > 4) {
            throw new EbasobivMängijateArvErind("Mängijate arv ei tohi olla üle nelja!");
        } else {
            this.mängijateArv = mängijateArv;
        }
    }

    public int getMängijateArv() {
        return mängijateArv;
    }

    public int getAlgRaha() {
        return algRaha;
    }

    //Määrab algraha, kui sisend on ebasobiv, siis viskab erindi
    public void setAlgRaha(int algRaha) throws EbasobivAlgRahaErind {
        if (algRaha > 0)
            this.algRaha = algRaha;
        else
            throw new EbasobivAlgRahaErind("Stardiraha peab olema positiivne!");
    }

    //Veeretamise meetod
    private static int veeretus() {
        return (int) Math.round(Math.random() * 5 + 1);
    }

    //Loob mängijate massiivi nimede massiivi põhjal
    private Mängija[] looMängijad(String[] nimed) {
        Mängija[] mängijad = new Mängija[nimed.length];
        for (int i = 0; i < nimed.length; i++) {
            Mängija mängija = new Mängija(nimed[i], algRaha, 0, false, 0);
            mängijad[i] = mängija;
        }
        return mängijad;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage peaLava) throws Exception {
        Mäng mäng = new Mäng();

        Group juur = new Group();
        Canvas lõuend = new Canvas(900,500);
        GraphicsContext gc = lõuend.getGraphicsContext2D();
        gc.fillRect(100,100,700,300);
        BorderPane borderPane = new BorderPane(lõuend);

        VBox vasakServ = new VBox();
        VBox paremServ = new VBox();
        HBox alumineServ = new HBox();
        HBox ülemineServ = new HBox();

        Rectangle rect0 = new Rectangle(100,100);
        rect0.setFill(Color.WHITESMOKE);
        Rectangle rect1 = new Rectangle(100,100);
        rect1.setFill(Color.BLUE);
        rect1.setOpacity(0.6);
        Rectangle rect2 = new Rectangle(100,100);
        rect2.setFill(Color.BLUE);
        rect2.setOpacity(0.8);
        Rectangle rect3 = new Rectangle(100,100);
        rect3.setFill(Color.BLUE);
        Rectangle rect4 = new Rectangle(100,100);
        rect4.setFill(Color.GRAY);
        Rectangle rect5 = new Rectangle(100,100);
        rect5.setFill(Color.MAGENTA);
        rect5.setOpacity(0.6);
        Rectangle rect6 = new Rectangle(100,100);
        rect6.setFill(Color.MAGENTA);
        rect6.setOpacity(0.8);
        Rectangle rect7 = new Rectangle(100,100);
        rect7.setFill(Color.MAGENTA);
        Rectangle rect8 = new Rectangle(100,100);
        rect8.setFill(Color.DARKGRAY);
        alumineServ.getChildren().addAll(rect8,rect7,rect6,rect5,rect4,rect3,rect2,rect1,rect0);
        borderPane.setBottom(alumineServ);

        Rectangle rect9 = new Rectangle(100,100);
        rect9.setFill(Color.ORANGE);
        rect9.setOpacity(0.6);
        Rectangle rect10 = new Rectangle(100,100);
        rect10.setFill(Color.ORANGE);
        rect10.setOpacity(0.8);
        Rectangle rect11 = new Rectangle(100,100);
        rect11.setFill(Color.ORANGE);
        vasakServ.getChildren().addAll(rect11,rect10,rect9);
        borderPane.setLeft(vasakServ);

        Rectangle rect12 = new Rectangle(100,100);
        rect12.setFill(Color.WHITESMOKE);
        Rectangle rect13 = new Rectangle(100,100);
        rect13.setFill(Color.RED);
        rect13.setOpacity(0.6);
        Rectangle rect14 = new Rectangle(100,100);
        rect14.setFill(Color.RED);
        rect14.setOpacity(0.8);
        Rectangle rect15 = new Rectangle(100,100);
        rect15.setFill(Color.RED);
        Rectangle rect16 = new Rectangle(100,100);
        rect16.setFill(Color.GRAY);
        Rectangle rect17 = new Rectangle(100,100);
        rect17.setFill(Color.YELLOW);
        rect17.setOpacity(0.6);
        Rectangle rect18 = new Rectangle(100,100);
        rect18.setFill(Color.YELLOW);
        rect18.setOpacity(0.8);
        Rectangle rect19 = new Rectangle(100,100);
        rect19.setFill(Color.YELLOW);
        Rectangle rect20 = new Rectangle(100,100);
        rect20.setFill(Color.DARKGRAY);
        ülemineServ.getChildren().addAll(rect12,rect13,rect14,rect15,rect16,rect17,rect18,rect19,rect20);
        borderPane.setTop(ülemineServ);

        Rectangle rect21 = new Rectangle(100,100);
        rect21.setFill(Color.GREEN);
        rect21.setOpacity(0.6);
        Rectangle rect22 = new Rectangle(100,100);
        rect22.setFill(Color.GREEN);
        rect22.setOpacity(0.8);
        Rectangle rect23 = new Rectangle(100,100);
        rect23.setFill(Color.GREEN);
        paremServ.getChildren().addAll(rect21,rect22,rect23);
        borderPane.setRight(paremServ);

        juur.getChildren().add(borderPane);
        juur.getChildren().add(lõuend);

        //Küsib mängijate arvu
        Stage mituMängijat = new Stage();
        //BorderPane juur2 = new BorderPane();
        Label mituMängijatKüsimus = new Label("Sisestage mängijate arv (maksimaalne mängijate arv on 4)");
        TextField mängjateArvuTekst = new TextField();
        mängjateArvuTekst.setAlignment(Pos.CENTER_RIGHT);
        mängjateArvuTekst.setText("");
        //juur2.setTop(mängjateArvuTekst);

        Button okNupp = new Button("OK");

        okNupp.setOnMouseClicked(event1 -> {
            try {
                mäng.setMängijateArv(Integer.parseInt(mängjateArvuTekst.getText()));
                mituMängijat.hide();

                Stage stardiRaha = new Stage();
                Label stardiRahaKüsimus = new Label("Sisestage stardiraha (positiivne täisarv)");
                TextField stardiRahaTekst = new TextField();
                stardiRahaTekst.setAlignment(Pos.CENTER_RIGHT);
                stardiRahaTekst.setText("");

                Button okNupp2 = new Button("OK");

                okNupp2.setOnMouseClicked(event2 -> {
                    try {
                        mäng.setAlgRaha(Integer.parseInt(stardiRahaTekst.getText()));
                        stardiRaha.hide();
                    } catch (EbasobivAlgRahaErind e) {
                        Stage ebaSobivRahaAken = new Stage();
                        Label erindiSõnum = new Label(e.getMessage());
                        Button sulge = new Button("Sulge aken");

                        sulge.setOnMouseClicked(event3 -> {
                            ebaSobivRahaAken.hide();
                        });

                        FlowPane pane = new FlowPane(10, 10);
                        pane.setAlignment(Pos.CENTER);
                        pane.getChildren().add(sulge);

                        VBox vBox = new VBox(10);
                        vBox.setAlignment(Pos.CENTER);
                        vBox.getChildren().addAll(erindiSõnum, pane);

                        Scene stseen2 = new Scene(vBox);
                        ebaSobivRahaAken.setScene(stseen2);
                        ebaSobivRahaAken.show();
                    }
                });

                FlowPane pane = new FlowPane(10, 10);
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().add(okNupp2);

                VBox vBox = new VBox(10);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(stardiRahaKüsimus, stardiRahaTekst, pane);

                Scene stseen2 = new Scene(vBox);
                stardiRaha.setScene(stseen2);
                stardiRaha.show();

            } catch (EbasobivMängijateArvErind e) {
               Stage ebaSobivMängijateArvAken = new Stage();
               Label erindiSõnum = new Label(e.getMessage());
               Button sulge = new Button("Sulge aken");

               sulge.setOnMouseClicked(event2 -> {
                   ebaSobivMängijateArvAken.hide();
               });

               FlowPane pane = new FlowPane(10, 10);
               pane.setAlignment(Pos.CENTER);
               pane.getChildren().add(sulge);

               VBox vBox = new VBox(10);
               vBox.setAlignment(Pos.CENTER);
               vBox.getChildren().addAll(erindiSõnum, pane);

               Scene stseen2 = new Scene(vBox);
               ebaSobivMängijateArvAken.setScene(stseen2);
               ebaSobivMängijateArvAken.show();
            }
        });

        FlowPane pane = new FlowPane(10, 10);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(okNupp);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(mituMängijatKüsimus, mängjateArvuTekst, pane);

        Scene stseen2 = new Scene(vBox);
        mituMängijat.setScene(stseen2);

        peaLava.setTitle("Monopoly");
        Scene stseen = new Scene(juur);
        peaLava.setScene(stseen);
        peaLava.show();
        mituMängijat.show();

        /*
        //Loome mängulaua
        ArrayList<Mänguruut> mängulaud = Mängulaud.mängulauaLoomine();
        Scanner scan = new Scanner(System.in); //klaviatuurilt sisestamiseks

        Mäng mäng = new Mäng();
        //Küsime kasutajalt mitu mängijat mängib nii kaua kuni sisestatakse sobiv arv
        while (true) {
            System.out.println("Sisestage mängijate arv (maksimaalne mängijate arv on 4): ");
            int mängijate_arv = scan.nextInt();
            try {
                mäng.setMängijateArv(mängijate_arv);
                break;
            } catch (EbasobivMängijateArvErind e) {
                System.out.println(e.getMessage());
            }
        }

        //Nimetasin mängijate arvu niisuguseks nagu enne, et ei peaks hilisemas koodis igal pool muutma
        int mängijate_arv = mäng.getMängijateArv();

        //Küsime kasutajalt, mis on stardiraha. Jätkame küsimist, kuni sisestatakse sobiv summa
        while (true) {
            System.out.println("Sisestage stardiraha (positiivne täisarv) ");
            int algRaha = scan.nextInt();
            try {
                mäng.setAlgRaha(algRaha);
                break;
            } catch (EbasobivAlgRahaErind e) {
                System.out.println(e.getMessage());
            }
        }

        //Loome massiivi, kuhu salvestame mängijate nimed, ja küsime kasutajalt nimed. Seda massiivi kasutame mängijate loomiseks
        String[] nimed = new String[mäng.getMängijateArv()];
        for (int i = 0; i < mängijate_arv + 1; i++) {
            String nimi = scan.nextLine();
            if (i > 0) {
                nimed[i-1]=nimi;
            }
            if (i < mängijate_arv)
                System.out.println("Sisestage " + (i + 1) + ". mängija nimi: ");
        }

        //Nimetasin siin ka mängijate massiivi ümber niisuguseks nagu enne oli
        Mängija[] mängijad = mäng.looMängijad(nimed);
        */
        //Vana kood, ei hakanud ära kustutama igaks juhuks
        /*
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
            int mängijate_arv;
            Mängija[] mängijad;
            Mäng mäng;
            while (true) {
                System.out.println("Sisestage mängijate arv (maksimaalne mängijate arv on 4): ");
                mängijate_arv = scan.nextInt();
                try {
                    mäng = new Mäng(mängijate_arv);
                    mängijad = new Mängija[mängijate_arv];
                    for (int i = 0; i < mängijate_arv + 1; i++) {
                        String nimi = scan.nextLine();
                        if (i > 0) {
                            Mängija mängija = new Mängija(nimi, algRaha, 0, false, 0);
                            mängijad[i - 1] = mängija;
                        }
                        if (i < mängijate_arv)
                            System.out.println("Sisestage " + (i + 1) + ". mängija nimi: ");
                    }
                    break;
                } catch (EbasobivMängijateArvErind e) {
                    System.out.println(e.getMessage());
                }
            }
         */
    /*
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
         */
    }

}
