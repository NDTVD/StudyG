<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shop - Learning Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
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
            border-bottom: 2px solid #f39c12;
            padding-bottom: 15px;
        }
        .header h1 {
            margin: 0;
            color: #333;
            flex: 1;
        }
        .points-display {
            background-color: #fff3cd;
            padding: 15px 20px;
            border-radius: 8px;
            border: 2px solid #ffc107;
            text-align: right;
        }
        .points-display .label {
            color: #666;
            font-size: 12px;
            text-transform: uppercase;
        }
        .points-display .value {
            color: #f39c12;
            font-size: 24px;
            font-weight: bold;
        }
        h2 {
            color: #333;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
            margin-top: 30px;
        }
        .items-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }
        .item-card {
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            transition: all 0.3s ease;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .item-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.15);
            border-color: #f39c12;
        }
        .item-icon {
            font-size: 48px;
            text-align: center;
            margin-bottom: 15px;
        }
        .item-card h3 {
            margin: 0 0 10px 0;
            color: #333;
            font-size: 16px;
        }
        .item-card p {
            color: #666;
            font-size: 13px;
            margin: 10px 0;
            line-height: 1.4;
        }
        .item-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid #ddd;
        }
        .price {
            background-color: #f39c12;
            color: white;
            padding: 8px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }
        .buy-btn {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .buy-btn:hover {
            background-color: #45a049;
        }
        .buy-btn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #999;
        }
        .type-badge {
            display: inline-block;
            background-color: #e7f3ff;
            color: #2196F3;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 11px;
            font-weight: bold;
            margin-bottom: 8px;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/subject">Subjects</a>
        <a href="${pageContext.request.contextPath}/game">Game</a>
        <a href="${pageContext.request.contextPath}/shop" class="active">Shop</a>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
    </nav>

    <div class="container">
        <div class="header">
            <h1>🛒 Shop</h1>
            <div class="points-display">
                <div class="label">Your Points</div>
                <div class="value">${user.totalPoints}</div>
            </div>
        </div>

        <!-- Shop Items Section -->
        <h2>Available Items</h2>
        <div class="items-grid">
            <!-- Shop items will be displayed here -->
            <!-- Placeholder items for demo -->
            <div class="item-card">
                <div class="item-icon">🎮</div>
                <span class="type-badge">Game Ticket</span>
                <h3>Game Ticket Bundle</h3>
                <p>Get 5 game tickets to play Mystery Box games</p>
                <div class="item-footer">
                    <span class="price">50 pts</span>
                    <button class="buy-btn" onclick="purchaseItem(1)">Buy</button>
                </div>
            </div>

            <div class="item-card">
                <div class="item-icon">🎨</div>
                <span class="type-badge">Decoration</span>
                <h3>Golden Crown</h3>
                <p>Show your excellence with this golden crown decoration for your profile</p>
                <div class="item-footer">
                    <span class="price">100 pts</span>
                    <button class="buy-btn" onclick="purchaseItem(2)">Buy</button>
                </div>
            </div>

            <div class="item-card">
                <div class="item-icon">⭐</div>
                <span class="type-badge">Decoration</span>
                <h3>Shiny Badge</h3>
                <p>A brilliant badge to showcase your achievements</p>
                <div class="item-footer">
                    <span class="price">80 pts</span>
                    <button class="buy-btn" onclick="purchaseItem(3)">Buy</button>
                </div>
            </div>

            <div class="item-card">
                <div class="item-icon">🎪</div>
                <span class="type-badge">Decoration</span>
                <h3>Rainbow Effect</h3>
                <p>Add a magical rainbow effect to your profile background</p>
                <div class="item-footer">
                    <span class="price">150 pts</span>
                    <button class="buy-btn" onclick="purchaseItem(4)">Buy</button>
                </div>
            </div>

            <div class="item-card">
                <div class="item-icon">💎</div>
                <span class="type-badge">Decoration</span>
                <h3>Diamond Frame</h3>
                <p>Exclusive diamond frame for your profile picture</p>
                <div class="item-footer">
                    <span class="price">200 pts</span>
                    <button class="buy-btn" onclick="purchaseItem(5)">Buy</button>
                </div>
            </div>

            <div class="item-card">
                <div class="item-icon">🚀</div>
                <span class="type-badge">Game Ticket</span>
                <h3>Premium Ticket Pack</h3>
                <p>10 premium game tickets with bonus rewards</p>
                <div class="item-footer">
                    <span class="price">90 pts</span>
                    <button class="buy-btn" onclick="purchaseItem(6)">Buy</button>
                </div>
            </div>
        </div>

        <!-- My Decorations Section -->
        <h2>My Decorations</h2>
        <div class="items-grid">
            <!-- User's purchased decorations will be displayed here -->
            <div class="empty-state">
                <p>You haven't purchased any decorations yet. Buy some to decorate your profile!</p>
            </div>
        </div>
    </div>

    <script>
        function purchaseItem(itemId) {
            alert('Purchasing item ' + itemId + ' - feature to be implemented');
            // Implementation to send purchase request to server
        }
    </script>
</body>
</html>
