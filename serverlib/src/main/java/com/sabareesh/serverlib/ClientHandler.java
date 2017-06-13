package com.sabareesh.serverlib;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabareesh.commonlib.ControllerStats;
import com.sabareesh.commonlib.RequestQueue;
import com.sabareesh.commonlib.models.ControlData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by sabareesh on 9/4/15.
 */
public class ClientHandler implements Runnable {
    private Socket client;
    private ObjectMapper objectMapper;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    public ClientHandler(Socket client) throws IOException {
        System.out.println("Client Connected");
        this.client=client;
        inputStream=new DataInputStream(client.getInputStream());
        outputStream=new DataOutputStream(client.getOutputStream());
        objectMapper=new ObjectMapper();
    }

    @Override
    public void run() {
        while (true){
            try {

                handleReceive();

                String data=objectMapper.writeValueAsString(ControllerStats.getInstance());
              //  objectMapper.read
                send(data);


            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
                return;
            }

        }
    }
    private void send(String data) throws IOException {
        writeMessage(data.getBytes());
    }

    private void handleReceive() throws IOException {
        String data=receive("send");
        ReceivingStructure receivingStructure=objectMapper.readValue(data, ReceivingStructure.class);
        for (ControlData controlData : receivingStructure.getSignal()) {
            RequestQueue.getInstance().enqueue(controlData);
        }
    }

    private String receive(String expect) throws IOException {

        String data=new String(readMessage());
        if(data.contains(expect))
            return data;
        StringBuilder builder=new StringBuilder();
        builder.append(data);

        while (builder.toString().contains(expect)){
            builder.append(readMessage());
        }
        return builder.toString();
    }



    private void writeMessage(byte[] msg) throws IOException {
      //  Log.d("message lenght",msg.length+"");
        outputStream.writeInt(msg.length);
        outputStream.write(msg, 0, msg.length);
        outputStream.flush();
    }

    private byte[] readMessage() throws IOException {
        int msgLen = inputStream.readInt();
        byte[] msg = new byte[msgLen];
        inputStream.readFully(msg);
        return msg;
    }
}
