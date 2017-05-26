/*
	Autor: Felipe Pfeifer Rubin
	Matricula: 151050853
	Email: felipe.rubin@acad.pucrs.br

	Calculadora, responsavel por conversoes bin,hex,decimal
	
*/
public class Calculator{
	private static Calculator calculator = null;

	private Calculator(){

	}
	/*
		Utilizado de modo que so exista uma instancia de tal calculadora
	*/
	public static Calculator getInstance(){
		if(calculator == null) calculator = new Calculator();
		return calculator;
	}

	/*
		Converte um caracter hexadecimal em seu valor decimal
	*/
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
			//default: throw new Exception("Character "+hex+" is not a Hex character");
		}

	}

	/*
		Soma um numero inteiro a um hexadecimal representado por uma string
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

		while(n < 0){
			for(int j = v.length -1; j >= 0; j--){
				v[j] = prevHexChar(v[j]);

				if(v[j] != 'f')break;
			}
			n++;
		}

		String hexSum = new String(v);
		return "0x"+hexSum;
	}

	/*
		Recebe um caracter hexadecimal e retorna o valor anterior a este
	*/
	private static char prevHexChar(char c){
		switch(Character.toLowerCase(c)){
			case '0': return 'f';
			case '1': return '0';
			case '2': return '1';
			case '3': return '2';
			case '4': return '3';
			case '5': return '4';
			case '6': return '5';
			case '7': return '6';
			case '8': return '7';
			case '9': return '8';
			case 'a': return '9';
			case 'b': return 'a';
			case 'c': return 'b';
			case 'd': return 'c';
			case 'e': return 'd';
			case 'f': return 'e';
			default: return c;
		}		
	}	
	/*
		Retorna um caracter hexadecimal e retorna o proximo valor deste 
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

	/* 	
		Converte de inteiro para seu Hex representante
		
	*/
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
			default: return '\0';
		}
	}
	/*
		Transforma um numero hexadecimal em binario
	*/

	public static String hexToBinString(String hex, int bits){
		//recebeu o hex 0x24
		//String real[] = hex.split("0x");
		//char vet[] = real[0].toCharArray(); //pegou o 24
		boolean negative = false;
		int substringAux = 2; //Se for - vai ser 3, pois -0x vs 0x
		if(hex.contains("-")){
			substringAux = 3;
			negative = true;
		} 
		//char vet[] = hex.replace("0x","").toCharArray();

		//O FORMATO TEM QUE SER 0x...
		char vet[] = hex.substring(substringAux).toCharArray(); //melhor
		
		String aux = ""; 

		for(int i = 0; i < vet.length; i++){
			
			aux+= intToBinString(hexCharToInt(vet[i]),4);

		}

		int subIndex = aux.length() - bits;
		if(subIndex < 0){
			for(int i = 0; i < Math.abs(subIndex); i++) aux = "0"+aux;
		}

		if(negative) aux = complementOfTwoString(aux);


		return aux.substring(aux.length() - bits);
	}


	/*
		Recebe um numero inteiro e converte para o binario representante

	*/
	public static String intToBinString(int n, int bits){
		String aux = new String(intToBinArray(n));

		return aux.substring(32 - bits);

	}	

	/*
		Parametros:
			Num decimal p/ converter
			Quantos bits vai ficar

		Retorna o decimal representado naquela quantidade de bits
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
		Inicializa a String com um numero de bits 0
	*/
	private static char[] initString(int bits){
		char resp[] = new char[bits];
		for(int i = 0; i < resp.length; i++) resp[i]= '0';
		return resp;
	}

	/*
		Recebe um vetor de char com 0's e 1's e soma com um inteiro
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
		while(n < 0){
			for(int i = vet.length -1; i >= 0; i--){
				if(vet[i] == '0') continue;
				else{
					vet[i] = '0';
					i++;
					while(i != vet.length){
						vet[i] = '1';
						i++;
					}
					n++;
					break;
				}
			}
		}

		return vet;	
	}	

	/*
		Faz o complemento de 2 de numero binario em um vetor de caracteres
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

	/*
		Faz o complemento de 2 de um numero binario utilizando o metodo privado
		complementOfTwo(char c[])
	*/

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


	/*
		Recebe um binario e transforma em decimal
	*/
	public static int binToInt(String bin){
	 int cont = 0;

	 char v[] = bin.toCharArray();
	 for(int i = v.length -1; i >= 0; i--){
	 	if(v[i] == '1') cont+= Math.pow(2,v.length - i - 1);
	 }

	 return cont;
	}

	/*
		Recebe um binario negativo (PRE CONDICAO!)
		Retorna o inteiro negativo representando
	*/	

	public static int binToNegativeInt(String bin){

		int cont = 0;
		
		/*
			Desfaz o Complemento de 2
		*/
		char v[] = bin.toCharArray();

		v = addIntToBinArray(-1,v);

		for(int i = 0; i < v.length; i++){
			if(v[i] == '0') v[i] = '1';
			else v[i] = '0';
		}
		for(int i = v.length -1; i >= 0; i--){
	 		if(v[i] == '1') cont+= Math.pow(2,v.length - i - 1);
	 	}

		return  (- cont);
	}

	/*
		Recebe duas strings hexadecimais
	 	Retorna True, se primeiro > segundo
	 	Retorna False, se primeiro <= segundo

	*/

	public static boolean hexBiggerThan(String h1, String h2){
		if(h1.equals(h2)) return false;
		if(h1.contains("-") && !h2.contains("-")) return false;
		if(!h1.contains("-") && h2.contains("-")) return true;
		if(h1.contains("-") && h2.contains("-")) 
			return !hexBiggerThan(h1.substring(1),h2.substring(1));
		
	 	char hex1[] = h1.replaceAll("(0x0*)","").toLowerCase().toCharArray();
	 	char hex2[] = h2.replaceAll("(0x0*)","").toLowerCase().toCharArray();

	 	if(hex1.length > hex2.length) return true;

	 	if(hex2.length > hex1.length) return false;

	 	for(int i = 0; i < hex1.length; i++){
	 		if(hexCharToInt(hex1[i]) > hexCharToInt(hex2[i])) return true;
	 	}

	 	return false;
	 }

}