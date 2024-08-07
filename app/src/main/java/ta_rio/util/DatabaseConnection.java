package ta_rio.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private Connection databaseConnect;
    public String url = "jdbc:mysql://206.189.45.23:3306/spk_topsis_1";
    public String username = "root";
    public String password = "P@ssw0rd";

    public Connection connect() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Gagal koneksi: " + ex);
        }

        try {
            databaseConnect = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Gagal koneksi Database: " + ex);
        }

        return databaseConnect;
    }
}
