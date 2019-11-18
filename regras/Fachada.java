package regras;

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
}
