package net.kacemi.dao;

public class DaoImpl implements IDao {

    @Override
    public double getData() {
        System.out.println("Version base de données");
        double temp = 25;
        return temp;
    }


}
