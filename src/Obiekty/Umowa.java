package Obiekty;

import Utilites.Zegar;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Umowa {

    Pomieszczenie pomieszczenie;
    static final int dlugoscZadluzenia = 30;
    LocalDate poczatekNajmu;
    int miesiaceNajmu;
    LocalDate koniecNajmu;
    public File wezwanieDoZaplaty;
    public String pesel;


    public Umowa(Pomieszczenie pomieszczenie, LocalDate poczatekNajmu, String pesel, int dlugoscNajmu){
        this.poczatekNajmu = poczatekNajmu;
        this.pomieszczenie = pomieszczenie;
        this.miesiaceNajmu = dlugoscNajmu;
        this.pesel = pesel;
        this.koniecNajmu = poczatekNajmu.plusMonths(dlugoscNajmu);
    }


    public boolean czyNadalTrwaNajem(LocalDate data){
        return data.isBefore(this.koniecNajmu);
    }

    public boolean czyNadalZadluzony(LocalDate data) {
        return data.isBefore(this.koniecNajmu.plusDays(dlugoscZadluzenia));
    }

    public Pomieszczenie getPomieszczenie() {
        return pomieszczenie;
    }

    public void odnowUmowe(Pomieszczenie pomieszczenie, LocalDate data, int dlugoscWynajmu){
        this.poczatekNajmu = data;
        System.out.println("PODAJ DLUGOSC NAJMU W MIESIACACH");
        this.koniecNajmu = data.plusDays(dlugoscWynajmu);
        if(wezwanieDoZaplaty != null){
            wezwanieDoZaplaty = null;
        }
    }
}
