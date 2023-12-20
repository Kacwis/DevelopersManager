package Utilites;
import Pojazdy.*;
import Obiekty.*;
import Wyjatki.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InterfejsUzytkownika {

    Scanner scan = new Scanner(System.in);
    Osoba uzytkownik;
    String przerywnik = "----------------------------------------------";
    static boolean dzialaInterfejs;
    OpcjaMenu wyjscie = new OpcjaMenu("WYJSCIE", () ->
            zatrzymanieProgramu()
            );
    String cofnij = "COFNIJ";

    public InterfejsUzytkownika(){
        dzialaInterfejs = true;
        wybieranieUzytkownika();
        System.out.println("WYBRANO: " + uzytkownik);
        while(dzialaInterfejs){
            menuWyboru();
        }

    }

    public void zatrzymanieProgramu(){
        dzialaInterfejs = false;
        Zegar.INSTANCE.zegarStop();
        scan.close();
        System.out.println("DO ZOBACZENIA");
        System.out.println("zamykanie programu...");
    }

    public void wyswietlOpcje(ArrayList<OpcjaMenu> opcjeDoWyswietlenia){
        for (int i = 0; i <opcjeDoWyswietlenia.size() ; i++) {
            System.out.println((i + 1) +". " + opcjeDoWyswietlenia.get(i).napis);
        }
        System.out.println("PODAJ NUMER");
        int indeksWyboruAkcji = wczytajLiczbe() - 1;
        if(indeksWyboruAkcji >= 0 && indeksWyboruAkcji < opcjeDoWyswietlenia.size()){
            opcjeDoWyswietlenia.get(indeksWyboruAkcji).akcja.wykonaj();
        }
        else{
            System.out.println("PODANO ZLY NUMER");
            wyswietlOpcje(opcjeDoWyswietlenia);
        }
    }

    public void wybieranieUzytkownika(){
        List<Osoba> listaOsob = MenadzerStanu.listaOsob;
        ArrayList<OpcjaMenu> listaOpcjiWybraniaUzytkownika = new ArrayList<>();
        System.out.println("WYBIERZ UZYTKOWNIKA\n" + przerywnik);
        for(Osoba os : listaOsob){
            listaOpcjiWybraniaUzytkownika.add(new OpcjaMenu(os.toString(), () ->
                    uzytkownik = os
                    ));
        }
        listaOpcjiWybraniaUzytkownika.add(wyjscie);
        wyswietlOpcje(listaOpcjiWybraniaUzytkownika);
    }

    public void menuWyboru(){
        ArrayList<OpcjaMenu> listaOpcjiMenuGlowne = new ArrayList<>();
        listaOpcjiMenuGlowne.add(new OpcjaMenu("WYSWIETL SWOJE DANE I POSIADANE POMIESZCZENIA", () ->
                swojeDaneIPosiadanePomieszczenia())
        );
        listaOpcjiMenuGlowne.add(new OpcjaMenu("WYSWIETL WSZYSTKIE POMIESZCZENIA", () ->
                wyswietlWszystkiePomieszczenia()
                ));
        listaOpcjiMenuGlowne.add(new OpcjaMenu("ZAPISZ STAN OSIEDLA DO PLIKU", () ->
                zapisanieStanuOsiedlaDoPliku()
                ));
        listaOpcjiMenuGlowne.add(wyjscie);
        wyswietlOpcje(listaOpcjiMenuGlowne);
    }

    public void wyswietlWszystkiePomieszczenia(){
        ArrayList<OpcjaMenu> listaWyboruJakiePomieszczenia = new ArrayList<>();
        listaWyboruJakiePomieszczenia.add(new OpcjaMenu("WYSWIETL WYNAJMOWANE ZADLUZONE POMIESZCZENIA", () ->
                zadluzonePomieszczenia()
                ));
        listaWyboruJakiePomieszczenia.add(new OpcjaMenu("WYSWIETL DOSTEPNE POMIESZCZENIA", () ->
                mieszkaniaCzyMiejsca()
                ));
        listaWyboruJakiePomieszczenia.add(new OpcjaMenu("WYSWIETL ZAJETE POMIESZCZENIA", () ->
                zajetePomieszczenia()
                ));
        listaWyboruJakiePomieszczenia.add(new OpcjaMenu(cofnij , () ->
                menuWyboru()
                ));
        listaWyboruJakiePomieszczenia.add(wyjscie);
        wyswietlOpcje(listaWyboruJakiePomieszczenia);
    }

    public void zajetePomieszczenia(){
        ArrayList<Osoba> listaNajemcow = MenadzerStanu.stworzListeNajemcow();
        ArrayList<OpcjaMenu> listaOpcjiZajetePomieszczenia = new ArrayList<>();
        for(Osoba najemca : listaNajemcow){
            listaOpcjiZajetePomieszczenia.add(new OpcjaMenu(najemca.toString(), () ->
                    wyswietlListePomieszczen(najemca)
                    ));
        }
        listaOpcjiZajetePomieszczenia.add(new OpcjaMenu(cofnij, () ->
                wyswietlWszystkiePomieszczenia()
                ));
        listaOpcjiZajetePomieszczenia.add(wyjscie);
        wyswietlOpcje(listaOpcjiZajetePomieszczenia);
    }

    public void wyswietlListePomieszczen(Osoba osoba){
        ArrayList<Umowa> listaUmowPomieszczen = (ArrayList)osoba.getListaUmow();
        ArrayList<OpcjaMenu> listaOpcjiListyPomieszczen = new ArrayList<>();
        for(Umowa umowa : listaUmowPomieszczen){
            listaOpcjiListyPomieszczen.add(new OpcjaMenu(umowa.toString(), () ->
                    wyswietlZawartoscPomieszczenia(umowa)
                    ));
        }
        listaOpcjiListyPomieszczen.add(new OpcjaMenu(cofnij, () ->
                zajetePomieszczenia()
                ));
        listaOpcjiListyPomieszczen.add(wyjscie);
        wyswietlOpcje(listaOpcjiListyPomieszczen);
    }

    public void wyswietlZawartoscPomieszczenia(Umowa umowa){
        if(umowa.getClass() == UmowaMieszkanie.class){
            System.out.println(umowa.toString());
            List<Osoba> listaDomotorow = ((UmowaMieszkanie) umowa).getListaDomotorow();
            int numerator = 1;
            for(Osoba os : listaDomotorow){
                System.out.println((numerator) + ". " + os.toString());
                numerator++;
            }
        }
        else{
            System.out.println(umowa.toString());
            ArrayList<Przedmiot> listaPrzedmiotow = (ArrayList)((UmowaMiejsceParkingowe) umowa).getListaPrzedmiotow().values();
            int numerator = 1;
            for(Przedmiot przedmiot : listaPrzedmiotow){
                System.out.println(numerator + ". " + przedmiot.toString());
                numerator++;
            }
        }
    }


    public void mieszkaniaCzyMiejsca(){
        ArrayList<OpcjaMenu> listaOpcjiMieszkanieCzyMiejsce = new ArrayList<>();
        listaOpcjiMieszkanieCzyMiejsce.add(new OpcjaMenu("WYSWIETL DOSTEPNE MIESZKANIA", () ->
                dostepneMieszkania()
                ));
        listaOpcjiMieszkanieCzyMiejsce.add(new OpcjaMenu("WYSWIETL DOSTEPNE MIEJSCA PARKINGOWE", () ->
                dostepneMiejscaParkingowe()
                ));
        listaOpcjiMieszkanieCzyMiejsce.add(new OpcjaMenu(cofnij, () ->
                wyswietlWszystkiePomieszczenia()
                ));
        listaOpcjiMieszkanieCzyMiejsce.add(wyjscie);
        wyswietlOpcje(listaOpcjiMieszkanieCzyMiejsce);
    }

    public void dostepneMiejscaParkingowe(){
        ArrayList<MiejsceParkingowe> listaDostepnychMiejscParkingowych = MenadzerStanu.stworzListeDostepnychMiejscParkingowych();
        ArrayList<OpcjaMenu> listaOpcjiDostepnychMiejscParkingowych = new ArrayList<>();
        for(MiejsceParkingowe miejsceParkingowe : listaDostepnychMiejscParkingowych){
            listaOpcjiDostepnychMiejscParkingowych.add(new OpcjaMenu(miejsceParkingowe.toString(), () ->
                wynajmijLubWypiszDane(miejsceParkingowe)
                ));
        }
        listaOpcjiDostepnychMiejscParkingowych.add(new OpcjaMenu(cofnij, () ->
                mieszkaniaCzyMiejsca()
                ));
        listaOpcjiDostepnychMiejscParkingowych.add(wyjscie);
        wyswietlOpcje(listaOpcjiDostepnychMiejscParkingowych);
    }

    public void dostepneMieszkania(){
        ArrayList<Mieszkanie> listaDostepnychMieszkania = MenadzerStanu.stworzListeDostepnychMieszkan();
        ArrayList<OpcjaMenu> listaOpcjiDostepnychMieszkan = new ArrayList<>();
        for(Mieszkanie mieszkanie : listaDostepnychMieszkania){
            listaOpcjiDostepnychMieszkan.add(new OpcjaMenu(mieszkanie.toString(), () ->
                    wynajmijLubWypiszDane(mieszkanie)
                    ));
        }
        listaOpcjiDostepnychMieszkan.add(new OpcjaMenu(cofnij, () ->
                mieszkaniaCzyMiejsca()
        ));
        listaOpcjiDostepnychMieszkan.add(wyjscie);
        wyswietlOpcje(listaOpcjiDostepnychMieszkan);
    }

    public void wynajmijLubWypiszDane(Pomieszczenie pomieszczenie){
        ArrayList<OpcjaMenu> wynajmijLubWypiszDane = new ArrayList<>();
        wynajmijLubWypiszDane.add(new OpcjaMenu("WYNAJMIJ POMIESZCZENIE", () ->
                wynajmijPomieszczenie(pomieszczenie)
                ));
        wynajmijLubWypiszDane.add(new OpcjaMenu("WYSWIETL INFORMACJE", () ->
                wyswietlDanePomieszczenia(pomieszczenie)
                ));
        wynajmijLubWypiszDane.add(new OpcjaMenu(cofnij, () ->
                mieszkaniaCzyMiejsca()
        ));
        wynajmijLubWypiszDane.add(wyjscie);
        wyswietlOpcje(wynajmijLubWypiszDane);
    }

    public void wynajmijPomieszczenie(Pomieszczenie pomieszczenie){
        System.out.println("PODAJ DLUGOSC NAJMU W MIESIACACH");
        int dlugoscNajmu = wczytajLiczbe();
        try {
            uzytkownik.dodajMieszkanie(pomieszczenie, dlugoscNajmu);
        }
        catch(TooMuchRoomsException e){
            System.out.println(e.err());
        }
        catch (ProblematicTenantException e){
            System.out.println(e.err());
        }
    }

    public void wyswietlDanePomieszczenia(Pomieszczenie pomieszczenie){
        System.out.println(pomieszczenie);
    }


    public void zadluzonePomieszczenia(){
        ArrayList<Umowa> listaZadluzonychPomieszczen = stworzListeZadluzonychUmow();
        ArrayList<OpcjaMenu> listaOpcjiZadluzonychPomieszczen = new ArrayList<>();
        if(listaZadluzonychPomieszczen.isEmpty()){
            System.out.println("NIE POSIADASZ ZADNYCH ZADLUZEN");
            return;
        }
        for(Umowa umowa : listaZadluzonychPomieszczen){
            listaOpcjiZadluzonychPomieszczen.add(new OpcjaMenu(umowa.toString(), () ->
                    odnowUmowe(umowa)
                    ));
        }
        System.out.println("WYBIERZ POMIESZCZENIE");
        listaOpcjiZadluzonychPomieszczen.add(new OpcjaMenu(cofnij, () ->
                wyswietlWszystkiePomieszczenia()
                ));
        listaOpcjiZadluzonychPomieszczen.add(wyjscie);
        wyswietlOpcje(listaOpcjiZadluzonychPomieszczen);
    }

    public void odnowUmowe(Umowa umowa){
        System.out.println("PODAJ DLUGOSC NAJMU W MIESIACACH");
        int dlugoscNajmu = wczytajLiczbe();
        try {
            uzytkownik.dodajMieszkanie(umowa.getPomieszczenie(), dlugoscNajmu);
        }
        catch(TooMuchRoomsException e){
            System.out.println(e.err());
        }
        catch (ProblematicTenantException e){
            System.out.println(e.err());
        }
    }

    public void swojeDaneIPosiadanePomieszczenia(){
        wyswietlOpcjeSwojeDaneIPomieszczenia();
        ArrayList<OpcjaMenu> listaOpcjiSwoichDanych = new ArrayList<>();
        System.out.println(przerywnik);
        System.out.println("WYBIERZ NUMER POMIESZCZENIA");
        for(Umowa umowa : uzytkownik.getListaUmow()){
            listaOpcjiSwoichDanych.add(new OpcjaMenu(umowa.toString(), () ->
                    mieszkanieCzyMiejsceParkingowe(umowa)
                    ));
        }
        listaOpcjiSwoichDanych.add(new OpcjaMenu(cofnij , () ->
                menuWyboru()
                ));
        listaOpcjiSwoichDanych.add(wyjscie);
        wyswietlOpcje(listaOpcjiSwoichDanych);

    }

    public void mieszkanieCzyMiejsceParkingowe(Umowa umowa){
        if(umowa.getClass() == UmowaMieszkanie.class){
            mieszkanieAkcje(umowa);
        }
        else{
            miejsceParkingoweAkcje(umowa);
        }
    }

    public void mieszkanieAkcje(Umowa umowa){
        ArrayList<OpcjaMenu> listaOpcjiMenuMieszkanie = new ArrayList<>();
        List <Osoba> listaLokatorow = ((UmowaMieszkanie)umowa).getListaDomotorow();
        System.out.println(przerywnik);
        for(Osoba lokator : listaLokatorow){
            System.out.println(lokator);
        }
        System.out.println(przerywnik);
        listaOpcjiMenuMieszkanie.add(new OpcjaMenu("DODAJ LOKATORA", () ->
                dodawanieLokatora((UmowaMieszkanie) umowa)
                ));
        listaOpcjiMenuMieszkanie.add(new OpcjaMenu("USUN LOKATORA",() ->
                usuwanieLokatora((UmowaMieszkanie) umowa)
                ));
        listaOpcjiMenuMieszkanie.add(new OpcjaMenu(cofnij, () ->
                swojeDaneIPosiadanePomieszczenia()
                ));
        listaOpcjiMenuMieszkanie.add(wyjscie);
        wyswietlOpcje(listaOpcjiMenuMieszkanie);
    }

    public void dodawanieLokatora(UmowaMieszkanie umowaMieszkanie){
        System.out.println(przerywnik + "\nIMIE  NAZWISKO  PESEL  ADRES i W NASTEPNEJ LINI DZIEN MIESIAC ROK");
        String informacje = "";
        int dzien = 0;
        int miesiac = 0;
        int rok = 0;
        scan.nextLine();
        informacje = scan.nextLine();
        try {
            dzien = scan.nextInt();
            miesiac = scan.nextInt();
            rok = scan.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("ZLY FORMAT");
            dodawanieLokatora(umowaMieszkanie);
        }
        if (metodaRozdzielajacaStringi(informacje).length != 4) {
            System.out.println("NIE PODANO POPRAWNEJ ILOSCI DANYCH");
            return;
        }
        String[] imieNazwiskoPesel = metodaRozdzielajacaStringi(informacje);
        umowaMieszkanie.zamelduj(new Osoba(imieNazwiskoPesel[0], imieNazwiskoPesel[1], imieNazwiskoPesel[2], imieNazwiskoPesel[3], dzien, miesiac, rok));
    }

    public void usuwanieLokatora(UmowaMieszkanie umowaMieszkanie){
        wypiszListeLokatorow(umowaMieszkanie);
        int indeksLokatoraDoUsunieca = wczytajLiczbe() - 1;
        if(indeksLokatoraDoUsunieca >= 0 && indeksLokatoraDoUsunieca < umowaMieszkanie.getListaDomotorow().size()){
            umowaMieszkanie.wymelduj(umowaMieszkanie.getListaDomotorow().get(indeksLokatoraDoUsunieca));
        }
        else{
            System.out.println("PODAJ NUMER JESZCZE RAZ");
            usuwanieLokatora(umowaMieszkanie);
        }
    }

    public void miejsceParkingoweAkcje(Umowa umowa){
        ArrayList<OpcjaMenu> listaOpcjiMiejsceParkingoweAkcje = new ArrayList<>();
        listaOpcjiMiejsceParkingoweAkcje.add(new OpcjaMenu("USUN PRZEDMIOT", () ->
                usuwaniePrzedmiotu((UmowaMiejsceParkingowe) umowa)
                ));
        listaOpcjiMiejsceParkingoweAkcje.add(new OpcjaMenu("DODAJ PRZEDMIOT", () ->
                dodawaniePrzedmiotuLubPojazdu((UmowaMiejsceParkingowe) umowa)
                ));
        listaOpcjiMiejsceParkingoweAkcje.add(new OpcjaMenu(cofnij, () ->
                swojeDaneIPosiadanePomieszczenia()
                ));
        wyswietlOpcje(listaOpcjiMiejsceParkingoweAkcje);
    }

    public void usuwaniePrzedmiotu(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        List<Przedmiot> lokalnaListaPrzedmiotow =  new ArrayList<>(umowaMiejsceParkingowe.getListaPrzedmiotow().values());
        ArrayList<OpcjaMenu> listaOpcjiUsuwaniePrzedmiotu = new ArrayList<>();
        for (int i = 0; i < lokalnaListaPrzedmiotow.size(); i++) {
            System.out.println((i+1) + lokalnaListaPrzedmiotow.get(i).toString());
        }
        System.out.println(przerywnik + "\nPODAJ NUMER PRZEDMIOTU KTORY CHCESZ USUNAC");
        for(Przedmiot przedmiot : lokalnaListaPrzedmiotow){
            listaOpcjiUsuwaniePrzedmiotu.add(new OpcjaMenu(przedmiot.toString(), () ->
                    umowaMiejsceParkingowe.usunPrzedmiot(przedmiot)
                    ));
        }
        listaOpcjiUsuwaniePrzedmiotu.add(new OpcjaMenu(cofnij, () ->
                miejsceParkingoweAkcje(umowaMiejsceParkingowe)
                ));
        listaOpcjiUsuwaniePrzedmiotu.add(wyjscie);
        wyswietlOpcje(listaOpcjiUsuwaniePrzedmiotu);
    }

    public void dodawaniePrzedmiotuLubPojazdu(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        ArrayList<OpcjaMenu> pojazdCzyPrzedmiot = new ArrayList<>();
        pojazdCzyPrzedmiot.add(new OpcjaMenu("DODAJ PRZEDMIOT", () ->
                dodawaniePrzedmiotu(umowaMiejsceParkingowe)
                ));
        pojazdCzyPrzedmiot.add(new OpcjaMenu("DODAJ POJAZD", () ->
                dodawaniePojazdu(umowaMiejsceParkingowe)
                ));
        pojazdCzyPrzedmiot.add(new OpcjaMenu(cofnij, () ->
                miejsceParkingoweAkcje(umowaMiejsceParkingowe)
                ));
        pojazdCzyPrzedmiot.add(wyjscie);
        wyswietlOpcje(pojazdCzyPrzedmiot);
    }

    public void dodawaniePrzedmiotu(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        ArrayList<OpcjaMenu> listaWyboruSposobuDodania = new ArrayList<>();
        System.out.println("PODAJ SPOSOB OKRESLENIA OBJETOSCI PRZEDMIOTU");
        listaWyboruSposobuDodania.add(new OpcjaMenu("Z WYMIARAMI",() ->
                dodawaniePrzedmiotuZWymiarami(umowaMiejsceParkingowe)
                ));
        listaWyboruSposobuDodania.add(new OpcjaMenu("Z OBJETOSCIA", () ->
                dodawaniePrzedmiotuZObjetoscia(umowaMiejsceParkingowe)
                ));
        listaWyboruSposobuDodania.add(new OpcjaMenu(cofnij, () ->
                dodawaniePrzedmiotuLubPojazdu(umowaMiejsceParkingowe)
                ));
        listaWyboruSposobuDodania.add(wyjscie);
        wyswietlOpcje(listaWyboruSposobuDodania);
    }

    public void dodawaniePrzedmiotuZWymiarami(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        double x = 0;
        double y = 0;
        double z = 0;
        System.out.println("PODAJ ODPOWIEDNIO: SZEROKOSC, DLUGOSC, WYSOKOSC ORAZ NAZWE");
        try {
            x = scan.nextDouble();
            y = scan.nextDouble();
            z = scan.nextDouble();
        }
        catch (InputMismatchException e){
            System.out.println("ZLY FORMAT DANYCH");
            dodawaniePrzedmiotuZWymiarami(umowaMiejsceParkingowe);
        }
        String nazwa = scan.nextLine();
        try {
            uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new Przedmiot(x, y ,z ,nazwa){});
        }
        catch(TooManyThingsException e){
            System.out.println(e.err());
            return;
        }
    }

    public void dodawaniePrzedmiotuZObjetoscia(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        double objetosc = 0;
        System.out.println("PODAJ ODPOWIEDNIO: OBJETOSC ORAZ NAZWE");
        try {
            objetosc = scan.nextDouble();
        }
        catch(InputMismatchException e){
            System.out.println("ZLY FORMAT DANYCH");
            dodawaniePrzedmiotuZObjetoscia(umowaMiejsceParkingowe);
        }
        String nazwa = scan.nextLine();
        try {
            uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new Przedmiot(objetosc, nazwa));
        }
        catch(TooManyThingsException e){
            System.out.println(e.err());
            return;
        }
    }

    public void dodawaniePojazdu(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        ArrayList<OpcjaMenu> listaWyboruSposobuDodania = new ArrayList<>();
        System.out.println("PODAJ SPOSOB OKRESLENIA OBJETOSCI PRZEDMIOTU");
        listaWyboruSposobuDodania.add(new OpcjaMenu("Z WYMIARAMI",() ->
                dodawaniePojazduZWymiarami(umowaMiejsceParkingowe)
        ));
        listaWyboruSposobuDodania.add(new OpcjaMenu("Z OBJETOSCIA", () ->
                dodawaniePojazduZObjetoscia(umowaMiejsceParkingowe)
        ));
        listaWyboruSposobuDodania.add(new OpcjaMenu(cofnij, () ->
                dodawaniePrzedmiotuLubPojazdu(umowaMiejsceParkingowe)
                ));
        listaWyboruSposobuDodania.add(wyjscie);
        wyswietlOpcje(listaWyboruSposobuDodania);
    }

    public void dodawaniePojazduZWymiarami(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        ArrayList<OpcjaMenu> listaWyboruPojazdu = new ArrayList<>();
        listaWyboruPojazdu.add(new OpcjaMenu("SAMOCHOD MIEJSCKI", () ->
                dodanieSamochodMiejski(umowaMiejsceParkingowe, 1)
                ));
        listaWyboruPojazdu.add(new OpcjaMenu("SAMOCHOD TERENOWY", () ->
                dodanieSamochodTerenowy(umowaMiejsceParkingowe, 1)
                ));
        listaWyboruPojazdu.add(new OpcjaMenu("MOTOCYKL", () ->
                dodanieMotocykl(umowaMiejsceParkingowe, 1)
                ));
        listaWyboruPojazdu.add(new OpcjaMenu("LODZ", () ->
                dodanieLodz(umowaMiejsceParkingowe, 1)
                ));
        listaWyboruPojazdu.add(new OpcjaMenu("AMFIBIA", () ->
                dodanieAmfibi(umowaMiejsceParkingowe, 1)
                ));
        listaWyboruPojazdu.add(new OpcjaMenu(cofnij, () ->
                dodawaniePojazdu(umowaMiejsceParkingowe)
                ));
        listaWyboruPojazdu.add(wyjscie);
        wyswietlOpcje(listaWyboruPojazdu);
    }

    public void dodawaniePojazduZObjetoscia(UmowaMiejsceParkingowe umowaMiejsceParkingowe){
        ArrayList<OpcjaMenu> listaWyboruPojazdu = new ArrayList<>();
        listaWyboruPojazdu.add(new OpcjaMenu("SAMOCHOD MIEJSCKI", () ->
                dodanieSamochodMiejski(umowaMiejsceParkingowe, 2)
        ));
        listaWyboruPojazdu.add(new OpcjaMenu("SAMOCHOD TERENOWY", () ->
                dodanieSamochodTerenowy(umowaMiejsceParkingowe, 2)
        ));
        listaWyboruPojazdu.add(new OpcjaMenu("MOTOCYKL", () ->
                dodanieMotocykl(umowaMiejsceParkingowe, 2)
        ));
        listaWyboruPojazdu.add(new OpcjaMenu("LODZ", () ->
                dodanieLodz(umowaMiejsceParkingowe, 2)
        ));
        listaWyboruPojazdu.add(new OpcjaMenu("AMFIBIA", () ->
                dodanieAmfibi(umowaMiejsceParkingowe, 2)
        ));
        listaWyboruPojazdu.add(new OpcjaMenu(cofnij, () ->
                dodawaniePojazdu(umowaMiejsceParkingowe)
        ));
        listaWyboruPojazdu.add(wyjscie);
        wyswietlOpcje(listaWyboruPojazdu);
    }

    public void dodanieSamochodMiejski(UmowaMiejsceParkingowe umowaMiejsceParkingowe, int sposobDodania){
        String wszystkieInformacjeOSamochodzie = " POJEMNOSC SILNIKA I W NASTEPNEJ LINI\n"+
                "MARKA I TYP SILNIKA";
        double objetosc = 0;
        double x = 0;
        double y = 0;
        double z = 0;
        double pojemnoscSilnika = 0;
        scan.nextLine();
        if(sposobDodania == 1){
            System.out.println("PODAJ ODPOWIEDNIO PO SPACJI : SZEROKOSC, DLUGOSC , WYSOKOSC ," +wszystkieInformacjeOSamochodzie);                //double x, double y, double z, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, int ileDrzwi
            try {
                x = scan.nextDouble();
                y = scan.nextDouble();
                z = scan.nextDouble();
                pojemnoscSilnika = (double) scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLE PODANE DANE");
                dodanieSamochodMiejski(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calySamochod = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochod).length != 2){
                System.out.println("ZLE PODANE INFORMACJE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new SamochodMiejski(x, y, z, metodaRozdzielajacaStringi(calySamochod)[0], " SAMOCHOD ", metodaRozdzielajacaStringi(calySamochod)[1], pojemnoscSilnika, 3));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
        }
        else if(sposobDodania == 2){
            System.out.println("PODAJ ODPOWIEDNIO PO SPACJI : OBJETOSC I " +wszystkieInformacjeOSamochodzie);
            try {
                objetosc = scan.nextDouble();
                pojemnoscSilnika =  scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLE PODANE DANE");
                dodanieSamochodMiejski(umowaMiejsceParkingowe, sposobDodania);

            }
            scan.nextLine();
            String calySamochod = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochod).length != 2){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new SamochodMiejski(objetosc, metodaRozdzielajacaStringi(calySamochod)[0], " SAMOCHOD ", metodaRozdzielajacaStringi(calySamochod)[1], pojemnoscSilnika, 3));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
        }
        else
            return;
    }

    public void dodanieSamochodTerenowy(UmowaMiejsceParkingowe umowaMiejsceParkingowe, int sposobDodania){
        String wszystkieInformacjeOSamochodzie = " POJEMNOSC SILNIKA\nW NASTEPNEJ LINI\n"+
                "MARKA, TYP SILNIKA";
        double objetosc = 0;
        double x = 0;
        double y = 0;
        double z = 0;
        double pojemnoscSilnika = 0;
        if(sposobDodania == 1){
            System.out.println("PODAJ ODPOWIEDNIO: DLUGOSC, SZEROKOSC, WYSOKOSC," +wszystkieInformacjeOSamochodzie);
            try {
                x = scan.nextDouble();
                y = scan.nextDouble();
                z = scan.nextDouble();
                pojemnoscSilnika =  scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLE PODANE DANE");
                dodanieSamochodTerenowy(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calySamochodTerenowy = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochodTerenowy).length != 2){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new SamochodTerenowy
                        (x, y, z, metodaRozdzielajacaStringi(calySamochodTerenowy)[0], "SAMOCHOD", metodaRozdzielajacaStringi(calySamochodTerenowy)[1], pojemnoscSilnika,
                                "TERENOWE"));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
        }
        else if(sposobDodania == 2){
            System.out.println("PODAJ ODPOWIEDNIO: OBJETOSC," +wszystkieInformacjeOSamochodzie);
            try {
                objetosc = scan.nextDouble();
                pojemnoscSilnika = (double) scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLE PODANE DANE");
                dodanieSamochodTerenowy(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calySamochodTerenowy = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochodTerenowy).length != 2){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new SamochodTerenowy
                        (objetosc, metodaRozdzielajacaStringi(calySamochodTerenowy)[0], "SAMOCHOD", metodaRozdzielajacaStringi(calySamochodTerenowy)[1], pojemnoscSilnika,
                                "TERENOWE"));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
        }
        else
            return;
    }

    public void dodanieMotocykl(UmowaMiejsceParkingowe umowaMiejsceParkingowe, int sposobDodania){
        String wszystkieInformacjeOSamochodzie = " POJEMNOSC SILNIKA\nW NASTEPNEJ LINI\n"+
                "MARKA, TYP SILNIKA";
        double x = 0;
        double y = 0;
        double z = 0;
        double pojemnoscSilnika = 0;
        double objetosc = 0;
        if(sposobDodania == 1){
            System.out.println("PODAJ ODPOWIEDNIO: DLUGOSC, SZEROKOSC, WYSOKOSC," +wszystkieInformacjeOSamochodzie);
            try {
                x = scan.nextDouble();
                y = scan.nextDouble();
                z = scan.nextDouble();
                pojemnoscSilnika = scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLE PODANE DANE");
                dodanieMotocykl(umowaMiejsceParkingowe, sposobDodania);

            }
            scan.nextLine();
            String calyMotocykl = scan.nextLine();
            if(metodaRozdzielajacaStringi(calyMotocykl).length != 2){
                System.out.println("ZLE WPROWADZONE DANE");
                return;
            }
            try{
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new Motocykl(
                        x, y, z, metodaRozdzielajacaStringi(calyMotocykl)[0], "SAMOCHOD", metodaRozdzielajacaStringi(calyMotocykl)[1], pojemnoscSilnika, 2));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
            }
        }
        if(sposobDodania == 2){
            System.out.println("PODAJ ODPOWIEDNIO: OBJETOSC," +wszystkieInformacjeOSamochodzie);
            try {
                objetosc = scan.nextDouble();
                pojemnoscSilnika = scan.nextDouble();
            }
            catch (InputMismatchException e){
                System.out.println("ZLY FORMAT DANYCH");
                dodanieMotocykl(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calyMotocykl = scan.nextLine();
            if(metodaRozdzielajacaStringi(calyMotocykl).length != 2){
                System.out.println("ZLE PODANE DANE");

            }
            try{
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe, new Motocykl(
                        objetosc, metodaRozdzielajacaStringi(calyMotocykl)[0], "POJAZD LADOWY", metodaRozdzielajacaStringi(calyMotocykl)[1], pojemnoscSilnika, 2));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
            }

        }
        else
            return;
    }

    public void dodanieLodz(UmowaMiejsceParkingowe umowaMiejsceParkingowe, int sposobDodania){                             //public Lodz(double x, double y,double z, String nazwa, String typPojazdu, String typSilnika, String rodzajNapedu
        double x = 0;
        double y = 0;
        double z = 0;
        double objetosc = 0;
        if(sposobDodania == 1){
            System.out.println("PODAJ ODPOWIEDNIO: SZEROKOSC, DLUGOSC, WYSOKOSC\nW NASTEPNEJ LINI\nMARKA, TYP SILNIKA, RODZAJ NAPEDU");
            try {
                x = scan.nextDouble();
                y = scan.nextDouble();
                z = scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLY FORMAT DANYCH");
                dodanieLodz(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try{
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe,
                        new Lodz(x, y, z, metodaRozdzielajacaStringi(calaLodz)[0], "WODNY", metodaRozdzielajacaStringi(calaLodz)[1], metodaRozdzielajacaStringi(calaLodz)[2]));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
        }
        else if(sposobDodania == 2){
            System.out.println("PODAJ ODPOWIEDNIO: OBJETOSC\nW NASTEPNEJ LINI\nMARKA, TYP SILNIKA, RODZAJ NAPEDU");
            try {
                objetosc = scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLY FORMAT DANYCH ");
                dodanieLodz(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try{
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe,
                        new Lodz(objetosc, metodaRozdzielajacaStringi(calaLodz)[0], "WODNY", metodaRozdzielajacaStringi(calaLodz)[1], metodaRozdzielajacaStringi(calaLodz)[2]));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
        }
        else
            return;
    }

    public void dodanieAmfibi(UmowaMiejsceParkingowe umowaMiejsceParkingowe, int sposobDodania){ //(double x, double y, double z, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, int ileDrzwi, String budowaPojazdu
        double x = 0;
        double y = 0;
        double z = 0;
        double pojemnoscSilnika = 0;
        double objetosc = 0;
        if(sposobDodania == 1){
            System.out.println("PODAJ ODPOWIEDNIO: DLUGOSC, SZEROKOSC, WYSOKOSC, POJEMNOSC SILNIKA\nW NASTEPNEJ LINI\nMARKA, TYOP SILNIKA");
            try {
                x = scan.nextDouble();
                y = scan.nextDouble();
                z = scan.nextDouble();
                pojemnoscSilnika = scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLY FORMAT DANYCH");
                dodanieLodz(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
            }
            try{
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe,
                        new Amfibia(x, y, z, metodaRozdzielajacaStringi(calaLodz)[0], "WODNO-LADOWY", metodaRozdzielajacaStringi(calaLodz)[1],pojemnoscSilnika , "HERMETYCZNA"));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
            }
        }
        else if(sposobDodania == 2){
            System.out.println("PODAJ ODPOWIEDNIO: OBJETOSC,POJEMNOSC SILNIKA \nW NASTEPNEJ LINI\nMARKA, TYOP SILNIKA");
            try {
                objetosc = scan.nextDouble();
                pojemnoscSilnika = scan.nextDouble();
            }
            catch(InputMismatchException e){
                System.out.println("ZLY FORMAT DANYCH");
                dodanieAmfibi(umowaMiejsceParkingowe, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
            }
            try{
                uzytkownik.dodajPrzedmiot(umowaMiejsceParkingowe,
                        new Amfibia(objetosc, metodaRozdzielajacaStringi(calaLodz)[0], "WODNO-LADOWY", metodaRozdzielajacaStringi(calaLodz)[1],pojemnoscSilnika, "HERMETYCZNA"));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
            }
        }
        else
            return;
    }

    public void zapisanieStanuOsiedlaDoPliku(){
        StringBuilder zapisStanu = new StringBuilder();
        File file = new File("ZAPIS_STANU_OSIEDLA.txt");
        zapisStanu.append(System.getProperty("line.separator"));
        zapisStanu.append(przerywnik);
        zapisStanu.append(System.getProperty("line.separator"));
        zapisStanu.append("STAN OSIEDLA Z " + Zegar.INSTANCE.getCzasGlobalny() + "\n");
        zapisStanu.append(System.getProperty("line.separator"));
        zapisStanu.append(przerywnik);
        zapisStanu.append(System.getProperty("line.separator"));
        for(Osoba najemca : MenadzerStanu.stworzListeNajemcow()){
            zapisStanu.append(najemca);
            zapisStanu.append(System.getProperty("line.separator"));
            zapisStanu.append(przerywnik);
            zapisStanu.append(System.getProperty("line.separator"));
            for(Umowa umowa : najemca.getListaUmow()){
                zapisStanu.append(umowa);
                zapisStanu.append(System.getProperty("line.separator"));
                if(umowa.getClass() == UmowaMieszkanie.class){
                    UmowaMieszkanie umowaMieszkanie = (UmowaMieszkanie) umowa;
                    zapisStanu.append(System.getProperty("line.separator"));
                    zapisStanu.append("LISTA LOKATOROW");
                    zapisStanu.append(System.getProperty("line.separator"));
                    zapisStanu.append(przerywnik);
                    zapisStanu.append(System.getProperty("line.separator"));
                    for(Osoba lokator : umowaMieszkanie.getListaDomotorow()){
                        zapisStanu.append(lokator);
                        zapisStanu.append(System.getProperty("line.separator"));
                    }
                }
                else{
                    UmowaMiejsceParkingowe umowaMiejsceParkingowe = (UmowaMiejsceParkingowe) umowa;
                    zapisStanu.append("LISTA PRZEDMIOTOW");
                    zapisStanu.append(System.getProperty("line.separator"));
                    zapisStanu.append(przerywnik);
                    for(Przedmiot przedmiot : umowaMiejsceParkingowe.getListaPrzedmiotow().values()) {
                        zapisStanu.append(przedmiot );
                        zapisStanu.append(System.getProperty("line.separator"));
                    }
                }
            }
        }
        zapisStanu.append("DOSTEPNE MIESZKANIA");
        zapisStanu.append(System.getProperty("line.separator"));
        zapisStanu.append(przerywnik);
        zapisStanu.append(System.getProperty("line.separator"));
        for(Mieszkanie mieszkanie : MenadzerStanu.stworzListeDostepnychMieszkan()){
            zapisStanu.append(mieszkanie );
            zapisStanu.append(System.getProperty("line.separator"));
        }
        zapisStanu.append("DOSTEPNE MIEJSCA PARKINGOWE");
        zapisStanu.append(System.getProperty("line.separator"));
        zapisStanu.append(przerywnik);
        zapisStanu.append(System.getProperty("line.separator"));
        for(MiejsceParkingowe miejsceParkingowe : MenadzerStanu.stworzListeDostepnychMiejscParkingowych()){
            zapisStanu.append(miejsceParkingowe);
            zapisStanu.append(System.getProperty("line.separator"));
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(zapisStanu.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(writer != null){
            try {
                writer.close();
            }
            catch(IOException e ){
                e.getStackTrace();
            }
        }
    }

    public ArrayList<Umowa> stworzListeZadluzonychUmow(){
        List<Umowa> listaUmow = uzytkownik.getListaUmow();
        ArrayList<Umowa> listaZadluzonychUmow = new ArrayList<>();
        for(Umowa umowa : listaUmow){
            if(umowa.wezwanieDoZaplaty != null){
                listaZadluzonychUmow.add(umowa);
            }
        }
        return listaZadluzonychUmow;
    }



    public void wypiszListeUmow() {
        List<Umowa> lokalnaListaUmow = uzytkownik.getListaUmow();
        for (int i = 0; i < lokalnaListaUmow.size(); i++) {
            if (lokalnaListaUmow.get(i).wezwanieDoZaplaty == null)
                System.out.println((i + 1) + "." + lokalnaListaUmow.get(i).toString());
            else
                System.out.println((i + 1) + "." + lokalnaListaUmow.get(i).toString() + " ZADLUZONE");
        }
    }

    public void wypiszListeLokatorow(UmowaMieszkanie umowaMieszkanie){
        List<Osoba> lokalnaListaLokatorow = umowaMieszkanie.getListaDomotorow();
        for (int i = 0; i < lokalnaListaLokatorow.size() ; i++) {
            System.out.println((i+1) + ". " + lokalnaListaLokatorow.get(i));
        }
    }

    public int wczytajLiczbe(){
        try{
            return scan.nextInt();
        }
        catch(InputMismatchException e){
            System.out.println("ZLY FORMAT");
            wczytajLiczbe();
        }
        return -1;
    }


    public void wyswietlOpcjeSwojeDaneIPomieszczenia(){
        System.out.println("----- DANE OSOBOWE -----\n" +
                uzytkownik.getStringWszystkieDane());
    }

    public boolean czyZadluzone(Umowa umowa){
        if(umowa.wezwanieDoZaplaty != null)
            return  true;
        return false;
    }

    public String[] metodaRozdzielajacaStringi(String imieNazwiskoAdres){
        String[] tablicaRozdzielonych = imieNazwiskoAdres.split(" ");
        return tablicaRozdzielonych;
    }

}
