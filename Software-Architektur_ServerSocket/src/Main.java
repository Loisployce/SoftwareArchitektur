import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10000);
        boolean running = true;
        while(running) {
            Socket s = serverSocket.accept();
            try {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            process(s);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                Thread t = new Thread(runnable);
                t.start();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void process(Socket s) throws IOException, InterruptedException {
        BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.println("Gib mir eine Zahl (1: Botschaft ; 2: Abbruch)");
        pw.flush();
        int clientAuswahl = 2;
        try {
            clientAuswahl = Integer.parseInt(r.readLine());
        }catch (NumberFormatException e) {
            pw.println("Bitte gib eine Zahl ein (1/2)");
            pw.flush();
            e.printStackTrace();
        }
        switch (clientAuswahl) {
            case 1:
                pw.println("Bitte gib eine Botschaft ein!");
                pw.flush();
                String clientMsg = r.readLine();
                Thread.sleep(10000);
                System.out.println(clientMsg);
                break;
            case 2:
                pw.println("Abbrechen nicht autorisiert");
                pw.flush();
                break;
        }
    }
}