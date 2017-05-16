public class Calculator{
	private static Calculator calculator = null;

	private Calculator(){

	}

	public static Calculator getInstance(){
		if(calculator == null) calculator = new Calculator();
		return calculator;
	}


	private static int hexCharToInt(char hex){

		switch(Character.toLowerCase(hex)){
			case '0': return 0;
			case '1': return 1;
			case '2': return 2;
			case '3': return 3; 
			case '4': return 4;
			case '5': return 5;
			case '6': return 6;
			case '7': return 7;
			case '8': return 8;
			case '9': return 9;
			case 'a': return 10;
			case 'b': return 11;
			case 'c': return 12;
			case 'd': return 13;
			case 'e': return 14;
			case 'f': return 15;
			default: return -1;
		}

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

	// Convert de inteiro para Hex
	// Utilizar na hora de fazer o cod HEXA
	private static char intToHexChar(int c){
		switch(c){
			case 0: return '0';
			case 1: return '1';
			case 2: return '2';
			case 3: return '3';
			case 4: return '4';
			case 5: return '5';
			case 6: return '6';
			case 7: return '7';
			case 8: return '8';
			case 9: return '9';
			case 10: return 'a';
			case 11: return 'b';
			case 12: return 'c';
			case 13: return 'd';
			case 14: return 'e';
			case 15: return 'f';
			default: return '\0'; //Pode devolver isso?
		}
	}
	/*
	OTIMIZAR DEPOIS; sepa da pra modificar hexCharToInt p/ mandar direto a string corresp
	
	OBS: tava fazendo um metodo simples e acabei fazendo o de decriptografar...

		Transforma um numero HEXADECIMAL em BINARIO
	*/

	public static String hexToBinString(String hex, int bits){
		//recebeu o hex 0x24
		//String real[] = hex.split("0x");
		//char vet[] = real[0].toCharArray(); //pegou o 24
		boolean negative = false;
		if(hex.contains("-")){
			negative = true;
			System.out.println("NEGATIVOOO");
		} 
		//char vet[] = hex.replace("0x","").toCharArray();

		//O FORMATO TEM QUE SER 0x...
		char vet[] = hex.substring(2).toCharArray(); //melhor
		
		String aux = ""; 

		for(int i = 0; i < vet.length; i++){
			
			aux+= intToBinString(hexCharToInt(vet[i]),4);

		}


		//System.out.println("aux original"+aux);
		
		if(negative) aux = complementOfTwoString(aux);

		int subIndex = aux.length() - bits;
		if(subIndex < 0){
			for(int i = 0; i < Math.abs(subIndex); i++) aux = "0"+aux;
		}

		return aux.substring(aux.length() - bits);
	}


	//Igual ao numToBinArray, soh q devolve em String
	public static String intToBinString(int n, int bits){
		String aux = new String(intToBinArray(n));

		return aux.substring(32 - bits);

	}	

	/*
		Parametros:
			Num decimal p/ converter
			Quantos bits vai ficar

		Retorna o decimal representado naquela qt de bits
	*/
	private static char[] intToBinArray(int n){
		char vet[] = initString(32);
		int index = vet.length - 1;

		boolean negative = false;
		if(n < 0){
			negative = true;
			n = n * -1;
		}

		vet = addIntToBinArray(n,vet);

		if(negative){
			vet = complementOfTwo(vet);
		}

		return vet;
	}	

	/*
		Inicializa a String com um numero de bits
	*/
	private static char[] initString(int bits){
		char resp[] = new char[bits];
		for(int i = 0; i < resp.length; i++) resp[i]= '0';
		return resp;
	}

	/*
		Recebe um vetor de char com 0's e 1's e soma c/ um inteiro
	*/

	private static char[] addIntToBinArray(int n, char vet[]){
		int index = vet.length -1;
		while(n >= 1){
			

			while(index >= 0 && vet[index] == '1'){
				vet[index] = '0';
				index--;
			}
			if(index < 0){
				index = vet.length -1;
				continue;
			}

			vet[index] = '1';
			//index--;
			n--;
			index = vet.length - 1;

		}
		return vet;	
	}	

	/*
		Faz o complemento de 2 de numero binario
	*/
	private static char[] complementOfTwo(char c[]){
		
		//Inverte
		for(int i = 0; i < c.length; i++){
			if(c[i] == '1') c[i] = '0';
			else if(c[i] == '0') c[i] = '1';
		}

		//Add +1
		c = addIntToBinArray(1,c);

		return c;
	}

	public static String complementOfTwoString(String bin){
		return new String(complementOfTwo(bin.toCharArray()));
	}

	/*
		Recebe um binario ex: 0101 e retorna o valor em hex
	*/

	public static String binToHexString(String bin){
		String resp = "0x";
		
		while((bin.length() %4) != 0){
			bin = "0"+bin;
		}

		char binVet[] = bin.toCharArray();
		int cont;
		for(int i = 0; i < binVet.length; i+=4){
			//De quatro em quatro, vai somando
			cont = 0;

			for(int j = 3; j >= 0; j--){
				
				cont+= (Math.pow(2,j) * Integer.parseInt(binVet[i+(3-j)]+"") );

			}

			resp+= intToHexChar(cont);

		}

		return resp;
	}
	
	/*
		Pega um  numero em hexa e devolve ele
		em int soh q em string
	*/
	public static int hexToInt(String hex, int bits){
		String hexVet[] = hex.split("0x");

		char v[] = hexVet[1].toCharArray();
		int cont = 0;
		for(int i = 0; i < v.length; i++){

			cont+= hexCharToInt(v[i]) * Math.pow(16,v.length-i-1);
		}

		return cont;
	}
}