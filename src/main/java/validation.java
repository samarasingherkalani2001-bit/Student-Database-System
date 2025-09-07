import java.util.regex.Pattern;

public final class validation {
    private validation() {}

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static void requireName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
    }

    public static void requireValidEmail(String email) {
        if (email == null || email.isBlank() || !EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is invalid.");
        }
    }

    public static void requireValidGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.00 and 4.00.");
        }
    }
}
