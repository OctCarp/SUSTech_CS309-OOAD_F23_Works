package singleton;

import abstractFactory.DaoFactory;
import dao.ComputerDao;
import dao.StaffDao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class DaoFactoryImpl implements DaoFactory {
    private static DaoFactoryImpl instance = null;
    private final Class<?> staffDaoClass;
    private final Class<?> computerDaoClass;

    public static synchronized DaoFactoryImpl getInstance() {
        if (instance == null) {
            instance = new DaoFactoryImpl();
        }
        return instance;
    }

    private DaoFactoryImpl() {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("singleton/resources.properties"));
            prop.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            staffDaoClass = Class.forName(prop.getProperty("StaffDao"));
            computerDaoClass = Class.forName(prop.getProperty("ComputerDao"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StaffDao createStaffDao() {
        try {
            return (StaffDao) staffDaoClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ComputerDao createComputerDao() {
        try {
            return (ComputerDao) computerDaoClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}