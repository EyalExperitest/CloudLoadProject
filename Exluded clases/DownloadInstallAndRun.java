package runables;

import java.io.IOException;

import remote.RemoteRobocopy;
import remote.RomoteJarLauncher;

public class DownloadInstallAndRun implements Runnable {
	private String remoteAddress = "192.168.2.105";
	private String remoteUser = "vm_cloud_old\\Administrator";

	private String remotePassword = "Experitest2027";
	private String srcPath = "c:\\myjars\\";
	private String dstPath = "myjars\\";
	private String jarRemoteFolderPath = "C:\\myjars\\";
	private String jarName = "EribankAndroidTest13.jar";
	private boolean visableUI = true;

	public DownloadInstallAndRun(String remoteAddress,String remoteUser,String remotePassword,String srcPath,String dstPath,
			String jarRemoteFolderPath,String jarName,String installationFile ,boolean visableUI) {
		this.remoteAddress=remoteAddress;
		this.remoteUser=remoteUser;
		this.remotePassword=remotePassword;
		this.srcPath=srcPath;
		this.dstPath=dstPath;
		this.jarRemoteFolderPath=jarRemoteFolderPath;
		this.jarName=jarName;





	}

	@Override
	public void run() {
		Thread.currentThread().setName(" On "+this.remoteAddress);
		try {
			System.out.println("*************Copying jar to remote machine*************");
			RemoteRobocopy.remoteCopy(remoteAddress, remoteUser, remotePassword, srcPath, dstPath);
			System.out.println("*************Launching jar on remote machine*************");
			RomoteJarLauncher.remoteJarLaunch(remoteAddress, remoteUser, remotePassword, jarRemoteFolderPath, jarName, visableUI,false);
		} catch (InterruptedException | IOException e) {
 this we exclude this class from the rpoject

			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
