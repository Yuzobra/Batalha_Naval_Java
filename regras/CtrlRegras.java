package regras;

public class CtrlRegras {
	// 0: indica uma casa n�o preenchida
	// -1: indica uma casa preenchida com um ret�ngulo verde
	// 5:  indica uma casa preenchida com um ret�ngulo vermelho
	
	int tabuleiro1 [][];
	int tabuleiro2 [][];
	String jog1;
	String jog2;
	int vez=5;
	
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


	public int[][] getMatriz1() {
		return tabuleiro1;
	}
	public int[][] getMatriz2() {
		return tabuleiro2;
	}
	
	public void setValor1(int i, int j) {
		if (this.tabuleiro1[j][i] == 0) {
			this.tabuleiro1[j][i] = vez;
			return;
		}
		passarVez();
	}
	public void setValor2(int i, int j) {
		if (this.tabuleiro2[j][i] == 0) {
			this.tabuleiro2[j][i] = vez;
			return;
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
	public String[] getJogadores() {
		String[] jogadores =  new String[2];
		jogadores[0] = jog1;
		jogadores[1] = jog2;
		return jogadores;
	}
	public void setJogadores(String jog1, String jog2) {
		this.jog1 = jog1;
		this.jog2 = jog2;
	}
}
