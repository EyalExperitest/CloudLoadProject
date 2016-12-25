package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class RunCmd {

	public RunCmd() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
	


	}

	/**
	 * @param name
	 * @param command
	 * @throws IOException
	 * @throws InterruptedException
	 * Print a command and runs it in CMD , print output to console and wait for process to end, returns void
	 */


	public static void runCMD(String name, String command) throws IOException, InterruptedException {
		Process process =RunCmd.runCMD(command);
		ProcessReader processReader =new ProcessReader(process, name);
		Thread thread =new Thread(processReader);
		thread.start();
		process.waitFor();
	}

	/**
	 * @param command
	 * @throws IOException
	 * @returns Process
	 * Print a command and runs it in CMD, Return the Process
	 */
	public static Process runCMD(String command) throws IOException {
		System.out.println(command);

		return Runtime.getRuntime().exec(command);
	}

}
