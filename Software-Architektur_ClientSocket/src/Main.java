import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 10000);
        BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        System.out.println("Verbindung hergestellt!");

        System.out.println(r.readLine());
        Scanner scanner = new Scanner(System.in);
        int auswahl = scanner.nextInt();
        pw.println(auswahl);
        pw.flush();

        System.out.println(r.readLine());
        if(auswahl == 1) {
            String msg = scanner.next();
            pw.println(msg);
            pw.flush();
        }
    }
}