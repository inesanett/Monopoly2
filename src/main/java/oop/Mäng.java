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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

//import static javafx.application.Application.launch;

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

    //Nuppude loomine ja stardiruudule panemine
    private void loomeNupud(int mängijate_arv) {
        for (int i = 0; i < mängijatelist.size(); i++) {
            mängijatelist.get(i).getNupp().setCenterX(ruuduKoordinaat.get(0).getX() + 24 * i);
            mängijatelist.get(i).getNupp().setCenterY(ruuduKoordinaat.get(0).getY());
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
            System.out.println(mängijatelist.toString());
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

    //Loob mängulaua ruud
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

    public void start(Stage peaLava) throws Exception {

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
                Label stardiRahaKüsimus = new Label("Sisestage stardiraha (positiivne täisarv)");
                TextField stardiRahaTekst = new TextField();
                stardiRahaTekst.setAlignment(Pos.CENTER_RIGHT);
                stardiRahaTekst.setText("");

                Button okNupp2 = new Button("OK");
                okNupp2.setOnMouseClicked(event2 -> {
                    try {
                        this.setAlgRaha(Integer.parseInt(stardiRahaTekst.getText()));
                        stardiRaha.hide();
                        //List<String> nimed = new ArrayList<>();
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

                        List<Label> mängijateRahadeLabel = new ArrayList<>();
                        mängijateRahadeLabel.add(mängija1raha);
                        mängijateRahadeLabel.add(mängija2raha);
                        mängijateRahadeLabel.add(mängija3raha);
                        mängijateRahadeLabel.add(mängija4raha);

                        mängija1raha.setLayoutX(150);
                        mängija2raha.setLayoutX(320);
                        mängija3raha.setLayoutX(490);
                        mängija4raha.setLayoutX(660);

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

                        veeretuseNupp.setOnMouseClicked(eventVeeretus -> {
                            //Kontrollib kelle kord parajasti pooleli on
                            kelleKord += 1;
                            if (kelleKord == mängijate_arv)
                                kelleKord = 0;

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

                            Mängija praeguseKäiguMängija = mängijatelist.get(kelleKord);
                            int uusAsukohaIndeks = praeguseKäiguMängija.getAsukoht() + esimeneTäring + teineTäring;

                            System.out.println(praeguseKäiguMängija.isVangis());
                            if (!praeguseKäiguMängija.isVangis()) {
                                praeguseKäiguMängija.korrigeeriDuubel(esimeneTäring, teineTäring, mängulaud);
                                if (praeguseKäiguMängija.korrigeeriDuubel(esimeneTäring, teineTäring, mängulaud)) {
                                    praeguseKäiguMängija.getNupp().setCenterX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() + 24 * kelleKord);
                                    praeguseKäiguMängija.getNupp().setCenterY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY());
                                }

                                if (uusAsukohaIndeks > 23) {
                                    uusAsukohaIndeks = uusAsukohaIndeks - 24;
                                    praeguseKäiguMängija.setRaha(praeguseKäiguMängija.getRaha() + ((int) getAlgRaha() / 10));
                                }

                                praeguseKäiguMängija.setAsukoht(uusAsukohaIndeks);
                                praeguseKäiguMängija.getNupp().setCenterX(ruuduKoordinaat.get(uusAsukohaIndeks).getX() + 24 * kelleKord);
                                praeguseKäiguMängija.getNupp().setCenterY(ruuduKoordinaat.get(uusAsukohaIndeks).getY());

                                int asukoht = praeguseKäiguMängija.getAsukoht();
                                Mänguruut ruut = mängulaud.get(asukoht);
                                boolean onOstetud = ruut.isOstetud();
                                int praeguseMängijaraha = praeguseKäiguMängija.getRaha();
                                int mänguruuduHind = ruut.getHind();
                                int mänguruuduRent = ruut.getRent();

                                if (!ruut.isKrunt()) {
                                    if (uusAsukohaIndeks == Mängulaud.leiaVangiMinek(mängulaud)) {
                                        praeguseKäiguMängija.mineVangi(mängulaud);
                                        praeguseKäiguMängija.getNupp().setCenterX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() + 24 * kelleKord);
                                        praeguseKäiguMängija.getNupp().setCenterY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY());
                                    } else if (mängulaud.get(uusAsukohaIndeks).getNimi().equals("Loos")) {
                                        int loosiRaha = (int) (Math.random() * (2 * getAlgRaha() + 1) - getAlgRaha());
                                        praeguseKäiguMängija.setRaha(praeguseMängijaraha + loosiRaha);
                                    } else if (mängulaud.get(uusAsukohaIndeks).getNimi().equals("Tulumaks")) {
                                        int tulumaks = getAlgRaha() / 10;
                                        praeguseKäiguMängija.setRaha(praeguseMängijaraha - tulumaks);
                                    } else {
                                        ostaNupp.setDisable(true);
                                        eiOstaNupp.setDisable(true);
                                        veeretuseNupp.setDisable(false);
                                    }
                                } else {
                                    if (onOstetud) {
                                        boolean omanikOnVangis = ruut.getOmanik().isVangis();
                                        int ruuduOmanikuRaha = ruut.getOmanik().getRaha();
                                        if (!omanikOnVangis) {
                                            praeguseKäiguMängija.setRaha(praeguseMängijaraha - mänguruuduRent);
                                            ruut.getOmanik().setRaha(ruuduOmanikuRaha + mänguruuduRent);
                                        } else {
                                            ostaNupp.setDisable(true);
                                            eiOstaNupp.setDisable(true);
                                            veeretuseNupp.setDisable(false);
                                        }
                                    } else {
                                        if (ruut.isKrunt() && praeguseMängijaraha >= mänguruuduHind) {
                                            veeretuseNupp.setDisable(true);
                                            ostaNupp.setDisable(false);
                                            eiOstaNupp.setDisable(false);
                                        } else {
                                            ostaNupp.setDisable(true);
                                            eiOstaNupp.setDisable(true);
                                            veeretuseNupp.setDisable(false);
                                        }
                                    }
                                }
                            } else {
                                //Topib Punase X nupu peale
                                if (esimeneTäring == teineTäring)
                                    praeguseKäiguMängija.setVangis(false);
                            }

/*
                            System.out.println(mängulaud.get(praeguseKäiguMängija.getAsukoht()).isOstetud());
                            System.out.println(!(mängulaud.get(praeguseKäiguMängija.getAsukoht()).isKrunt()));
                            System.out.println(praeguseKäiguMängija.getRaha());
                            System.out.println(mängulaud.get(praeguseKäiguMängija.getAsukoht()).getHind());
                            System.out.println(praeguseKäiguMängija.getRaha()<mängulaud.get(praeguseKäiguMängija.getAsukoht()).getHind());
                            System.out.println();



 *//*
                            int asukoht = praeguseKäiguMängija.getAsukoht();
                            Mänguruut ruut = mängulaud.get(asukoht);
                            boolean onOstetud = ruut.isOstetud();
                            int praeguseMängijaraha = praeguseKäiguMängija.getRaha();
                            int mänguruuduHind = ruut.getHind();
                            int mänguruuduRent = ruut.getRent();

                            if (!ruut.isKrunt()) {
                                if (uusAsukohaIndeks == Mängulaud.leiaVangiMinek(mängulaud)) {
                                    System.out.println("Jah");
                                    praeguseKäiguMängija.mineVangi(mängulaud);
                                    praeguseKäiguMängija.getNupp().setCenterX(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getX() + 24 * kelleKord);
                                    praeguseKäiguMängija.getNupp().setCenterY(ruuduKoordinaat.get(Mängulaud.leiaVangla(mängulaud)).getY());
                                }
                            } else {
                                if (onOstetud) {
                                    boolean omanikOnVangis = ruut.getOmanik().isVangis();
                                    int ruuduOmanikuRaha = ruut.getOmanik().getRaha();
                                    if (!omanikOnVangis) {
                                        praeguseKäiguMängija.setRaha(praeguseMängijaraha - mänguruuduRent);
                                        ruut.getOmanik().setRaha(ruuduOmanikuRaha + mänguruuduRent);
                                    } else {
                                        ostaNupp.setDisable(true);
                                        eiOstaNupp.setDisable(true);
                                        veeretuseNupp.setDisable(false);
                                    }
                                } else {
                                    if (ruut.isKrunt() && praeguseMängijaraha >= mänguruuduHind) {
                                        veeretuseNupp.setDisable(true);
                                        ostaNupp.setDisable(false);
                                        eiOstaNupp.setDisable(false);
                                    } else {
                                        ostaNupp.setDisable(true);
                                        eiOstaNupp.setDisable(true);
                                        veeretuseNupp.setDisable(false);
                                    }
                                }
                                }
                                */


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



                        /*
                        //siit ei tööta
                        Stage alustaMäng = new Stage();
                        Button alustaMängNupp = new Button("Alusta mänguga!");

                        alustaMängNupp.setOnMouseClicked(eventAlustaMäng -> {
                            boolean mängKäib = true;
                            alustaMäng.hide();
                            while (mängKäib) {
                                Button veeretuseNupp = new Button("Veereta");
                                borderPane.setCenter(veeretuseNupp);

                                veeretuseNupp.setOnMouseClicked(eventVeeretus -> {
                                    int esimeneTäring = veeretus();
                                    int teineTäring = veeretus();
                                });
                            }

                        });

                        FlowPane pane = new FlowPane(10, 10);
                        pane.setAlignment(Pos.CENTER);
                        pane.getChildren().add(alustaMängNupp);

                        Scene stseen2 = new Scene(pane);
                        alustaMäng.setScene(stseen2);
                        alustaMäng.show();

                         */


                        //Siit lõpeb lisatud (vist toimiv värk)

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

        peaLava.setTitle("Monopoly");
        Scene stseen = new Scene(juur);
        peaLava.setScene(stseen);
        peaLava.show();
        peaLava.setResizable(false);
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
