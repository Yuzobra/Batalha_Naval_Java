package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import regras.*;


public class Peca extends JPanel implements MouseListener, MouseMotionListener, Observable {
	private int[][] peca;
	private Color cor;
	private Celula[][] matriz;
	private final double larg=30.0,alt=30.0,espLinha=5.0;
	private int flipped = 0;
	private int pressedX=0, pressedY=0, releasedX=0, releasedY=0, offsetX=0, offsetY=0, numPeca= 0;
	List<Observer> lob=new ArrayList<Observer>();
	
	
	public enum Type {
		Couraçado,HidroPlano,Sub,Crusador,Destroyer;
	}

	private Peca(int[][] peca, Color cor, int numPeca) {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.peca = peca;
		
		matriz = new Celula[peca.length][peca[0].length];
		this.cor = cor;
		double x,y = 0;
		this.numPeca = numPeca;
		for(int i=0;i<peca.length;i++) {
			x = 0;
			for(int j=0;j<peca[0].length;j++) {
				matriz[i][j]=new Celula(x,y);
				x+=larg;
			}
			y+=alt;
		}
		setOpaque(false);
	}
	
    public static Peca criaPeca (Type type, Color cor, int numPeca) {
		int matrix_peca [][] ; 
		
		if(type == Type.Couraçado)
		{
			matrix_peca = new int[][]{{1,1,1,1,1}};
		}
		else if(type == Type.Crusador)
		{
			matrix_peca = new int[][]{{1,1,1,1}};
		}
		else if(type == Type.Destroyer)
		{
			matrix_peca = new int[][]{{1,1}};
		}
		else if(type == Type.HidroPlano)
		{
			matrix_peca = new int[][]{{0,1,0},{1,0,1}};
		}
		else
		{
			matrix_peca = new int [][] {{1}};
		}
		
		Peca p = new Peca(matrix_peca,cor,numPeca);
		return p;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		Rectangle2D rt; 
		
		
		g2d.setStroke(new BasicStroke(5.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f));
				
		g2d.setPaint(cor);
		

		
		for(int i=0;i<peca.length;i++) {
			for(int j=0;j<peca[0].length;j++) {
				if(peca[i][j]!=0) {
					rt=new Rectangle2D.Double(matriz[i][j].getX()+(espLinha/2),matriz[i][j].getY()+(espLinha/2),larg+1,alt+1);
					g2d.fill(rt);
				}
			}
		}
		
		
	}
	
	public int[][] getPeca(){
		return peca;
	}
	
	public void viraPeca() {
		int [][] newPeca;
		newPeca = new int[this.peca[0].length][this.peca.length];
		for(int i=0; i < this.peca.length; i++) {
			for(int j=0; j < this.peca[0].length; j++) {
				newPeca[j][i] = this.peca[i][j];
				flipped++;
			}
		}
		
		if(flipped%2 == 0) {
			int[][] flippedNewPeca = new int[newPeca.length][newPeca[0].length];
			for(int i = 0; i < newPeca.length ;i++) {
				flippedNewPeca[i] = newPeca[newPeca.length-1-i];
			}
			newPeca = flippedNewPeca;
		}
		
		this.peca = newPeca;
		
		double x, y=0;
		matriz = new Celula[peca.length][peca[0].length];
		for(int i=0;i<peca.length;i++) {
			x = 0;
			for(int j=0;j<peca[0].length;j++) {
				matriz[i][j]=new Celula(x,y);
				x+=larg;
			}
			y+=alt;		
		}
		this.setBounds(this.getX(), this.getY(), this.getHeight(), this.getWidth() );
		repaint();
	}

	public void addObserver(Observer o) {
		lob.add(o);
	}

	public void removeObserver(Observer o) {
		lob.remove(o);
	}

	@Override
	public Object get() {
		Object data[] = new Object[7]; 
		data[0] = "movement-released";
		data[1] = this.getX() + pressedX;
		data[2] = this.getY() + pressedY;
		data[3] = offsetX;
		data[4] = offsetY;
		data[5] = peca;
		data[6] = numPeca;
		return data;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			if((int)(e.getY() / alt) > peca.length || (int)(e.getY() / alt) < 0) {
				this.pressedX = e.getX();
			}
			
			
			if((int)(e.getX() / larg) > peca[(int)(e.getY() / alt)].length || (int)(e.getX() / alt) < 0) {
				this.pressedY = e.getY();
			}		
			if(peca[(int)(e.getY() / alt)][(int)(e.getX() / larg)] == 0) {
				int i = (int)(e.getY()/alt);
				int j = 0;
				while(peca[i][j] == 0) {
					j++;
				}
		
				this.pressedX = (int)(larg/2) + (int)(j*larg);
			}	
		}
		catch (Exception e1){

		}
		
		e.getComponent().setLocation((e.getX()+e.getComponent().getX())- pressedX ,(e.getY()+e.getComponent().getY())-pressedY );	
		offsetX = (int) (pressedX/larg);
		offsetY = (int) (pressedY/alt);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON3) {
           this.viraPeca();
          }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
			pressedX = e.getX();
			pressedY = e.getY();
			
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		for(Observer o:lob)
			o.notify(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
