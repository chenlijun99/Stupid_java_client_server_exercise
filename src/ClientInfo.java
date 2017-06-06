/**
 Chat Server
 ClientInfo class contains information about a client, connected to the server.
 */
 
import java.net.Socket;
 
public class ClientInfo
{
    private Socket m_socket = null;
    private ClientListener m_listener = null;
    private ClientSender m_sender = null;
	private String m_username = null;
	private String m_address = null;


	//ClientInfo(Socket socket, ClientListener listener, ClientSender sender, String username) {
		//m_socket = socket;
		//m_listener = listener;
		//m_sender = sender;
		//m_username = username;
	//}

	public void setSender(ClientSender sender) {
		m_sender = sender;
	}

	public void setListener(ClientListener listener) {
		m_listener = listener;
	}

	public void setSocket(Socket socket) {
		m_socket = socket;
		setUsername(m_socket.getInetAddress().getHostName());
		setAddress(m_socket.getInetAddress().getHostAddress());
	}

	public void setUsername(String username) {
		m_username = username;
	}

	public void setAddress(String address) {
		m_address = address;
	}

	public ClientSender getSender() {
		return m_sender;
	}

	public ClientListener getListener() {
		return m_listener;
	}

	public Socket getSocket() {
		return m_socket;
	}

	public String getUsername() {
		return m_username;
	}

	public String getAddress() {
		return m_address;
	}
}
