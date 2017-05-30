import java.io.*;
import java.net.*;

public class Client {

	private Socket m_connection;
	private ObjectInputStream m_fromServer;
	private ObjectOutputStream m_toServer;
	private String m_address;
	private int m_serverPort;

	public Client(String address, int serverPort) {
		m_serverPort = serverPort;
		m_address = address;
		try {
			m_connection = new Socket(m_address, m_serverPort);
			m_fromServer = new ObjectInputStream(m_connection.getInputStream());
			m_toServer = new ObjectOutputStream(m_connection.getOutputStream());

			System.out.println("Connected to server " + m_connection.getInetAddress().getHostName() + 
					" at " + m_address + ":" + m_serverPort);
		}
		catch(IOException ioe) {
			System.err.println("Can not establish connection to " +
					m_address + ":" + m_serverPort);
			ioe.printStackTrace();
			System.exit(-1);
		}
	}

	public void conversazione() {
		Sender sender = new Sender(m_toServer);
		sender.setDaemon(true);
		sender.start();
		try {
			Message message;
			while ((message = (Message)m_fromServer.readObject()) != null) {
				System.out.printf("\n%30s%s %s %tB %<te, %<tY %<tR %<Tp\n%30s%s\n\n> ",
						"",
						message.getSenderUsername(),
						message.getSenderAddress(),
						message.getDate(),
						"",
						message.getPayload());
			}
		} catch (IOException ioe) {
			System.err.println("Connection to server broken.");
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {

		}
	}
}
class Sender extends Thread
{
	private ObjectOutputStream m_toServer;

	public Sender(ObjectOutputStream out)
	{
		m_toServer = out;
	}

	public void run()
	{
		try {
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			while (!isInterrupted()) {
				System.out.printf("> ");
				String s = console.readLine();
				Message message = new Message(s);
				m_toServer.writeObject(message);
				m_toServer.flush();
			}
		} catch (IOException ioe) {
		}
	}
}
