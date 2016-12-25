package remote;

import java.io.File;
import java.io.IOException;

import utils.RunCmd;
public class RemoteServiceManger {



	public RemoteServiceManger() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub


		String remoteAddress = "192.168.2.106";
		String remoteUser = "vm_cloud_master\\Administrator";
		String remotePassword = "Experitest2027";

		String serviceName = "ecaservice";

		RemoteServiceManger.remoteStopService(remoteAddress, remoteUser, remotePassword, serviceName);
		RemoteServiceManger.remoteStartService(remoteAddress, remoteUser, remotePassword, serviceName);



		//runCMD("Installing SeeTest","PsExec.exe \\\\192.168.2.106 -u vm_cloud_master\\Administrator -p Experitest2027 -f -c SeeTest_windows_10_3_3026_debug.exe -q");



	}

	/**
	 * @param remoteAddress
	 * @param remoteUser
	 * @param remotePassword
	 * @param serviceName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void remoteStopService(String remoteAddress, String remoteUser, String remotePassword,
			String serviceName) throws IOException, InterruptedException {
		String name = "Stoping  "+serviceName;
		String command = "PsExec.exe \\\\"
						 +remoteAddress
						 +" -u " +remoteUser
						 +" -p " +remotePassword
						 +" -s  net stop "+serviceName ;
		RunCmd.runCMD(name,command);
	}
	public static void remoteStartService(String remoteAddress, String remoteUser, String remotePassword,
			String serviceName) throws IOException, InterruptedException {
		String name = "Startin  "+serviceName +" on "+remoteAddress;
		String command = "PsExec.exe \\\\"
						 +remoteAddress
						 +" -u " +remoteUser
						 +" -p " +remotePassword
						 +" -s  net start "+serviceName ;
		RunCmd.runCMD(name,command);
	}


}
