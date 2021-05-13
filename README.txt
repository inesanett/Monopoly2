Rühmatöö nr.2 ("Monopoly") kirjeldus:

1. Autorid: Joosep Lember ja Ines Anett Nigol

2. Programm kujutab endast lihtsustatud Monopoli mängu. Võrreldes tavamänguga puuduvad või erinevad mõned meetodid.
Näiteks puudub mängus majade ja hotellide süsteem ning ühiskonna kaardid, mängulaud on lühem. Loosikaartide asemel
on lihtne rahaloos, kust saab võita või kaotada raha kindlates piirides. Mängus on olemas kruntide ostmise, rendi
maksmise ja vanglasüsteem. Samuti on olemas tulumaksu ruut ning igale uuele ringile minnes saab mängija raha juurde.

Programmi saab käivitada gradle'i kaudu. Mängu alguses küsitakse, mitu mängijat mängida soovib (maksimaalselt 4) ning palutakse sisestada mängijate nimed. 
Samuti küsitakse, kui suure rahasummaga soovitakse mängu alustada. Käigu alguses veeretatakse täringut. Juhul kui 
mängija ei ole vangis saab ta sattuda erinevatele mänguruutudele. Kui tegu on vaba krundiga ning mängijal on piisavalt
raha, et krunti osta, antakse talle selleks võimalus. Loosiruutude, vanglasse sattumise, tulumaksu ja rendi maksmise 
süsteem on automatiseeritud. Mäng lõppeb, kui keegi mängijatest jääb pankrotti. Lõpuekraanil kuvatakse mängu kaotaja
ning kaotamise põhjus. Samuti antakse võimalus mängu võitjad ja kaotajad koos mängimise kellaajaga faili salvestada.

3. Programmis on 5 erinevat klassi: "Mäng", "Mängija", "Mängulaud", "Mänguruut" ja "Koordinaadid". 
Klass "Mäng" on peaklass, mis tuleb käivitada, et mäng tööle hakkaks. Klassis küsitakse mängijate arvu, nende nimesid
algraha ning seejärel algab mäng, kus iga mängija jaoks korratakse samu tegevusi (veeretatakse täringut, vaadatakse, 
kas mängija on vangis, vaadatakse, kas mängijal on piisavalt raha, et krunte osta, renti maksta jne) ning seejärel 
reageerib programm vastavalt vajadusele. Mängu käiku kuvatakse javafx-i abil ekraanil.

Klass "Mängija" on mängijate loomiseks. Seal klassis on mängijale omased isendiväljad ning mängu ja mängija suhtluseks vajalikud
meetodid. Meetodid on: get- ja set-meetodid, onPankrotis, korrigeeriDuubel, mineVangi, veeretaVangistVabaks. Meetodite täpne töö 
eesmärk on kommenteeritud programmis.

Klass "Mängulaud" loob mängulaua lugedes sisse etteantud tekstifaili "mängulaud.txt", kuhu on kirja pandud kõik mänguruudud
ja nende omadused. Mängulaua klassis on veel meetodid, mis leiavad vanglaruudu ja vangimineku ruudu.

Klass "Mänguruut" on mänguruutude loomiseks vajalik klass, kus on isendiväljadena kõik mänguruutude omadused. Selles klassis on
veel vajalikud get- ja set-meetodid.

Klassi "Koordinaadid" abil antakse igale mänguruudule vajalikud koordinaadid, et nende põhjal nuppe liigutada.

4. ja 5. punkt:
Tööjaotus oli taaskord üsna vaba. Võtsime mängu loomiseks aluseks enda tekstipõhise Monopoly (1. rühmatöö) ning seadsime selle 
üles eelseadistatud javafx-i projekti. Enamik asju tegime koos, kuna graafika elemente tuli mängu järjest lisada ning nõnda
tundus mõlemale kergem. 

6. Graafika lisamine oli keeruline, kuna enam ei saanud mängu töötamiseks kasutada lihtsalt while(true) tsüklit ning mängus 
tuleb pidevalt paljusid asju kontrollida (näiteks, kas krunt on ostetud, kas midagi vajutati, kas keegi sattus pankrotti jne).
Samuti tuli vaadata, et graafilised elemendid õigel hetkel ekraanile kuvatakse.

7. Oleme lõpptulemusega rahul, kuna suutsime tekstipõhisest Monopolist edasi arendada töötava graafilise versiooni. Oleme teadlikud, 
et tegu pole ilusa java stiiliga, kuid kuna lõpp läks kiireks oli põhiline eesmärk saada mäng üldse tööle. 

8. Selleks, et näha, kas graafilised elemendid ilmuvad õigel ajal ekraanile, pidi mängu mitu korda läbi mängima. Kolme järjestikuse 
duubli veeretamise tõttu vangiminekut kontrollisime luues täringu, kus olid vaid numbrid 1 ja 2. Vigade leidmiseks väljastasime
ekraanile kontrolllauseid, et teada saada, kas programm jõuab õigetel hetkedel soovitud kohtadesse. 