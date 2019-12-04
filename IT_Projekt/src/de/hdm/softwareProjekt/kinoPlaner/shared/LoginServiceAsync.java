package de.hdm.softwareProjekt.kinoPlaner.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;


public interface LoginServiceAsync {
	
	void login(String requestUri, AsyncCallback <Anwender> asyncCallback);

}
