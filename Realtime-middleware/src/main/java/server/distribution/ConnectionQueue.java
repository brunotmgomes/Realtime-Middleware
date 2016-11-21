package server.distribution;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import global.Marshaller;
import global.datatypes.messages.Message;
import server.infrastructure.ServerRequestHandler;

public class ConnectionQueue {
	
	private ArrayList<Message> queue;
	private String id;
	
	private Socket skt;
	
	public ConnectionQueue(String id, Socket skt) {
		this.queue = new ArrayList<Message>();
		this.id = id;
		this.skt = skt;
	}
	
	
	/**
	 * adiciona uma mensagem a lista de mensagens desse cliente.
	 * @param msg
	 */
	public synchronized void enqueue(Message msg){ 
		queue.add(msg);
	}
	
	/**tira a mensagem da lista de mensagens desse cliente.
	 * 
	 * @param msg
	 */
	private synchronized void dequeue(Message msg){
		queue.remove(msg);
	}
	
	/**
	 * dispara uma nova thread para entregar todas as mensagens para o cliente.
	 */
	public void notifyClient(){ 
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		    	 runNotify();
		     }
		});  
		t1.start();
	}
	
	private synchronized void runNotify(){ //envia as mensagens para o cliente, caso não conssiga, fica tentando até conseguir.
		Marshaller marshaller = new Marshaller();
		for(int i = 0; i < queue.size(); i++){
 			boolean messageSent = false;
 			while(!messageSent){
 				try{
 					Message msg = queue.get(i);
 					byte[] msgBytes = marshaller.marshall(msg);
 					ServerRequestHandler handler = new ServerRequestHandler(skt);
 					handler.send(msgBytes);
 					dequeue(msg);
 					messageSent = true;
 				}catch(IOException e){
 					//se der exceção, vai voltar pro loop e tentar enviar novamente.
 				}
 			}
 		}
	}
	
	//serve para setar o novo socket na hora do resubscribe
		public void setSocket(Socket skt){
			this.skt = skt;
		}
		
		//fecha o socket na hora do unsubscribe.
		public void closeConnection() throws IOException{
			skt.close();
		}
		
		public String getId(){
			return id;
		}
		
		public void setId(String id){
			this.id = id;
		}
	
	
	

}
