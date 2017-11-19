package sandbox;

/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program will demonstrate how to exec 'sudo' on the remote.
 *
 */

import ssh.SSHUtils;

public class Sudo{
	public static void main(String[] arg){
		String userName = "QA MAC 10";//System.getProperty("user.name");
		String hostAdress = "192.168.2.11";
		String command = "launchctl load /Library/LaunchDaemons/com.experitest.cloudserver.plist";//"printenv SUDO_USER";

		try{
			String sudoPass = "123456";
			SSHUtils.sshSudoComand(userName, hostAdress, command, sudoPass );
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	
}