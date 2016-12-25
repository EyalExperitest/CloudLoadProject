package remote;

import java.io.Closeable;
import java.io.IOException;

import utils.RunCmd;

public class RemoteRobocopy implements Closeable {
	private String address;
	private String user;
	private String password;
	private boolean isOpen=false;
	
	
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		String remoteAddress = "192.168.2.106";
		String remoteUser = "vm_cloud_master\\Administrator";
		String remotePassword = "Experitest2027";
		String srcPath = "c:\\myjars\\";
		String dstPath = "myjars\\";

		RemoteRobocopy.remoteCopy(remoteAddress, remoteUser, remotePassword, srcPath, dstPath);
		
		
		
	}




	/**
	 * @param remoteAddress
	 * @param remoteUser
	 * @param remotePassword
	 * @param srcPath
	 * @param dstPath
	 * @throws InterruptedException 
	 */
	public static void remoteCopy(String remoteAddress, String remoteUser, String remotePassword, String srcPath,
			String dstPath) throws InterruptedException {
		try(RemoteRobocopy remoteMachine =new RemoteRobocopy(remoteAddress, remoteUser, remotePassword)){
			remoteMachine.robocopy(srcPath, dstPath,remoteAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void remoteReverseCopy(String remoteAddress, String remoteUser, String remotePassword, String srcPath,
			String dstPath) throws InterruptedException {
		try(RemoteRobocopy remoteMachine =new RemoteRobocopy(remoteAddress, remoteUser, remotePassword)){
			remoteMachine.reverserobocopy(srcPath, dstPath, remoteAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private RemoteRobocopy(String address,String user,String password ) throws IOException {
		this.address=address;
		this.user=user;
		this.password=password;
		
		String command = "net use \\\\"+address+" /user:"+user+" "+password;
		RunCmd.runCMD(command);
		this.isOpen=true;
		// TODO Auto-generated constructor stub
	}


	
	private void robocopy(String srcPath,String dstPath,String remoteAddress) throws IOException, InterruptedException{
		//String command = "robocopy "+"c:\\myjars\\"+" \\\\"+address+"\\"+dstPath;
		String command = "robocopy "+srcPath+" \\\\"+address+"\\"+dstPath;

		String name="Robo Copy from "+srcPath+" to "+dstPath+" on "+remoteAddress;
		RunCmd.runCMD(name,command);

	}
	private void reverserobocopy(String srcPath,String dstPath,String remoteAddress) throws IOException, InterruptedException{
		String command = "robocopy  \\\\"+address+"\\"+dstPath+" "+srcPath;
		String name="Robo Copy from "+srcPath+" to "+dstPath+" on "+remoteAddress;
		RunCmd.runCMD(name,command);

	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		if (isOpen) {
			this.close();
		}
		super.finalize();
	}




	@Override
	public void close() throws IOException {
		String command = "net use /D \\\\"+address;
		RunCmd.runCMD(command);
		this.isOpen=false;

	}

}
