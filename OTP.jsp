<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate OTP</title>
</head>
<body>
    <h1>Generate OTP</h1>
    <form action="otp" method="post">
        <label for="email">Enter your Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <input type="submit" value="Send OTP">
    </form>
    <p>Already have an OTP? <a href="verifyOtp.jsp">Verify here</a>.</p>
</body>
</html>
