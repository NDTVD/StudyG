# StudG - Study Game Web Application

A Java-based web application that gamifies learning with mystery box rewards, item collection, and progress tracking.

## Features

### 🎮 Core Gameplay
- **Mystery Box System**: 16 interactive boxes with per-box probability distributions
  - Each box has unique reward odds for points, play tickets, and rare items
  - Jackpot mechanic (1/1000 chance per real open)
  - Preview mode when out of plays (non-persistent visual preview)
  
- **Learning Module**: Study topics and take tests to earn rewards
  - Learning rewards: 30 points + play tickets + rare items
  - Test results update plays and item inventory
  
- **Reward Types**
  - **Points**: 50 points per reward
  - **Play Tickets**: Single (play:1), Double (play:2), or Jackpot (play:100)
  - **Items**: Rare stickers to collect and display

### 👤 User Management
- User registration and authentication
- Session-based login
- User profiles with inventory tracking
- Persistent data via DAO pattern (MSSQL or InMemory)

### 📦 Inventory System
- View collected items
- Item persistence in database
- Display items in user profile

## Project Structure

```
StudG/
├── src/java/com/studg/
│   ├── dao/                          # Data Access Objects
│   │   ├── UserDAO.java              # Interface
│   │   ├── InMemoryUserDAO.java      # In-memory implementation
│   │   ├── MSSQLUserDAO.java         # MSSQL implementation
│   │   └── DAOFactory.java           # Factory pattern
│   ├── model/
│   │   └── User.java                 # User model (profiles, items, stats)
│   ├── service/
│   │   ├── RewardService.java        # Interface
│   │   ├── SimpleRewardService.java  # Mystery box rewards (game)
│   │   ├── SubRewardService.java     # Interface for learning rewards
│   │   └── SimpleSubRewardService.java # Learning rewards implementation
│   └── servlet/
│       ├── LoginServlet.java
│       ├── LogoutServlet.java
│       ├── RegisterServlet.java
│       ├── GameServlet.java          # Game hub
│       ├── MysteryServlet.java       # Mystery page (checks plays first)
│       ├── OpenBoxServlet.java       # AJAX: open box endpoint
│       ├── ClearPickedServlet.java   # Clear picked boxes on session unload
│       ├── TestServlet.java          # Learning/test results
│       ├── LearnServlet.java         # Learning page
│       ├── SubjectServlet.java       # Subjects listing
│       └── ShopServlet.java          # Shop page
├── web/
│   ├── login.jsp
│   ├── register.jsp
│   ├── home.jsp
│   ├── game.jsp
│   ├── mystery.jsp                   # 16-box UI
│   ├── mystery_blocked.jsp           # Warning when plays=0
│   ├── inventory.jsp                 # Collected items
│   ├── test.jsp
│   ├── subjects.jsp
│   ├── shop.jsp
│   ├── error404.jsp
│   ├── css/
│   │   └── style.css                 # Global styles + reward color classes
│   └── WEB-INF/
│       ├── web.xml                   # Servlet mappings
│       └── classes/                  # Compiled servlets
├── sql/
│   └── schema.sql                    # Database schema
├── build.xml                         # Ant build script
└── README.md                         # This file
```

## Setup & Build

### Prerequisites
- Java 8+
- Apache Tomcat 8.5+
- Apache Ant
- MSSQL Server (optional; defaults to in-memory DAO)

### Build Instructions

```bash
cd d:\FPT\StudG

# Clean and build
ant clean build

# Deploy to Tomcat
# Copy build/web/ contents to Tomcat webapps/StudG/
```

### Database Setup (Optional - MSSQL)

1. Run [sql/schema.sql](sql/schema.sql) to create tables:
   - `users` (username, password, points, plays)
   - `learned_subjects` (user_id, subject_id)
   - `profile_items` (user_id, item_key)

2. Configure `web.xml` to use `MSSQLUserDAO` instead of `InMemoryUserDAO`

### Running Locally

```bash
# Using Tomcat
# 1. Start Tomcat server
# 2. Navigate to http://localhost:8080/StudG/

# Or use NetBeans IDE:
# Open the project and run (F6)
```

## User Workflow

### Registration & First Play
1. Click **Register** on login page
2. Create username/password (new account gets `plays=1` and `points=100`)
3. Login
4. Visit `/mystery` → click a box to consume 1 play and claim reward

### Mystery Box Gameplay

#### When plays > 0 (Real Opens)
```
POST /openbox?index=0
→ Consumes 1 play, applies reward, updates DB, returns JSON
```

**Response:**
```json
{
  "reward": "play:2",
  "itemDisplay": "🎟️ Play x2",
  "points": 150,
  "plays": 2
}
```

#### When plays = 0 (Preview Only)
```
POST /openbox
→ Generates visual preview of all 16 boxes (non-persistent)
```

**Response:**
```json
{
  "multi": true,
  "results": [
    {"reward": "points:50"},
    {"reward": "play:100"},
    {"reward": "excluded"},
    ...
  ],
  "points": 100,
  "plays": 0
}
```

### Learning & Test Rewards
1. Visit `/learn` → subjects list
2. Study a subject
3. Take test → Success grants:
   - `points:30`
   - `play:1` or `play:2`
   - Rare item (chance)

## Mystery Box Probabilities

Each of 16 boxes has its own reward distribution:

```
Box Index → [points:50%, play:1%, play:2%, item:%]
0         → [60%, 25%, 5%, 10%]
1         → [55%, 30%, 5%, 10%]
...
15        → [20%, 60%, 10%, 10%] (most likely to give plays)
```

**Jackpot:** 1/1000 chance on any real open (returns `play:100`)

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/login` | GET/POST | Login page & authentication |
| `/register` | GET/POST | Registration page & user creation |
| `/game` | GET | Game hub |
| `/mystery` | GET | Mystery box page (blocked if plays=0) |
| `/openbox` | POST | AJAX: open box (real or preview) |
| `/clearpicked` | POST | Clear picked boxes from session |
| `/inventory` | GET | View collected items |
| `/test` | GET/POST | Learning test |
| `/learn` | GET | Learning page |
| `/subjects` | GET | Subjects listing |
| `/shop` | GET | Shop page |
| `/logout` | GET | Logout & clear session |

## Session State

**Keys stored in `HttpSession`:**
- `user` (String): username
- `points` (Integer): current points
- `plays` (Integer): play tickets remaining
- `pickedBoxes` (Set<Integer>): boxes opened this session (preview tracking)

## Color Coding (Front-end)

Mystery boxes display with CSS classes based on reward:

```css
.box.jackpot     /* red - play:100 */
.box.sticker     /* yellow - items */
.box.play2       /* purple - play:2 */
.box.play1       /* blue - play:1 */
.box.points50    /* green - points:50 */
.box.none        /* gray - no reward */
.box.excluded    /* faded - already picked (preview) */
```

## File Changes Reference

### Key Recent Additions
- `OpenBoxServlet.java` → Preview logic with `checkPlays()` and `buildPreviewJson()`
- `MysteryServlet.java` → Blocks access when `plays <= 0`
- `ClearPickedServlet.java` → Clears session picked boxes
- `mystery.jsp` → 16-box UI with AJAX + multi-reveal on preview
- `inventory.jsp` → Item collection display
- `SimpleSubRewardService.java` → Learning-specific reward distribution

### Service Architecture
- **Game Rewards** → `SimpleRewardService` (mystery box with jackpot)
- **Learning Rewards** → `SimpleSubRewardService` (fixed distribution, no jackpot)

## Troubleshooting

### Error: "Cannot select box" / Error 401
- **Cause**: Session expired or not logged in
- **Fix**: Login again; check browser cookies for `JSESSIONID`

### Error on `/mystery`
- **Cause**: Database connection or DAO factory misconfigured
- **Fix**: Check `DAOFactory.java` is returning correct DAO (InMemory or MSSQL)

### Boxes don't reveal in preview
- **Cause**: `plays > 0` when expecting plays=0
- **Fix**: Open boxes in real mode to consume all plays (or set directly in DB)

### Stats don't update
- **Cause**: DAO not saving user data
- **Fix**: Verify `UserDAO.save()` is called after reward; check DB schema columns

## Testing Checklist

- [ ] Register new account → gets 1 play + 100 points
- [ ] Open 1 box → consumes play, updates stats
- [ ] With plays=0, visit `/mystery` → shows preview (non-persistent)
- [ ] Click box in preview → no stats change
- [ ] Learn and test → earn rewards correctly
- [ ] Visit `/inventory` → shows collected items
- [ ] Use `ClearPickedServlet` → resets boxes on next page load

## License

Project for educational purposes.

## Contact & Support

For issues or questions, check server logs in Tomcat `catalina.out` or browser DevTools (Network tab for HTTP responses).
