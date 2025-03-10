package net.kacemi.metier;

import net.kacemi.dao.IDao;

public class MetierImpl implements IMetier {
    IDao dao;

    public MetierImpl() {

    }

    /*
    Pour injecter un objet d'une classe qui implémente l'interface IDao pendant l'instantiation
     */
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    /*
    Pour injecter un objet d'une classe qui implémente l'interface IDao
     */
    public void setDao(IDao dao){
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        return (t*Math.PI/180.0 + Math.atan(t))*100/((Math.random()+0.1)*50);
    }
}
