import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	private String m_senderUsername;
	private String m_senderAddress;
	private Date m_date;
	private String m_payload;

	Message(ClientInfo sender, String payload)
	{
		m_senderUsername = sender.getUsername();
		m_senderAddress = sender.getAddress();
		m_date = new Date();
		m_payload = payload;
	}

	Message(String payload)
	{
		m_payload = payload;
	}

	public void setSenderInfo(ClientInfo sender) {
		m_senderUsername = sender.getUsername();
		m_senderAddress = sender.getAddress();
	}

	public String getSenderUsername() {
		return m_senderUsername;
	}

	public String getSenderAddress() {
		return m_senderAddress;
	}

	public Date getDate() {
		return m_date;
	}

	public String getPayload() {
		return m_payload;
	}
}

