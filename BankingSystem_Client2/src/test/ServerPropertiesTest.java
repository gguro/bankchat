package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import server.ServerProperties;

public class ServerPropertiesTest {
	public static void main(String[] args) {
		ServerProperties sprops = new ServerProperties();
		
		try {
			//cprops.loadProperties();
			sprops.saveProperties();
			System.out.println(sprops.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
