import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.dao.factory.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        SellerDao sellerDao = DaoFactory.createSellerDao();

        // === TEST -> INSERT ===
        Seller seller = new Seller();

        seller.setName("Yuri Teixeira");
        seller.setEmail("yuriteixeirac@proton.me");
        seller.setBirthDate(new Date(2007, 10, 18));
        seller.setSalary(9999.0);

        seller.setDepartment(departmentDao.findById(1));

        sellerDao.insert(seller);

        // === TEST -> UPDATE ===
        Seller updateSeller = new Seller();
        updateSeller.setId(26); // Latest id to the table
        updateSeller.setName("Yuri Teixeira de Carvalho");
        updateSeller.setEmail("yuriteixeirac@gmail.com");
        updateSeller.setSalary(15000.0);
        updateSeller.setBirthDate(new Date(2007, 10, 18));

        updateSeller.setDepartment(departmentDao.findById(1));

        sellerDao.update(updateSeller);

        // === TEST -> FETCH BY ID ===
        Seller findSeller = sellerDao.findById(25);
        System.out.println(findSeller);

        // === TEST -> FETCH ALL ===
        List<Seller> sellers = sellerDao.findAll();
        sellers.forEach(System.out::println);

        // === TEST -> FETCH BY DEPARTMENT ===
        Department department = departmentDao.findById(1);

        List<Seller> sellersByDepartment = sellerDao.findByDepartment(department);
        sellersByDepartment.forEach(System.out::println);

        // === TEST -> DELETE ===
        sellerDao.deleteById(27);
    }
}
