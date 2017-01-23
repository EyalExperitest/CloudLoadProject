package sandbox;

import ssh.SSHUtils;

import javax.swing.*;

public class Exec{
	public static void main(String[] arg){
		try{
			String user="QA MAC 10";
			String host="192.168.2.11";
			String command=JOptionPane.showInputDialog("Enter command", "set|grep SSH");
			String output = SSHUtils.sshExec(user, host, command);

			System.out.println("Output: "+output);

		}
		catch(Exception e){
			System.out.println(e);
		}
	}



}

