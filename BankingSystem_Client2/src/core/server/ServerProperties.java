package core.server;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Properties;

public class ServerProperties extends Properties {
	
	public static String fileName = "system.prop";
	
	public ServerProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void loadProperties() throws FileNotFoundException, IOException {
		super.load(new FileInputStream(fileName));
	}
	
	public void saveProperties() throws FileNotFoundException, IOException {
		String comments = "Banking Server Config File";
		super.store(new FileOutputStream(fileName), comments);
	}
	
}