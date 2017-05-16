import java.util.*;
public class Information{
	private Map<String,InstructionType> instructions;
	private Map<String,String> op;
	private Map<String,String> func; //Soh p/ quem tem op = 0
	public Information(){

		instructions = new HashMap<>();
		op = new HashMap<>();
		func = new HashMap<>();

		instructions.put("j",InstructionType.J);
		op.put("j","2");
		instructions.put("beq",InstructionType.BRANCH);
		op.put("beq","4");
		instructions.put("bne",InstructionType.BRANCH);
		op.put("bne","5");
		instructions.put("lw",InstructionType.LOADSTORE);
		op.put("lw","0x23");
		instructions.put("sw",InstructionType.LOADSTORE);
		op.put("sw","0x2b");

		instructions.put("xori",InstructionType.I);
		op.put("xori","0xe");		
		instructions.put("addiu",InstructionType.I);	
		op.put("addiu","9");	
		instructions.put("sltiu",InstructionType.I);
		op.put("sltiu","0xb");		
		instructions.put("andi",InstructionType.I);
		op.put("andi","0xc");

		instructions.put("addu",InstructionType.R);
		op.put("addu","0");
		func.put("addu","0x21");
		instructions.put("subu",InstructionType.R);
		op.put("subu","0");
		func.put("subu","0x23");
		instructions.put("or",InstructionType.R);
		op.put("or","0");
		func.put("or","0x25");
		instructions.put("slt",InstructionType.R);
		op.put("slt","0");
		func.put("slt","0x2a");

		instructions.put("sll",InstructionType.SHIFT);
		op.put("sll","0");
		func.put("sll","0");
		instructions.put("srl",InstructionType.SHIFT);
		op.put("srl","0");
		func.put("srl","2");

		instructions.put("lui",InstructionType.LUI);
		op.put("lui","0xf");

	}

	/*
		Retorna o tipo da Instrucao
	*/
	public InstructionType getType(String instr){
		return instructions.get(instr);
	}

	/*
		Retorna o OPCode da Instrucao
	*/
	public String getOP(String instr){
		
		return op.get(instr);

	}

	/*
		Retorna o FuncCode da Instrucao
	*/
	public String getFunc(String instr){

		return func.get(instr);
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


	/*
		Fazer os metodos pra decodificacao
	*/




}