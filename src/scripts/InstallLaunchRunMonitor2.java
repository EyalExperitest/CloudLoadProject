/**
 * 
 */
package scripts;

import java.io.File;
import java.io.IOException;
import com.experitest.client.Client;

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
public class InstallLaunchRunMonitor2 {

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

	public InstallLaunchRunMonitor2()  {
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
				String url = "http://192.168.1.213:8090/guestAuth/repository/download/bt2/.lastSuccessful/SeeTest_windows_10_4_%7Bbuild.number%7D.exe";
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
			STproperties.setCloudIP("192.168.2.11");
			STproperties.setCloudPort(1080);
			/* username number is recived from the jar execution comman*/
			STproperties.setCloudUser("user"+args[0]);

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
			//****************************************
			client =new Client(host, port, true);
			doEriBankPayAndroid(client);


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

	/**
	 * @param client
	 */
	public static void doEriBankPayAndroid(Client client) {

		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", "AndroidEriBankTest");


		long seconds;
		long minutes;
		try {
			String deviceName = client.waitForDevice("@os='android' ", 300000);
			client.openDevice();


			client.deviceAction("Unlock");

			try {

				client.launch(APP_NAME, true, true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				client.applicationClose(APP_NAME);
				client.uninstall(APP_NAME);
				client.install(APP_NAME, true, true);
				client.launch(APP_NAME, true, true);
			}



			client.waitForElement("NATIVE", "text=Login", 0, 10000);
			client.elementSendText("NATIVE", "hint=Username", 0, "company");
			client.elementSendText("NATIVE", "hint=Password", 0, "ENCRYPT:3272454672327251412F5A4F792F2F67704667592F513D3D");
			client.click("NATIVE", "text=Login", 0, 1);

			client.waitForElement("NATIVE", "text=Make Payment", 0, 10000);
			client.click("NATIVE", "text=Make Payment", 0, 1);

			client.waitForElement("NATIVE",  "hint=Phone", 0, 10000);
			client.elementSendText("NATIVE", "hint=Phone", 0, "0983427896");
			client.elementSendText("NATIVE", "hint=Name", 0, "Eyal");
			client.elementSendText("NATIVE", "hint=Amount", 0, "100");
			client.click("NATIVE", "text=Select", 0, 1);
			client.elementListSelect("", "text=Mexico", 0, true);

			client.waitForElement("NATIVE", "id=sendPaymentButton", 0, 10000);
			client.click("NATIVE", "id=sendPaymentButton", 0, 1);

			client.waitForElement("NATIVE", "text=Yes", 0, 10000);
			client.click("NATIVE", "text=Yes", 0, 1);

			client.waitForElement("NATIVE", "text=Logout", 0, 10000);
			client.click("NATIVE", "text=Logout", 0, 1);

			client.closeDevice();
			client.releaseDevice(deviceName, true, true, true);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			client.report(e.toString(), false);
		}
		t1=System.currentTimeMillis();
		long totalMils=t1-t0;
		long totalSeconds =totalMils/1000;
		seconds = totalSeconds%60;
		minutes = totalSeconds/60;

		client.report("The time running is : "+ minutes+" Minutes and "+seconds+" Seconds", true);

		String reportPath =client.generateReport(false)+"\\index.html";
		OpenHTML.openHTML(reportPath);

		// Releases the client so that other clients can approach the agent in the near future. 
		client.releaseClient();
	}

}
