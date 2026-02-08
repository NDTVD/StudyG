-- SQL schema for StudyGameWeb (MSSQL)

CREATE TABLE users (
    username VARCHAR(100) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    points INT NOT NULL DEFAULT 0,
    plays INT NOT NULL DEFAULT 0
);

CREATE TABLE learned_subjects (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    subject VARCHAR(200) NOT NULL,
    CONSTRAINT FK_learned_user FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE profile_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    item_key VARCHAR(200) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_item_user FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE shop_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    item_key VARCHAR(200) NOT NULL,
    display_name VARCHAR(200) NOT NULL,
    price INT NOT NULL
);

-- sample data
INSERT INTO users(username, password, points, plays) VALUES ('alice','pass',300,1);
INSERT INTO users(username, password, points, plays) VALUES ('bob','pass',0,0);

INSERT INTO shop_items(item_key, display_name, price) VALUES ('play_ticket','Play Ticket',100);
INSERT INTO shop_items(item_key, display_name, price) VALUES ('sticker_rare','Rare Sticker',200);
