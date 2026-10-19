import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentManagementSystem {
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Integer> bonusAppliedIds = new HashSet<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Java");
            System.out.println("\n==========================================");
            System.out.println("   STUDENT MANAGEMENT SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Register New Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Filter Passing Students (>=65)");
            System.out.println("5. Filter Failing Students (<65)");
            System.out.println("6. Search Students by Name");
            System.out.println("7. Sort Students by Name");
            System.out.println("8. Sort Students by Average Marks");
            System.out.println("9. Display All Courses");
            System.out.println("10. Display Unique Courses");
            System.out.println("11. Search Students by Course");
            System.out.println("12. Display Unique Skills");
            System.out.println("13. Group Students by Course");
            System.out.println("14. View Student Performance Statistics");
            System.out.println("15. View Best Performing Student");
            System.out.println("16. View Top 3 Students");
            System.out.println("17. Apply Bonus Marks (+10 if avg>50)");
            System.out.println("18. View Final Student Ranking");
            System.out.println("0. Exit");
             System.out.println("==========================================");
            System.out.print("Enter your choice: ");
            int choice;
            try { choice = Integer.parseInt(scanner.nextLine().trim()); }
            catch (Exception e) { System.out.println("Invalid"); continue; }
            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> displayAllStudents();
                case 3 -> searchById();
                case 4 -> filterPassing();
                case 5 -> filterFailing();
                case 6 -> searchByName();
                case 7 -> sortByName();
                case 8 -> sortByAverage();
                case 9 -> displayAllCourses();
                case 10 -> displayUniqueCourses();
                case 11 -> searchByCourse();
                case 12 -> displayUniqueSkills();
                case 13 -> groupByCourse();
                case 14 -> performanceStatistics();
                case 15 -> bestPerforming();
                case 16 -> top3Students();
                case 17 -> applyBonusMarks();
                case 18 -> finalRanking();
                case 0 -> { System.out.println("Bye"); return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static int readInt(String p) {
        while (true) {
            System.out.print(p);
            try { int v = Integer.parseInt(scanner.nextLine().trim()); if(v<0) throw new Exception(); return v; }
            catch (Exception e) { System.out.println("Invalid number"); }
        }
    }

    private static double readMark(String p) {
        while (true) {
            System.out.print(p);
            try { double v = Double.parseDouble(scanner.nextLine().trim()); if(v<0||v>100){System.out.println("0-100"); continue;} return v; }
            catch (Exception e) { System.out.println("Invalid mark"); }
        }
    }

    private static void registerStudent() {
        int id = readInt("Student ID: ");
        if (students.stream().anyMatch(s -> s.getId() == id)) { System.out.println("ID exists!"); return; }
        System.out.print("Student name: "); String name = scanner.nextLine().trim();
        int age = readInt("Age: ");
        int nc = readInt("Number of courses: ");
        Map<String, Double> courses = new HashMap<>();
        for (int i=0;i<nc;i++) {
            System.out.print("Course name "+(i+1)+": "); String cn = scanner.nextLine().trim();
            double m = readMark("Mark for "+cn+": "); courses.put(cn, m);
        }
        int ns = readInt("Number of skills: ");
        List<String> skills = new ArrayList<>();
        for (int i=0;i<ns;i++) { System.out.print("Skill "+(i+1)+": "); skills.add(scanner.nextLine().trim()); }
        students.add(new Student(id, name, age, courses, skills));
        System.out.println("Registered!");
    }

    private static void displayAllStudents() { if(students.isEmpty()) System.out.println("No students"); else students.forEach(System.out::println); }
    private static void searchById() { int id=readInt("Enter ID: "); students.stream().filter(s->s.getId()==id).findFirst().ifPresentOrElse(System.out::println, ()->System.out.println("Not found")); }
    private static void filterPassing() { students.stream().filter(s->s.getAverage()>=65).forEach(System.out::println); }
    private static void filterFailing() { students.stream().filter(s->s.getAverage()<65).forEach(System.out::println); }
    private static void searchByName() { System.out.print("Name: "); String n=scanner.nextLine().trim().toLowerCase(); students.stream().filter(s->s.getStudentName().toLowerCase().contains(n)).forEach(System.out::println); }
    private static void sortByName() { students.stream().sorted(Comparator.comparing(Student::getStudentName)).forEach(System.out::println); }
    private static void sortByAverage() { students.stream().sorted(Comparator.comparingDouble(Student::getAverage).reversed()).forEach(s->System.out.println(s.getStudentName()+" - "+String.format("%.2f",s.getAverage()))); }
    private static void displayAllCourses() { students.stream().flatMap(s->s.getCourses().keySet().stream()).forEach(System.out::println); }
    private static void displayUniqueCourses() { students.stream().flatMap(s->s.getCourses().keySet().stream()).distinct().sorted().forEach(System.out::println); }
    private static void searchByCourse() { System.out.print("Course: "); String c=scanner.nextLine().trim(); students.stream().filter(s->s.getCourses().keySet().stream().anyMatch(k->k.equalsIgnoreCase(c))).forEach(System.out::println); }
    private static void displayUniqueSkills() { students.stream().flatMap(s->s.getSkills().stream()).distinct().sorted().forEach(System.out::println); }
    private static void groupByCourse() { Map<String, List<Student>> g=new HashMap<>(); students.forEach(st->st.getCourses().keySet().forEach(course->g.computeIfAbsent(course,k->new ArrayList<>()).add(st))); g.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e->{System.out.println(e.getKey()+":"); e.getValue().forEach(s->System.out.println("  - "+s.getStudentName()));}); }
    private static void performanceStatistics() {
        students.forEach(s->{ DoubleSummaryStatistics st=s.getCourses().values().stream().mapToDouble(Double::doubleValue).summaryStatistics(); System.out.printf("%s Avg:%.2f Max:%.2f Min:%.2f Sum:%.2f%n",s.getStudentName(),st.getAverage(),st.getMax(),st.getMin(),st.getSum()); });
        System.out.println("Passing (>=65): "+students.stream().filter(s->s.getAverage()>=65).count());
    }
    private static void bestPerforming() { students.stream().max(Comparator.comparingDouble(Student::getAverage)).ifPresent(s->System.out.println("Best: "+s)); }
    private static void top3Students() { students.stream().sorted(Comparator.comparingDouble(Student::getAverage).reversed()).limit(3).forEach(System.out::println); }
    private static void applyBonusMarks() {
        var eligible = students.stream().filter(s->s.getAverage()>50).filter(s->!bonusAppliedIds.contains(s.getId())).collect(Collectors.toList());
        if (eligible.isEmpty()) { System.out.println("No eligible"); return; }
        eligible.forEach(s->{ double orig=s.getAverage(); s.getCourses().replaceAll((k,v)->Math.min(100.0,v+10)); bonusAppliedIds.add(s.getId()); System.out.printf("ID:%d %s %.2f -> %.2f%n",s.getId(),s.getStudentName(),orig,s.getAverage()); });
    }
    private static void finalRanking() { students.stream().sorted(Comparator.comparingDouble(Student::getAverage).reversed()).forEach(s->System.out.printf("%s - %.2f%n",s.getStudentName(),s.getAverage())); }
}