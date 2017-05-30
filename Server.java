import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
  Multithreaded Chat Server Implementation – Server
  */

public class Server {

	private Socket m_connection;
	private int m_acceptedConnections = 5;
	private int m_listeningPort = 4000;

	public Server(int listeningPort) 
	{
		m_listeningPort = listeningPort;

		try (ServerSocket server = new ServerSocket(m_listeningPort, m_acceptedConnections)) {
			System.out.println("Server running on port " + m_listeningPort);

			ServerDispatcher serverDispatcher = new ServerDispatcher();
			serverDispatcher.start();

			while (true) {
				try {

					m_connection = server.accept();

					ClientInfo clientInfo = new ClientInfo();
					clientInfo.setSocket(m_connection);

					ClientSender clientSender = new ClientSender(clientInfo, serverDispatcher);
					ClientListener clientListener = new ClientListener(clientInfo, serverDispatcher);

					clientInfo.setListener(clientListener);
					clientInfo.setSender(clientSender);

					clientListener.start();
					clientSender.start();

					serverDispatcher.addClient(clientInfo);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

}
