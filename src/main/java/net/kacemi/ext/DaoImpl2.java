package net.kacemi.ext;

import net.kacemi.dao.IDao;
import org.springframework.stereotype.Component;


@Component("d2")
public class DaoImpl2 implements IDao {

    @Override
    public double getData() {
        System.out.println("Version capteurs...");
        return 12.5;
    }
}
