public class ProvaClient 
{
	public static void main(String[] args) 
	{
		Client c = null;
		if (args.length == 2) {
			c = new Client(args[0], Integer.parseInt(args[1]));
		} else {
			c = new Client("127.0.0.1", 8080);
		}
		c.conversazione();
	}
}
