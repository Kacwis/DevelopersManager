package Pojazdy;
import Obiekty.Pojazd;

public class SamochodMiejski extends Pojazd{

    double pojemnoscSilnika;
    int ileDrzwi;

    public SamochodMiejski(double wielkosc, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, int ileDrzwi) {
        super(wielkosc, nazwa, typPojazdu, typSilnika);
        this.pojemnoscSilnika = pojemnoscSilnika;
        this.ileDrzwi = ileDrzwi;
    }

    public SamochodMiejski(double x, double y, double z, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, int ileDrzwi) {
       this(x * y * z, nazwa, typPojazdu, typSilnika, pojemnoscSilnika, ileDrzwi);
    }
}
