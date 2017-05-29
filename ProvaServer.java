public class ProvaServer {

	public static void main(String[] args) {
		String username = System.getProperty("user.name");
		System.out.println("username = " + username);
		String serverName="Server"+username;

		//se uso parametri:
		//if (args.length < 1) {
			//System.out.println("Sintassi:");
			//System.out.println(" java ProvaServer nomequalsiasi");
			//return;
		//} else { 
			//System.out.println("nomequalsiasi= "+args[0]);
		//}
		//Server s = new Server(args[0]);
		//
		Server s = new Server(serverName);
	}
}
