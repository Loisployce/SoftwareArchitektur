import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class RpcReader extends BufferedReader {
    public RpcReader(Reader in) {
        super(in);
    }

    @Override
    public String readLine() throws IOException {
        String msg = super.readLine();
        System.out.println(this + " Reading: " + msg);
        return msg;
    }
}
