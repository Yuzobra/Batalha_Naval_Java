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
				this.tabuleiro1[i][j] = 0;
				this.tabuleiro2[i][j] = 0;
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
	
	public void setValor(int i, int j, short numTab) {
		
		if(numTab == 1) {
			if (this.tabuleiro1[j][i] == 0) {
				this.tabuleiro1[j][i] = vez;
				return;
			}	
		}
		else {
			if (this.tabuleiro2[j][i] == 0) {
				this.tabuleiro2[j][i] = vez;
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
		if(tabuleiro[posY][posX] != 0) {
			return false;
		}
		return true;
	}
	
	public short orderAttack(String jog, int posX , int posY, short numTab)
	{
		int [][] tabuleiro;		

		System.out.printf("posX : %d  posY: %d \n", posX,posY);
		
		
		
		// 1 -> Possui uma peca nao atacada
		// 2 -> Possui uma peca atacada
		// 3 -> Nao possui nada
		
		
		if(numTab == 1) {
			tabuleiro = this.tabuleiro1;
		}
		else {
			tabuleiro = this.tabuleiro2;
		}
		
		if(tabuleiro[posY][posX] == 1 ) 
		{
			tabuleiro[posY][posX] = 2;
			return 1;
		}
		else if(tabuleiro[posY][posX] == 3)
		{
			return 0;
		}
		else
		{
			tabuleiro[posY][posX] = 3;
			return -1;
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
