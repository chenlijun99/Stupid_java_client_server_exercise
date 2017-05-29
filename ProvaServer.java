public class ProvaServer {

	public static void main(String[] args) {
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
		
		Server s = new Server(18080);
	}
}
