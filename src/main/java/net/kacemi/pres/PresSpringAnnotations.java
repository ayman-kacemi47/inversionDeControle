package net.kacemi.pres;

import net.kacemi.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotations {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.kacemi");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("RES= "+metier.calcul());
    }
}
