package server.serverConnect;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    ServerSocket serverSocket;
    private boolean flag = true;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1441);
            System.out.println("Server start!!!");
            while(flag){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client" + clientSocket + " here!");
                new Thread(new ServerWork(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //rmi

}
