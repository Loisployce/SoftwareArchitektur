import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gib deinen Namen ein");
        String username = scanner.next();
        Chatter chatter = new Chatter(username);
        Socket socket = new Socket("localhost", 9090);
        ChatterProxy proxy = new ChatterProxy(socket);
        proxy.addChatter(chatter);

        boolean running = true;
        while (running) {
            System.out.println("1. Send Message ; 2. Enter Chatroom ; 3. Leave Chatroom");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    System.out.println("Enter message: ");
                    String message = scanner.next();
                    proxy.sendMessage(message, chatter);
                    break;
                case "2":
                    proxy.addChatter(chatter);
                    break;
                case "3":
                    proxy.exitChatter(chatter);
                    proxy.endConnection();
                    running = false;
                    break;
                default:
                    System.out.println("Pls enter 1, 2 or 3");
            }
        }
    }
}