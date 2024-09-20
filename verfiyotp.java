import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/verifyOtp")
public class verfiyotp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String enteredOtp = request.getParameter("otp");

        if (verifyOtpInDatabase(email, enteredOtp)) {
            response.getWriter().print("OTP verification successful!");
            // Further actions (e.g., login or registration)
        } else {
            response.getWriter().print("Invalid or expired OTP.");
        }
    }

    private boolean verifyOtpInDatabase(String email, String otp) {
        String dburl = "jdbc:mysql://localhost:3306/userdb55";
        String dbUname = "root";
        String dbPassword = "root";
        String dbDriver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        String sql = "SELECT * FROM member WHERE email = ? AND otp = ? AND otp_expiry > NOW()";
        try (Connection con = DriverManager.getConnection(dburl, dbUname, dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, email);
            ps.setString(2, otp);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // Return true if a valid OTP exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
