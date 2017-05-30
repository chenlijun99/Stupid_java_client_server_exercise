import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

/**
  Chat Server 
 *
 * Sends messages to the client. Messages are stored in a message queue. When
 * the queue is empty, ClientSender falls in sleep until a new message is
 * arrived in the queue. When the queue is not empty, ClientSender sends the
 * messages from the queue to the client socket.
 */



public class ClientSender extends Thread
{
	private Vector<Message> m_messageQueue = new Vector<Message>();

	private ServerDispatcher m_serverDispatcher;
	private ClientInfo m_clientInfo;
	private ObjectOutputStream m_output;

	public ClientSender(ClientInfo clientInfo, ServerDispatcher serverDispatcher)
			throws IOException
		{
			m_clientInfo = clientInfo;
			m_serverDispatcher = serverDispatcher;
			Socket socket = clientInfo.getSocket();
			m_output = new ObjectOutputStream(socket.getOutputStream());
			m_output.flush();
		}

	/**
	 * Adds given message to the message queue and notifies this thread
	 * (actually getNextMessageFromQueue method) that a message is arrived.
	 * sendMessage is called by other threads (ServeDispatcher).
	 */
	public synchronized void sendMessage(Message message)
	{
		m_messageQueue.add(message);
		notify();
	}

	/**
	 * @return and deletes the next message from the message queue. If the queue
	 * is empty, falls in sleep until notified for message arrival by sendMessage
	 * method.
	 */
	private synchronized Message getNextMessageFromQueue() throws InterruptedException
	{
		while (m_messageQueue.size() == 0)
			wait();
		Message message = m_messageQueue.get(0);
		m_messageQueue.removeElementAt(0);
		return message;
	}

	/**
	 * Sends given message to the client's socket.
	 */
	private void sendMessageToClient(Message message) throws IOException
	{
		m_output.writeObject(message);
		m_output.flush();
	}

	/**
	 * Until interrupted, reads messages from the message queue
	 * and sends them to the client's socket.
	 */
	public void run()
	{
		try {
			for (Message m : new MessageHistory("history.txt").getHistory()) {
				sendMessageToClient(m);
			}
			while (!isInterrupted()) {
				Message message = getNextMessageFromQueue();
				sendMessageToClient(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		m_clientInfo.getListener().interrupt();
		m_serverDispatcher.deleteClient(m_clientInfo);
	}

}
