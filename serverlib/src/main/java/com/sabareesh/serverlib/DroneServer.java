package com.sabareesh.serverlib;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabareesh on 9/4/15.
 */
public class DroneServer implements Runnable {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private List<Thread> threads;
    public DroneServer(int port) throws IOException {
        serverSocket=new ServerSocket(port);
      //  serverSocket.setSoTimeout(10000);
        clients=new ArrayList<>();
        threads=new ArrayList<>();
        Log.d("ip",serverSocket.getInetAddress().getHostAddress());

    }
    @Override
    public void run() {
        while (true){
            try{
                System.out.println("Waiting for nodes on "+serverSocket.getLocalPort());
                Socket client=serverSocket.accept();

                handleClient(client);

            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }
    private void handleClient(Socket client) throws IOException {
        System.out.println("client connected");
        ClientHandler clientHandler=new ClientHandler(client);
        clients.add(clientHandler);
        Thread thread=new Thread(clientHandler);
        thread.start();
        threads.add(thread);


    }
}
