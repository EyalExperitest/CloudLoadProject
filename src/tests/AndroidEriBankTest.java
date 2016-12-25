package tests;

//package <set your test package>;
import com.experitest.client.*;

import launcher.STAProccess;
import launcher.STproperties;

import java.io.IOException;

import org.junit.*;
/**
 *
 */
public class AndroidEriBankTest {
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\eyal.neumann\\workspace\\project38";
	protected Client client = null;
	private STAProccess process;

	@Before
	public void setUp() throws IOException, InterruptedException{
		STproperties.readySeeTest();
		process= new STAProccess();
		process.waitForLaunch();
		client = new Client(host, port, true);
		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", "AndroidEriBankTest");
	}

	@Test
	public void testAndroidEriBankTest(){
		String deviceName = client.waitForDevice("@os='android' ", 300000);
		client.openDevice();
		client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
		client.elementSendText("NATIVE", "hint=Username", 0, "company");
		client.elementSendText("NATIVE", "hint=Password", 0, "ENCRYPT:3272454672327251412F5A4F792F2F67704667592F513D3D");
		client.click("NATIVE", "text=Login", 0, 1);
		client.click("NATIVE", "text=Make Payment", 0, 1);
		client.elementSendText("NATIVE", "hint=Phone", 0, "0983427896");
		client.elementSendText("NATIVE", "hint=Name", 0, "Eyal");
		client.elementSendText("NATIVE", "hint=Amount", 0, "100");
		client.click("NATIVE", "text=Select", 0, 1);
		client.elementListSelect("", "text=Mexico", 0, true);
		client.click("NATIVE", "id=sendPaymentButton", 0, 1);
		client.click("NATIVE", "text=Yes", 0, 1);
		client.click("NATIVE", "text=Logout", 0, 1);
		
		client.closeDevice();
		client.releaseDevice(deviceName, true, true, true);
	}

	@After
	public void tearDown(){
		// Generates a report of the test case.
		// For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
		client.generateReport(false);
		// Releases the client so that other clients can approach the agent in the near future. 
		client.releaseClient();
		
		process.destroy();
	}
}
