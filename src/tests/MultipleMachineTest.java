package tests;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import monitors.MacCloudMonitor;
import runables.FlagObserver;
import runables.TransferAndRun;

public class MultipleMachineTest {
	private static final int NUMBER_OF_REMOTE_MACHINES = 4;
	private String[] remoteAddresses ={ 
			"192.168.2.106",
			"192.168.2.34",
			"192.168.2.1",
			"192.168.2.105"};
	private String[] remoteUsers ={ 
			"vm_cloud_master\\Administrator",
			"vm_cloud_branch\\Administrator",
			"vm_cloud_releas\\Administrator",
			"vm_cloud_old\\Administrator"};
	
	private String remotePassword = "ExperiQA2027";
	private String srcPath = "c:\\myjars\\";
	private String dstPath = "myjars\\";
	private String jarName = "InstallLaunchRunMonitor4.jar";
	
	
	
	private TransferAndRun[] transferAndRun ;
	private FlagObserver[] flagObservers;
	private Thread[] launcherThreads;
	private Thread[] observerThreads;
	boolean visableUI = true;
	private long time0;
	private long time1;
	private Thread cloudMonitorThread;
	private MacCloudMonitor macCloudMonitor;
	private boolean isCloudAMac=true;

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

		//**********Cloud Monitor Set Up********\/

		if (isCloudAMac) {
			macCloudMonitor = new MacCloudMonitor("QA MAC 10", "192.168.2.11");
			macCloudMonitor.setSudoPassword("123456");
			macCloudMonitor.setServer(true);
			macCloudMonitor.setAgent(true);
			macCloudMonitor.restartAndLearn();

			System.out.println("Server PID : "+ macCloudMonitor.getServerPID());
			System.out.println("Agent PID : "+ macCloudMonitor.getAgentPID());

			cloudMonitorThread =new Thread (macCloudMonitor);
			macCloudMonitor.setOn();
			cloudMonitorThread.start();
		}
		//**********Cloud Monitor Set Up********^^





		transferAndRun =new TransferAndRun[NUMBER_OF_REMOTE_MACHINES];
		launcherThreads =new Thread[NUMBER_OF_REMOTE_MACHINES];
		flagObservers =new FlagObserver[NUMBER_OF_REMOTE_MACHINES];
		observerThreads =new Thread[NUMBER_OF_REMOTE_MACHINES];



		for (int i=0;i<NUMBER_OF_REMOTE_MACHINES;i++){
			transferAndRun[i]=new TransferAndRun(remoteAddresses[i], remoteUsers[i], remotePassword, srcPath, dstPath, jarName, (i+1));
			launcherThreads[i]=new Thread(transferAndRun[i]);
			//	String filepath=dstPath+"FinishFlag";
			flagObservers[i] = new FlagObserver(remoteAddresses[i], remoteUsers[i], remotePassword, dstPath, "FinishFlag");
			observerThreads[i] =new Thread(flagObservers[i]);
		}

		time0  = System.currentTimeMillis();







	}

	@After
	public void tearDown() throws Exception {

		for (Thread thread :observerThreads){
			thread.join();
			System.out.println(thread.getName()+ " is done ");
		}
		/*		System.out.println( "Click Ok To End Monitoring");
		JOptionPane.showConfirmDialog( null, "Click Ok To End Monitoring", "Monitoring", JOptionPane.PLAIN_MESSAGE );*/
	
		if (isCloudAMac) {
			System.out.println("Closing Cloud Monitor Thread");
			macCloudMonitor.setOff();
			cloudMonitorThread.join();
		}
		
		time1  = System.currentTimeMillis();
		long totalTime= time1-time0;
		long totalSeconds = totalTime/1000;
		long totalMinutes = totalSeconds/60;
		long seconds = totalSeconds%60;
		System.out.println("Time :"+totalMinutes+":"+seconds );

	}

	@Test
	public void test() throws IOException, InterruptedException {

		for (Thread thread :launcherThreads){
			thread.start();
		}
		for (Thread thread :launcherThreads){
			thread.join();
		}
		for (Thread thread :observerThreads){
			thread.start();
		}






	}

}
