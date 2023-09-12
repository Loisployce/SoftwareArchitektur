import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements IChatRoom{
    private List<IChatter> chatterList = new ArrayList();
    @Override
    public void sendMessage(String message, IChatter chatter) {
        if(!chatterList.contains(chatter)) {
            throw new RuntimeException("Chatter nicht im ChatRoom");
        }
        for (IChatter myChatter:
             chatterList) {
            myChatter.receiveMessage(chatter.getName() + " said: " + message);
        }
    }

    @Override
    public void addChatter(IChatter chatter) {
        this.chatterList.add(chatter);
        sendMessage("I entered the chat room" , chatter);
    }

    @Override
    public void exitChatter(IChatter chatter) {
        sendMessage("I left the Chatroom: ", chatter);
        this.chatterList.remove(chatter);
    }
}
