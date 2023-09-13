import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Hashtable;

public class ChatRoomServerProxy implements Runnable {
    private Socket socket;
    private IChatRoom chatRoom;
    private RpcWriter writer;
    private RpcReader reader;
    private Hashtable<String, IChatter> hashtable = new Hashtable<>();
    private boolean running = true;

    public ChatRoomServerProxy(Socket socket, IChatRoom chatRoom) {
        this.socket = socket;
        this.chatRoom = chatRoom;
    }

    @Override
    public void run() {
        try {
            this.reader = new RpcReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new RpcWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (running) {
                writer.println(
                        "protocol: 1. Send Message ; 2. Enter ChatRoom ; 3. Leave ChatRoom; (Techn.: 4. End Connection)");
                String input = reader.readLine();
                switch (input) {
                    case "1":
                        sendMessage();
                        break;
                    case "2":
                        enterChatroom();
                        break;
                    case "3":
                        leaveChatroom();
                        break;
                    case "4":
                        endConnection();
                    default:
                        writer.println("protocol error: " + input);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endConnection() throws IOException {
        this.running = false;
        socket.close();
    }

    public void sendMessage() throws IOException {
        writer.println("please enter your message!");
        String message = reader.readLine();
        try {
            this.chatRoom.sendMessage(message, getChatter());
            writer.println("0: Okay");
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("1: err " + e.getMessage());
        }
    }

    public void enterChatroom() {
        try {
            chatRoom.addChatter(getChatter());
            writer.println("0: Okay");
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("2: err " + e.getMessage());
        }
    }

    public void leaveChatroom() {
        try {
            chatRoom.exitChatter(getChatter());
            writer.println("0: Okay");
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("3: err " + e.getMessage());
        }
    }

    public IChatter getChatter() throws IOException {
        writer.println("Gib mir doch den Chatter");
        String chatterId = reader.readLine();
        IChatter ret = hashtable.get(chatterId);
        if (ret == null) {
            writer.println("Gib mir deine IP");
            String ip = reader.readLine();
            writer.println("Gib mir deinen Port");
            String port = reader.readLine();
            Socket s = new Socket(ip, Integer.parseInt(port));
            ChatterClientProxy clientProxy = new ChatterClientProxy(s);
            hashtable.put(chatterId, clientProxy);
        }
        return hashtable.get(chatterId);
    }
}
