import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registerdao {
    private String dburl = "jdbc:mysql://localhost:3306/userdb55";
    private String dbUname = "root";
    private String dbPassword = "root";
    private String dbDriver = "com.mysql.cj.jdbc.Driver"; 

    // Load the database driver
    public void loadDriver() {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Get a connection to the database
    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(dburl, dbUname, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // Insert a member into the database
    public String insert(member member) {
        loadDriver(); 
        Connection con = getConnection(); 
        String result = "Data entered successfully"; 

        String sql = "INSERT INTO member (name, email, password, dob, aadhar, mob) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getPassword()); // Corrected method call
            ps.setString(4, member.getDob());
            ps.setString(5, member.getAadhar());
            ps.setString(6, member.getMob());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                result = "Data not entered";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Data not entered";
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
