public interface IChatRoom {
    public void sendMessage(String message, IChatter chatter);
    public void addChatter(IChatter chatter);
    public void exitChatter(IChatter chatter);
}
