package Obiekty;

import Utilites.Zegar;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UmowaMieszkanie extends Umowa{

    List <Osoba> listaDomotorow;

    public UmowaMieszkanie(Mieszkanie mieszkanie, LocalDate poczatekNajmu, String pesel, int dlugoscNajmu){
        super(mieszkanie, poczatekNajmu, pesel, dlugoscNajmu);
        this.listaDomotorow = new ArrayList<>();
    }

    public String toString(){
        return "Mieszkanie  \n" +"   Poczatek najmu: " + poczatekNajmu.toString() + "\n   Koniec najmu: " + koniecNajmu.toString() +"\n   " + pomieszczenie.toString();
    }

    public void zamelduj(Osoba os){
        listaDomotorow.add(os);
        ((Mieszkanie) pomieszczenie).dodajLokatora(os);
    }

    public void wymelduj(Osoba os){
        listaDomotorow.remove(os);
        ((Mieszkanie) pomieszczenie).usunLokatora(os);
    }


    public List<Osoba> getListaDomotorow(){
        return listaDomotorow;
    }

    public Pomieszczenie getMieszkanie(){
        return pomieszczenie;
    }

    public boolean czySieZmiesci(Przedmiot przedmiot){
        return pomieszczenie.powierzchniaUzytkowa > przedmiot.getObjetosc();
    }

}
