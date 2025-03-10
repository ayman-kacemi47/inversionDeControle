package net.kacemi.ext;

import net.kacemi.dao.IDao;

public class DaoImpl2 implements IDao {

    @Override
    public double getData() {
        System.out.println("Versoin capteurs...");
        return 12.5;
    }
}
