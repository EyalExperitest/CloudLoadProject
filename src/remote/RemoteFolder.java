package remote;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

import utils.RunCmd;

public class RemoteFolder implements Closeable {
	private String address;
	private String user;
	private String password;
	private boolean isOpen=false;






	public static void main(String[] args) throws InterruptedException {


		String remoteAddress = "192.168.2.106";
		String remoteUser = "vm_cloud_master\\Administrator";
		//String machineName = "vm_cloud_master";
		String remotePassword = "ExperiQA2027";
		String path = "myjars";
		String fileName = "FinishFlag.txt";

	
		createEmptyFileInRemoteFolder(remoteAddress, remoteUser, remotePassword, path, fileName);
		Thread.sleep(5000);
		
		
		
		boolean isFlagInFolder = isFileInRemoteFolder(remoteAddress, remoteUser, remotePassword, path, fileName);
		System.out.println("isFlagInFolder : "+isFlagInFolder);

		if (isFlagInFolder){
			deleteFileInRemoteFolder(remoteAddress, remoteUser, remotePassword, path, fileName);
		}
	
		boolean isFlagInFolder2 = isFileInRemoteFolder(remoteAddress, remoteUser, remotePassword, path, fileName);
		System.out.println("isFlagInFolder : "+isFlagInFolder2);





	}




	/**
	 * @param remoteAddress
	 * @param remoteUser
	 * @param remotePassword
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static boolean isFileInRemoteFolder(String remoteAddress, String remoteUser, String remotePassword,
			String path, String fileName) {
		boolean isFileInFolder=false;
		try (	RemoteFolder remoteFolder =new RemoteFolder(remoteAddress, remoteUser, remotePassword);){
			isFileInFolder=remoteFolder.isFileInFolder(path, fileName);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isFileInFolder;
	}

	public static void deleteFileInRemoteFolder(String remoteAddress, String remoteUser, String remotePassword,
			String path, String fileName) {
		try (	RemoteFolder remoteFolder =new RemoteFolder(remoteAddress, remoteUser, remotePassword);){
			remoteFolder.deleteFileInFolder(path, fileName);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createEmptyFileInRemoteFolder(String remoteAddress, String remoteUser, String remotePassword,
			String path, String fileName) {
		try (	RemoteFolder remoteFolder =new RemoteFolder(remoteAddress, remoteUser, remotePassword);){
			remoteFolder.createEmptyFileInFolder(path, fileName);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @param path
	 * @param fileName
	 * @param remoteFolder
	 * @throws IOException
	 */
	private  boolean isFileInFolder(String path, String fileName) throws IOException {
		boolean isFileInFolder=false;
		String command="powershell dir \\\\"+this.address+"\\"+path;

		//System.out.println(command);
		Process process = Runtime.getRuntime().exec(command);

		process.getOutputStream().close();
		String currentLine;
		BufferedReader stdout = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		while (process.isAlive() /*&& processLine==null*/){
			currentLine = stdout.readLine();
			//System.out.println(currentLine);
			if (currentLine!=null){	
				if (currentLine.contains(fileName)){

					isFileInFolder=true;

				}
			}
		}
		return isFileInFolder;
	}

	private  void deleteFileInFolder(String path, String fileName) throws IOException {
		String command="powershell del \\\\"+this.address+"\\"+path+"\\"+fileName;

		System.out.println(command);
		Runtime.getRuntime().exec(command);

	}
	private  void createEmptyFileInFolder(String path, String fileName) throws IOException {
		String command="fsutil file createnew \\\\"+this.address+"\\"+path+"\\"+fileName+" 0";
		System.out.println(command);
		Runtime.getRuntime().exec(command);

	}

	/**
	 * @param remoteAddress
	 * @param remoteUser
	 * @param remotePassword
	 * @param srcPath
	 * @param dstPath
	 * @throws InterruptedException 
	 */





	private RemoteFolder(String address,String user,String password ) throws IOException {
		this.address=address;
		this.user=user;
		this.password=password;

		String command = "net use \\\\"+address+" /user:"+user+" "+password;
		 Runtime.getRuntime().exec(command);
		//RunCmd.runCMD(command);
		this.isOpen=true;
		// TODO Auto-generated constructor stub
	}







	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		if (isOpen) {
			this.close();
		}
		super.finalize();
	}




	@Override
	public void close() throws IOException {
		String command = "net use /D \\\\"+address;
		 Runtime.getRuntime().exec(command);
		//RunCmd.runCMD(command);
		this.isOpen=false;

	}

}
