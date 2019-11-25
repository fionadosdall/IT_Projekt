package de.hdm.softwareProjekt.kinoPlaner.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

//Hier wird die Verbindung zu unserer Datenbank verwaltet. 

public class DBConnection {

	/**
	 *  Diese Klasse wird nur einmal instanziiert. 
	 *  Die Klassenvariable con ist static, damit sie NUR EINMAL für sämtliche Instanzen der DB Connection-Klasse vorkommt. 
	 *  
	 */
	
	private static Connection con = null; 
	
	private static String googleURL = null; 
	private static String localURL = null; 
	
	public static Connection connection() {
        // Wenn bisher noch keine Verbindung zur DB aufgebaut wurde: 
//        if (con == null) {
//            String url = null;
//            try {
//                
//                } else {
//                    
//                }
//                con = DriverManager.getConnection(url);
//            } catch (Exception e) {
//                con = null;
//                e.printStackTrace();
//                throw new RuntimeException(e.getMessage());
//            }
//        }
        return con;
    }
}
