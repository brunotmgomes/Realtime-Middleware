package server.distribution;

import java.io.Serializable;

public interface ChannelDataHandler {

	/**
	 * called whenever there is an update to the channel
	 * @param data the update contents
	 * @return the response to the channel subscribers
	 */
	Serializable addData(Serializable data);
	
}
