import java.io.IOException;

public interface IChatRoom {
    public void sendMessage(String message, IChatter chatter) throws IOException;
    public void addChatter(IChatter chatter);
    public void exitChatter(IChatter chatter);
}
