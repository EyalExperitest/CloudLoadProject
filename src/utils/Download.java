package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download {

	public Download() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param url
	 * @param file
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void downolad(String url, String file)
			throws MalformedURLException, IOException, FileNotFoundException {
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

}
