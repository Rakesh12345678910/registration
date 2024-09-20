import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/otp")
public class otp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        // Generate OTP
        String otp = generateOtp();

        // Store OTP in the database
        if (storeOtpInDatabase(email, otp)) {
            // Send OTP to email
            if (sendOtpEmail(email, otp)) {
                response.getWriter().print("OTP sent to your email.");
                request.getRequestDispatcher("verfyotp.jsp").forward(request, response);
            } else {
                response.getWriter().print("Failed to send OTP email.");
            }
        } else {
            response.getWriter().print("Failed to generate OTP.");
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private boolean storeOtpInDatabase(String email, String otp) {
        String dburl = "jdbc:mysql://localhost:3306/userdb55";
        String dbUname = "root";
        String dbPassword = "root";
        String dbDriver = "com.mysql.cj.jdbc.Driver";

        // Load the database driver
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        String sql = "UPDATE member SET otp = ?, otp_expiry = DATE_ADD(NOW(), INTERVAL 5 MINUTE) WHERE email = ?";
        try (Connection con = DriverManager.getConnection(dburl, dbUname, dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, otp);
            ps.setString(2, email);
            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated > 0; // Return true if OTP was successfully updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean sendOtpEmail(String email, String otp) {
        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Use your SMTP server
        props.put("mail.smtp.port", "587");

        // Create a session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tmrakesh68@gmail.com", "oelpjcmzyjduyqmh"); // Replace with your email and password
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tmrakesh68@gmail.com")); // Replace with your email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp);

            // Send the message
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            // Print detailed error message
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
            return false;
        }
    }
}

    