package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ProcessReader implements Runnable {
    private final Process process;
    private String name;
    public ProcessReader(Process process, String name){
        this.process =process;
        this.name= name;
    }
    @Override
    public void run() {
        Thread.currentThread().setName(name);
        try {
            process.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        try(BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));) {
            while (process.isAlive()) {
                System.out.println("["+name+"] "+stdout.readLine());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}