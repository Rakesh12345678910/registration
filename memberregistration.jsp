<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration Form</title>
    <script>
        function validateName() {
            const nameInput = document.getElementById("name");
            const nameValue = nameInput.value;

            // Regular expression to allow only letters (a-z, A-Z) and spaces
            const regex = /^[a-zA-Z\s]+$/;

            if (!regex.test(nameValue)) {
                alert("Please enter only characters for the name.");
                nameInput.focus();
                return false; // Prevent form submission
            }
            return true; // Allow form submission
        }

        function setMaxDate() {
            const today = new Date();
            const formattedDate = today.toISOString().split('T')[0];
            document.getElementById("dob").setAttribute("max", formattedDate);
        }

        window.onload = setMaxDate; // Set max date on page load
    </script>
</head>
<body>
    <h1>User Registration Form</h1>
    <form action="Register" method="post" onsubmit="return validateName();">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
         
        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" required><br><br>
      
        <label for="aadhar">Aadhar Number:</label>
        <input type="text" id="aadhar" name="aadhar" required pattern="\d{12}" title="Aadhar number should be 12 digits"><br><br>

        <label for="mob">Mobile Number:</label>
        <input type="text" id="mob" name="mob" required pattern="\d{10}" title="Mobile number should be 10 digits"><br><br>

        <input type="submit" value="Submit">
    </form>
</body>
</html>
