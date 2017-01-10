package runables;

import remote.RemoteFolder;

public class FlagObserver implements Runnable {
	private String address;
	private String user;
	private String password;
	private String path = "myjars";
	private String fileName = "FinishFlag.txt";
	
	
	
	public FlagObserver(String remoteAddress, String remoteUser, String remotePassword,	String path, String fileName) {
		this.address=remoteAddress;
		this.user=remoteUser;
		this.password=remotePassword;
		this.path=path;
		this.fileName=fileName;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread.currentThread().setName(this.address);
		boolean isFlagFileFound=false;
		while(!isFlagFileFound){
			isFlagFileFound =RemoteFolder.isFileInRemoteFolder(this.address, this.user, this.password, this.path,this.fileName);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+" Finished Test");
		RemoteFolder.deleteFileInRemoteFolder(this.address, this.user, this.password, this.path, this.fileName);
		

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
