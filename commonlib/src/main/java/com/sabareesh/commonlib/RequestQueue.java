package com.sabareesh.commonlib;

import com.sabareesh.commonlib.models.ControlData;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by sabareesh on 10/21/15.
 */
public class RequestQueue {
    private static RequestQueue ourInstance = new RequestQueue();

    public static RequestQueue getInstance() {
        return ourInstance;
    }
    private Queue<ControlData> queue;
    private RequestQueue() {
        queue=new LinkedList<>();

    }
    public void enqueue(ControlData data){
        queue.add(data);
    }
    public ControlData dequeue(){
        return queue.remove();
    }
    public int getSize(){
        return queue.size();
    }
}
