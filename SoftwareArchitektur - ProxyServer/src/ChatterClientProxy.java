import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatterClientProxy implements IChatter{
    private Socket socket;
    private RpcWriter writer;
    private RpcReader reader;

    public ChatterClientProxy(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new RpcWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new RpcReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void receiveMessage(String message) {
        try {
            reader.readLine();
            writer.println("1");
            reader.readLine();
            writer.println(message);
            String ret = reader.readLine();
            if(!ret.startsWith("0")){
                throw new RuntimeException(ret);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        try {
            reader.readLine();
            writer.println("2");
            String ret = reader.readLine();
            if(ret.startsWith("0")){
                String retNull = reader.readLine();
                return retNull.startsWith("3") ?  null : reader.readLine();
            }else {
                throw new RuntimeException(ret);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void endConnection(){
        try{
            reader.readLine();
            writer.println("3");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
