package launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class STAProccess extends Process {

	private static Process process;
	static  private String seeTestAutoPath="C:\\Program Files (x86)\\Experitest\\SeeTest";
	static private String launchInSTAConfirmationLine ="(imagestudio.ImageStudioApp) INFO 	Load time";	

	static private String seeTestAutoExecutable="studio.exe";
	static private LogReader logReader =null;
	static private String logDirStr =null;//"C:\\Users\\eyal.neumann\\AppData\\Roaming\\seetest";
	static private File lastLog;
	static private Thread launchMonitorThread=null;
	public STAProccess() throws IOException {
		this(false);
	}

	public STAProccess(boolean firstLaunch) throws IOException {
		// TODO Auto-generated constructor stub
		if (firstLaunch){
			STAProccess.process =  Runtime.getRuntime().exec(seeTestAutoPath+"\\"+seeTestAutoExecutable);
			System.out.println("Launching SeeTest for the first time");
			String appdatapath=System.getenv("Appdata");
			logDirStr = appdatapath+"\\seetest";
			lastLog = LogHandeler.findLatestLogFile(logDirStr);
			logReader=new LogReader(lastLog.getAbsolutePath(),launchInSTAConfirmationLine);
			launchMonitorThread=new Thread (logReader);
			launchMonitorThread.start();

		}
		else{
			String appdatapath=System.getenv("Appdata");
			logDirStr = appdatapath+"\\seetest";
			lastLog = LogHandeler.findLatestLogFile(logDirStr);

			LogHandeler.clearFile(lastLog);

			logReader=new LogReader(lastLog.getAbsolutePath(),launchInSTAConfirmationLine);
			launchMonitorThread=new Thread (logReader);
			launchMonitorThread.start();

			STAProccess.process =  Runtime.getRuntime().exec(seeTestAutoPath+"\\"+seeTestAutoExecutable);
			System.out.println("Launching SeeTest started");	
		}
			

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		STAProccess.process.destroy();

	}
	public void waitForLaunch() throws InterruptedException{
		launchMonitorThread.join();
		System.out.println("Launching SeeTest ended");

	}


	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return STAProccess.process.isAlive();
	}

	@Override
	public int exitValue() {
		// TODO Auto-generated method stub
		return STAProccess.process.exitValue();
	}

	@Override
	public InputStream getErrorStream() {
		// TODO Auto-generated method stub
		return STAProccess.process.getErrorStream();
	}

	@Override
	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		return STAProccess.process.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return STAProccess.process.getOutputStream();
	}

	@Override
	public int waitFor() throws InterruptedException {
		// TODO Auto-generated method stub
		return STAProccess.process.waitFor();
	}
	
	@Deprecated
	public void reset() throws IOException, InterruptedException{
		lastLog = LogHandeler.findLatestLogFile(logDirStr);
		LogHandeler.clearFile(lastLog);
		logReader=new LogReader(lastLog.getAbsolutePath(),launchInSTAConfirmationLine);
		Thread resetMonitorThread=new Thread (logReader);
		resetMonitorThread.start();

		STAProccess.process =  Runtime.getRuntime().exec(seeTestAutoPath+"\\"+seeTestAutoExecutable);
		resetMonitorThread.join();


	}


	public static void main(String[] args) throws IOException, InterruptedException {

		STAProccess.stopCloudAgent();
		/*		STAProccess.closeSeeTest();

		STAProccess sTAProccess= new STAProccess();

		int memoryUsage = sTAProccess.getMemoryUsage();
		System.out.println("Memory Usage :"+memoryUsage);
		sTAProccess.waitForLaunch();
		memoryUsage = sTAProccess.getMemoryUsage();
		System.out.println("Memory Usage :"+memoryUsage);
		Thread.sleep(10000);
		memoryUsage = sTAProccess.getMemoryUsage();
		System.out.println("Memory Usage :"+memoryUsage);*/





	}

	/**
	 * Only on Windows OS
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void closeSeeTest() throws IOException, InterruptedException {
		Process shutdown =Runtime.getRuntime().exec("TASKKILL /F /IM " + "studio.exe");
		System.out.println("Killing SeeTest started");
		shutdown.waitFor();
		System.out.println("Killing SeeTest ended");

	}

	/**
	 * Stopes Cloud Agent, requires proper authorization
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void stopCloudAgent() throws IOException, InterruptedException {
		Process stopping =Runtime.getRuntime().exec("net stop ecaservice");
		System.out.println("Stoping Cloud Agent started");
		stopping.waitFor();
		System.out.println("Stoping Cloud Agent ended");

	}
	/**
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public  int getMemoryUsage() throws IOException {
		String command = "powershell TASKLIST | findstr studio.exe";
		Process process =Runtime.getRuntime().exec(command);
		process.getOutputStream().close();
		String line;
		BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
		line=stdout.readLine();
		//System.out.println(line);
		String[] words =line.split(" +");
		int memoryUsage =Integer.parseInt(words[4].replaceAll(",", ""));
		return memoryUsage;
	}

	@Override
	public Process destroyForcibly() {
		// TODO Auto-generated method stub
		return STAProccess.process.destroyForcibly();
	}

	@Override
	public boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return STAProccess.process.waitFor(timeout, unit);
	}



	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return STAProccess.process.equals(obj);
	}


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return STAProccess.process.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return STAProccess.process.toString();
	}
	/*	private static int getMemoryUsage() throws IOException{
		String command = "TASKLIST | findstr studio.exe";
		 Executing the command
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		// Getting the results
		powerShellProcess.getOutputStream().close();
		String line;
		BufferedReader stdout = new BufferedReader(new InputStreamReader(
				powerShellProcess.getInputStream()));
		line = stdout.readLine();
		line = stdout.readLine();
		System.out.println(line);

		stdout.close();
		String[] sLine = line.split(" ");
		int i=0;
		for (String string:sLine){
			System.out.println((i++)+") "+string);
		}




		return 0;

	}*/

}
