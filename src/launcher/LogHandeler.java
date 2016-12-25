package launcher;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public class LogHandeler {

	private static final String LOG_HEAD = "SeeTest-2016";

	public LogHandeler() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String logDirStr ="C:\\Users\\eyal.neumann\\AppData\\Roaming\\seetest";

		File lastLog = findLatestLogFile(logDirStr);
		System.out.println(lastLog.getName());

		clearFile(lastLog);





	}

	/**
	 * @param file
	 * @throws IOException
	 */
	public static void clearFile(File file) throws IOException {
		file.delete();
		file.createNewFile();
	}

	/**
	 * @param logDirStr
	 * @return Latest log file
	 */
	public static File findLatestLogFile(String logDirStr) {
		File logDir =new File(logDirStr);
		while (!logDir.exists()){
			System.out.println("SeeTest AppData Folder not yet created");
			waitTime();
		}

		File[] files =logDir.listFiles();
		SortedSet<File> logFiles=new  TreeSet<File>();

		while (logFiles.isEmpty()){
			
			for(File file:files){
				//System.out.println(file.getName());
				
				if(file.getName().startsWith(LOG_HEAD)){
					logFiles.add(file);
				}


			}
			if (logFiles.isEmpty()){
				System.out.println("Log File Not yet created");
				waitTime();
				files =logDir.listFiles();
			}
		}


		File lastLog =logFiles.last();
		return lastLog;
	}

	/**
	 * 
	 */
	private static void waitTime() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}