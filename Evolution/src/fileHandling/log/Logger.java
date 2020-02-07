package fileHandling.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {

	static String logPath = System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt";
	static List<String> Log = new ArrayList<String>();
	
	public static File create(int tried){
		try {
			if (Files.isDirectory(Paths.get(System.getenv("APPDATA")+"\\Evolution\\logs")) == false) {
				Files.createDirectories(Paths.get(System.getenv("APPDATA")+"\\Evolution\\logs"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (tried == 0) {
			if (Files.exists(Paths.get(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt")) == true) {
				DateFormat dateFormat = new SimpleDateFormat("ss-mm-HH dd-MM-yyyy");
				String date = dateFormat.format(new Date());
				File f1 = new File(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt");
				File f2 = new File(System.getenv("APPDATA")+"\\Evolution\\logs\\Old Log "+date+".txt");
				f1.renameTo(f2);
				try {
					Files.createFile(Paths.get(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				int has_tried = 1;
				create(has_tried);
			}else {
				try {
					Files.createFile(Paths.get(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		File file = new File(logPath);
		return file;
	}
	
	public static void main(String e,Integer rule, File file) {
		try {
			if (rule == -1) {
				Buildlog(e);
				log(file, Log);
				Log.clear();
			}else {
				Buildlog(e);
			}
		} catch (IOException e2) {
			e2.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static void Buildlog(String exception) {
		Log.add(exception+"\n");
	}
	
	public static void log(File file, List<String> Log) throws IOException {
		Path path = FileSystems.getDefault().getPath(logPath);
		for (String string:Log) {
			byte[] strToBytes = string.getBytes();
			Files.write(path, strToBytes, StandardOpenOption.APPEND);
		}
	}
	
	public static void IOSevereErrorHandler(IOException e, File file) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		main("[SEVERE]"+exceptionAsString,-1,file);
		System.exit(-1);
	}
	
	public static void NullPointerSevereErrorHandler(NullPointerException e, File file) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		main("[SEVERE]"+exceptionAsString,-1,file);
		System.exit(-1);
	}
	
	public static void FileNotFoundSevereErrorHandler(FileNotFoundException e, File file) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		main("[SEVERE]"+exceptionAsString,-1,file);
		System.exit(-1);
	}

	public static void IOUnhealthyErrorHandler(IOException e, File file) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		main("[UNHEALTHY]"+exceptionAsString,-1,file);
		System.exit(-1);
	}
	
	public static void NullPointerUnhealthyErrorHandler(NullPointerException e, File file) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		main("[UNHEALTHY]"+exceptionAsString,-1,file);
		System.exit(-1);
	}
	
	public static void FileNotFoundUnhealthyErrorHandler(FileNotFoundException e, File file) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		main("[UNHEALTHY]"+exceptionAsString,-1,file);
		System.exit(-1);
	}
	
	public static void UnhealthyException(Exception e, File file) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		main("[UNHEALTHY]"+exceptionAsString,-1,file);
		System.exit(-1);
	}
}
