package queue;

import java.util.ArrayList;

import global.Message;
import infrastructure.client.ClientRequestHandler;

public class ConnectionObject {
	private ArrayList<Message> queue = new ArrayList<Message>();
	private int id;
	private boolean isSending = true;
	
	ClientRequestHandler clq;
	
	public ConnectionObject(int id, ClientRequestHandler clq) {
		this.id = id;
		this.clq = clq;
	}
	
	public void enqueue(Message msg){
		queue.add(msg);
	}
	
	private void dequeue(Message msg){
		queue.remove(msg);
	}
	
	private synchronized void runNotify(){
		for(int i = 0; i < queue.size(); i++){
 			boolean messageSent = false;
 			while(!messageSent){
 				try{
 					Message msg = queue.get(i)
 					clq.send(msg);
 					dequeue(msg);
 					messageSent = true;
 				}catch(Exception e){
 					
 				}
 			}
 		}
	}
	
	public void notifyClient(){
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		    	 runNotify();
		     }
		});  
		t1.start();
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
}
