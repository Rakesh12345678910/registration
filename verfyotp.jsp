<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>verfy_otp</title>
</head>
<body>
<form action="verifyOtp" method="post">
    <input type="email" name="email" placeholder="Enter your email" required>
    <input type="text" name="otp" placeholder="Enter your OTP" required>
    <button type="submit">Verify OTP</button>
</form>
</body>
</html>