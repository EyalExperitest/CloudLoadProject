package ssh;

import com.jcraft.jsch.UserInfo;

public class HCUser implements UserInfo {

	private static final String PASSWORD = "123456";

	public HCUser() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getPassphrase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return PASSWORD;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean promptPassword(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean promptYesNo(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void showMessage(String arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
