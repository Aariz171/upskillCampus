# HRMS - Human Resource Management System
Console-based Java application for managing employees, attendance, and leaves.

## Default Login
- Username: admin
- Password: admin123

## How to Run

### Option 1 - Manual Compile (No Maven)
```
# Windows
cd HRMS
mkdir out
for /r src %f in (*.java) do javac -d out "%f"
java -cp out com.hrms.main.Main

# Mac/Linux
cd HRMS
find src -name "*.java" | xargs javac -d out
java -cp out com.hrms.main.Main
```

### Option 2 - Maven
```
mvn clean package
java -jar target/hrms.jar
```

## Features
- Secure login with SHA-256 password hashing
- Employee CRUD (Add, View, Update, Delete)
- Attendance tracking (Present / Absent / On Leave)
- Leave management (Submit, Approve, Reject)
- Employee search by name, ID, or department
- Report generation saved to reports/ folder
- All data persisted to data/ folder (no database needed)

## Project Structure
```
HRMS/
├── src/com/hrms/
│   ├── auth/          AuthService.java
│   ├── main/          Main.java
│   ├── models/        Employee, Admin, Attendance, LeaveRequest + enums
│   ├── repositories/  EmployeeRepository, AttendanceRepository, LeaveRepository
│   ├── services/      EmployeeService, AttendanceService, LeaveService, ReportService
│   ├── ui/            MainMenu, EmployeeMenu, AttendanceMenu, LeaveMenu, SearchMenu, ReportMenu
│   └── utils/         ConsoleUtils, FileHandler, HashUtils, HRMSException, ValidationUtils
├── data/              Auto-created - stores .txt data files
├── reports/           Auto-created - stores generated reports
└── pom.xml
```
