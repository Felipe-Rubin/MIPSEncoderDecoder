import javax.swing.*;
public class App {
	public static void main(String args[]) throws Exception{
		
		if(args.length == 0){
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					new MainFrame();
				}
			});
		}else{

		if(args.length < 2){
			System.out.println("Usage: java -jar <JarName>  <-e or -d> <File>");
			System.exit(0);
		}
		Parser p = new Parser(args[1]);
		if(args[0].equals("-e")) p.parseASM();
		else if(args[0].equals("-d")) p.parseCode();
		//p.parseASM();
		//p.parseCode();
		//Encoder e = new Encoder();
		//e.encode("");
		}

		
	}
}