public class App {
	public static void main(String args[]){
		Parser p = new Parser(args[0]);
		p.parseASM();
	}
}