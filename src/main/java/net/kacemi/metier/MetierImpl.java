package net.kacemi.metier;

import net.kacemi.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {

    IDao dao;

// On doit l'enlever pour faire instantation par constructeur et eviter "this.dao" is null
//    public MetierImpl() {
//
//    }

    /*
    Pour injecter un objet d'une classe qui implémente l'interface IDao pendant l'instantiation
     */
    public MetierImpl(@Qualifier("d2") IDao dao) {
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
