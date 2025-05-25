Hostel Mess Feedback System (Java Console-Based)

Project Description
This is a console-based Java project for collecting and managing feedback on hostel mess services. 
The system allows students to log in, submit feedback and vote on daily meals, and admins to view reports. 
All data is stored using file I/O .

Features
- Student and Admin login
- Submit textual feedback on meals (Breakfast, Lunch, Dinner)
- Vote on meal quality (Good, Average, Poor)
- Admin can view vote summary reports
- File-based data storage using .txt files

Technologies Used
- Java (JDK 8+)
- File I/O (BufferedReader, PrintWriter, etc.)
- No GUI, No database

Project Structure
MessFeedbackSystem/
├── model/
│   ├── User.java
│   ├── Feedback.java
│   └── Vote.java
├── dao/
│   ├── UserDAO.java
│   ├── FeedbackDAO.java
│   └── VoteDAO.java
├── ui/
│   └── FeedbackMenu.java
├── util/
│   └── FileUtil.java
└── Main.java

How to Run
1. Compile all .java files:
   javac Main.java
2. Run the main class:
   java Main

Author
- Bipin Nayak
- B.Tech CSE - Semester 2
