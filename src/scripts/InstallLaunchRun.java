/**
 * 
 */
package scripts;

import java.io.File;
import java.io.IOException;
import com.experitest.client.Client;

import launcher.STAProccess;
import launcher.STproperties;
import utils.Download;
import utils.OpenHTML;
import utils.RunCmd;

/**
 * @author eyal.neumann
 *
 */
public class InstallLaunchRun {

	/**
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws MalformedURLException 
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
	
	public InstallLaunchRun()  {
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
		if (installFlag) {
			System.out.println("Downloading Latest master version");
			String url = "http://192.168.1.213:8090/guestAuth/repository/download/bt2/.lastSuccessful/SeeTest_windows_10_3_%7Bbuild.number%7D.exe";
			String file = System.getenv("Temp") + "\\SeeTest_windows.exe";
			Download.downolad(url, file);
			STAProccess.closeSeeTest();
			System.out.println("Installing SeeTest");
			RunCmd.runCMD("Install " + file, file + " -q");
			File instalationFile = new File(file);
			System.out.println("Deleting Instalation File");
			instalationFile.delete();
		}
		
		
		//*********** First Launch************

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
		//*********** Second Launch************
		System.out.println("***********Launch************");
		STproperties.readySeeTest();
		process= new STAProccess(false);
		process.waitForLaunch();
		client =new Client(host, port, true);
		doEriBankPayAndroid(client);

		process.destroy();	}

	/**
	 * @param client1
	 */
	public static void doEriBankPayAndroid(Client client) {
		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", "AndroidEriBankTest");


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
		t1=System.currentTimeMillis();
		long totalMils=t1-t0;
		long totalSeconds =totalMils/1000;
		long seconds =totalSeconds%60;
		long minutes =totalSeconds/60;
		client.report("The time running is : "+ minutes+" Minutes and "+seconds+" Seconds", true);
		String reportPath =client.generateReport(false);
        OpenHTML.openHTML(reportPath);

		// Releases the client so that other clients can approach the agent in the near future. 
		client.releaseClient();
	}

}
