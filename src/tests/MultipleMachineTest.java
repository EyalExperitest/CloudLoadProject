package tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import runables.DownloadInstallAndRun;

public class MultipleMachineTest {
	private static final int NUMBER_OF_REMOTE_MACHINES = 4;

	//	private String remoteAddress = "192.168.2.106";
	//	private String remoteAddress = "192.168.2.34";
	//	private String remoteAddress = "192.168.2.1";
	//	private String remoteAddress = "192.168.2.105";
	//	private String remoteAddress = "192.168.2.104";
	private String[] remoteAddresses ={ "192.168.2.106",
			"192.168.2.34",
			"192.168.2.1",
			"192.168.2.105"};

	//	private String remoteUser = "vm_cloud_master\\Administrator";
	//	private String remoteUser = "vm_cloud_branch\\Administrator";
	//  private String remoteUser = "vm_cloud_releas\\Administrator";
	//	private String remoteUser = "vm_cloud_old\\Administrator";
	//	private String remoteUser = "vm_cloud_older\\Administrator";
	private String[] remoteUsers ={ "vm_cloud_master\\Administrator",
			"vm_cloud_branch\\Administrator",
			"vm_cloud_releas\\Administrator",
			"vm_cloud_old\\Administrator"};
	private String remotePassword = "Experitest2027";
	private String srcPath = "c:\\myjars\\";
	private String dstPath = "myjars\\";
	private String jarRemoteFolderPath = "C:\\myjars\\";
	private String jarName = "InstallLaunchRunMonitor.jar";
	private String installationFile = "SeeTest_windows_10_3_3026_debug.exe";
	private DownloadInstallAndRun[] downloadInstallAndRun ;
	private Thread[] threads;
	boolean visableUI = true;
	private long time0;
	private long time1;
	/*public InstallAndRun(
	 * String remoteAddress,
	 * String remoteUser,
	 * String remotePassword,
	 * String srcPath,
	 * String dstPath,
	 * String jarRemoteFolderPath,
	 * String jarName,
	 * String installationFile ,
	 * boolean visableUI) {
	 *  
	 */
	@Before
	public void setUp() throws Exception {
		downloadInstallAndRun =new DownloadInstallAndRun[5];
		threads =new Thread[NUMBER_OF_REMOTE_MACHINES];


		for (int i=0;i<NUMBER_OF_REMOTE_MACHINES;i++){
			downloadInstallAndRun[i]=new DownloadInstallAndRun(remoteAddresses[i], remoteUsers[i], remotePassword, srcPath, dstPath, jarRemoteFolderPath, jarName, installationFile, visableUI);
			threads[i]=new Thread(downloadInstallAndRun[i]);
		}

		 time0  = System.currentTimeMillis();





	}

	@After
	public void tearDown() throws Exception {
		 time1  = System.currentTimeMillis();
		 long totalTime= time1-time0;
		 long totalSeconds = totalTime/1000;
		 long totalMinutes = totalSeconds/60;
		 long seconds = totalSeconds%60;
		 System.out.println("Time :"+totalMinutes+":"+seconds );

	}

	@Test
	public void test() throws IOException, InterruptedException {

		for (Thread thread :threads){
			thread.start();
		}
		for (Thread thread :threads){
			thread.join();
		}



	}

}
