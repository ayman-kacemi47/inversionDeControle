package net.kacemi.pres;

import net.kacemi.dao.DaoImpl;
import net.kacemi.dao.IDao;
import net.kacemi.metier.IMetier;
import net.kacemi.metier.MetierImpl;

public class PresStatique {
    public static void main(String[] args) {

                IDao dao = new DaoImpl();
                IMetier metier = new MetierImpl(dao);

                System.out.println("res="+metier.calcul());


            }
}

