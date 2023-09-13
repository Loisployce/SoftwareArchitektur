public class Chatter implements IChatter{
    private String name;

    public Chatter(String name) {
        this.name = name;
    }
    @Override
    public void receiveMessage(String message) {
        System.out.println(getName() + " hört: " + message);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
