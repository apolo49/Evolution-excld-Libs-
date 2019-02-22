package python;

import java.io.*;


public class PyExecuter {
	public static void main(String args[], String file) {

        String s = null;
        String path = System.getProperty("user.dir").concat("\\src\\python\\").concat(file); //locates desired file to run
        String in = "%localappdata%\\Programs\\Python\\Python37-32\\python.exe ".concat(path); //locates python.exe and builds command to run file

        try {
            Process p = Runtime.getRuntime().exec(in); //executes the command in windows directly
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream())); //get input stream and save to buffer
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream())); //get error stream and save to buffer
            
            while ((s = stdInput.readLine()) != null) { //while loop for while there is an input stream
                System.out.println(s); //prints input stream
            }
            while ((s = stdError.readLine()) != null) { //while loop for while there is an error stream
            	System.out.println("The system has caught an error in the python code:\n");
                System.out.println(s); //prints error stream
            }
            
        }
        catch (IOException e) { //catches exception and stops the program to prevent breaks.
            System.out.println("The program caught an exception:");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
