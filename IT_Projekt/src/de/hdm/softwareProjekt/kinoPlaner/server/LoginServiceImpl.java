package de.hdm.softwareProjekt.kinoPlaner.server;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwareProjekt.kinoPlaner.server.db.AnwenderMapper;
import de.hdm.softwareProjekt.kinoPlaner.shared.LoginServiceAsync;
import de.hdm.softwareProjekt.kinoPlaner.shared.bo.Anwender;

/** 
 * Hier wird die Login-Methode implementiert.
 * Diese prüft ob ein <code>User</code> dem System beaknnt ist.
 * Ist dies der Fall, werden die Attribute des  <code>User</code> gesetzt.
 * Anderenfalls wird ein neuer Datensatz in die Datenbank geschrieben und
 * der <code>User</code> eingeloggt.
 * Ist der <code>User</code> nicht mit seinem Google Account eingeloggt
 * wird ein LoginLInk für das GoogleUserServiceAPI erstellt
 * @author fiona 
 *
 */

public class LoginServiceImpl extends RemoteServiceServlet implements LoginServiceAsync {

	private static final long serialVersionUID = 1L;
	
	public Anwender login(String requestUri) {
	/**
		AnwenderService anwenderService = AnwenderServiceFactory.getAnwenderService();
		com.google.appengine.api.users.Users googleUser = AnwenderService.getCurrentAnwender();
		
		Anwender anwender = new Anwender();
		
		
		/**
		 * Wenn der <code>User</code> mit seinem Gooogle Account eingeloggt ist, wird überprüft,
		 * ob dieser unserem System bekannt ist
		 */
	/**
		if (googleUser != null) {
			
			Anwender existA = AnwenderMapper.anwenderMapper().findByGmail(googleUser.getEmail());
			
		
		/**
		 * Falls der User dem System bekannt ist, wird dieser eingeloggt
		 */
		/**
		if (existA != null) {
			
			existA.setIstEingeloggt(true);
			existA.setLogoutUrl(anwenderService.creatLogoutURL(requestUri));
			
			
			return existA;
		}
		
		/**
		 * Falls der <code>User</code> sich zum ersten Mal im System anmeldet
		 * wird ein neuer Datensatz in dei Datenbank geschrieben
		 */
		/**
		anwender.setIstEingeloggt(true);
		anwender.setLogoutUrl(anwenderService.createLogoutURL(requestUri));
		anwender.setGmail(googleUser.getEmail());
		AnwenderMapper.anwenderMapper().insert(anwender);
		}
		
	anwender.setLoginUrl(anwenderService.createLoginURL(requestUri));
	
	return anwender;
	**/
		return null;
	}

	@Override
	public void login(String requestUri, AsyncCallback<Anwender> asyncCallback) {
		// TODO Auto-generated method stub
		
	}
}
