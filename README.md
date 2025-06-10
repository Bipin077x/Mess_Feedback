# MessFeedbackIO

MessFeedbackIO is a simple Java application for managing user feedback, ratings, and votes for a mess (cafeteria) system. It uses plain text files for data storage and provides basic user registration, login, and feedback management features.

## Team Members

- **Bipin Nayak** : 24SCSE1180206 (admin)
- **Deepak Singh** : 24SCSE1180234
- **MD Dilkhush** : 24SCSE1180481
- **Neeraj Rai** : 24SCSE1180566

## Features

- User registration and login
- Submit and view feedback
- Rate and vote on feedback
- Simple file-based storage (no database required)

## Project Structure

```
MessFeedbackIO/
├── out/                # Compiled .class files
├── src/                # Source code
│   ├── dao/            # Data access objects (e.g., UserDAO.java)
│   ├── models/         # Data models (e.g., User.java)
│   ├── Root/           # Main entry point (Main.java)
│   ├── ui/             # User interface classes
│   └── util/           # Utility classes (e.g., FileUtil.java)
├── users.txt           # User data
├── feedbacks.txt       # Feedback data
├── ratings.txt         # Ratings data
├── votes.txt           # Votes data
├── summary.txt         # Summary data
├── .gitignore
└── MessFeedbackIO.iml
```

## Getting Started

### Prerequisites

- Java JDK 8 or higher

### Compile

Open a terminal in the `src` directory and run:

```sh
javac -d ../out Root/Main.java dao/*.java models/*.java ui/*.java util/*.java
```

### Run

Change to the `out` directory and run the main class:

```sh
cd ../out
java Root.Main
```

## Usage

- Register a new user or log in with existing credentials.
- Submit feedback, rate, and vote as per the menu options in the application.

## Notes

- All data is stored in plain text files in the project root.
- No external dependencies are required.

## License

This project is for educational purposes.
