<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subjects - Learning Platform</title>
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
        h1 {
            color: #333;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 15px;
        }
        h2 {
            color: #333;
            margin-top: 30px;
            margin-bottom: 15px;
        }
        .subjects-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }
        .subject-card {
            background: white;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            transition: all 0.3s ease;
            cursor: pointer;
        }
        .subject-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.15);
            border-color: #4CAF50;
        }
        .subject-card.completed {
            background-color: #f0f8f0;
            border-color: #4CAF50;
        }
        .subject-card h3 {
            margin: 0 0 10px 0;
            color: #333;
            font-size: 18px;
        }
        .subject-card p {
            color: #666;
            font-size: 14px;
            margin: 10px 0;
            line-height: 1.5;
        }
        .subject-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid #e0e0e0;
        }
        .points-badge {
            background-color: #4CAF50;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-badge {
            background-color: #2196F3;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-badge.completed {
            background-color: #4CAF50;
        }
        .btn-learn {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .btn-learn:hover {
            background-color: #1976D2;
        }
        .btn-learn:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #999;
        }
        .empty-state h3 {
            margin: 0 0 10px 0;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/subject" class="active">Subjects</a>
        <a href="${pageContext.request.contextPath}/game">Game</a>
        <a href="${pageContext.request.contextPath}/shop">Shop</a>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
    </nav>

    <div class="container">
        <h1>📚 Learning Subjects</h1>

        <!-- Available Subjects -->
        <h2>Available to Learn</h2>
        <c:if test="${empty availableSubjects}">
            <div class="empty-state">
                <h3>No available subjects</h3>
                <p>You have completed all available subjects! Check back later for new ones.</p>
            </div>
        </c:if>

        <div class="subjects-grid">
            <c:forEach var="subject" items="${availableSubjects}">
                <div class="subject-card">
                    <h3>${subject.subjectName}</h3>
                    <p>${subject.description}</p>
                    <div class="subject-meta">
                        <span class="points-badge">+${subject.points} pts</span>
                        <button class="btn-learn" onclick="learnSubject(${subject.subjectId})">
                            Start Learning
                        </button>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Completed Subjects -->
        <h2>Completed Subjects</h2>
        <c:if test="${empty completedSubjects}">
            <div class="empty-state">
                <h3>No completed subjects yet</h3>
                <p>Start learning to complete subjects!</p>
            </div>
        </c:if>

        <div class="subjects-grid">
            <c:forEach var="subject" items="${completedSubjects}">
                <div class="subject-card completed">
                    <h3>${subject.subjectName}</h3>
                    <p>${subject.description}</p>
                    <div class="subject-meta">
                        <span class="points-badge">+${subject.points} pts</span>
                        <span class="status-badge completed">✓ Completed</span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script>
        function learnSubject(subjectId) {
            // Placeholder for learning functionality
            // Will be linked to actual learning module
            alert('Learning module for subject ' + subjectId + ' will open here');
            // window.location.href = '${pageContext.request.contextPath}/learn?subjectId=' + subjectId;
        }
    </script>
</body>
</html>
