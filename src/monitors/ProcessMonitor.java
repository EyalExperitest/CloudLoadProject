package monitors;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import utils.CSV;
import utils.GetDate;

public class ProcessMonitor implements Runnable {
	private boolean isOn=true; 
	private int sampleTime=0;
	private String path="";
	private String processName;
	public ProcessMonitor(String processName) {
		// TODO Auto-generated constructor stub
		this.processName=processName;
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		String thisIP = "";
		try {
			thisIP = Inet4Address.getLocalHost().getHostAddress().replace("192.168", "").replace(".", "_");
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String date = GetDate.getDate();
		String dateCleaned=date.replaceAll("[^0-9]+", "_");
		
		String fileName=path+this.processName+thisIP+dateCleaned;
		String dateHeader = "Date ";
		String memoryHeader = processName +" memory";
		String cpuHeader = processName +" CPU";

		try (CSV csv =new CSV(fileName,dateHeader,memoryHeader,cpuHeader)) {
			while(this.isOn()){
				
				String processMemoreyUsage =""+ BasicMonitors.getProcessMemoreyUsage(processName);
				double cpuUsageDouble = BasicMonitors.getCpuUsage(processName);
				String preCPUUsage =(cpuUsageDouble<10)? "0":"";
				String cpuUsage = preCPUUsage+cpuUsageDouble;
				date = GetDate.getDate();
				csv.addLine(date,processMemoreyUsage,cpuUsage);
				
				try {
					Thread.sleep(sampleTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}	




		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();





		}

	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String processName = "studio";
		
		Thread thread;
		ProcessMonitor processMonitor=new ProcessMonitor(processName);
		thread =new Thread(processMonitor);
		thread.start();
		
		Thread.sleep(60000);
		
		processMonitor.setOff();
		thread.join();




	}




	public boolean isOn() {
		return isOn;
	}




	public void setOff() {
		this.isOn = false;
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
