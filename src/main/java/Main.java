
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final studentservise service = new studentservise();

    public static void main(String[] args) {
        printBanner();
        while (true) {
            try {
                switch (menu()) {
                    case 1 -> addStudent();
                    case 2 -> listStudents();
                    case 3 -> updateStudent();
                    case 4 -> deleteStudent();
                    case 5 -> searchById();
                    case 6 -> searchByName();
                    case 7 -> quit();
                    default -> System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Validation error: " + ex.getMessage());
            } catch (SQLException ex) {
                System.out.println(" Database error: " + ex.getMessage());
            } catch (InputMismatchException ex) {
                System.out.println(" Invalid input type. Try again.");
                in.nextLine(); // clear scanner buffer
            }
        }
    }

    private static void printBanner() {
        System.out.println("\n=== student Database (JDBC + MySQL) ===");
    }

    private static int menu() {
        System.out.println("\n1) Add student");
        System.out.println("2) View All");
        System.out.println("3) Update student");
        System.out.println("4) Delete student");
        System.out.println("5) Search by ID");
        System.out.println("6) Search by Name");
        System.out.println("7) Exit");
        System.out.print("Choose: ");
        return safeInt();
    }



    private static void addStudent() throws SQLException {
        System.out.println("\n-- Add student --");
        String name   = askStr("Name");
        String email  = askStr("Email");
        String course = askStr("Course (optional)");
        double gpa    = askDouble("GPA (0.00 - 4.00)");

        int id = service.create(new student(name, email, course, gpa));
        System.out.println(" Inserted with ID: " + id);
    }

    private static void listStudents() throws SQLException {
        System.out.println("\n-- All Students --");
        List<student> all = service.getAll();
        if (all.isEmpty()) {
            System.out.println("(no records)");
        } else {
            all.forEach(System.out::println);
        }
    }

    private static void updateStudent() throws SQLException {
        System.out.println("\n-- Update student --");
        int id = askInt("ID to update");
        student current = service.get(id);
        if (current == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println("Current: " + current);

        String name   = askStrDefault("New Name", current.getName());
        String email  = askStrDefault("New Email", current.getEmail());
        String course = askStrDefault("New Course", current.getCourse());
        double gpa    = askDoubleDefault("New GPA", current.getGpa());

        student updated = new student(id, name, email, course, gpa);
        boolean ok = service.update(updated);
        System.out.println(ok ? " Updated." : "No change.");
    }

    private static void deleteStudent() throws SQLException {
        System.out.println("\n-- Delete student --");
        int id = askInt("ID to delete");
        boolean ok = service.delete(id);
        System.out.println(ok ? " Deleted." : "ID not found.");
    }

    private static void searchById() throws SQLException {
        int id = askInt("\nSearch ID");
        student s = service.get(id);
        System.out.println(s == null ? "Not found." : s);
    }

    private static void searchByName() throws SQLException {
        String q = askStr("\nName contains");
        List<student> list = service.searchByName(q);
        if (list.isEmpty()) System.out.println("No matches.");
        else list.forEach(System.out::println);
    }

    private static void quit() {
        System.out.println("\nBye! 👋");
        System.exit(0);
    }



    private static int safeInt() {
        while (true) {
            try { return Integer.parseInt(in.nextLine().trim()); }
            catch (Exception e) { System.out.print("Enter a number: "); }
        }
    }

    private static int askInt(String label) {
        System.out.print(label + ": ");
        return safeInt();
    }

    private static double askDouble(String label) {
        while (true) {
            System.out.print(label + ": ");
            try { return Double.parseDouble(in.nextLine().trim()); }
            catch (Exception e) { System.out.println("Enter a valid number."); }
        }
    }

    private static double askDoubleDefault(String label, double def) {
        System.out.print(label + " [" + def + "]: ");
        String s = in.nextLine().trim();
        if (s.isBlank()) return def;
        try { return Double.parseDouble(s); }
        catch (Exception e) {
            System.out.println("Invalid; keeping " + def);
            return def;
        }
    }

    private static String askStr(String label) {
        System.out.print(label + ": ");
        return in.nextLine().trim();
    }

    private static String askStrDefault(String label, String def) {
        System.out.print(label + " [" + (def == null ? "" : def) + "]: ");
        String s = in.nextLine().trim();
        return s.isBlank() ? def : s;
    }
}
