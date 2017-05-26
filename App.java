/*
	Autor: Felipe Pfeifer Rubin
	Matricula: 151050853
	Email: felipe.rubin@acad.pucrs.br

	Inicio da App
*/
import javax.swing.*;
public class App {
	/*
		Inicia o programa
	*/
	public static void main(String args[]){
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MainFrame();
			}
		});
		
	}
}