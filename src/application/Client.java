package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client 
{
	private final PrintStream printStream;
	
	@SuppressWarnings("CallToThreadStartDuringObjectConstruction")
	public Client(String ip,int port)throws IOException
	{
		Socket s = new Socket(ip,port);
		
		new Thread(() ->  {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String line;
				
				while((line = reader.readLine())!=null) {
					System.out.print("Server response : " + line);
					System.out.print("\nCommands [add, sub, mul, div, mod, exit] : ");
				}
			} catch (IOException ex) {
				System.err.println("\nDisconnected");
				System.exit(0);
			}
		}).start();
		
		printStream = new PrintStream(s.getOutputStream(), true);
	}
	
	public void sendMessage(String operation) {
        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter 1st number : ");
        int f1 = in.nextInt();

        System.out.print("Enter 2nd number : ");
        int s1 = in.nextInt();

        printStream.println(operation + ":" + f1 + ":" + s1);

    }

	public void connect()
	{
		
	}
}
