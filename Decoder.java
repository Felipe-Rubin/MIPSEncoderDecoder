import java.util.*;
public class Decoder{
	private Information information;
	public Decoder(){
		information = new Information();
	}
	/*
		Decodifica a instrucao
	*/
	public String decode(Map<String,String> textMemory, String currMemory, String instruction) throws Exception{
		
		String binInstruction = Calculator.hexToBinString(instruction,32);

		String opCode = binInstruction.substring(0,6);


		switch(information.getTypeByOpCode(opCode)){
			case J: return decodeTypeJ(textMemory, binInstruction);
			case BRANCH: return decodeTypeBRANCH(textMemory,currMemory,binInstruction);
			case LOADSTORE: return decodeTypeLOADSTORE(binInstruction);
			case I: return decodeTypeI(binInstruction);
			case R: 
			case SHIFT: return (information.getTypeByFuncCode(binInstruction.substring(26)) == InstructionType.R ? decodeTypeR(binInstruction) : decodeTypeSHIFT(binInstruction));
			case LUI: return decodeTypeLUI(binInstruction);
			default: throw new Exception("Error encode("+instruction+")");
		}

		
	}


	/*

	*/
	private String decodeTypeJ(Map<String,String> textMemory, String binInstruction){
		//Tem q criar a label
		String decoded = "";

		decoded+= "j ";

		String jumpAddr = binInstruction.substring(6);

		jumpAddr = "0000"+jumpAddr+"00";

		decoded+= Calculator.binToHexString(jumpAddr); 


		return decoded;
	}

	/*

	*/
	private String decodeTypeBRANCH(Map<String,String> textMemory, String currMemory, String binInstruction){
		String decoded = "";

		

		return decoded;
	}

	/*
	*/
	private String decodeTypeLOADSTORE(String binInstruction){
		String decoded = "";
		return decoded;
	}

	/*

	*/
	private String decodeTypeI(String binInstruction){
		String decoded = "";
		return decoded;
	}

	/*

	*/
	private String decodeTypeR(String binInstruction){
		String decoded = "";
		return decoded;
	}
	
	/*
	*/
	private String decodeTypeSHIFT(String binInstruction){
		String decoded = "";
		return decoded;
	}

	/*
	*/
	private String decodeTypeLUI(String binInstruction){
		String decoded = "";
		return decoded;
	}









}