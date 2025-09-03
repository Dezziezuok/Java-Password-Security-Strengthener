import java.security.SecureRandom;
import java.util.Scanner;

public class PasswordStrengthener {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT_CHARS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%&?";
    private static final SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a password to check its strength:");
        String password = scanner.nextLine();

        int score = calculateStrengthScore(password);
        System.out.println("\n--- Password Strength Report ---");
        System.out.println("Password Strength Score: " + score + "/10");
        
        if (score <= 7) {
            System.out.println("\nYour password is not strong enough. Would you like to generate a new, strong password? (yes/no)");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("yes")) {
                String generatedPassword = generateStrongPassword(score);
                System.out.println("Here is a strong, generated password: " + generatedPassword);
            }
        } else {
            System.out.println("Your password is strong!");
        }

        scanner.close();
    }

    private static int calculateStrengthScore(String password) {
        if (password.length() < 8) return 0;

        int score = 0;
        if (password.length() >= 12) score += 2; // Length bonus
        else if (password.length() >= 8) score += 1;

        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()-_+=<>?].*");

        int characterVariety = 0;
        if (hasLower) characterVariety++;
        if (hasUpper) characterVariety++;
        if (hasDigit) characterVariety++;
        if (hasSpecial) characterVariety++;

        score += characterVariety * 2; // +2 for each type

        // Penalty for simple passwords
        if (password.toLowerCase().contains("password") || password.toLowerCase().contains("123456")) {
            score = 0;
        }

        return Math.min(score, 10); // Cap the score at 10
    }

    private static String generateStrongPassword(int currentScore) {
        StringBuilder passwordBuilder = new StringBuilder();
        String allChars = "";

        // Ensure the password has at least one of each required character type
        passwordBuilder.append(LOWERCASE_CHARS.charAt(random.nextInt(LOWERCASE_CHARS.length())));
        passwordBuilder.append(UPPERCASE_CHARS.charAt(random.nextInt(UPPERCASE_CHARS.length())));
        passwordBuilder.append(DIGIT_CHARS.charAt(random.nextInt(DIGIT_CHARS.length())));
        passwordBuilder.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // Combine all character types for the rest of the password
        allChars = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGIT_CHARS + SPECIAL_CHARS;

        // Fill the rest of the password to reach a strong length (12-16 characters)
        int passwordLength = 12 + random.nextInt(5); // Random length between 12 and 16
        while (passwordBuilder.length() < passwordLength) {
            passwordBuilder.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the characters to avoid a predictable pattern
        String shuffledPassword = shuffleString(passwordBuilder.toString());
        return shuffledPassword;
    }

    private static String shuffleString(String s) {
        char[] array = s.toCharArray();
        for (int i = 0; i < array.length; i++) {
            int randomIndex = random.nextInt(array.length);
            char temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
        }
        return new String(array);
    }
}