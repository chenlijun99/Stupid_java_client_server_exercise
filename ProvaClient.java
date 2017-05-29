public class ProvaClient {

	public static void main(String[] args) 
	{
		//se uso parametri:
		//if (args.length < 1)
		//{
			//System.out.println("Sintassi:");
			//System.out.println(" java ProvaClient nomequalsiasi");
			//return;
		//}else System.out.println("nomequalsiasi= "+args[0]);
		//Client c = new Client(args[0]);
		
		Client c = new Client("127.0.0.1", 18080);

		//		c.conversazione();
		c.conversazione();
	}

}
