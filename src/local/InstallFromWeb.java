package local;

import java.io.File;
import java.io.IOException;

import utils.Download;
import utils.RunCmd;

public class InstallFromWeb {

	public InstallFromWeb() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String url = "http://192.168.1.213:8090/guestAuth/repository/download/bt2/.lastSuccessful/SeeTest_windows_10_3_%7Bbuild.number%7D.exe";
		String file = System.getenv("Temp")+"\\SeeTest_windows.exe";
		Download.downolad(url, file);
		
		System.out.println("Download Complete");
		Thread.sleep(10000);
		System.out.println("Wait Complete");

		RunCmd.runCMD("Install "+file, file+" -q");
		File instalationFile =new File(file);
		instalationFile.delete();
	}

}
