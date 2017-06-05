import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {

	private String m_senderUsername = null;
	private String m_senderAddress = null;
	private Date m_date = null;
	private String m_payload = null;

	Message() {

	}

	Message(String senderUsername, String senderAddress, Date date, String payload)
	{
		m_senderUsername = senderUsername;
		m_senderAddress = senderAddress;
		m_date = date;
		m_payload = payload;
	}

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
		m_date = new Date();
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

	public void setSenderUsername(String senderUsername) {
		m_senderUsername = senderUsername;
	}

	public void setSenderAddress(String senderAddress) {
		m_senderAddress = senderAddress;
	}

	public void setDate(Date date) {
		m_date = date;
	}

	public void setPayload(String payload) {
		m_payload = payload;
	}

	public String toString() 
	{
		return String.format("%s\\;%s\\;%s\\;%s",
					this.getSenderUsername(),
					this.getSenderAddress(),
					new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss").format(this.getDate()),
					this.getPayload());

	}
}
