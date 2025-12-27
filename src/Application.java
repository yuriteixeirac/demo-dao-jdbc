import database.Database;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        Connection conn = Database.getConnection();
        Database.closeConnection();
    }
}
