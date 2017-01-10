package monitors;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.jcraft.jsch.JSchException;

import ssh.SSHUtils;
import utils.CSV;
import utils.GetDate;

public class MacCloudMonitor implements Runnable{

	private static final int CPU_USAGE_ATTEMPT = 1;
	public class CloudMeasures {
		
		public Double serverCPU; 
		public Double agentCPU;
		public Integer serverMemory;
		public Integer agentMemory;
		
		private CloudMeasures() {
			this.serverCPU=new Double(0.0);
			this.agentCPU=new Double(0.0);
			this.serverMemory =new Integer(0);
			this.agentMemory =new Integer(0);
		}

	}
	
	private String userName;
	private String host;
	private String password;
	private String sudoPassword;
	private Integer agentPID =null;
	private Integer serverPID =null;
	private boolean isServer=true;
	private boolean isAgent=true;

	private boolean isOn=true; 
	private int sampleTime=0;
	private String path="";


	public MacCloudMonitor(String userName,String host)  {
		// TODO Auto-generated constructor stub
		this.userName=userName;
		this.host=host;

	}
	@Override
	public void run() {
		String date = GetDate.getDate();
		String dateCleaned=date.replaceAll("[^0-9]+", "_");

		String fileName=path+"Cloud_"+this.host+"_"+dateCleaned+".csv";
		String dateHeader = "Date ";
		String agentMemoryHeader = "Agent memory";
		String agentCPUHeader = "Agent CPU";
		String serverMemoryHeader = "Server memory";
		String serverCPUHeader = "Server CPU";

		try (CSV csv =new CSV(fileName,dateHeader,serverMemoryHeader,serverCPUHeader,agentMemoryHeader,agentCPUHeader)) {
			System.out.println("Write CSV :");
			while(this.isOn()){
				//System.out.println("is On");

				CloudMeasures cloudMeasures = this.getCloudMeasures();
				 date = GetDate.getDate();
				 csv.addLine(date,
						 cloudMeasures.serverMemory.toString(),
						 cloudMeasures.serverCPU.toString(),
						 cloudMeasures.agentMemory.toString(),
						 cloudMeasures.agentCPU.toString());
				 

				try {
					Thread.sleep(sampleTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}	




		} catch (IOException | JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();





		}




	}
	public static void main(String[] args) throws JSchException, IOException, InterruptedException {
		// TODO Auto-generated method stub

		
		MacCloudMonitor macCloudMonitor =new MacCloudMonitor("QA MAC 10","192.168.2.11");
		macCloudMonitor.setSudoPassword("123456");
		macCloudMonitor.setServer(true);
		macCloudMonitor.setAgent(true);
		macCloudMonitor.restartAndLearn();
		
		
		System.out.println("Server PID : "+ macCloudMonitor.getServerPID());
		System.out.println("Agent PID : "+ macCloudMonitor.getAgentPID());

		Thread thread =new Thread (macCloudMonitor);
		macCloudMonitor.setOn();
		thread.start();
		//Thread.sleep(60000);
		JOptionPane.showConfirmDialog( null, "Click Ok To End Monitoring", "Monitoring", JOptionPane.PLAIN_MESSAGE );
		System.out.println("Closing Monitor Thread");
		
		macCloudMonitor.setOff();
		thread.join();
		





	}
	/**
	 * @param macCloudMonitor
	 * @throws JSchException
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * 
	 * Restart Agent and Server, and  update their pid .
	 * 
	 */
	public void restartAndLearn() 	throws JSchException, IOException, InterruptedException {
		if (this.isAgent()){
			this.stopAgent();
		}
		if (this.isServer()){
			this.stopServer();
		}
		Thread.sleep(5000);
		String[][] words = this.getJavaTop();
		Set<Integer> pids =new HashSet<Integer>();
		for (int i=0;i<words.length;i++){
			String pidWord = words[i][0];
			Integer pid = Integer.parseInt(pidWord);
			pids.add(pid);
			System.out.println(pidWord +" Is not a Cloud process");
		}
		if (this.isServer()){
			this.startServer();
			Thread.sleep(5000);
			String[][] words2 = this.getJavaTop();
			for (int i=0;i<words2.length;i++){
				String pidWord = words2[i][0];
				Integer pid = Integer.parseInt(pidWord);
				if(pids.contains(pid)){
					System.out.println(pidWord +" Is not a Cloud  Server");
				}
				else{
					this.setServerPID(pid);	
					pids.add(pid);
					System.out.println(pidWord +" Is a Cloud  Server");
					break;

				}

			}
		}
		if (this.isAgent()){
			this.startAgent();
			Thread.sleep(5000);

			String[][] words3 = this.getJavaTop();
			for (int i=0;i<words3.length;i++){
				String pidWord = words3[i][0];
				Integer pid = Integer.parseInt(pidWord);
				if(pids.contains(pid)){
					System.out.println(pidWord +" Is not a Cloud Agent ");
				}
				else{
					this.setAgentPID(pid);	
					pids.add(pid);
					System.out.println(pidWord +" Is a Cloud Agent ");
					break;

				}

			}
		}
	}

	public String[][] getJavaTop() throws JSchException, IOException{

		String command ="top -l 1 |grep java";
		String output = SSHUtils.sshExec(userName, host, command);
		String[] lines =output.split("\n");
		String[][] words =new String[lines.length][];
		for (int i=0;i<lines.length;i++){
			words[i]=lines[i].split("[ \t]+");
		}
		return words;
	}
	public Double getServerCpu() throws JSchException, IOException{
		Double serverCpu =new Double(0.0);
		
		if (this.isServer()) {
			String command = "ps -p " + this.getServerPID() + " -o %cpu | tail -1";
			for (int i = 0; i < CPU_USAGE_ATTEMPT; i++) {
				String output = SSHUtils.sshExec(userName, host, command);
				Double outPutDouble = Double.parseDouble(output);
				if (outPutDouble.compareTo(serverCpu) > 0) {
					serverCpu = outPutDouble;
				}

			} 
		}
		return serverCpu;
	}
	public Double getAgentCpu() throws JSchException, IOException{
		Double serverCpu =new Double(0.0);
		if (this.isAgent()) {
			String command = "ps -p " + this.getAgentPID() + " -o %cpu | tail -1";
			for (int i = 0; i < CPU_USAGE_ATTEMPT; i++) {
				String output = SSHUtils.sshExec(userName, host, command);
				Double outPutDouble = Double.parseDouble(output);
				if (outPutDouble.compareTo(serverCpu) > 0) {
					serverCpu = outPutDouble;
				}

			} 
		}
		return serverCpu;
	}
	public  CloudMeasures getCloudMeasures() throws JSchException, IOException{
		CloudMeasures cloudMeasures=new CloudMeasures();
		String[][] words=getJavaTop();
		for (int i=0;i<words.length;i++){
			Integer pid = Integer.parseInt(words[i][0]);
			if (pid.equals(this.getServerPID())){
				String serverMemoryCleaned =words[i][7].replace("M", "").replace("+", "");
				//System.out.println(words[i][7]+" => "+serverMemoryCleaned);
				cloudMeasures.serverMemory= Integer.parseInt(serverMemoryCleaned);
				//cloudMeasures.serverCPU= Double.parseDouble(words[i][2]);
			}
			if (pid.equals(this.getAgentPID())){
				String agentMemoryCleaned =words[i][7].replace("M", "").replace("+", "");
				//System.out.println(words[i][7]+" => "+agentMemoryCleaned);
				cloudMeasures.agentMemory= Integer.parseInt(agentMemoryCleaned);
				//cloudMeasures.agentCPU= Double.parseDouble(words[i][2]);
			}
			
			
		}
		cloudMeasures.serverCPU=this.getServerCpu();
		cloudMeasures.agentCPU=this.getAgentCpu();
		
		
		
		return cloudMeasures;





	}


	public void stopAgent() throws JSchException, IOException{
		String command="launchctl unload /Library/LaunchDaemons/com.experitest.cloudagent.plist";
		SSHUtils.sshSudoComand(userName, host, command, sudoPassword );
	}
	public void startAgent() throws JSchException, IOException{
		String command="launchctl load /Library/LaunchDaemons/com.experitest.cloudagent.plist";
		SSHUtils.sshSudoComand(userName, host, command, sudoPassword );
	}
	public void stopServer() throws JSchException, IOException{
		String command="launchctl unload /Library/LaunchDaemons/com.experitest.cloudserver.plist";
		SSHUtils.sshSudoComand(userName, host, command, sudoPassword );
	}
	public void startServer() throws JSchException, IOException{
		String command="launchctl load /Library/LaunchDaemons/com.experitest.cloudserver.plist";
		SSHUtils.sshSudoComand(userName, host, command, sudoPassword );
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSudoPassword() {
		return sudoPassword;
	}

	public void setSudoPassword(String sudoPassword) {
		this.sudoPassword = sudoPassword;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAgentPID() {
		return agentPID;
	}

	public void setAgentPID(Integer agentPID) {
		this.agentPID = agentPID;
	}

	public Integer getServerPID() {
		return serverPID;
	}

	public void setServerPID(Integer serverPID) {
		this.serverPID = serverPID;
	}
	public boolean isServer() {
		return isServer;
	}
	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}
	public boolean isAgent() {
		return isAgent;
	}
	public void setAgent(boolean isAgent) {
		this.isAgent = isAgent;
	}
	public boolean isOn() {
		return isOn;
	}
	public void setOff() {
		this.isOn = false;
	}
	public void setOn() {
		this.isOn = true;
	}
	public int getSampleTime() {
		return sampleTime;
	}
	public void setSampleTime(int sampleTime) {
		this.sampleTime = sampleTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}



}
