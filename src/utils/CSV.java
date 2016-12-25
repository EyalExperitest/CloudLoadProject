package utils;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

public class CSV implements Closeable {
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String COMMA_DELIMITER = ",";
	private String[] parameters;
	private String fileName;
	private FileWriter fileWriter;
	private int length;
	public CSV( String fileName,String...parameters) throws IOException {
		// TODO Auto-generated constructor stub
		this.parameters=parameters;
		this.fileName=fileName;
		System.out.println(this.fileName);
		this.fileWriter = new FileWriter(this.fileName);
		this.length = parameters.length;
		writeLine(parameters);
	}
	/**
	 * @param parameters
	 * @throws IOException
	 */
	private void writeLine(String... parameters) throws IOException {
		for(int i=0;i<this.length;i++){
			
			this.fileWriter.write(parameters[i]);
			this.fileWriter.write((i!=length-1)?COMMA_DELIMITER:"");
			//System.out.print(parameters[i]);
			//System.out.print((i!=length-1)?COMMA_DELIMITER:"");
		}
	}
	public void addLine(String...parameters) throws IOException{
		if(this.length==parameters.length){
			this.fileWriter.write(NEW_LINE_SEPARATOR);
			//System.out.println("");
			writeLine(parameters);
		}
	}
	
	
	
	private void printHeader(){
		for (String string:parameters){
			System.out.println(string);
		}
	}
	

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.fileWriter.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (CSV csv = new CSV("exampleCSV.csv","A","B","C","D");){
			csv.printHeader();
			csv.addLine("1","2","3","4");
			csv.addLine("1","2","3","4");
			csv.addLine("a","b","c","d");
			csv.addLine("10","20","30","40");
			csv.addLine("a","b","c","d");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
