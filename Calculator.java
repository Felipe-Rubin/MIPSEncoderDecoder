public class Calculator{
	private static Calculator calculator = null;

	private Calculator(){

	}

	public static Calculator getInstance(){
		if(calculator == null) calculator = new Calculator();
		return calculator;
	}

	/*
		Soma um numero ao hex
	*/
	public static String addIntToHex(int n, String hex){
		char v[] = hex.substring(2).toCharArray(); //Corta o 0x

		int i = v.length -1;

		while(n > 0){

			if(i < 0) return null; //EM caso de erro
			v[i] = nextHexChar(v[i]);
			if(v[i] == '0'){
				i--;		
			}else{
				n--;
				i = v.length -1;
			}

		}

		String hexSum = new String(v);
		return "0x"+hexSum;
	}


	/*
		Retorna o proximo hex 
	*/
	private static char nextHexChar(char c){
		switch(Character.toLowerCase(c)){
			case '0': return '1';
			case '1': return '2';
			case '2': return '3';
			case '3': return '4';
			case '4': return '5';
			case '5': return '6';
			case '6': return '7';
			case '7': return '8';
			case '8': return '9';
			case '9': return 'a';
			case 'a': return 'b';
			case 'b': return 'c';
			case 'c': return 'd';
			case 'd': return 'e';
			case 'e': return 'f';
			case 'f': return '0';
			default: return c;
		}
	}

}