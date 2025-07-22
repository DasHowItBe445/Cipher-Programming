import java.util.Scanner;

public class CipherProgram {

    // Utility: Modular inverse for affine/multiplicative decryption
    static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return -1;
    }

    // Additive Cipher
    static String additiveEncrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                result.append((char) ((ch - base + key) % 26 + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    static String additiveDecrypt(String text, int key) {
        return additiveEncrypt(text, 26 - key); // inverse is 26 - key
    }

    // Multiplicative Cipher
    static String multiplicativeEncrypt(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int enc = (key * (ch - base)) % 26;
                result.append((char) (enc + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    static String multiplicativeDecrypt(String text, int key) {
        int keyInv = modInverse(key, 26);
        if (keyInv == -1) return "Invalid key: No modular inverse.";
        return multiplicativeEncrypt(text, keyInv);
    }

    // Affine Cipher
    static String affineEncrypt(String text, int a, int b) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int enc = (a * (ch - base) + b) % 26;
                result.append((char) (enc + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    static String affineDecrypt(String text, int a, int b) {
        int aInv = modInverse(a, 26);
        if (aInv == -1) return "Invalid key: 'a' has no modular inverse.";
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int dec = (aInv * ((ch - base - b + 26)) % 26);
                result.append((char) (dec + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Main Program with Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text;
        int choice;

        do {
            System.out.println("\nChoose Cipher Type:");
            System.out.println("1. Additive Cipher");
            System.out.println("2. Multiplicative Cipher");
            System.out.println("3. Affine Cipher");
            System.out.println("4. Exit");
            System.out.print("Your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1: // Additive
                    System.out.print("Enter text: ");
                    text = sc.nextLine();
                    System.out.print("Enter additive key (0–25): ");
                    int addKey = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Encrypted: " + additiveEncrypt(text, addKey));
                    System.out.println("Decrypted: " + additiveDecrypt(additiveEncrypt(text, addKey), addKey));
                    break;

                case 2: // Multiplicative
                    System.out.print("Enter text: ");
                    text = sc.nextLine();
                    System.out.print("Enter multiplicative key (must be coprime with 26): ");
                    int multKey = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Encrypted: " + multiplicativeEncrypt(text, multKey));
                    System.out.println("Decrypted: " + multiplicativeDecrypt(multiplicativeEncrypt(text, multKey), multKey));
                    break;

                case 3: // Affine
                    System.out.print("Enter text: ");
                    text = sc.nextLine();
                    System.out.print("Enter key 'a' (must be coprime with 26): ");
                    int a = sc.nextInt();
                    System.out.print("Enter key 'b': ");
                    int b = sc.nextInt();
                    sc.nextLine();
                    String encAffine = affineEncrypt(text, a, b);
                    System.out.println("Encrypted: " + encAffine);
                    System.out.println("Decrypted: " + affineDecrypt(encAffine, a, b));
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }
}
