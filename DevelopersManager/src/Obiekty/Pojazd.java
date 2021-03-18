package Obiekty;

public class Pojazd extends Przedmiot {

    String typPojazdu;
    String typSilnika;

    public Pojazd(double wielkosc , String nazwa , String typPojazdu, String typSilnika) {
        super(wielkosc, nazwa);
        this.typPojazdu = typPojazdu;
        this.typSilnika = typSilnika;
    }

    public Pojazd(double x, double y, double z , String nazwa , String typPojazdu, String typSilnika) {
        this(x*y*z, nazwa, typPojazdu, typSilnika);
    }

    public String toString(){
        return typPojazdu + " " + typPojazdu + " " + " " + nazwa;
    }
}
