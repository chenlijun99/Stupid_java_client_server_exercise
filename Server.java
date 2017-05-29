/**
  Multithreaded Chat Server Implementation – Server
  */
import java.net.*;
import java.io.*;

public class Server {

	private Socket connessione;
	private BufferedReader dalClient;
	private PrintStream alClient;
	private String name;
	private int acceptedPorts = 5;
	private int LISTENING_PORT = 4000;

	public Server(String name) 
	{
		this.name = name;

		try (ServerSocket server = new ServerSocket(LISTENING_PORT, acceptedPorts)) {
			System.out.println("Server attivo on port " + LISTENING_PORT);

			ServerDispatcher serverDispatcher = new ServerDispatcher();
			serverDispatcher.start();

			while (true) {
				try {
					//        Socket socket = server.accept();
					connessione = server.accept();
					ClientInfo clientInfo = new ClientInfo();
					//               clientInfo.mSocket = socket;
					clientInfo.mSocket = connessione;
					ClientListener clientListener =
						new ClientListener(clientInfo, serverDispatcher);
					ClientSender clientSender =
						new ClientSender(clientInfo, serverDispatcher);
					clientInfo.mClientListener = clientListener;
					clientInfo.mClientSender = clientSender;
					clientListener.start();
					clientSender.start();
					serverDispatcher.addClient(clientInfo);
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		} catch (IOException e) {

		}

	}

}
