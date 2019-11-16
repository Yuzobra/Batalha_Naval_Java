package gui;

class Celula {
	private	double x,y;
    private	Estado estado;

	
	
	
	public enum Estado{
		Vazio,Ocupado,Atacado,Invalido;
	}
	
	
	public Celula(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		this.estado = Estado.Vazio;
	}






	public double getX() {
		return x;
	}





	public void setX(double x) {
		this.x = x;
	}





	public double getY() {
		return y;
	}





	public void setY(double y) {
		this.y = y;
	}





	public Estado getEstado() {
		return estado;
	}





	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	

	
}
