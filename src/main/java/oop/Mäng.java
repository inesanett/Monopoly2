package oop;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Mäng extends Application {

    private int mängijateArv;
    private int algRaha;
    private List<Mängija> mängijatelist;
    private Color[] värvilist;
    private List<Koordinaadid> ruuduKoordinaat;
    private Group juur = new Group();
    private int kelleKord;
    private ArrayList<Mänguruut> mängulaud;

    public Mäng() {
        this.mängijateArv = 0;
        this.algRaha = 150;
        this.mängijatelist = new ArrayList<>();
        this.värvilist = new Color[]{Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW};
        this.kelleKord = mängijateArv - 1;
        this.ruuduKoordinaat = mänguruutudeKoordinaadid();
        this.mängulaud = Mängulaud.mängulauaLoomine();
    }

    private List<Koordinaadid> mänguruutudeKoordinaadid() {
        List<Koordinaadid> koordinaatidelist = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Koordinaadid koordinaat = new Koordinaadid(14 + 100 * i, 20);
            koordinaatidelist.add(koordinaat);
        }
        for (int i = 1; i < 4; i++) {
            Koordinaadid koordinaat = new Koordinaadid(814, 20 + 100 * i);
            koordinaatidelist.add(koordinaat);
        }
        for (int i = 0; i < 9; i++) {
            Koordinaadid koordinaat = new Koordinaadid(814 - 100 * i, 420);
            koordinaatidelist.add(koordinaat);
        }
        for (int i = 1; i < 4; i++) {
            Koordinaadid koordinaat = new Koordinaadid(14, 420 - 100 * i);
            koordinaatidelist.add(koordinaat);
        }
        return koordinaatidelist;
    }

    //Määrab mängijate arvu, kui sisend on ebasobiv, siis viskab erindi
    public void setMängijateArv(int mängijateArv) throws EbasobivMängijateArvErind {
        if (mängijateArv < 0) {
            throw new EbasobivMängijateArvErind("Mängijate arv ei saa olla negatiivne! ");
        } else if (mängijateArv > 4) {
            throw new EbasobivMängijateArvErind("Mängijate arv ei tohi olla üle nelja!");
        } else if (mängijateArv == 0) {
            throw new EbasobivMängijateArvErind("Nulli mängijaga ei saa mängu mängida!");
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
        if (algRaha > 0) {
            this.algRaha = algRaha;
        } else
            throw new EbasobivAlgRahaErind("Stardiraha peab olema positiivne!");
    }

    //Veeretamise meetod
    private static int veeretus() {
        return (int) Math.round(Math.random() * 5 + 1);
    }

    //Lõputingimuse kontrollimine
    private void lõpuTingimus(Mängija mängija, String põhjus) {
        if (mängija.getRaha() < 0) {
            Rectangle rectangle = new Rectangle(900, 500);
            juur.getChildren().add(rectangle);
            Label kaotaja = new Label("Mäng sai läbi! " + mängija.getNimi() + " kaotas!");
            Label põhjuselabel = new Label(põhjus);
            põhjuselabel.setLayoutX(270);
            põhjuselabel.setLayoutY(300);
            põhjuselabel.setTextFill(Color.ANTIQUEWHITE);
            põhjuselabel.setFont(Font.font("Cambria", 30));
            kaotaja.setTextFill(Color.ANTIQUEWHITE);
            kaotaja.setFont(Font.font("Cambria", 30));
            kaotaja.setLayoutY(230);
            kaotaja.setLayoutX(250);
            juur.getChildren().addAll(kaotaja, põhjuselabel);

            Label lõputekst = new Label("Mängu salvestamiseks vajuta kastis \"ENTER\"");
            lõputekst.setTextFill(Color.LIGHTBLUE);
            lõputekst.setLayoutX(270);
            lõputekst.setLayoutY(100);
            lõputekst.setFont(Font.font("Cambria", 17));
            juur.getChildren().add(lõputekst);

            TextField tekst = new TextField();
            tekst.setText("SALVESTA!");
            tekst.setLayoutX(360);
            tekst.setLayoutY(150);

            tekst.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    tekst.setText("SALVESTATUD!");
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("Tulemused.txt", true))) {
                        bw.write("Mäng toimus: " + LocalDateTime.now() + "\n");
                        bw.write("Kaotaja: " + mängija.getNimi() + "\n");
                        mängijatelist.remove(mängija);
                        bw.write("Võitja(d): \n");
                        for (Mängija m : mängijatelist) {
                            bw.write(m.getNimi() + "\n");
                        }
                        bw.write("-----------------------------------------------------------------\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            juur.getChildren().add(tekst);
        }
    }

    //Nuppude loomine ja stardiruudule panemine
    private void loomeNupud(int mängijate_arv) {
        for (int i = 0; i < mängijatelist.size(); i++) {
            mängijatelist.get(i).getNupp().setCenterX(ruuduKoordinaat.get(0).getX() + 24 * i);
            mängijatelist.get(i).getNupp().setCenterY(ruuduKoordinaat.get(0).getY());
            mängijatelist.get(i).getNupp().setOpacity(0.75);
            juur.getChildren().add(mängijatelist.get(i).getNupp());
        }
    }

    //Mängijate nimede sisestamine
    private void avaAken(int mängijate_arv, int i) {
        if (i == mängijate_arv) {
            loomeNupud(mängijate_arv);
            return;
        }

        Stage mängijaNimiAken = new Stage();
        mängijaNimiAken.setTitle("Mängija nime sisestamine");
        Label mängijaNimeKüsimus = new Label("Sisestage " + (i + 1) + ". mängija nimi: ");
        TextField mängijaNimiTekst = new TextField();
        mängijaNimiTekst.setAlignment(Pos.CENTER_RIGHT);
        mängijaNimiTekst.setText("");
        Button okNupp3 = new Button("OK");

        okNupp3.setOnMouseClicked(event3 -> {
            String nimi = mängijaNimiTekst.getText();
            mängijaNimiAken.hide();
            Mängija mängija = new Mängija(nimi, getAlgRaha(), 0, false, 0, värvilist[i]);
            mängijatelist.add(mängija);
            avaAken(mängijate_arv, i + 1);
        });

        FlowPane pane = new FlowPane(10, 10);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(okNupp3);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(mängijaNimeKüsimus, mängijaNimiTekst, pane);

        Scene stseen2 = new Scene(vBox);
        mängijaNimiAken.setScene(stseen2);
        mängijaNimiAken.show();
        mängijaNimiAken.setResizable(false);
    }

    //Loob mängulaua ruudu
    private Rectangle teeKast(Color värv, double tuhmus) {
        Rectangle kast = new Rectangle(100, 100);
        kast.setFill(värv);
        kast.setOpacity(tuhmus);
        return kast;
    }

    //Järgnevates meetodites lisatakse lauale ruudu nimi, hind, rent ja omanik
    private void ruutudenimed() {
        for (int i = 0; i < ruuduKoordinaat.size(); i++) {
            Label label = new Label(mängulaud.get(i).getNimi());
            label.setLayoutX(ruuduKoordinaat.get(i).getX() - 10);
            label.setLayoutY(ruuduKoordinaat.get(i).getY() + 60);
            label.setTextFill(Color.BLACK);
            juur.getChildren().add(label);
        }
    }

    private void ruutudehinnad() {
        for (int i = 0; i < ruuduKoordinaat.size(); i++) {
            if (!(i == 0 || i == 4 || i == 8 || i == 12 || i == 16 || i == 20)) {
                Label label = new Label("Hind: " + mängulaud.get(i).getHind());
                label.setLayoutX(ruuduKoordinaat.get(i).getX() - 10);
                label.setLayoutY(ruuduKoordinaat.get(i).getY() + 25);
                label.setTextFill(Color.BLACK);
                juur.getChildren().add(label);
            }
        }
    }

    private void ruutuderendid() {
        for (int i = 0; i < ruuduKoordinaat.size(); i++) {
            if (!(i == 0 || i == 4 || i == 8 || i == 12 || i == 16 || i == 20)) {
                Label label = new Label("Rent: " + mängulaud.get(i).getRent());
                label.setLayoutX(ruuduKoordinaat.get(i).getX() - 10);
                label.setLayoutY(ruuduKoordinaat.get(i).getY() + 10);
                label.setTextFill(Color.BLACK);
                juur.getChildren().add(label);
            }
        }
    }

    private void ruutudeOmanikud() {
        for (int i = 0; i < ruuduKoordinaat.size(); i++) {
            if (mängulaud.get(i).getOmanik() != null) {
                Label label = new Label("Omanik: " + mängulaud.get(i).getOmanik().getNimi());
                label.setLayoutX(ruuduKoordinaat.get(i).getX() - 10);
                label.setLayoutY(ruuduKoordinaat.get(i).getY() + 45);
                label.setTextFill(Color.BLACK);
                juur.getChildren().add(label);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage peaLava) {
        Canvas lõuend = new Canvas(900, 500);
        GraphicsContext gc = lõuend.getGraphicsContext2D();
        gc.fillRect(100, 100, 700, 300);
        BorderPane borderPane = new BorderPane(lõuend);

        //Loome mänguruudud osade kaupa
        VBox vasakServ = new VBox();
        VBox paremServ = new VBox();
        HBox alumineServ = new HBox();
        HBox ülemineServ = new HBox();

        List<Rectangle> ülemised = new ArrayList<>();
        ülemised.add(teeKast(Color.WHITE, 1));
        for (int i = 0; i < 3; i++) {
            ülemised.add(teeKast(Color.LIGHTBLUE, 1 - 0.2 * i));
        }
        ülemised.add(teeKast(Color.GRAY, 1));
        for (int i = 0; i < 3; i++) {
            ülemised.add(teeKast(Color.PURPLE, 1 - 0.2 * i));
        }
        ülemised.add(teeKast(Color.LIGHTGRAY, 1));
        ülemineServ.getChildren().addAll(ülemised);
        borderPane.setTop(ülemineServ);

        List<Rectangle> parempoolsed = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            parempoolsed.add(teeKast(Color.YELLOWGREEN, 1 - 0.2 * i));
        }
        paremServ.getChildren().addAll(parempoolsed);
        borderPane.setRight(paremServ);

        List<Rectangle> alumised = new ArrayList<>();
        alumised.add(teeKast(Color.LIGHTGRAY, 1));
        for (int i = 0; i < 3; i++) {
            alumised.add(teeKast(Color.MAGENTA, 1 - 0.2 * i));
        }
        alumised.add(teeKast(Color.GRAY, 1));
        for (int i = 0; i < 3; i++) {
            alumised.add(teeKast(Color.ORANGE, 1 - 0.2 * i));
        }
        alumised.add(teeKast(Color.WHITE, 1));
        alumineServ.getChildren().addAll(alumised);
        borderPane.setBottom(alumineServ);

        List<Rectangle> vasakpoolsed = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            vasakpoolsed.add(teeKast(Color.LIMEGREEN, 1 - 0.2 * i));
        }
        vasakServ.getChildren().addAll(vasakpoolsed);
        borderPane.setLeft(vasakServ);

        juur.getChildren().add(borderPane);
        juur.getChildren().add(lõuend);
        ruutudenimed();
        ruutudehinnad();
        ruutuderendid();
        ruutudeOmanikud();

        //Küsib mängijate arvu
        Stage mituMängijat = new Stage();
        mituMängijat.setTitle("Mängijate arvu sisetamine");
        Label mituMängijatKüsimus = new Label("Sisestage mängijate arv (maksimaalne mängijate arv on 4)");
        TextField mängijateArvuTekst = new TextField();
        mängijateArvuTekst.setAlignment(Pos.CENTER_RIGHT);
        mängijateArvuTekst.setText("");
        Button okNupp = new Button("OK");

        okNupp.setOnMouseClicked(event1 -> {
            try {
                this.setMängijateArv(Integer.parseInt(mängijateArvuTekst.getText()));
                mituMängijat.hide();
                int mängijate_arv = this.getMängijateArv();

                Stage stardiRaha = new Stage();
                stardiRaha.setTitle("Stardiraha sisestamine");
                Label stardiRahaKüsimus = new Label("Sisestage stardiraha (positiivne täisarv)");
                TextField stardiRahaTekst = new TextField();
                stardiRahaTekst.setAlignment(Pos.CENTER_RIGHT);
                stardiRahaTekst.setText("");

                Button okNupp2 = new Button("OK");
                okNupp2.setOnMouseClicked(event2 -> {
                    try {
                        this.setAlgRaha(Integer.parseInt(stardiRahaTekst.getText()));
                        stardiRaha.hide();
                        avaAken(mängijate_arv, 0);

                        Button veeretuseNupp = new Button("Veereta");
                        Button ostaNupp = new Button("Ostan krundi");
                        Button eiOstaNupp = new Button("Ei soovi krunti osta");

                        Label täring1väärtus = new Label("Täring 1: ");
                        Label täring2väärtus = new Label("Täring 2: ");

                        Label mängija1raha = new Label("Mängija 1 raha: " + algRaha);
                        Label mängija2raha = new Label("Mängija 2 raha: " + algRaha);
                        Label mängija3raha = new Label("Mängija 3 raha: " + algRaha);
                        Label mängija4raha = new Label("Mängija 4 raha: " + algRaha);

                        Label reeglid = new Label("Käigu alustamiseks vajuta nuppu \"Veereta\". Kui " +
                                "krunti on võimalik osta, siis saab seda \n teha vajutades nupule \"Ostan krundi\". " +
                                "Tegevusruutude info kajastub mänguekraanil. \n   Rendi ja tulumaksu maksmine käib " +
                                "automaatselt ja stardiruudust möödudes saab \n      mängija " +
                                "automaatselt kümnendiku stardirahast. Loosiruudul on võimalik võita " +
                                "\n             või kaotada kuni pool stardirahast. " +
                                "Vangist pääsed veeretades duubli.");
                        reeglid.setTextFill(Color.ANTIQUEWHITE);
                        reeglid.setFont(Font.font("Cambria", 15));
                        reeglid.setLayoutY(120);
                        reeglid.setLayoutX(230);
                        juur.getChildren().add(reeglid);

                        Label infoSilt = new Label("");
                        infoSilt.setLayoutY(330);
                        infoSilt.setLayoutX(130);
                        infoSilt.setTextFill(Color.ANTIQUEWHITE);
                        infoSilt.setFont(Font.font("Cambria", 17));
                        juur.getChildren().add(infoSilt);

                        List<Label> mängijateRahadeLabel = new ArrayList<>();
                        mängijateRahadeLabel.add(mängija1raha);
                        mängijateRahadeLabel.add(mängija2raha);
                        mängijateRahadeLabel.add(mängija3raha);
                        mängijateRahadeLabel.add(mängija4raha);

                        mängija1raha.setLayoutX(130);
                        mängija2raha.setLayoutX(300);
                        mängija3raha.setLayoutX(470);
                        mängija4raha.setLayoutX(640);
                        mängija1raha.setLayoutY(370);
                        mängija2raha.setLayoutY(370);
                        mängija3raha.setLayoutY(370);
                        mängija4raha.setLayoutY(370);

                        mängija1raha.setTextFill(värvilist[0]);
                        mängija2raha.setTextFill(värvilist[1]);
                        mängija3raha.setTextFill(värvilist[2]);
                        mängija4raha.setTextFill(värvilist[3]);

                        mängija1raha.setFont(Font.font("Cambria", 15));
                        mängija2raha.setFont(Font.font("Cambria", 15));
                        mängija3raha.setFont(Font.font("Cambria", 15));
                        mängija4raha.setFont(Font.font("Cambria", 15));

                        täring1väärtus.setLayoutX(120);
                        täring1väärtus.setLayoutY(120);
                        täring2väärtus.setLayoutX(120);
                        täring2väärtus.setLayoutY(160);
                        täring1väärtus.setTextFill(Color.WHITESMOKE);
                        täring2väärtus.setTextFill(Color.WHITESMOKE);
                        täring1väärtus.setFont(Font.font("Cambria", 20));
                        täring2väärtus.setFont(Font.font("Cambria", 20));

                        veeretuseNupp.setLayoutX(430);
                        veeretuseNupp.setLayoutY(240);
                        ostaNupp.setLayoutX(330);
                        ostaNupp.setLayoutY(240);
                        eiOstaNupp.setLayoutX(510);
                        eiOstaNupp.setLayoutY(240);

                        juur.getChildren().add(veeretuseNupp);
                        juur.getChildren().add(ostaNupp);
                        juur.getChildren().add(eiOstaNupp);
                        juur.getChildren().addAll(täring1väärtus, täring2väärtus);
                        juur.getChildren().addAll(mängijateRahadeLabel);

                        //X-d vanglisolijate peale
                        Label vangis1 = new Label("X");
                        vangis1.setTextFill(Color.BLACK);
                        vangis1.setLayoutX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() - 5);
                        vangis1.setLayoutY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY() - 10);
                        vangis1.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
                        vangis1.setVisible(false);

                        Label vangis2 = new Label("X");
                        vangis2.setTextFill(Color.BLACK);
                        vangis2.setLayoutX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() + 24 - 5);
                        vangis2.setLayoutY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY() - 10);
                        vangis2.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
                        vangis2.setVisible(false);

                        Label vangis3 = new Label("X");
                        vangis3.setTextFill(Color.BLACK);
                        vangis3.setLayoutX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() + (24 * 2) - 5);
                        vangis3.setLayoutY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY() - 10);
                        vangis3.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
                        vangis3.setVisible(false);

                        Label vangis4 = new Label("X");
                        vangis4.setTextFill(Color.BLACK);
                        vangis4.setLayoutX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() + (24 * 3) - 5);
                        vangis4.setLayoutY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY() - 10);
                        vangis4.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
                        vangis4.setVisible(false);

                        List<Label> xList = new ArrayList<>();
                        xList.add(vangis1);
                        xList.add(vangis2);
                        xList.add(vangis3);
                        xList.add(vangis4);
                        juur.getChildren().addAll(xList);

                        ostaNupp.setDisable(true);
                        eiOstaNupp.setDisable(true);

                        veeretuseNupp.setOnMouseClicked(eventVeeretus -> {
                            infoSilt.setText("");

                            //Kontrollib kelle kord parajasti pooleli on
                            kelleKord += 1;
                            if (kelleKord == mängijate_arv)
                                kelleKord = 0;

                            Mängija praeguseKäiguMängija = mängijatelist.get(kelleKord);

                            //Kontrollib kas mängija on pankrotis
                            int esimeneTäring = veeretus();
                            int teineTäring = veeretus();

                            täring1väärtus.setText("Täring 1: " + esimeneTäring);
                            täring2väärtus.setText("Täring 2: " + teineTäring);

                            //Mängija rahasumma kuvamine ekraanil
                            int abi = 0;
                            for (int i = 0; i < 4; i++) {
                                if (abi < mängijate_arv) {
                                    mängijateRahadeLabel.get(abi).setText(mängijatelist.get(abi).getNimi() + " raha: " + mängijatelist.get(abi).getRaha());
                                    abi++;
                                } else
                                    mängijateRahadeLabel.get(i).setText("");
                            }

                            int uusAsukohaIndeks = praeguseKäiguMängija.getAsukoht() + esimeneTäring + teineTäring;

                            if (!praeguseKäiguMängija.isVangis()) {
                                //Kui ei ole vangis
                                boolean kasKolmasDuubel = praeguseKäiguMängija.korrigeeriDuubel(esimeneTäring, teineTäring, mängulaud);
                                //Kontrollib, kas lähed duubli tõttu vangi
                                if (kasKolmasDuubel) {
                                    infoSilt.setText(praeguseKäiguMängija.getNimi() + ": Veeretasid 3. duubli! Lähed Vangi!");
                                    praeguseKäiguMängija.getNupp().setCenterX(ruuduKoordinaat.get(praeguseKäiguMängija.getAsukoht()).getX() + 24 * kelleKord);
                                    praeguseKäiguMängija.getNupp().setCenterY(ruuduKoordinaat.get(praeguseKäiguMängija.getAsukoht()).getY());
                                } else {
                                    //Kui ei lähe duubli tõttu vangi
                                    //Kui indeks on üle 23, siis korrigeerib ja lisab raha
                                    if (uusAsukohaIndeks > 23) {
                                        uusAsukohaIndeks = uusAsukohaIndeks - 24;
                                        praeguseKäiguMängija.setRaha(praeguseKäiguMängija.getRaha() + (getAlgRaha() / 10));
                                        mängijateRahadeLabel.get(kelleKord).setText(mängijatelist.get(kelleKord).getNimi() + " raha: " + mängijatelist.get(kelleKord).getRaha());
                                    }

                                    //Kõnnib järgmisele ruudule
                                    praeguseKäiguMängija.setAsukoht(uusAsukohaIndeks);
                                    praeguseKäiguMängija.getNupp().setCenterX(ruuduKoordinaat.get(uusAsukohaIndeks).getX() + 24 * kelleKord);
                                    praeguseKäiguMängija.getNupp().setCenterY(ruuduKoordinaat.get(uusAsukohaIndeks).getY());

                                    int asukoht = praeguseKäiguMängija.getAsukoht();
                                    Mänguruut ruut = mängulaud.get(asukoht);
                                    boolean onOstetud = ruut.isOstetud();
                                    int praeguseMängijaraha = praeguseKäiguMängija.getRaha();
                                    int mänguruuduHind = ruut.getHind();
                                    int mänguruuduRent = ruut.getRent();

                                    //Kontrollib, kas ruut on krunt (ostetav v mitte)
                                    if (!ruut.isKrunt()) {
                                        //Kui ruut ei ole krunt, siis on tegu tegevusruuduga
                                        if (uusAsukohaIndeks == Mängulaud.leiaVangiMinek(mängulaud)) {
                                            //Kui satud vangiminekuruudule
                                            infoSilt.setText(praeguseKäiguMängija.getNimi() + ": Sattusid \"Mine vangi\" ruudule! Lähed Vangi!");
                                            praeguseKäiguMängija.mineVangi(mängulaud);
                                            praeguseKäiguMängija.getNupp().setCenterX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() + 24 * kelleKord);
                                            praeguseKäiguMängija.getNupp().setCenterY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY());
                                        } else if (mängulaud.get(uusAsukohaIndeks).getNimi().equals("Loos")) {
                                            //Kui satud loosiruudule
                                            int loosiRaha = (int) (Math.random() * (2 * getAlgRaha() + 1) - getAlgRaha());
                                            if (loosiRaha < 0)
                                                infoSilt.setText(praeguseKäiguMängija.getNimi() + ": Sattusid loosiruudule! Kaotasid " + (-loosiRaha) + " raha!");
                                            else
                                                infoSilt.setText(praeguseKäiguMängija.getNimi() + ": Sattusid loosiruudule! Võitsid " + loosiRaha + " raha!");
                                            praeguseKäiguMängija.setRaha(praeguseMängijaraha + loosiRaha);
                                            lõpuTingimus(praeguseKäiguMängija, "Läksid loosiga pankrotti!");
                                        } else if (mängulaud.get(uusAsukohaIndeks).getNimi().equals("Tulumaks")) {
                                            //Kui satud tulumaksuruudule
                                            int tulumaks = getAlgRaha() / 10;
                                            infoSilt.setText(praeguseKäiguMängija.getNimi() + ": Sattusid ruudule \"Tulumaks\"! Maksad " + tulumaks + " raha!");
                                            praeguseKäiguMängija.setRaha(praeguseMängijaraha - tulumaks);
                                            lõpuTingimus(praeguseKäiguMängija, "Tulumaks oli üle jõu!");
                                        } else {
                                            //Kui satud vangla- või stardiruudule
                                            ostaNupp.setDisable(true);
                                            eiOstaNupp.setDisable(true);
                                            veeretuseNupp.setDisable(false);
                                        }
                                    } else {
                                        //Kui satud krundile
                                        if (onOstetud) {
                                            //Kui see krunt on juba ostetud
                                            boolean omanikOnVangis = ruut.getOmanik().isVangis();
                                            int ruuduOmanikuRaha = ruut.getOmanik().getRaha();
                                            if (!omanikOnVangis) {
                                                //Kui omanik ei ole vangis, maksad talle renti
                                                praeguseKäiguMängija.setRaha(praeguseMängijaraha - mänguruuduRent);
                                                ruut.getOmanik().setRaha(ruuduOmanikuRaha + mänguruuduRent);
                                                if (ruut.getOmanik() != praeguseKäiguMängija)
                                                    infoSilt.setText(praeguseKäiguMängija.getNimi() + ": Selle krundi omanik on " + ruut.getOmanik().getNimi() + ". Maksad talle " + mänguruuduRent + " raha!");
                                                lõpuTingimus(praeguseKäiguMängija, "Rendi maksmine võttis raha ära!");
                                            } else {
                                                //Kui omanik on vangis, siis maksma ei pea, aga osta ei saa
                                                ostaNupp.setDisable(true);
                                                eiOstaNupp.setDisable(true);
                                                veeretuseNupp.setDisable(false);
                                            }
                                        } else {
                                            //Kui krunt ei ole ostetud
                                            if (ruut.isKrunt() && praeguseMängijaraha >= mänguruuduHind) {
                                                //Kui sa saad seda krunti osta, siis pakub ostuvõimalust
                                                veeretuseNupp.setDisable(true);
                                                ostaNupp.setDisable(false);
                                                eiOstaNupp.setDisable(false);
                                            } else {
                                                //Kui ei saa siis veereta edasi
                                                ostaNupp.setDisable(true);
                                                eiOstaNupp.setDisable(true);
                                                veeretuseNupp.setDisable(false);
                                            }
                                        }
                                    }
                                }
                            } else {
                                //Kui sa olid vangis, siis kui sa veeretasid duubli - enam ei ole vangis
                                if (esimeneTäring == teineTäring) {
                                    infoSilt.setText(praeguseKäiguMängija.getNimi() + ": Veeretasid duubli! Pääsed vanglast!");
                                    xList.get(kelleKord).setVisible(false);
                                    xList.get(kelleKord).toFront();
                                    praeguseKäiguMängija.setVangis(false);
                                }
                            }

                            if (praeguseKäiguMängija.isVangis()) {
                                xList.get(kelleKord).setVisible(true);
                            }
                        });

                        ostaNupp.setOnMouseClicked(eventOstmine -> {
                            Mängija praeguseKäiguMängija = mängijatelist.get(kelleKord);
                            mängulaud.get(praeguseKäiguMängija.getAsukoht()).setOstetud(true);
                            mängulaud.get(praeguseKäiguMängija.getAsukoht()).setOmanik(praeguseKäiguMängija);
                            praeguseKäiguMängija.setRaha(praeguseKäiguMängija.getRaha() - mängulaud.get(praeguseKäiguMängija.getAsukoht()).getHind());
                            ruutudeOmanikud();
                            ostaNupp.setDisable(true);
                            eiOstaNupp.setDisable(true);
                            veeretuseNupp.setDisable(false);
                        });

                        eiOstaNupp.setOnMouseClicked(eventMitteOstmine -> {
                            veeretuseNupp.setDisable(false);
                            ostaNupp.setDisable(true);
                            eiOstaNupp.setDisable(true);
                        });

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
                        ebaSobivRahaAken.setResizable(false);
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
                stardiRaha.setResizable(false);
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
                ebaSobivMängijateArvAken.setResizable(false);
                ebaSobivMängijateArvAken.show();
            }
        });

        FlowPane pane = new FlowPane(10, 10);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(okNupp);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(mituMängijatKüsimus, mängijateArvuTekst, pane);

        Scene stseen2 = new Scene(vBox);
        mituMängijat.setScene(stseen2);
        mituMängijat.setResizable(false);

        peaLava.setTitle("Monopoly (mängu saad sulgeda ristist)");
        Scene stseen = new Scene(juur);
        peaLava.setScene(stseen);
        peaLava.show();
        peaLava.setResizable(false);
        mituMängijat.show();
    }
}
