package Utilites;
import Obiekty.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenadzerStanu {

    public static List<Osoba> listaOsob = new ArrayList<>();
    public static List<Mieszkanie> listaMieszkan = new ArrayList<>();
    public static List<MiejsceParkingowe> listaMiejscParkingowych = new ArrayList<>();


    public static ArrayList<Osoba> stworzListeNajemcow(){
        ArrayList<Osoba> listaNieNajemcow = new ArrayList<>();
        for (int i = 0; i <listaOsob.size() ; i++) {
            if(!listaOsob.get(i).czyNajemca()){
                continue;
            }
            listaNieNajemcow.add(listaOsob.get(i));
        }
        return listaNieNajemcow;
    }
    public static ArrayList<Mieszkanie> stworzListeDostepnychMieszkan(){
        ArrayList<Mieszkanie> listaDostepnychMieszkan = new ArrayList<>();
        for (int i = 0; i <listaMieszkan.size() ; i++) {
            if(listaMieszkan.get(i).czyWynajete == true){
                continue;
            }
            listaDostepnychMieszkan.add(listaMieszkan.get(i));
        }
        return  listaDostepnychMieszkan;
    }

    public static ArrayList<MiejsceParkingowe> stworzListeDostepnychMiejscParkingowych(){
        ArrayList<MiejsceParkingowe> listaDostepnychMiejscParkingowych = new ArrayList<>();
        for (int i = 0; i <listaMiejscParkingowych.size() ; i++) {
            if(listaMiejscParkingowych.get(i).czyWynajete == true){
                continue;
            }
            listaDostepnychMiejscParkingowych.add(listaMiejscParkingowych.get(i));
        }
        return listaDostepnychMiejscParkingowych;
    }
}
