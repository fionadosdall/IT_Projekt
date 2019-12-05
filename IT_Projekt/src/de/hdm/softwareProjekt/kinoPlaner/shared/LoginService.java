package de.hdm.softwareProjekt.kinoPlaner.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

public interface LoginService extends RemoteService {
	

	public Anwender login(String requestUri);

}
