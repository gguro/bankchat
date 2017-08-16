package client;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Properties;

public class ClientProperties extends Properties {
	
	public static String fileName = "client.prop";
	
	public ClientProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void loadProperties() throws FileNotFoundException, IOException {
		super.load(new FileInputStream(fileName));
	}
	
	public void saveProperties() throws FileNotFoundException, IOException {
		String comments = "Banking Client Config File";
		super.store(new FileOutputStream(fileName), comments);
	}
	
}