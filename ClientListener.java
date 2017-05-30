import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Chat Server - 
 *
 * ClientListener class is purposed to listen for client messages and
 * to forward them to ServerDispatcher.
 */
 
 
public class ClientListener extends Thread
{
    private ServerDispatcher m_serverDispatcher;
    private ClientInfo m_clientInfo;
	private ObjectInputStream m_input;
 
	public ClientListener(ClientInfo clientInfo, ServerDispatcher serverDispatcher)
			throws IOException
    {

        m_clientInfo = clientInfo;
        m_serverDispatcher = serverDispatcher;
        Socket socket = clientInfo.getSocket();

        m_input = new ObjectInputStream(socket.getInputStream());
    }
 
    /**
     * Until interrupted, reads messages from the client socket, forwards them
     * to the server dispatcher's queue and notifies the server dispatcher.
     */
    public void run()
    {
        try {
           while (!isInterrupted()) {
               Message message = (Message) m_input.readObject();
			   message.setSenderInfo(m_clientInfo);
               if (message == null)
                   break;
               m_serverDispatcher.dispatchMessage(message);
           }
        } catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
 
        // Communication is broken. Interrupt both listener and sender threads
        m_clientInfo.getSender().interrupt();
        m_serverDispatcher.deleteClient(m_clientInfo);
    }
 
}
