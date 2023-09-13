import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        boolean running = true;
        ChatRoom chatRoom = new ChatRoom();
        while(running) {
            Socket s = serverSocket.accept();
            ChatRoomServerProxy serverProxy = new ChatRoomServerProxy(s, chatRoom);
            Thread t = new Thread(serverProxy);
            t.start();
        }
    }
}