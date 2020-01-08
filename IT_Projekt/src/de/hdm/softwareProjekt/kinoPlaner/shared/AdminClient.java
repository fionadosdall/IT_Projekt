package de.hdm.softwareProjekt.kinoPlaner.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

@RemoteServiceRelativePath("adminClient")
public interface AdminClient extends RemoteService{
	
	public void init() throws IllegalArgumentException;
	
	public Anwender erstellenAnwender(String name, String gmail) throws IllegalArgumentException;

}
