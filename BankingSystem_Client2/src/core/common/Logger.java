package core.common;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

public class Logger {
	
	private static Logger instance;
	private static String logFileName = "logger.log";
	private static PrintWriter pw;
	
	private Logger() {}
	
	public static Logger getInstance() {
		if(instance == null) {
			System.out.println("Create logger.. ");
			instance = new Logger();
			try {
				pw = new PrintWriter(new FileOutputStream(new File(logFileName), true));
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		} 
		
		return instance;
	}
	
	public void log(String msg) {
		pw.println("[" + LocalTime.now() + "] : " + msg);
		pw.flush();
	}
}
