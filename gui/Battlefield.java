package gui;

import javax.swing.*;

import gui.Celula.Estado;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import regras.*;
 

public class Battlefield extends JPanel implements MouseListener , Observable {
	private double xIni;
	private double yIni;
	private short numTab;
	private Celula tab[][]=new Celula[15][15];
	private Line2D.Double ln[]=new Line2D.Double[32];
	private Fachada ctrl; 
	private String nomeJog = "placeholder";
	private boolean AttackMode = false;
	private boolean isHidden = false;
	public boolean isUnderAttack = false;
	private int ataques = 3;
	int pressedX=0 , pressedY=0;
	List<Observer> lob=new ArrayList<Observer>();

	private final double larg=30.0,alt=30.0,espLinha=5.0;
	
	public Battlefield(double xIni, double yIni, short numTab, Fachada f){
		this.xIni = xIni;
		this.yIni = yIni;
		this.numTab = numTab;
		addMouseListener(this);
		double x=xIni,y=yIni;
		ctrl=f;
		
		
		for(int i=0;i<15;i++) {
			x=xIni;
			for(int j=0;j<15;j++) {
				tab[i][j]=new Celula(x,y);
				x+=larg+espLinha;
			}
			y+=alt+espLinha;
		}

		
		for(int i = 0; i < 15; i++ ) {
			ln[i]= new Line2D.Double((xIni + (i*(larg+espLinha))),yIni , (xIni + (i*(larg+espLinha))), yIni+(15*(alt+espLinha)));
		}
		for(int i = 0; i < 15; i++ ) {
			ln[i+15]= new Line2D.Double(xIni,(yIni +(i*(alt+espLinha))), xIni+(15*(larg+espLinha)), (yIni + (i*(alt+espLinha))));
		}
		ln[30] = new Line2D.Double(xIni+(15*(larg+espLinha)),yIni, xIni+(15*(larg+espLinha)), (yIni + (15*(alt+espLinha))));
		ln[31] = new Line2D.Double(xIni,yIni+(15*(alt+espLinha)), xIni+(15*(larg+espLinha)), (yIni + (15*(alt+espLinha))));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println("pintando");
		Graphics2D g2d=(Graphics2D) g;
		Rectangle2D rt; 
		int mat[][]=ctrl.getMatriz(numTab);
		int vez=ctrl.getVez();

		g2d.setStroke(new BasicStroke(5.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f));
				
		g2d.setPaint(Color.black);
		
		for(int i=0;i<32;i++) {
			g2d.draw(ln[i]);

			
		}
		
		g2d.drawString(nomeJog, (int)(xIni+(7.5*(larg+espLinha))), (int)(yIni/2));
		
		
		String labelY = "ABCDEFGHIJKLMNO";
		
		for(int i=0;i<15;i++) {
			g2d.drawString(String.valueOf(labelY.charAt(i)), (int)(xIni-15), (int)(yIni + (i*(alt+espLinha)+alt*0.7)));
			g2d.drawString(String.valueOf(i),(int)(xIni +(i*(larg+espLinha))+(larg*0.5)),(int)(yIni-7));
		}
		
		
		
		if(AttackMode == false) {
			for(int i=0;i<15;i++) {
				for(int j=0;j<15;j++) {
					if(mat[i][j]!=0) {
						if(numTab == 1)
							g2d.setPaint(Color.YELLOW);
						else
							g2d.setPaint(Color.GREEN);
						rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
						g2d.fill(rt);
					}
				}
			}			
		}
		else
		{
			//System.out.printf("entro no else com isHidden: %b ", this.isHidden);
			if(isHidden == true)
			{
				System.out.println("escondendo");
				for(int i=0;i<15;i++) {
					for(int j=0;j<15;j++) 
					{

						 if(mat[i][j] == 2)
						{
							g2d.setColor(Color.RED);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else if(mat[i][j] == 3)
						{
							g2d.setColor(Color.BLUE);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else //if(mat[i][j]== 0) 
						{
							//System.out.println("pqp desenha krl ");
							g2d.setColor(Color.CYAN);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
					}
				}
			}
			else
			{
				System.out.println("começando ataque");
				for(int i=0;i<15;i++) {
					for(int j=0;j<15;j++) {
						if(mat[i][j]!=0) 
						{
							if(numTab == 1)
								{g2d.setPaint(Color.YELLOW);}
							else
								{g2d.setPaint(Color.GREEN);}
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						
						}
						else
						{
							g2d.setColor(Color.CYAN);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);							
						}
					}
				}
				
			}
		}
		
		
		
		
	}
	
	public void setJogador(Jogador jogador) {
		this.nomeJog = jogador.getMyName();
	}
	
	public Boolean setPeca(int[][] peca, int x, int y, int offsetX, int offsetY) {
		/* Returns true if you are able to add peca to the battlefield */
		int[][] tabuleiro = ctrl.getMatriz(numTab);
		int posX = (int)((x/(espLinha+larg))) - 1;
		int posY = (int)((y/(espLinha+alt))) - 1;
		
		
		
		
		for(int i = 0; i < peca.length; i++) {
			for(int j = 0; j < peca[i].length; j++) {
				if(peca[i][j] != 0) {
					if(posX - offsetX + j < 0 || posX - offsetX +j > 14 || posY - offsetY + i < 0 || posY - offsetY + i > 14) /* Verify if it fits in the battlefield */{
						return false;
					}
					if(ctrl.verificaConflito(posX+j-offsetX, posY+i-offsetY,numTab) == false) /* Verify if there are any pieces conflicting with this one */{
						return false;
					}
				}
			}
		}
		
		if((x > xIni && x < xIni + 15*(larg+espLinha)) && (y > 0 && y < 16*(alt+espLinha))) {
			for(int i = 0; i < peca.length; i++) {
				for(int j = 0; j < peca[i].length; j++) {
					if(peca[i][j] != 0) {
						ctrl.setValor(posX+j-offsetX,posY+i-offsetY, numTab);
					}
				}
			}
		}
		repaint();
		return true;
	}
	
	public void setAttackMode() 
	{

		this.AttackMode = true;
		this.isHidden = true;
		repaint();
	}
	
	/*
	public void setHidden(Graphics g) {
		super.paintComponent(g);
		System.out.println("funcionando");
		Graphics2D g2d=(Graphics2D) g;
		Rectangle2D rt;
		
		g2d.setStroke(new BasicStroke(5.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f));
		
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				
					g2d.setPaint(new Color(135,206,250,1));
					rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
					g2d.fill(rt);
				
			}
		}
		repaint();
	}*/
	
 	public void mouseClicked(MouseEvent e) {
		Rectangle2D rt;
		int x=e.getX(),y=e.getY();
		y-=yIni;		
		
		if((x > xIni && x < xIni + 15*(larg+espLinha)) && (y > 0 && y < 15*(alt+espLinha))) {
			x-=xIni;
//			ctrl.setValor((int)(x/(larg+espLinha)),(int)(y/(alt+espLinha)), numTab);
//			repaint();
		}	
	}

	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) 
	{
		if(AttackMode == true)
		{
			if(isUnderAttack == true)
			{
				//System.out.println("recebendo ");
				pressedX = e.getX();
				pressedY = e.getY();
				
				Attack(pressedX, pressedY);
			}
			
		}

	}
	
	public void Attack(int x , int y)
	{
		if(x > 40 || y > 40 )
		{
			int celX = (x - 40) / (30 + 5); 
			int celY = (y - 40) / (30 + 5);
			
			//System.out.println("passou to teste");
			
			short estado = ctrl.orderAttack(nomeJog , x, y, numTab);
			
			
	
			if(estado != 0)
			{
				if(estado == 1)
				{
					tab[celY][celX].setEstado(Estado.Atacado); 
				}
				else
				{
					tab[celY][celX].setEstado(Estado.Erro);
				}
				repaint();
			}
		}
	}
	
	
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	
	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		System.out.printf("Settando %b\n" , isHidden);
	}

	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		lob.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		lob.remove(o);
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		Object data[] = new Object[5]; 
		data[0] = "attack-executed";
		data[1] = this.getX() + pressedX;
		data[2] = this.getY() + pressedY;
		data[3] = ataques;
		data[4] = numTab;
		return data;
	}
}
