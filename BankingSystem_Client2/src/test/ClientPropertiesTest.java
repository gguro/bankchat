package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import client.ClientProperties;

public class ClientPropertiesTest {
	public static void main(String[] args) {
		ClientProperties cprops = new ClientProperties();
		
		try {
			
			//cprops.saveProperties();
			cprops.loadProperties();
			System.out.println(cprops.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
