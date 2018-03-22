import java.util.InputMismatchException;
import java.util.Scanner;
/*
 * 
 * 		Program oblicza funkcje autokorelacji kolejnych stanów wygenerowanych za pomocą LFSR
 * 		Uzytkownik musi podac liczbę wspolczynników wielomianu pierwotnego a nastepnie wartości 
 * 		kolejnych wspolczynnikow.
 * 		Na podstawie podanego wielomianu program oblicza wszystkie mozliwe stany m-sekwencji.
 * 		
 * 		W przypadku podania wielomianu, ktory nie jest wielomianem pierwotny uzytkownik zostanie 
 * 		o tym poinformowany.
 * 		
 * 		W przypadku podania nieprawidlowych danych program się zakończy.
 * 
 * 
 */
public class main
{
	
	public static void main(String[] args)
	{
		int polynomial =0;
		int first = 0;
		int factorNumber = 0;
		int POLY_length = 0;
		int StingLength =0;
		String Spolynomial="";
		
		
		System.out.println("Podaj liczbę wszystkich wspolczynnikow wielomianu pierwonego: ");
		Scanner read = new Scanner(System.in);
		try
		{
			factorNumber = read.nextInt();
		}
		catch (InputMismatchException ex) 
		{
			System.out.println("Złe dane wejsciowe....");System.exit(0);
		}
		
		System.out.println("Podaj pierwszy wspolczynnik wielomianu pierwonego: ");
		read = new Scanner(System.in);
		try
		{
			first = read.nextInt();
			POLY_length = first;
			
		}
		catch (InputMismatchException ex) 
		{
			System.out.println("Złe dane wejsciowe....");System.exit(0);
		}
		
		
		
		double size1 = Math.pow(2, POLY_length)-1;
		double size2 = POLY_length;
		
		int tabPolynominal[][] = new int[(int)size1] [(int)size2];
		int tabXORs [] = new int[(int)size2];
		int poly [] = new int[(int)size2];
		int StartingSequence[] = new int[(int)size2];
		StartingSequence[(int)size2-1] = 1;
		
		poly[0] = first;
		for(int i=1; i<factorNumber; i++)
		{
			System.out.println("Podaj kolejny wspolczynnik wielomianu pierwonego: ");
			read = new Scanner(System.in);
			try
			{
					poly[i] = read.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Złe dane wejsciowe....");System.exit(0);
			}
		
		}
		for(int i=0 ;i< factorNumber-1; i++)
		{
			if(poly[i]<poly[i+1]) 
			{
				System.out.println("Złe dane wejsciowe....");System.exit(0);
			}
		}
		
		System.out.print("Podany wielomian to:  ");
		for(int i=0;i<factorNumber;i++)
		{
			System.out.print("x^");System.out.print(poly[i]);System.out.print(" + ");
		}
		System.out.print("1");System.out.println("");
		
		// uzupelnianie tablicy 2D zerami
		for(int i=0; i<size1; i++)
		{
			for(int j=0; j<size2; j++)
			{
				tabPolynominal[i][j] = 0;
			}
		}	

		//tworzenie tablicy bramek XOR
		for(int i=0; i<factorNumber; i++)
		{
				tabXORs[i] = 0;
		}
		for(int i=1; i<factorNumber;i++)
		{
			//int tempIndex = (int)size2 -  (Spolynomial.charAt(i) - 48);
			int tempIndex =  poly[0] - poly[i] - 1;
			tabXORs[tempIndex] = 1;
		}
		//wyswietlenie tablicy bramek XOR
		System.out.print("XORs table: ");
		for(int i=0; i<size2; i++)
		{
			 System.out.print(tabXORs[i]);   
		}
		System.out.println();System.out.println();
		System.out.print("Starting sequence: ");
		for(int i=0; i<size2; i++)
		{
			 System.out.print(StartingSequence[i]);   
			 tabPolynominal[0][i] = StartingSequence[i];
		}

		//obliczenia
		int tempvalue=0;
		//int size = POLY_length*POLY_length -1;
		for(int i=0; i<size1-1; i++)
		{
			 tempvalue = tabPolynominal[i][(int)size2-1];
			 for(int k=0; k<tabXORs.length; k++)
			 {
				 if(tabXORs[k] == 1)
				 {
					 tempvalue = (tempvalue + tabPolynominal[i][k]) % 2;
				 }
			}
			for(int j=1; j<size2 ;j++)
			{	 
				 tabPolynominal[i+1][j] = tabPolynominal[i][j-1];
				 tabPolynominal[i+1][0] = tempvalue;
			}
		}
		
		// sprawdzenie
		int isOKAY=0;
		int isokayTimer=0;
		for(int i=1;i<size1;i++)
		{
			for(int j=0; j<size2;j++)
			{
				 if(tabPolynominal[i][j] == tabPolynominal[0][j])
				 {
					 isOKAY=0;
				 }
				 else
				 {
					 isOKAY=1;break;
				 }
			}
			if(isOKAY==1)
			{
				isokayTimer++; 
			}
			else break;
		}
		
		//wyswietalanie rezultatu
		System.out.println();System.out.println();
		
		for(int i=0; i<isokayTimer+1 ;i++)
		{
			for(int j=0; j<size2;j++)
			{
				 System.out.print(tabPolynominal[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		if(isokayTimer == size1-1)
		{
			System.out.println("Is OKAY :))  !!");	System.out.println();
		}
		else
		{
			System.out.print("To nie jest wielomian pierwotny ponieważ liczba różnych sanów wynosi: ");
			System.out.print(isokayTimer+1);
			System.out.print(" zamiast ");System.out.println((int)size1);
		}
		
		
		System.out.println("Wartości wyjściowe:");
		int tabOutputs[] = new int[(int)size1];
		for(int i=0; i<size1; i++)
		{
			if(tabPolynominal[i][(int)size2-1] ==1 )tabOutputs[i]=1;
			else tabOutputs[i]=-1;
		}
		for(int i=0; i<size1; i++)
		{
			System.out.print("Output ");System.out.print(i);System.out.print(": ");System.out.println(tabOutputs[i]);
		}	System.out.println();
		System.out.println("Obliczanie funkcji autokorelacji");
		int suma=0;
		for(int j =0; j<size1; j++)
		{
			suma =0;
			for(int i=0; i<size1; i++)
			{
				suma = suma  + tabOutputs[i]*tabOutputs[(i+j)%(int)size1];
			}
			suma = suma %((int)size1+1);
			System.out.print("R[");System.out.print(j);System.out.print("] = ");System.out.println(suma);
		}
	
		
	}

}
