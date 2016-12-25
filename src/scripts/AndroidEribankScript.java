package scripts;

import java.io.IOException;

import com.experitest.client.Client;

import launcher.STAProccess;
import launcher.STproperties;

public class AndroidEribankScript {
	
	private static final String APP_NAME = "cloud:com.experitest.ExperiBank/.LoginActivity";
	private static String host = "localhost";
	private static int port = 8889;
	private static String projectBaseDirectory = "C:\\Users\\eyal.neumann\\workspace\\project38";
	protected static Client client = null;
	private static STAProccess process;
	
	public AndroidEribankScript() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//STAProccess.closeSeeTest();
		
		//*********** First Launch************
		System.out.println("***********First Launch************");
		STAProccess process0= new STAProccess(true);
		process0.waitForLaunch();
		(new Client(host, port, true)).exit();
		process0.waitFor();
		
		//*********** Second Launch************
		System.out.println("***********Second Launch************");
		STproperties.readySeeTest();
		process= new STAProccess(false);
		process.waitForLaunch();
		client = new Client(host, port, true);
		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", "AndroidEriBankTest");
		
		
		String deviceName = client.waitForDevice("@os='android' ", 300000);
		client.openDevice();
		try {
			client.launch(APP_NAME, true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			client.uninstall(APP_NAME);
			client.install(APP_NAME, true, true);
			client.launch(APP_NAME, true, true);

		}
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
		
		client.generateReport(false);
		// Releases the client so that other clients can approach the agent in the near future. 
		client.releaseClient();
		
		process.destroy();
		
		
		
	}

}
