package Pojazdy;
import Obiekty.Pojazd;

public class Lodz extends Pojazd {

    String rodzajNapedu;

    public Lodz(double wielkosc, String nazwa, String typPojazdu, String typSilnika, String rodzajNapedu) {
        super(wielkosc, nazwa, typPojazdu, typSilnika);
        this.rodzajNapedu = rodzajNapedu;
    }

    public Lodz(double x, double y,double z, String nazwa, String typPojazdu, String typSilnika, String rodzajNapedu) {
        this(x * y * z, nazwa, typPojazdu, typSilnika, rodzajNapedu);

    }
}
