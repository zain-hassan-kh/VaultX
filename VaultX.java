import java.util.*;
public class VaultX {
    private static Scanner console = new Scanner(System.in);
    private static String encryptedFullName;         
    private static String encryptedUsername;         
    private static String encryptedPassword;        
    private static String encryptedSecurityQuestion; 
    private static String encryptedAnswer;          
    private static String encryptedBalance;      
    private static String encryptedAccountNumber;
    private static String encryptedPassKey;         
    private static String fullName;           
    private static String securityQuestion;    
    private static double balance;

    public static void main(String[] args) {
        while (true) {  
            System.out.println("\nMain Menu");
            System.out.println("1. Sign Up");              
            System.out.println("2. Login");                
            System.out.println("3. Update Credentials");   
            System.out.println("4. Forgot Password");      
            System.out.println("5. Delete Account");       
            System.out.println("6. Exit");                 
            System.out.print("Enter choice: "); 
            String choice = console.nextLine().trim();
            switch (choice) {
                case "1":
                    signUp();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    updateCredentials();
                    break;
                case "4":
                    forgotPasswordRecovery();
                    break;
                case "5":
                    deleteAccount();
                    return;
                case "6":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            } 
        }
    }
    private static String getRandomSecurityQuestion() {
        Random rand = new Random();
        int questionNumber = rand.nextInt(10);
        switch (questionNumber) {
            case 0: return "What is your mother's maiden name?";
            case 1: return "What was the name of your first pet?";
            case 2: return "What was the name of your elementary school?";
            case 3: return "What is your favorite color?";
            case 4: return "What is your favorite book?";
            case 5: return "What was the model of your first car?";
            case 6: return "What is the name of the city where you were born?";
            case 7: return "What is your favorite food?";
            case 8: return "What was the name of your best friend in high school?";
            case 9: return "What is the name of your childhood hero?";
            default: return "No question available.";
        }
    }
    public static void signUp() {
        System.out.println("\nSign-Up");
        String fullNamePlain = promptNonEmpty("Enter full name: ");
        String usernamePlain = promptNonEmpty("Enter username: ");
        String passwordPlain = promptNonEmpty("Enter password: ");
        securityQuestion = getRandomSecurityQuestion();
        System.out.println("Security Question: " + securityQuestion);
        String answerPlain = promptNonEmpty("Enter security answer: ");
        String initialDeposit = promptValidAmount("Enter initial deposit: ");
        String accountNumberPlain = generateAccountNumber();
        String passKeyPlain = generatePassKey();
        System.out.println("\nYour Account Number: " + accountNumberPlain);
        System.out.println("Your PassKey: " + passKeyPlain);
        encryptedFullName = encrypt(fullNamePlain);
        encryptedUsername = encrypt(usernamePlain);
        encryptedPassword = encrypt(passwordPlain);
        encryptedSecurityQuestion = encrypt(securityQuestion); 
        encryptedAnswer = encrypt(answerPlain);
        encryptedBalance = encrypt(initialDeposit);
        encryptedAccountNumber = encrypt(accountNumberPlain);
        encryptedPassKey = encrypt(passKeyPlain);
        fullName = fullNamePlain;
        balance = Double.parseDouble(initialDeposit);
    }
    public static void login() {
        System.out.println("\nLogin");
        if (encryptedUsername == null) {
            System.out.println("No account found. Please sign up first.");
            return;
        }

        String inputUsername = promptNonEmpty("Enter username: ");
        String inputPassword = promptNonEmpty("Enter password: ");
        if (encrypt(inputUsername).equals(encryptedUsername) && 
            encrypt(inputPassword).equals(encryptedPassword)) {
            System.out.println("\nWelcome, " + fullName + "!");
            bankMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    public static void forgotPasswordRecovery() {
        System.out.println("\nForgot Password");
        if (encryptedAccountNumber == null) {
            System.out.println("No account found. Please sign up first.");
            return;
        }
        String inputAccountNumber = promptNonEmpty("Enter account number: ");
        if (!encrypt(inputAccountNumber).equals(encryptedAccountNumber)) {
            System.out.println("Invalid account number.");
            return;
        }
        String inputPassKey = promptNonEmpty("Enter PassKey: ");
        if (!encrypt(inputPassKey).equals(encryptedPassKey)) {
            System.out.println("Invalid PassKey.");
            return;
        }
        System.out.println("Security Question: " + securityQuestion);
        String inputAnswer = promptNonEmpty("Enter answer: ");
        if (!encrypt(inputAnswer).equals(encryptedAnswer)) {
            System.out.println("Incorrect answer.");
            return;
        }
        encryptedPassword = encrypt(promptNonEmpty("Enter new password: "));
        System.out.println("Password reset successfully.");
    }
    public static void updateCredentials() {
        System.out.println("\nUpdate Credentials");
        if (encryptedAccountNumber == null) {
            System.out.println("No account found. Please sign up first.");
            return;
        }
        String inputUsername = promptNonEmpty("Enter username: ");
        String inputPassword = promptNonEmpty("Enter password: ");
        if (!encrypt(inputUsername).equals(encryptedUsername) ||
            !encrypt(inputPassword).equals(encryptedPassword)) {
            System.out.println("Invalid credentials.");
            return;
        }
        while (true) {
            System.out.println("\nUpdate Menu");
            System.out.println("1. Update Username");
            System.out.println("2. Update Full Name");
            System.out.println("3. Exit");
            String choice = promptNonEmpty("Enter choice: ");
            switch (choice) {
                case "1":
                    encryptedUsername = encrypt(promptNonEmpty("Enter new username: "));
                    System.out.println("Username updated.");
                    break;
                case "2":
                    fullName = promptNonEmpty("Enter new full name: ");
                    encryptedFullName = encrypt(fullName);
                    System.out.println("Full name updated.");
                    break;
                case "3":
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    private static void deleteAccount() {
        if (encryptedUsername == null) {
            System.out.println("No account found. Nothing to delete.");
            return;
        }
        String confirm = promptNonEmpty("Are you sure you want to PERMANENTLY DELETE your account? (yes/no): ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Account deletion cancelled.");
            return;
        }
        String inputUsername = promptNonEmpty("Enter your username: ");
        String inputPassword = promptNonEmpty("Enter your password: ");
        String inputSecurityAnswer = promptNonEmpty(securityQuestion + " ");
        String inputAccountNumber = promptNonEmpty("Enter your account number: ");
        String inputPassKey = promptNonEmpty("Enter your passkey: ");
    
        boolean verified = encrypt(inputUsername).equals(encryptedUsername)
                        && encrypt(inputPassword).equals(encryptedPassword)
                        && encrypt(inputSecurityAnswer).equals(encryptedAnswer)
                        && encrypt(inputAccountNumber).equals(encryptedAccountNumber)
                        && encrypt(inputPassKey).equals(encryptedPassKey);
    
        if (!verified) {
            System.out.println("Verification failed. Account deletion aborted.");
            return;
        }
        encryptedFullName = null;
        encryptedUsername = null;
        encryptedPassword = null;
        encryptedSecurityQuestion = null;
        encryptedAnswer = null;
        encryptedBalance = null;
        encryptedAccountNumber = null;
        encryptedPassKey = null;
        fullName = null;
        securityQuestion = null;
        balance = 0.0;
    
        System.out.println("Account deleted successfully. All data erased.");
    }
    private static void bankMenu() {
        while (true) {
            System.out.println("\nBank Menu");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Logout");
            String choice = promptNonEmpty("Enter choice: ");
            switch (choice) {
                case "1":
                    System.out.println("Current Balance: " + balance);
                    break;

                case "2":
                    deposit();
                    break;
                case "3":
                    withdraw();
                    break;

                case "4":
                    System.out.println("Logged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    private static void deposit() {
        double amount = Double.parseDouble(promptValidAmount("Enter deposit amount: "));  
        balance += amount;  
        encryptedBalance = encrypt(String.valueOf(balance));
        System.out.println("Deposit successful. New balance: " + balance);
    }
    private static void withdraw() {
        double amount = Double.parseDouble(promptValidAmount("Enter withdrawal amount: "));   
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }  
        balance -= amount; 
        encryptedBalance = encrypt(String.valueOf(balance));  
        System.out.println("Withdrawal successful. New balance: " + balance);
    }
    private static String generateAccountNumber() {
        String letters = ""; 
        for (int i = 0; i < 4; i++) {
            letters += (char) ('A' + (int) (Math.random() * 26));
        }
        String numbers = ""; 
        for (int i = 0; i < 9; i++) {
            numbers += (int) (Math.random() * 10);
        }
        return letters + numbers;
    }
    private static String generatePassKey() {
        String digits = ""; 
        for (int i = 0; i < 5; i++) {
            digits += (int) (Math.random() * 10);
        }
        return "PK" + digits;
    }
    private static String encrypt(String input) {
        String encrypted = ""; 
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i); 
            if (c >= 'A' && c <= 'Z') { 
                encrypted += (char) ('A' + (c - 'A' + 3) % 26);
            }
            else if (c >= 'a' && c <= 'z') {
                encrypted += (char) ('a' + (c - 'a' + 3) % 26);
            }
            else if (c >= '0' && c <= '9') {
                encrypted += (char) ('0' + (c - '0' + 3) % 10);
            }
            else {
                encrypted += c;
            }
        }
        return encrypted;
    }
    private static String promptNonEmpty(String prompt) {
        String input;  
        while (true) {
            System.out.print(prompt);
            input = console.nextLine().trim(); 
            if (!input.isEmpty()) {
                return input; 
            }
            System.out.println("Input cannot be empty.");
        }
    }
    private static String promptValidAmount(String prompt) {
        while (true) {
            System.out.print(prompt); 
            String input = console.nextLine().trim(); 
            if (isNumeric(input)) {
                double amount = Double.parseDouble(input);
                if (amount >= 0) {
                    return input;  
                }
                System.out.println("Amount cannot be negative.");
            } else {
                System.out.println("Invalid amount. Use digits and one decimal point.");
            }
        }
    }
    private static boolean isNumeric(String input) {
        if (input.isEmpty()) return false;

        int dots = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i); 

            if (c == '.') {
                dots++;
            } 
            else if (!Character.isDigit(c)) {
                return false;
            }
        }
        return dots <= 1;
    }
}