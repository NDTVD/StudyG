<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mystery Box Game - Learning Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            min-height: 100vh;
        }
        .navbar {
            background-color: rgba(0,0,0,0.3);
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
            background-color: rgba(255,255,255,0.2);
        }
        .navbar a.active {
            background-color: #4CAF50;
        }
        .navbar .logout-btn {
            float: right;
            background-color: #e74c3c;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 30px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.2);
            text-align: center;
        }
        h1 {
            color: #333;
            margin: 0 0 10px 0;
            font-size: 32px;
        }
        .game-info {
            display: flex;
            justify-content: center;
            gap: 40px;
            margin: 20px 0 40px 0;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
        }
        .info-item {
            text-align: center;
        }
        .info-label {
            color: #666;
            font-size: 12px;
            text-transform: uppercase;
            margin-bottom: 5px;
        }
        .info-value {
            color: #4CAF50;
            font-size: 24px;
            font-weight: bold;
        }
        .game-area {
            margin: 40px 0;
        }
        .mystery-box {
            width: 150px;
            height: 150px;
            margin: 20px auto;
            background: linear-gradient(135deg, #f39c12, #e67e22);
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 60px;
            cursor: pointer;
            transition: all 0.3s;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            animation: float 3s ease-in-out infinite;
        }
        .mystery-box:hover:not(:disabled) {
            transform: scale(1.05);
            box-shadow: 0 8px 20px rgba(0,0,0,0.3);
        }
        .mystery-box:active:not(:disabled) {
            transform: scale(0.95);
        }
        @keyframes float {
            0%, 100% { transform: translateY(0px); }
            50% { transform: translateY(-10px); }
        }
        .play-btn {
            background-color: #4CAF50;
            color: white;
            padding: 15px 40px;
            font-size: 18px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s;
            margin-top: 20px;
        }
        .play-btn:hover:not(:disabled) {
            background-color: #45a049;
        }
        .play-btn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
        .reward-message {
            margin: 30px 0;
            padding: 20px;
            border-radius: 8px;
            font-size: 18px;
            font-weight: bold;
            display: none;
        }
        .reward-message.show {
            display: block;
            animation: slideIn 0.5s ease-out;
        }
        .reward-message.points {
            background-color: #d4edda;
            color: #155724;
            border: 2px solid #28a745;
        }
        .reward-message.ticket {
            background-color: #cfe2ff;
            color: #084298;
            border: 2px solid #0d6efd;
        }
        .reward-message.rare {
            background-color: #fff3cd;
            color: #664d03;
            border: 2px solid #ffc107;
        }
        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        .no-tickets {
            background-color: #f8d7da;
            color: #721c24;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
            border: 1px solid #f5c6cb;
        }
        .tips {
            background-color: #e7f3ff;
            padding: 15px;
            border-left: 4px solid #2196F3;
            border-radius: 4px;
            margin-top: 30px;
            text-align: left;
        }
        .tips h3 {
            margin-top: 0;
            color: #2196F3;
        }
        .tips ul {
            margin: 0;
            padding-left: 20px;
        }
        .tips li {
            color: #333;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/subject">Subjects</a>
        <a href="${pageContext.request.contextPath}/game" class="active">Game</a>
        <a href="${pageContext.request.contextPath}/shop">Shop</a>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
    </nav>

    <div class="container">
        <h1>🎁 Mystery Box Game</h1>
        
        <div class="game-info">
            <div class="info-item">
                <div class="info-label">Game Tickets</div>
                <div class="info-value" id="ticketCount">${user.gameTickets}</div>
            </div>
            <div class="info-item">
                <div class="info-label">Total Points</div>
                <div class="info-value">${user.totalPoints}</div>
            </div>
        </div>

        <c:if test="${user.gameTickets <= 0}">
            <div class="no-tickets">
                <strong>No Game Tickets!</strong> Complete subjects or purchase tickets from the shop to play.
            </div>
        </c:if>

        <div class="game-area">
            <p>Click the box to open your mystery reward!</p>
            <div class="mystery-box" id="mysteryBox">
                📦
            </div>
            <button class="play-btn" id="playBtn" onclick="playMysteryBox()" 
                    <c:if test="${user.gameTickets <= 0}">disabled</c:if>>
                Play Mystery Box
            </button>
        </div>

        <div class="reward-message" id="rewardMessage">
            <!-- Reward message will be displayed here -->
        </div>

        <div class="tips">
            <h3>Reward Probabilities</h3>
            <ul>
                <li>🎯 40% - Win 50 points</li>
                <li>🎯 35% - Win 100 points</li>
                <li>🎮 20% - Win 1 game ticket</li>
                <li>⭐ 5% - Win a rare decoration item</li>
            </ul>
        </div>
    </div>

    <script>
        function playMysteryBox() {
            const playBtn = document.getElementById('playBtn');
            const ticketCount = ${user.gameTickets};

            if (ticketCount <= 0) {
                alert('No game tickets available!');
                return;
            }

            playBtn.disabled = true;
            const mysteryBox = document.getElementById('mysteryBox');
            mysteryBox.style.animation = 'none';
            
            // Trigger animation
            setTimeout(() => {
                mysteryBox.style.animation = 'float 0.5s infinite';
            }, 10);

            // Send request to server
            fetch('${pageContext.request.contextPath}/game', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'action=playMysteryBox'
            })
            .then(response => response.json())
            .then(data => {
                // Display reward
                showReward(data);
                
                // Update ticket count
                const newCount = ticketCount - 1;
                document.getElementById('ticketCount').textContent = newCount;
                
                playBtn.disabled = newCount <= 0;
            })
            .catch(error => {
                alert('Error playing game: ' + error);
                playBtn.disabled = false;
            });
        }

        function showReward(reward) {
            const rewardMsg = document.getElementById('rewardMessage');
            let message = '';
            let className = 'reward-message';

            if (reward.rewardType === 'POINTS') {
                message = '🎯 You won ' + reward.amount + ' points!';
                className += ' points';
            } else if (reward.rewardType === 'GAME_TICKET') {
                message = '🎮 You won ' + reward.amount + ' game ticket!';
                className += ' ticket';
            } else if (reward.rewardType === 'RARE_ITEM') {
                message = '⭐ You won a rare decoration item!';
                className += ' rare';
            }

            rewardMsg.textContent = message || reward.message;
            rewardMsg.className = className + ' show';

            setTimeout(() => {
                rewardMsg.classList.remove('show');
            }, 3000);
        }
    </script>
</body>
</html>
