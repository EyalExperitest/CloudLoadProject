package ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SSHUtils {
	public static void main(String[] args) throws JSchException, IOException{
		String string =SSHUtils.sshExec("QA MAC 10", "192.168.2.11", "ps -p 12342 -o %cpu | tail -1");
		System.out.println(string);
		
		
		
	}
	public SSHUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userName
	 * @param hostAdress
	 * @param command
	 * @throws JSchException
	 * @throws IOException
	 */
	public static void sshSudoComand(String userName, String hostAdress, String command,String sudoPassword)
			throws JSchException, IOException {
		JSch jsch=new JSch();  
	
		String host;
		String user=userName;
		host=hostAdress;
		Session session=jsch.getSession(user, host, 22);
		UserInfo ui=new HCUser();
		session.setUserInfo(ui);
		session.connect();
		//String sudoPassword = "123456";
		String sudo_pass=sudoPassword;
		Channel channel=session.openChannel("exec");
	
		// man sudo
		//   -S  The -S (stdin) option causes sudo to read the password from the
		//       standard input instead of the terminal device.
		//   -p  The -p (prompt) option allows you to override the default
		//       password prompt and use a custom one.
		((ChannelExec)channel).setCommand("sudo -S -p '' "+command);
		InputStream in=channel.getInputStream();
		OutputStream out=channel.getOutputStream();
		((ChannelExec)channel).setErrStream(System.err);
		channel.connect();
		out.write((sudo_pass+"\n").getBytes());
		out.flush();
		byte[] tmp=new byte[1024];
		while(true){
			while(in.available()>0){
				int i=in.read(tmp, 0, 1024);
				if(i<0)break;
				System.out.print(new String(tmp, 0, i));
			}
			if(channel.isClosed()){
				System.out.println("exit-status: "+channel.getExitStatus());
				break;
			}
			try{Thread.sleep(1000);}catch(Exception ee){}
		}
		channel.disconnect();
		session.disconnect();
	}

	/**
	 * @param user
	 * @param host
	 * @param command
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
	public static String sshExec(String user, String host, String command) throws JSchException, IOException {
		JSch jsch=new JSch();  
	
	
		Session session=jsch.getSession(user, host, 22);
		UserInfo ui=new HCUser();
		session.setUserInfo(ui);
		session.connect();
	
	
		Channel channel=session.openChannel("exec");
		((ChannelExec)channel).setCommand(command);
	
		channel.setInputStream(null);
	
	
		((ChannelExec)channel).setErrStream(System.err);
	
		InputStream in=channel.getInputStream();
	
		channel.connect();
		String output=null;
		byte[] tmp=new byte[1024];
		while(true){
			while(in.available()>0){
				int i=in.read(tmp, 0, 1024);
				if(i<0)break;
				output = new String(tmp, 0, i);
				//System.out.print("Out :"+output);
			}
			if(channel.isClosed()){
				if(in.available()>0) continue; 
				//System.out.println("exit-status: "+channel.getExitStatus());
				break;
			}
			try{Thread.sleep(1000);}catch(Exception ee){}
		}
		channel.disconnect();
		session.disconnect();
		return output;
	}

}
