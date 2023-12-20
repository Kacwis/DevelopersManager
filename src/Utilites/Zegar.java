package Utilites;
import Obiekty.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class Zegar {

    public static Zegar INSTANCE = new Zegar();
    Thread zegar;
    Thread sprawdzanieUmow;
    LocalDate czasGlobalny;
    boolean czyDzialaZegar;

    public static Zegar getInstance() {
        return INSTANCE;
    }


    private Zegar() {
        this.sprawdzanieUmow = new Thread(new Runnable() {
            @Override
            public void run() {
                czyDzialaZegar = true;
                ArrayList<Osoba> listaNajemcow = MenadzerStanu.stworzListeNajemcow();
                while (czyDzialaZegar) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < listaNajemcow.size(); i++) {
                        Osoba mieszkaniec = listaNajemcow.get(i);
                        if (mieszkaniec.getListaUmow().isEmpty())
                            continue;
                        for (int j = 0; j < mieszkaniec.getListaUmow().size(); j++) {
                            Umowa umowa = mieszkaniec.getListaUmow().get(j);
                            if (umowa.czyNadalTrwaNajem(czasGlobalny)) {
                                continue;
                            }
                            umowa.wezwanieDoZaplaty = new File(umowa.getPomieszczenie().toString() + " ZADLUZONE");
                            if (umowa.czyNadalZadluzony(czasGlobalny)) {
                                continue;
                            }
                            umowa.wezwanieDoZaplaty = null;
                            umowa.pesel = null;
                            mieszkanieCzyMiejsceParkingowe(umowa, mieszkaniec);
                        }
                    }
                }
            }
        });

        this.zegar = new Thread(new Runnable() {
            @Override
            public void run() {
                while(czyDzialaZegar){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    czasGlobalny = czasGlobalny.plusDays(1);
                }
            }
        });
    }

    public void zegarStart(LocalDate dataStartu){
        czyDzialaZegar = true;
        zegar.start();
        sprawdzanieUmow.start();
        this.czasGlobalny = dataStartu;
    }

    public void zegarStop(){
        czyDzialaZegar = false;
    }

    public LocalDate getCzasGlobalny() {
        return czasGlobalny;
    }

    public void mieszkanieCzyMiejsceParkingowe(Umowa umowa, Osoba mieszkaniec) {
        if (umowa.getClass() == UmowaMiejsceParkingowe.class) {
            if (((MiejsceParkingowe) umowa.getPomieszczenie()).czyJestPojazd() == true) {
                ((MiejsceParkingowe) umowa.getPomieszczenie()).usunPojazd();
            } else {
                ((UmowaMiejsceParkingowe) umowa).getListaPrzedmiotow().clear();
                ((MiejsceParkingowe) umowa.getPomieszczenie()).getMapaPrzedmiotow().clear();
            }
        } else {

        }


    }
}




