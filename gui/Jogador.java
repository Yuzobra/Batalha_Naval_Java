package gui;

import java.awt.Color;

import gui.Peca.Type;

public class Jogador {
	
	private Peca myPieces [] ;
	private String myName;
	int vez = 0;
	
	
	private Jogador(Peca [] myPieces, String myName, int vez) {
		super();
		this.myName = myName;
		this.myPieces= myPieces;
		this.vez = vez;
	}

	public static Jogador criaJogador(String myName, int vez)
	{
		
		Peca[] p = new Peca[15]; 
		//System.out.println("-------------------inicio jogador -------------------------");
		for(int i=0 ; i<15;i++)
		{
			if(i<5)
			{
				//System.out.println("hidro");
				p[i] = Peca.criaPeca(Type.HidroPlano, new Color(230, 170, 7),i);
			}
			else if (i>=5 && i < 9)
			{
				//System.out.println("sub");
				p[i] = Peca.criaPeca(Type.Sub, new Color(189, 111, 159),i);
			}
			else if(i>=9 && i < 12)
			{
				//System.out.println("destroyer");
				p[i] = Peca.criaPeca(Type.Destroyer, new Color(111, 170, 189),i);
			}
			else if(i>=12 && i < 14)
			{
				//System.out.println("crusador");
				p[i] = Peca.criaPeca(Type.Crusador, new Color(109, 158, 89),i);
			}
			else if(i == 14)
			{
				//System.out.println("couraçado");
				p[i] = Peca.criaPeca(Type.Couraçado, new Color(219, 101, 86),i);
			}
		}
		
		
		
		
		Jogador j = new Jogador(p,myName,vez);
		return j;
	}

	public Peca[] getMyPieces() {
		return myPieces;
	}


	public void setMyPieces(Peca[] myPieces) {
		this.myPieces = myPieces;
	}


	public String getMyName() {
		return myName;
	}


	public void setMyName(String myName) {
		this.myName = myName;
	}


	public int getVez() {
		return vez;
	}


	public void setVez(int vez) {
		this.vez = vez;
	}
	
	
}
