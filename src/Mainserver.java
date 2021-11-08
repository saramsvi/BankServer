import server.serverConnect.Server;

public class Mainserver {

    public static void main(String[] args) {
        Thread thread = new Thread(new Server());
        thread.start();
    }
}
