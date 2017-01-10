package launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class STproperties {
	static private 	int    cloudPort 	 = 80;
	static private	String cloudIP   	 = "192.168.1.210";
	static private 	String cloudUser 	 = "eyal";
	static private 	String encPassword	 = "2F7A6F3176536C663675306E6556312F31514D7455673D3D";
	static private 	String licenseServer = "192.168.1.205" ;


	public STproperties() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readySeeTest();

	}

	/**
	 * @throws IOException
	 */
	public static void readySeeTest() throws IOException {
		Properties prop = new Properties();

		//String cloudIP = "192.168.1.210";


		//String appdatapath = "C:\\Users\\eyal.neumann\\AppData\\Roaming";
		String appdatapath=System.getenv("Appdata");




		String seetestAppDataPath = appdatapath+"\\seetest";
		File sPFile =new File (seetestAppDataPath);
		if (sPFile.exists()){
			System.out.println("SeeTest AppData folder exist");
		}
		else{
			System.out.println("SeeTest AppData Folder dont exist");
			System.out.println("Creating SeeTest AppData Folder");
			sPFile.mkdirs();
		}


		String appPropertyPath = appdatapath+"\\seetest\\app.properties";
		System.out.println("app.property file should be at : "+appPropertyPath);
		File pFile =new File (appPropertyPath);
		if (pFile.exists()){
			System.out.println("app.property file is at : "+appPropertyPath);

		}else{
			System.out.println("app.property file is not at : "+appPropertyPath);
			boolean isExist= pFile.createNewFile();
			System.out.println("createNewFile returns : "+isExist);
		}

		try (InputStream input = new FileInputStream(appPropertyPath);){
			prop.load(input);
			try (OutputStream output  = new FileOutputStream(appPropertyPath);){

				prop.setProperty("use.floating.licens", "true");
				prop.setProperty("license.type", "sentinel");
				prop.setProperty("remote.license.manager", STproperties.getLicenseServer());
				prop.setProperty("license.type", "sentinel");

				prop.setProperty("floating.defaults", "ANDROID,IPHONE,DEVELOPER_MACHINE,");
				prop.setProperty("first.launch", "false");
				prop.setProperty("suspend.floating.dialog", "true");

				prop.setProperty("cloudserver.available.list", getCloudIP()+":"+getCloudPort());
				prop.setProperty("cloudserver.list", getCloudIP()+":"+getCloudPort()+":"+getCloudUser()+":"+encPassword+":true:false:");
				prop.setProperty("block.cloud.server.access", "false");



				prop.store(output,null);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getCloudUser() {
		return cloudUser;
	}

	public static void setCloudUser(String cloudUser) {
		STproperties.cloudUser = cloudUser;
	}

	private static int getCloudPort() {
		return cloudPort;
	}

	public static void setCloudPort(int cloudPort) {
		STproperties.cloudPort = cloudPort;
	}

	private static String getCloudIP() {
		return cloudIP;
	}

	public static void setCloudIP(String cloudIP) {
		STproperties.cloudIP = cloudIP;
	}

	private static String getLicenseServer() {
		return licenseServer;
	}

	public static void setLicenseServer(String licenseServer) {
		STproperties.licenseServer = licenseServer;
	}


}
