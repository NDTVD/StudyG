<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - StudyGame</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<h2>Login</h2>
<form action="login" method="post">
    Username: <input type="text" name="username" required/><br/>
    Password: <input type="password" name="password" required/><br/>
    <button type="submit">Login</button>
    <p style="color:red">${param.error}</p>
</form>
<p>Don't have an account? <a href="register">Register</a></p>
</body>
</html>
