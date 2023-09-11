import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Hashtable;

public class ChatterProxy implements IChatRoom{
    private Socket socket;
    private RpcWriter writer;
    private RpcReader reader;
    private Hashtable<IChatter, String> hashtable = new Hashtable<>();
    int maxId = 0;

    public ChatterProxy(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new RpcWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new RpcReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void sendMessage(String message, IChatter chatter) {
        try {
            reader.readLine();
            writer.println("1");
            reader.readLine();
            writer.println(message);
            sendChatter(chatter);
            String msg = reader.readLine();
            if(msg.startsWith("0")){
                return;
            }else {
                throw new RuntimeException(msg);
            }

        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void addChatter(IChatter chatter) {
        try {
            reader.readLine();
            writer.println("2");
            sendChatter(chatter);
            String msg = reader.readLine();
            if(msg.startsWith("0")){
                return;
            }else {
                throw new RuntimeException(msg);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void exitChatter(IChatter chatter) {
        try {
            reader.readLine();
            writer.println("3");
            sendChatter(chatter);
            String msg = reader.readLine();
            if(msg.startsWith("0")){
                return;
            }else {
                throw new RuntimeException(msg);
            }
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void sendChatter(IChatter chatter) throws IOException {
        String ret = hashtable.get(chatter);
        if(ret == null) {
            maxId++;
            hashtable.put(chatter, ""+maxId);
            String id = hashtable.get(chatter);
            reader.readLine();
            writer.println(id);
            reader.readLine(); //Gib mir den Namen
            writer.println(chatter.getName());
        }else {
            String id = hashtable.get(chatter);
            reader.readLine();
            writer.println(id);
        }
    }

    public void endConnection() {
        try {
            reader.readLine();
            writer.println("4");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
