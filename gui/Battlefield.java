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
	public int ataques = 0;
	int pressedX=0 , pressedY=0;
	List<Observer> lob=new ArrayList<Observer>();
	Object data[] = new Object[5]; 
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
		
		//System.out.println("desenhando a parada ");
		
		if(AttackMode == false) {
			for(int i=0;i<15;i++) {
				for(int j=0;j<15;j++) {
					if(mat[i][j] != -1) 
					{
						//System.out.println("dando merda onde vc espera");
						if(numTab == 1)
							g2d.setPaint(Color.YELLOW);
						else
							g2d.setPaint(Color.GREEN);
						rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
						g2d.fill(rt);
					}
					
					if (mat[i][j] < -1)
					{
						
						g2d.setColor(Color.RED);
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
				for(int i=0;i<15;i++) {
					for(int j=0;j<15;j++) 
					{
						
						if(mat[i][j] == 200)
						{
							g2d.setColor(Color.DARK_GRAY);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else if(mat[i][j] >= 100)
						{
							g2d.setColor(Color.RED);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else if(mat[i][j] == -500)
						{
							g2d.setColor(Color.BLUE);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else //if(mat[i][j]== 0) 
						{
							g2d.setColor(Color.CYAN);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						 
					}
				}
			}
			else
			{

				for(int i=0;i<15;i++) {
					for(int j=0;j<15;j++) {
						
						
						
						if(mat[i][j] == 200)
						{
							g2d.setColor(Color.DARK_GRAY);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else if(mat[i][j] >= 100)
						{
							g2d.setColor(Color.RED);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else if(mat[i][j] == -500)
						{
							g2d.setColor(Color.BLUE);
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						}
						else if(mat[i][j] >= 0) 
						{
							if(numTab == 1)
								{g2d.setPaint(Color.YELLOW);}
							else
								{g2d.setPaint(Color.GREEN);}
							rt=new Rectangle2D.Double(tab[i][j].getX()+(espLinha/2),tab[i][j].getY()+(espLinha/2),larg+1,alt+1);
							g2d.fill(rt);
						
						}
						else //if(mat[i][j]== 0) 
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
	
	public Boolean setPeca(int[][] peca, int x, int y, int offsetX, int offsetY, int numPeca) {
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
						ctrl.setValor(posX+j-offsetX,posY+i-offsetY, numTab , numPeca );
					}
				}
			}
		}
		ctrl.setPadding(numTab, numPeca);
		repaint();
		return true;
	}
	
	public void setAttackMode() 
	{

		this.AttackMode = true;
		this.isHidden = true;
		ataques = 0;
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
		else
		{
			
			int posX = (int)((e.getX()/(espLinha+larg))) - 1;
			int posY = (int)((e.getY()/(espLinha+alt))) - 1;
			
			
			if(ctrl.verificaPeca(posX, posY, numTab))
			{
				this.removePeca(posX, posY, numTab);
			}
		}

	}
	
	public void removePeca(int posX, int posY, short numTab)
	{
		
		ctrl.removePadding(posX, posY, numTab);
		int numPeca = ctrl.removePeca(posX, posY, numTab);
		repaint();
		
		data[0] = "remove";
		data[1] = numPeca ;
		data[2] = numTab;
		
		for(Observer o:lob)
			o.notify(this);
	}
	
	public void Attack(int x , int y)
	{
		if(x > 40 && y > 40 )
		{
			int celX = (x - 40) / (30 + 5); 
			int celY = (y - 40) / (30 + 5);
			boolean afundou = false;
			System.out.println("atacando");

//			System.out.println("passou to teste");
			
			short estado = ctrl.orderAttack(nomeJog , x, y, numTab);
			data[0] = "attack-executed";
			
			System.out.printf("estado do ataque: %d\n", estado);
			
			if(celX >= 0 && celY >= 0 && celX < 15 && celY < 15 ) {
				if(estado == 0) // Nao atingiu pe�a
				{
					
					tab[celY][celX].setEstado(Estado.Erro);
					
					data[1] = ataques;
					data[2] = numTab;
					data[3] = "erro";
					
					for(Observer o:lob)
						o.notify(this);
					
				}
				else if(estado == 1) // Atingiu uma peca
				{
					System.out.println("entrando no if");
					tab[celY][celX].setEstado(Estado.Atacado); 
					afundou = ctrl.checaPecaAfundada(numTab, x, y);
					ataques++;
					data[1] = ataques;
					data[2] = numTab;
					data[3] = "peca";
					data[4] = ctrl.getUltimaPeca();
					for(Observer o:lob)
						o.notify(this);
				}
				else // Atacou uma celula j� atacada
				{
					ataques++;
					data[1] = ataques;
					data[2] = numTab;
					data[3] = "agua";
					for(Observer o:lob)
						o.notify(this);
				}
			}
			if(ataques == 3) {
				ataques = 0;
			}
			
			if(afundou)
			{
				if(ctrl.checaVencedor(numTab))
				{
					repaint();
					System.out.println("Venceu");
					int reposta  = JOptionPane.showConfirmDialog(null, "Jogador "+ nomeJog.strip() + " Venceu!!!!, \n Quer Jogar De novo?", "Vitoria", JOptionPane.YES_NO_OPTION);
					if(reposta == JOptionPane.YES_OPTION)
					{
						// TO-DO
						data[0] = "recomeca";
					
						
						for(Observer o:lob)
							o.notify(this);
					}
					else
					{
						System.exit(0);
					}
				}
			}
			repaint();

		}
	}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	
	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
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
		return data;
	}
	
	public void reset()
	{
		tab = new Celula[15][15];
		this.AttackMode = false;
		this.isHidden = false;
		this.isUnderAttack = false;
		this.ataques = 0;
		this.pressedX=0 ; this.pressedY=0;
		
		this.data= new Object[5]; 
		ctrl.reset();
	}
}
