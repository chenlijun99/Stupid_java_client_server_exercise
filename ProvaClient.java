public class ProvaClient {

	public static void main(String[] args) 
	{
		String username = System.getProperty("user.name");
		System.out.println("username = " + username);
		String clientName="Client"+username;

		//se uso parametri:
		//if (args.length < 1)
		//{
			//System.out.println("Sintassi:");
			//System.out.println(" java ProvaClient nomequalsiasi");
			//return;
		//}else System.out.println("nomequalsiasi= "+args[0]);
		//Client c = new Client(args[0]);
		
		Client c = new Client(clientName);

		//		c.conversazione();
		c.conversazione_con_thread();
	}

}
