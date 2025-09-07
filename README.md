Got it 👍 Let’s make your `README.md` in a **different style** — more **modern, concise, and badge-based** so it looks professional for GitHub.

Here’s a fresh version:

---

````markdown
# 🎓 Student Management System (Java + MySQL)

![Java](https://img.shields.io/badge/Java-24-orange?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-green)

A simple **console + Swing UI based Student Management System** built with  
**Java (JDBC)** and **MySQL** for learning CRUD operations and database integration.

---

## ✨ Features
- ➕ Add new student  
- 📋 View all students  
- ✏️ Update student (name, email, course, GPA)  
- ❌ Delete student  
- 🔍 Search by ID or Name  
- 🛡 Input validation (email unique, GPA 0.00–4.00)

---

## 🛠 Tech Stack
- **Language**: Java 24  
- **Database**: MySQL 8.0+  
- **Build Tool**: Maven  
- **Dependencies**: [mysql-connector-j](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j)  

---

## 🚀 Getting Started

### 1. Clone Repository
```bash
git clone https://github.com/sahiru0302/Student-Management-System-.git
cd Student-Management-System-
````

### 2. Create Database in MySQL

```sql
CREATE DATABASE studentdb2;
USE studentdb2;

CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    email  VARCHAR(100) UNIQUE,
    course VARCHAR(50),
    gpa    DECIMAL(3,2) CHECK (gpa >= 0.00 AND gpa <= 4.00)
);
```

### 3. Configure Database Connection

Update your `DatabaseConnection.java`:

```java
private static final String DB_NAME = "studentdb2";
private static final String USER = "your-username";
private static final String PASS = "your-password";
```

### 4. Run Project

```bash
mvn clean compile exec:java -Dexec.mainClass=Main
```

Or in IntelliJ IDEA → Run `Main`.

---

## 📂 Project Structure

```
src/main/java/
 ├── DatabaseConnection.java  # DB connector
 ├── StudentDAO.java          # DAO with CRUD methods
 ├── Main.java                # Console menu
 └── ui/StudentApp.java       # Swing UI (optional)
```

---

## 📸 Example Run (Console)

```
=== Student Database (JDBC + MySQL) ===

1) Add Student
2) View All
3) Update Student
4) Delete Student
5) Search by ID
6) Search by Name
7) Exit
Choose: 
```

---

## 👨‍💻 Author

* **Kalani Sewmini (S16736)**
* Repo: [Student-Management-System-](https://github.com/sahiru0302/Student-Management-System-.git)

---

## 📜 License

This project is licensed under the MIT License.

```

---

This style is **badge-based, clean sections, and professional** (like many GitHub open-source projects).  

👉 Do you also want me to create a **shorter “one-page” style** README (minimal text, just badges + quick setup)? That’s good if your lecturer only wants a summary.
```
