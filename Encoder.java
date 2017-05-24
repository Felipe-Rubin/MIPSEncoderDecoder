import java.util.*;

public class Encoder{
	private Information information;
	public Encoder(){
		information = new Information();
	}

	/*
		Codifica a instrucao
	*/

	public String encode(Map<String,String> labelMemory, String currMemory, String instruction) throws Exception{
		System.out.println("INstr "+instruction);
		String instructionParts[] = instruction.split(" ");

		String encoded = "";

		//Encode OPCODE
		String opCode = information.getOP(instructionParts[0]);
		if(opCode.contains("0x")){
			encoded+=Calculator.hexToBinString(opCode,6);
		}else{
			encoded+=Calculator.intToBinString(Integer.parseInt(opCode),6);
		}

		//Pega o resto das informacoes
		try{
			switch(information.getType(instructionParts[0])){
				case J : encoded+=encodeTypeJ(labelMemory,instruction);break;
				case BRANCH : encoded+=encodeTypeBranch(labelMemory,currMemory,instruction);break;
				case LOADSTORE: encoded+=encodeTypeLOADSTORE(instruction);break;
				case I: encoded+=encodeTypeI(instruction);break;
				case R: encoded+=encodeTypeR(instruction);break;
				case SHIFT: encoded+=encodeTypeSHIFT(instruction);break;
				case LUI: encoded+=encodeTypeLUI(instruction);break;
				default: throw new Exception("Error Type encode("+instruction+")");
			}
		}catch(Exception e){
			throw new Exception("Could not Encode instruction\n"+instruction+"\n"+e.getMessage());
		}

		//Retorna convertendo binario pra hexa
		return Calculator.binToHexString(encoded);

	}

	/*
		Codifica instrucoes do tipo J
	*/
	private String encodeTypeJ(Map<String,String> labelMemory, String instruction){
	
		String instructionParts[] = instruction.split(" ");
		
		return (Calculator.hexToBinString(labelMemory.get(instructionParts[1]),32)).substring(4,30);

	}

	/*
		Codifica instrucoes do tipo Branch
	*/
	private String encodeTypeBranch(Map<String,String> labelMemory,String currMemory,String instruction) throws Exception{
		//ordem OP, rs, rt, offset
		String instructionParts[] = instruction.split(" ");

		String actualParts[] = instructionParts[1].split(",");

		String encoded = "";
		//rs
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);
		//rt
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[1]),5);

		String pcMemory = Calculator.addIntToHex(4,currMemory);

		int labelAddr = Calculator.hexToInt(labelMemory.get(actualParts[2]),32);

		int pcAddr = Calculator.hexToInt(pcMemory,32);

		encoded+= Calculator.intToBinString((labelAddr - pcAddr)/4,16);

		return encoded;
	}

	/*
		Codifica instrucoes do tipo LOADSTORE
	*/
	private String encodeTypeLOADSTORE(String instruction) throws Exception{
		/*
			original: op, rs ,rt, offset
			rs sendo o dentro dos parenteses
			3 casos Possiveis
			1) lw/sw $t1,100
			2) lw/sw $t0,-100($t2)
			3) lw/sw $t0,($t2)
		*/

		String instructionParts[] = instruction.split(" ");

		String actualParts[] = instructionParts[1].split("(,\\()|(,)|(\\()|(\\))");

		String encoded = "";

		//encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);

		if(actualParts.length == 3){ //Caso 2

			encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[2]),5);
			encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);

			if(actualParts[1].contains("0x")){
				encoded+=Calculator.hexToBinString(actualParts[1],16);
			}else{
				encoded+=Calculator.intToBinString(Integer.parseInt(actualParts[1]),16);
			}

		}else{
			throw new Exception("Erro encode LW/SW");
		}
		/*
			Os outros casos abaixo funcionam, mas nao estaria correto codificar se n tem as
			3 partes.
		*/
		/*else{
			//casos 1 ou 3

			if(actualParts[1].contains("$")){
				//Caso  3

				encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[1]),5);
				encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);
				encoded+=Calculator.intToBinString(0,16);
			}else{
				//Caso 1
				encoded+=Calculator.intToBinString(information.getRegisterValue("$0"),5);
				encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);
				if(actualParts[1].contains("0x")){
					encoded+=Calculator.hexToBinString(actualParts[1],16);
				}else{
					encoded+=Calculator.intToBinString(Integer.parseInt(actualParts[1]),16);
				}
			}
		}*/

		return encoded;
	}

	/*
		Codifica instrucoes do tipo I
	*/
	private String encodeTypeI(String instruction) throws Exception{
		String instructionParts[] = instruction.split(" ");
		String actualParts[] = instructionParts[1].split(",");

		String encoded = "";

		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[1]),5);
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);
		if(actualParts[2].contains("0x")){
			System.out.println(instruction);
			encoded+=Calculator.hexToBinString(actualParts[2],16);
		}else{
			encoded+=Calculator.intToBinString(Integer.parseInt(actualParts[2]),16);
		}

		return encoded;
	}

	/*
		Codifica instrucoes do tipo R
	*/
	private String encodeTypeR(String instruction) throws Exception{
		String instructionParts[] = instruction.split(" ");

		String actualParts[] = instructionParts[1].split(",");

		String encoded = "";
		//rs
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[1]),5);
		//rt
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[2]),5);
		//rd
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);
		//shamt
		encoded+=Calculator.intToBinString(0,5);

		String funcCode = information.getFunc(instructionParts[0]);

		if(funcCode.contains("0x")){

			encoded+=Calculator.hexToBinString(funcCode,6);
		}else{
			encoded+=Calculator.intToBinString(Integer.parseInt(funcCode),6);
		}

		return encoded;
	}

	/*
		Codifica instrucoes do tipo SHIFT
	*/
	private String encodeTypeSHIFT(String instruction) throws Exception{
		String instructionParts[] = instruction.split(" ");

		String actualParts[] = instructionParts[1].split(",");
		
		String encoded = "";

		//rs
		encoded+=Calculator.intToBinString(0,5);
		//rt
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[1]),5);
		//rd
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);
		//shamt
		if(actualParts[2].contains("0x")){
			encoded+=Calculator.hexToBinString(actualParts[2],5);
		}else{
			encoded+=Calculator.intToBinString(Integer.parseInt(actualParts[2]),5);
		}
		//func

		String funcCode = information.getFunc(instructionParts[0]);

		if(funcCode.contains("0x")){
			encoded+=Calculator.hexToBinString(funcCode,6);
		}else{
			encoded+=Calculator.intToBinString(Integer.parseInt(funcCode),6);
		}


		return encoded;
	}

	/*
		Codifica instrucoes do tipo LUI
	*/
	private String encodeTypeLUI(String instruction) throws Exception{
		
		String instructionParts[] = instruction.split(" ");

		String actualParts[] = instructionParts[1].split(",");

		String encoded = "";

		//rs
		encoded+=Calculator.intToBinString(information.getRegisterValue("$0"),5);
		//rt
		encoded+=Calculator.intToBinString(information.getRegisterValue(actualParts[0]),5);

		//imm
		// PRECISA CHECAR SE EH UM NUMERO NEGATIVO ? PQ ELE N PODE CARREGAR NEGATIVOS
		if(actualParts[1].contains("0x")){
			encoded+=Calculator.hexToBinString(actualParts[1],16);
		}else{
			encoded+=Calculator.intToBinString(Integer.parseInt(actualParts[1]),16);
		}

		return encoded;
	}	


}