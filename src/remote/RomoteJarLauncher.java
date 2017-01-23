package remote;

import java.io.IOException;

import utils.RunCmd;

public class RomoteJarLauncher {
	public static void main(String[] args) throws InterruptedException, IOException {
		String remoteAddress = "192.168.2.106";
		String remoteUser = "vm_cloud_master\\Administrator";
		String remotePassword = "Experitest2027";

		String jarRemoteFolderPath = "C:\\myjars\\";
		String jarName = "InstallLaunchRun.jar";
		boolean visableUI = true;
		boolean waitForFinish =false;

		/*add arg[0] because should be called with the user serial number*/
		remoteJarLaunch(remoteAddress, remoteUser, remotePassword, jarRemoteFolderPath, jarName, visableUI,waitForFinish, args[0]);
	}


	/**
	 * @param remoteAddress
	 * @param remoteUser
	 * @param remotePassword
	 * @param jarRemoteFolderPath
	 * @param jarName
	 * @param visableUI
	 * @param waitForFinish 
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public static void remoteJarLaunch(String remoteAddress, String remoteUser, String remotePassword,
			String jarRemoteFolderPath, String jarName, boolean visableUI, boolean waitForFinish, String userNumber) throws IOException, InterruptedException {
		String jarPath = jarRemoteFolderPath+jarName;
		String iFlag = visableUI?" -i ":"";
		String dFlag = waitForFinish ?"":" -d ";

		String command = "PsExec.exe \\\\"
						 +remoteAddress
						 +iFlag
						 +" -u " +remoteUser
						 +" -p " +remotePassword
						 +dFlag
						 +" java -jar "
						 +"\""+jarPath + "\" "
						 + userNumber ;
		String name = "Running jar "+jarName+" on "+remoteAddress;
		RunCmd.runCMD(name,command);
	}


	public RomoteJarLauncher() throws IOException, InterruptedException {
		// TODO Auto-generated constructor stub
		

		
		
		
		
	}
	public static void remoteJarLaunch(String remoteAddress, String remoteUser, String remotePassword, String jarRemoteFolderPath, String jarName, boolean visableUI, boolean waitForFinish) throws IOException, InterruptedException {
		remoteJarLaunch(remoteAddress,remoteUser,remotePassword,jarRemoteFolderPath,jarName,visableUI,waitForFinish,"");
	}
}
