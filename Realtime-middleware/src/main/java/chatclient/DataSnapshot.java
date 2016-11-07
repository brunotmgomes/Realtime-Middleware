package chatclient;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import global.exceptions.IncompatibleClassException;

public class DataSnapshot {

	private String json;
	
	public <T> T getValue(java.lang.Class<T> aClass){
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = mapper.readValue(json, aClass);
		} catch (IOException e) {
			throw new IncompatibleClassException();
		}
		return object;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	
	
}
