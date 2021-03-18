import Obiekty.*;
import Pojazdy.*;
import Utilites.*;
import Wyjatki.ProblematicTenantException;
import Wyjatki.TooManyThingsException;
import Wyjatki.TooMuchRoomsException;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MenadzerStanu.listaMieszkan.add(new Mieszkanie(40));
        MenadzerStanu.listaMieszkan.add(new Mieszkanie(120));
        MenadzerStanu.listaMieszkan.add( new Mieszkanie(5,30,20));
        MenadzerStanu.listaMiejscParkingowych.add( new MiejsceParkingowe(80));
        MenadzerStanu.listaMiejscParkingowych.add( new MiejsceParkingowe(20,5,8));
        MenadzerStanu.listaMieszkan.add( new Mieszkanie(80));
        MenadzerStanu.listaMieszkan.add( new Mieszkanie(40));
        MenadzerStanu.listaMieszkan.add(  new Mieszkanie(12,20,5));
        MenadzerStanu.listaMiejscParkingowych.add( new MiejsceParkingowe(200,100,5));
        MenadzerStanu.listaMiejscParkingowych.add( new MiejsceParkingowe(500));
        MenadzerStanu.listaMieszkan.add(new Mieszkanie(1500));
        MenadzerStanu.listaMiejscParkingowych.add( new MiejsceParkingowe(10));

        Osoba janK = new Osoba("Jan", "Kowalski", "123513423344", "Akacjowa", 5,3,1976);
        MenadzerStanu.listaOsob.add(janK);
        Osoba mateuszP = new Osoba("Mateusz", "Potocki", "114534322331", "Abasdw",5,3,1276) ;
        MenadzerStanu.listaOsob.add(mateuszP);
        Osoba radekM = new Osoba("Radek", "Morski", "123513554633", "abwusjkdw",5,3,1271) ;
        MenadzerStanu.listaOsob.add(radekM);
        Osoba janueszI = new Osoba("Janusz", "Informatyk", "123513586654", "dkasmdlmw",5,3,1926);
        MenadzerStanu.listaOsob.add(janueszI);
        Osoba michałN = new Osoba("Michał", "Nowak", "00207017520", "Koło Szkoły",6,9,1337);
        MenadzerStanu.listaOsob.add(michałN);
        Osoba janK2 = new Osoba("Jan", "Kowal", "123513541242", "dasldwp",5,3,1976);
        Zegar.INSTANCE.zegarStart(LocalDate.now());
        MenadzerStanu.listaOsob.add(janK2);

        try{
            janK.dodajMieszkanie(MenadzerStanu.listaMieszkan.get(0), 3);
        }
        catch(ProblematicTenantException e){
            System.out.println("sdwdasd");
        }
        catch(TooMuchRoomsException e){
            System.out.println("2");
        }
        InterfejsUzytkownika interfejs = new InterfejsUzytkownika();








    }
}
