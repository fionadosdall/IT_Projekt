package de.hdm.softwareProjekt.kinoPlaner.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	
	public Anwender login(String requestUri);

}
