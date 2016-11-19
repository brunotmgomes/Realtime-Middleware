package queue;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import global.Marshaller;
import global.Message;


//Tem um id para o cliente e um socket para enviar as mensagens para o cliente.
public class ConnectionObject {
	private ArrayList<Message> queue = new ArrayList<Message>();
	private String id;
	Socket skt;
	
	public ConnectionObject(String id, Socket skt) {
		this.id = id;
		this.skt = skt;
	}
	
	public synchronized void enqueue(Message msg){ //adiciona uma mensagem a lista de mensagens desse cliente.
		queue.add(msg);
	}
	
	private synchronized void dequeue(Message msg){ //tira a mensagem da lista de mensagens desse cliente.
		queue.remove(msg);
	}
	
	public void notifyClient(){ //dispara uma nova thread para entregar todas as mensagens para o cliente.
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
 					DataOutputStream output = new DataOutputStream(skt.getOutputStream());

 					byte[] msgBytes = marshaller.marshall(msg);
 					int sentMessageSize = msgBytes.length;
 					output.writeInt(sentMessageSize);
 					output.write(msgBytes,0, sentMessageSize);
 					output.flush();
 					
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
	public void closeSocket() throws IOException{
		skt.close();
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
}
