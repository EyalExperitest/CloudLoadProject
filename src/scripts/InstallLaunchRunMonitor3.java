/**
 * 
 */
package scripts;

import java.io.File;
import java.io.IOException;
import com.experitest.client.Client;

import basictests.devicetester.DeviceTest;
import launcher.STAProccess;
import launcher.STproperties;
import monitors.ProcessMonitor;
import utils.Download;
import utils.OpenHTML;
import utils.RunCmd;

/**
 * @author eyal.neumann
 *
 */
public class InstallLaunchRunMonitor3 {

	private static final int DEVICES_PER_USER = 2;

	private static final String SHAERD_FOLDER = "C:\\myjars\\";

	/**
	 * @throws IOException 
	 * @throws InterruptedException
	 * 
	 */
	//private static final String APP_NAME = "com.experitest.ExperiBank/.LoginActivity";
	private static final String APP_NAME = "cloud:com.experitest.ExperiBank/.LoginActivity";

	private static String host = "localhost";
	private static int port = 8889;
	private static String projectBaseDirectory = "C:\\Users\\eyal.neumann\\workspace\\project38";
	protected static Client client = null;
	private static STAProccess process;
	private static long t0;
	private static long t1;

	public InstallLaunchRunMonitor3()  {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		t0=System.currentTimeMillis();
		boolean installFlag=true;
		try {
			if (installFlag) {
				System.out.println("Downloading Latest master version");
				String url = "http://192.168.1.213:8090/guestAuth/repository/download/bt2/.lastSuccessful/SeeTest_windows_11_4_%7Bbuild.number%7D.exe";
				String file = System.getenv("Temp") + "\\SeeTest_windows.exe";
				Download.downolad(url, file);
				STAProccess.closeSeeTest();
				System.out.println("Installing SeeTest");
				RunCmd.runCMD("Install " + file, file + " -q");
				File instalationFile = new File(file);
				System.out.println("Deleting Instalation File");
				instalationFile.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//*********** First Launch************

		try {
			STAProccess.closeSeeTest();
			STAProccess.stopCloudAgent();


			File seeTestAtAppData = new File(System.getenv("Appdata")+"\\seetest");

			if(seeTestAtAppData.exists() && seeTestAtAppData.isDirectory()){
				System.out.println("***********First Launch has already been done************");	
			}
			else{
				System.out.println("***********First Launch************");
				STAProccess process0= new STAProccess(true);
				process0.waitForLaunch();
				(new Client(host, port, true)).exit();
				process0.waitFor();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//*********** Second Launch************
		try {
			System.out.println("***********Launch************");
			STproperties.setCloudIP("qacloud.experitest.com");
			STproperties.setCloudPort(443);
			/* username number is recived from the jar execution comman*/
			STproperties.setCloudUser("user"+args[0]);
			STproperties.setDeviceNumber(DEVICES_PER_USER);

			STproperties.readySeeTest();
			process= new STAProccess(false);
			process.waitForLaunch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//******** Starting Monitor Thread**************
			String processName = "studio";

			Thread thread;
			ProcessMonitor processMonitor=new ProcessMonitor(processName);
			processMonitor.setPath(SHAERD_FOLDER);
			thread =new Thread(processMonitor);
			thread.start();
			
			
			//**********Starting Tests *******
			
			
			DeviceTest[] deviceTests =new DeviceTest[DEVICES_PER_USER];
			Thread[] testThreads = new Thread[DEVICES_PER_USER];
			
			for (int i=0;i<deviceTests.length;i++){
				String query=(i%3==0)?"@os='ios' and @remote='true'":(i%3==1)?"@os='android' and @remote='true'":"";
				deviceTests[i]=new DeviceTest(new Client(host, port, true),query);
				testThreads[i]=new Thread(deviceTests[i]);
				testThreads[i].setName("Thread "+query.replaceAll("and @remote='true'","").replaceAll("@os=", "").replaceAll("'","")+" "+i);
				testThreads[i].start();
				
			}
			
			for (int i=0;i<deviceTests.length;i++){
				testThreads[i].join();
			}

			
			
			
			
			
			
			
			
			



			//*************Stopping Monitor Thread ******* 
			processMonitor.setOff();
			thread.join();
			
			//********************************************
			process.destroy();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//*************Creating Finish Flag File **************
		
		String flagFilePath=SHAERD_FOLDER+"FinishFlag";
		File flagFile =new File(flagFilePath);
		flagFile.createNewFile();
		
		

	}



}
