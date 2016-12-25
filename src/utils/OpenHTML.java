package utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenHTML {

    public static void main(String[] args) {

        String Path = "C:/Users/yotam.gamliel/seetest-reports/index.html";
        OpenHTML.openHTML(Path);
        // write your code here
    }

	/**
	 * @param Path
	 */
	public static void openHTML(String Path) {
		File htmlFile = new File(Path);
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
