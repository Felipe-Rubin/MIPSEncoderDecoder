/*
	Autor: Felipe Pfeifer Rubin
	Matricula: 151050853
	Email: felipe.rubin@acad.pucrs.br

	Responsavel por armazenar as informacoes de 
	codificacao e decodificao das instrucoes
*/
import java.util.*;
public class Information{
	private Map<String,InstructionType> instructions; //tipo das instrucoes
	private Map<String,String> op; //op code das instrucoes <Nome,OP>
	private Map<String,String> func; //Soh p/ quem tem op = 0
	private Map<String,InstructionType> opType; //tipo das instrucoes pelo opcode
	private Map<String,InstructionType> funcType; //tipo das instrucoes pelo func code

	private Map<String,String> opName; // <OPbinario,Nome>
	private Map<String,String> funcName; //<FuncBinario,Nome>
	
	/*
		Cria todas as informacoes de codificacao e decodificacao
	*/
	public Information(){

		instructions = new HashMap<>();
		op = new HashMap<>();
		func = new HashMap<>();
		opType = new HashMap<>();
		funcType = new HashMap<>();
		opName = new HashMap<>();
		funcName = new HashMap<>();


		instructions.put("j",InstructionType.J);
		op.put("j","2");
		opType.put("000010",InstructionType.J);
		opName.put("000010","j");

		instructions.put("beq",InstructionType.BRANCH);
		op.put("beq","4");
		opType.put("000100",InstructionType.BRANCH);
		opName.put("000100","beq");

		instructions.put("bne",InstructionType.BRANCH);
		op.put("bne","5");
		opType.put("000101",InstructionType.BRANCH);
		opName.put("000101","bne");		

		instructions.put("lw",InstructionType.LOADSTORE);
		op.put("lw","0x23");
		opType.put("100011",InstructionType.LOADSTORE);
		opName.put("100011","lw");				

		instructions.put("sw",InstructionType.LOADSTORE);
		op.put("sw","0x2b");
		opType.put("101011",InstructionType.LOADSTORE);
		opName.put("101011","sw");	

		instructions.put("xori",InstructionType.I);
		op.put("xori","0xe");
		opType.put("001110",InstructionType.I);
		opName.put("001110","xori");	

		instructions.put("addiu",InstructionType.I);
		op.put("addiu","9");
		opType.put("001001",InstructionType.I);
		opName.put("001001","addiu");	

		instructions.put("sltiu",InstructionType.I);
		op.put("sltiu","0xb");		
		opType.put("001011",InstructionType.I);
		opName.put("001011","sltiu");	

		instructions.put("andi",InstructionType.I);
		op.put("andi","0xc");
		opType.put("001100",InstructionType.I);
		opName.put("001100","andi");	

		instructions.put("addu",InstructionType.R);
		op.put("addu","0");
		func.put("addu","0x21");
		funcType.put("100001",InstructionType.R);	
		funcName.put("100001","addu");

		instructions.put("subu",InstructionType.R);
		op.put("subu","0");
		func.put("subu","0x23");
		funcType.put("100011",InstructionType.R);
		funcName.put("100011","subu");

		instructions.put("or",InstructionType.R);
		op.put("or","0");
		func.put("or","0x25");
		funcType.put("100101",InstructionType.R);
		funcName.put("100101","or");

		instructions.put("slt",InstructionType.R);
		op.put("slt","0");
		func.put("slt","0x2a");
		funcType.put("101010",InstructionType.R);
		funcName.put("101010","slt");


		opType.put("000000",InstructionType.R);


		instructions.put("sll",InstructionType.SHIFT);
		op.put("sll","0");
		func.put("sll","0");
		funcType.put("000000",InstructionType.SHIFT);
		funcName.put("000000","sll");


		instructions.put("srl",InstructionType.SHIFT);
		op.put("srl","0");
		func.put("srl","2");
		funcType.put("000010",InstructionType.SHIFT);
		funcName.put("000010","srl");


		instructions.put("lui",InstructionType.LUI);
		op.put("lui","0xf");
		opType.put("001111",InstructionType.LUI);
		opName.put("001111","lui");


	}

	/*
		Retorna nome da instrucao a partir do Op Code
	*/
	public String getNameByOpCode(String opCode) throws Exception{
		String name = opName.get(opCode);
		if (name != null) return name;
		else throw new Exception("Instruction not found for \n opcode " +opCode);
		
	}
	/*
		Retorna nome da instrucao a parti do Func Code
	*/
	public String getNameByFuncCode(String funcCode) throws Exception{
		String name = funcName.get(funcCode);
		if(name != null) return name;
		else throw new Exception("Instruction not found for \n funcCode " +funcCode);
	}
	/*
		Retorna o tipo da instrucao a partir do Op Code

	*/
	public InstructionType getTypeByOpCode(String opCode) throws Exception{
		InstructionType type = opType.get(opCode);
		if(type != null) return type;
		else throw new Exception("Instruction Type not found for \n opCode "+opCode);
	}

	/*
		Retorna o tipo da instrucao a partir do func Code,
		importante, pois o tipo SHIFT na verdade eh do tipo R
	*/
	public InstructionType getTypeByFuncCode(String funcCode) throws Exception{
		InstructionType type = funcType.get(funcCode);
		if(type != null) return type;
		else throw new Exception("Instruction Type not found for \n funcCode "+funcCode);
	}

	/*
		Retorna o tipo da Instrucao a partir do seu nome
	*/
	public InstructionType getType(String instr) throws Exception{
		InstructionType type = instructions.get(instr);
		if(type != null) return type;
		else throw new Exception("Instruction Type not found for \n instruction "+instr);
	}

	/*
		Retorna o OPCode da Instrucao
	*/
	public String getOP(String instr) throws Exception{
		String opc = op.get(instr);
		if(opc != null) return opc;
		else throw new  Exception("OPCode not found for \n instruction "+instr);
	}

	/*
		Retorna o FuncCode da Instrucao
	*/
	public String getFunc(String instr) throws Exception{
		String fcd = func.get(instr);
		if(fcd != null) return fcd;
		else throw new Exception("FuncCode Type not found for \n instruction "+instr);
	}

	/*
		Recebe um "nome" do registrador e retorna o valor dele
	*/
	public int getRegisterValue(String register) throws Exception{
		switch(register){
			case "$zero":
			case "$0":  return 0;
			case "$at":
			case "$1" : return 1 ;
			case "$v0": 
			case "$2" : return 2 ;
			case "$v1":
			case "$3" : return 3 ;
			case "$a0": 
			case "$4" : return 4 ;
			case "$a1": 
			case "$5" : return 5 ;
			case "$a2": 
			case "$6" : return 6 ;
			case "$a3": 
			case "$7" : return 7 ;
			case "$t0":
			case "$8" : return 8 ;
			case "$t1": 
			case "$9" : return 9 ;
			case "$t2": 
			case "$10": return 10 ;
			case "$t3": 
			case "$11": return 11 ;
			case "$t4":
			case "$12": return 12 ;
			case "$t5": 
			case "$13": return 13 ;
			case "$t6":
			case "$14": return 14 ;
			case "$t7": 
			case "$15": return 15 ;
			case "$s0": 
			case "$16": return 16 ;
			case "$s1": 
			case "$17": return 17 ;
			case "$s2": 
			case "$18": return 18 ;
			case "$s3": 
			case "$19": return 19 ;
			case "$s4": 
			case "$20": return 20 ;
			case "$s5": 
			case "$21": return 21 ;
			case "$s6": 
			case "$22": return 22 ;
			case "$s7": 
			case "$23": return 23 ;
			case "$t8": 
			case "$24": return 24 ;
			case "$t9": 
			case "$25": return 25 ;
			case "$k0": 
			case "$26": return 26 ;
			case "$k1": 
			case "$27": return 27 ;
			case "$gp": 
			case "$28": return 28 ;
			case "$sp": 
			case "$29": return 29 ;
			case "$fp": 
			case "$30": return 30 ;
			case "$ra": 
			case "$31": return 31 ;
			default: throw new Exception("This register doesn't exist: "+register);
		}
	}

	/*
		Recebe o valor de um registrador e retorna seu nome
	*/
	public String getRegisterName(int register) throws Exception {
		switch(register){
			case 0: return "$zero"; 
			case 1: return "$at" ;
			case 2: return "$v0" ;
			case 3: return "$v1" ;
			case 4: return "$a0" ;
			case 5: return "$a1" ;
			case 6: return "$a2" ;
			case 7: return "$a3" ;
			case 8: return "$t0" ;
			case 9: return "$t1" ;
			case 10: return "$t2" ;
			case 11: return "$t3" ;
			case 12: return "$t4" ;
			case 13: return "$t5" ;
			case 14: return "$t6" ;
			case 15: return "$t7" ;
			case 16: return "$s0" ;
			case 17: return "$s1" ;
			case 18: return "$s2" ;
			case 19: return "$s3" ;
			case 20: return "$s4" ;
			case 21: return "$s5" ;
			case 22: return "$s6" ;
			case 23: return "$s7" ;
			case 24: return "$t8" ;
			case 25: return "$t9" ;
			case 26: return "$k0" ;
			case 27: return "$k1" ;
			case 28: return "$gp" ;
			case 29: return "$sp" ;
			case 30: return "$fp" ;
			case 31: return "$ra" ;
			default: throw new Exception("This register doesn't exist: "+ register);
		}			
	}


}