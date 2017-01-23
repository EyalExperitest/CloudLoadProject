package sandbox;
import java.io.IOException;
import java.net.Inet4Address;

import javax.swing.JOptionPane;

import com.jcraft.jsch.*;

import ssh.SSHUtils;


public class SandBox {
	public SandBox() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * Set the prompts, to find execution ends
	 */

	public static void main(String[] args) throws IOException, InterruptedException, JSchException {
		// TODO Auto-generated method stub
		
		String ip = Inet4Address.getLocalHost().getHostAddress().replace("192.168", "").replace(".", "_");
		System.out.println(ip);
		










	}


}
