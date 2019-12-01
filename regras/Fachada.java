package regras;

import java.io.File;

import gui.Jogador;

public class Fachada {
	CtrlRegras ctrl;
	static Fachada f=null;
	
	private Fachada() {
		ctrl=new CtrlRegras();
	}
	
	public static Fachada getFachada() {
		if(f==null)
			f=new Fachada();
		
		return f;	
	}
			
	public int[][] getMatriz(short numTab) {
		return ctrl.getMatriz(numTab);
	}
	
	public int getVez() {
		return ctrl.vez;
	}
	
	public void register(Observer o) {
		ctrl.addObserver(o);
	}
	
	public void setValor(int x,int y,short numTab, int numPeca) {
		ctrl.setValor(x, y, numTab, numPeca);
	}
	
	public void setJogadores(String jog1, String jog2) {
		ctrl.setJogadores(jog1, jog2);
	}
	
	public boolean verificaConflito(int posX, int posY, short numTab) {
		return ctrl.verificaConflito(posX, posY, numTab);
	}
	
	public short orderAttack(String jog, int posX , int posY, short numTab)
	{
		return ctrl.orderAttack(jog, posX, posY, numTab);
	}
	
	public boolean checaVencedor(int vez)
	{
		return ctrl.checaVencedor(vez);
	}
	
	public boolean checaPecaAfundada(int numTab, int posX , int posY)
	{
		return ctrl.checaPecaAfundada(numTab, posX, posY);
	}
	
	public void setPadding(short numTab , int numPeca)
	{
		ctrl.setPadding(numTab, numPeca);
	}
	
	public boolean verificaPeca(int posX , int posY, short numTab)
	{
		return ctrl.verificaPeca(posX, posY, numTab);
	}
	
	public int removePeca(int posX , int posY, short numTab)
	{
		return ctrl.removePeca(posX, posY, numTab);
	}
	
	public void removePadding(int posX , int posY, short numTab)
	{
		ctrl.removePadding(posX, posY, numTab);
	}
	
	public void saveGame(boolean jog1Posicionado, boolean jog2Posicionado, int vez, int numAcertos, boolean attackHasEnded) {
		ctrl.saveGame(jog1Posicionado, jog2Posicionado, vez, numAcertos, attackHasEnded);
	}
	
	public void loadGame(File file) {
		ctrl.loadGame(file);
	}
	
	public Jogador getJogador(int numJog) {
		return ctrl.getJogador(numJog);
	}
}
