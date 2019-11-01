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
	
	public void setValor(int x,int y,short numTab) {
		ctrl.setValor(x, y, numTab);
	}
	
	public void setJogadores(String jog1, String jog2) {
		ctrl.setJogadores(jog1, jog2);
	}
}
