/**
 Chat Client
*/

import java.net.*;
import java.io.*;

public class Client {
	private Socket connessione;
	private BufferedReader dalServer;
//	private PrintStream alServer;
	private PrintWriter alServer;
	private String name;
	private String indirizzo;
	private int SERVER_PORT = 4000;
	public Client(String name) {
		this.name = name;
		BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
		try {
						System.out.println("Inserire indirizzo server: ");
			System.out.println("Inserire indirizzo server: ");

			System.out.println("Inserire indirizzo server: ");
			indirizzo = t.readLine();
			connessione = new Socket(indirizzo, SERVER_PORT);
			dalServer = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
//			alServer = new PrintStream(connessione.getOutputStream());
			alServer = new PrintWriter(new OutputStreamWriter(connessione.getOutputStream()));

           System.out.println("Connected to server " +

              indirizzo + ":" + SERVER_PORT);
		}
		catch(IOException ioe) {

           System.err.println("Can not establish connection to " +

               indirizzo + ":" + SERVER_PORT);

           ioe.printStackTrace();

           System.exit(-1);
		}
	} // Client()

	public void conversazione() {
		String messaggio = "";
		BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(!messaggio.equals("/logout")) {
				messaggio = dalServer.readLine();
				System.out.println(messaggio);
				if(!messaggio.equals("/logout")) {
					messaggio = t.readLine();
					alServer.println(name+" scrive: "+messaggio);
				}
			} // while
			connessione.close();
		}
		catch(IOException e) {
			System.out.println("Conversazione interrotta");
		}
	} // conversazione()


	public void conversazione_con_thread() {
        // Create and start Sender thread

        Sender sender = new Sender(alServer);

        sender.setDaemon(true);

        sender.start();

 

        try {

           // Read messages from the server and print them

            String message;

           while ((message=dalServer.readLine()) != null) {

               System.out.println(message);

           }

        } catch (IOException ioe) {

           System.err.println("Connection to server broken.");

           ioe.printStackTrace();

        }
	} // conversazione_con_thread()

}

class Sender extends Thread

{

	private PrintWriter mOut;

	public Sender(PrintWriter aOut)
		{
			mOut = aOut;
		}

 

    /**

     * Until interrupted reads messages from the standard input (keyboard)

     * and sends them to the chat server through the socket.

     */

	public void run()
	{
	  try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while (!isInterrupted()) {
				String message = in.readLine();
				mOut.println(message);
				mOut.flush();
			 }
			} catch (IOException ioe) {
            // Communication is broken
				}
	}
}
