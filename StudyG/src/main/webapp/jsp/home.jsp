<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Learning Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }
        .navbar {
            background-color: #2c3e50;
            padding: 0;
            margin: 0;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .navbar a, .navbar span {
            color: white;
            padding: 15px 20px;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
        }
        .navbar a:hover {
            background-color: #34495e;
        }
        .navbar a.active {
            background-color: #4CAF50;
        }
        .navbar .logout-btn {
            float: right;
            background-color: #e74c3c;
        }
        .navbar .logout-btn:hover {
            background-color: #c0392b;
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 15px;
        }
        .user-info {
            display: flex;
            gap: 30px;
            font-size: 14px;
        }
        .info-item {
            display: flex;
            flex-direction: column;
        }
        .info-label {
            color: #666;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .info-value {
            color: #4CAF50;
            font-size: 18px;
            font-weight: bold;
        }
        .welcome-message {
            font-size: 24px;
            color: #333;
            margin-bottom: 20px;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .stat-card h3 {
            margin: 0 0 10px 0;
            font-size: 14px;
            opacity: 0.9;
        }
        .stat-card .value {
            font-size: 28px;
            font-weight: bold;
            margin: 0;
        }
        .action-buttons {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 15px;
        }
        .action-btn {
            padding: 15px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
            transition: background-color 0.3s;
            display: inline-block;
        }
        .action-btn:hover {
            background-color: #45a049;
        }
        .action-btn.secondary {
            background-color: #3498db;
        }
        .action-btn.secondary:hover {
            background-color: #2980b9;
        }
        .action-btn.shop {
            background-color: #f39c12;
        }
        .action-btn.shop:hover {
            background-color: #e67e22;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/home" class="active">Home</a>
        <a href="${pageContext.request.contextPath}/subject">Subjects</a>
        <a href="${pageContext.request.contextPath}/game">Game</a>
        <a href="${pageContext.request.contextPath}/shop">Shop</a>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
    </nav>

    <div class="container">
        <div class="header">
            <h1 class="welcome-message">Welcome, ${user.username}!</h1>
            <div class="user-info">
                <div class="info-item">
                    <span class="info-label">Total Points</span>
                    <span class="info-value">${user.totalPoints}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">Game Tickets</span>
                    <span class="info-value">${user.gameTickets}</span>
                </div>
            </div>
        </div>

        <div class="stats-grid">
            <div class="stat-card">
                <h3>Points to Next Ticket</h3>
                <p class="value">${(100 - (user.totalPoints % 100)) % 100}</p>
            </div>
            <div class="stat-card">
                <h3>Game Tickets Available</h3>
                <p class="value">${user.gameTickets}</p>
            </div>
            <div class="stat-card">
                <h3>Profile Level</h3>
                <p class="value">${(user.totalPoints / 500) + 1}</p>
            </div>
        </div>

        <div style="margin-bottom: 30px;">
            <h2>Quick Actions</h2>
            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/subject" class="action-btn">
                    📚 Learn Subjects
                </a>
                <a href="${pageContext.request.contextPath}/game" class="action-btn secondary">
                    🎮 Play Mystery Box
                </a>
                <a href="${pageContext.request.contextPath}/shop" class="action-btn shop">
                    🛒 Visit Shop
                </a>
            </div>
        </div>

        <div style="background-color: #f9f9f9; padding: 15px; border-left: 4px solid #4CAF50; border-radius: 4px;">
            <h3 style="margin-top: 0;">Tips:</h3>
            <ul>
                <li>Complete subjects to earn points</li>
                <li>Collect 100 points to get 1 game ticket</li>
                <li>Play Mystery Box to win rewards</li>
                <li>Use points to purchase decorations in the shop</li>
                <li>Weekly challenges for completed subjects coming soon!</li>
            </ul>
        </div>
    </div>
</body>
</html>
