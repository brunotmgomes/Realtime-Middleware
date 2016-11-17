package queue;

import java.util.ArrayList;

import global.Message;

public class Queue {
	private ArrayList<ConnectionObject> connectionQueue = new ArrayList<ConnectionObject>();
	
	public void subscribe(ConnectionObject connectionObject){
		connectionQueue.add(connectionObject);
	}
	
	public void unsubscribe(ConnectionObject connectionObject){
		if(!connectionQueue.isEmpty())
			connectionQueue.remove(connectionObject);
	}
	
	public void enqueue(Message msg){
		for(int i = 0; i < connectionQueue.size(); i++){
			connectionQueue.get(i).enqueue(msg);
		}
	}
	
	public void notifySubscribers(){
		for(int i = 0; i < connectionQueue.size(); i++){
			connectionQueue.get(i).notifyClient();
		}
	}
}
