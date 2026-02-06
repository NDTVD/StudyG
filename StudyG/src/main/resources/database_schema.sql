-- Learning Platform Database Schema for MSSQL
-- Create database if not exists
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'LearningPlatform')
CREATE DATABASE LearningPlatform;

USE LearningPlatform;

-- Users Table
IF OBJECT_ID('Users', 'U') IS NOT NULL DROP TABLE Users;
CREATE TABLE Users (
    userId INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(MAX) NOT NULL,
    totalPoints INT DEFAULT 0,
    gameTickets INT DEFAULT 0,
    createdAt DATETIME DEFAULT GETDATE(),
    lastLogin DATETIME,
    isActive BIT DEFAULT 1
);

-- Subjects Table
IF OBJECT_ID('Subjects', 'U') IS NOT NULL DROP TABLE Subjects;
CREATE TABLE Subjects (
    subjectId INT PRIMARY KEY IDENTITY(1,1),
    subjectName NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX),
    points INT NOT NULL DEFAULT 10,
    createdAt DATETIME DEFAULT GETDATE(),
    isActive BIT DEFAULT 1
);

-- UserSubjectProgress Table (tracks completed subjects)
IF OBJECT_ID('UserSubjectProgress', 'U') IS NOT NULL DROP TABLE UserSubjectProgress;
CREATE TABLE UserSubjectProgress (
    progressId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    subjectId INT NOT NULL,
    completed BIT DEFAULT 0,
    completedDate DATETIME,
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId),
    UNIQUE(userId, subjectId)
);

-- Tests Table (records all test attempts)
IF OBJECT_ID('Tests', 'U') IS NOT NULL DROP TABLE Tests;
CREATE TABLE Tests (
    testId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    subjectId INT NOT NULL,
    section INT NOT NULL,
    score INT,
    passed BIT DEFAULT 0,
    testDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId)
);

-- ShopItems Table
IF OBJECT_ID('ShopItems', 'U') IS NOT NULL DROP TABLE ShopItems;
CREATE TABLE ShopItems (
    itemId INT PRIMARY KEY IDENTITY(1,1),
    itemName NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX),
    price INT NOT NULL,
    itemType NVARCHAR(50) NOT NULL, -- GAME_TICKET or PROFILE_DECORATION
    imageUrl NVARCHAR(MAX),
    available BIT DEFAULT 1,
    createdAt DATETIME DEFAULT GETDATE()
);

-- UserPurchases Table (tracks bought decorations)
IF OBJECT_ID('UserPurchases', 'U') IS NOT NULL DROP TABLE UserPurchases;
CREATE TABLE UserPurchases (
    purchaseId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    itemId INT NOT NULL,
    purchaseDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (itemId) REFERENCES ShopItems(itemId)
);

-- GameTickets Table (tracks ticket transactions)
IF OBJECT_ID('GameTickets', 'U') IS NOT NULL DROP TABLE GameTickets;
CREATE TABLE GameTickets (
    ticketId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    quantity INT NOT NULL,
    acquiredDate DATETIME DEFAULT GETDATE(),
    source NVARCHAR(50), -- FROM_TEST, FROM_PURCHASE, FROM_GAME
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

-- Weekly Review Questions Table
IF OBJECT_ID('WeeklyReviews', 'U') IS NOT NULL DROP TABLE WeeklyReviews;
CREATE TABLE WeeklyReviews (
    reviewId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    subjectId INT NOT NULL,
    reviewDate DATETIME DEFAULT GETDATE(),
    passed BIT DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId)
);

-- Insert sample data
-- Sample subjects
INSERT INTO Subjects (subjectName, description, points) VALUES
('Introduction to Python', 'Learn the basics of Python programming', 50),
('Web Development with HTML/CSS', 'Master frontend development fundamentals', 60),
('JavaScript Fundamentals', 'Deep dive into JavaScript language', 55),
('Database Design with SQL', 'Learn how to design and query databases', 70),
('Object-Oriented Programming', 'Understand OOP concepts and design patterns', 75),
('RESTful API Development', 'Build scalable REST APIs', 80),
('Cloud Computing with AWS', 'Deploy applications on cloud', 85),
('Mobile App Development', 'Create mobile applications', 90);

-- Sample shop items
INSERT INTO ShopItems (itemName, description, price, itemType, available) VALUES
('Game Ticket Bundle', 'Get 5 game tickets to play Mystery Box games', 50, 'GAME_TICKET', 1),
('Golden Crown', 'Show your excellence with this golden crown decoration', 100, 'PROFILE_DECORATION', 1),
('Shiny Badge', 'A brilliant badge to showcase your achievements', 80, 'PROFILE_DECORATION', 1),
('Rainbow Effect', 'Add a magical rainbow effect to your profile background', 150, 'PROFILE_DECORATION', 1),
('Diamond Frame', 'Exclusive diamond frame for your profile picture', 200, 'PROFILE_DECORATION', 1),
('Premium Ticket Pack', '10 premium game tickets with bonus rewards', 90, 'GAME_TICKET', 1),
('Platinum Badge', 'Ultra rare platinum badge for elite learners', 300, 'PROFILE_DECORATION', 1),
('Mega Ticket Bundle', '25 game tickets super pack', 200, 'GAME_TICKET', 1);

-- Create indexes for better performance
CREATE INDEX idx_user_subject ON UserSubjectProgress(userId, subjectId);
CREATE INDEX idx_test_user ON Tests(userId);
CREATE INDEX idx_purchase_user ON UserPurchases(userId);
CREATE INDEX idx_review_user ON WeeklyReviews(userId);

PRINT 'Database schema created successfully!';
