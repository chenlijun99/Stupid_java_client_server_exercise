public class ProvaServer {

	public static void main(String[] args) {
		if (args.length == 1) {
			Server s = new Server(Integer.parseInt(args[0]));
		} else {
			Server s = new Server(8080);
		}
	}
}
