package python;

import java.io.*;


public class PyExecuter {
	public static void main(String args[], String file) {

        String s = null;
        String path = System.getProperty("user.dir").concat("\\src\\python\\").concat(file);
        String in = "C:\\Users\\Joe\\Appdata\\Local\\Programs\\Python\\Python37-32\\python.exe ".concat(path);

        try {
            
            Process p = Runtime.getRuntime().exec(in);
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            System.out.println("Executable output (if any):\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            System.out.println("Error of the executable (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
        }
        catch (IOException e) {
            System.out.println("The program caught an exception:");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}