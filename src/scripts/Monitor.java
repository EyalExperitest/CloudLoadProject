/**
 * 
 */
package scripts;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

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
public class Monitor {

	protected static Client client = null;
	public Monitor()  {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		//******** Starting Monitor Thread**************
		String processName = "cloudserver";
		
		Thread thread;
		ProcessMonitor processMonitor=new ProcessMonitor(processName);
		processMonitor.setPath("C:\\myjars\\");
		thread =new Thread(processMonitor);
		thread.start();
		//****************************************
	
		JOptionPane.showConfirmDialog( null, "Click Ok To End Monitoring", "Monitoring", JOptionPane.PLAIN_MESSAGE );

		//*************Stopping Monitor Thread ******* 
		processMonitor.setOff();
		thread.join();
		//********************************************
		
			}

	/**
	 * @param client1
	 */
	

}
