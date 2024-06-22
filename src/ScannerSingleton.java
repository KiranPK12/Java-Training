import java.util.Scanner;

public class ScannerSingleton {
    private static Scanner sc = new Scanner(System.in);

    private ScannerSingleton() {
        // Private constructor to prevent instantiation
    }

    public static Scanner getInstance() {
        return sc;
    }

    public static void close() {
        sc.close();
    }
}
