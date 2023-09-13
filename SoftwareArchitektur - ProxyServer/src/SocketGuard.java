import java.io.IOException;
import java.net.Socket;

public class SocketGuard implements Runnable {
    private Socket socket;
    private int timeout;
    private boolean active = true;

    public SocketGuard(Socket socket, int timeout) {
        this.socket = socket;
        this.timeout = timeout;

        Thread t = new Thread(this);
        t.start();

    }

    public void deactivate() {
        active = false;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (active) {
            try {
                socket.close();
                System.out.println("Killing Socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
