/*
	Fix the initial file 

*/
import java.util.*;
import java.io.*;
import java.nio.*;
public class Parser{
	private String file;
	private String currentMemory;
	private String currentMemoryData;
	private Map<String,String> dataMemory; //<PosMemoria,Dado>
	private Map<String,String> textMemory; //<PosMemoria,Instr>
	private Map<String,String> labelMemory; //<Label,PosMemoria>
	public Parser(String file){
		this.file = file;
		currentMemory = "0x00400000";
		currentMemoryData = "0x10010000";
		dataMemory = new LinkedHashMap<>();
		textMemory = new LinkedHashMap<>();
		labelMemory = new LinkedHashMap<>();
	}

	public void parseASM(){
		try{
			
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line = "";

			while((line = br.readLine()) != null){

				if(line.matches("[\\s]*")) continue; //Se n tiver nada so pula

				if(line.contains(".globl")) continue; //Ignora

				if(line.contains(".text")){
					
					//pula o .text

					while((line = br.readLine()) != null){
						line = beautifyInstruction(line);						
						if(line.matches("[\\s]*")) continue; // Se n tiver nada pula
						if(line.contains(".globl")) continue; //Ignora
						if(line.contains(".data")) break; //Acabou as instr
						

						if(line.contains(":")){

							String labelSplit[] = line.split("\\s*:\\s*");//divide label de instr
							// <Label_1,0x004...>
							labelMemory.put(labelSplit[0],currentMemory);
							if(labelSplit.length > 1){
								line = labelSplit[1];
							}else continue;
						}
						// <0x004..., Instr>
						textMemory.put(currentMemory,line);

						//Currmemory++;
						currentMemory = Calculator.addIntToHex(4,currentMemory);
						
					}

				}

				if(line.contains(".data")){

					while((line = br.readLine()) != null){
						if(line.matches("[\\s]*")) continue;
						if(line.contains(".globl")) continue;
						if(line.contains(".text")) break;

						//TO DO , TALVEZ N PRECISE USAR O .data
					}

				}

			}


		}catch(Exception e){
			System.out.println(e.getMessage());
		}


		for(Map.Entry<String,String> ks : textMemory.entrySet()){
			System.out.print("["+ks.getKey()+ "] ");
			System.out.println(ks.getValue());

		}

		for(Map.Entry<String,String> ks : labelMemory.entrySet()){
			System.out.print("["+ks.getKey()+ "] ");
			System.out.println(ks.getValue());
		}
	}
	/*
		Recebe uma instrucao ma formatada(muitos tabs, espacos...)
		Retorna ela formatada
	*/
	public String beautifyInstruction(String horrible){
		horrible = horrible.replaceAll("[#].*","");
		horrible = horrible.replaceAll("\\s+$","");
 		horrible = horrible.replaceAll("^[\\t\\s]*","");
		horrible = horrible.replaceAll("\\s*,",",");
		horrible = horrible.replaceAll(",\\s*",",");
		horrible = horrible.replaceAll("\\s*\\(\\s*","\\(");
		horrible = horrible.replaceAll("\\(\\s*","\\(");
		horrible = horrible.replaceAll("\\s*\\)","\\)");
		horrible = horrible.replaceAll("\\s+"," ");
		return horrible;
	}
}