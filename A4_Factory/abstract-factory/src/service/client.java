package service;

import bean.Computer;
import bean.Staff;
import dao.ComputerDao;
import dao.ComputerFactory;
import dao.StaffDao;
import dao.StaffFactory;
import singleton.DaoFactoryImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

import static singleton.DaoFactoryImpl.getInstance;

public class client {
    public static void main(String[] args) {

        StaffFactory staffFactory = new StaffFactory();
        ComputerFactory computerFactory = new ComputerFactory();

        StaffDao staffDao = staffFactory.createStaffDao(1);
        ComputerDao computerDao = computerFactory.createComputerDao(1);

        test(staffDao, computerDao);
        DaoFactoryImpl d1 = getInstance();
        d1.createComputerDao();

    }

    public static void test(StaffDao staffDao, ComputerDao computerDao) {
        Scanner input = new Scanner(System.in);
        int op = -1;
        do {
            try {
                op = input.nextInt();
                switch (op) {
                    case 1:
                        staffDao.insertStaff(new Staff());
                        break;
                    case 2:
                        staffDao.updateStaff(1);
                        break;
                    case 3:
                        staffDao.deleteStaff(1);
                        break;
                    case 4:
                        computerDao.insertComputer(new Computer(1));
                        break;
                    case 5:
                        computerDao.updateComputer(1);
                        break;
                    case 6:
                        computerDao.deleteComputer(1);
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Exception " + e);
            }
        } while (op != 0);
    }
}
