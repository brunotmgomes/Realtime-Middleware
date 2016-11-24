package client.distribution;

import java.io.IOException;

import client.infrastructure.ClientRequestHandler;
import global.Config;
import global.Marshaller;
import global.datatypes.messages.Message;
import global.datatypes.messages.NotifyMessage;
import global.datatypes.messages.SubscribeMessage;
import global.datatypes.messages.SubscribeResponseMessage;
import global.datatypes.messages.UnsubscribeMessage;
import global.datatypes.messages.components.MessageBody;
import global.datatypes.messages.components.MessageHeader;
import global.datatypes.messages.components.MessageType;
import global.exceptions.ConnectionDownException;

public class ChannelSubscriptionHandler implements Runnable{

	private String channel;
	private String myClientId;
	private volatile ChannelUpdateListener listener;
	
	private ClientRequestHandler handler;
	
	private volatile boolean stopFlag;
	
	// checks internet connection
	// checks for stop flag
	// keeps connection open until it is asked to close
	public ChannelSubscriptionHandler(String channel, ChannelUpdateListener listener){
		this.channel = channel;
		this.listener = listener;
		this.stopFlag = false;
	}
	
	public void setListener(ChannelUpdateListener listener){
		this.listener = listener;
	}
	
	//send listen message to server and pass updates to listener
	//closes connection when done
	@Override
	public void run() {
		initHandler();
		startChannelUpdates();
		while(!stopFlag){
			mainUpdateLoop();			
		}
		sendUnsubscribeMessage();
	}
	
	private void mainUpdateLoop(){
		try {
			byte [] msgBytes = this.handler.receive();
			if(!stopFlag){			
				Marshaller mrsh = new Marshaller();
				NotifyMessage message = (NotifyMessage) mrsh.unmarshall(msgBytes);
				this.listener.onNewData(message.body.object);
				//send ok back
			}	
		} catch (ConnectionDownException e) {
			System.out.println("info - Connection to server lost");
			reconnect();
		} catch(IOException e){
			e.printStackTrace();
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void stopUpdates(){
		stopFlag = true;
	}

	
	private void initHandler() {
		try {
			this.handler = new ClientRequestHandler(Config.HOST, Config.PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startChannelUpdates(){
		SubscribeMessage msg = new SubscribeMessage(channel);
		sendMessage(msg);
		waitIdFromServer();
	}
	
	private void waitIdFromServer(){
		try {
			byte [] msgBytes = this.handler.receive();
			Marshaller mrsh = new Marshaller();
			SubscribeResponseMessage message = (SubscribeResponseMessage) mrsh.unmarshall(msgBytes);
			myClientId  = (String) message.body.object;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void reconnect(){
		initHandler();
		Message msg = new Message();
		msg.header = new MessageHeader();
		msg.header.messageType = MessageType.RESUBSCRIBE;
		msg.header.channel = channel;
		msg.body = new MessageBody();
		msg.body.object = myClientId;
		
		sendMessage(msg);
	}
	
	private void sendUnsubscribeMessage(){
		UnsubscribeMessage msg = new UnsubscribeMessage();
		sendMessage(msg);
		//espera resposta, fecha conexao
	}
	
	private void sendMessage(Message msg){
		Marshaller mrsh = new Marshaller();
		byte[] msgBytes;
		try {
			msgBytes = mrsh.marshall(msg);
			handler.send(msgBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	



	
	
}
