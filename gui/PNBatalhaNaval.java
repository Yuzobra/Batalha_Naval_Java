package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import regras.*;
 
public class PNBatalhaNaval extends JPanel implements MouseListener {
	double xIni=40.0,yIni=40.0,xIni2=800.0,larg=30.0,alt=30.0,espLinha=5.0;
	int iClick,jClick;
	Celula tab1[][]=new Celula[15][15];
	Celula tab2[][]=new Celula[15][15];
	String[] jogadores;
	
	Line2D.Double ln1[]=new Line2D.Double[32];
	Line2D.Double ln2[]=new Line2D.Double[32];
	CtrlRegras ctrl;
	
	
	JTextField nameTextField = new JTextField(20);
	JTextField nameTextField2 = new JTextField(20);
    JButton button = new JButton("Start");

	public PNBatalhaNaval(CtrlRegras c) {
		double x=xIni,y=yIni;
		ctrl=c;
		
		for(int i=0;i<15;i++) {
			x=xIni;
			for(int j=0;j<15;j++) {
				tab1[i][j]=new Celula(x,y);
				x+=larg+espLinha;
			}
			y+=alt+espLinha;
		}
		
		y=yIni;
		for(int i=0;i<15;i++) {
			x=xIni2;
			for(int j=0;j<15;j++) {
				tab2[i][j]=new Celula(x,y);
				x+=larg+espLinha;
			}
			y+=alt+espLinha;
		}
		
		addMouseListener(this);
		
		for(int i = 0; i < 15; i++ ) {
			ln1[i]= new Line2D.Double((xIni + (i*(larg+espLinha))),yIni , (xIni + (i*(larg+espLinha))), yIni+(15*(alt+espLinha)));
		}
		for(int i = 0; i < 15; i++ ) {
			ln1[i+15]= new Line2D.Double(xIni,(yIni +(i*(alt+espLinha))), xIni+(15*(larg+espLinha)), (yIni + (i*(alt+espLinha))));
		}
		ln1[30] = new Line2D.Double(xIni+(15*(larg+espLinha)),yIni, xIni+(15*(larg+espLinha)), (yIni + (15*(alt+espLinha))));
		ln1[31] = new Line2D.Double(xIni,yIni+(15*(alt+espLinha)), xIni+(15*(larg+espLinha)), (yIni + (15*(alt+espLinha))));
		
		
		for(int i = 0; i < 15; i++ ) {
			ln2[i]= new Line2D.Double((xIni2 + (i*(larg+espLinha))),yIni , (xIni2 + (i*(larg+espLinha))), yIni+(15*(alt+espLinha)));
		}
		for(int i = 0; i < 15; i++ ) {
			ln2[i+15]= new Line2D.Double(xIni2,(yIni +(i*(alt+espLinha))), xIni2+(15*(larg+espLinha)), (yIni + (i*(alt+espLinha))));
		}
		ln2[30] = new Line2D.Double(xIni2+(15*(larg+espLinha)),yIni, xIni2+(15*(larg+espLinha)), (yIni + (15*(alt+espLinha))));
		ln2[31] = new Line2D.Double(xIni2,yIni+(15*(alt+espLinha)), xIni2+(15*(larg+espLinha)), (yIni + (15*(alt+espLinha))));
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		Rectangle2D rt;
		int mat1[][]=ctrl.getMatriz1();
		int mat2[][]=ctrl.getMatriz2();
		int vez=ctrl.getVez();
		jogadores = ctrl.getJogadores();
		
		if(jogadores[0].compareTo("") == 0 || jogadores[1].compareTo("") == 0) {
			g2d.drawString("Jogador 1:", 650, 350);
			g2d.drawString("Jogador 2:", 650, 400);
			nameTextField.setBounds(715, 335, 100, 25);
	        nameTextField.setFont(nameTextField.getFont().deriveFont(15f));

			nameTextField2.setBounds(715, 385, 100, 25); 
	        nameTextField2.setFont(nameTextField.getFont().deriveFont(15f));

	        button.addActionListener(new IntroButton());
	        button.setBounds(670, 450, 100, 40);
	        add(nameTextField);
	        add(nameTextField2);
	        add(button);
		}
		else {
			remove(button);
			remove(nameTextField);
			remove(nameTextField2);
			g2d.setStroke(new BasicStroke(5.0f,
	                BasicStroke.CAP_BUTT,
	                BasicStroke.JOIN_MITER,
	                10.0f));
					
			g2d.setPaint(Color.black);
			
			for(int i=0;i<32;i++) {
				g2d.draw(ln1[i]);
				g2d.draw(ln2[i]);
				
			}
			
			g2d.drawString(jogadores[0], (int)(xIni+(7.5*(larg+espLinha))), (int)(yIni/2));
			g2d.drawString(jogadores[1], (int)(xIni2+(7.5*(larg+espLinha))),(int) (yIni/2));
			
			
			String labelY = "ABCDEFGHIJKLMNOPQRS";
			
			for(int i=0;i<15;i++) {
				g2d.drawString(String.valueOf(labelY.charAt(i)), (int)(xIni-15), (int)(yIni + (i*(alt+espLinha)+alt*0.7)));
				g2d.drawString(String.valueOf(labelY.charAt(i)), (int)(xIni2-15), (int)(yIni + (i*(alt+espLinha)+alt*0.7)));
			}
			
			
			
			
			
			for(int i=0;i<15;i++) {
				for(int j=0;j<15;j++) {
					if(mat1[i][j]!=0) {
						g2d.setPaint(Color.red);
						rt=new Rectangle2D.Double(tab1[i][j].x+(espLinha/2),tab1[i][j].y+(espLinha/2),larg+1,alt+1);
						g2d.fill(rt);
					}
					if(mat2[i][j]!=0) {
						g2d.setPaint(Color.blue);
						rt=new Rectangle2D.Double(tab2[i][j].x+(espLinha/2),tab2[i][j].y+(espLinha/2),larg+1,alt+1);
						g2d.fill(rt);
					}

				}
			}
			
		}
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		Rectangle2D rt;
		int x=e.getX(),y=e.getY();
		
		
		y-=yIni;		
		
		if((x > xIni && x < xIni + 15*(larg+espLinha)) && (y > 0 && y < 15*(alt+espLinha))) {
			x-=xIni;
			ctrl.setValor1((int)(x/(larg+espLinha)),(int)(y/(alt+espLinha)));
			repaint();
		}
		else if((x > xIni2 && x < xIni2 + 15*(larg+espLinha)) && (y > 0 && y < 15*(alt+espLinha))) {
			x-=xIni2;
			ctrl.setValor2((int)(x/(larg+espLinha)),(int)(y/(alt+espLinha)));
			repaint();
		}	
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	
	class IntroButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	       ctrl.setJogadores(nameTextField.getText(), nameTextField2.getText());
	       jogadores = ctrl.getJogadores();
	       repaint();
	    }
	}
}