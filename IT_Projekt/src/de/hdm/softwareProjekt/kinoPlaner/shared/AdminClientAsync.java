package de.hdm.softwareProjekt.kinoPlaner.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public interface AdminClientAsync {
	
	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void erstellenAnwender(String name, String gmail, AsyncCallback<Anwender> callback)
			throws IllegalArgumentException;
	
	public void getAnwender(AsyncCallback<Anwender> callback) throws IllegalArgumentException;

	public void setAnwender(Anwender anwender, AsyncCallback<Void> callback) throws IllegalArgumentException;


}
