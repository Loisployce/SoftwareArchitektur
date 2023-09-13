import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatterServerProxy implements Runnable {
    private Socket socket;
    private RpcWriter writer;
    private RpcReader reader;
    private IChatter chatter;
    private boolean running = true;

    public ChatterServerProxy(Socket socket, IChatter chatter) {
        this.socket = socket;
        this.chatter = chatter;
    }

    @Override
    public void run() {
        try {
            this.reader = new RpcReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new RpcWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (running) {
                writer.println("protocol: 1. Receive message ; 2. Get name ; 3. End Connection");
                String input = reader.readLine();
                switch (input) {
                    case "1":
                        receiveMessage();
                        break;
                    case "2":
                        getName();
                        break;
                    case "3":
                        endConnection();
                        break;
                    default:
                        writer.println("protocol error: " + input);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void receiveMessage() {
        writer.println("please enter your message");
        try {
            String message = reader.readLine();
            this.chatter.receiveMessage(message);
            writer.println("0: Okay");
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("1: " + e.getMessage());
        }
    }

    private void getName() {
        try {
            String name = this.chatter.getName();
            writer.println("0: Okay");
            if (name == null) {
                writer.println("3: Null");
            } else {
                writer.println("4: Value");
                writer.println(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("1: " + e.getMessage());
        }
    }

    private void endConnection() {
        this.running = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
