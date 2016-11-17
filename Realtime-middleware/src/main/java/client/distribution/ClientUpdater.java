package client.distribution;

import client.infrastructure.ClientRequestHandler;
import global.Config;
import global.Marshaller;
import global.Message;
import global.datatypes.request.SubscribeMessage;
import global.datatypes.request.UnsubscribeMessage;
import global.datatypes.response.UpdateMessage;

public class ClientUpdater implements Runnable{

	private String channel;
	private volatile ChannelUpdateListener listener;
	
	private ClientRequestHandler handler;
	
	private volatile boolean stopFlag;
	
	// checks internet connection
	// checks for stop flag
	// keeps connection open until it is asked to close
	public ClientUpdater(String channel, ChannelUpdateListener listener){
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
		sendStartUpdateMessage();
		while(!stopFlag){
			mainUpdateLoop();			
		}
		sendStopUpdatesMessage();
	}
	
	public void mainUpdateLoop(){
		try {
			byte [] msgBytes = this.handler.receive();
			if(!stopFlag){
				Marshaller mrsh = new Marshaller();
				UpdateMessage message = (UpdateMessage) mrsh.unmarshall(msgBytes);
				this.listener.onNewData(message.body.object);
				//send ok back
			}	
		} catch (Exception e) {
			//reconnect if exception is from disconnection
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
	
	private void sendStartUpdateMessage(){
		SubscribeMessage msg = new SubscribeMessage(channel);
		sendMessage(msg);
	}
	
	private void sendStopUpdatesMessage(){
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
