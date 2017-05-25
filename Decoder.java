import java.util.*;
public class Decoder{
	private Information information; //Objeto de informacoes
	private int labelCont; //Contador p/ gerar as labels, e.g. Label_Val(labelCont)
	public Decoder(){
		labelCont = 0;
		information = new Information();
	}
	/*
		Decodifica a instrucao
	*/
	public String decode(Map<String,String> labelMemory, String currMemory, String instruction) throws Exception{
		
		String binInstruction = Calculator.hexToBinString(instruction,32);

		String opCode = binInstruction.substring(0,6);

		try{
			switch(information.getTypeByOpCode(opCode)){
				case J: return decodeTypeJ(labelMemory, binInstruction);
				case BRANCH: return decodeTypeBRANCH(labelMemory,currMemory,binInstruction);
				case LOADSTORE: return decodeTypeLOADSTORE(binInstruction);
				case I: return decodeTypeI(binInstruction);
				case R: 
				case SHIFT: return (information.getTypeByFuncCode(binInstruction.substring(26)) == InstructionType.R ? decodeTypeR(binInstruction) : decodeTypeSHIFT(binInstruction));
				case LUI: return decodeTypeLUI(binInstruction);
				default: throw new Exception("Error On OPCODE ("+instruction+")");
			}
		}catch(Exception e){
			throw new Exception("Could not Decode Hex\n"+instruction+"\n"+e.getMessage());
		}

		
	}


	/*
		Decodifica a instrucao do tipo J
	*/
	private String decodeTypeJ(Map<String,String> labelMemory, String binInstruction){
		//Tem q criar a label
		String decoded = "";

		decoded+= "j ";

		String jumpAddr = binInstruction.substring(6);

		jumpAddr = Calculator.binToHexString("0000"+jumpAddr+"00");

		String labelName = "";
		if((labelName = labelMemory.get(jumpAddr)) == null){ //Precisa criar a Label

			labelName = "Label_"+labelCont;
			labelMemory.put(jumpAddr,labelName);
			labelCont++;
		}

		decoded+= labelName; 


		return decoded;
	}

	/*
		Decodifica instrucoes do tipo BRANCH
	*/
	private String decodeTypeBRANCH(Map<String,String> labelMemory, String currMemory, String binInstruction) throws Exception{
		String decoded = "";

		String opCode = binInstruction.substring(0,6);

		//beq ou bne
		decoded+=information.getNameByOpCode(opCode)+" ";

		//rs
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(6,11)))+",";

		//rt
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(11,16)))+",";		

		//offset
		String offset = binInstruction.substring(16,32);

		int labelDistance = 0;

		if(offset.charAt(0) == '1'){ //Negativo
			labelDistance = Calculator.binToNegativeInt(offset);
		}else{ //positivo
			labelDistance = Calculator.binToInt(offset);
		}

		//Endereco da label
		String labelAddr = Calculator.addIntToHex((4 *(labelDistance +1)),currMemory);


		String labelName = "";
		if((labelName = labelMemory.get(labelAddr)) == null){ //Precisa criar a label

			labelName = "Label_"+labelCont;
			labelMemory.put(labelAddr,labelName);
			labelCont++;
		}

		decoded+= labelName;


		return decoded;
	}

	/*
		Decodifica instrucoes Load/Store
	*/
	private String decodeTypeLOADSTORE(String binInstruction) throws Exception{
		String decoded = "";
		String opCode = binInstruction.substring(0,6);

		//beq ou bne
		decoded+=information.getNameByOpCode(opCode)+" ";
		//rt
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(11,16)))+",";

		//offset
		String offset = binInstruction.substring(16,32);
		int offsetVal = 0;
		if(offset.charAt(0) == '1'){ //Negativo
			offsetVal = Calculator.binToNegativeInt(offset);
		}else{ //positivo
			offsetVal = Calculator.binToInt(offset);
		}

		decoded+=offsetVal;
		//rs
		decoded+="("+information.getRegisterName(Calculator.binToInt(binInstruction.substring(6,11)))+")";

		return decoded;
	}

	/*
		Decodifica Instrucoes do Tipo-I verificando tambem
		se o bit + significativo eh de sinal ou nao
	*/
	private String decodeTypeI(String binInstruction) throws Exception{
		String decoded = "";

		String opCode = binInstruction.substring(0,6);

		String instructionName = information.getNameByOpCode(opCode);
		//Tipo I
		decoded+=instructionName+" ";
		//rt
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(11,16)))+",";
		//rs
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(6,11)))+",";

		//imm
		String imm = binInstruction.substring(16,32);
		int immVal = 0;
		if(instructionName.equals("xori") || instructionName.equals("andi")){
			//Nao aceita negativos
			immVal = Calculator.binToInt(imm);
		}else{
			if(imm.charAt(0) == '1'){ //Negativo
				immVal = Calculator.binToNegativeInt(imm);
			}else{ //positivo
				immVal = Calculator.binToInt(imm);
			}
		}

		decoded+=immVal;


		return decoded;
	}

	/*
		Decodifica instrucoes do tipo R, (shift tem caso a parte)
	*/
	private String decodeTypeR(String binInstruction) throws Exception{
		String decoded = "";

		String funcCode = binInstruction.substring(26,32);

		//uma instr do tipo R
		decoded+=information.getNameByFuncCode(funcCode)+" ";
		//rd
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(16,21)))+",";
		//rs
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(6,11)))+",";
		//rt
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(11,16)));


		return decoded;
	}
	
	/*
		Decodifica instrucoes SLL ou SRL
	*/
	private String decodeTypeSHIFT(String binInstruction) throws Exception{
		String decoded = "";
		String funcCode = binInstruction.substring(26,32);

		//sll ou srl
		decoded+=information.getNameByFuncCode(funcCode)+" ";
		//rd
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(16,21)))+",";
		//rt
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(11,16)))+",";
		//shamt
		decoded+= Calculator.binToInt(binInstruction.substring(21,26));

		return decoded;
	}

	/*
		Decodifica a instrucao LUI
	*/
	private String decodeTypeLUI(String binInstruction) throws Exception{
		String decoded = "";
		
		String opCode = binInstruction.substring(0,6);

		//lui mesmo, soh por padronizar
		decoded+=information.getNameByOpCode(opCode)+" ";
		//rt
		decoded+=information.getRegisterName(Calculator.binToInt(binInstruction.substring(11,16)))+",";
		
		decoded+= Calculator.binToInt(binInstruction.substring(16,32));


		return decoded;
	}


}