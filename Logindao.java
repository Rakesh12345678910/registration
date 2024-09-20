

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logindao {
    private String dburl = "jdbc:mysql://localhost:3306/userdb55";
    private String dbUname = "root";
    private String dbpassword = "root";
    private String dbDriver = "com.mysql.cj.jdbc.Driver"; // Corrected the driver class name

    // Load the database driver
    public void loadDriver(String dbDriver) {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Validate user credentials
    public boolean validateUser(String email, String hashedPassword) {
        loadDriver(dbDriver); // Load the driver
        boolean isValidUser = false;

        // SQL query to validate user
        String sql = "SELECT * FROM member WHERE email = ? AND password = ?";
        try (Connection con = DriverManager.getConnection(dburl, dbUname, dbpassword);
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                isValidUser = true; // User exists with matching email and password
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValidUser;
    }
}
