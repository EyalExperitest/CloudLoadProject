package remote;

import java.io.File;
import java.io.IOException;

import utils.RunCmd;
public class RemoteInstaller {



	public RemoteInstaller() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub


		String remoteAddress = "192.168.2.106";
		String remoteUser = "vm_cloud_master\\Administrator";
		String remotePassword = "Experitest2027";

		String installationFile = "SeeTest_windows_10_3_3026_debug.exe";

		remoteInstall(remoteAddress, remoteUser, remotePassword, installationFile);



		//runCMD("Installing SeeTest","PsExec.exe \\\\192.168.2.106 -u vm_cloud_master\\Administrator -p Experitest2027 -f -c SeeTest_windows_10_3_3026_debug.exe -q");



	}

	/**
	 * @param remoteAddress
	 * @param remoteUser
	 * @param remotePassword
	 * @param installationFile
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void remoteInstall(String remoteAddress, String remoteUser, String remotePassword,
			String installationFile) throws IOException, InterruptedException {
		String name = "Installing "+installationFile+" on "+remoteAddress;
		String command = "PsExec.exe \\\\"
						 +remoteAddress
						 +" -u " +remoteUser
						 +" -p " +remotePassword
						 +" -f -c " +installationFile +" -q" ;
		RunCmd.runCMD(name,command);
	}


}
