package regras;

import java.util.ArrayList;
import java.util.List;

public class CtrlRegras implements Observable {	
	int tabuleiro1 [][];
	int tabuleiro2 [][];
	String jog1;
	String jog2;
	int vez=5;
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
		
		passarVez();
	}	
	
	public int getVez() {
		return vez;
	}	

	public void passarVez() {
		if(vez == 5) {
			vez = -1;
		}
		else {
			vez = 5;
		}
	}
	public void setJogadores(String jog1, String jog2) {
		this.jog1 = jog1;
		this.jog2 = jog2;
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
		
		
		if(tabuleiro[celY][celX] >= 0 ) 
		{
			
			tabuleiro[celY][celX] = tabuleiro[celY][celX] + 100;
			System.out.printf("configurando celula como atacada com valor: %d\n" ,tabuleiro[celY][celX]);
			return 1;
		}
		else if(tabuleiro[celY][celX] == -1)
		{
			System.out.println("configurando celula como erro");
			tabuleiro[celY][celX] = -3;
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
	
	
	
	public void addObserver(Observer o) {
		lob.add(o);
	}


	public void removeObserver(Observer o) {
		lob.remove(o);
	}

	@Override
	public Object get() {
		Object data[] = new Object[5];
		String jogs[] = new String[2];
		jogs[0] = jog1;
		jogs[1] = jog2;
		
		data[0] = "regras";
		data[1] = tabuleiro1;
		data[2] = tabuleiro2;
		data[3] = vez;
		data[4] = jogs;
		return data;
	}
}
