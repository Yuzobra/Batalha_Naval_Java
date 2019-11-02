package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.*;
import regras.*;


public class Movement implements MouseListener, MouseMotionListener, Observable{

	private int pressedX, pressedY, releasedX, releasedY, offsetX, offsetY, numPeca;
	List<Observer> lob=new ArrayList<Observer>();
	private final double larg=30.0,alt=30.0;
	
	JPanel panel;
	
	
	public Movement(JPanel panel, int numPeca) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		this.panel = panel;
		this.numPeca = numPeca;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int[][] peca = ((Peca)this.panel).getPeca();
		
		

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

	public void addObserver(Observer o) {
		lob.add(o);
	}


	public void removeObserver(Observer o) {
		lob.remove(o);
	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		Object data[] = new Object[7]; 
		data[0] = "movement-released";
		data[1] = panel.getX() + pressedX;
		data[2] = panel.getY() + pressedY;
		data[3] = offsetX;
		data[4] = offsetY;
		data[5] = ((Peca) panel).getPeca();
		data[6] = numPeca;
		return data;
	}
}
