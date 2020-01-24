package de.hdm.softwareProjekt.kinoPlaner.server;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwareProjekt.kinoPlaner.server.db.AnwenderMapper;
import de.hdm.softwareProjekt.kinoPlaner.shared.LoginService;
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

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	
	

	private static final long serialVersionUID = 1L;

	@Override
	public Anwender login(String requestUri) {
		// TODO Auto-generated method stub
		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User googleUser = userService.getCurrentUser(); 
		
		Anwender anwender = new Anwender();
		
		
		
		/**
		 * Wenn der <code>User</code> mit seinem Gooogle Account eingeloggt ist, wird überprüft,
		 * ob dieser unserem System bekannt ist
		 */

		if (googleUser != null) {
			
			Anwender existA = AnwenderMapper.anwenderMapper().findByGmail(googleUser.getEmail());
			
		
		/**
		 * Falls der User dem System bekannt ist, wird dieser eingeloggt
		 */

		if (existA != null) {
			
			existA.setIstEingeloggt(true);
			existA.setLogoutUrl(userService.createLogoutURL(requestUri));
			
			
			return existA;
		}
		
		/**
		 * Falls der <code>User</code> sich zum ersten Mal im System anmeldet
		 * wird ein neuer Datensatz in dei Datenbank geschrieben
		 */

		anwender.setIstEingeloggt(true);
		anwender.setLogoutUrl(userService.createLogoutURL(requestUri));
		anwender.setGmail(googleUser.getEmail());
		anwender.setName("Null");
		AnwenderMapper.anwenderMapper().insert(anwender);
		}
		
		anwender.setLoginUrl(userService.createLoginURL(requestUri));
	
	return anwender;


		
	}

}

