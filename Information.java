
public class Information{
	 
	public Information(){

	}

	private class Register{
		 
		public Register(){

		}


		public int getRegisterValue(String register) throws Exception{
			switch(register){
				case "$zero": return 0; 
				case "$at": return 1 ;
				case "$v0": return 2 ;
				case "$v1": return 3 ;
				case "$a0": return 4 ;
				case "$a1": return 5 ;
				case "$a2": return 6 ;
				case "$a3": return 7 ;
				case "$t0": return 8 ;
				case "$t1": return 9 ;
				case "$t2": return 10 ;
				case "$t3": return 11 ;
				case "$t4": return 12 ;
				case "$t5": return 13 ;
				case "$t6": return 14 ;
				case "$t7": return 15 ;
				case "$s0": return 16 ;
				case "$s1": return 17 ;
				case "$s2": return 18 ;
				case "$s3": return 19 ;
				case "$s4": return 20 ;
				case "$s5": return 21 ;
				case "$s6": return 22 ;
				case "$s7": return 23 ;
				case "$t8": return 24 ;
				case "$t9": return 25 ;
				case "$k0": return 26 ;
				case "$k1": return 27 ;
				case "$gp": return 28 ;
				case "$sp": return 29 ;
				case "$fp": return 30 ;
				case "$ra": return 31 ;
				default: throw new Exception("This register doesn't exist");
			}
		}

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
				default: throw new Exception("This register doesn't exist");
			}			
		}
	}

	private class Instruction {
		private String name;
		
		public Instruction(){

		}
	}




}