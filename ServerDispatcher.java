/**
  Chat Server

 * ServerDispatcher class is purposed to listen for messages received
 * from clients and to dispatch them to all the clients connected to the
 * chat server.
 */

import java.net.*;
import java.util.*;

public class ServerDispatcher extends Thread
{
	private Vector<Message> m_messageQueue = new Vector<Message>();
	private Vector<ClientInfo> m_clients = new Vector<ClientInfo>();

	public synchronized void addClient(ClientInfo clientInfo)
	{
		m_clients.add(clientInfo);
	}

	public synchronized void deleteClient(ClientInfo clientInfo)
	{
		int index = m_clients.indexOf(clientInfo);
		if (index != -1) {
			m_clients.removeElementAt(index);
		}
	}

	/**
	 * Adds given message to the dispatcher's message queue and notifies this
	 * thread to wake up the message queue reader (getNextMessageFromQueue method).
	 * dispatchMessage method is called by other threads (ClientListener) when
	 * a message is arrived.
	 */
	public synchronized void dispatchMessage(Message message)
	{
		m_messageQueue.add(message);
		notify();
	}

	/**
	 * @return and deletes the next message from the message queue. If there is no
	 * messages in the queue, falls in sleep until notified by dispatchMessage method.
	 */
	private synchronized Message getNextMessageFromQueue()
			throws InterruptedException
		{
			while (m_messageQueue.size() == 0)
				wait();
			Message message = m_messageQueue.get(0);
			m_messageQueue.removeElementAt(0);
			return message;
		}

	/**
	 * Sends given message to all clients in the client list. Actually the
	 * message is added to the client sender thread's message queue and this
	 * client sender thread is notified.
	 */
	private synchronized void sendMessageToAllClients(Message message)
	{
		for (int i = 0; i < m_clients.size(); i++) {
			m_clients.get(i).getSender().sendMessage(message);
		}
	}

	public void run()
	{
		try {
			while (true) {
				Message message = getNextMessageFromQueue();
				sendMessageToAllClients(message);
			}
		} catch (InterruptedException ie) {
			// Thread interrupted. Stop its execution
		}
	}

}
