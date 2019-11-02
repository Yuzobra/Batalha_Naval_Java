package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import regras.*;


public class Peca extends JPanel {
	private int[][] peca;
	private Color cor;
	private Celula[][] matriz;
	private final double larg=30.0,alt=30.0,espLinha=5.0;
	
	
	public Peca(int[][] peca, Color cor) {
		this.peca = peca;
		matriz = new Celula[peca.length][peca[0].length];
		this.cor = cor;
		double x,y = 0;
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
					rt=new Rectangle2D.Double(matriz[i][j].x+(espLinha/2),matriz[i][j].y+(espLinha/2),larg+1,alt+1);
					g2d.fill(rt);
				}
			}
		}
		
		
	}
	
	public int[][] getPeca(){
		return peca;
	}
	
	
	
}
