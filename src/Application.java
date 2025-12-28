import database.Database;
import model.dao.DataAccessObject;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        Connection conn = Database.getConnection();

        DataAccessObject<Department> departmentDao = new DepartmentDaoJDBC(conn);
        DataAccessObject<Seller> sellerDao = new SellerDaoJDBC(conn);

//        Department dep = departmentDao.findById(1);
//        Seller seller = new Seller();
//
//        seller.setName("Yuri Teixeira");
//        seller.setEmail("yuriteixeirac@proton.me");
//        seller.setBirthDate(new Date("18/10/2007"));
//        seller.setSalary(9999.9);
//        seller.setDepartment(dep);
//
//        sellerDao.insert(seller);

        sellerDao.findAll().forEach(System.out::println);
    }
}
