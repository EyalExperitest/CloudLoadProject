package sandbox;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.jcraft.jsch.*;

import utils.RunCmd;


public class SandBox {
	public SandBox() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * Set the prompts, to find execution ends
	 */

	public static void main(String[] args) throws IOException, InterruptedException, JSchException {
		// TODO Auto-generated method stub
		Class classToExport = HelloWorld.class;
		String className = classToExport.getName();
		String destenationFile = "HelloWorld.jar";


		String manifestFileName = "myFile";
		File manifestFile= new File("manifestFiles\\"+manifestFileName+".mf");
		manifestFile.createNewFile();
		FileWriter fileWriter = new FileWriter(manifestFile.getPath());
		fileWriter.write("Main-Class: "+className+"\n" );
		fileWriter.close();

		System.out.println(className);
		String classPath =className.replaceAll("\\.", "/");
		System.out.println(classPath);
		String command="jar -cvmf "+manifestFile.getPath()+" "+destenationFile+" "+"*.class";
		System.out.println(command);
		RunCmd.runCMD("Create Runnable Jar", command);






/*		String ip = Inet4Address.getLocalHost().getHostAddress().replace("192.168", "").replace(".", "_");
		System.out.println(ip);*/











	}


}
