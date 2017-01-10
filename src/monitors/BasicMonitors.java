package monitors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BasicMonitors {

	private static final int NUMBER_OF_LOGICAL_PROCESSORS = 4;

	public BasicMonitors() {





	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String processName = "studio";
		int result = getProcessMemoreyUsage(processName);
		System.out.println("Memory Usage : "+result);
		double cpuUsage = getCpuUsage(processName);
		System.out.println("CPU Usage : "+cpuUsage);





	}

	/**
	 * @param processName
	 * @return
	 * @throws IOException
	 */
	public static double getCpuUsage(String processName) throws IOException {
		String command = "TypePerf.exe -sc 1 \"\\Process("+processName+")\\% Processor Time\"";
		Process process = Runtime.getRuntime().exec(command);
		String line1;

		BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
		@SuppressWarnings("unused")
		String readLine = stdout.readLine();
		//System.out.println(readLine);
		readLine = stdout.readLine();
		//System.out.println(readLine);
		line1= stdout.readLine();
		String[] words =line1.split("\"");
		String processorTimeString = words[3];
		//System.out.println("Processor Time : "+processorTimeString);
		Double processorTime= Double.parseDouble(processorTimeString);
		Double cpuUsage = processorTime/NUMBER_OF_LOGICAL_PROCESSORS;
		cpuUsage=Math.floor(cpuUsage * 100) / 100;
		return cpuUsage;
	}

	/**
	 * @param processName
	 * @return
	 * @throws IOException
	 */
	public static int getProcessMemoreyUsage(String processName) throws IOException {
		String command = "TASKLIST";
		Process process = Runtime.getRuntime().exec(command);
		// Getting the results
		process.getOutputStream().close();
		String currentLine;
		String processLine=null;

		BufferedReader stdout = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		Integer  memoryUsage=new Integer(0);
		while (process.isAlive() /*&& processLine==null*/){
			currentLine = stdout.readLine();
			if (currentLine!=null){	
				if (currentLine.contains(processName)){
					processLine=currentLine;
					if (processLine!=null){
						String[] words =processLine.split(" +");
						
						Integer parseInt = Integer.parseInt(words[4].replaceAll(",", ""));
						//System.out.println("parseInt = "+parseInt);
						memoryUsage =memoryUsage+parseInt;	
						//System.out.println("memoryUsage = "+memoryUsage);

					}

				}
			}
		}

		int result = memoryUsage;
		return result;
	}

}
