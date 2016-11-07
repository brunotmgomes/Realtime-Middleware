package chatclient;

public class Main {

	// client creates listener, that binds the infrastructure with the front end
	// listener is sent to clientRequest handler to send to the surface the
	// json data received on updates
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		RealtimeChannelProxy realtimeChannel = new RealtimeChannelProxy("chat1");
		realtimeChannel.subscribe(new ChatUpdateListener());
		
		
	}
	
	static class ChatUpdateListener implements ValueEventListener{

		@Override
		public void onDataUpdate(DataSnapshot data) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
