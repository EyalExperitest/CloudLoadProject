import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import systemobject.terminal.Prompt;
import systemobject.terminal.SSH;

public class SandBox {
	public SandBox() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * Set the prompts, to find execution ends
	 */

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ArrayList<Prompt> prompts = new ArrayList<Prompt>();
		for ( String p : Arrays.asList("$" , "#" , "Password:") ) {
			Prompt prompt = new Prompt();
			prompt.setCommandEnd(true);
			prompt.setPrompt(p);
			prompts.add(prompt);
		}
		String ip = "192.168.2.11";
		String user = "QA MAC 10";
		String password = "123456";
		SSH ssh =new SSH(ip, user, password);
		ssh.setPrompts(prompts);
		ssh.connect();
		ssh.sendString("sudo launchctl unload /Library/LaunchDaemons/com.experitest.cloudserver.plist", true);
		int timeOut = 10000;
		try {
			Prompt prompt =ssh.waitForPrompt(timeOut);
			if(prompt.getPrompt().equals("Password:")){      
				ssh.sendString(password+"\n", true);  
				ssh.waitForPrompt(timeOut);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getCause().printStackTrace();
		}
		String result = ssh.getResult();
		System.out.println("result: "+result);
		













	}


}
