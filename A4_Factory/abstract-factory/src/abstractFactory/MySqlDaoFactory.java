package abstractFactory;

import dao.ComputerDao;
import dao.StaffDao;
import dao.MysqlComputerDao;
import dao.MysqlStaffDao;

public class MySqlDaoFactory implements DaoFactory {

    @Override
    public ComputerDao createComputerDao() {
        return new MysqlComputerDao();
    }

    @Override
    public StaffDao createStaffDao() {
        return new MysqlStaffDao();
    }
}
