import jdk.jshell.execution.Util;

import java.io.IOException;
import java.net.*;
import java.rmi.server.UID;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("localhost", 9090);

        System.out.println("Gib deinen Namen ein");
        String username = scanner.next();
        Chatter chatter = new Chatter(username);

        ChatRoomProxy proxy = new ChatRoomProxy(socket);

        boolean running = true;
        while (running) {
            System.out.println("1. Send Message ; 2. Enter Chatroom ; 3. Leave Chatroom ; 4. Verbindung beenden");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    System.out.println("Enter message: ");
                    String message = scanner.next();
                    try {
                        proxy.sendMessage(message, chatter);
                    } catch (Exception e) {
                        System.err.println("Du bist nicht im Chatroom");
                    }
                    break;
                case "2":
                    proxy.addChatter(chatter);
                    break;
                case "3":
                    proxy.exitChatter(chatter);
                    break;
                case "4":
                    proxy.endConnection();
                    running = false;
                    break;
                default:
                    System.out.println("Pls enter 1, 2 or 3");
            }
        }
    }
}