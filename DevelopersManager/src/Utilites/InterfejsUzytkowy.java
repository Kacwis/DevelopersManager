package Utilites;
import Obiekty.*;
import Pojazdy.*;
import Wyjatki.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InterfejsUzytkowy {
    /*
    List <Osoba> listaNieNajemcow;
    public Scanner scan;
    Osoba uzytkownik;
    boolean dziala;


    public InterfejsUzytkowy(){
        listaNieNajemcow = new ArrayList<>();
        this.scan = new Scanner(System.in);
        // Zegar.INSTANCE.zegarStart(LocalDate.now());
        boolean dziala = true;
        System.out.println("                 ---- WITAM ----\n" +
                           "------------------------------------------------------");
        System.out.println("----- WYBRANIE CYFRY 0 POWODUJE WYJSCIE Z SEKCJI ----\n" +
                            " ---- ORAZ W MENU GLOWNYM OPUSZCZENIE PROGRAMU ----");
        System.out.println("------- ZALOGUJ SIE WYBIERAJAC SWOJA OSOBE -------");
        MenadzerStanu.wypiszListaOsob();
        uzytkownik = MenadzerStanu.listaOsob.get(scan.nextInt() - 1 );
        System.out.println(" WYBRANO: " + uzytkownik);
        while(uzytkownik != null && dziala){
            System.out.println("-------- WYBIERZ INTERESUJACA CIE OPCJE -------");
            System.out.println(stringMenuWyboru());
            int numerOpcjiMenuGlowne = wczytajLiczbe();
            if(numerOpcjiMenuGlowne < 5 && numerOpcjiMenuGlowne > 0){
                menuWyboru(numerOpcjiMenuGlowne);
            }
            else if(numerOpcjiMenuGlowne == 0){
                System.out.println("---- MILEGO DNIA ----");
                System.out.println("Zamykanie programu...");
                dziala = false;
                Zegar.INSTANCE.zegarStop();
                scan.close();
            }
            else if(numerOpcjiMenuGlowne == -1){
                System.out.println("PODAJ NUMER JESZCZE RAZ");
                continue;
            }
        }
    }


    public void menuWyboru(int a){
        switch(a){
            case 1:
                wypiszOpcjaNr1();
                opcjaWypiszSwojeDaneIMieszkania();
                break;
            case 2:
                opcjaWyswietlWszystkiePomieszczenia();
                break;

        }
    }

    public String stringMenuWyboru(){
        return "1. WYPISZ SWOJE DANE I POSIADANE POMIESZCZENIA \n" +
               "2. WYSWIETL WSZYSTKIE POMIESZCZENIA \n" +
               "3. ZAPISZ STAN OSIEDLA \n"+
                "------------------- DATA ------------------\n"+
                "---------------- " + Zegar.INSTANCE.czasGlobalny + " ----------------- ";

    }

    public void wypiszOpcjaNr1(){
        System.out.println("----- DANE OSOBOWE -----\n" +
                uzytkownik.getWszystkieDane() + "\n" +
                "-- POSIADANE POMIESZCZENIA --");
        wypiszListeUmow(uzytkownik);
        System.out.println("WYBIERZ NUMER POMIESZCZENIA DLA DALSZYCH AKCJI");

    }

    public void dodajLokatora(String imie, String nazwisko, String pesel, String adres, int dzien, int miesiac , int rok, int numerMieszkania){
        Osoba osoba = new Osoba(imie, nazwisko, pesel, adres, dzien , miesiac , rok);
        MenadzerStanu.listaOsob.add(osoba);
        uzytkownik.zamelduj(osoba, numerMieszkania);
    }

    public void wypiszListeNieNajemcow(){
        int numerator = 1;
        for (int i = 0; i < MenadzerStanu.listaOsob.size() ; i++) {
            if(!MenadzerStanu.listaOsob.get(i).czyNajemca()){
                listaNieNajemcow.add(MenadzerStanu.listaOsob.get(i));
                System.out.println(numerator + ". " + listaNieNajemcow.get(numerator - 1));
                numerator++;
            }
        }
    }

    public int sizeListyNieNajemcow(){
        return this.listaNieNajemcow.size();
    }


    public String getListaDostepnychPojazdow(){
        return  "1. Samochod miejski\n" +
                "2. Samochod terenowy\n" +
                "3. Motocykl\n" +
                "4. Lodz\n" +
                "5. Amfibia";
    }


    public String[] metodaRozdzielajacaStringi(String imieNazwiskoAdres){
        String[] tablicaRozdzielonych = imieNazwiskoAdres.split(" ");
        return tablicaRozdzielonych;
    }

    public void opcjaWymeldujLubWezDane(Osoba os, Umowa umowa){
        System.out.println("---- DOSTEPNE AKCJE ---- \n" +
                1 + " WYMELDUJ WYBRANA OSOBE \n" +
                2 + " WYSWIETL JEJ DANE OSOBOWE");
        int wybor = wczytajLiczbe();
        switch(wybor){
            case 0:
                return;
            case 1:
                uzytkownik.wymelduj(os , uzytkownik.getListaUmow().indexOf(umowa));
                System.out.println("WYMELDOWANO");
                break;
            case 2:
                int numerDomotora = umowa.getListaDomotorow().indexOf(os);
                System.out.println(umowa.getListaDomotorow().get(numerDomotora).getWszystkieDane());
                break;
            default:
                System.out.println("WYBRANO ZLY NUMER");
                opcjaWymeldujLubWezDane(os, umowa);
        }
    }

    public void opcjaWypiszSwojeDaneIMieszkania(){
        int indeksPomieszczenia = wczytajLiczbe() - 1;
        if(indeksPomieszczenia < -1 || indeksPomieszczenia >= uzytkownik.getListaUmow().size())
        {
            System.out.println("ZLY NUMER POMIESZCZENIA");
            System.out.println("PODAJ NUMER JESZCZE RAZ");
            wypiszOpcjaNr1();
            opcjaWypiszSwojeDaneIMieszkania();
        }
        else if(indeksPomieszczenia == -1){
            return;
        }
        else {
            Umowa umowa = uzytkownik.getListaUmow().get(indeksPomieszczenia);
            System.out.println( "---- WYBRANE POMIESZCZENIE ---- \n" +
                                umowa.getPomieszczenie() +  " ----\n"+
                                "-------------------------------------------------------------");
            mieszkanieCzyMiejsceParkingowe(umowa);
        }


    }

    public void mieszkanieCzyMiejsceParkingowe(Umowa umowa) {
        if (umowa.getPomieszczenie().getClass() == Mieszkanie.class){
            mieszkanieAkcje(umowa);
        }
        else if(umowa.getPomieszczenie().getClass() == MiejsceParkingowe.class){
            miejsceParkingoweOpcje(umowa);
        }
        else
            return;
    }

    public void miejsceParkingoweOpcje(Umowa umowa){
        System.out.println("1.USUN PRZEDMIOT Z MIEJSCA PARKINGOWEGO\n"+
                            "2.DODAJ PRZEDMIOT DO MIEJSCA PARKINGOWEGO\n");
        List<Przedmiot> lokalnaListaPrzedmiotow = umowa.getListaPrzedmiotow();
        System.out.println("--------- LISTA PRZEDMIOTOW -------------");
        for (int i = 0; i <lokalnaListaPrzedmiotow.size() ; i++) {
            System.out.println((i+1) + ". " +  lokalnaListaPrzedmiotow.get(i).toString());
        }
        int numerMiejsceParkingoweOpcje = wczytajLiczbe();
        if(numerMiejsceParkingoweOpcje == 1)
            usunPrzedmiotZListy(umowa);
        else if(numerMiejsceParkingoweOpcje == 2)
            dodawaniePrzedmiotuLubPojazdu(umowa);
        else if(numerMiejsceParkingoweOpcje == 0)
            return;
        else {
            System.out.println("ZLY NUMER");
            miejsceParkingoweOpcje(umowa);
        }
    }

    public void dodawanieZListy(Umowa umowa,int numerLokatoraListyNieNajemcow){
        umowa.getListaDomotorow().add(listaNieNajemcow.get(numerLokatoraListyNieNajemcow));
    }

    public void dodawanieNowegoLokatora(Umowa umowa) {
        System.out.println("IMIE  NAZWISKO  PESEL  ADRES i W NASTEPNEJ LINI DZIEN MIESIAC ROK");
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
            dodawanieNowegoLokatora(umowa);
        }
        if (metodaRozdzielajacaStringi(informacje).length != 4) {
            System.out.println("NIE PODANO POPRAWNEJ ILOSCI DANYCH");
            return;
        }
        dodajLokatora(metodaRozdzielajacaStringi(informacje)[0], metodaRozdzielajacaStringi(informacje)[1], metodaRozdzielajacaStringi(informacje)[2], metodaRozdzielajacaStringi(informacje)[3], dzien, miesiac, rok, uzytkownik.getListaUmow().indexOf(umowa));
        System.out.println(listaNieNajemcow);

    }

    public void dodawanieLokatora(Umowa umowa){
        System.out.println("MOZNA DODAC LOKATOROW DOSTEPNYCH W ZAKRESIE OSIEDLA WYBIERAJAC NUMER OSOBY");
        wypiszListeNieNajemcow();
        System.out.println("LUB MOZNA DODAC TEZ WLASNA OSOBE Z POZA OSIEDLA POPRZEZ KLIKANIE " + (listaNieNajemcow.size()+1) +
                "\nPODAJAC ODPOWIEDNIE DANE");
        int numerLokatoraListyNieNajemcow = wczytajLiczbe() - 1;
        if(numerLokatoraListyNieNajemcow < sizeListyNieNajemcow()){
            dodawanieZListy(umowa, numerLokatoraListyNieNajemcow);
            listaNieNajemcow.remove(numerLokatoraListyNieNajemcow);
        }
        else if(numerLokatoraListyNieNajemcow == listaNieNajemcow.size()){
            dodawanieNowegoLokatora(umowa);
        }
        else
            return;

    }

    public void mieszkanieAkcje(Umowa umowa){
        List<Osoba> lokalnaListaDomotorow = umowa.getListaDomotorow();
        for(int i = 0;i<lokalnaListaDomotorow.size(); i++){
            System.out.println((i+1) + ". " + lokalnaListaDomotorow.get(i));
        }
        if(umowa.wezwanieDoZaplaty == null){
            System.out.println((lokalnaListaDomotorow.size() + 1 )+". DODAJ NOWA OSOBE");
            mieszkanieNieZadluzone(umowa, lokalnaListaDomotorow);
        }
        else{
            System.out.println(lokalnaListaDomotorow.size() +". DODAJ NOWA OSOBE");
            System.out.println("-------------------------------\n" + (lokalnaListaDomotorow.size() + 1) + ". ODNOW NAJEM" );
            mieszkanieZadluzone(umowa, lokalnaListaDomotorow);
        }
    }

    public void usunPrzedmiotZListy(Umowa umowa){
        List<Przedmiot> lokalnaListaPrzedmiotow = umowa.getListaPrzedmiotow();
        for (int i = 0; i <lokalnaListaPrzedmiotow.size() ; i++) {
            System.out.println((i + 1) + "." + lokalnaListaPrzedmiotow.get(i));
        }
        int indeksPrzedmiotuZListy = wczytajLiczbe() - 1;
        if(indeksPrzedmiotuZListy < lokalnaListaPrzedmiotow.size() && indeksPrzedmiotuZListy >= 0){
            uzytkownik.usunPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), indeksPrzedmiotuZListy);
        }
        else if(indeksPrzedmiotuZListy == -1)
            return;
        else{
            System.out.println("ZLY NUMER PODAJ JESZCZE RAZ ");
            usunPrzedmiotZListy(umowa);
        }

    }

    public void dodawaniePrzedmiotuLubPojazdu(Umowa umowa) {
        System.out.println("1.PRZEDMIOT\n" + "2.POJAZD");
        int przedmiotCzyPojazd = wczytajLiczbe();
        switch (przedmiotCzyPojazd ) {
            case 0:
                return;
            case 1:
                dodawaniePrzedmiotu(umowa);
                break;
            case 2:
                dodawaniePojazdu(umowa);
                break;
            default:
                System.out.println("BLEDNA CYFRA");
                dodawaniePrzedmiotuLubPojazdu(umowa);
        }
    }

    public void dodawaniePrzedmiotu(Umowa umowa){
        System.out.println("PODAJ SPOSOB OKRESLENIA OBJETOSCI PRZEDMIOTU\n" +
                            "1. Z WYMIARAMI\n" +
                            "2. Z GOTOWA OBJETOSCIA");
        int numerSposobuDodaniaPrzedmiotu = wczytajLiczbe();
        if(numerSposobuDodaniaPrzedmiotu == 1){
            System.out.println("PODAJ ODPOWIEDNIO: SZEROKOSC, DLUGOSC, WYSOKOSC ORAZ NAZWE");
            double x = scan.nextDouble();
            double y = scan.nextDouble();
            double z = scan.nextDouble();
            String nazwa = scan.nextLine();
            try {
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new Przedmiot(x, y, z, nazwa));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
            System.out.println("DODANO " + nazwa);
        }
        else if(numerSposobuDodaniaPrzedmiotu == 2){
            System.out.println("PODAJ ODPOWIEDNIO: OBJETOSC ORAZ NAZWE");
            double objetosc = scan.nextDouble();
            String nazwa = scan.nextLine();
            try {
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new Przedmiot(objetosc, nazwa));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
            System.out.println("DODANO " + nazwa );
        }
        else
            return;
    }

    public void dodawaniePojazdu(Umowa umowa){
        System.out.println("PODAJ SPOSOB OKRESLENIA OBJETOSCI PRZEDMIOTU\n" +
                "1. Z WYMIARAMI\n" +
                "2. Z GOTOWA OBJETOSCIA");
        int numerSposobuDodaniaPojazdu = wczytajLiczbe();
        wyborPojazdu(umowa, numerSposobuDodaniaPojazdu);
    }

    public void wyborPojazdu(Umowa umowa, int sposobDodania){
        System.out.println(getListaDostepnychPojazdow());
        int numerWyboruPojazdu = wczytajLiczbe();
        switch(numerWyboruPojazdu){
            case 0:
                return;
            case 1:
                dodanieSamochodMiejski(umowa,sposobDodania);
                break;
            case 2:
                dodanieSamochodTerenowy(umowa, sposobDodania);
                break;
            case 3:
                dodanieMotocykl(umowa, sposobDodania);
                break;
            case 4:
                dodanieLodz(umowa, sposobDodania);
                break;
            case 5:
                dodanieAmfibi(umowa, sposobDodania);
                break;
            default:
                System.out.println("WYBIERZ POJAZD POPRAWNIE");
                wyborPojazdu(umowa, sposobDodania);

        }
    }

    public void dodanieSamochodMiejski(Umowa umowa, int sposobDodania){
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
                dodanieSamochodMiejski(umowa, sposobDodania);

            }
            scan.nextLine();
            String calySamochod = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochod).length != 2){
                System.out.println("ZLE PODANE INFORMACJE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new SamochodMiejski(x, y, z, metodaRozdzielajacaStringi(calySamochod)[0], " SAMOCHOD ", metodaRozdzielajacaStringi(calySamochod)[1], pojemnoscSilnika, 3));
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
                dodanieSamochodMiejski(umowa, sposobDodania);

            }
            scan.nextLine();
            String calySamochod = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochod).length != 2){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new SamochodMiejski(objetosc, metodaRozdzielajacaStringi(calySamochod)[0], " SAMOCHOD ", metodaRozdzielajacaStringi(calySamochod)[1], pojemnoscSilnika, 3));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
                return;
            }
        }
        else
            return;
    }

    public void dodanieSamochodTerenowy(Umowa umowa, int sposobDodania){
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
                dodanieSamochodTerenowy(umowa, sposobDodania);

            }
            scan.nextLine();
            String calySamochodTerenowy = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochodTerenowy).length != 2){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new SamochodTerenowy
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
                dodanieSamochodTerenowy(umowa, sposobDodania);
            }
            scan.nextLine();
            String calySamochodTerenowy = scan.nextLine();
            if(metodaRozdzielajacaStringi(calySamochodTerenowy).length != 2){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try {
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new SamochodTerenowy
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

    public void dodanieMotocykl(Umowa umowa, int sposobDodania){
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
                dodanieMotocykl(umowa, sposobDodania);

            }
            scan.nextLine();
            String calyMotocykl = scan.nextLine();
            if(metodaRozdzielajacaStringi(calyMotocykl).length != 2){
                System.out.println("ZLE WPROWADZONE DANE");
                return;
            }
            try{
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new Motocykl(
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
                dodanieMotocykl(umowa, sposobDodania);
            }
            scan.nextLine();
            String calyMotocykl = scan.nextLine();
            if(metodaRozdzielajacaStringi(calyMotocykl).length != 2){
                System.out.println("ZLE PODANE DANE");

            }
            try{
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa), new Motocykl(
                        objetosc, metodaRozdzielajacaStringi(calyMotocykl)[0], "POJAZD LADOWY", metodaRozdzielajacaStringi(calyMotocykl)[1], pojemnoscSilnika, 2));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
            }

        }
        else
            return;
    }

    public void dodanieLodz(Umowa umowa, int sposobDodania){ //public Lodz(double x, double y,double z, String nazwa, String typPojazdu, String typSilnika, String rodzajNapedu
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
                dodanieLodz(umowa, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try{
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa),
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
                dodanieLodz(umowa, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
                return;
            }
            try{
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa),
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

    public void dodanieAmfibi(Umowa umowa, int sposobDodania){ //(double x, double y, double z, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, int ileDrzwi, String budowaPojazdu
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
                dodanieLodz(umowa, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
            }
            try{
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa),
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
                dodanieAmfibi(umowa, sposobDodania);
            }
            scan.nextLine();
            String calaLodz = scan.nextLine();
            if(metodaRozdzielajacaStringi(calaLodz).length != 3){
                System.out.println("ZLE PODANE DANE");
            }
            try{
                uzytkownik.dodajPrzedmiot(uzytkownik.getListaUmow().indexOf(umowa),
                        new Amfibia(objetosc, metodaRozdzielajacaStringi(calaLodz)[0], "WODNO-LADOWY", metodaRozdzielajacaStringi(calaLodz)[1],pojemnoscSilnika, "HERMETYCZNA"));
            }
            catch(TooManyThingsException e){
                System.out.println(e.err());
            }
        }
        else
            return;
    }

    public void opcjaWyswietlWszystkiePomieszczenia(){
        System.out.println("WYBIERZ POMIESZCZENIA\n" +
                            "  1.DO WYNAJECIA\n" + "  2.JUZ WYNAJETE");
        int wyborDoWynajcieCzyNie = wczytajLiczbe();
        if(wyborDoWynajcieCzyNie == 1){
            mieszkanieCzyMiejsceParkingoweDoWynajecia();
        }
        else if(wyborDoWynajcieCzyNie == 2){
            wypiszZajetePomiesczenia();
            zajetePomieszczenie();
        }

    }

    public void wyborMieszkania(){
        int numerWyboruMieszkania = wczytajLiczbe() - 1;
        if(numerWyboruMieszkania < -1 && numerWyboruMieszkania >= MenadzerStanu.listaDostepnychMieszkan.size()){
            System.out.println("WYBRANO ZLY NUMER");
            wyborMieszkania();
        }
        else if(numerWyboruMieszkania == 0)
            return;

        dostepneMieszkaniaOpcje(numerWyboruMieszkania);
    }

    public void wyborMiejscaParkingowego(){
        int numerWyboruMiejscaParkingowego = wczytajLiczbe() - 1;
        if(numerWyboruMiejscaParkingowego < -1 && numerWyboruMiejscaParkingowego >= MenadzerStanu.listaDostepnychMiejscParkingowych.size()){
            System.out.println("WYBRANO ZLY NUMER:");
            wyborMiejscaParkingowego();
        }
        else if(numerWyboruMiejscaParkingowego == -1){
            return;
        }
        dostepneMiejscaParkingoweOpcje(numerWyboruMiejscaParkingowego);
    }

    public void mieszkanieCzyMiejsceParkingoweDoWynajecia(){
        System.out.println("WYBIERZ CZY INTERESUJA CIE MIESZKANIA CZY MIEJSCA PARKINGOWE\n" +
                            "  1. DOSTEPNE MIESZKANIA\n  2. DOSTEPNE MIEJSCA PARKINGOWE");
        int wyborMieszkanCzyMiejsc = wczytajLiczbe();
        if(wyborMieszkanCzyMiejsc == 1){
            wypiszListeDostepnychMieszkan();
            System.out.println("--------------------------------------\n"+               //METODA
                                "WYBIERZ INTERESUJACE CIE MIESZKANIE");
            wyborMieszkania();
        }
        else if(wyborMieszkanCzyMiejsc == 2){
            wypiszListeDostepnychMiejscParkingowych();
            System.out.println("--------------------------------------\n"+
                    "WYBIERZ INTERESUJACE CIE MIEJSCE PARKINGOWE");
                    wyborMiejscaParkingowego();
        }
        else if(wyborMieszkanCzyMiejsc == 0){
            return;
        }
        else {
            System.out.println("WYBRANO ZLY NUMER");
            mieszkanieCzyMiejsceParkingoweDoWynajecia();
        }
    }

    public void dostepneMieszkaniaOpcje(int numerWyborMieszkania){
        System.out.println("---- WYBRANE MIESZKANIE ----");
        System.out.println(MenadzerStanu.listaDostepnychMieszkan.get(numerWyborMieszkania));
        Mieszkanie mieszkanie = MenadzerStanu.listaDostepnychMieszkan.get(numerWyborMieszkania);
        System.out.println("1. WYNAJMIJ WYBRANE MIESZKANIE\n" +
                            "2. WYSWIETL INFORMACJE"  );
        int numerWyboruOpcjiWMieszkaniu = wczytajLiczbe();
        if(numerWyboruOpcjiWMieszkaniu == 1){
            dodawanieMieszkania(mieszkanie);
        }
        else if(numerWyboruOpcjiWMieszkaniu == 2){
            System.out.println(mieszkanie);
        }
        else if(numerWyboruOpcjiWMieszkaniu == 0){
            return;
        }
        else{
            System.out.println("ZLY FORMAT DANYCH");
            dostepneMieszkaniaOpcje(numerWyborMieszkania);
        }
    }

    public void dodawanieMieszkania(Mieszkanie mieszkanie){
        System.out.println("PODAJ DLUGOSC WYNAJMU W MIESIACACH");
        int dlugoscWynajm = wczytajLiczbe();
        try{
            uzytkownik.dodajMieszkanie(mieszkanie,dlugoscWynajm);
        }
        catch(ProblematicTenantException e){
            System.out.println(uzytkownik + " " + e.err());
            wypiszListeUmow(uzytkownik);
        }
        catch(TooMuchRoomsException e){
            System.out.println(e.err());
        }
    }

    public void dodawanieMiejscaParkingowego(MiejsceParkingowe miejsceParkingowe){
        System.out.println("PODAJ DLUGOSC WYNAJMU W MIESIACACH");
        int dlugoscNajmu = wczytajLiczbe();
        if(uzytkownik.dataNajpozniejszegoKoncaNajmu().isAfter(Zegar.INSTANCE.getCzasGlobalny().plusMonths(dlugoscNajmu))){
            try{
                uzytkownik.dodajMieszkanie(miejsceParkingowe, dlugoscNajmu );
            }
            catch(ProblematicTenantException e){
                System.out.println(e.err());
            }
            catch(TooMuchRoomsException e){
                System.out.println(e.err());
            }
        }
        else if(dlugoscNajmu == 0){
            return;
        }
        else {
            System.out.println("MUSISZ PODAC DLUGOSC NAJMU KTORA MAKSYMALNIE SIE KONCZY Z NAJMEM MIESZKANIA");
            System.out.println("AKTUALNA DATA " + Zegar.INSTANCE.czasGlobalny);
            System.out.println("DATA KONCA NAJMU MIESZKANIA" + uzytkownik.dataNajpozniejszegoKoncaNajmu());
            dodawanieMiejscaParkingowego(miejsceParkingowe);
        }
    }

    public void dostepneMiejscaParkingoweOpcje(int numerWyboruMiejscaParkingowego){
        MiejsceParkingowe miejsceParkingowe = MenadzerStanu.listaDostepnychMiejscParkingowych.get(numerWyboruMiejscaParkingowego);
        System.out.println("1.WYNAJMIJ MIEJSCE PARKINGOWE\n" +
                            "2.WYSWIETL INFROMACJE ");
        int numerWyboruMiejscaParkingoweOpcje = wczytajLiczbe();
        if(numerWyboruMiejscaParkingoweOpcje == 1){
            dodawanieMiejscaParkingowego(miejsceParkingowe);
        }
        else if(numerWyboruMiejscaParkingowego == 2){
            System.out.println(MenadzerStanu.listaDostepnychMiejscParkingowych.get(numerWyboruMiejscaParkingowego));
        }
        else
            return;
    }

    public void wypiszZajetePomiesczenia(){
        MenadzerStanu.stworzListeNajemncow();
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        for (int i = 0; i <MenadzerStanu.listaNajemcow.size() ; i++) {
            System.out.println((i + 1) + "." + MenadzerStanu.listaNajemcow.get(i));
            System.out.println("-------------------------");
            wypiszListeUmowBezNumerow(MenadzerStanu.listaNajemcow.get(i));
        }
    }

    public void wypiszListeUmowBezNumerow(Osoba osoba){
        List<Umowa> lokalnaListaUmow = osoba.getListaUmow();
        char literka = 'a';
        for (int i = 0; i < lokalnaListaUmow.size() ; i++) {
            System.out.println(literka +") " + lokalnaListaUmow.get(i));
            literka++;
        }
        System.out.println("-------------------------------");
    }

    public void wypiszListeUmow(Osoba osoba){
        List<Umowa> lokalnaListaUmow = osoba.getListaUmow();
        for (int i = 0; i <lokalnaListaUmow.size() ; i++) {
            if(lokalnaListaUmow.get(i).wezwanieDoZaplaty == null)
                System.out.println((i + 1) + "." + lokalnaListaUmow.get(i).toStringPomieszczen());
            else
                System.out.println((i + 1) + "." + lokalnaListaUmow.get(i).toStringPomieszczen() + " ZADLUZONE");
        }
    }

    public void zajetePomieszczenie(){
        System.out.println("WYBIERZ NUMER OSOBY KTORA POSIADA INTERESUJACE CIE POMIESZCZENIE");
        int numerOsobyZajetePomieszczenia = wczytajLiczbe() - 1;
        if(numerOsobyZajetePomieszczenia < -1 || numerOsobyZajetePomieszczenia >= MenadzerStanu.listaNajemcow.size()){
            System.out.println("ZLY NUMER");
            zajetePomieszczenie();
        }
        else if(numerOsobyZajetePomieszczenia == -1){
            return;
        }
        wypiszListeUmow(MenadzerStanu.listaNajemcow.get(numerOsobyZajetePomieszczenia));
        wybraniePomieszczenia(numerOsobyZajetePomieszczenia);

    }

    public void wybraniePomieszczenia(int numerOsobyZajetePomieszczenia){
        System.out.println("PROSZE WYBRAC POMIESZCZENIE");
        List <Umowa> lokalnaListaUmow = MenadzerStanu.listaNajemcow.get(numerOsobyZajetePomieszczenia).getListaUmow();
        int numerPomieszczeniaZajetePomieszczenia = wczytajLiczbe() - 1;
        if(numerPomieszczeniaZajetePomieszczenia < -1 || numerPomieszczeniaZajetePomieszczenia >lokalnaListaUmow.size()){
            System.out.println("PODAJ POPRAWNY NUMER\nALBO WPISZ 0 ZEBY WYJSC");
            wybraniePomieszczenia(numerOsobyZajetePomieszczenia);
        }
        else if(numerPomieszczeniaZajetePomieszczenia == - 1){
            return;
        }
        if(lokalnaListaUmow.get(numerPomieszczeniaZajetePomieszczenia).getPomieszczenie().getClass() == MiejsceParkingowe.class){
            Umowa miejsceParkingowe = lokalnaListaUmow.get(numerPomieszczeniaZajetePomieszczenia);
            System.out.println("-----------------------\n"+
                                miejsceParkingowe.getPomieszczenie()+
                                "\n------------------------");
            wypiszListePrzedmiotow(miejsceParkingowe);
        }
        else{
            Umowa mieszkanie = lokalnaListaUmow.get(numerPomieszczeniaZajetePomieszczenia);
            System.out.println("-----------------------\n"+
                    mieszkanie.getPomieszczenie()+
                    "\n------------------------");
            wypiszListaDomotorow(mieszkanie);
        }
    }

    public void wypiszListaDomotorow(Umowa mieszkanie){
        List<Osoba> lokalnaListaDomotorow = mieszkanie.getListaDomotorow();
        System.out.println("-----------------------");
        for (int i = 0; i <lokalnaListaDomotorow.size() ; i++) {
            System.out.println((i+1) + "." + lokalnaListaDomotorow.get(i));
        }
        System.out.println("--------------------------");

    }

    public void mieszkanieZadluzone(Umowa umowa, List <Osoba> listaDomotorow){
        int indeksListyDomotorow = wczytajLiczbe() - 1;
        if(indeksListyDomotorow >= 0 && indeksListyDomotorow < listaDomotorow.size()){
            opcjaWymeldujLubWezDane(listaDomotorow.get(indeksListyDomotorow), umowa);
        }
        else if(indeksListyDomotorow == listaDomotorow.size()){
            dodawanieLokatora(umowa);
        }
        else if(indeksListyDomotorow == -1){
            return;
        }
        else if(indeksListyDomotorow == listaDomotorow.size() + 1){
            System.out.println("---- ODNOW UMOWE ----" +
                                "\nPodaj Dlugosc Najmu");
            int dlugoscNajmu = wczytajLiczbe();
            try{
                uzytkownik.dodajMieszkanie(umowa.getPomieszczenie(), dlugoscNajmu);
            }
            catch(ProblematicTenantException e){
                System.out.println(e.err());
            }
            catch(TooMuchRoomsException e){
                System.out.println(e.err());
            }
        }
        else{
            System.out.println("PODANY NUMER JEST BLEDNY");
            mieszkanieAkcje(umowa);
        }

    }

    public void mieszkanieNieZadluzone(Umowa umowa, List<Osoba> listaDomotorow){
        int indeksListyDomotorow = wczytajLiczbe() -1;
        if(indeksListyDomotorow > -1 && indeksListyDomotorow < listaDomotorow.size()){
            opcjaWymeldujLubWezDane(listaDomotorow.get(indeksListyDomotorow), umowa);
        }
        else if(indeksListyDomotorow == listaDomotorow.size()){
            dodawanieLokatora(umowa);
        }
        else if(indeksListyDomotorow == -1){
            return;
        }
        else{
            System.out.println("PODANY NUMER JEST BLEDNY");
            mieszkanieAkcje(umowa);
        }

    }

    public int wczytajLiczbe(){
        int numerAkcji;
        scan.nextLine();
       try{
           numerAkcji = scan.nextInt();
           return  numerAkcji;
       }
       catch (InputMismatchException e){
           System.out.println("ZLY FORMAT");
       }
        return -1;
    }

    public void wypiszListePrzedmiotow(Umowa umowa){
        List<Przedmiot> listaPrzedmiotow = umowa.getListaPrzedmiotow();
        for (int i = 0; i <listaPrzedmiotow.size() ; i++) {
            System.out.println(i+1 + ". " + listaPrzedmiotow.get(i) );
        }
    }

    public void wypiszListeDostepnychMieszkan(){
        MenadzerStanu.stworzListeDostepnychMieszkan();
        List<Mieszkanie> listaDostepnychMieszkan = MenadzerStanu.listaDostepnychMieszkan;
        System.out.println("-------- DOSTEPNE MIESZKANIA ---------");
        for (int i = 0; i <listaDostepnychMieszkan.size() ; i++) {
            System.out.println((i + 1) + ". " + listaDostepnychMieszkan.get(i));
        }
    }

    public void wypiszListeDostepnychMiejscParkingowych(){
        MenadzerStanu.stworzListeDostepnychMiejscParkingowych();
        List<MiejsceParkingowe> listaDostepnychMiejscParkingowych = MenadzerStanu.listaDostepnychMiejscParkingowych;
        System.out.println("---- DOSTEPNE MIEJSCA PARKINGOWE -----");
        for (int i = 0; i <listaDostepnychMiejscParkingowych.size() ; i++) {
            System.out.println((i + 1) + ". " + listaDostepnychMiejscParkingowych.get(i));
        }
    }

     */
}
