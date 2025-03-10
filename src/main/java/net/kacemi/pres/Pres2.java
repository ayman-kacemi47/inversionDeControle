package net.kacemi.pres;

import net.kacemi.dao.IDao;
import net.kacemi.metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Scanner sc = new Scanner(new File("config.txt"));
        //lire la classe dao à partir du fichier config
        String daoClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        Object dao = (IDao) cDao.newInstance();

        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

        System.out.println("using config.txt");
        System.out.println("res="+metier.calcul());
    }
}
