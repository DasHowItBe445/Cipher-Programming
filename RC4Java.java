import java.util.Scanner;

public class RC4Java {

    // Converts a string to an array of ASCII integer values
    public static int[] stringToAscii(String text) {
        int[] ascii = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            ascii[i] = (int) text.charAt(i);
        }
        return ascii;
    }

    // Converts an array of ASCII values to a string
    public static String asciiToString(int[] asciiList) {
        StringBuilder sb = new StringBuilder();
        for (int code : asciiList) {
            sb.append((char) code);
        }
        return sb.toString();
    }

    // Key Scheduling Algorithm (KSA)
    public static byte[] ksa(byte[] key) {
        int keyLen = key.length;
        byte[] S = new byte[256];
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + (S[i] & 0xFF) + (key[i % keyLen] & 0xFF)) & 0xFF;
            byte temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
        return S;
    }

    // PRGA: Generates keystream for RC4
    public static byte[] prga(byte[] S, int dataLen) {
        byte[] keystream = new byte[dataLen];
        int i = 0, j = 0;
        for (int k = 0; k < dataLen; k++) {
            i = (i + 1) & 0xFF;
            j = (j + (S[i] & 0xFF)) & 0xFF;
            byte temp = S[i];
            S[i] = S[j];
            S[j] = temp;
            int t = ((S[i] & 0xFF) + (S[j] & 0xFF)) & 0xFF;
            keystream[k] = S[t];
        }
        return keystream;
    }

    // RC4 encryption/decryption
    public static byte[] rc4(byte[] key, byte[] data) {
        byte[] S = ksa(key);
        byte[] keystream = prga(S, data.length);
        byte[] output = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            output[i] = (byte) (data[i] ^ keystream[i]);
        }
        return output;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String keyStr = scanner.nextLine();
        System.out.print("Enter the plaintext: ");
        String plaintext = scanner.nextLine();

        int[] asciiValues = stringToAscii(plaintext);
        System.out.print("Plaintext ASCII values: ");
        for (int val : asciiValues) {
            System.out.print(val + " ");
        }
        System.out.println();

        byte[] ciphertext = rc4(keyStr.getBytes(), plaintext.getBytes());
        System.out.print("Ciphertext (hex): ");
        for (byte b : ciphertext) {
            System.out.printf("%02X", b);
        }
        System.out.println();

        byte[] decrypted = rc4(keyStr.getBytes(), ciphertext);
        int[] decryptedAscii = new int[decrypted.length];
        for (int i = 0; i < decrypted.length; i++) {
            decryptedAscii[i] = decrypted[i] & 0xFF;
        }
        System.out.print("Decrypted ASCII values: ");
        for (int val : decryptedAscii) {
            System.out.print(val + " ");
        }
        System.out.println();

        String decryptedText = asciiToString(decryptedAscii);
        System.out.println("Decrypted text: " + decryptedText);

        scanner.close();
    }
}
