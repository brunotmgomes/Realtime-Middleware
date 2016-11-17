package chatserver;

public interface SyncronizedObject {

	/**
	 * saves the data
	 */
	public void receiveUpdate();
	
	/**
	 * returns
	 */
	public void broadcastUpdate(Object object);
	
	/**
	 * 
	 */
	
	
}
