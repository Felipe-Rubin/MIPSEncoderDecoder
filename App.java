import javax.swing.*;
public class App {
	public static void main(String args[]){
		
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
		try{
		if(args[0].equals("-e")) System.out.println(p.parseASMFile());
		else if(args[0].equals("-d")) System.out.println(p.parseCodeFile());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		}

		
	}
}