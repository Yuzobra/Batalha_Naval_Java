package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import regras.*;
 
public class PNBatalhaNaval extends JPanel implements Observer {
	double xIni=40.0,yIni=40.0,xIni2=800.0,larg=30.0,alt=30.0,espLinha=5.0;
	int iClick,jClick;
	Celula tab1[][]=new Celula[15][15];
	Celula tab2[][]=new Celula[15][15];
	String[] jogadores;
	
	Line2D.Double ln1[]=new Line2D.Double[32];
	Line2D.Double ln2[]=new Line2D.Double[32];
	Fachada ctrl;
	
	
	JTextField nameTextField = new JTextField(20);
	JTextField nameTextField2 = new JTextField(20);
    JButton button = new JButton("Start");

    Battlefield BF1;
    Battlefield BF2;
    JPanel container;
    
    Peca vPecas[];

    boolean jog1Posicionado = false, jog2Posicionado = false;
	

	public PNBatalhaNaval(Fachada f) {
		ctrl = f;
		Fachada.getFachada().register(this);
		BF1 = new Battlefield(xIni, yIni, (short)1, Fachada.getFachada());
		BF2 = new Battlefield(xIni, yIni, (short)2, Fachada.getFachada());
		jogadores = new String[2];
		jogadores[0] = jogadores[1] = "";
		container = new JPanel(new GridLayout(2,1));
		container.setSize(1400, 700);
		container.setLayout(null);
		container.setLocation(0, 0);
		
		
		vPecas = new Peca[15];
		this.CriaPecas();
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		
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
			
			// ADD TABULEIROS
			
			if(!jog1Posicionado) {
				// ADD TABULEIROS
				BF1.setBounds(700,0, 700, 1000);
				container.add(BF1,15);
			}
			
			else if (!jog2Posicionado) {
				container.remove(BF1);
				BF2.setBounds(700,0, 700, 1000);
				setPecas();
				container.add(BF2,15);
				
			}
			else {
				BF1.setBounds(0,0, 700, 1000);
				container.remove(BF2);
				BF1.setAttackMode();
				BF2.setAttackMode();
				container.add(BF1,0);
				container.add(BF2,1);
			}		    
		    add(container);
		    
		}		
	}
	
	
	
	
	class IntroButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	       ctrl.setJogadores(nameTextField.getText(), nameTextField2.getText());
	       BF1.setJogador(jogadores[0]);
	       BF2.setJogador(jogadores[1]);
	    }
	}


	public void notify(Observable o) {
		Object lob[] =(Object[]) o.get();
		String type = (String) lob[0];
		if(type.compareTo("regras") == 0) {
			jogadores = (String[])lob[4];	
			repaint();
		}
		else if(type.compareTo("movement-released") == 0) {
			if((int)lob[1] > (int)(700 + xIni) && (int)lob[2] > (int)yIni) {

				//AJEITAR ISSO:
				if(!jog1Posicionado) {
					if(BF1.setPeca((int[][])lob[5], (int)lob[1] - 700, (int)lob[2],(int)lob[3],(int)lob[4])) {
						container.remove(vPecas[(int)lob[6]]);
					}	
					if(container.getComponentCount() == 1) {
						jog1Posicionado = true;
						repaint();
					}
				
				}
				else if(!jog2Posicionado){					
					if(BF2.setPeca((int[][])lob[5], (int)lob[1] - 700, (int)lob[2],(int)lob[3],(int)lob[4])) {
						container.remove(vPecas[(int)lob[6]]);
					}
					if(container.getComponentCount() == 1) {
						jog2Posicionado = true;
						repaint();
					}
				}
			}
		}
	}
	
	
	private void CriaPecas(){
		int[][] matriz = new int[][]{{0,1,0},{1,0,1}};
		Color myColor;
		myColor = new Color(230, 170, 7);
		this.vPecas[0] = new Peca(matriz, myColor);
		this.vPecas[1] = new Peca(matriz, myColor);
		this.vPecas[2] = new Peca(matriz, myColor);
		this.vPecas[3] = new Peca(matriz, myColor);
		this.vPecas[4] = new Peca(matriz, myColor);
		
		
		myColor = new Color(109, 158, 89);		
		matriz = new int[][]{{1,1,1,1}};
		this.vPecas[5] = new Peca(matriz, myColor);
		this.vPecas[6] = new Peca(matriz, myColor);
		
		myColor = new Color(189, 111, 159);
		matriz = new int[][]{{1}};
		this.vPecas[7] = new Peca(matriz, myColor);
		this.vPecas[8] = new Peca(matriz, myColor);
		this.vPecas[9] = new Peca(matriz, myColor);
		this.vPecas[10] = new Peca(matriz, myColor);
		
		myColor = new Color(111, 170, 189);
		matriz = new int[][]{{1,1}};
		this.vPecas[11] = new Peca(matriz, myColor);
		this.vPecas[12] = new Peca(matriz, myColor);
		this.vPecas[13] = new Peca(matriz, myColor);
		
		myColor = new Color(219, 101, 86);
		matriz = new int[][]{{1,1,1,1,1}};
		this.vPecas[14] = new Peca(matriz, myColor);
		
		Movement mv = new Movement(vPecas[0],0);
		Movement mv1 = new Movement(vPecas[1],1);
		Movement mv2 = new Movement(vPecas[2],2);
		Movement mv3 = new Movement(vPecas[3],3);
		Movement mv4 = new Movement(vPecas[4],4);
		Movement mv5 = new Movement(vPecas[5],5);
		Movement mv6 = new Movement(vPecas[6],6);
		Movement mv7 = new Movement(vPecas[7],7);
		Movement mv8 = new Movement(vPecas[8],8);
		Movement mv9 = new Movement(vPecas[9],9);
		Movement mv10 = new Movement(vPecas[10],10);
		Movement mv11 = new Movement(vPecas[11],11);
		Movement mv12 = new Movement(vPecas[12],12);
		Movement mv13 = new Movement(vPecas[13],13);
		Movement mv14 = new Movement(vPecas[14],14);
		
	    
		mv.addObserver(this);
		mv1.addObserver(this);
		mv2.addObserver(this);
		mv3.addObserver(this);
		mv4.addObserver(this);
		mv5.addObserver(this);
		mv6.addObserver(this);
		mv7.addObserver(this);
		mv8.addObserver(this);
		mv9.addObserver(this);
		mv10.addObserver(this);
		mv11.addObserver(this);
		mv12.addObserver(this);
		mv13.addObserver(this);
		mv14.addObserver(this);
		
		
		vPecas[0].setBounds(50,100, 100,70);
		vPecas[1].setBounds(185,100, 100,70);
		vPecas[2].setBounds(320,100, 100,70);
		vPecas[3].setBounds(455,100, 100,70);				
		vPecas[4].setBounds(590,100, 100,70);				
		
		vPecas[5].setBounds(50,200, 120,30);
		vPecas[6].setBounds(185,200, 120,30);
		
		vPecas[7].setBounds(50,300, 30,30);
		vPecas[8].setBounds(185,300, 30,30);
		vPecas[9].setBounds(320,300, 30,30);
		vPecas[10].setBounds(455,300, 30,30);				
		
		vPecas[11].setBounds(50,400, 60,30);		
		vPecas[12].setBounds(185,400, 60,30);
		vPecas[13].setBounds(320,400, 60,30);
		
		vPecas[14].setBounds(50,500, 150,30);
		
		
		setPecas();
		
	}
	
	private void setPecas() {
		vPecas[0].setLocation(50,100);
		vPecas[1].setLocation(185,100);
		vPecas[2].setLocation(320,100);
		vPecas[3].setLocation(455,100);				
		vPecas[4].setLocation(590,100);				
		
		vPecas[5].setLocation(50,200);
		vPecas[6].setLocation(185,200);
		
		vPecas[7].setLocation(50,300);
		vPecas[8].setLocation(185,300);
		vPecas[9].setLocation(320,300);
		vPecas[10].setLocation(455,300);				
		
		vPecas[11].setLocation(50,400);		
		vPecas[12].setLocation(185,400);
		vPecas[13].setLocation(320,400);
		
		vPecas[14].setLocation(50,500);
	    
		container.add(vPecas[0],0);
	    container.add(vPecas[1],1);
	    container.add(vPecas[2],2);
	    container.add(vPecas[3],3);
	    container.add(vPecas[4],4);
	    container.add(vPecas[5],5);
	    container.add(vPecas[6],6);
	    container.add(vPecas[7],7);
	    container.add(vPecas[8],8);
	    container.add(vPecas[9],9);
	    container.add(vPecas[10],10);
	    container.add(vPecas[11],11);
	    container.add(vPecas[12],12);
	    container.add(vPecas[13],13);
	    container.add(vPecas[14],14);
	}
}