package utils;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class GetPassword {

	public GetPassword() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 */
	public static String getPassword() {
		final String passwd;
		final String message = "Enter password";
		if( System.console() == null ) { 
			final JPasswordField pf = new JPasswordField(); 
			passwd = JOptionPane.showConfirmDialog( null, pf, message, 
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION 
					? new String( pf.getPassword() ) : ""; 
		} else {
			passwd = new String( System.console().readPassword( "%s> ", message ) );	
		}
		return passwd;
	}

}
