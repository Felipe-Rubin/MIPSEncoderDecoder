/*
	Fix the initial file 

*/
import java.util.*;
import java.io.*;
import java.nio.*;
public class Parser{
	private String file; //Arquivo a ser lido
	private String currentMemory; //Contador indica a PosMemTexto atual
	private String currentMemoryData; //Contador indica a PosMemDados atual
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

	/*
		Getter e Setters
	*/
	public String getFile(){
		return file;
	}
	public void setFile(String file){
		this.file = file;
	}
	public String getCurrentMemory(){
		return currentMemory;
	}
	public void setCurrentMemory(String currentMemory){
		this.currentMemory = currentMemory;
	}
	public String getCurrentMemoryData(){
		return currentMemoryData;
	}
	public void setCurrentMemoryData(String currentMemoryData){
		this.currentMemoryData = currentMemoryData;
	}
	public Map<String,String> getDataMemory(){
		return dataMemory;
	}
	public void setDataMemory(Map<String,String> dataMemory){
		this.dataMemory = dataMemory;
	}

	public Map<String,String> getTextMemory(){
		return textMemory;
	}
	public void setTextMemory(Map<String,String> textMemory){
		this.textMemory = textMemory;
	}
	public Map<String,String> getLabelMemory(){
		return labelMemory;
	}
	public void setLabelMemory(Map<String,String> labelMemory){
		this.labelMemory = labelMemory;
	}		
	/*
		Cria o Reader de uma String p/ codificar
	*/
	public String parseASMString() throws Exception{
		Reader r = new StringReader(file);
		return parseASM(r);
	}
	/*
		Cria o Reader de um Arquivo p/ codificar
	*/
	public String parseASMFile() throws Exception{
		Reader r = new FileReader(file);
		return parseASM(r);
	}
	/*
		Cria o Reader de uma String p/ decodificar
	*/
	public String parseCodeString() throws Exception{
		Reader r = new StringReader(file);
		return parseCode(r);
	}	
	/*
		Cria o Reader de um Arquivo p/ decodificar
	*/
	public String parseCodeFile() throws Exception{
		Reader r = new FileReader(file);
		return parseCode(r);
	}

	/*
		Le o arquivo .asm, nesse caso file eh uma string
		que contem tudo
	*/
	private String parseASM(Reader r) throws Exception{
		String resp = "";
		String line = "";

		try{
			
			BufferedReader br = new BufferedReader(r);

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
				//Saiu do 
				if(line == null) break;

				if(line.contains(".data")){

					while((line = br.readLine()) != null){
						if(line.matches("[\\s]*")) continue;
						if(line.contains(".globl")) continue;
						if(line.contains(".text")) break;

						//Nenhuma instrucao utiliza .data, logo soh pular
					}
					//aqui a line pode ser nula 

				}
				
				if(line == null) break;

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
			}


		}catch(Exception e){
			throw new Exception("Error Reading line: "+ line+"\n"+e.getMessage());
		}

		Encoder encoder = new Encoder();
		String encodeCurr = "";
		try{
			for(Map.Entry<String,String> ks : textMemory.entrySet()){
				encodeCurr = ks.getValue();
				resp+=encoder.encode(labelMemory,ks.getKey(),ks.getValue())+"\n";
			}

		}catch(Exception e){
			throw new Exception("Error while Encoding instruction\n"+encodeCurr+"\n"+e.getMessage());
		}

		return resp;		
	}


	/*
		Le o arquivo p/ decodificara as instrucoes

		O label memory sera usado ao contrario aqui!
		labelMemory<PosMemoria,Label>
	*/
	private String parseCode(Reader r) throws Exception{
		String resp = "";
		String line =  "";
		try{
			BufferedReader br = new BufferedReader(r);
			while((line = br.readLine()) != null){

				if(line.matches("[\\s]*")) continue; //Se n tiver nada so pula
				
				if(!line.contains("0x")){  //Para o caso de testar valores direto do mars, n sai com 0x..
					if(line.length() != 8){
						System.out.println(line.length());
						throw new Exception("Faile to accept Length of hex in"+line);
					}
					textMemory.put(currentMemory,"0x"+line);
				}else{
					if(line.substring(2).length() != 8){
						System.out.println(line.substring(2).length());
						throw new Exception("Faile to accept Length of hex in "+line);
					}
					textMemory.put(currentMemory,line);
				}

				currentMemory = Calculator.addIntToHex(4,currentMemory);
			}
		}catch(Exception e){
			throw new Exception("Error Reading line: "+ line+"\n"+e.getMessage());
			//System.out.println(e.getMessage());
		}

		Decoder decoder = new Decoder();
		//Para a memoria inicial
		labelMemory.put("0x00400000","main");
		resp+=".text\n\n";
		resp+=".globl main\n";
		String decodeCurr = "";
		try{
			//Antes textMemory<PosMem,HexInstr> agora textMemory<PosMem,Instr>
			for(Map.Entry<String,String> ks : textMemory.entrySet()){
				decodeCurr = ks.getValue();
				textMemory.put(ks.getKey(),decoder.decode(labelMemory,ks.getKey(),ks.getValue()));
				//System.out.println(decoder.decode(labelMemory,ks.getKey(),ks.getValue()));
			}

			String label = "";
			String lastMemory = "";
			for(Map.Entry<String,String> ks : textMemory.entrySet()){
				lastMemory = ks.getKey();
				if((label = labelMemory.get(ks.getKey())) != null){
					resp+= label +":\n";

				}
				decodeCurr = ks.getValue();
				resp+=ks.getValue()+"\n";

			}
			System.out.println("Last Memory "+lastMemory);
			//no caso de saltar p/ uma label q esteja no final do asm sem nada depois
			boolean shouldWriteLabel = false;
			for(Map.Entry<String,String> ks : labelMemory.entrySet()){
				if(shouldWriteLabel) resp+= ks.getValue()+":\n";
				if(ks.getKey().equals(lastMemory)) shouldWriteLabel = true;
			}
			//
			resp+=".data";



		}catch(Exception e){
			throw new Exception ("Error while Decoding hex\n"+decodeCurr+"\n"+e.getMessage());
			
			//System.out.println(e.getMessage());
		}

		return resp;		

	}	

	/*
		Recebe uma instrucao ma formatada(muitos tabs, espacos...)
		Retorna ela formatada
	*/
	public String beautifyInstruction(String horrible){
		System.out.println("Antes "+horrible);
		horrible = horrible.replaceAll("[#].*","");
		horrible = horrible.replaceAll("\\s+$","");
 		horrible = horrible.replaceAll("^[\\t\\s]*","");
		horrible = horrible.replaceAll("\\s*,",",");
		horrible = horrible.replaceAll(",\\s*",",");
		horrible = horrible.replaceAll("-\\s*","-");
		horrible = horrible.replaceAll("\\s*\\(\\s*","\\(");
		horrible = horrible.replaceAll("\\(\\s*","\\(");
		horrible = horrible.replaceAll("\\s*\\)","\\)");
		horrible = horrible.replaceAll("\\s+"," ");
		System.out.println("DPOIS "+horrible);
		return horrible;
	}
}