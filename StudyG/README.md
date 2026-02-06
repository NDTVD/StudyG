# Learning Platform Application

A comprehensive educational web application built with Java Servlets and JSP, featuring integrated game rewards, subject tracking, and a virtual shop system.

## Technology Stack

- **Frontend:** JSP, HTML5, CSS3, JavaScript
- **Backend:** Java Servlets, OOP Design Patterns
- **Database:** MSSQL Server
- **Build Tool:** Maven
- **Server:** Apache Tomcat

## Features

### 1. **User Authentication**
   - User registration with strong password validation
   - Login system with session management
   - Secure password hashing using SHA-256

### 2. **Subject Learning**
   - Browse available subjects
   - Track subject completion
   - Categorize subjects as available or completed
   - Prevent re-learning of completed subjects
   - Earn points upon subject completion

### 3. **Testing System**
   - Create tests for each subject section
   - Record test results and scores
   - Track passed/failed tests
   - Calculate points from successful tests
   - Support for weekly knowledge review

### 4. **Points System**
   - Earn points by completing subjects and tests
   - Convert 100 points to 1 game ticket
   - Display current points and tickets in user dashboard
   - Track total user points

### 5. **Mystery Box Game**
   - Play with game tickets
   - Random reward system:
     - 40% chance: 50 points
     - 35% chance: 100 points
     - 20% chance: 1 game ticket
     - 5% chance: Rare decoration item
   - Animated game interface
   - Real-time reward display

### 6. **Shop System**
   - Purchase game tickets
   - Buy profile decoration items
   - Points-based currency system
   - Track purchased decorations
   - OOP compliant with interface-based design

### 7. **Cookie-Based Subject Tracking**
   - Track recently learned subjects
   - Easy subject ranking
   - Lightweight client-side storage

## Project Structure

```
learning-platform-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/learningplatform/
│   │   │       ├── servlet/          # Web request handlers
│   │   │       │   ├── LoginServlet.java
│   │   │       │   ├── RegisterServlet.java
│   │   │       │   ├── HomeServlet.java
│   │   │       │   ├── SubjectServlet.java
│   │   │       │   ├── GameServlet.java
│   │   │       │   └── ShopServlet.java
│   │   │       ├── service/          # Business logic interfaces
│   │   │       │   ├── IUserService.java
│   │   │       │   ├── ISubjectService.java
│   │   │       │   ├── ITestService.java
│   │   │       │   ├── IShopService.java
│   │   │       │   ├── IGameService.java
│   │   │       │   └── impl/         # Service implementations
│   │   │       ├── model/            # Data models
│   │   │       │   ├── User.java
│   │   │       │   ├── Subject.java
│   │   │       │   ├── Test.java
│   │   │       │   ├── GameTicket.java
│   │   │       │   └── ShopItem.java
│   │   │       ├── util/             # Utilities
│   │   │       │   ├── DatabaseConnection.java
│   │   │       │   ├── PasswordUtils.java
│   │   │       │   └── ServletUtils.java
│   │   │       └── dao/              # Data access objects (optional)
│   │   ├── webapp/
│   │   │   ├── WEB-INF/
│   │   │   │   └── web.xml
│   │   │   ├── jsp/
│   │   │   │   ├── login.jsp
│   │   │   │   ├── register.jsp
│   │   │   │   ├── home.jsp
│   │   │   │   ├── subject.jsp
│   │   │   │   ├── game.jsp
│   │   │   │   ├── shop.jsp
│   │   │   │   ├── error404.jsp
│   │   │   │   └── error500.jsp
│   │   │   ├── css/
│   │   │   │   └── style.css
│   │   │   ├── js/
│   │   │   └── index.jsp
│   │   └── resources/
│   │       └── database_schema.sql
│   └── test/
│       └── java/
└── pom.xml
```

## Setup Instructions

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- MSSQL Server 2016 or higher
- Apache Tomcat 9+

### Step 1: Database Setup

1. Create a new database or modify connection settings:
   - Open `src/main/util/DatabaseConnection.java`
   - Update connection parameters:
     ```java
     private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=YourDBName;encrypt=true;trustServerCertificate=true";
     private static final String USER = "your_username";
     private static final String PASSWORD = "your_password";
     ```

2. Execute the database schema:
   - Run `src/main/resources/database_schema.sql` on your MSSQL Server
   - This creates all necessary tables and sample data

### Step 2: Project Build

```bash
# Navigate to project directory
cd learning-platform-app

# Build the project
mvn clean package

# The WAR file will be created at: target/learning-platform-app-1.0.war
```

### Step 3: Deployment

**Option 1: Tomcat Deployment**
```bash
# Copy WAR file to Tomcat
cp target/learning-platform-app-1.0.war $TOMCAT_HOME/webapps/

# Start Tomcat
$TOMCAT_HOME/bin/startup.sh  # Linux/Mac
# or
%TOMCAT_HOME%\bin\startup.bat  # Windows
```

**Option 2: Maven Tomcat Plugin**
```bash
mvn tomcat7:run
# Access at http://localhost:8080/learning-platform
```

### Step 4: Access the Application
- URL: `http://localhost:8080/learning-platform/`
- Default page redirects to login

## Usage

### Creating a User Account
1. Click "Register here" on login page
2. Fill in username, email, and password
3. Password must contain:
   - At least 8 characters
   - Uppercase and lowercase letters
   - At least one number
4. Submit to create account

### Learning Subjects
1. Login with your credentials
2. Navigate to "Subjects" tab
3. Click "Start Learning" on available subjects
4. Complete test to mark subject as finished
5. Earn points based on subject difficulty

### Playing Mystery Box
1. Complete subjects to earn game tickets
2. Navigate to "Game" tab
3. Click the mystery box or "Play Mystery Box" button
4. Win random rewards (points, tickets, or decorations)

### Shop
1. Navigate to "Shop" tab
2. View available items and your points
3. Click "Buy" to purchase items
4. Game ticket purchases increase your play count
5. Decoration purchases appear on your profile

## OOP Design Principles

### 1. **Dependency Inversion**
- Service interfaces (`IUserService`, `ISubjectService`, etc.)
- Implementation classes (`UserServiceImpl`, `SubjectServiceImpl`, etc.)
- Servlets depend on interfaces, not concrete implementations

### 2. **Single Responsibility**
- Each service handles one domain (User, Subject, Test, Shop, Game)
- Utility classes handle specific tasks (Password, Database, Servlet Utils)
- Models represent single entities

### 3. **Open/Closed Principle**
- Services are open for extension (new implementations)
- Closed for modification (interface contracts remain stable)

### 4. **Liskov Substitution**
- All service implementations can replace their interfaces
- Polymorphic behavior in servlet handlers

### 5. **Interface Segregation**
- Each service interface is focused and minimal
- Clients depend only on methods they use

## Database Schema

### Key Tables
- **Users**: User accounts and profile data
- **Subjects**: Available learning subjects
- **Tests**: Test results and attempts
- **UserSubjectProgress**: Tracks completion status
- **ShopItems**: Available shop items
- **UserPurchases**: Transaction history
- **GameTickets**: Ticket transactions
- **WeeklyReviews**: Knowledge review records

## API Endpoints

### Authentication
- POST `/login` - User login
- POST `/register` - User registration
- GET/POST `/logout` - User logout

### Navigation
- GET `/home` - Dashboard
- GET `/subject` - Subject listing
- POST `/subject` - View subject details
- GET `/game` - Game interface
- POST `/game` - Play mystery box
- GET `/shop` - Shop interface
- POST `/shop` - Purchase items

## Future Enhancements

1. **Learning Modules**
   - Interactive course content
   - Video integration
   - Code challenges

2. **Social Features**
   - Leaderboards
   - Friend system
   - Achievements/Badges

3. **Advanced Analytics**
   - Learning progress reports
   - Performance statistics
   - Personalized recommendations

4. **Mobile Application**
   - React Native app
   - Offline learning mode
   - Push notifications

5. **Admin Panel**
   - Subject management
   - Shop item management
   - User analytics

## Security Features

- Password hashing with SHA-256
- Session management with timeout
- Input validation and sanitization
- XSS prevention
- SQL injection prevention (prepared statements)

## Troubleshooting

### Database Connection Issues
- Check MSSQL Server is running
- Verify connection string in `DatabaseConnection.java`
- Ensure MSSQL JDBC driver is in dependencies

### Session Issues
- Clear browser cookies
- Check server session timeout settings
- Verify session configuration in `web.xml`

### Build Issues
```bash
# Update dependencies
mvn clean install

# Check Java version
java -version  # Should be 11 or higher
```

## License

This project is provided as an educational example.

## Support

For issues or questions, please refer to the code comments and documentation within each class.

---

**Developed with OOP best practices and modern Java web development standards.**
