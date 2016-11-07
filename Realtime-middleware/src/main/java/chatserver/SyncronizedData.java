package chatserver;

public interface SyncronizedData {

	/**
	 * saves the data
	 */
	public void onUpdated();
	
	/**
	 * returns
	 */
	public void getNewerThan(Object object);
	
	/**
	 * 
	 */
	
	
}
