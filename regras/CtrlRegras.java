package regras;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gui.Jogador;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class CtrlRegras implements Observable {	
	int tabuleiro1 [][];
	int tabuleiro2 [][];
	String jog1;
	String jog2;
	
	Jogador j1;
	Jogador j2;
	
	String jogs[] = new String[2];
	
	int vez=5;
	
	Object data[] = new Object[5];
	List<Observer> lob=new ArrayList<Observer>();
	public CtrlRegras() {
		this.tabuleiro1 = new int[15][15];
		this.tabuleiro2 = new int[15][15];
		int i, j;
		for(i = 0; i < 15;i++){
			for(j = 0; j < 15;j++){
				this.tabuleiro1[i][j] = -1;
				this.tabuleiro2[i][j] = -1;
			}	
		}
		this.jog1 = "";
		this.jog2 = "";
		
		j1 = Jogador.criaJogador("stub", 1);
		j2 = Jogador.criaJogador("stub", 2);

	}

	public int[][] getMatriz(short numTab) {
		if(numTab == 1)
			return tabuleiro1;
		else
			return tabuleiro2;
	}
	
	public void setValor(int i, int j, short numTab , int numPeca) {
		
		if(numTab == 1) {
			if (this.tabuleiro1[j][i] == -1) {
				this.tabuleiro1[j][i] = numPeca;
				return;
			}	
		}
		else {
			if (this.tabuleiro2[j][i] == -1) {
				this.tabuleiro2[j][i] = numPeca;
				return;
			}	
		}
	}		
	
	public void setJogadores(String jog1, String jog2) {
		
		this.jog1 = jog1;
		this.jog2 = jog2;

		jogs[0] = jog1;
		jogs[1] = jog2;
		
		data[0] = "regras";
		data[1] = tabuleiro1;
		data[2] = tabuleiro2;
		data[3] = vez;
		data[4] = jogs;
		for(Observer o:lob)
			o.notify(this);
	}

	public boolean verificaConflito(int posX , int posY, short numTab) {
		int [][] tabuleiro;		
		
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		if(tabuleiro[posY][posX] != -1) {
			return false;
		}
		return true;
	}
	
	public boolean verificaPeca(int posX , int posY, short numTab) 
	{
		int [][] tabuleiro;		
		
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		
		if(tabuleiro[posY][posX] >= 0) {
			return true;
		}
		return false;
	}
	
	public short orderAttack(String jog, int posX , int posY, short numTab)
	{
		int [][] tabuleiro;		

		int celX = (posX - 40) / (30 + 5); //posIni = 40 && larg = 30 && espLinha = 5
		int celY = (posY - 40) / (30 + 5); //posIni = 40 && alt = 30 && espLinha = 5
		
		//System.out.printf("posX : %d  posY: %d celX: %d celY: %d\n", posX,posY, celX, celY);
		
		
		
		// 1 -> Possui uma peca nao atacada
		// 2 -> Possui uma peca atacada
		// 3 -> Nao possui nada
		
		
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		
		
		if(tabuleiro[celY][celX] >= 0 && tabuleiro[celY][celX] < 100 ) 
		{
			
			tabuleiro[celY][celX] = tabuleiro[celY][celX] + 100;
			System.out.printf("configurando celula como atacada com valor: %d\n" ,tabuleiro[celY][celX]);
			return 1;
		}
		else if(tabuleiro[celY][celX] <= -1 && tabuleiro[celY][celX] != -500)
		{
			System.out.println("configurando celula como erro");
			tabuleiro[celY][celX] = -500;
			return -1;
		}
		
		
		if(numTab == 1) {
			this.tabuleiro1 = tabuleiro  ;
		}
		else {
			this.tabuleiro2 = tabuleiro  ;
		}
		
		return 0;
	}

	public boolean checaPecaAfundada(int numTab, int posX , int posY)
	{
		System.out.println("checando afundada ");
		int [][] tabuleiro;	
		

		int celX = (posX - 40) / (30 + 5); //posIni = 40 && larg = 30 && espLinha = 5
		int celY = (posY - 40) / (30 + 5);
		

		
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}

		int numPeca = tabuleiro[celY][celX] - 100;
		System.out.printf("conferindo peça : %d", numPeca );
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == numPeca)
				{
					return false; 					
				}				
			}
		}
		
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == numPeca + 100)
				{
					tabuleiro[i][j] = 200;					
				}				
			}
		}
		System.out.printf("afundou");
		return true;
	}
	
	public boolean checaVencedor(int numTab)
	{
		int [][] tabuleiro;	
		int contador = 0;
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == 200)
				{
					contador++;
				}
				
			}	
		}
		
		if(contador == 38)
		{
			System.out.println("VENCEU");
			return true;
		}
		else
		{
			return false;
		}
	}	
	
	public void setPadding(short numTab , int numPeca)
	{
		int [][] tabuleiro;	
		int contador = 0;
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == numPeca)
				{
					if( j > 0 && j < 14)
					{
						if(tabuleiro[i][j-1] <= -1)
						{
							tabuleiro[i][j-1] = tabuleiro[i][j-1] - (100+numPeca);
						}
						if(tabuleiro[i][j+1] <= -1)
						{
							tabuleiro[i][j+1] = tabuleiro[i][j+1] - (100+numPeca);
						}
						if(i > 0)
						{
							if(tabuleiro[i-1][j-1] <= -1)
							{
								tabuleiro[i-1][j-1] = tabuleiro[i-1][j-1] - (100+numPeca);
							}
							if(tabuleiro[i-1][j+1] <= -1)
							{
								tabuleiro[i-1][j+1] = tabuleiro[i-1][j+1] - (100+numPeca);
							}							
						}
						if(i < 14)
						{
							if(tabuleiro[i+1][j-1] <= -1)
							{
								tabuleiro[i+1][j-1] = tabuleiro[i+1][j-1] - (100+numPeca);
							}
							if(tabuleiro[i+1][j+1] <= -1)
							{
								tabuleiro[i+1][j+1] = tabuleiro[i+1][j+1] - (100+numPeca);
							}							
						}
					}
					else if( j == 0)
					{
						if(tabuleiro[i][j+1] <= -1)
						{
							tabuleiro[i][j+1] = tabuleiro[i][j+1] - (100+numPeca);
						}
						if(i > 0)
						{
							if(tabuleiro[i-1][j+1] <= -1)
							{
								tabuleiro[i-1][j+1] = tabuleiro[i-1][j+1] - (100+numPeca);
							}							
						}
						if(i < 14)
						{
							if(tabuleiro[i+1][j+1] <= -1)
							{
								tabuleiro[i+1][j+1] = tabuleiro[i+1][j+1] - (100+numPeca);
							}							
						}
					}
					else if (j == 14)
					{
						if(tabuleiro[i][j-1] <= -1)
						{
							tabuleiro[i][j-1] = tabuleiro[i][j-1] - (100+numPeca);
						}
						if(i > 0)
						{
							if(tabuleiro[i-1][j-1] <= -1)
							{
								tabuleiro[i-1][j-1] = tabuleiro[i-1][j-1] - (100+numPeca);
							}
						}
						if(i < 14)
						{
							if(tabuleiro[i+1][j-1] <= -1)
							{
								tabuleiro[i+1][j-1] = tabuleiro[i+1][j-1] - (100+numPeca);
							}
						}
					}
				}
				
			}	
		}
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == numPeca)
				{
					if( i > 0 && i < 14)
					{
						if(tabuleiro[i-1][j] <= -1)
						{
							tabuleiro[i-1][j] = tabuleiro[i-1][j] - (100+numPeca);
						}
						if(tabuleiro[i+1][j] <= -1)
						{
							tabuleiro[i+1][j] = tabuleiro[i+1][j] - (100+numPeca);
						}
					}
					if(i == 0)
					{
						if(tabuleiro[i+1][j] <= -1)
						{
							tabuleiro[i+1][j] = tabuleiro[i+1][j] - (100+numPeca);
						}
					}
					if(i==14)
					{
						if(tabuleiro[i-1][j] <= -1)
						{
							tabuleiro[i-1][j] = tabuleiro[i-1][j] - (100+numPeca);
						}
					}
				}
			}	
		}
	}
	
	public int removePeca(int posX , int posY, short numTab) 
	{
		int [][] tabuleiro;
		int numPeca = 0;
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		numPeca = tabuleiro[posY][posX];
		
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == numPeca)
				{
					System.out.print("I: ");
					System.out.print(i);
					System.out.print(" J: ");
					System.out.println(j);
					tabuleiro[i][j]= -1;
				}
			}
		}
		return numPeca;
	}
	
	public void removePadding(int posX , int posY, short numTab)
	{
		int [][] tabuleiro;	
		int contador = 0;
		
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		
		int numPeca = tabuleiro[posY][posX];
		
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == numPeca)
				{
					if( j > 0 && j < 14)
					{
						if(tabuleiro[i][j-1] <= -1)
						{
							tabuleiro[i][j-1] = tabuleiro[i][j-1] + (100+numPeca);
						}
						if(tabuleiro[i][j+1] <= -1)
						{
							tabuleiro[i][j+1] = tabuleiro[i][j+1] + (100+numPeca);
						}
						if(i > 0)
						{
							if(tabuleiro[i-1][j-1] <= -1)
							{
								tabuleiro[i-1][j-1] = tabuleiro[i-1][j-1] + (100+numPeca);
							}
							if(tabuleiro[i-1][j+1] <= -1)
							{
								tabuleiro[i-1][j+1] = tabuleiro[i-1][j+1] + (100+numPeca);
							}							
						}
						if(i < 14)
						{
							if(tabuleiro[i+1][j-1] <= -1)
							{
								tabuleiro[i+1][j-1] = tabuleiro[i+1][j-1] + (100+numPeca);
							}
							if(tabuleiro[i+1][j+1] <= -1)
							{
								tabuleiro[i+1][j+1] = tabuleiro[i+1][j+1] + (100+numPeca);
							}							
						}
					}
					else if( j == 0)
					{
						if(tabuleiro[i][j+1] <= -1)
						{
							tabuleiro[i][j+1] = tabuleiro[i][j+1] + (100+numPeca);
						}
						if(i > 0)
						{
							if(tabuleiro[i-1][j+1] <= -1)
							{
								tabuleiro[i-1][j+1] = tabuleiro[i-1][j+1] + (100+numPeca);
							}							
						}
						if(i < 14)
						{
							if(tabuleiro[i+1][j+1] <= -1)
							{
								tabuleiro[i+1][j+1] = tabuleiro[i+1][j+1] + (100+numPeca);
							}							
						}
					}
					else if (j == 14)
					{
						if(tabuleiro[i][j-1] <= -1)
						{
							tabuleiro[i][j-1] = tabuleiro[i][j-1] + (100+numPeca);
						}
						if(i > 0)
						{
							if(tabuleiro[i-1][j-1] <= -1)
							{
								tabuleiro[i-1][j-1] = tabuleiro[i-1][j-1] + (100+numPeca);
							}
						}
						if(i < 14)
						{
							if(tabuleiro[i+1][j-1] <= -1)
							{
								tabuleiro[i+1][j-1] = tabuleiro[i+1][j-1] + (100+numPeca);
							}
						}
					}
				}
				
			}	
		}
		for(int i = 0; i < 15;i++){
			for(int j = 0; j < 15;j++){
				if(tabuleiro[i][j] == numPeca)
				{
					if( i > 0 && i < 14)
					{
						if(tabuleiro[i-1][j] <= -1)
						{
							tabuleiro[i-1][j] = tabuleiro[i-1][j] + (100+numPeca);
						}
						if(tabuleiro[i+1][j] <= -1)
						{
							tabuleiro[i+1][j] = tabuleiro[i+1][j] + (100+numPeca);
						}
					}
					if(i == 0)
					{
						if(tabuleiro[i+1][j] <= -1)
						{
							tabuleiro[i+1][j] = tabuleiro[i+1][j] + (100+numPeca);
						}
					}
					if(i==14)
					{
						if(tabuleiro[i-1][j] <= -1)
						{
							tabuleiro[i-1][j] = tabuleiro[i-1][j] + (100+numPeca);
						}
					}
				}
			}	
		}
	}
	
	public void addObserver(Observer o) {
		lob.add(o);
	}

	public void saveGame(boolean jog1Posicionado, boolean jog2Posicionado ,int Vez, int numAcertos , boolean attackHasEnded) {
		if(jog1Posicionado == true &&  jog2Posicionado == true)
		{
			String data = "";
			File saveFile;
			FileWriter fr = null;
			int i, j;
			try {
				saveFile = new File("saveFile.txt");
	            fr = new FileWriter(saveFile);
	            
	            
	            for(i = 0; i < 15; i++) /* Salvar tabuleiro do jogador 1 */ {
	            	for(j = 0; j < 15; j++) {
	            		data = data + Integer.toString(tabuleiro1[j][i]);
	            		if(i == 14 && j == 14) {
	            			data = data + "\n";
	            		}
	            		else {
	            			data = data + ",";
	            		}
	            	}
	            }
	            for(i = 0; i < 15; i++) {
	            	for(j = 0; j < 15; j++) /* Salvar tabuleiro do jogador 2 */ {
	            		data = data + Integer.toString(tabuleiro2[j][i]);
	            		if(i == 14 && j == 14) {
	            			data = data + "\n";
	            		}
	            		else {
	            			data = data + ",";
	            		}
	            	}
	            }
	            
	            // Salvar de qual jogador é a vez
	            if(attackHasEnded)
	            {
		            if(vez == 1) {
		            	data = data + Integer.toString(2) + "\n";
		            }
		            else {            	
		            	data = data + Integer.toString(1) + "\n";
		            }
	            }
	            else
	            {
	            	 if(vez == 1) {
	 	            	data = data + Integer.toString(1) + "\n";
	 	            }
	 	            else {            	
	 	            	data = data + Integer.toString(2) + "\n";
	 	            }
	            }
	            // Salvar numero de tiros já feitos
	            data = data + Integer.toString(numAcertos) + "\n";
	            		
	            // Salvar nome dos jogadores
	            data = data + j1.getMyName() + "\n";
	            data = data + j2.getMyName();
	            
	            
	            fr.write(data);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            //close resources
	            try {
	                fr.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
	}

	public void loadGame(File loadGameFile) {
		Scanner s;
		String line;
		int i = 0;
		try {
			s = new Scanner(loadGameFile);
			while(s.hasNextLine()) {
				line = s.nextLine();
				
				if(i == 0) /* Preencher tabuleiro do jogador 1 */ {
					fillBoard((short)1, line);
				}
				else if(i == 1) /* Preencher tabuleiro do jogador 2 */ {
					fillBoard((short)2, line);	
				}
				else if(i == 2) /* Preencher de quem é a vez */ {
					vez = Integer.parseInt(line);
					System.out.printf("printando vez %d\n", vez);
				}
				
				else if(i == 3) /* Preencher quantos tiros foram feitos TODO */ 
				{	
					data[0] = "setNumAttacks";
					data[1] = Integer.parseInt(line);
							
					for(Observer o:lob)
						o.notify(this);
				}
				
				else if(i == 4) /* Preencher nome do jogador 1 */ {
					j1.setMyName(line);
				}
				
				else if(i == 5) /* Preencher nome do jogador 2  */ {
					j2.setMyName(line);
				}
				
				i++;
			}
			
			//System.out.println(vez);
			System.out.println(j1.getMyName());
			System.out.println(j2.getMyName());
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	private void fillBoard(short numTab, String line) {
		int i, j, k=0;
		
		String[] values = line.split(",", -1);
		
		for(i = 0; i < 15; i++) {
			for(j = 0; j < 15; j++) {
				if(numTab == 1) {
					tabuleiro1[j][i] = Integer.parseInt(values[k++]);
				}
				else {
					tabuleiro2[j][i] = Integer.parseInt(values[k++]);	
				}
			}
		}

	}
	
	public Jogador getJogador(int numJog) {
		if(numJog == 1) {
			return j1;
		}
		else {
			return j2;
		}
	}
	
	public void removeObserver(Observer o) {
		lob.remove(o);
	}

	public Object get() {

		
		return data;
	}

	public void reset()
	{
		this.tabuleiro1 = new int[15][15];
		this.tabuleiro2 = new int[15][15];
		int i, j;
		for(i = 0; i < 15;i++){
			for(j = 0; j < 15;j++){
				this.tabuleiro1[i][j] = -1;
				this.tabuleiro2[i][j] = -1;
			}	
		}

	}

}
