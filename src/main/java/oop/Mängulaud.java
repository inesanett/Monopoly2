package oop;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Mängulaud {

    //Mängulauda loov meetod
    public static ArrayList<Mänguruut> mängulauaLoomine() throws Exception{
        File mängulauaFail = new File("mängulaud.txt");
        ArrayList<Mänguruut> mängulaud = new ArrayList<>();
        try (Scanner sc = new Scanner(mängulauaFail, "UTF-8")) {
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                String[] info = rida.split(",");
                Mänguruut mänguruut = new Mänguruut(Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2]),
                        Integer.parseInt(info[3]), info[4], Boolean.parseBoolean(info[5]), Boolean.parseBoolean(info[6]));
                mängulaud.add(mänguruut);
            }
        }
        return mängulaud;
    }
    
    //Meetod, mis leiab konkreetse mängulaua korral üles vangi saatmise ruudu indeksi
    public static int leiaVangiMinek(ArrayList<Mänguruut> mängulaud) {
        for (int i = 0; i < mängulaud.size() ; i++) {
            if (mängulaud.get(i).getNimi().equals("Vangiminek"))
                    return i;
        }
        return 0;
    }

    //Meetod, mis leiab konkreetse mängulaua korral üles vangla indeksi
    public static int leiaVangla(ArrayList<Mänguruut> mängulaud) {
        for (int i = 0; i < mängulaud.size() ; i++) {
            if (mängulaud.get(i).getNimi().equals("Vangla"))
                return i;
        }
        return 0;
    }
}
