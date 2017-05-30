import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

public class MessageHistory {
	private String nomeFile_;

	public MessageHistory(String nomeFile)
	{
		nomeFile_ = nomeFile;
	}

	public void addMessage(Message message) throws IOException
	{
		boolean append = true;
		try (PrintWriter fileOut = new PrintWriter(new FileWriter(nomeFile_, append))) {
			fileOut.println(message);
		}
	}

	public Vector<Message> getHistory() throws IOException
	{
		Vector<Message> messages = new Vector<Message>();
		try (BufferedReader fileIn = new BufferedReader(new FileReader(nomeFile_))) {
			StringTokenizer tokenizer;
			for (String m; (m = fileIn.readLine()) != null;) {
				tokenizer = new StringTokenizer(m, "\\;");
				Message message = new Message();
				message.setSenderUsername(tokenizer.nextToken());
				message.setSenderAddress(tokenizer.nextToken());
				String dateString = tokenizer.nextToken();
				Date date = new SimpleDateFormat("YYYY-MM-dd, hh:mm:ss").parse(dateString);
				message.setDate(date);
				message.setPayload(tokenizer.nextToken());

				messages.addElement(message);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return messages;
	}

	

	
}
