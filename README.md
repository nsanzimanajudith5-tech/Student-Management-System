# JAVA - Student Management System

## 1. Project Explanation

I built a console-based Student Management System in Java that allows users to interact with student records through a menu.

The system stores each student with:
- ID (int)
- Name (String)
- Age (int)
- Courses with marks (Map<String, Double>)
- Skills (List<String>)

The project demonstrates practical use of Java Collections Framework and Java Stream API as required.

### What I Implemented (5 Tasks)

**Task 1 - Registration & Display:**
I used ArrayList to store all students, HashMap for courses, and ArrayList for skills. User can register new students and display all.

**Task 2 - Searching & Filtering:**
I used Stream API filter() to:
- Search student by ID
- Filter passing students (average >=65)
- Filter failing students (average <65)
- Search by name (contains, case-insensitive)
- Search by course name

**Task 3 - Sorting & Courses:**
I used Stream sorted(), flatMap(), distinct():
- Sort students by name using Comparator.comparing()
- Sort by average marks using comparingDouble().reversed()
- Display all courses (with duplicates) using flatMap
- Display unique courses using distinct() and sorted()

**Task 4 - Skills & Statistics:**
- Display unique skills using flatMap + distinct()
- Group students by course using HashMap and computeIfAbsent
- Performance statistics for each student using DoubleSummaryStatistics (average, max, min, sum) and count passing students

**Task 5 - Ranking & Bonus Marks:**
- Best performing student using Stream max()
- Top 3 students using sorted() + limit(3)
- Apply Bonus Marks: If average > 50, add +10 to each course mark (capped at 100) using replaceAll() and track applied IDs with HashSet to prevent double bonus
- Final ranking using sorted() reversed

### Collections Used
- List, ArrayList, Map, HashMap, Set, HashSet, Scanner

### Stream API Methods Used
- stream(), filter(), map(), flatMap(), sorted(), distinct(), collect(), findFirst(), max(), limit(), summaryStatistics(), replaceAll(), anyMatch(), computeIfAbsent()

## 2. How to Run

### Requirements
- Java JDK 17 or higher installed
- Terminal or VS Code

### Steps

**1. Clone or Download**
```bash
git clone https://github.com/YOUR_USERNAME/Student-Management-System.git
cd Student-Management-System# JAVA - Student Management System

