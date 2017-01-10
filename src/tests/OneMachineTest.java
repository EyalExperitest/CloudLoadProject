package tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import remote.RemoteInstaller;
import remote.RemoteRobocopy;
import remote.RomoteJarLauncher;

public class OneMachineTest {
//	private String remoteAddress = "192.168.2.106";
//	private String remoteAddress = "192.168.2.34";
//	private String remoteAddress = "192.168.2.1";
	private String remoteAddress = "192.168.2.105";
//	private String remoteAddress = "192.168.2.104";


//	private String remoteUser = "vm_cloud_master\\Administrator";
//	private String remoteUser = "vm_cloud_branch\\Administrator";
//  private String remoteUser = "vm_cloud_releas\\Administrator";
	private String remoteUser = "vm_cloud_old\\Administrator";
//	private String remoteUser = "vm_cloud_older\\Administrator";

	private String remotePassword = "Experitest2027";
	private String srcPath = "c:\\myjars\\";
	private String dstPath = "myjars\\";
	private String jarRemoteFolderPath = "C:\\myjars\\";
	private String jarName = "EribankAndroidTest13.jar";
	boolean visableUI = true;


	@Before
	public void setUp() throws Exception {
		
		
		



		
		
		
	}

	@After
	public void tearDown() throws Exception {
		
		
		
		
		
		
	}

	@Test
	public void test() throws IOException, InterruptedException {
		
/**/	
		
		String installationFile = "SeeTest_windows_10_3_3026_debug.exe";
		System.out.println("*************Installing SeeTest on remote machine*************");
		RemoteInstaller.remoteInstall(remoteAddress, remoteUser, remotePassword, installationFile);
		System.out.println("*************Stoping Agent service*************");
		//RemoteServiceManger.remoteStopService(remoteAddress, remoteUser, remotePassword, "ecaservice");
		Thread.sleep(10000);
		System.out.println("*************Copying jar to remote machine*************");
		RemoteRobocopy.remoteCopy(remoteAddress, remoteUser, remotePassword, srcPath, dstPath);
		System.out.println("*************Launching jar on remote machine*************");
		RomoteJarLauncher.remoteJarLaunch(remoteAddress, remoteUser, remotePassword, jarRemoteFolderPath, jarName, visableUI,true);
		
		
		
		
	}

}
